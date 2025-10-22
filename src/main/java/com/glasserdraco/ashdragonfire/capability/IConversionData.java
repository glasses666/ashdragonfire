package com.glasserdraco.ashdragonfire.capability;

import java.util.UUID;

/**
 * 转换数据Capability接口
 * 
 * 用于存储和管理实体的转换信息：
 * - 转换类型
 * - 主人UUID
 * - 转换时间
 * - 原始阵营
 * - 当前阵营
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public interface IConversionData {
    
    /**
     * 获取转换类型
     * @return 转换类型（bonded, flame_sworn, mechanized, ally）
     */
    String getConversionType();
    
    /**
     * 设置转换类型
     * @param type 转换类型
     */
    void setConversionType(String type);
    
    /**
     * 获取主人UUID
     * @return 主人UUID，如果没有主人则返回null
     */
    UUID getOwnerUUID();
    
    /**
     * 设置主人UUID
     * @param uuid 主人UUID
     */
    void setOwnerUUID(UUID uuid);
    
    /**
     * 获取转换时间戳
     * @return 转换时间（毫秒）
     */
    long getConversionTime();
    
    /**
     * 设置转换时间戳
     * @param time 转换时间（毫秒）
     */
    void setConversionTime(long time);
    
    /**
     * 获取原始阵营
     * @return 原始阵营名称
     */
    String getOriginalFaction();
    
    /**
     * 设置原始阵营
     * @param faction 原始阵营名称
     */
    void setOriginalFaction(String faction);
    
    /**
     * 获取当前阵营
     * @return 当前阵营名称
     */
    String getCurrentFaction();
    
    /**
     * 设置当前阵营
     * @param faction 当前阵营名称
     */
    void setCurrentFaction(String faction);
    
    /**
     * 检查是否被转换
     * @return 是否被转换
     */
    boolean isConverted();
    
    /**
     * 检查是否有主人
     * @return 是否有主人
     */
    boolean hasOwner();
    
    /**
     * 清除所有转换数据
     */
    void clear();
    
    /**
     * 复制数据到另一个实例
     * @param other 目标实例
     */
    void copyTo(IConversionData other);
}

