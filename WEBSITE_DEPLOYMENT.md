# 官方网站部署说明

## 🌐 网站信息

**网站地址**: http://8.138.150.152:8888

**部署方式**: Docker 容器

**容器名称**: ashdragonfire-web

---

## 📦 网站内容

网站包含以下页面：

1. **首页** (/) - 模组介绍、特色展示、阵营对比、统计数据
2. **下载页** (/download) - 版本列表、系统要求、下载链接
3. **文档页** (/docs) - 安装指南、玩法指南
4. **Wiki页** (/wiki) - 阵营、实体、物品、技能详细信息
5. **更新日志** (/changelog) - 版本历史

---

## 🎨 设计特点

- **深色主题** - 黑色背景配合红橙渐变
- **响应式布局** - 完美支持移动端和桌面端
- **专业导航** - 清晰的页面导航和页脚
- **完整内容** - 详细的模组信息和文档

---

## 🐳 Docker 管理命令

### 查看容器状态
\`\`\`bash
docker ps | grep ashdragonfire-web
\`\`\`

### 查看容器日志
\`\`\`bash
docker logs ashdragonfire-web
\`\`\`

### 重启容器
\`\`\`bash
docker restart ashdragonfire-web
\`\`\`

### 停止容器
\`\`\`bash
docker stop ashdragonfire-web
\`\`\`

### 删除容器
\`\`\`bash
docker stop ashdragonfire-web
docker rm ashdragonfire-web
\`\`\`

---

## 🔄 更新网站

如果需要更新网站内容：

1. 重新构建项目
\`\`\`bash
cd /path/to/ashdragonfire-website
pnpm build
\`\`\`

2. 打包并上传到服务器
\`\`\`bash
tar -czf ashdragonfire-website-dist.tar.gz dist/
scp ashdragonfire-website-dist.tar.gz root@8.138.150.152:/root/
\`\`\`

3. 在服务器上更新
\`\`\`bash
cd /root
tar -xzf ashdragonfire-website-dist.tar.gz
cp -r dist/public/* /var/www/ashdragonfire/
docker restart ashdragonfire-web
\`\`\`

---

## 📊 服务器信息

- **服务器IP**: 8.138.150.152
- **操作系统**: Alibaba Cloud Linux 3
- **Web服务器**: Nginx (Docker)
- **端口**: 8888 (80端口被翼龙面板占用)

---

## 🎉 部署完成时间

2025-10-22 11:53:47 CST

---

## 📝 注意事项

1. 端口80被 Pterodactyl Panel (翼龙面板) 占用，因此使用端口8888
2. 容器设置为自动重启 (unless-stopped)
3. 网站文件位于 /var/www/ashdragonfire/
4. 如需使用80端口，需要先停止翼龙面板容器

---

**Made with ❤️ by Glasses666**
