#!/bin/bash

# LangGraph4j Studio 集成测试脚本

echo "========================================="
echo "LangGraph4j Studio 集成测试"
echo "========================================="
echo ""

# 配置
BASE_URL="http://localhost:8123/api"

# 检查服务是否启动
echo "1. 检查服务是否启动..."
if curl -s "$BASE_URL/health" > /dev/null 2>&1; then
    echo "✅ 服务正常运行"
else
    echo "❌ 服务未启动，请先运行: mvn spring-boot:run"
    exit 1
fi
echo ""

# 测试 Studio 端点
echo "2. 测试 Studio 端点..."
STUDIO_RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/langgraph4j/studio")
if [ "$STUDIO_RESPONSE" = "200" ]; then
    echo "✅ Studio UI 可访问: $BASE_URL/langgraph4j/studio"
else
    echo "⚠️  Studio 返回状态码: $STUDIO_RESPONSE"
fi
echo ""

# 测试简单工作流 API
echo "3. 测试简单工作流 API..."
SIMPLE_RESPONSE=$(curl -s -X POST "$BASE_URL/workflow/simple/execute" \
  -H "Content-Type: application/json" \
  -d '{"prompt": "测试"}' \
  -w "\n%{http_code}")
SIMPLE_CODE=$(echo "$SIMPLE_RESPONSE" | tail -n1)
if [ "$SIMPLE_CODE" = "200" ]; then
    echo "✅ 简单工作流 API 正常"
else
    echo "⚠️  简单工作流返回状态码: $SIMPLE_CODE"
fi
echo ""

# 测试并发工作流 API
echo "4. 测试并发工作流 API..."
CONCURRENT_RESPONSE=$(curl -s -X POST "$BASE_URL/workflow/concurrent/execute" \
  -H "Content-Type: application/json" \
  -d '{"prompt": "测试"}' \
  -w "\n%{http_code}")
CONCURRENT_CODE=$(echo "$CONCURRENT_RESPONSE" | tail -n1)
if [ "$CONCURRENT_CODE" = "200" ]; then
    echo "✅ 并发工作流 API 正常"
else
    echo "⚠️  并发工作流返回状态码: $CONCURRENT_CODE"
fi
echo ""

# 测试获取工作流图
echo "5. 测试获取工作流图..."
GRAPH_RESPONSE=$(curl -s "$BASE_URL/workflow/graph/simple" -w "\n%{http_code}")
GRAPH_CODE=$(echo "$GRAPH_RESPONSE" | tail -n1)
if [ "$GRAPH_CODE" = "200" ]; then
    echo "✅ 工作流图 API 正常"
else
    echo "⚠️  工作流图返回状态码: $GRAPH_CODE"
fi
echo ""

echo "========================================="
echo "测试完成！"
echo "========================================="
echo ""
echo "📊 访问 Studio UI:"
echo "   http://localhost:8123/api/langgraph4j/studio"
echo ""
echo "📖 查看文档:"
echo "   - README_LANGGRAPH_STUDIO.md"
echo "   - LANGGRAPH_STUDIO_GUIDE.md"
echo ""
