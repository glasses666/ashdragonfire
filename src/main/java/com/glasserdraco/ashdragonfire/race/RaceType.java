package com.glasserdraco.ashdragonfire.race;

import com.glasserdraco.ashdragonfire.faction.Faction;

/**
 * 种族类型枚举
 * 
 * 定义游戏中的种族类型
 * 每个种族对应一个阵营,拥有独特的能力和特性
 */
public enum RaceType {
    
    /**
     * 龙族 - 魔法种族
     * 可以变身为龙形态,使用龙语魔法
     */
    DRAGON(
        "dragon",
        "龙族",
        "古老而强大的魔法种族,掌握元素之力",
        Faction.DRAGON
    ),
    
    /**
     * 人类 - 科技种族
     * 使用机械装备和科技武器
     */
    HUMAN(
        "human",
        "人类",
        "新兴的科技文明,以智慧和科技对抗魔法",
        Faction.HUMAN
    );
    
    private final String id;
    private final String displayName;
    private final String description;
    private final Faction faction;
    
    /**
     * 构造函数
     * 
     * @param id 种族ID
     * @param displayName 显示名称
     * @param description 种族描述
     * @param faction 所属阵营
     */
    RaceType(String id, String displayName, String description, Faction faction) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.faction = faction;
    }
    
    public String getId() {
        return id;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Faction getFaction() {
        return faction;
    }
    
    /**
     * 根据阵营获取种族
     * 
     * @param faction 阵营
     * @return 种族类型,如果阵营为NONE则返回null
     */
    public static RaceType fromFaction(Faction faction) {
        for (RaceType race : values()) {
            if (race.faction == faction) {
                return race;
            }
        }
        return null;
    }
    
    /**
     * 根据ID获取种族
     * 
     * @param id 种族ID
     * @return 种族类型,如果不存在则返回null
     */
    public static RaceType fromId(String id) {
        for (RaceType race : values()) {
            if (race.id.equals(id)) {
                return race;
            }
        }
        return null;
    }
}

