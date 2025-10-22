package com.glasserdraco.ashdragonfire.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;

/**
 * 转换实体AI管理器
 * 
 * 负责为转换后的实体添加AI行为：
 * - 基础AI（游泳、漫游、看向玩家等）
 * - 跟随主人
 * - 保护主人
 * - 攻击敌人
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public class ConvertedAIManager {
    
    /**
     * 为转换后的实体注册AI
     * 
     * @param entity 实体
     * @param conversionType 转换类型
     */
    public static void registerConvertedAI(Mob entity, String conversionType) {
        // 清除原有AI
        entity.goalSelector.removeAllGoals(goal -> true);
        entity.targetSelector.removeAllGoals(goal -> true);
        
        // 添加基础AI
        addBasicAI(entity);
        
        // 根据转换类型添加特定AI
        switch (conversionType) {
            case "bonded":
                addBondedDragonAI(entity);
                break;
            case "flame_sworn":
                addFlameSwornAI(entity);
                break;
            case "mechanized":
                addMechanizedDrakeAI(entity);
                break;
            case "ally":
                addDragonAllyAI(entity);
                break;
        }
    }
    
    /**
     * 添加基础AI（所有转换实体共享）
     */
    private static void addBasicAI(Mob entity) {
        // 优先级0: 游泳
        entity.goalSelector.addGoal(0, new FloatGoal(entity));
        
        // 优先级8: 看向玩家
        entity.goalSelector.addGoal(8, new LookAtPlayerGoal(entity, Player.class, 8.0F));
        
        // 优先级9: 随机看向周围
        entity.goalSelector.addGoal(9, new RandomLookAroundGoal(entity));
        
        // 优先级10: 随机漫游
        entity.goalSelector.addGoal(10, new WaterAvoidingRandomStrollGoal(entity, 0.8D));
    }
    
    /**
     * 添加链接之龙AI
     * 特点：忠诚、可骑乘、保留龙族能力
     */
    private static void addBondedDragonAI(Mob entity) {
        // 优先级1: 跟随主人
        entity.goalSelector.addGoal(1, new FollowOwnerGoal(entity, 1.2D));
        
        // 优先级2: 保护主人
        entity.targetSelector.addGoal(1, new DefendOwnerGoal(entity));
        
        // TODO: 添加龙族特有AI（飞行、吐息等）
    }
    
    /**
     * 添加誓火者AI
     * 特点：人类身体、龙族忠诚、火焰能力
     */
    private static void addFlameSwornAI(Mob entity) {
        // 优先级1: 跟随主人
        entity.goalSelector.addGoal(1, new FollowOwnerGoal(entity, 1.0D));
        
        // 优先级2: 保护主人
        entity.targetSelector.addGoal(1, new DefendOwnerGoal(entity));
        
        // TODO: 添加火焰攻击AI
    }
    
    /**
     * 添加机械化巨龙AI
     * 特点：高防御、失去部分魔法能力、机械化
     */
    private static void addMechanizedDrakeAI(Mob entity) {
        // 优先级1: 跟随主人
        entity.goalSelector.addGoal(1, new FollowOwnerGoal(entity, 0.9D));
        
        // 优先级2: 保护主人
        entity.targetSelector.addGoal(1, new DefendOwnerGoal(entity));
        
        // TODO: 添加机械化攻击AI
    }
    
    /**
     * 添加龙族盟友AI
     * 特点：保留原有能力、和平转换
     */
    private static void addDragonAllyAI(Mob entity) {
        // 优先级1: 跟随主人
        entity.goalSelector.addGoal(1, new FollowOwnerGoal(entity, 1.0D));
        
        // 优先级2: 保护主人
        entity.targetSelector.addGoal(1, new DefendOwnerGoal(entity));
        
        // TODO: 添加人类战斗AI
    }
    
    /**
     * 检查实体是否需要更新AI
     */
    public static boolean needsAIUpdate(Mob entity) {
        // 检查是否被转换
        if (!entity.getPersistentData().contains("ConversionType")) {
            return false;
        }
        
        // 检查是否已经注册过AI
        return !entity.getPersistentData().getBoolean("AIRegistered");
    }
    
    /**
     * 标记AI已注册
     */
    public static void markAIRegistered(Mob entity) {
        entity.getPersistentData().putBoolean("AIRegistered", true);
    }
}

