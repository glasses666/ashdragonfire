# 快速开始指南

欢迎使用《灰烬与龙焰:永燃之战》模组开发框架!本指南将帮助你快速上手。

## 📋 前置要求

在开始之前,请确保已安装:

- **JDK 21** ([下载地址](https://adoptium.net/))
- **IntelliJ IDEA** 或 **Eclipse** ([IDEA 下载](https://www.jetbrains.com/idea/download/))
- **Git** (可选,用于版本控制)

## 🚀 5分钟快速启动

### 步骤 1: 导入项目

**使用 IntelliJ IDEA:**

1. 打开 IntelliJ IDEA
2. 选择 `File` → `Open`
3. 选择 `ashdragonfire` 文件夹
4. 等待 Gradle 同步完成 (首次可能需要几分钟)

**使用 Eclipse:**

1. 打开 Eclipse
2. 选择 `File` → `Import` → `Existing Gradle Project`
3. 选择 `ashdragonfire` 文件夹
4. 完成导入

### 步骤 2: 生成运行配置

在项目根目录打开终端,运行:

```bash
# Windows
gradlew.bat genIntellijRuns

# Linux/Mac
./gradlew genIntellijRuns
```

### 步骤 3: 运行游戏

**在 IDE 中:**
- IntelliJ IDEA: 右上角选择 `runClient`,点击运行按钮
- Eclipse: 在 Run Configurations 中选择 `runClient`

**使用命令行:**

```bash
# 运行客户端
./gradlew runClient

# 运行服务器
./gradlew runServer
```

### 步骤 4: 测试模组

1. 游戏启动后,创建新世界
2. 进入游戏后,你应该会看到阵营选择提示
3. 按 `E` 打开物品栏,搜索 "dragon" 或 "ash" 查看模组物品

## 📝 开发你的第一个功能

### 示例: 添加一个新物品

1. 打开 `src/main/java/com/glasserdraco/ashdragonfire/item/ModItems.java`

2. 添加新物品注册:

```java
public static final RegistryObject<Item> MY_ITEM = ITEMS.register("my_item",
    () -> new Item(new Item.Properties()));
```

3. 在语言文件中添加名称:

打开 `src/main/resources/assets/ashdragonfire/lang/zh_cn.json`,添加:

```json
"item.ashdragonfire.my_item": "我的物品"
```

4. 重新运行游戏,使用 `/give @s ashdragonfire:my_item` 获取物品

## 🎨 添加材质 (可选)

1. 创建 16x16 像素的 PNG 图片
2. 保存为 `src/main/resources/assets/ashdragonfire/textures/item/my_item.png`
3. 创建模型文件 `src/main/resources/assets/ashdragonfire/models/item/my_item.json`:

```json
{
  "parent": "item/generated",
  "textures": {
    "layer0": "ashdragonfire:item/my_item"
  }
}
```

## 🔧 常用命令

```bash
# 清理构建
./gradlew clean

# 构建模组 jar
./gradlew build

# 重新加载数据包 (游戏内)
/reload

# 给予物品
/give @s ashdragonfire:dragon_crystal_staff

# 切换游戏模式
/gamemode creative
```

## 📚 项目结构速览

```
ashdragonfire/
├── src/main/java/com/glasserdraco/ashdragonfire/
│   ├── AshDragonfireMod.java          # 主类,从这里开始
│   ├── item/ModItems.java             # 物品注册
│   ├── block/ModBlocks.java           # 方块注册
│   ├── faction/FactionManager.java    # 阵营系统
│   ├── ability/AbilityRegistry.java   # 技能系统
│   └── ...
├── src/main/resources/
│   ├── assets/ashdragonfire/          # 客户端资源
│   │   ├── lang/                      # 语言文件
│   │   ├── textures/                  # 材质
│   │   └── models/                    # 模型
│   └── data/ashdragonfire/            # 服务器数据
│       ├── recipes/                   # 配方
│       └── loot_tables/               # 战利品表
└── build.gradle                       # Gradle 配置
```

## 🐛 常见问题

### Q: Gradle 同步失败

**A:** 检查网络连接,或尝试使用国内镜像:

在 `build.gradle` 的 `repositories` 中添加:

```gradle
maven { url 'https://maven.aliyun.com/repository/public' }
```

### Q: 游戏启动失败

**A:** 确保:
1. JDK 版本为 21
2. Gradle 同步已完成
3. 运行配置已生成 (`genIntellijRuns`)

### Q: 找不到模组物品

**A:** 
1. 检查物品是否正确注册
2. 使用 `/reload` 重新加载
3. 查看日志是否有错误信息

### Q: 修改代码后没有生效

**A:**
1. 停止游戏
2. 重新构建项目 (`Build` → `Rebuild Project`)
3. 重新运行游戏

## 📖 下一步学习

1. **阅读文档**:
   - `README.md` - 模组介绍
   - `DEVELOPMENT.md` - 详细开发指南
   - `PROJECT_OVERVIEW.md` - 项目结构说明

2. **学习示例**:
   - 查看 `FireBreathAbility.java` 学习技能实现
   - 查看 `FactionManager.java` 学习数据管理
   - 查看 `ModEvents.java` 学习事件处理

3. **参考资源**:
   - [Forge 官方文档](https://docs.minecraftforge.net/)
   - [Minecraft Wiki](https://minecraft.fandom.com/)
   - [模组开发教程](https://forge.gemwire.uk/wiki/Main_Page)

## 💡 开发建议

### 推荐开发顺序

1. **熟悉现有代码** (1-2天)
   - 阅读主类和核心系统
   - 运行游戏,测试现有功能
   - 理解注册系统

2. **实现简单功能** (3-5天)
   - 添加新物品和方块
   - 创建简单配方
   - 添加材质和模型

3. **实现核心功能** (1-2周)
   - 完成数据持久化
   - 实现网络同步
   - 创建 GUI 界面

4. **完善游戏内容** (2-3周)
   - 实现实体和 AI
   - 配置世界生成
   - 添加更多技能

5. **优化和测试** (1周)
   - 性能优化
   - Bug 修复
   - 多人测试

## 🤝 获取帮助

遇到问题?

1. **查看日志**: `logs/latest.log` 或 `logs/debug.log`
2. **搜索错误**: 复制错误信息到 Google 搜索
3. **查阅文档**: 阅读 `DEVELOPMENT.md` 中的详细说明
4. **提问**: 在 GitHub Issues 中提问

## 🎉 开始创作!

现在你已经准备好开始开发了!记住:

- **小步快跑**: 每次只实现一个小功能
- **频繁测试**: 每次修改后都运行游戏测试
- **阅读代码**: 现有代码是最好的学习材料
- **保持耐心**: 模组开发需要时间和实践

祝你开发愉快! 🚀

---

有问题?查看 [DEVELOPMENT.md](DEVELOPMENT.md) 获取更详细的指南。

