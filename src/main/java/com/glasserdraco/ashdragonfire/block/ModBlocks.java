package com.glasserdraco.ashdragonfire.block;

import com.glasserdraco.ashdragonfire.AshDragonfireMod;
import com.glasserdraco.ashdragonfire.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * 方块注册类
 * 
 * 使用 DeferredRegister 系统注册所有模组方块
 * 每个方块都会自动注册对应的 BlockItem
 * 
 * 方块分类:
 * - 龙族方块: 龙晶矿、熔岩祭坛、龙族圣殿方块
 * - 人类方块: 能量核心、机械工作台、钢铁建筑方块
 * - 共享方块: 遗迹石砖、古代符文方块
 */
public class ModBlocks {
    
    /**
     * 方块注册器
     */
    public static final DeferredRegister<Block> BLOCKS = 
        DeferredRegister.create(ForgeRegistries.BLOCKS, AshDragonfireMod.MOD_ID);
    
    // ========== 龙族方块 ==========
    
    /**
     * 龙晶矿石 - 龙族主要资源矿石
     * 在焰冠群岛维度中生成
     */
    public static final RegistryObject<Block> DRAGON_CRYSTAL_ORE_BLOCK = registerBlock("dragon_crystal_ore_block",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(3.0f, 3.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)));
    
    /**
     * 深层龙晶矿石 - 更稀有的龙晶矿石
     * 在地下深层生成
     */
    public static final RegistryObject<Block> DEEPSLATE_DRAGON_CRYSTAL_ORE = registerBlock("deepslate_dragon_crystal_ore",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(4.5f, 3.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.DEEPSLATE)));
    
    /**
     * 龙晶方块 - 龙晶锭合成的装饰方块
     * 可用于建筑和储存
     */
    public static final RegistryObject<Block> DRAGON_CRYSTAL_BLOCK = registerBlock("dragon_crystal_block",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(5.0f, 6.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.METAL)));
    
    /**
     * 熔岩祭坛 - 龙族功能方块
     * 用于龙语魔法仪式和装备强化
     */
    public static final RegistryObject<Block> LAVA_ALTAR = registerBlock("lava_altar",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(3.5f, 6.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .lightLevel(state -> 7)));
    
    // ========== 人类方块 ==========
    
    /**
     * 钢铁方块 - 人类建筑材料
     * 高强度防御方块
     */
    public static final RegistryObject<Block> STEEL_BLOCK = registerBlock("steel_block",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(6.0f, 8.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.METAL)));
    
    /**
     * 机械工作台 - 人类功能方块
     * 用于制作科技装备
     */
    public static final RegistryObject<Block> MECHANICAL_WORKBENCH = registerBlock("mechanical_workbench",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(3.0f, 5.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.ANVIL)));
    
    /**
     * 能量核心方块 - 人类能量存储
     * 可以储存和传输能量
     */
    public static final RegistryObject<Block> ENERGY_CORE_BLOCK = registerBlock("energy_core_block",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(4.0f, 6.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.GLASS)
            .lightLevel(state -> 10)));
    
    // ========== 共享方块 ==========
    
    /**
     * 遗迹石砖 - 远古遗迹建筑材料
     * 在中立遗迹结构中生成
     */
    public static final RegistryObject<Block> RUIN_STONE_BRICKS = registerBlock("ruin_stone_bricks",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(4.0f, 10.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)));
    
    /**
     * 古代符文方块 - 远古魔法方块
     * 包含神秘符文,可用于解锁特殊功能
     */
    public static final RegistryObject<Block> ANCIENT_RUNE_BLOCK = registerBlock("ancient_rune_block",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(5.0f, 12.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .lightLevel(state -> 5)));
    
    /**
     * 以太水晶方块 - 能量转换方块
     * 可以在魔法和科技能量之间转换
     */
    public static final RegistryObject<Block> AETHER_CRYSTAL_BLOCK = registerBlock("aether_crystal_block",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(3.0f, 5.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.AMETHYST)
            .lightLevel(state -> 12)));
    
    /**
     * 辅助方法: 注册方块及其对应的 BlockItem
     * 
     * @param name 方块注册名
     * @param block 方块供应器
     * @return 注册对象
     */
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    
    /**
     * 辅助方法: 为方块注册对应的物品
     * 
     * @param name 物品注册名
     * @param block 方块注册对象
     */
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    
    /**
     * 注册方法
     * 在主类中调用,将所有方块注册到事件总线
     * 
     * @param eventBus Forge 事件总线
     */
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        AshDragonfireMod.LOGGER.info("方块注册完成: {} 个方块已注册", BLOCKS.getEntries().size());
    }
}

