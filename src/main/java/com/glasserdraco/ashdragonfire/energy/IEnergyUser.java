package com.glasserdraco.ashdragonfire.energy;

/**
 * 能量使用者接口
 * 
 * 定义统一的能量系统接口
 * 支持魔力(Mana)和科技能量(Tech Energy)两种能量类型
 * 
 * 实现类:
 * - 玩家能量系统
 * - 机械装备能量系统
 * - 魔法物品能量系统
 */
public interface IEnergyUser {
    
    /**
     * 获取当前能量值
     * 
     * @return 当前能量
     */
    int getEnergy();
    
    /**
     * 获取最大能量值
     * 
     * @return 最大能量
     */
    int getMaxEnergy();
    
    /**
     * 设置当前能量值
     * 
     * @param energy 新的能量值
     */
    void setEnergy(int energy);
    
    /**
     * 增加能量
     * 
     * @param amount 增加的能量值
     * @return 实际增加的能量值
     */
    default int addEnergy(int amount) {
        int current = getEnergy();
        int max = getMaxEnergy();
        int newEnergy = Math.min(current + amount, max);
        setEnergy(newEnergy);
        return newEnergy - current;
    }
    
    /**
     * 消耗能量
     * 
     * @param amount 消耗的能量值
     * @return 是否成功消耗
     */
    default boolean consumeEnergy(int amount) {
        int current = getEnergy();
        if (current >= amount) {
            setEnergy(current - amount);
            return true;
        }
        return false;
    }
    
    /**
     * 检查是否有足够的能量
     * 
     * @param amount 需要的能量值
     * @return 是否有足够能量
     */
    default boolean hasEnergy(int amount) {
        return getEnergy() >= amount;
    }
    
    /**
     * 获取能量百分比
     * 
     * @return 能量百分比 (0.0 - 1.0)
     */
    default float getEnergyPercentage() {
        return (float) getEnergy() / getMaxEnergy();
    }
    
    /**
     * 能量是否已满
     * 
     * @return 是否已满
     */
    default boolean isEnergyFull() {
        return getEnergy() >= getMaxEnergy();
    }
    
    /**
     * 能量是否已空
     * 
     * @return 是否已空
     */
    default boolean isEnergyEmpty() {
        return getEnergy() <= 0;
    }
    
    /**
     * 获取能量恢复速度(每秒)
     * 
     * @return 恢复速度
     */
    int getEnergyRegenRate();
    
    /**
     * 恢复能量
     * 通常每tick调用一次
     */
    default void regenerateEnergy() {
        if (!isEnergyFull()) {
            // 每秒恢复,1秒=20tick
            int regenAmount = getEnergyRegenRate() / 20;
            addEnergy(regenAmount);
        }
    }
}

