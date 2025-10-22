package com.glasserdraco.ashdragonfire.faction;

import com.glasserdraco.ashdragonfire.AshDragonfireMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 阵营管理器
 * 
 * 管理所有玩家的阵营数据和阵营相关操作
 * 包括阵营选择、声望系统、领地控制等
 * 
 * 主要功能:
 * - 玩家阵营数据存储与查询
 * - 阵营切换与验证
 * - 声望系统管理
 * - 阵营统计数据
 * 
 * 注意: 这是一个简化的内存存储实现
 * 生产环境应该使用 SavedData 或 Capability 系统持久化数据
 */
@Mod.EventBusSubscriber(modid = AshDragonfireMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FactionManager {
    
    /**
     * 玩家阵营数据缓存
     * Key: 玩家UUID
     * Value: 阵营数据
     */
    private static final Map<UUID, FactionData> playerFactionData = new HashMap<>();
    
    /**
     * 阵营统计数据
     * 记录每个阵营的玩家数量
     */
    private static final Map<Faction, Integer> factionPlayerCount = new HashMap<>();
    
    /**
     * 初始化阵营系统
     * 在主类的 commonSetup 阶段调用
     */
    public static void init() {
        AshDragonfireMod.LOGGER.info("初始化阵营系统...");
        
        // 初始化阵营统计
        for (Faction faction : Faction.values()) {
            factionPlayerCount.put(faction, 0);
        }
        
        AshDragonfireMod.LOGGER.info("阵营系统初始化完成!");
    }
    
    /**
     * 获取玩家的阵营数据
     * 如果不存在则创建新的数据
     * 
     * @param player 玩家
     * @return 阵营数据
     */
    public static FactionData getFactionData(ServerPlayer player) {
        return playerFactionData.computeIfAbsent(player.getUUID(), 
            uuid -> new FactionData(uuid));
    }
    
    /**
     * 设置玩家的阵营
     * 
     * @param player 玩家
     * @param faction 新阵营
     * @return 是否成功
     */
    public static boolean setPlayerFaction(ServerPlayer player, Faction faction) {
        FactionData data = getFactionData(player);
        Faction oldFaction = data.getFaction();
        
        // 检查是否已经是该阵营
        if (oldFaction == faction) {
            return false;
        }
        
        // 检查是否允许切换阵营
        if (oldFaction != Faction.NONE && faction != Faction.NONE) {
            // 已选择阵营后不允许切换(可以根据需求修改)
            AshDragonfireMod.LOGGER.warn("玩家 {} 尝试从 {} 切换到 {}, 但阵营切换被禁止", 
                player.getName().getString(), oldFaction.getDisplayName(), faction.getDisplayName());
            return false;
        }
        
        // 更新阵营
        data.setFaction(faction);
        
        // 更新统计数据
        if (oldFaction != Faction.NONE) {
            factionPlayerCount.merge(oldFaction, -1, Integer::sum);
        }
        factionPlayerCount.merge(faction, 1, Integer::sum);
        
        // 发送消息
        player.sendSystemMessage(Component.literal("你已加入 ")
            .append(faction.getColoredName())
            .append("!"));
        
        AshDragonfireMod.LOGGER.info("玩家 {} 加入了阵营: {}", 
            player.getName().getString(), faction.getDisplayName());
        
        // TODO: 同步到客户端
        // PacketHandler.sendToPlayer(new SyncFactionDataPacket(data), player);
        
        // TODO: 触发阵营加入事件
        // MinecraftForge.EVENT_BUS.post(new FactionJoinEvent(player, faction));
        
        return true;
    }
    
    /**
     * 获取玩家的阵营
     * 
     * @param player 玩家
     * @return 阵营
     */
    public static Faction getPlayerFaction(ServerPlayer player) {
        return getFactionData(player).getFaction();
    }
    
    /**
     * 增加玩家声望
     * 
     * @param player 玩家
     * @param amount 声望值
     */
    public static void addReputation(ServerPlayer player, int amount) {
        FactionData data = getFactionData(player);
        data.addReputation(amount);
        
        AshDragonfireMod.LOGGER.debug("玩家 {} 获得 {} 点声望, 当前声望: {}", 
            player.getName().getString(), amount, data.getReputation());
        
        // TODO: 同步到客户端
        // PacketHandler.sendToPlayer(new SyncFactionDataPacket(data), player);
    }
    
    /**
     * 减少玩家声望
     * 
     * @param player 玩家
     * @param amount 声望值
     */
    public static void removeReputation(ServerPlayer player, int amount) {
        FactionData data = getFactionData(player);
        data.removeReputation(amount);
        
        AshDragonfireMod.LOGGER.debug("玩家 {} 失去 {} 点声望, 当前声望: {}", 
            player.getName().getString(), amount, data.getReputation());
        
        // TODO: 同步到客户端
        // PacketHandler.sendToPlayer(new SyncFactionDataPacket(data), player);
    }
    
    /**
     * 获取阵营的玩家数量
     * 
     * @param faction 阵营
     * @return 玩家数量
     */
    public static int getFactionPlayerCount(Faction faction) {
        return factionPlayerCount.getOrDefault(faction, 0);
    }
    
    /**
     * 检查两个玩家是否为敌对阵营
     * 
     * @param player1 玩家1
     * @param player2 玩家2
     * @return 是否敌对
     */
    public static boolean areHostile(ServerPlayer player1, ServerPlayer player2) {
        Faction faction1 = getPlayerFaction(player1);
        Faction faction2 = getPlayerFaction(player2);
        return faction1.isHostile(faction2);
    }
    
    /**
     * 检查两个玩家是否为友好阵营
     * 
     * @param player1 玩家1
     * @param player2 玩家2
     * @return 是否友好
     */
    public static boolean areFriendly(ServerPlayer player1, ServerPlayer player2) {
        Faction faction1 = getPlayerFaction(player1);
        Faction faction2 = getPlayerFaction(player2);
        return faction1.isFriendly(faction2);
    }
    
    /**
     * 玩家登出事件处理
     * 清理内存中的数据(可选)
     */
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        // 注意: 这里不清理数据,因为玩家可能会重新登录
        // 如果需要持久化,应该在这里保存到文件
        // playerFactionData.remove(event.getEntity().getUUID());
    }
}

