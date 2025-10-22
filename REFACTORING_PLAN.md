# 代码重构计划 | Refactoring Plan

## 📋 目标

将当前的线性包结构重构为模块化架构，提高代码可维护性和可扩展性。

---

## 🏗️ 新包结构设计

### 当前结构
```
com.glasserdraco.ashdragonfire/
├── AshDragonfireMod.java
├── ability/
├── ai/
├── block/
├── capability/
├── client/
├── conversion/
├── core/
├── energy/
├── entity/
├── event/
├── faction/
├── item/
├── network/
├── race/
└── world/
```

### 目标结构
```
com.glasserdraco.ashdragonfire/
├── AshDragonfireMod.java          # 主类
│
├── core/                           # 核心系统
│   ├── Config.java                 # 配置管理
│   ├── ModRegistry.java            # 统一注册入口
│   └── Constants.java              # 常量定义
│
├── common/                         # 通用逻辑（服务器+客户端）
│   ├── faction/                    # 阵营系统
│   │   ├── Faction.java
│   │   ├── FactionManager.java
│   │   └── FactionData.java
│   │
│   ├── race/                       # 种族系统
│   │   ├── RaceType.java
│   │   └── RaceAbility.java
│   │
│   ├── conversion/                 # 转换系统
│   │   ├── ConversionManager.java
│   │   └── ConversionType.java
│   │
│   ├── ability/                    # 技能系统
│   │   ├── AbilityRegistry.java
│   │   ├── dragon/
│   │   └── human/
│   │
│   ├── energy/                     # 能量系统
│   │   ├── IEnergyUser.java
│   │   ├── ManaSystem.java
│   │   └── TechEnergySystem.java
│   │
│   └── event/                      # 事件系统
│       ├── ModEvents.java
│       └── WarEventManager.java
│
├── content/                        # 游戏内容
│   ├── item/                       # 物品
│   │   ├── ModItems.java
│   │   ├── ConversionItems.java
│   │   ├── base/                   # 基础物品类
│   │   ├── dragon/                 # 龙族物品
│   │   └── human/                  # 人类物品
│   │
│   ├── block/                      # 方块
│   │   ├── ModBlocks.java
│   │   ├── base/
│   │   ├── dragon/
│   │   └── human/
│   │
│   └── entity/                     # 实体
│       ├── ModEntities.java
│       ├── ai/                     # AI系统
│       ├── dragon/
│       └── human/
│
├── systems/                        # 游戏系统
│   ├── capability/                 # Capability系统
│   │   ├── IConversionData.java
│   │   ├── ConversionData.java
│   │   └── ConversionDataProvider.java
│   │
│   ├── network/                    # 网络同步
│   │   ├── PacketHandler.java
│   │   └── packets/
│   │       └── ConversionSyncPacket.java
│   │
│   └── world/                      # 世界生成
│       ├── ModDimensions.java
│       └── structure/
│
├── client/                         # 客户端专用
│   ├── renderer/                   # 渲染器
│   │   ├── entity/
│   │   └── TextureSwitchManager.java
│   │
│   ├── gui/                        # GUI界面
│   │   ├── FactionSelectionScreen.java
│   │   └── SkillTreeScreen.java
│   │
│   └── particle/                   # 粒子效果
│       └── ModParticles.java
│
├── datagen/                        # 数据生成
│   ├── ModRecipeProvider.java
│   ├── ModLootTableProvider.java
│   └── ModLanguageProvider.java
│
└── api/                            # 公共API
    ├── IFactionProvider.java
    └── IConversionHandler.java
```

---

## 🔄 重构步骤

### Phase 1: 创建新包结构 ✅
- [x] 创建 `core/` 包
- [x] 创建 `common/` 包
- [x] 创建 `content/` 包
- [x] 创建 `systems/` 包
- [x] 创建 `client/` 包
- [x] 创建 `datagen/` 包

### Phase 2: 移动核心文件
- [ ] 移动 `Config.java` → `core/`
- [ ] 创建 `core/ModRegistry.java`
- [ ] 创建 `core/Constants.java`

### Phase 3: 重组通用逻辑
- [ ] 移动 `faction/` → `common/faction/`
- [ ] 移动 `race/` → `common/race/`
- [ ] 移动 `conversion/` → `common/conversion/`
- [ ] 移动 `ability/` → `common/ability/`
- [ ] 移动 `energy/` → `common/energy/`
- [ ] 移动 `event/` → `common/event/`

### Phase 4: 重组游戏内容
- [ ] 移动 `item/` → `content/item/`
- [ ] 移动 `block/` → `content/block/`
- [ ] 移动 `entity/` → `content/entity/`
- [ ] 移动 `ai/` → `content/entity/ai/`

### Phase 5: 重组系统模块
- [ ] 移动 `capability/` → `systems/capability/`
- [ ] 移动 `network/` → `systems/network/`
- [ ] 移动 `world/` → `systems/world/`

### Phase 6: 重组客户端
- [ ] 移动 `client/TextureSwitchManager.java` → `client/renderer/`
- [ ] 创建 `client/gui/` 包
- [ ] 创建 `client/particle/` 包

### Phase 7: 更新导入和引用
- [ ] 更新所有文件的 import 语句
- [ ] 更新 `AshDragonfireMod.java` 中的引用
- [ ] 更新事件注册

### Phase 8: 测试和验证
- [ ] 编译测试
- [ ] 运行客户端测试
- [ ] 运行服务器测试
- [ ] 功能测试

---

## 📝 重构规范

### 命名规范

#### 包名
- 全小写
- 使用复数形式（如 `items`, `blocks`, `entities`）
- 避免缩写

#### 类名
- 使用 PascalCase
- 清晰描述类的用途
- 避免过长的名称

#### 方法名
- 使用 camelCase
- 动词开头（如 `convertEntity`, `registerItems`）
- 布尔方法使用 `is`, `has`, `can` 前缀

### 注册规范

#### 使用 DeferredRegister

```java
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, AshDragonfireMod.MOD_ID);
    
    public static final RegistryObject<Item> DRAGON_CRYSTAL_STAFF = 
        ITEMS.register("dragon_crystal_staff", () -> new DragonCrystalStaffItem(...));
}
```

#### 事件注册

```java
@Mod.EventBusSubscriber(modid = AshDragonfireMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {
    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        // 初始化代码
    }
}
```

---

## 🎯 预期收益

### 代码组织
- ✅ 清晰的模块划分
- ✅ 更好的代码可读性
- ✅ 更容易定位文件

### 开发效率
- ✅ 减少命名冲突
- ✅ 更容易添加新功能
- ✅ 更容易协作开发

### 性能优化
- ✅ 客户端/服务器逻辑分离
- ✅ 更好的懒加载支持
- ✅ 减少不必要的类加载

### 可维护性
- ✅ 更容易重构单个模块
- ✅ 更容易编写测试
- ✅ 更容易文档化

---

## ⚠️ 注意事项

### 向后兼容
- 保持公共 API 不变
- 使用 `@Deprecated` 标记旧类
- 提供迁移指南

### 测试覆盖
- 每个重构步骤后运行测试
- 确保功能正常工作
- 检查多人游戏兼容性

### 文档更新
- 更新 JavaDoc
- 更新 README
- 更新开发指南

---

## 📅 时间表

| 阶段 | 预计时间 | 状态 |
|------|---------|------|
| Phase 1 | 1天 | ✅ 完成 |
| Phase 2-3 | 2天 | 🟡 进行中 |
| Phase 4-5 | 2天 | ⏳ 待开始 |
| Phase 6-7 | 2天 | ⏳ 待开始 |
| Phase 8 | 1天 | ⏳ 待开始 |
| **总计** | **8天** | |

---

## 🔗 相关文档

- [Forge 最佳实践](https://docs.minecraftforge.net/en/latest/gettingstarted/)
- [Java 包命名规范](https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html)
- [模组开发指南](DEVELOPMENT.md)

---

**更新日期**: 2025-01-01  
**负责人**: GlasserDraco  
**状态**: 🟡 进行中

