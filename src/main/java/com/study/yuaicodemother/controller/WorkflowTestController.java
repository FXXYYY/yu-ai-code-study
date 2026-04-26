package com.study.yuaicodemother.controller;

import com.study.yuaicodemother.commmon.BaseResponse;
import com.study.yuaicodemother.commmon.ResultUtils;
import com.study.yuaicodemother.langgrap4j.CodeGenConcurrentWorkflow;
import com.study.yuaicodemother.langgrap4j.CodeGenWorkflow;
import com.study.yuaicodemother.langgrap4j.context.WorkflowContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * LangGraph 工作流测试控制器
 * 用于测试和演示 LangGraph4j Studio 集成的工作流
 */
@Slf4j
@RestController
@RequestMapping("/workflow")
@Tag(name = "LangGraph工作流", description = "工作流测试和演示接口")
public class WorkflowTestController {

    /**
     * 执行简单代码生成工作流
     */
    @PostMapping("/simple/execute")
    @Operation(summary = "执行简单代码生成工作流", description = "串行执行的代码生成流程")
    public BaseResponse<?> executeSimpleWorkflow(@RequestBody WorkflowRequest request) {
        log.info("收到简单工作流执行请求: {}", request.getPrompt());
        
        try {
            CodeGenWorkflow workflow = new CodeGenWorkflow();
            WorkflowContext context = workflow.executeWorkflow(request.getPrompt());
            
            return ResultUtils.success(context);
        } catch (Exception e) {
            log.error("简单工作流执行失败", e);
            return ResultUtils.error(500, "工作流执行失败: " + e.getMessage());
        }
    }

    /**
     * 执行并发代码生成工作流
     */
    @PostMapping("/concurrent/execute")
    @Operation(summary = "执行并发代码生成工作流", description = "并行图片收集的代码生成流程")
    public BaseResponse<?> executeConcurrentWorkflow(@RequestBody WorkflowRequest request) {
        log.info("收到并发工作流执行请求: {}", request.getPrompt());
        
        try {
            CodeGenConcurrentWorkflow workflow = new CodeGenConcurrentWorkflow();
            WorkflowContext context = workflow.executeWorkflow(request.getPrompt());
            
            return ResultUtils.success(context);
        } catch (Exception e) {
            log.error("并发工作流执行失败", e);
            return ResultUtils.error(500, "工作流执行失败: " + e.getMessage());
        }
    }

    /**
     * 获取工作流图信息（Mermaid格式）
     */
    @GetMapping("/graph/{type}")
    @Operation(summary = "获取工作流图", description = "获取工作流的 Mermaid 图表")
    public BaseResponse<?> getWorkflowGraph(@PathVariable String type) {
        try {
            String graphContent;
            if ("simple".equals(type)) {
                CodeGenWorkflow workflow = new CodeGenWorkflow();
                var compiledGraph = workflow.createWorkflow();
                var graph = compiledGraph.getGraph(org.bsc.langgraph4j.GraphRepresentation.Type.MERMAID);
                graphContent = graph.content();
            } else if ("concurrent".equals(type)) {
                CodeGenConcurrentWorkflow workflow = new CodeGenConcurrentWorkflow();
                var compiledGraph = workflow.createWorkflow();
                var graph = compiledGraph.getGraph(org.bsc.langgraph4j.GraphRepresentation.Type.MERMAID);
                graphContent = graph.content();
            } else {
                return ResultUtils.error(400, "不支持的工作流类型: " + type);
            }
            
            return ResultUtils.success(graphContent);
        } catch (Exception e) {
            log.error("获取工作流图失败", e);
            return ResultUtils.error(500, "获取工作流图失败: " + e.getMessage());
        }
    }

    /**
     * 工作流请求对象
     */
    @lombok.Data
    public static class WorkflowRequest {
        private String prompt;
    }
}
