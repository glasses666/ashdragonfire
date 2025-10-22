package com.glasserdraco.ashdragonfire.ability.dragon;

import com.glasserdraco.ashdragonfire.core.Config;
import com.glasserdraco.ashdragonfire.energy.ManaSystem;
import com.glasserdraco.ashdragonfire.race.RaceAbility;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 火焰吐息技能
 * 
 * 龙族基础攻击技能
 * 向前方喷射火焰,对锥形范围内的敌人造成伤害
 * 
 * 技能特性:
 * - 消耗魔力
 * - 有冷却时间
 * - 对多个目标造成伤害
 * - 附加燃烧效果
 */
public class FireBreathAbility implements RaceAbility {
    
    private static final String ABILITY_ID = "fire_breath";
    private static final String ABILITY_NAME = "火焰吐息";
    private static final String ABILITY_DESCRIPTION = "向前方喷射火焰,对锥形范围内的敌人造成伤害并点燃目标";
    
    private static final int COOLDOWN = 60; // 3秒 (60 ticks)
    private static final int RANGE = 10; // 射程10格
    private static final float CONE_ANGLE = 30.0f; // 锥形角度30度
    
    @Override
    public String getAbilityId() {
        return ABILITY_ID;
    }
    
    @Override
    public String getAbilityName() {
        return ABILITY_NAME;
    }
    
    @Override
    public String getAbilityDescription() {
        return ABILITY_DESCRIPTION;
    }
    
    @Override
    public boolean canUse(ServerPlayer player) {
        // 检查是否有足够魔力
        return ManaSystem.hasMana(player, getEnergyCost());
    }
    
    @Override
    public boolean activate(ServerPlayer player) {
        // 检查是否可以使用
        if (!canUse(player)) {
            return false;
        }
        
        // 消耗魔力
        if (!ManaSystem.consumeMana(player, getEnergyCost())) {
            return false;
        }
        
        // 获取玩家视线方向
        Vec3 lookVec = player.getLookAngle();
        Vec3 startPos = player.getEyePosition();
        
        // 播放音效
        player.level().playSound(null, player.blockPosition(), 
            SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0f, 1.0f);
        
        // 生成粒子效果
        if (player.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < RANGE * 5; i++) {
                double distance = i * 0.2;
                Vec3 particlePos = startPos.add(lookVec.scale(distance));
                
                // 添加随机偏移形成锥形
                double spread = distance * 0.1;
                double offsetX = (Math.random() - 0.5) * spread;
                double offsetY = (Math.random() - 0.5) * spread;
                double offsetZ = (Math.random() - 0.5) * spread;
                
                serverLevel.sendParticles(ParticleTypes.FLAME,
                    particlePos.x + offsetX,
                    particlePos.y + offsetY,
                    particlePos.z + offsetZ,
                    1, 0, 0, 0, 0.01);
            }
        }
        
        // 检测并伤害锥形范围内的实体
        AABB searchBox = new AABB(startPos, startPos).inflate(RANGE);
        List<LivingEntity> entities = player.level().getEntitiesOfClass(
            LivingEntity.class, searchBox, 
            entity -> entity != player && entity.isAlive()
        );
        
        int damage = Config.FIRE_BREATH_DAMAGE.get();
        
        for (LivingEntity target : entities) {
            // 检查目标是否在锥形范围内
            Vec3 toTarget = target.position().subtract(startPos).normalize();
            double angle = Math.toDegrees(Math.acos(lookVec.dot(toTarget)));
            
            if (angle <= CONE_ANGLE && startPos.distanceTo(target.position()) <= RANGE) {
                // 造成伤害
                target.hurt(player.damageSources().playerAttack(player), damage);
                
                // 点燃目标
                target.setSecondsOnFire(5);
            }
        }
        
        return true;
    }
    
    @Override
    public void deactivate(ServerPlayer player) {
        // 火焰吐息是瞬发技能,不需要取消
    }
    
    @Override
    public void tick(ServerPlayer player) {
        // 火焰吐息是瞬发技能,不需要持续更新
    }
    
    @Override
    public int getCooldown() {
        return COOLDOWN;
    }
    
    @Override
    public int getEnergyCost() {
        return Config.FIRE_BREATH_MANA_COST.get();
    }
    
    @Override
    public boolean isPassive() {
        return false;
    }
}

