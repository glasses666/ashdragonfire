package com.glasserdraco.ashdragonfire.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

/**
 * 保护主人AI目标
 * 
 * 转换后的实体会保护主人：
 * - 攻击伤害主人的敌人
 * - 攻击主人正在攻击的目标
 * - 不攻击友军
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public class DefendOwnerGoal extends TargetGoal {
    
    private final Mob mob;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;
    
    public DefendOwnerGoal(Mob mob) {
        super(mob, false);
        this.mob = mob;
    }
    
    @Override
    public boolean canUse() {
        // 检查是否被转换且有主人
        if (!mob.getPersistentData().contains("OwnerUUID")) {
            return false;
        }
        
        Player owner = getOwner();
        if (owner == null) {
            return false;
        }
        
        // 检查主人是否被攻击
        LivingEntity attacker = owner.getLastHurtByMob();
        if (attacker == null) {
            return false;
        }
        
        // 检查攻击者是否是友军
        if (isFriendly(attacker)) {
            return false;
        }
        
        // 检查时间戳，避免攻击过期目标
        int lastHurtByMobTimestamp = owner.getLastHurtByMobTimestamp();
        if (lastHurtByMobTimestamp != this.timestamp && canAttack(attacker, TargetingConditions.DEFAULT)) {
            this.ownerLastHurtBy = attacker;
            this.timestamp = lastHurtByMobTimestamp;
            return true;
        }
        
        return false;
    }
    
    @Override
    public void start() {
        mob.setTarget(ownerLastHurtBy);
        super.start();
    }
    
    /**
     * 获取主人
     */
    private Player getOwner() {
        if (!mob.getPersistentData().contains("OwnerUUID")) {
            return null;
        }
        
        return mob.level().getPlayerByUUID(mob.getPersistentData().getUUID("OwnerUUID"));
    }
    
    /**
     * 检查是否是友军
     */
    private boolean isFriendly(LivingEntity target) {
        Player owner = getOwner();
        if (owner == null) {
            return false;
        }
        
        // 主人本身
        if (target.getUUID().equals(owner.getUUID())) {
            return true;
        }
        
        // 同阵营
        String myFaction = mob.getPersistentData().getString("CurrentFaction");
        String targetFaction = target.getPersistentData().getString("CurrentFaction");
        
        if (myFaction.equals(targetFaction) && !myFaction.isEmpty()) {
            return true;
        }
        
        // 同一主人的转换单位
        if (target.getPersistentData().contains("OwnerUUID")) {
            return target.getPersistentData().getUUID("OwnerUUID").equals(owner.getUUID());
        }
        
        return false;
    }
}

