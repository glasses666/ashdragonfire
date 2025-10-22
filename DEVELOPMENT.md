# 开发指南

本文档为《灰烬与龙焰:永燃之战》模组的开发指南,包含架构说明、扩展方法和最佳实践。

## 架构概览

### 模块化设计

模组采用模块化架构,每个功能模块独立开发,通过接口和事件系统通信。

#### 核心模块

**1. 阵营系统 (faction/)**
- `Faction.java`: 阵营枚举定义
- `FactionManager.java`: 阵营管理器,处理玩家阵营数据
- `FactionData.java`: 玩家阵营数据类,包含声望、统计等

**2. 种族系统 (race/)**
- `RaceType.java`: 种族类型枚举
- `RaceAbility.java`: 种族能力接口
- 实现类负责具体种族特性

**3. 能量系统 (energy/)**
- `IEnergyUser.java`: 统一能量接口
- `ManaSystem.java`: 龙族魔力系统
- `TechEnergySystem.java`: 人类科技能量系统

**4. 技能系统 (ability/)**
- `AbilityRegistry.java`: 技能注册器
- `dragon/`: 龙族技能实现
- `human/`: 人类技能实现

**5. 事件系统 (event/)**
- `ModEvents.java`: 游戏事件监听
- `WarEventManager.java`: 战争事件管理

## 添加新内容

### 添加新物品

1. 在 `ModItems.java` 中注册物品:

```java
public static final RegistryObject<Item> NEW_ITEM = ITEMS.register("new_item",
    () -> new Item(new Item.Properties()));
```

2. 添加语言文件条目:

```json
"item.ashdragonfire.new_item": "新物品名称"
```

3. 创建材质文件 (可选):
   - `assets/ashdragonfire/textures/item/new_item.png`
   - `assets/ashdragonfire/models/item/new_item.json`

### 添加新方块

1. 在 `ModBlocks.java` 中注册方块:

```java
public static final RegistryObject<Block> NEW_BLOCK = registerBlock("new_block",
    () -> new Block(BlockBehaviour.Properties.of()
        .strength(3.0f, 3.0f)
        .requiresCorrectToolForDrops()
        .sound(SoundType.STONE)));
```

2. 方块会自动注册对应的 BlockItem

3. 添加语言文件、材质和模型

### 添加新技能

1. 创建技能类实现 `RaceAbility` 接口:

```java
public class NewAbility implements RaceAbility {
    @Override
    public String getAbilityId() {
        return "new_ability";
    }
    
    @Override
    public boolean activate(ServerPlayer player) {
        // 技能逻辑
        return true;
    }
    
    // 实现其他必需方法...
}
```

2. 在 `AbilityRegistry.register()` 中注册:

```java
registerAbility(new NewAbility());
```

3. 添加语言文件条目

### 添加新实体

1. 创建实体类继承适当的基类:

```java
public class NewEntity extends Monster {
    public NewEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }
    
    // 实现实体行为...
}
```

2. 在 `ModEntities.java` 中注册:

```java
public static final RegistryObject<EntityType<NewEntity>> NEW_ENTITY = 
    ENTITY_TYPES.register("new_entity", () -> EntityType.Builder.of(
        NewEntity::new, MobCategory.CREATURE)
        .sized(0.9f, 2.0f)
        .build("new_entity"));
```

3. 创建实体渲染器 (客户端)

## 数据持久化

### 当前实现

目前使用内存存储 (HashMap),数据在服务器重启后会丢失。

### 推荐实现

**方案1: 使用 Capability 系统**

```java
// 定义 Capability
public class FactionCapability {
    public static final Capability<IFactionData> FACTION_DATA = 
        CapabilityManager.get(new CapabilityToken<>(){});
}

// 附加到玩家
@SubscribeEvent
public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
    if (event.getObject() instanceof Player) {
        event.addCapability(
            new ResourceLocation(MOD_ID, "faction_data"),
            new FactionDataProvider()
        );
    }
}
```

**方案2: 使用 SavedData 系统**

```java
public class FactionSavedData extends SavedData {
    private Map<UUID, FactionData> playerData = new HashMap<>();
    
    public static FactionSavedData load(CompoundTag tag) {
        FactionSavedData data = new FactionSavedData();
        // 从 NBT 加载数据
        return data;
    }
    
    @Override
    public CompoundTag save(CompoundTag tag) {
        // 保存数据到 NBT
        return tag;
    }
}
```

## 网络通信

### 创建数据包

1. 创建数据包类:

```java
public class ExamplePacket {
    private final String data;
    
    public ExamplePacket(String data) {
        this.data = data;
    }
    
    public static void encode(ExamplePacket packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.data);
    }
    
    public static ExamplePacket decode(FriendlyByteBuf buf) {
        return new ExamplePacket(buf.readUtf());
    }
    
    public static void handle(ExamplePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // 处理数据包
        });
        ctx.get().setPacketHandled(true);
    }
}
```

2. 在 `PacketHandler.register()` 中注册:

```java
INSTANCE.messageBuilder(ExamplePacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
    .decoder(ExamplePacket::decode)
    .encoder(ExamplePacket::encode)
    .consumerMainThread(ExamplePacket::handle)
    .add();
```

## 配置系统

配置文件使用 Forge 的 `ForgeConfigSpec` 系统。

### 添加新配置项

在 `Config.java` 中添加:

```java
public static final ForgeConfigSpec.IntValue NEW_CONFIG_VALUE;

static {
    BUILDER.push("category");
    NEW_CONFIG_VALUE = BUILDER
        .comment("配置说明")
        .defineInRange("newConfigValue", 100, 1, 1000);
    BUILDER.pop();
}
```

配置文件会自动生成在 `config/ashdragonfire-common.toml`

## 事件处理

### 监听游戏事件

使用 `@SubscribeEvent` 注解:

```java
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide) {
            // 服务器端玩家tick逻辑
        }
    }
}
```

### 自定义事件

创建自定义事件类:

```java
public class FactionJoinEvent extends Event {
    private final Player player;
    private final Faction faction;
    
    public FactionJoinEvent(Player player, Faction faction) {
        this.player = player;
        this.faction = faction;
    }
    
    // Getters...
}
```

触发事件:

```java
MinecraftForge.EVENT_BUS.post(new FactionJoinEvent(player, faction));
```

## 客户端开发

### 渲染系统

实体渲染器示例:

```java
public class DragonGuardRenderer extends MobRenderer<DragonGuardEntity, DragonGuardModel> {
    
    public DragonGuardRenderer(EntityRendererProvider.Context context) {
        super(context, new DragonGuardModel(context.bakeLayer(MODEL_LAYER)), 0.5f);
    }
    
    @Override
    public ResourceLocation getTextureLocation(DragonGuardEntity entity) {
        return new ResourceLocation(MOD_ID, "textures/entity/dragon_guard.png");
    }
}
```

注册渲染器:

```java
@SubscribeEvent
public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
    event.registerEntityRenderer(ModEntities.DRAGON_GUARD.get(), DragonGuardRenderer::new);
}
```

### GUI 开发

创建自定义屏幕:

```java
public class FactionSelectionScreen extends Screen {
    
    public FactionSelectionScreen() {
        super(Component.literal("选择阵营"));
    }
    
    @Override
    protected void init() {
        super.init();
        // 添加按钮和组件
    }
    
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
    }
}
```

## 测试

### 单元测试

在 `src/test/java/` 中创建测试类:

```java
public class FactionTest {
    @Test
    public void testFactionHostility() {
        assertTrue(Faction.DRAGON.isHostile(Faction.HUMAN));
        assertFalse(Faction.DRAGON.isHostile(Faction.DRAGON));
    }
}
```

### 游戏内测试

1. 运行 `./gradlew runClient` 启动测试客户端
2. 使用 `/reload` 命令重载数据包
3. 使用调试日志查看问题

## 性能优化

### 最佳实践

1. **避免频繁的世界查询**: 缓存结果,使用事件而非轮询
2. **合理使用网络包**: 只同步必要的数据
3. **优化粒子效果**: 限制粒子数量,使用客户端预测
4. **异步操作**: 耗时操作使用异步任务

### 性能分析

使用 Spark 或 JProfiler 进行性能分析:

```bash
# 添加 JVM 参数
-XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints
```

## 调试技巧

### 日志记录

使用模组的日志记录器:

```java
AshDragonfireMod.LOGGER.info("信息日志");
AshDragonfireMod.LOGGER.warn("警告日志");
AshDragonfireMod.LOGGER.error("错误日志");
AshDragonfireMod.LOGGER.debug("调试日志");
```

### 远程调试

在 IntelliJ IDEA 中配置远程调试:

1. Run → Edit Configurations
2. 添加 Remote JVM Debug
3. 设置端口 (默认 5005)
4. 启动游戏时添加参数: `-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005`

## 发布流程

1. 更新版本号 (build.gradle)
2. 更新 CHANGELOG
3. 构建模组: `./gradlew build`
4. 测试生成的 jar 文件
5. 创建 GitHub Release
6. 上传到 CurseForge/Modrinth

## 常见问题

**Q: 为什么我的物品没有材质?**
A: 检查材质文件路径和模型 JSON 文件是否正确。

**Q: 如何调试网络包?**
A: 在数据包的 handle 方法中添加日志,检查是否正确发送和接收。

**Q: 实体不生成怎么办?**
A: 检查实体注册、生成规则和生物群系配置。

## 资源链接

- [Forge 文档](https://docs.minecraftforge.net/)
- [Minecraft Wiki](https://minecraft.fandom.com/)
- [Forge 社区论坛](https://forums.minecraftforge.net/)
- [模组开发教程](https://forge.gemwire.uk/wiki/Main_Page)

---

最后更新: 2025-01-01

