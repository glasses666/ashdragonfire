# 贡献指南 | Contributing Guide

感谢你对《灰烬与龙焰：永燃之战》项目的关注！我们欢迎任何形式的贡献。

---

## 🤝 如何贡献

### 1. 报告 Bug

如果你发现了 Bug，请：

1. 检查 [Issues](https://github.com/glasses666/ashdragonfire/issues) 确认问题未被报告
2. 使用 [Bug Report 模板](https://github.com/glasses666/ashdragonfire/issues/new?template=bug_report.md) 创建新 Issue
3. 提供详细信息：
   - Minecraft 版本
   - Forge 版本
   - 模组版本
   - 重现步骤
   - 崩溃日志（如果有）

### 2. 功能建议

如果你有新想法，请：

1. 检查 [Issues](https://github.com/glasses666/ashdragonfire/issues) 确认建议未被提出
2. 使用 [Feature Request 模板](https://github.com/glasses666/ashdragonfire/issues/new?template=feature_request.md) 创建新 Issue
3. 详细描述：
   - 功能目的
   - 预期效果
   - 可能的实现方式

### 3. 提交代码

#### 准备工作

1. **Fork 仓库**
   ```bash
   # 在 GitHub 上点击 Fork 按钮
   ```

2. **克隆你的 Fork**
   ```bash
   git clone https://github.com/YOUR_USERNAME/ashdragonfire.git
   cd ashdragonfire
   ```

3. **添加上游仓库**
   ```bash
   git remote add upstream https://github.com/glasses666/ashdragonfire.git
   ```

4. **创建分支**
   ```bash
   git checkout -b feature/your-feature-name
   ```

#### 开发规范

##### 代码风格

- **命名规范**
  - 类名：`PascalCase`（如 `DragonSoulLureItem`）
  - 方法名：`camelCase`（如 `convertEntity`）
  - 变量名：`camelCase`（如 `ownerUUID`）
  - 常量名：`UPPER_SNAKE_CASE`（如 `MAX_DISTANCE`）
  - 包名：`lowercase`（如 `com.glasserdraco.ashdragonfire`）

- **注释规范**
  ```java
  /**
   * 类或方法的简要描述
   * 
   * 详细说明（可选）
   * 
   * @param paramName 参数说明
   * @return 返回值说明
   * @author 作者名
   * @version 版本号
   */
  ```

- **代码格式**
  - 缩进：4个空格
  - 行宽：120字符
  - 大括号：K&R 风格

##### 提交信息

使用清晰的提交信息：

```
<type>(<scope>): <subject>

<body>

<footer>
```

**类型（type）**：
- `feat`: 新功能
- `fix`: Bug 修复
- `docs`: 文档更新
- `style`: 代码格式（不影响功能）
- `refactor`: 重构
- `test`: 测试相关
- `chore`: 构建或辅助工具

**示例**：
```
feat(conversion): Add dragon soul lure item

- Implement taming mechanic for wild dragons
- Add success rate based on faction reputation
- Create crafting recipe with dragon crystal

Closes #123
```

#### 测试

在提交前，请确保：

1. **代码能编译**
   ```bash
   ./gradlew build
   ```

2. **游戏能运行**
   ```bash
   ./gradlew runClient
   ```

3. **功能正常工作**
   - 测试新功能
   - 确保没有破坏现有功能
   - 检查多人游戏兼容性

#### 提交 Pull Request

1. **推送到你的 Fork**
   ```bash
   git push origin feature/your-feature-name
   ```

2. **创建 Pull Request**
   - 在 GitHub 上打开你的 Fork
   - 点击 "New Pull Request"
   - 填写 PR 模板
   - 等待审核

3. **响应反馈**
   - 及时回复评论
   - 根据建议修改代码
   - 保持 PR 更新

---

## 📝 文档贡献

文档同样重要！你可以：

- 修正拼写错误
- 改进说明清晰度
- 添加示例代码
- 翻译文档

---

## 🎨 美术贡献

我们欢迎美术资源贡献：

- **材质**：16x16 像素艺术风格
- **模型**：使用 Blockbench 制作
- **音效**：OGG 格式
- **动画**：GeckoLib 格式

---

## 🔍 代码审查标准

Pull Request 会根据以下标准审查：

### 必须满足
- ✅ 代码能编译
- ✅ 功能正常工作
- ✅ 没有明显的 Bug
- ✅ 遵循命名规范

### 建议满足
- 📝 有清晰的注释
- 🧪 有相应的测试
- 📚 更新了文档
- ⚡ 考虑了性能

### 不接受
- ❌ 破坏现有功能
- ❌ 包含恶意代码
- ❌ 严重性能问题
- ❌ 不遵循项目规范

---

## 🎯 优先级任务

如果你不知道从哪里开始，可以查看：

- [Good First Issue](https://github.com/glasses666/ashdragonfire/labels/good%20first%20issue) - 适合新手
- [Help Wanted](https://github.com/glasses666/ashdragonfire/labels/help%20wanted) - 需要帮助
- [High Priority](https://github.com/glasses666/ashdragonfire/labels/priority%3A%20high) - 高优先级

---

## 📞 联系方式

如有疑问，可以通过以下方式联系：

- GitHub Issues
- GitHub Discussions
- 项目主页留言

---

## 🏆 贡献者名单

感谢所有贡献者！

<!-- ALL-CONTRIBUTORS-LIST:START -->
<!-- 这里会自动生成贡献者列表 -->
<!-- ALL-CONTRIBUTORS-LIST:END -->

---

**再次感谢你的贡献！** 🎉

你的每一份努力都让这个项目变得更好！

