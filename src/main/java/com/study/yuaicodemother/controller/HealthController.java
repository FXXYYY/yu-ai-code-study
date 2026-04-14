package com.study.yuaicodemother.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fxy
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/")
    public String healthCheck() {
        return "ok";
    }
}
