package com.study.yuaicodemother.langgrap4j.config;

import com.study.yuaicodemother.langgrap4j.CodeGenConcurrentWorkflow;
import com.study.yuaicodemother.langgrap4j.CodeGenWorkflow;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.prebuilt.MessagesState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LangGraph4j Studio 配置类
 * 将工作流注册为 Spring Bean，Studio 会自动发现并展示
 */
@Slf4j
@Configuration
public class LangGraphStudioConfig {

    /**
     * 注册简单代码生成工作流
     * Bean 名称将作为工作流的标识符
     */
    @Bean("simple-code-gen")
    public CompiledGraph<MessagesState<String>> simpleCodeGenWorkflow() {
        log.info("注册简单代码生成工作流到 Spring Context");
        CodeGenWorkflow workflow = new CodeGenWorkflow();
        return workflow.createWorkflow();
    }

    /**
     * 注册并发代码生成工作流
     */
    @Bean("concurrent-code-gen")
    public CompiledGraph<MessagesState<String>> concurrentCodeGenWorkflow() {
        log.info("注册并发代码生成工作流到 Spring Context");
        CodeGenConcurrentWorkflow workflow = new CodeGenConcurrentWorkflow();
        return workflow.createWorkflow();
    }
}
