package com.glasserdraco.ashdragonfire.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

/**
 * 模组配置类
 * 
 * 使用 Forge 的配置系统管理所有可调整的游戏参数
 * 配置文件会自动生成在 config/ashdragonfire-common.toml
 * 
 * 主要配置项:
 * - 阵营系统参数
 * - 技能伤害与消耗
 * - 事件触发周期
 * - 世界生成设置
 */
@Mod.EventBusSubscriber
public class Config {
    
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    
    // ========== 阵营系统配置 ==========
    public static final ForgeConfigSpec.IntValue REPUTATION_PER_KILL;
    public static final ForgeConfigSpec.IntValue REPUTATION_PER_TERRITORY_CAPTURE;
    public static final ForgeConfigSpec.IntValue MAX_REPUTATION;
    
    // ========== 能量系统配置 ==========
    public static final ForgeConfigSpec.IntValue DRAGON_MAX_MANA;
    public static final ForgeConfigSpec.IntValue DRAGON_MANA_REGEN_RATE;
    public static final ForgeConfigSpec.IntValue HUMAN_MAX_ENERGY;
    public static final ForgeConfigSpec.IntValue HUMAN_ENERGY_REGEN_RATE;
    
    // ========== 技能系统配置 ==========
    public static final ForgeConfigSpec.IntValue FIRE_BREATH_DAMAGE;
    public static final ForgeConfigSpec.IntValue FIRE_BREATH_MANA_COST;
    public static final ForgeConfigSpec.IntValue STORM_CALL_DAMAGE;
    public static final ForgeConfigSpec.IntValue STORM_CALL_MANA_COST;
    public static final ForgeConfigSpec.IntValue CANNON_FIRE_DAMAGE;
    public static final ForgeConfigSpec.IntValue CANNON_FIRE_ENERGY_COST;
    
    // ========== 事件系统配置 ==========
    public static final ForgeConfigSpec.IntValue WAR_EVENT_INTERVAL_DAYS;
    public static final ForgeConfigSpec.IntValue WORLD_BOSS_SPAWN_CHANCE;
    public static final ForgeConfigSpec.BooleanValue ENABLE_TERRITORY_CONFLICT;
    
    // ========== 世界生成配置 ==========
    public static final ForgeConfigSpec.BooleanValue ENABLE_FLAMECREST_DIMENSION;
    public static final ForgeConfigSpec.BooleanValue ENABLE_GEARHOLD_DIMENSION;
    public static final ForgeConfigSpec.IntValue STRUCTURE_SPAWN_CHANCE;
    
    static {
        BUILDER.push("Ash & Dragonfire: Eternal Conflict Configuration");
        
        // 阵营系统
        BUILDER.push("faction");
        REPUTATION_PER_KILL = BUILDER
            .comment("击杀敌对阵营玩家获得的声望值")
            .defineInRange("reputationPerKill", 10, 1, 1000);
        REPUTATION_PER_TERRITORY_CAPTURE = BUILDER
            .comment("占领领地获得的声望值")
            .defineInRange("reputationPerTerritoryCapture", 50, 1, 1000);
        MAX_REPUTATION = BUILDER
            .comment("最大声望值")
            .defineInRange("maxReputation", 10000, 100, 100000);
        BUILDER.pop();
        
        // 能量系统
        BUILDER.push("energy");
        DRAGON_MAX_MANA = BUILDER
            .comment("龙族玩家最大魔力值")
            .defineInRange("dragonMaxMana", 1000, 100, 10000);
        DRAGON_MANA_REGEN_RATE = BUILDER
            .comment("龙族魔力每秒恢复速度")
            .defineInRange("dragonManaRegenRate", 5, 1, 100);
        HUMAN_MAX_ENERGY = BUILDER
            .comment("人类玩家最大能量值")
            .defineInRange("humanMaxEnergy", 1000, 100, 10000);
        HUMAN_ENERGY_REGEN_RATE = BUILDER
            .comment("人类能量每秒恢复速度")
            .defineInRange("humanEnergyRegenRate", 5, 1, 100);
        BUILDER.pop();
        
        // 技能系统
        BUILDER.push("abilities");
        FIRE_BREATH_DAMAGE = BUILDER
            .comment("火焰吐息伤害")
            .defineInRange("fireBreathDamage", 20, 1, 100);
        FIRE_BREATH_MANA_COST = BUILDER
            .comment("火焰吐息魔力消耗")
            .defineInRange("fireBreathManaCost", 50, 1, 500);
        STORM_CALL_DAMAGE = BUILDER
            .comment("风暴召唤伤害")
            .defineInRange("stormCallDamage", 30, 1, 100);
        STORM_CALL_MANA_COST = BUILDER
            .comment("风暴召唤魔力消耗")
            .defineInRange("stormCallManaCost", 80, 1, 500);
        CANNON_FIRE_DAMAGE = BUILDER
            .comment("火炮射击伤害")
            .defineInRange("cannonFireDamage", 25, 1, 100);
        CANNON_FIRE_ENERGY_COST = BUILDER
            .comment("火炮射击能量消耗")
            .defineInRange("cannonFireEnergyCost", 60, 1, 500);
        BUILDER.pop();
        
        // 事件系统
        BUILDER.push("events");
        WAR_EVENT_INTERVAL_DAYS = BUILDER
            .comment("阵营战争事件触发间隔(游戏内天数)")
            .defineInRange("warEventIntervalDays", 7, 1, 30);
        WORLD_BOSS_SPAWN_CHANCE = BUILDER
            .comment("世界Boss生成概率(%)")
            .defineInRange("worldBossSpawnChance", 10, 0, 100);
        ENABLE_TERRITORY_CONFLICT = BUILDER
            .comment("是否启用领地争夺系统")
            .define("enableTerritoryConflict", true);
        BUILDER.pop();
        
        // 世界生成
        BUILDER.push("worldgen");
        ENABLE_FLAMECREST_DIMENSION = BUILDER
            .comment("是否启用焰冠群岛维度")
            .define("enableFlamecrestDimension", true);
        ENABLE_GEARHOLD_DIMENSION = BUILDER
            .comment("是否启用齿轮之城维度")
            .define("enableGearholdDimension", true);
        STRUCTURE_SPAWN_CHANCE = BUILDER
            .comment("结构生成概率(%)")
            .defineInRange("structureSpawnChance", 30, 0, 100);
        BUILDER.pop();
        
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

