package com.glasserdraco.ashdragonfire.entity;

import com.glasserdraco.ashdragonfire.AshDragonfireMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 实体注册类
 * 
 * 使用 DeferredRegister 系统注册所有模组实体
 * 包括生物、载具、投射物等
 * 
 * 实体分类:
 * - 龙族生物: 龙族守卫、火焰龙、风暴龙
 * - 人类单位: 机械兵、飞艇、火炮
 * - Boss实体: 远古巨龙、机械神
 * - 投射物: 龙焰球、能量射线
 * 
 * 注意: 实体类需要单独创建,这里只是注册实体类型
 * 实际的实体行为需要在对应的实体类中实现
 */
public class ModEntities {
    
    /**
     * 实体类型注册器
     */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = 
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AshDragonfireMod.MOD_ID);
    
    // ========== 龙族生物 ==========
    
    /**
     * 龙族守卫 - 龙族基础战斗单位
     * 在龙族领地中生成,保护龙族圣殿
     * 
     * TODO: 创建对应的实体类 DragonGuardEntity
     */
    // public static final RegistryObject<EntityType<DragonGuardEntity>> DRAGON_GUARD = 
    //     ENTITY_TYPES.register("dragon_guard", () -> EntityType.Builder.of(
    //         DragonGuardEntity::new, MobCategory.CREATURE)
    //         .sized(0.9f, 2.0f)
    //         .build("dragon_guard"));
    
    /**
     * 火焰龙 - 龙族飞行单位
     * 可以飞行并喷射火焰
     * 
     * TODO: 创建对应的实体类 FireDragonEntity
     */
    // public static final RegistryObject<EntityType<FireDragonEntity>> FIRE_DRAGON = 
    //     ENTITY_TYPES.register("fire_dragon", () -> EntityType.Builder.of(
    //         FireDragonEntity::new, MobCategory.CREATURE)
    //         .sized(2.5f, 2.5f)
    //         .build("fire_dragon"));
    
    // ========== 人类单位 ==========
    
    /**
     * 机械兵 - 人类基础战斗单位
     * 装备能量武器,在人类领地中巡逻
     * 
     * TODO: 创建对应的实体类 MechSoldierEntity
     */
    // public static final RegistryObject<EntityType<MechSoldierEntity>> MECH_SOLDIER = 
    //     ENTITY_TYPES.register("mech_soldier", () -> EntityType.Builder.of(
    //         MechSoldierEntity::new, MobCategory.CREATURE)
    //         .sized(0.8f, 2.2f)
    //         .build("mech_soldier"));
    
    /**
     * 飞艇 - 人类载具实体
     * 可以搭载玩家和货物
     * 
     * TODO: 创建对应的实体类 AirshipEntity
     */
    // public static final RegistryObject<EntityType<AirshipEntity>> AIRSHIP = 
    //     ENTITY_TYPES.register("airship", () -> EntityType.Builder.of(
    //         AirshipEntity::new, MobCategory.MISC)
    //         .sized(5.0f, 3.0f)
    //         .build("airship"));
    
    // ========== Boss实体 ==========
    
    /**
     * 远古巨龙 - 龙族世界Boss
     * 强大的龙族领袖,拥有多种元素攻击
     * 
     * TODO: 创建对应的实体类 AncientDragonEntity
     */
    // public static final RegistryObject<EntityType<AncientDragonEntity>> ANCIENT_DRAGON = 
    //     ENTITY_TYPES.register("ancient_dragon", () -> EntityType.Builder.of(
    //         AncientDragonEntity::new, MobCategory.MONSTER)
    //         .sized(5.0f, 5.0f)
    //         .fireImmune()
    //         .build("ancient_dragon"));
    
    /**
     * 机械神 - 人类世界Boss
     * 巨型机械战争机器,装备多种重型武器
     * 
     * TODO: 创建对应的实体类 MechanicalGodEntity
     */
    // public static final RegistryObject<EntityType<MechanicalGodEntity>> MECHANICAL_GOD = 
    //     ENTITY_TYPES.register("mechanical_god", () -> EntityType.Builder.of(
    //         MechanicalGodEntity::new, MobCategory.MONSTER)
    //         .sized(4.0f, 6.0f)
    //         .fireImmune()
    //         .build("mechanical_god"));
    
    // ========== 投射物 ==========
    
    /**
     * 龙焰球 - 龙族魔法投射物
     * 火焰吐息技能的投射物
     * 
     * TODO: 创建对应的实体类 DragonfireballEntity
     */
    // public static final RegistryObject<EntityType<DragonfireballEntity>> DRAGONFIRE_BALL = 
    //     ENTITY_TYPES.register("dragonfire_ball", () -> EntityType.Builder.<DragonfireballEntity>of(
    //         DragonfireballEntity::new, MobCategory.MISC)
    //         .sized(0.5f, 0.5f)
    //         .build("dragonfire_ball"));
    
    /**
     * 能量射线 - 人类科技投射物
     * 能量步枪的射线攻击
     * 
     * TODO: 创建对应的实体类 EnergyBeamEntity
     */
    // public static final RegistryObject<EntityType<EnergyBeamEntity>> ENERGY_BEAM = 
    //     ENTITY_TYPES.register("energy_beam", () -> EntityType.Builder.<EnergyBeamEntity>of(
    //         EnergyBeamEntity::new, MobCategory.MISC)
    //         .sized(0.3f, 0.3f)
    //         .build("energy_beam"));
    
    /**
     * 注册方法
     * 在主类中调用,将所有实体类型注册到事件总线
     * 
     * @param eventBus Forge 事件总线
     */
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
        AshDragonfireMod.LOGGER.info("实体类型注册完成: {} 个实体类型已注册", ENTITY_TYPES.getEntries().size());
    }
}

