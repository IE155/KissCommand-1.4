# KissCommand

**版本**: 1.4-SNAPSHOT  
**作者**: ie155, 18ay  
**API 版本**: 1.21  
**官网/仓库**: [GitHub](https://github.com/IE155)

---

## 插件简介

KissCommand是一个Paper 1.21+的插件，允许玩家 `/kiss <玩家名>` 指令向其他玩家发送一个可见消息和特效。

---

### 功能

- 指令只影响目标玩家和发起者，其他玩家无法看到。
- 发送消息时带有渐变色效果。
- 目标玩家头顶会生成心形粒子效果。
- 目标玩家播放音效提示。
- 1.4版本更新内容：在config.yml可自定义，可开启或关闭在玩家身体中部渲染爱心光环，半径和数量可以自定义。
- 光环环渲染默认关闭,在config.yml里可以打开.
---

## 安装方法

1. 将 `KissCommand-1.4-SNAPSHOT.jar` 文件放入服务器的 `plugins/` 文件夹。
3. 首次启动时，插件会自动生成默认配置文件 `config.yml`（可用于自定义消息，支持`渐变色`哦！）。

---

## 指令

| 指令 | 说明 | 用法 |
|------|------|------|
| `/kiss` | 向指定玩家发送一个 kiss 提示 | `/kiss <玩家ID>` |
| `/kiss2load`|在服务器输入，无需重启，直接重新加载config.yml|`/kiss2load`|

---

提一句：如果出现/kisscommand:kiss或者/kisscommand:kiss2load是正常的，原理是什么我也不知道，解决方法是不管它（最佳方法）。

**示例**：

```text
/kiss 18ay
/kiss2load
