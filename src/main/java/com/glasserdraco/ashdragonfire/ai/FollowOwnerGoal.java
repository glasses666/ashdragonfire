package com.glasserdraco.ashdragonfire.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

/**
 * 跟随主人AI目标
 * 
 * 转换后的实体会跟随主人移动：
 * - 距离过远时传送到主人身边
 * - 距离适中时跑向主人
 * - 距离过近时停止移动
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public class FollowOwnerGoal extends ConvertedEntityGoal {
    
    private static final double MIN_DISTANCE = 3.0;  // 最小跟随距离
    private static final double MAX_DISTANCE = 10.0; // 最大跟随距离
    private static final double TELEPORT_DISTANCE = 30.0; // 传送距离
    
    private final PathNavigation navigation;
    private final double speedModifier;
    private int timeToRecalcPath;
    private float oldWaterCost;
    
    public FollowOwnerGoal(Mob mob, double speedModifier) {
        super(mob);
        this.speedModifier = speedModifier;
        this.navigation = mob.getNavigation();
    }
    
    @Override
    public boolean canUse() {
        // 必须被转换且有主人
        if (!isConverted() || ownerUUID == null) {
            return false;
        }
        
        Player owner = getOwner();
        if (owner == null) {
            return false;
        }
        
        // 主人死亡或在旁观模式
        if (!owner.isAlive() || owner.isSpectator()) {
            return false;
        }
        
        // 距离太近不需要跟随
        double distance = getDistanceToOwner();
        if (distance < MIN_DISTANCE) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean canContinueToUse() {
        // 正在寻路且距离仍然需要跟随
        if (navigation.isDone()) {
            return false;
        }
        
        double distance = getDistanceToOwner();
        return distance > MIN_DISTANCE;
    }
    
    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = mob.getPathfindingMalus(BlockPathTypes.WATER);
        mob.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }
    
    @Override
    public void stop() {
        navigation.stop();
        mob.setPathfindingMalus(BlockPathTypes.WATER, oldWaterCost);
    }
    
    @Override
    public void tick() {
        Player owner = getOwner();
        if (owner == null) {
            return;
        }
        
        // 让实体看向主人
        mob.getLookControl().setLookAt(owner, 10.0F, mob.getMaxHeadXRot());
        
        // 减少寻路计算频率
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            
            double distance = getDistanceToOwner();
            
            // 距离太远，传送到主人身边
            if (distance > TELEPORT_DISTANCE) {
                teleportToOwner(owner);
            }
            // 距离适中，寻路到主人
            else if (distance > MIN_DISTANCE) {
                navigation.moveTo(owner, speedModifier);
            }
        }
    }
    
    /**
     * 传送到主人身边
     */
    private void teleportToOwner(Player owner) {
        // 在主人周围3格内寻找安全位置
        for (int i = 0; i < 10; i++) {
            double x = owner.getX() + (mob.getRandom().nextDouble() - 0.5) * 6.0;
            double y = owner.getY();
            double z = owner.getZ() + (mob.getRandom().nextDouble() - 0.5) * 6.0;
            
            // 检查位置是否安全
            if (isSafePosition(x, y, z)) {
                mob.teleportTo(x, y, z);
                navigation.stop();
                return;
            }
        }
    }
    
    /**
     * 检查位置是否安全
     */
    private boolean isSafePosition(double x, double y, double z) {
        // TODO: 实现更完善的安全检查
        // 检查是否在固体方块上，上方是否有空间等
        return true;
    }
}

