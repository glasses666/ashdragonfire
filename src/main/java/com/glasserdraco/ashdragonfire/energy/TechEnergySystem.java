package com.glasserdraco.ashdragonfire.energy;

import com.glasserdraco.ashdragonfire.core.Config;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 科技能量系统
 * 
 * 人类专用的能量系统
 * 管理所有人类玩家的科技能量
 * 
 * 能量特性:
 * - 用于驱动机械装备和科技武器
 * - 可以通过能量核心充能
 * - 兼容 Forge Energy (FE) 系统
 */
public class TechEnergySystem {
    
    /**
     * 玩家能量数据存储
     * Key: 玩家UUID
     * Value: 能量数据
     */
    private static final Map<UUID, EnergyData> playerEnergyData = new HashMap<>();
    
    /**
     * 获取玩家的能量数据
     * 
     * @param player 玩家
     * @return 能量数据
     */
    public static EnergyData getEnergyData(ServerPlayer player) {
        return playerEnergyData.computeIfAbsent(player.getUUID(), 
            uuid -> new EnergyData(Config.HUMAN_MAX_ENERGY.get()));
    }
    
    /**
     * 获取玩家当前能量值
     * 
     * @param player 玩家
     * @return 能量值
     */
    public static int getEnergy(ServerPlayer player) {
        return getEnergyData(player).getEnergy();
    }
    
    /**
     * 消耗玩家能量
     * 
     * @param player 玩家
     * @param amount 消耗量
     * @return 是否成功消耗
     */
    public static boolean consumeEnergy(ServerPlayer player, int amount) {
        return getEnergyData(player).consumeEnergy(amount);
    }
    
    /**
     * 充能
     * 
     * @param player 玩家
     * @param amount 充能量
     * @return 实际充能量
     */
    public static int chargeEnergy(ServerPlayer player, int amount) {
        return getEnergyData(player).addEnergy(amount);
    }
    
    /**
     * 检查玩家是否有足够能量
     * 
     * @param player 玩家
     * @param amount 需要的能量
     * @return 是否有足够能量
     */
    public static boolean hasEnergy(ServerPlayer player, int amount) {
        return getEnergyData(player).hasEnergy(amount);
    }
    
    /**
     * 每tick更新能量
     * 应该在玩家tick事件中调用
     * 
     * @param player 玩家
     */
    public static void tick(ServerPlayer player) {
        EnergyData data = getEnergyData(player);
        data.regenerateEnergy();
        
        // TODO: 检查玩家是否装备能量核心
        // 如果装备了能量核心,增加恢复速度
        // ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        // if (chestplate.getItem() instanceof EnergyArmorItem) {
        //     data.addEnergy(data.getEnergyRegenRate() / 5); // 额外恢复
        // }
    }
    
    /**
     * 能量数据类
     * 实现 IEnergyUser 接口
     */
    public static class EnergyData implements IEnergyUser {
        
        private int currentEnergy;
        private final int maxEnergy;
        private final int regenRate;
        
        public EnergyData(int maxEnergy) {
            this.maxEnergy = maxEnergy;
            this.currentEnergy = maxEnergy;
            this.regenRate = Config.HUMAN_ENERGY_REGEN_RATE.get();
        }
        
        @Override
        public int getEnergy() {
            return currentEnergy;
        }
        
        @Override
        public int getMaxEnergy() {
            return maxEnergy;
        }
        
        @Override
        public void setEnergy(int energy) {
            this.currentEnergy = Math.max(0, Math.min(energy, maxEnergy));
        }
        
        @Override
        public int getEnergyRegenRate() {
            return regenRate;
        }
        
        /**
         * 转换为 Forge Energy (FE)
         * 1 Tech Energy = 10 FE
         * 
         * @return FE值
         */
        public int toForgeEnergy() {
            return currentEnergy * 10;
        }
        
        /**
         * 从 Forge Energy (FE) 转换
         * 10 FE = 1 Tech Energy
         * 
         * @param fe FE值
         */
        public void fromForgeEnergy(int fe) {
            setEnergy(fe / 10);
        }
    }
}

