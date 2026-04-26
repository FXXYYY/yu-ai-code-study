# LangGraph4j Studio 集成说明

## 📖 概述

本项目已成功集成 **LangGraph4j Studio**，提供工作流的可视化、调试和监控功能。

## 🚀 快速开始

### 1. 启动应用

```bash
mvn spring-boot:run
```

### 2. 访问 Studio UI

启动应用后，在浏览器中访问：

```
http://localhost:8123/api/langgraph4j/studio
```

### 3. 使用 Studio

在 Studio 界面中，您可以：

- 📊 **可视化工作流图**：查看工作流的节点和连接关系
- ▶️ **执行工作流**：手动触发工作流执行并观察每一步的状态
- 🔍 **调试状态**：查看每个节点的输入输出和状态变化
- ⏸️ **断点调试**：在特定节点暂停执行，检查中间状态

## 📋 已注册的工作流

### 1. simple-code-gen（简单代码生成工作流）

- **类型**：串行执行
- **流程**：图片计划 → 提示词增强 → 路由 → 代码生成 → 质检 → 项目构建
- **适用场景**：简单的代码生成任务

### 2. concurrent-code-gen（并发代码生成工作流）

- **类型**：并行执行
- **流程**：图片计划 → [内容图片收集 | 插图收集 | 图表收集 | Logo收集]（并行）→ 聚合 → 提示词增强 → ...
- **适用场景**：需要收集多种类型图片的复杂代码生成任务

## 🔧 API 接口

除了 Studio UI，您还可以通过 REST API 调用工作流：

### 执行简单工作流

```bash
POST http://localhost:8123/api/workflow/simple/execute
Content-Type: application/json

{
  "prompt": "创建一个个人博客首页"
}
```

### 执行并发工作流

```bash
POST http://localhost:8123/api/workflow/concurrent/execute
Content-Type: application/json

{
  "prompt": "创建一个电商网站主页"
}
```

### 获取工作流图（Mermaid格式）

```bash
GET http://localhost:8123/api/workflow/graph/simple
GET http://localhost:8123/api/workflow/graph/concurrent
```

## 📝 配置说明

### application.yaml 配置

```yaml
langgraph4j:
  studio:
    # Studio UI 访问路径
    path: /langgraph4j/studio
    # 是否启用 Studio
    enabled: true
```

### 注册新工作流到 Studio

在 `LangGraphStudioConfig.java` 中添加新的 Bean：

```java
@Bean
public LangGraphStudioConfiguration myNewWorkflow() {
    // 创建工作流实例
    MyWorkflow workflow = new MyWorkflow();
    CompiledGraph<MessagesState<String>> compiledGraph = workflow.createWorkflow();
    
    return LangGraphStudioConfiguration.builder()
            .name("my-workflow")
            .description("我的工作流描述")
            .compiledGraph(compiledGraph)
            .build();
}
```

## 🎯 Studio 功能特性

### 1. 工作流可视化

- 自动渲染 Mermaid 图表
- 显示节点之间的连接关系
- 支持条件分支的可视化

### 2. 实时状态追踪

- 查看每个节点的执行状态
- 监控 WorkflowContext 的变化
- 追踪消息历史

### 3. 交互式调试

- 手动触发节点执行
- 修改输入参数重新执行
- 查看详细的执行日志

### 4. 多工作流管理

- 切换不同的工作流
- 比较不同工作流的执行结果
- 同时运行多个工作流实例

## 🔍 故障排查

### Studio 无法访问

1. 检查应用是否正常启动
2. 确认端口号是否正确（默认 8123）
3. 检查 context-path 配置（默认 /api）
4. 确认 `langgraph4j.studio.enabled=true`

### 工作流未显示

1. 检查 `LangGraphStudioConfig` 是否正确配置
2. 确认工作流 Bean 是否被 Spring 扫描到
3. 查看启动日志是否有错误信息

### 执行失败

1. 检查 AI 模型配置（DeepSeek API Key）
2. 确认 Redis 服务是否正常运行
3. 查看详细错误日志

## 📚 相关资源

- [LangGraph4j 官方文档](https://github.com/langgraph4j/langgraph4j)
- [LangGraph4j Studio 示例](https://github.com/langgraph4j/langgraph4j-examples)
- [项目工作流代码](../src/main/java/com/study/yuaicodemother/langgrap4j/)

## 💡 最佳实践

1. **开发阶段**：使用 Studio 进行工作流调试和验证
2. **测试阶段**：通过 API 接口进行自动化测试
3. **生产环境**：可以禁用 Studio (`enabled: false`) 以减少资源占用
4. **性能优化**：对于并发工作流，合理配置线程池大小

## 🔄 版本信息

- **LangGraph4j Core**: 1.6.0-rc2
- **LangGraph4j Studio Spring Boot**: 1.6.0-rc2
- **Spring Boot**: 3.5.13
- **Java**: 21

---

**最后更新**: 2026-04-23
