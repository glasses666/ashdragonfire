package com.glasserdraco.ashdragonfire.faction;

import com.glasserdraco.ashdragonfire.core.Config;

import java.util.UUID;

/**
 * 阵营数据类
 * 
 * 存储单个玩家的阵营相关数据
 * 包括阵营归属、声望值、贡献度等
 * 
 * 数据持久化:
 * 在生产环境中,这个类应该实现序列化接口
 * 并通过 Capability 或 SavedData 系统保存
 */
public class FactionData {
    
    /** 玩家UUID */
    private final UUID playerUUID;
    
    /** 所属阵营 */
    private Faction faction;
    
    /** 声望值 */
    private int reputation;
    
    /** 击杀数 */
    private int kills;
    
    /** 死亡数 */
    private int deaths;
    
    /** 占领领地数 */
    private int territoriesCaptured;
    
    /** 参与战争事件次数 */
    private int warEventsParticipated;
    
    /**
     * 构造函数
     * 
     * @param playerUUID 玩家UUID
     */
    public FactionData(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.faction = Faction.NONE;
        this.reputation = 0;
        this.kills = 0;
        this.deaths = 0;
        this.territoriesCaptured = 0;
        this.warEventsParticipated = 0;
    }
    
    // ========== Getter 方法 ==========
    
    public UUID getPlayerUUID() {
        return playerUUID;
    }
    
    public Faction getFaction() {
        return faction;
    }
    
    public int getReputation() {
        return reputation;
    }
    
    public int getKills() {
        return kills;
    }
    
    public int getDeaths() {
        return deaths;
    }
    
    public int getTerritoriesCaptured() {
        return territoriesCaptured;
    }
    
    public int getWarEventsParticipated() {
        return warEventsParticipated;
    }
    
    /**
     * 获取K/D比率
     * 
     * @return K/D比率
     */
    public double getKDRatio() {
        if (deaths == 0) {
            return kills;
        }
        return (double) kills / deaths;
    }
    
    // ========== Setter 方法 ==========
    
    public void setFaction(Faction faction) {
        this.faction = faction;
    }
    
    /**
     * 增加声望
     * 
     * @param amount 增加的声望值
     */
    public void addReputation(int amount) {
        this.reputation = Math.min(
            this.reputation + amount, 
            Config.MAX_REPUTATION.get()
        );
    }
    
    /**
     * 减少声望
     * 
     * @param amount 减少的声望值
     */
    public void removeReputation(int amount) {
        this.reputation = Math.max(0, this.reputation - amount);
    }
    
    /**
     * 设置声望
     * 
     * @param reputation 新的声望值
     */
    public void setReputation(int reputation) {
        this.reputation = Math.max(0, Math.min(reputation, Config.MAX_REPUTATION.get()));
    }
    
    /**
     * 增加击杀数
     */
    public void addKill() {
        this.kills++;
    }
    
    /**
     * 增加死亡数
     */
    public void addDeath() {
        this.deaths++;
    }
    
    /**
     * 增加占领领地数
     */
    public void addTerritoryCaptured() {
        this.territoriesCaptured++;
    }
    
    /**
     * 增加参与战争事件次数
     */
    public void addWarEventParticipation() {
        this.warEventsParticipated++;
    }
    
    // ========== 声望等级系统 ==========
    
    /**
     * 获取声望等级
     * 根据声望值计算玩家在阵营中的等级
     * 
     * @return 声望等级 (0-10)
     */
    public int getReputationLevel() {
        if (reputation < 100) return 0;
        if (reputation < 300) return 1;
        if (reputation < 600) return 2;
        if (reputation < 1000) return 3;
        if (reputation < 1500) return 4;
        if (reputation < 2100) return 5;
        if (reputation < 2800) return 6;
        if (reputation < 3600) return 7;
        if (reputation < 4500) return 8;
        if (reputation < 5500) return 9;
        return 10;
    }
    
    /**
     * 获取声望等级名称
     * 
     * @return 等级名称
     */
    public String getReputationLevelName() {
        return switch (getReputationLevel()) {
            case 0 -> "新兵";
            case 1 -> "士兵";
            case 2 -> "精锐";
            case 3 -> "老兵";
            case 4 -> "勇士";
            case 5 -> "英雄";
            case 6 -> "精英";
            case 7 -> "冠军";
            case 8 -> "传奇";
            case 9 -> "神话";
            case 10 -> "不朽";
            default -> "未知";
        };
    }
    
    /**
     * 重置数据
     * 用于玩家切换阵营或重置进度
     */
    public void reset() {
        this.faction = Faction.NONE;
        this.reputation = 0;
        this.kills = 0;
        this.deaths = 0;
        this.territoriesCaptured = 0;
        this.warEventsParticipated = 0;
    }
    
    @Override
    public String toString() {
        return String.format(
            "FactionData{player=%s, faction=%s, reputation=%d, level=%d, kills=%d, deaths=%d}",
            playerUUID, faction.getDisplayName(), reputation, getReputationLevel(), kills, deaths
        );
    }
}

