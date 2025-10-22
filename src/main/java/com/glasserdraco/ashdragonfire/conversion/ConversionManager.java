package com.glasserdraco.ashdragonfire.conversion;

import com.glasserdraco.ashdragonfire.faction.Faction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 阵营转换管理器
 * 
 * 负责管理实体的阵营转换逻辑，包括：
 * - 驯服（龙族 → 玩家伙伴）
 * - 净化（人类 → 龙族阵营）
 * - 支配（龙族 → 人类阵营，强制）
 * - 说服（人类 → 龙族阵营，和平）
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public class ConversionManager {
    
    // 转换冷却时间（毫秒）
    private static final long CONVERSION_COOLDOWN = 60000; // 60秒
    
    // 玩家转换冷却记录
    private static final Map<UUID, Long> playerCooldowns = new HashMap<>();
    
    // 玩家拥有的转换单位数量限制
    private static final int MAX_CONVERTED_UNITS = 3;
    
    /**
     * 检查玩家是否在冷却中
     */
    public static boolean isOnCooldown(Player player) {
        UUID playerId = player.getUUID();
        if (!playerCooldowns.containsKey(playerId)) {
            return false;
        }
        
        long lastConversion = playerCooldowns.get(playerId);
        long currentTime = System.currentTimeMillis();
        
        return (currentTime - lastConversion) < CONVERSION_COOLDOWN;
    }
    
    /**
     * 获取剩余冷却时间（秒）
     */
    public static int getRemainingCooldown(Player player) {
        if (!isOnCooldown(player)) {
            return 0;
        }
        
        UUID playerId = player.getUUID();
        long lastConversion = playerCooldowns.get(playerId);
        long currentTime = System.currentTimeMillis();
        long remaining = CONVERSION_COOLDOWN - (currentTime - lastConversion);
        
        return (int) (remaining / 1000);
    }
    
    /**
     * 设置玩家冷却
     */
    public static void setCooldown(Player player) {
        playerCooldowns.put(player.getUUID(), System.currentTimeMillis());
    }
    
    /**
     * 检查玩家是否达到转换单位数量上限
     */
    public static boolean hasReachedConversionLimit(Player player) {
        // TODO: 实现计数逻辑
        return false;
    }
    
    /**
     * 尝试驯服龙族实体（使用龙魂引）
     * 
     * @param player 玩家
     * @param target 目标龙族实体
     * @param reputation 玩家声望等级（1-10）
     * @return 是否成功
     */
    public static boolean attemptTameDragon(Player player, LivingEntity target, int reputation) {
        // 检查冷却
        if (isOnCooldown(player)) {
            return false;
        }
        
        // 检查数量限制
        if (hasReachedConversionLimit(player)) {
            return false;
        }
        
        // 计算成功率（基础50%，声望每级+4%）
        int baseChance = 50;
        int reputationBonus = reputation * 4;
        int successChance = Math.min(90, baseChance + reputationBonus);
        
        // 随机判定
        boolean success = player.level().random.nextInt(100) < successChance;
        
        if (success) {
            // 转换成功
            convertToBondedDragon(player, target);
            spawnSoulBondParticles(target);
            setCooldown(player);
            return true;
        } else {
            // 转换失败，龙族进入狂暴状态
            makeEntityEnraged(target);
            return false;
        }
    }
    
    /**
     * 净化人类士兵（使用灰烬之血药剂）
     * 
     * @param player 玩家
     * @param target 目标人类实体
     * @return 是否成功
     */
    public static boolean purifyHuman(Player player, LivingEntity target) {
        // 检查冷却
        if (isOnCooldown(player)) {
            return false;
        }
        
        // 净化100%成功
        convertToFlameSworn(target);
        spawnPurificationParticles(target);
        setCooldown(player);
        return true;
    }
    
    /**
     * 支配龙族（使用神经支配者）
     * 
     * @param player 玩家
     * @param target 目标龙族实体
     * @return 是否成功
     */
    public static boolean dominateDragon(Player player, LivingEntity target) {
        // 检查冷却
        if (isOnCooldown(player)) {
            return false;
        }
        
        // 支配100%成功
        convertToMechanizedDrake(target, player);
        spawnDominationParticles(target);
        setCooldown(player);
        return true;
    }
    
    /**
     * 说服人类士兵（使用龙语卷轴）
     * 
     * @param player 玩家
     * @param target 目标人类实体
     * @param reputation 玩家声望等级
     * @return 是否成功
     */
    public static boolean persuadeHuman(Player player, LivingEntity target, int reputation) {
        // 检查冷却
        if (isOnCooldown(player)) {
            return false;
        }
        
        // 计算成功率（基础40%，声望每级+4%）
        int baseChance = 40;
        int reputationBonus = reputation * 4;
        int successChance = Math.min(80, baseChance + reputationBonus);
        
        // 随机判定
        boolean success = player.level().random.nextInt(100) < successChance;
        
        if (success) {
            convertToDragonAlly(target);
            spawnPersuasionParticles(target);
            setCooldown(player);
            return true;
        } else {
            // 失败，士兵逃跑
            makeEntityFlee(target, player);
            return false;
        }
    }
    
    // ==================== 转换实现方法 ====================
    
    /**
     * 转换为链接之龙（驯服）
     */
    private static void convertToBondedDragon(Player owner, LivingEntity dragon) {
        CompoundTag data = dragon.getPersistentData();
        data.putString("ConversionType", "bonded");
        data.putUUID("OwnerUUID", owner.getUUID());
        data.putLong("ConversionTime", System.currentTimeMillis());
        data.putString("OriginalFaction", Faction.DRAKYN_DOMINION.name());
        data.putString("CurrentFaction", "PLAYER_ALLY");
        
        // TODO: 更换AI行为
        // TODO: 更换纹理为 bonded_dragon.png
        // TODO: 添加可骑乘功能
    }
    
    /**
     * 转换为誓火者（净化）
     */
    private static void convertToFlameSworn(LivingEntity human) {
        CompoundTag data = human.getPersistentData();
        data.putString("ConversionType", "flame_sworn");
        data.putLong("ConversionTime", System.currentTimeMillis());
        data.putString("OriginalFaction", Faction.IRON_FEDERATION.name());
        data.putString("CurrentFaction", Faction.DRAKYN_DOMINION.name());
        
        // TODO: 更换AI行为
        // TODO: 更换纹理为 flame_sworn.png
        // TODO: 添加火焰抗性
    }
    
    /**
     * 转换为机械化巨龙（支配）
     */
    private static void convertToMechanizedDrake(LivingEntity dragon, Player owner) {
        CompoundTag data = dragon.getPersistentData();
        data.putString("ConversionType", "mechanized");
        data.putUUID("OwnerUUID", owner.getUUID());
        data.putLong("ConversionTime", System.currentTimeMillis());
        data.putString("OriginalFaction", Faction.DRAKYN_DOMINION.name());
        data.putString("CurrentFaction", Faction.IRON_FEDERATION.name());
        
        // TODO: 更换AI行为
        // TODO: 更换纹理为 mechanized_drake.png
        // TODO: 添加钢铁装甲效果
    }
    
    /**
     * 转换为龙族盟友（说服）
     */
    private static void convertToDragonAlly(LivingEntity human) {
        CompoundTag data = human.getPersistentData();
        data.putString("ConversionType", "ally");
        data.putLong("ConversionTime", System.currentTimeMillis());
        data.putString("OriginalFaction", Faction.IRON_FEDERATION.name());
        data.putString("CurrentFaction", Faction.DRAKYN_DOMINION.name());
        
        // TODO: 更换AI行为
        // TODO: 更换纹理为 dragon_ally_human.png
    }
    
    // ==================== 粒子效果方法 ====================
    
    /**
     * 生成灵魂链接粒子效果（紫色螺旋）
     */
    private static void spawnSoulBondParticles(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 50; i++) {
                double offsetX = (entity.getRandom().nextDouble() - 0.5) * 2;
                double offsetY = entity.getRandom().nextDouble() * 2;
                double offsetZ = (entity.getRandom().nextDouble() - 0.5) * 2;
                
                serverLevel.sendParticles(
                    ParticleTypes.PORTAL,
                    entity.getX() + offsetX,
                    entity.getY() + offsetY,
                    entity.getZ() + offsetZ,
                    1, 0, 0, 0, 0.1
                );
            }
        }
    }
    
    /**
     * 生成净化粒子效果（红色火焰）
     */
    private static void spawnPurificationParticles(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 40; i++) {
                double offsetX = (entity.getRandom().nextDouble() - 0.5) * 1.5;
                double offsetY = entity.getRandom().nextDouble() * 2;
                double offsetZ = (entity.getRandom().nextDouble() - 0.5) * 1.5;
                
                serverLevel.sendParticles(
                    ParticleTypes.FLAME,
                    entity.getX() + offsetX,
                    entity.getY() + offsetY,
                    entity.getZ() + offsetZ,
                    1, 0, 0.1, 0, 0.05
                );
            }
        }
    }
    
    /**
     * 生成支配粒子效果（蓝色电流）
     */
    private static void spawnDominationParticles(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 60; i++) {
                double offsetX = (entity.getRandom().nextDouble() - 0.5) * 2;
                double offsetY = entity.getRandom().nextDouble() * 2.5;
                double offsetZ = (entity.getRandom().nextDouble() - 0.5) * 2;
                
                serverLevel.sendParticles(
                    ParticleTypes.ELECTRIC_SPARK,
                    entity.getX() + offsetX,
                    entity.getY() + offsetY,
                    entity.getZ() + offsetZ,
                    1, 0, 0, 0, 0.2
                );
            }
        }
    }
    
    /**
     * 生成说服粒子效果（金色光点）
     */
    private static void spawnPersuasionParticles(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 30; i++) {
                double offsetX = (entity.getRandom().nextDouble() - 0.5) * 1;
                double offsetY = entity.getRandom().nextDouble() * 2;
                double offsetZ = (entity.getRandom().nextDouble() - 0.5) * 1;
                
                serverLevel.sendParticles(
                    ParticleTypes.END_ROD,
                    entity.getX() + offsetX,
                    entity.getY() + offsetY,
                    entity.getZ() + offsetZ,
                    1, 0, 0, 0, 0.05
                );
            }
        }
    }
    
    // ==================== 失败惩罚方法 ====================
    
    /**
     * 使实体进入狂暴状态
     */
    private static void makeEntityEnraged(LivingEntity entity) {
        CompoundTag data = entity.getPersistentData();
        data.putBoolean("Enraged", true);
        data.putLong("EnrageEndTime", System.currentTimeMillis() + 30000); // 30秒
        
        // TODO: 提升攻击力50%
        // TODO: 添加红色粒子效果
    }
    
    /**
     * 使实体逃跑
     */
    private static void makeEntityFlee(LivingEntity entity, Player player) {
        CompoundTag data = entity.getPersistentData();
        data.putBoolean("Fleeing", true);
        data.putUUID("FleeFrom", player.getUUID());
        data.putLong("FleeEndTime", System.currentTimeMillis() + 15000); // 15秒
        
        // TODO: 添加逃跑AI
        // TODO: 呼叫增援
    }
    
    /**
     * 检查实体是否已被转换
     */
    public static boolean isConverted(LivingEntity entity) {
        return entity.getPersistentData().contains("ConversionType");
    }
    
    /**
     * 获取实体的转换类型
     */
    public static String getConversionType(LivingEntity entity) {
        return entity.getPersistentData().getString("ConversionType");
    }
    
    /**
     * 获取实体的主人UUID
     */
    public static UUID getOwnerUUID(LivingEntity entity) {
        if (entity.getPersistentData().contains("OwnerUUID")) {
            return entity.getPersistentData().getUUID("OwnerUUID");
        }
        return null;
    }
}

