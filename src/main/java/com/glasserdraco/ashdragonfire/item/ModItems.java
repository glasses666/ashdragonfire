package com.glasserdraco.ashdragonfire.item;

import com.glasserdraco.ashdragonfire.AshDragonfireMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 物品注册类
 * 
 * 使用 DeferredRegister 系统注册所有模组物品
 * 这是 Forge 1.21.1 推荐的注册方式,线程安全且高效
 * 
 * 物品分类:
 * - 龙族装备: 龙晶杖、鳞甲、龙焰心核
 * - 人类装备: 火炮、机械臂、能量步枪
 * - 共享材料: 古代符石、以太核心、灰烬之血
 */
public class ModItems {
    
    /**
     * 物品注册器
     * 所有物品都通过这个注册器注册到游戏中
     */
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, AshDragonfireMod.MOD_ID);
    
    // ========== 龙族装备 ==========
    
    /**
     * 龙晶杖 - 龙族基础魔法武器
     * 可以释放龙语魔法,消耗魔力值
     */
    public static final RegistryObject<Item> DRAGON_CRYSTAL_STAFF = ITEMS.register("dragon_crystal_staff",
        () -> new Item(new Item.Properties()));
    
    /**
     * 龙鳞甲 - 龙族防具材料
     * 提供高额魔法抗性和物理防御
     */
    public static final RegistryObject<Item> DRAGON_SCALE_ARMOR = ITEMS.register("dragon_scale_armor",
        () -> new Item(new Item.Properties()));
    
    /**
     * 龙焰心核 - 龙族核心能量源
     * 用于强化龙族装备和技能
     */
    public static final RegistryObject<Item> DRAGONFIRE_CORE = ITEMS.register("dragonfire_core",
        () -> new Item(new Item.Properties()));
    
    // ========== 人类装备 ==========
    
    /**
     * 能量步枪 - 人类远程武器
     * 消耗能量核心发射高能射线
     */
    public static final RegistryObject<Item> ENERGY_RIFLE = ITEMS.register("energy_rifle",
        () -> new Item(new Item.Properties()));
    
    /**
     * 机械臂 - 人类近战武器
     * 提供额外力量和挖掘速度
     */
    public static final RegistryObject<Item> MECHANICAL_ARM = ITEMS.register("mechanical_arm",
        () -> new Item(new Item.Properties()));
    
    /**
     * 火炮弹药 - 人类重型武器弹药
     * 用于火炮系统
     */
    public static final RegistryObject<Item> CANNON_AMMO = ITEMS.register("cannon_ammo",
        () -> new Item(new Item.Properties()));
    
    // ========== 共享材料 ==========
    
    /**
     * 古代符石 - 远古文明遗物
     * 可用于制作高级装备,两个阵营通用
     */
    public static final RegistryObject<Item> ANCIENT_RUNE_STONE = ITEMS.register("ancient_rune_stone",
        () -> new Item(new Item.Properties()));
    
    /**
     * 以太核心 - 纯净能量结晶
     * 魔法与科技能量的转换媒介
     */
    public static final RegistryObject<Item> AETHER_CORE = ITEMS.register("aether_core",
        () -> new Item(new Item.Properties()));
    
    /**
     * 灰烬之血 - 战争残留物质
     * 稀有材料,用于制作传说级装备
     */
    public static final RegistryObject<Item> ASH_BLOOD = ITEMS.register("ash_blood",
        () -> new Item(new Item.Properties()));
    
    /**
     * 龙晶矿石 - 龙族资源
     * 在焰冠群岛中生成
     */
    public static final RegistryObject<Item> DRAGON_CRYSTAL_ORE = ITEMS.register("dragon_crystal_ore",
        () -> new Item(new Item.Properties()));
    
    /**
     * 龙晶锭 - 龙晶矿石冶炼产物
     * 用于制作龙族装备
     */
    public static final RegistryObject<Item> DRAGON_CRYSTAL_INGOT = ITEMS.register("dragon_crystal_ingot",
        () -> new Item(new Item.Properties()));
    
    /**
     * 钢铁合金 - 人类工业材料
     * 用于制作机械装备
     */
    public static final RegistryObject<Item> STEEL_ALLOY = ITEMS.register("steel_alloy",
        () -> new Item(new Item.Properties()));
    
    /**
     * 注册方法
     * 在主类中调用,将所有物品注册到事件总线
     * 
     * @param eventBus Forge 事件总线
     */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        AshDragonfireMod.LOGGER.info("物品注册完成: {} 个物品已注册", ITEMS.getEntries().size());
    }
}

