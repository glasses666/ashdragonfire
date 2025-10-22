package com.glasserdraco.ashdragonfire.energy;

import com.glasserdraco.ashdragonfire.core.Config;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 魔力系统
 * 
 * 龙族专用的能量系统
 * 管理所有龙族玩家的魔力值
 * 
 * 魔力特性:
 * - 用于释放龙语魔法
 * - 可以通过休息或魔法物品恢复
 * - 受环境影响(在火山地带恢复更快)
 */
public class ManaSystem {
    
    /**
     * 玩家魔力数据存储
     * Key: 玩家UUID
     * Value: 魔力数据
     */
    private static final Map<UUID, ManaData> playerManaData = new HashMap<>();
    
    /**
     * 获取玩家的魔力数据
     * 
     * @param player 玩家
     * @return 魔力数据
     */
    public static ManaData getManaData(ServerPlayer player) {
        return playerManaData.computeIfAbsent(player.getUUID(), 
            uuid -> new ManaData(Config.DRAGON_MAX_MANA.get()));
    }
    
    /**
     * 获取玩家当前魔力值
     * 
     * @param player 玩家
     * @return 魔力值
     */
    public static int getMana(ServerPlayer player) {
        return getManaData(player).getEnergy();
    }
    
    /**
     * 消耗玩家魔力
     * 
     * @param player 玩家
     * @param amount 消耗量
     * @return 是否成功消耗
     */
    public static boolean consumeMana(ServerPlayer player, int amount) {
        return getManaData(player).consumeEnergy(amount);
    }
    
    /**
     * 恢复玩家魔力
     * 
     * @param player 玩家
     * @param amount 恢复量
     * @return 实际恢复量
     */
    public static int restoreMana(ServerPlayer player, int amount) {
        return getManaData(player).addEnergy(amount);
    }
    
    /**
     * 检查玩家是否有足够魔力
     * 
     * @param player 玩家
     * @param amount 需要的魔力
     * @return 是否有足够魔力
     */
    public static boolean hasMana(ServerPlayer player, int amount) {
        return getManaData(player).hasEnergy(amount);
    }
    
    /**
     * 每tick更新魔力
     * 应该在玩家tick事件中调用
     * 
     * @param player 玩家
     */
    public static void tick(ServerPlayer player) {
        ManaData data = getManaData(player);
        data.regenerateEnergy();
        
        // TODO: 根据环境调整恢复速度
        // 例如: 在火山地带恢复更快
        // if (player.level().getBiome(player.blockPosition()).is(ModBiomes.VOLCANO)) {
        //     data.addEnergy(data.getEnergyRegenRate() / 10); // 额外恢复
        // }
    }
    
    /**
     * 魔力数据类
     * 实现 IEnergyUser 接口
     */
    public static class ManaData implements IEnergyUser {
        
        private int currentMana;
        private final int maxMana;
        private final int regenRate;
        
        public ManaData(int maxMana) {
            this.maxMana = maxMana;
            this.currentMana = maxMana;
            this.regenRate = Config.DRAGON_MANA_REGEN_RATE.get();
        }
        
        @Override
        public int getEnergy() {
            return currentMana;
        }
        
        @Override
        public int getMaxEnergy() {
            return maxMana;
        }
        
        @Override
        public void setEnergy(int energy) {
            this.currentMana = Math.max(0, Math.min(energy, maxMana));
        }
        
        @Override
        public int getEnergyRegenRate() {
            return regenRate;
        }
    }
}

