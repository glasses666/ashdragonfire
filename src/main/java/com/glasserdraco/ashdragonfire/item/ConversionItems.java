package com.glasserdraco.ashdragonfire.item;

import com.glasserdraco.ashdragonfire.AshDragonfireMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 阵营转换物品注册类
 * 
 * 注册所有用于阵营转换的物品：
 * - 龙魂引（驯服龙族）
 * - 灰烬之血药剂（净化人类）
 * - 神经支配者（支配龙族）
 * - 龙语卷轴（说服人类）
 * - 相关制作材料
 * 
 * @author GlasserDraco
 * @version 1.0
 */
public class ConversionItems {
    
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, AshDragonfireMod.MOD_ID);
    
    // ==================== 核心转换物品 ====================
    
    /**
     * 龙魂引 - 用于驯服龙族
     * 稀有度：史诗（紫色）
     * 成功率：50-90%（取决于声望）
     */
    public static final RegistryObject<Item> DRAGON_SOUL_LURE = ITEMS.register("dragon_soul_lure",
        () -> new DragonSoulLureItem(new Item.Properties()
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .durability(3)
        )
    );
    
    /**
     * 灰烬之血药剂 - 用于净化人类士兵
     * 稀有度：稀有（青色）
     * 成功率：100%
     */
    public static final RegistryObject<Item> ASH_BLOOD_POTION = ITEMS.register("ash_blood_potion",
        () -> new AshBloodPotionItem(new Item.Properties()
            .stacksTo(16)
            .rarity(Rarity.RARE)
        )
    );
    
    /**
     * 神经支配者 - 用于强制支配龙族
     * 稀有度：史诗（紫色）
     * 成功率：100%
     * 耐久度消耗取决于声望
     */
    public static final RegistryObject<Item> NEURAL_DOMINATOR = ITEMS.register("neural_dominator",
        () -> new NeuralDominatorItem(new Item.Properties()
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .durability(10)
        )
    );
    
    /**
     * 龙语卷轴 - 用于说服人类士兵
     * 稀有度：普通（白色）
     * 成功率：40-80%（取决于声望）
     */
    public static final RegistryObject<Item> DRAGON_TONGUE_SCROLL = ITEMS.register("dragon_tongue_scroll",
        () -> new DragonTongueScrollItem(new Item.Properties()
            .stacksTo(16)
            .rarity(Rarity.COMMON)
        )
    );
    
    // ==================== 制作材料 ====================
    
    /**
     * 征服弹 - 神经支配者的弹药
     */
    public static final RegistryObject<Item> SUBJUGATION_BOLT = ITEMS.register("subjugation_bolt",
        () -> new Item(new Item.Properties()
            .stacksTo(64)
            .rarity(Rarity.UNCOMMON)
        )
    );
    
    /**
     * 龙晶粉末 - 由龙晶锭粉碎获得
     */
    public static final RegistryObject<Item> DRAGON_CRYSTAL_DUST = ITEMS.register("dragon_crystal_dust",
        () -> new Item(new Item.Properties()
            .stacksTo(64)
            .rarity(Rarity.COMMON)
        )
    );
    
    /**
     * 灵魂碎片 - 击败敌对实体掉落
     */
    public static final RegistryObject<Item> SOUL_FRAGMENT = ITEMS.register("soul_fragment",
        () -> new Item(new Item.Properties()
            .stacksTo(64)
            .rarity(Rarity.UNCOMMON)
        )
    );
    
    /**
     * 控制芯片 - 高级科技组件
     */
    public static final RegistryObject<Item> CONTROL_CHIP = ITEMS.register("control_chip",
        () -> new Item(new Item.Properties()
            .stacksTo(16)
            .rarity(Rarity.RARE)
        )
    );
    
    /**
     * 注册所有物品到游戏
     */
    public static void register() {
        AshDragonfireMod.LOGGER.info("Registering Conversion Items for " + AshDragonfireMod.MOD_ID);
    }
}

