package com.glasserdraco.ashdragonfire;

import com.glasserdraco.ashdragonfire.ability.AbilityRegistry;
import com.glasserdraco.ashdragonfire.block.ModBlocks;
import com.glasserdraco.ashdragonfire.core.Config;
import com.glasserdraco.ashdragonfire.entity.ModEntities;
import com.glasserdraco.ashdragonfire.event.ModEvents;
import com.glasserdraco.ashdragonfire.faction.FactionManager;
import com.glasserdraco.ashdragonfire.item.ModItems;
import com.glasserdraco.ashdragonfire.network.PacketHandler;
import com.glasserdraco.ashdragonfire.world.ModDimensions;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * 《灰烬与龙焰:永燃之战》主类
 * 
 * 这是模组的入口点,负责:
 * 1. 初始化所有注册系统(物品、方块、实体等)
 * 2. 注册事件监听器
 * 3. 配置网络通信
 * 4. 加载配置文件
 * 
 * @author GlasserDraco
 * @version 1.0.0
 */
@Mod(AshDragonfireMod.MOD_ID)
public class AshDragonfireMod {
    
    /** 模组ID - 必须与 mods.toml 中的 modId 一致 */
    public static final String MOD_ID = "ashdragonfire";
    
    /** 日志记录器 */
    public static final Logger LOGGER = LogUtils.getLogger();
    
    /**
     * 模组构造函数
     * Forge 会在模组加载时自动调用此构造函数
     */
    public AshDragonfireMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        // ========== 注册所有 DeferredRegister ==========
        // 这些注册器会在适当的时机自动注册所有内容到游戏中
        
        LOGGER.info("正在注册物品...");
        ModItems.register(modEventBus);
        
        LOGGER.info("正在注册方块...");
        ModBlocks.register(modEventBus);
        
        LOGGER.info("正在注册实体...");
        ModEntities.register(modEventBus);
        
        LOGGER.info("正在注册维度...");
        ModDimensions.register(modEventBus);
        
        LOGGER.info("正在注册技能系统...");
        AbilityRegistry.register(modEventBus);
        
        // ========== 注册模组生命周期事件 ==========
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        
        // ========== 注册游戏事件监听器 ==========
        MinecraftForge.EVENT_BUS.register(ModEvents.class);
        MinecraftForge.EVENT_BUS.register(FactionManager.class);
        
        // ========== 加载配置文件 ==========
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        
        LOGGER.info("《灰烬与龙焰:永燃之战》模组加载完成!");
    }
    
    /**
     * 通用设置阶段
     * 在这里进行服务器和客户端都需要的初始化工作
     * 
     * @param event 通用设置事件
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            LOGGER.info("正在初始化网络通信系统...");
            PacketHandler.register();
            
            LOGGER.info("正在初始化阵营系统...");
            FactionManager.init();
            
            LOGGER.info("通用设置完成!");
        });
    }
    
    /**
     * 客户端设置阶段
     * 在这里进行仅客户端需要的初始化工作(如渲染、GUI等)
     * 
     * @param event 客户端设置事件
     */
    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            LOGGER.info("正在初始化客户端渲染...");
            // 这里可以注册实体渲染器、粒子效果等
            
            LOGGER.info("客户端设置完成!");
        });
    }
}

