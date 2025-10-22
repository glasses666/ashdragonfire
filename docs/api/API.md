# Ash & Dragonfire API Documentation

## 📚 概述

本文档提供了《灰烬与龙焰》模组的公共 API，允许第三方模组与本模组进行交互和扩展。

---

## 🔌 API 接口

### IFactionProvider

提供阵营信息的接口。

```java
package com.glasserdraco.ashdragonfire.api;

import net.minecraft.world.entity.player.Player;

public interface IFactionProvider {
    /**
     * 获取玩家的阵营
     * @param player 玩家实体
     * @return 阵营名称（dragon/human/neutral）
     */
    String getFaction(Player player);
    
    /**
     * 设置玩家的阵营
     * @param player 玩家实体
     * @param faction 阵营名称
     */
    void setFaction(Player player, String faction);
    
    /**
     * 获取玩家的声望值
     * @param player 玩家实体
     * @return 声望值
     */
    int getReputation(Player player);
    
    /**
     * 添加声望值
     * @param player 玩家实体
     * @param amount 声望数量
     */
    void addReputation(Player player, int amount);
}
```

### IConversionHandler

处理实体转换的接口。

```java
package com.glasserdraco.ashdragonfire.api;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface IConversionHandler {
    /**
     * 尝试转换实体
     * @param target 目标实体
     * @param owner 主人玩家
     * @param conversionType 转换类型
     * @return 是否成功
     */
    boolean convertEntity(LivingEntity target, Player owner, String conversionType);
    
    /**
     * 检查实体是否被转换
     * @param entity 实体
     * @return 是否被转换
     */
    boolean isConverted(LivingEntity entity);
    
    /**
     * 获取实体的转换类型
     * @param entity 实体
     * @return 转换类型
     */
    String getConversionType(LivingEntity entity);
    
    /**
     * 获取实体的主人
     * @param entity 实体
     * @return 主人UUID
     */
    UUID getOwner(LivingEntity entity);
}
```

---

## 🎯 使用示例

### 检查玩家阵营

```java
import com.glasserdraco.ashdragonfire.api.IFactionProvider;

public class MyMod {
    public void checkPlayerFaction(Player player) {
        IFactionProvider factionProvider = // 获取实例
        String faction = factionProvider.getFaction(player);
        
        if (faction.equals("dragon")) {
            // 玩家是龙族
        } else if (faction.equals("human")) {
            // 玩家是人类
        }
    }
}
```

### 转换实体

```java
import com.glasserdraco.ashdragonfire.api.IConversionHandler;

public class MyMod {
    public void convertDragon(LivingEntity dragon, Player player) {
        IConversionHandler handler = // 获取实例
        boolean success = handler.convertEntity(dragon, player, "bonded");
        
        if (success) {
            // 转换成功
        } else {
            // 转换失败
        }
    }
}
```

---

## 📦 依赖配置

### Gradle

```gradle
repositories {
    maven {
        name = "AshDragonfire Maven"
        url = "https://maven.glasserdraco.com/releases"
    }
}

dependencies {
    implementation fg.deobf("com.glasserdraco:ashdragonfire:1.21.1-0.2.0")
}
```

### mods.toml

```toml
[[dependencies.yourmod]]
    modId="ashdragonfire"
    mandatory=false
    versionRange="[0.2.0,)"
    ordering="AFTER"
    side="BOTH"
```

---

## 🔧 事件系统

### FactionJoinEvent

玩家加入阵营时触发。

```java
@SubscribeEvent
public void onFactionJoin(FactionJoinEvent event) {
    Player player = event.getPlayer();
    String faction = event.getFaction();
    
    // 自定义逻辑
}
```

### ConversionEvent

实体转换时触发。

```java
@SubscribeEvent
public void onConversion(ConversionEvent event) {
    LivingEntity entity = event.getEntity();
    Player owner = event.getOwner();
    String type = event.getConversionType();
    
    // 可以取消事件
    if (shouldCancel) {
        event.setCanceled(true);
    }
}
```

---

## 📝 数据包扩展

### 添加自定义技能

在 `data/yourmod/ashdragonfire/skills/` 目录下创建 JSON 文件：

```json
{
  "id": "custom_skill",
  "name": "Custom Skill",
  "description": "A custom skill",
  "faction": "dragon",
  "type": "active",
  "cooldown": 10.0,
  "mana_cost": 50,
  "damage": {
    "base": 10.0,
    "type": "magic"
  }
}
```

### 添加自定义阵营

在 `data/yourmod/ashdragonfire/factions/` 目录下创建 JSON 文件：

```json
{
  "id": "custom_faction",
  "name": "Custom Faction",
  "description": "A custom faction",
  "color": "#00FF00",
  "attributes": {
    "health": 20.0,
    "armor": 5.0
  }
}
```

---

## 🎨 客户端扩展

### 注册自定义纹理

```java
import com.glasserdraco.ashdragonfire.client.TextureSwitchManager;

public class MyModClient {
    public void registerTextures() {
        TextureSwitchManager.registerConversionTexture(
            "custom_type",
            new ResourceLocation("mymod", "textures/entity/custom.png")
        );
    }
}
```

---

## 📞 支持

如有问题，请：

- 查看 [Wiki](../wiki/Home.md)
- 提交 [Issue](https://github.com/glasses666/ashdragonfire/issues)
- 加入 [讨论](https://github.com/glasses666/ashdragonfire/discussions)

---

**API 版本**: 0.2.0  
**最后更新**: 2025-01-01  
**兼容性**: Minecraft 1.21.1, Forge 1.21.1

