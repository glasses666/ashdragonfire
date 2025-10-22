package com.glasserdraco.ashdragonfire.race;

import net.minecraft.server.level.ServerPlayer;

/**
 * 种族能力接口
 * 
 * 定义种族特有能力的标准接口
 * 所有种族能力实现类都应该实现这个接口
 * 
 * 能力类型:
 * - 被动能力: 持续生效的特性
 * - 主动能力: 需要玩家触发的技能
 * - 变身能力: 改变玩家形态
 */
public interface RaceAbility {
    
    /**
     * 获取能力ID
     * 
     * @return 能力唯一标识符
     */
    String getAbilityId();
    
    /**
     * 获取能力名称
     * 
     * @return 能力显示名称
     */
    String getAbilityName();
    
    /**
     * 获取能力描述
     * 
     * @return 能力详细描述
     */
    String getAbilityDescription();
    
    /**
     * 检查玩家是否可以使用该能力
     * 
     * @param player 玩家
     * @return 是否可以使用
     */
    boolean canUse(ServerPlayer player);
    
    /**
     * 激活能力
     * 
     * @param player 玩家
     * @return 是否成功激活
     */
    boolean activate(ServerPlayer player);
    
    /**
     * 取消能力
     * 用于取消持续性能力或变身状态
     * 
     * @param player 玩家
     */
    void deactivate(ServerPlayer player);
    
    /**
     * 能力更新
     * 每tick调用一次,用于处理持续性效果
     * 
     * @param player 玩家
     */
    void tick(ServerPlayer player);
    
    /**
     * 获取能力冷却时间(tick)
     * 
     * @return 冷却时间
     */
    int getCooldown();
    
    /**
     * 获取能量消耗
     * 
     * @return 能量消耗值
     */
    int getEnergyCost();
    
    /**
     * 是否为被动能力
     * 
     * @return true表示被动能力
     */
    boolean isPassive();
}

