# 构建和测试指南

本文档提供《灰烬与龙焰:永燃之战》模组的完整构建、编译和测试流程。

## 📋 前置要求

### 必需软件
- **JDK 21** - [下载地址](https://adoptium.net/temurin/releases/)
- **Git** - [下载地址](https://git-scm.com/downloads)
- **IntelliJ IDEA** (推荐) 或 **Eclipse** - [下载地址](https://www.jetbrains.com/idea/download/)

### 系统要求
- **操作系统**: Windows 10/11, macOS 10.14+, Linux
- **内存**: 至少 4GB RAM (推荐 8GB+)
- **磁盘空间**: 至少 5GB 可用空间
- **网络**: 稳定的互联网连接（首次构建需要下载约 500MB 依赖）

## 🚀 快速开始（5分钟）

### 步骤 1: 克隆项目

```bash
# 克隆 GitHub 仓库
git clone https://github.com/glasses666/ashdragonfire.git
cd ashdragonfire
```

### 步骤 2: 验证 Java 版本

```bash
# 检查 Java 版本（必须是 21）
java -version

# 应该显示类似：
# openjdk version "21.0.x"
```

如果版本不对，请安装 JDK 21 并设置 `JAVA_HOME` 环境变量。

### 步骤 3: 首次构建（需要 10-30 分钟）

```bash
# Windows
gradlew.bat build

# Linux/macOS
./gradlew build
```

**注意**: 首次构建会下载 Minecraft、Forge 和所有依赖，需要较长时间。请耐心等待。

### 步骤 4: 运行测试客户端

```bash
# Windows
gradlew.bat runClient

# Linux/macOS
./gradlew runClient
```

游戏启动后，你应该能看到模组加载信息。

## 🔧 详细构建流程

### 方案 A: 使用 IntelliJ IDEA（推荐）

#### 1. 导入项目

1. 打开 IntelliJ IDEA
2. 选择 `File` → `Open`
3. 选择 `ashdragonfire` 文件夹
4. 点击 `OK`

#### 2. 等待 Gradle 同步

IDEA 会自动检测到这是一个 Gradle 项目并开始同步。

- 查看右下角的进度条
- 首次同步需要 10-30 分钟
- 同步完成后会显示 "Gradle sync finished"

#### 3. 生成运行配置

打开终端（`View` → `Tool Windows` → `Terminal`），运行：

```bash
# Windows
gradlew.bat genIntellijRuns

# Linux/macOS
./gradlew genIntellijRuns
```

#### 4. 刷新 Gradle 项目

右键点击项目根目录 → `Gradle` → `Reload Gradle Project`

#### 5. 运行游戏

在右上角的运行配置下拉菜单中选择 `runClient`，然后点击绿色运行按钮。

### 方案 B: 使用命令行

#### 1. 构建项目

```bash
# 清理之前的构建
./gradlew clean

# 构建项目
./gradlew build
```

#### 2. 运行客户端

```bash
./gradlew runClient
```

#### 3. 运行服务器

```bash
./gradlew runServer
```

## 🧪 测试清单

### 基础测试

启动游戏后，按以下步骤测试：

#### 1. 模组加载测试

- [ ] 游戏启动成功
- [ ] 在主菜单点击 `Mods` 按钮
- [ ] 找到 "Ash & Dragonfire" 模组
- [ ] 版本显示为 `1.0.0`
- [ ] 模组ID显示为 `ashdragonfire`

#### 2. 创建测试世界

- [ ] 创建新的创造模式世界
- [ ] 世界生成正常
- [ ] 没有崩溃或错误

#### 3. 物品测试

打开创造模式物品栏，搜索 "ash" 或 "dragon"：

**龙族物品**:
- [ ] 龙晶杖 (Dragon Crystal Staff) - 有材质
- [ ] 龙鳞甲 (Dragon Scale Armor) - 有材质
- [ ] 龙焰心核 (Dragonfire Core) - 有材质
- [ ] 龙晶锭 (Dragon Crystal Ingot) - 有材质

**人类物品**:
- [ ] 能量步枪 (Energy Rifle) - 有材质
- [ ] 机械臂 (Mechanical Arm) - 有材质
- [ ] 火炮弹药 (Cannon Ammo) - 有材质
- [ ] 钢铁合金 (Steel Alloy) - 有材质

**共享材料**:
- [ ] 古代符石 (Ancient Rune Stone) - 有材质
- [ ] 以太核心 (Aether Core) - 有材质
- [ ] 灰烬之血 (Ash Blood) - 有材质

#### 4. 方块测试

**龙族方块**:
- [ ] 龙晶矿石 (Dragon Crystal Ore) - 有材质，可放置
- [ ] 深层龙晶矿石 (Deepslate Dragon Crystal Ore) - 有材质，可放置
- [ ] 龙晶方块 (Dragon Crystal Block) - 有材质，可放置
- [ ] 熔岩祭坛 (Lava Altar) - 有材质，可放置

**人类方块**:
- [ ] 钢铁方块 (Steel Block) - 有材质，可放置
- [ ] 机械工作台 (Mechanical Workbench) - 有材质，可放置
- [ ] 能量核心方块 (Energy Core Block) - 有材质，可放置

#### 5. 语言测试

- [ ] 切换到英文 (English) - 所有文本正确显示
- [ ] 切换到简体中文 - 所有文本正确显示

### 高级测试（可选）

#### 6. 日志检查

查看 `logs/latest.log` 文件：

- [ ] 没有 ERROR 级别的日志
- [ ] 模组正确注册了所有物品和方块
- [ ] 没有资源加载失败的警告

#### 7. 性能测试

- [ ] 游戏帧率正常（F3 查看）
- [ ] 内存使用合理
- [ ] 没有明显的卡顿

## 🐛 常见问题排查

### 问题 1: Gradle 同步失败

**症状**: "Could not resolve all dependencies" 或网络错误

**解决方案**:
1. 检查网络连接
2. 使用国内镜像（见下方配置）
3. 重试: `./gradlew build --refresh-dependencies`

**国内镜像配置**:

在 `build.gradle` 的 `repositories` 部分添加：

```gradle
repositories {
    maven { url 'https://maven.aliyun.com/repository/public' }
    maven { url 'https://maven.aliyun.com/repository/central' }
    // ... 其他仓库
}
```

### 问题 2: Java 版本错误

**症状**: "Unsupported class file major version" 或 "Java 21 required"

**解决方案**:
1. 安装 JDK 21
2. 设置 `JAVA_HOME` 环境变量
3. 在 IDEA 中设置项目 SDK: `File` → `Project Structure` → `Project SDK`

### 问题 3: 内存不足

**症状**: "OutOfMemoryError" 或构建过程中卡死

**解决方案**:

在 `gradle.properties` 中增加内存：

```properties
org.gradle.jvmargs=-Xmx4G -Xms1G
```

### 问题 4: 游戏启动失败

**症状**: 游戏窗口闪退或崩溃

**解决方案**:
1. 查看 `logs/latest.log` 查找错误信息
2. 确保所有依赖正确下载
3. 清理并重新构建: `./gradlew clean build`

### 问题 5: 材质缺失

**症状**: 物品或方块显示为紫黑方块

**解决方案**:
1. 检查 `src/main/resources/assets/ashdragonfire/textures/` 目录
2. 确保材质文件存在且命名正确
3. 检查模型 JSON 文件路径

### 问题 6: 物品未注册

**症状**: 创造模式物品栏中找不到物品

**解决方案**:
1. 检查 `ModItems.java` 中的注册代码
2. 查看日志确认物品已注册
3. 使用 `/give @s ashdragonfire:item_name` 命令测试

## 📊 构建输出

成功构建后，你会在以下位置找到文件：

```
build/
├── libs/
│   └── ashdragonfire-1.0.0.jar          # 模组 jar 文件 ⭐
├── classes/
│   └── java/main/                        # 编译后的 class 文件
└── resources/
    └── main/                             # 处理后的资源文件
```

### 安装模组

1. 找到 `build/libs/ashdragonfire-1.0.0.jar`
2. 复制到 Minecraft 的 `mods` 文件夹
3. 启动 Minecraft（需要安装对应版本的 Forge）

## 🔍 调试技巧

### 1. 启用调试日志

在 `src/main/resources/log4j2.xml` 中设置：

```xml
<Logger level="debug" name="com.glasserdraco.ashdragonfire"/>
```

### 2. 使用断点调试

在 IDEA 中:
1. 在代码行号左侧点击设置断点
2. 使用 Debug 模式运行 `runClient`
3. 游戏会在断点处暂停

### 3. 热重载（实验性）

某些代码修改可以不重启游戏:
1. 修改代码
2. `Build` → `Rebuild Project`
3. 在游戏中使用 `/reload` 命令

### 4. 查看游戏日志

实时查看日志:

```bash
# Linux/macOS
tail -f logs/latest.log

# Windows (PowerShell)
Get-Content logs/latest.log -Wait
```

## 📝 开发工作流

### 推荐的开发流程

1. **修改代码** - 在 IDEA 中编辑 Java 文件
2. **构建项目** - `Build` → `Build Project` (Ctrl+F9)
3. **运行测试** - 使用 `runClient` 配置
4. **测试功能** - 在游戏中验证修改
5. **查看日志** - 检查是否有错误
6. **提交代码** - `git add` → `git commit` → `git push`

### 快捷键（IDEA）

- `Ctrl + F9` - 构建项目
- `Shift + F10` - 运行
- `Shift + F9` - 调试
- `Ctrl + Shift + F9` - 重新编译当前文件
- `Alt + 4` - 打开运行窗口
- `Alt + 5` - 打开调试窗口

## 🚢 发布模组

### 准备发布

1. **更新版本号** - 在 `gradle.properties` 中修改 `mod_version`
2. **更新 CHANGELOG** - 记录所有更改
3. **完整测试** - 确保所有功能正常
4. **构建发布版本**:

```bash
./gradlew build
```

5. **找到 jar 文件** - `build/libs/ashdragonfire-x.x.x.jar`

### 发布到 CurseForge

1. 访问 [CurseForge](https://www.curseforge.com/minecraft/mc-mods)
2. 创建新项目
3. 上传 jar 文件
4. 填写模组信息和更新日志
5. 发布

### 发布到 Modrinth

1. 访问 [Modrinth](https://modrinth.com/)
2. 创建新项目
3. 上传 jar 文件
4. 填写模组信息
5. 发布

## 📚 相关资源

### 官方文档
- [Forge 文档](https://docs.minecraftforge.net/)
- [Minecraft Wiki](https://minecraft.fandom.com/)
- [Gradle 文档](https://docs.gradle.org/)

### 社区资源
- [Forge 论坛](https://forums.minecraftforge.net/)
- [模组开发教程](https://forge.gemwire.uk/wiki/Main_Page)
- [Discord 社区](https://discord.gg/UvedJ9m)

### 本项目文档
- `README.md` - 项目介绍
- `DEVELOPMENT.md` - 开发指南
- `QUICKSTART.md` - 快速开始
- `PROJECT_OVERVIEW.md` - 项目概览
- `ASSETS_SUMMARY.md` - 资源总结

## ✅ 最终检查清单

在提交或发布前，确保：

- [ ] 所有代码编译无错误
- [ ] 所有物品和方块正确注册
- [ ] 所有材质正确显示
- [ ] 语言文件完整（中英文）
- [ ] 没有控制台错误或警告
- [ ] 游戏可以正常启动和退出
- [ ] 多人游戏兼容性测试（可选）
- [ ] 与其他模组兼容性测试（可选）
- [ ] 更新文档和 CHANGELOG
- [ ] 创建 Git tag 标记版本

## 🎉 成功标志

如果你看到以下内容，说明构建成功：

```
BUILD SUCCESSFUL in Xm Xs
```

如果游戏启动后看到：

```
[ashdragonfire] Ash & Dragonfire mod loaded successfully!
[ashdragonfire] Registered 12 items
[ashdragonfire] Registered 10 blocks
```

**恭喜！你的模组已经成功构建和运行！** 🎊

---

**最后更新**: 2025-01-01  
**适用版本**: Minecraft 1.21.1 + Forge 52.0.29+  
**项目地址**: https://github.com/glasses666/ashdragonfire

