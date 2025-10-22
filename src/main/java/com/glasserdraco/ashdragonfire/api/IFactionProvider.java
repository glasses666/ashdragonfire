package com.glasserdraco.ashdragonfire.api;

import com.glasserdraco.ashdragonfire.faction.Faction;
import net.minecraft.server.level.ServerPlayer;

/**
 * 阵营提供者接口
 * 
 * 用于扩展和集成其他模组的阵营系统
 * 允许其他模组添加新的阵营或修改阵营行为
 * 
 * 使用场景:
 * - 添加第三方阵营
 * - 自定义阵营关系
 * - 集成其他模组的团队系统
 */
public interface IFactionProvider {
    
    /**
     * 获取玩家的阵营
     * 
     * @param player 玩家
     * @return 阵营
     */
    Faction getPlayerFaction(ServerPlayer player);
    
    /**
     * 设置玩家的阵营
     * 
     * @param player 玩家
     * @param faction 新阵营
     * @return 是否成功设置
     */
    boolean setPlayerFaction(ServerPlayer player, Faction faction);
    
    /**
     * 检查两个玩家是否为敌对关系
     * 
     * @param player1 玩家1
     * @param player2 玩家2
     * @return 是否敌对
     */
    boolean areHostile(ServerPlayer player1, ServerPlayer player2);
    
    /**
     * 检查两个玩家是否为友好关系
     * 
     * @param player1 玩家1
     * @param player2 玩家2
     * @return 是否友好
     */
    boolean areFriendly(ServerPlayer player1, ServerPlayer player2);
}

