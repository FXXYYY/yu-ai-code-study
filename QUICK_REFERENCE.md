# LangGraph4j Studio 快速参考

## 🚀 一键启动

```bash
mvn spring-boot:run
```

## 🌐 访问地址

- **Studio UI**: http://localhost:8123/api/langgraph4j/studio
- **API Docs**: http://localhost:8123/api/doc.html

## 📋 工作流列表

| 工作流名称 | Bean 名称 | 类型 | 说明 |
|-----------|----------|------|------|
| 简单代码生成 | `simple-code-gen` | 串行 | 基础代码生成流程 |
| 并发代码生成 | `concurrent-code-gen` | 并行 | 支持并发图片收集 |

## 🔧 常用 API

### 执行工作流

```bash
# 简单工作流
curl -X POST http://localhost:8123/api/workflow/simple/execute \
  -H "Content-Type: application/json" \
  -d '{"prompt": "创建个人博客"}'

# 并发工作流
curl -X POST http://localhost:8123/api/workflow/concurrent/execute \
  -H "Content-Type: application/json" \
  -d '{"prompt": "创建电商网站"}'
```

### 获取工作流图

```bash
# Mermaid 格式
curl http://localhost:8123/api/workflow/graph/simple
curl http://localhost:8123/api/workflow/graph/concurrent
```

## 🧪 测试命令

```bash
# 运行自动化测试
./test-studio.sh
```

## 📝 配置文件

**application.yaml**
```yaml
langgraph4j:
  studio:
    path: /langgraph4j/studio
    enabled: true  # 生产环境设为 false
```

## ➕ 添加新工作流

```java
@Bean("my-workflow")
public CompiledGraph<MessagesState<String>> myWorkflow() {
    MyWorkflow workflow = new MyWorkflow();
    return workflow.createWorkflow();
}
```

## 📂 关键文件

| 文件 | 路径 | 说明 |
|------|------|------|
| Studio 配置 | `langgrap4j/config/LangGraphStudioConfig.java` | 工作流注册 |
| 测试控制器 | `controller/WorkflowTestController.java` | API 接口 |
| 启动验证器 | `langgrap4j/StudioStartupVerifier.java` | 启动检查 |
| 使用文档 | `README_LANGGRAPH_STUDIO.md` | 详细指南 |

## 🐛 常见问题

**Q: Studio 无法访问？**  
A: 检查应用是否启动，确认端口 8123 和 context-path `/api`

**Q: 工作流未显示？**  
A: 检查 Bean 是否正确注册，查看启动日志

**Q: 执行失败？**  
A: 检查 DeepSeek API Key 和 Redis 连接

## 📚 更多信息

- 📖 [完整指南](README_LANGGRAPH_STUDIO.md)
- 📖 [详细手册](LANGGRAPH_STUDIO_GUIDE.md)
- 📖 [集成总结](INTEGRATION_SUMMARY.md)

---

**版本**: 1.6.0-rc2 | **更新**: 2026-04-23
