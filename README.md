# 灰烬与龙焰:永燃之战 (Ash & Dragonfire: Eternal Conflict)

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-green.svg)](https://www.minecraft.net/)
[![Forge](https://img.shields.io/badge/Forge-52.0.29-orange.svg)](https://files.minecraftforge.net/)
[![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-red.svg)](LICENSE)

一款大型阵营对抗类 Minecraft 模组,在魔法与科技交织的世界中,龙族与人类展开史诗般的战争。

## 📖 模组简介

在艾格斯大陆(Aegis)上,龙族与人类长期对立。龙族拥有强大的魔法与飞行能力,生活在浮空岛与火山地带;人类掌握火炮与机械科技,建立了钢铁城市与飞艇要塞。战争在天空与大地之间持续进行,玩家可选择任一阵营并参与改变世界的战争。

## ✨ 核心特性

### 🏰 阵营系统
- **两大阵营**: 龙族领地 (Drakyn Dominion) vs 钢铁联邦 (Iron Federation)
- **声望系统**: 通过战斗、占领领地获得声望,解锁更强大的能力
- **领地争夺**: 控制关键区域,影响世界资源分布

### 🐉 龙族 (魔法阵营)
- **龙形变身**: 三个成长阶段(基础、成年、上古)
- **龙语魔法**: 火焰吐息、风暴召唤、岩浆喷发、闪电打击
- **魔力系统**: 独特的魔力能量系统
- **专属装备**: 龙晶杖、龙鳞甲、龙焰心核

### ⚙️ 人类 (科技阵营)
- **科技魔导**: 融合魔法与科技的武器系统
- **机械装备**: 火炮、机甲、能量步枪
- **能量系统**: 兼容 Forge Energy 的科技能量
- **载具系统**: 飞艇、机械战车

### 🌍 世界生成
- **焰冠群岛**: 龙族维度,浮空岛与火山地形
- **齿轮之城**: 人类维度,钢铁废墟城市
- **特殊结构**: 龙族圣殿、机械要塞、远古遗迹

### ⚔️ 战争事件
- **定期战争**: 每周触发阵营大战
- **世界Boss**: 远古巨龙、机械神等强大Boss
- **领地冲突**: 争夺前线区域控制权
- **动态世界**: 战争结果影响天气、资源与NPC

## 🛠️ 技术信息

- **Minecraft版本**: 1.21.1
- **Forge版本**: 52.0.29+
- **Java版本**: 21
- **模组ID**: `ashdragonfire`
- **主包**: `com.glasserdraco.ashdragonfire`

## 📦 安装方法

1. 确保已安装 Minecraft 1.21.1 和对应版本的 Forge
2. 下载模组 jar 文件
3. 将 jar 文件放入 `.minecraft/mods/` 目录
4. 启动游戏

## 🎮 快速开始

1. **进入游戏**: 首次进入会提示选择阵营
2. **选择阵营**: 龙族或人类,选择后无法更改
3. **学习技能**: 按 `K` 键(默认)打开技能树
4. **参与战争**: 击败敌对阵营玩家获得声望
5. **探索维度**: 寻找传送门前往阵营专属维度

## ⚙️ 配置文件

配置文件位于 `config/ashdragonfire-common.toml`,可调整:
- 技能伤害与消耗
- 声望获取速度
- 战争事件周期
- 世界生成参数

## 🔧 开发环境搭建

### 前置要求
- JDK 21
- Gradle 8.x
- IntelliJ IDEA 或 Eclipse

### 构建步骤

```bash
# 克隆仓库
git clone https://github.com/glasserdraco/ashdragonfire.git
cd ashdragonfire

# 设置开发环境
./gradlew genIntellijRuns  # IntelliJ IDEA
# 或
./gradlew genEclipseRuns   # Eclipse

# 构建模组
./gradlew build

# 生成的 jar 文件位于 build/libs/
```

## 📂 项目结构

```
ashdragonfire/
├── src/main/
│   ├── java/com/glasserdraco/ashdragonfire/
│   │   ├── AshDragonfireMod.java          # 主类
│   │   ├── core/                          # 核心系统
│   │   ├── faction/                       # 阵营系统
│   │   ├── race/                          # 种族系统
│   │   ├── energy/                        # 能量系统
│   │   ├── ability/                       # 技能系统
│   │   ├── item/                          # 物品
│   │   ├── block/                         # 方块
│   │   ├── entity/                        # 实体
│   │   ├── world/                         # 世界生成
│   │   ├── event/                         # 事件系统
│   │   ├── network/                       # 网络通信
│   │   ├── client/                        # 客户端
│   │   ├── data/                          # 数据管理
│   │   └── api/                           # API接口
│   └── resources/
│       ├── META-INF/mods.toml             # 模组元数据
│       ├── assets/ashdragonfire/          # 资源文件
│       └── data/ashdragonfire/            # 数据包
├── build.gradle                           # Gradle构建脚本
└── README.md                              # 本文件
```

## 🔌 API 使用

模组提供了扩展 API,允许其他模组集成:

```java
// 获取玩家阵营
Faction faction = FactionManager.getPlayerFaction(player);

// 检查玩家是否敌对
boolean hostile = FactionManager.areHostile(player1, player2);

// 注册自定义技能
AbilityRegistry.registerAbility(new CustomAbility());
```

## 🤝 贡献指南

欢迎贡献代码、报告问题或提出建议!

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📝 待实现功能

- [ ] 完整的实体渲染系统
- [ ] GUI界面实现
- [ ] 更多龙族技能
- [ ] 更多人类科技装备
- [ ] 完整的世界生成配置
- [ ] 数据持久化(Capability系统)
- [ ] 网络同步优化
- [ ] 音效与粒子效果
- [ ] 任务系统
- [ ] 经济系统

## 📄 许可证

All Rights Reserved © 2025 GlasserDraco

未经许可,不得复制、修改或分发本模组。

## 👤 作者

**GlasserDraco**
- GitHub: [@glasserdraco](https://github.com/glasserdraco)

## 🙏 致谢

- Minecraft Forge 团队
- Minecraft 模组开发社区
- 所有测试者和贡献者

---

**注意**: 本模组目前处于开发阶段,许多功能尚未完全实现。请定期查看更新!

