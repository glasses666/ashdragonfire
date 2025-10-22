package com.glasserdraco.ashdragonfire.world;

import com.glasserdraco.ashdragonfire.AshDragonfireMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * 维度注册类
 * 
 * 注册模组的自定义维度
 * 包括龙族领地和人类城市两个主要维度
 * 
 * 维度列表:
 * - 焰冠群岛 (Flamecrest Isles): 龙族维度,浮空岛+火山地形
 * - 齿轮之城 (Gearhold Frontier): 人类维度,钢铁废墟城市
 * 
 * 注意: 维度的详细配置需要在 data/ashdragonfire/dimension/ 中定义
 */
public class ModDimensions {
    
    /**
     * 焰冠群岛维度键
     * 龙族的主要活动区域
     */
    public static final ResourceKey<Level> FLAMECREST_ISLES = ResourceKey.create(
        Registries.DIMENSION,
        new ResourceLocation(AshDragonfireMod.MOD_ID, "flamecrest_isles")
    );
    
    /**
     * 焰冠群岛维度类型键
     */
    public static final ResourceKey<DimensionType> FLAMECREST_ISLES_TYPE = ResourceKey.create(
        Registries.DIMENSION_TYPE,
        new ResourceLocation(AshDragonfireMod.MOD_ID, "flamecrest_isles")
    );
    
    /**
     * 齿轮之城维度键
     * 人类的主要活动区域
     */
    public static final ResourceKey<Level> GEARHOLD_FRONTIER = ResourceKey.create(
        Registries.DIMENSION,
        new ResourceLocation(AshDragonfireMod.MOD_ID, "gearhold_frontier")
    );
    
    /**
     * 齿轮之城维度类型键
     */
    public static final ResourceKey<DimensionType> GEARHOLD_FRONTIER_TYPE = ResourceKey.create(
        Registries.DIMENSION_TYPE,
        new ResourceLocation(AshDragonfireMod.MOD_ID, "gearhold_frontier")
    );
    
    /**
     * 注册方法
     * 在主类中调用
     * 
     * 注意: Forge 1.21.1 中维度主要通过数据包(datapack)定义
     * 这里只是定义维度的ResourceKey,实际配置在 data/ashdragonfire/dimension/ 中
     * 
     * @param eventBus Forge 事件总线
     */
    public static void register(IEventBus eventBus) {
        AshDragonfireMod.LOGGER.info("维度键注册完成!");
        AshDragonfireMod.LOGGER.info("- 焰冠群岛: {}", FLAMECREST_ISLES.location());
        AshDragonfireMod.LOGGER.info("- 齿轮之城: {}", GEARHOLD_FRONTIER.location());
        AshDragonfireMod.LOGGER.info("请确保在 data/ashdragonfire/dimension/ 中配置维度数据!");
    }
}

