package com.study.yuaicodemother.langgrap4j;

import com.study.yuaicodemother.langgrap4j.context.WorkflowContext;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.GraphRepresentation;
import org.bsc.langgraph4j.prebuilt.MessagesState;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * LangGraph4j Studio 启动验证器
 * 应用启动时自动验证工作流是否正确注册
 */
@Slf4j
@Component
public class StudioStartupVerifier implements CommandLineRunner {

    private final Map<String, CompiledGraph<MessagesState<String>>> workflows;

    public StudioStartupVerifier(Map<String, CompiledGraph<MessagesState<String>>> workflows) {
        this.workflows = workflows;
    }

    @Override
    public void run(String... args) {
        log.info("========================================");
        log.info("LangGraph4j Studio 工作流注册验证");
        log.info("========================================");
        
        if (workflows.isEmpty()) {
            log.warn("⚠️  未检测到任何工作流！");
            return;
        }
        
        log.info("✅ 检测到 {} 个工作流:", workflows.size());
        
        workflows.forEach((name, graph) -> {
            log.info("  📊 工作流名称: {}", name);
            
            // 获取工作流图信息
            try {
                GraphRepresentation graphRep = graph.getGraph(GraphRepresentation.Type.MERMAID);
                log.info("     节点数量: {}", countNodes(graphRep.content()));
                log.info("     状态: ✅ 已注册");
            } catch (Exception e) {
                log.error("     状态: ❌ 获取图信息失败", e);
            }
        });
        
        log.info("========================================");
        log.info("🎯 访问 Studio UI: http://localhost:8123/api/langgraph4j/studio");
        log.info("========================================");
    }
    
    /**
     * 简单统计 Mermaid 图中的节点数量
     */
    private int countNodes(String mermaidContent) {
        if (mermaidContent == null) {
            return 0;
        }
        // 简单计算：统计以方括号或圆括号开始的行
        String[] lines = mermaidContent.split("\n");
        int count = 0;
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.contains("[") || trimmed.contains("(")) {
                count++;
            }
        }
        return count;
    }
}
