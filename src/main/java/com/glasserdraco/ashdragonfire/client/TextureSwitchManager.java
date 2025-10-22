package com.glasserdraco.ashdragonfire.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态纹理切换管理器
 * 
 * 根据实体的转换类型动态切换纹理：
 * - bonded → bonded_dragon.png
 * - flame_sworn → flame_sworn.png
 * - mechanized → mechanized_drake.png
 * - ally → dragon_ally_human.png
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public class TextureSwitchManager {
    
    // 转换类型到纹理的映射
    private static final Map<String, ResourceLocation> CONVERSION_TEXTURES = new HashMap<>();
    
    static {
        // 龙族转换纹理
        CONVERSION_TEXTURES.put("bonded", 
            new ResourceLocation("ashdragonfire", "textures/entity/bonded_dragon.png"));
        CONVERSION_TEXTURES.put("mechanized", 
            new ResourceLocation("ashdragonfire", "textures/entity/mechanized_drake.png"));
        
        // 人类转换纹理
        CONVERSION_TEXTURES.put("flame_sworn", 
            new ResourceLocation("ashdragonfire", "textures/entity/flame_sworn.png"));
        CONVERSION_TEXTURES.put("ally", 
            new ResourceLocation("ashdragonfire", "textures/entity/dragon_ally_human.png"));
    }
    
    /**
     * 获取实体的纹理
     * 
     * @param entity 实体
     * @param defaultTexture 默认纹理
     * @return 纹理资源位置
     */
    public static ResourceLocation getEntityTexture(LivingEntity entity, ResourceLocation defaultTexture) {
        // 检查是否被转换
        if (!entity.getPersistentData().contains("ConversionType")) {
            return defaultTexture;
        }
        
        // 获取转换类型
        String conversionType = entity.getPersistentData().getString("ConversionType");
        
        // 返回对应的转换纹理，如果没有则返回默认纹理
        return CONVERSION_TEXTURES.getOrDefault(conversionType, defaultTexture);
    }
    
    /**
     * 注册自定义转换纹理
     * 
     * @param conversionType 转换类型
     * @param texture 纹理资源位置
     */
    public static void registerConversionTexture(String conversionType, ResourceLocation texture) {
        CONVERSION_TEXTURES.put(conversionType, texture);
    }
    
    /**
     * 检查实体是否需要切换纹理
     * 
     * @param entity 实体
     * @return 是否需要切换
     */
    public static boolean needsTextureSwitching(LivingEntity entity) {
        if (!entity.getPersistentData().contains("ConversionType")) {
            return false;
        }
        
        String conversionType = entity.getPersistentData().getString("ConversionType");
        return CONVERSION_TEXTURES.containsKey(conversionType);
    }
    
    /**
     * 获取所有已注册的转换纹理
     * 
     * @return 转换纹理映射
     */
    public static Map<String, ResourceLocation> getAllConversionTextures() {
        return new HashMap<>(CONVERSION_TEXTURES);
    }
}

