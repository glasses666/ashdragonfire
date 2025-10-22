package com.glasserdraco.ashdragonfire.event;

import com.glasserdraco.ashdragonfire.AshDragonfireMod;
import com.glasserdraco.ashdragonfire.core.Config;
import com.glasserdraco.ashdragonfire.faction.Faction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * 战争事件管理器
 * 
 * 管理阵营战争事件的触发、进行和结算
 * 包括定期战争、领地争夺、世界Boss事件等
 * 
 * 事件类型:
 * - 阵营战争: 定期触发的大规模PVP事件
 * - 领地争夺: 争夺特定区域的控制权
 * - 世界Boss: 全服玩家共同挑战的Boss
 * - 突袭事件: 随机触发的小规模战斗
 */
public class WarEventManager {
    
    /**
     * 当前活跃的战争事件
     */
    private static WarEvent currentEvent = null;
    
    /**
     * 距离下次战争事件的游戏内天数
     */
    private static int daysUntilNextEvent = 0;
    
    /**
     * 阵营战争统计数据
     */
    private static final Map<Faction, WarStats> factionStats = new HashMap<>();
    
    static {
        // 初始化阵营统计
        factionStats.put(Faction.DRAGON, new WarStats());
        factionStats.put(Faction.HUMAN, new WarStats());
    }
    
    /**
     * 初始化战争事件系统
     * 
     * @param server 服务器实例
     */
    public static void init(MinecraftServer server) {
        AshDragonfireMod.LOGGER.info("初始化战争事件系统...");
        daysUntilNextEvent = Config.WAR_EVENT_INTERVAL_DAYS.get();
    }
    
    /**
     * 每游戏日更新
     * 应该在服务器tick事件中调用
     * 
     * @param server 服务器实例
     * @param currentDay 当前游戏日
     */
    public static void onDayPassed(MinecraftServer server, long currentDay) {
        daysUntilNextEvent--;
        
        if (daysUntilNextEvent <= 0) {
            // 触发战争事件
            startWarEvent(server);
            daysUntilNextEvent = Config.WAR_EVENT_INTERVAL_DAYS.get();
        } else if (daysUntilNextEvent == 1) {
            // 提前一天通知玩家
            broadcastMessage(server, Component.literal("§c§l[战争警告] 阵营战争将在明天开始!请做好准备!"));
        }
    }
    
    /**
     * 开始战争事件
     * 
     * @param server 服务器实例
     */
    public static void startWarEvent(MinecraftServer server) {
        if (currentEvent != null) {
            AshDragonfireMod.LOGGER.warn("已有战争事件正在进行,无法开始新事件");
            return;
        }
        
        AshDragonfireMod.LOGGER.info("开始阵营战争事件!");
        
        // 创建新的战争事件
        currentEvent = new WarEvent();
        
        // 广播消息
        broadcastMessage(server, Component.literal("§c§l=== 阵营战争开始! ==="));
        broadcastMessage(server, Component.literal("§e龙族与人类的战争再次爆发!"));
        broadcastMessage(server, Component.literal("§e击败敌对阵营玩家获得声望奖励!"));
        
        // TODO: 在前线区域生成战争标记
        // TODO: 增加敌对阵营单位的生成率
        // TODO: 开启特殊战争规则(如双倍声望)
    }
    
    /**
     * 结束战争事件
     * 
     * @param server 服务器实例
     * @param winner 获胜阵营
     */
    public static void endWarEvent(MinecraftServer server, Faction winner) {
        if (currentEvent == null) {
            return;
        }
        
        AshDragonfireMod.LOGGER.info("战争事件结束,获胜方: {}", winner.getDisplayName());
        
        // 更新统计数据
        WarStats stats = factionStats.get(winner);
        if (stats != null) {
            stats.warsWon++;
        }
        
        // 广播结果
        broadcastMessage(server, Component.literal("§c§l=== 阵营战争结束! ==="));
        broadcastMessage(server, Component.literal("§e获胜阵营: ").append(winner.getColoredName()));
        broadcastMessage(server, Component.literal("§e所有参战玩家获得额外声望奖励!"));
        
        // TODO: 发放奖励
        // TODO: 更新世界状态(如天气、光照)
        // TODO: 调整资源产量
        
        currentEvent = null;
    }
    
    /**
     * 玩家击杀事件处理
     * 
     * @param killer 击杀者
     * @param victim 被击杀者
     */
    public static void onPlayerKill(ServerPlayer killer, ServerPlayer victim) {
        if (currentEvent == null) {
            return;
        }
        
        // TODO: 记录击杀数据
        // TODO: 更新战争进度
        // TODO: 检查是否达到胜利条件
    }
    
    /**
     * 检查是否有战争事件正在进行
     * 
     * @return 是否有活跃事件
     */
    public static boolean isWarEventActive() {
        return currentEvent != null;
    }
    
    /**
     * 获取距离下次事件的天数
     * 
     * @return 天数
     */
    public static int getDaysUntilNextEvent() {
        return daysUntilNextEvent;
    }
    
    /**
     * 获取阵营战争统计
     * 
     * @param faction 阵营
     * @return 统计数据
     */
    public static WarStats getFactionStats(Faction faction) {
        return factionStats.get(faction);
    }
    
    /**
     * 向所有玩家广播消息
     * 
     * @param server 服务器实例
     * @param message 消息内容
     */
    private static void broadcastMessage(MinecraftServer server, Component message) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            player.sendSystemMessage(message);
        }
    }
    
    /**
     * 战争事件类
     */
    private static class WarEvent {
        private final long startTime;
        private int dragonKills = 0;
        private int humanKills = 0;
        
        public WarEvent() {
            this.startTime = System.currentTimeMillis();
        }
        
        public long getStartTime() {
            return startTime;
        }
        
        public long getDuration() {
            return System.currentTimeMillis() - startTime;
        }
    }
    
    /**
     * 战争统计数据类
     */
    public static class WarStats {
        private int warsWon = 0;
        private int warsLost = 0;
        private int totalKills = 0;
        private int totalDeaths = 0;
        
        public int getWarsWon() {
            return warsWon;
        }
        
        public int getWarsLost() {
            return warsLost;
        }
        
        public int getTotalKills() {
            return totalKills;
        }
        
        public int getTotalDeaths() {
            return totalDeaths;
        }
        
        public double getWinRate() {
            int total = warsWon + warsLost;
            if (total == 0) return 0.0;
            return (double) warsWon / total;
        }
    }
}

