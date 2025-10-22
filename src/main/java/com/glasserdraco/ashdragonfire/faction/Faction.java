package com.glasserdraco.ashdragonfire.faction;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/**
 * 阵营枚举
 * 
 * 定义游戏中的两大对立阵营及中立状态
 * 每个阵营都有独特的特性、颜色和描述
 * 
 * 阵营关系:
 * - 龙族 vs 人类: 敌对关系
 * - 中立: 未选择阵营的玩家
 */
public enum Faction {
    
    /**
     * 中立 - 未选择阵营
     * 玩家初次进入游戏时的默认状态
     */
    NONE(
        "none",
        "未选择阵营",
        "你尚未选择阵营。请选择龙族或人类阵营开始你的旅程。",
        ChatFormatting.GRAY
    ),
    
    /**
     * 龙族阵营 - Drakyn Dominion
     * 掌握魔法与元素力量的古老种族
     */
    DRAGON(
        "dragon",
        "龙族领地",
        "龙族是艾格斯大陆的古老统治者,掌握着强大的龙语魔法和元素之力。他们居住在浮空岛与火山地带,以魔力为生命之源。",
        ChatFormatting.RED
    ),
    
    /**
     * 人类阵营 - Iron Federation
     * 掌握科技与机械力量的新兴势力
     */
    HUMAN(
        "human",
        "钢铁联邦",
        "人类是新兴的科技文明,通过火炮、机械与魔导科技对抗龙族的统治。他们建立了钢铁城市与飞艇要塞,以科技改变世界。",
        ChatFormatting.BLUE
    );
    
    private final String id;
    private final String displayName;
    private final String description;
    private final ChatFormatting color;
    
    /**
     * 构造函数
     * 
     * @param id 阵营ID(用于数据存储)
     * @param displayName 显示名称
     * @param description 阵营描述
     * @param color 阵营颜色
     */
    Faction(String id, String displayName, String description, ChatFormatting color) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.color = color;
    }
    
    /**
     * 获取阵营ID
     * 
     * @return 阵营ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * 获取显示名称
     * 
     * @return 显示名称
     */
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * 获取阵营描述
     * 
     * @return 阵营描述
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 获取阵营颜色
     * 
     * @return 颜色格式
     */
    public ChatFormatting getColor() {
        return color;
    }
    
    /**
     * 获取带颜色的显示名称组件
     * 
     * @return 文本组件
     */
    public MutableComponent getColoredName() {
        return Component.literal(displayName).withStyle(color);
    }
    
    /**
     * 检查是否为敌对阵营
     * 
     * @param other 另一个阵营
     * @return 是否敌对
     */
    public boolean isHostile(Faction other) {
        if (this == NONE || other == NONE) {
            return false;
        }
        return this != other;
    }
    
    /**
     * 检查是否为友好阵营
     * 
     * @param other 另一个阵营
     * @return 是否友好
     */
    public boolean isFriendly(Faction other) {
        return this == other && this != NONE;
    }
    
    /**
     * 根据ID获取阵营
     * 
     * @param id 阵营ID
     * @return 阵营枚举,如果不存在则返回 NONE
     */
    public static Faction fromId(String id) {
        for (Faction faction : values()) {
            if (faction.id.equals(id)) {
                return faction;
            }
        }
        return NONE;
    }
    
    /**
     * 根据序号获取阵营
     * 
     * @param ordinal 序号
     * @return 阵营枚举,如果超出范围则返回 NONE
     */
    public static Faction fromOrdinal(int ordinal) {
        if (ordinal >= 0 && ordinal < values().length) {
            return values()[ordinal];
        }
        return NONE;
    }
}

