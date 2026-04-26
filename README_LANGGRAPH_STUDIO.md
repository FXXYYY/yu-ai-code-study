# Yu AI Code Mother - LangGraph4j Studio 集成指南

## 🎯 项目简介

本项目是一个基于 **LangGraph4j** 的智能代码生成平台，已成功集成 **LangGraph4j Studio**，提供工作流的可视化、调试和监控功能。

## ✨ 主要特性

- 🤖 **智能代码生成**：基于 DeepSeek 大模型的代码生成能力
- 📊 **工作流可视化**：通过 LangGraph4j Studio 可视化工作流执行过程
- 🔄 **并发处理**：支持并发的图片收集和处理
- 🎨 **多类型图片资源**：内容图片、插图、图表、Logo 等自动收集
- 🔍 **代码质检**：自动生成后的代码质量检查
- 🚀 **项目构建**：支持 Vue 项目的自动化构建

## 🚀 快速开始

### 1. 环境要求

- Java 21+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+

### 2. 启动应用

```bash
# 克隆项目
git clone <repository-url>
cd yu-ai-code-mother

# 编译项目
mvn clean install

# 启动应用
mvn spring-boot:run
```

### 3. 访问 LangGraph4j Studio

应用启动后，在浏览器中访问：

```
http://localhost:8123/api/langgraph4j/studio
```

## 📋 工作流说明

### 1. simple-code-gen（简单代码生成工作流）

**流程**：
```
图片计划 → 提示词增强 → 路由 → 代码生成 → 质检 → 项目构建
```

**特点**：
- 串行执行，简单直观
- 适合简单的代码生成任务
- 易于调试和理解

### 2. concurrent-code-gen（并发代码生成工作流）

**流程**：
```
图片计划 
  ↓
[并行分支]
├─ 内容图片收集
├─ 插图收集  
├─ 图表收集
└─ Logo收集
  ↓
图片聚合 → 提示词增强 → 路由 → 代码生成 → 质检 → 项目构建
```

**特点**：
- 并行图片收集，提升效率
- 适合复杂的代码生成任务
- 支持多种类型图片资源

## 🎮 使用 Studio

### 基本操作

1. **选择工作流**：在左上角下拉菜单中选择要查看的工作流
2. **查看流程图**：自动渲染 Mermaid 格式的流程图
3. **执行工作流**：点击 "Submit" 按钮触发执行
4. **观察状态**：实时查看每个节点的执行状态和上下文变化

### 调试功能

- **断点调试**：在特定节点暂停执行
- **状态检查**：查看 WorkflowContext 的完整信息
- **消息历史**：追踪所有消息的流转过程
- **参数调整**：修改输入参数重新执行

## 🔧 API 接口

除了 Studio UI，您还可以通过 REST API 调用工作流：

### 执行简单工作流

```bash
curl -X POST http://localhost:8123/api/workflow/simple/execute \
  -H "Content-Type: application/json" \
  -d '{"prompt": "创建一个个人博客首页"}'
```

### 执行并发工作流

```bash
curl -X POST http://localhost:8123/api/workflow/concurrent/execute \
  -H "Content-Type: application/json" \
  -d '{"prompt": "创建一个电商网站主页"}'
```

### 获取工作流图

```bash
# 获取简单工作流图
curl http://localhost:8123/api/workflow/graph/simple

# 获取并发工作流图
curl http://localhost:8123/api/workflow/graph/concurrent
```

## 📝 配置说明

### application.yaml

```yaml
# LangGraph4j Studio 配置
langgraph4j:
  studio:
    path: /langgraph4j/studio  # Studio 访问路径
    enabled: true               # 是否启用 Studio

# AI 模型配置
langchain4j:
  open-ai:
    chat-model:
      base-url: https://api.deepseek.com
      api-key: your-api-key
      model-name: deepseek-chat
```

### 注册新工作流

在 `LangGraphStudioConfig.java` 中添加新的 Bean：

```java
@Bean("my-workflow")
public CompiledGraph<MessagesState<String>> myWorkflow() {
    MyWorkflow workflow = new MyWorkflow();
    return workflow.createWorkflow();
}
```

## 🏗️ 项目结构

```
src/main/java/com/study/yuaicodemother/
├── langgrap4j/
│   ├── config/
│   │   └── LangGraphStudioConfig.java  # Studio 配置
│   ├── context/
│   │   └── WorkflowContext.java        # 工作流上下文
│   ├── node/                           # 工作流节点
│   │   ├── ImagePlanNode.java
│   │   ├── PromptEnhancerNode.java
│   │   ├── CodeGeneratorNode.java
│   │   └── ...
│   ├── CodeGenWorkflow.java            # 简单工作流
│   └── CodeGenConcurrentWorkflow.java  # 并发工作流
└── controller/
    └── WorkflowTestController.java     # 测试控制器
```

## 🔍 故障排查

### Studio 无法访问

1. 确认应用已正常启动
2. 检查端口号（默认 8123）和 context-path（默认 /api）
3. 确认 `langgraph4j.studio.enabled=true`
4. 查看启动日志是否有错误

### 工作流未显示

1. 检查 `LangGraphStudioConfig` 是否正确配置
2. 确认工作流 Bean 被 Spring 扫描到
3. 查看控制台日志中的注册信息

### 执行失败

1. 检查 DeepSeek API Key 配置
2. 确认 Redis 服务正常运行
3. 查看详细错误日志

## 📚 相关文档

- [LangGraph4j Studio 详细指南](LANGGRAPH_STUDIO_GUIDE.md)
- [LangGraph4j 官方文档](https://github.com/langgraph4j/langgraph4j)
- [LangGraph4j 示例仓库](https://github.com/langgraph4j/langgraph4j-examples)

## 💡 最佳实践

1. **开发阶段**：使用 Studio 进行工作流调试和验证
2. **测试阶段**：通过 API 接口进行自动化测试
3. **生产环境**：可以禁用 Studio (`enabled: false`) 减少资源占用
4. **性能优化**：合理配置并发工作流的线程池大小

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

## 📄 许可证

MIT License

---

**最后更新**: 2026-04-23
