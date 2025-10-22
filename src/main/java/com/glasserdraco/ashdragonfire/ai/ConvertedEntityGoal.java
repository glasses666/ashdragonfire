package com.glasserdraco.ashdragonfire.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

/**
 * 转换实体AI目标基类
 * 
 * 为所有转换后的实体提供基础AI行为：
 * - 跟随主人
 * - 保护主人
 * - 攻击主人的敌人
 * - 不攻击主人和同阵营单位
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public abstract class ConvertedEntityGoal extends Goal {
    
    protected final Mob mob;
    protected Player owner;
    protected UUID ownerUUID;
    
    public ConvertedEntityGoal(Mob mob) {
        this.mob = mob;
        this.ownerUUID = getOwnerUUID();
    }
    
    /**
     * 获取主人UUID
     */
    protected UUID getOwnerUUID() {
        if (mob.getPersistentData().contains("OwnerUUID")) {
            return mob.getPersistentData().getUUID("OwnerUUID");
        }
        return null;
    }
    
    /**
     * 获取主人玩家实体
     */
    protected Player getOwner() {
        if (ownerUUID == null) {
            return null;
        }
        
        return mob.level().getPlayerByUUID(ownerUUID);
    }
    
    /**
     * 检查实体是否被转换
     */
    protected boolean isConverted() {
        return mob.getPersistentData().contains("ConversionType");
    }
    
    /**
     * 获取转换类型
     */
    protected String getConversionType() {
        return mob.getPersistentData().getString("ConversionType");
    }
    
    /**
     * 检查目标是否是友军
     */
    protected boolean isFriendly(Mob target) {
        // 检查是否是主人
        if (target instanceof Player && target.getUUID().equals(ownerUUID)) {
            return true;
        }
        
        // 检查是否是同阵营
        String myFaction = mob.getPersistentData().getString("CurrentFaction");
        String targetFaction = target.getPersistentData().getString("CurrentFaction");
        
        if (myFaction.equals(targetFaction) && !myFaction.isEmpty()) {
            return true;
        }
        
        // 检查是否是同一主人的转换单位
        if (target.getPersistentData().contains("OwnerUUID")) {
            UUID targetOwner = target.getPersistentData().getUUID("OwnerUUID");
            return targetOwner.equals(ownerUUID);
        }
        
        return false;
    }
    
    /**
     * 计算与主人的距离
     */
    protected double getDistanceToOwner() {
        Player owner = getOwner();
        if (owner == null) {
            return Double.MAX_VALUE;
        }
        return mob.distanceTo(owner);
    }
}

