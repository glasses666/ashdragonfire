package com.glasserdraco.ashdragonfire.ability.human;

import com.glasserdraco.ashdragonfire.core.Config;
import com.glasserdraco.ashdragonfire.energy.TechEnergySystem;
import com.glasserdraco.ashdragonfire.race.RaceAbility;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;

/**
 * 火炮射击技能
 * 
 * 人类远程攻击技能
 * 发射高能炮弹,命中后产生爆炸
 * 
 * 技能特性:
 * - 消耗能量
 * - 有冷却时间
 * - 造成范围爆炸伤害
 * - 可破坏地形(可配置)
 */
public class CannonFireAbility implements RaceAbility {
    
    private static final String ABILITY_ID = "cannon_fire";
    private static final String ABILITY_NAME = "火炮射击";
    private static final String ABILITY_DESCRIPTION = "发射高能炮弹,命中后产生强力爆炸";
    
    private static final int COOLDOWN = 100; // 5秒 (100 ticks)
    private static final int RANGE = 30; // 射程30格
    private static final float EXPLOSION_POWER = 3.0f; // 爆炸威力
    
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
        // 检查是否有足够能量
        return TechEnergySystem.hasEnergy(player, getEnergyCost());
    }
    
    @Override
    public boolean activate(ServerPlayer player) {
        // 检查是否可以使用
        if (!canUse(player)) {
            return false;
        }
        
        // 消耗能量
        if (!TechEnergySystem.consumeEnergy(player, getEnergyCost())) {
            return false;
        }
        
        // 获取玩家视线方向和起始位置
        Vec3 lookVec = player.getLookAngle();
        Vec3 startPos = player.getEyePosition();
        Vec3 endPos = startPos.add(lookVec.scale(RANGE));
        
        // 播放发射音效
        player.level().playSound(null, player.blockPosition(), 
            SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0f, 0.8f);
        
        // 生成炮弹轨迹粒子
        if (player.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < RANGE * 2; i++) {
                double progress = i / (RANGE * 2.0);
                Vec3 particlePos = startPos.add(lookVec.scale(progress * RANGE));
                
                serverLevel.sendParticles(ParticleTypes.SMOKE,
                    particlePos.x, particlePos.y, particlePos.z,
                    2, 0.1, 0.1, 0.1, 0.01);
            }
        }
        
        // 射线检测找到命中点
        var hitResult = player.level().clip(new net.minecraft.world.level.ClipContext(
            startPos, endPos,
            net.minecraft.world.level.ClipContext.Block.COLLIDER,
            net.minecraft.world.level.ClipContext.Fluid.NONE,
            player
        ));
        
        Vec3 explosionPos = hitResult.getLocation();
        
        // 创建爆炸
        player.level().explode(
            player,
            explosionPos.x, explosionPos.y, explosionPos.z,
            EXPLOSION_POWER,
            false, // 不破坏方块(可以改为true允许破坏)
            Explosion.BlockInteraction.KEEP
        );
        
        // 生成爆炸粒子
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.EXPLOSION,
                explosionPos.x, explosionPos.y, explosionPos.z,
                10, 1, 1, 1, 0.1);
        }
        
        return true;
    }
    
    @Override
    public void deactivate(ServerPlayer player) {
        // 火炮射击是瞬发技能,不需要取消
    }
    
    @Override
    public void tick(ServerPlayer player) {
        // 火炮射击是瞬发技能,不需要持续更新
    }
    
    @Override
    public int getCooldown() {
        return COOLDOWN;
    }
    
    @Override
    public int getEnergyCost() {
        return Config.CANNON_FIRE_ENERGY_COST.get();
    }
    
    @Override
    public boolean isPassive() {
        return false;
    }
}

