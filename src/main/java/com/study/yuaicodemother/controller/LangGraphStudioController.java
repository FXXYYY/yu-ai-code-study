package com.study.yuaicodemother.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * LangGraph4j Studio 页面控制器
 */
@RestController
@RequestMapping("/langgraph4j")
public class LangGraphStudioController {

    /**
     * 提供 Studio HTML 页面
     */
    @GetMapping("/studio")
    public ResponseEntity<Resource> getStudioPage() throws IOException {
        Resource resource = new ClassPathResource("static/langgraph4j/studio.html");
        
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(resource);
    }
}
