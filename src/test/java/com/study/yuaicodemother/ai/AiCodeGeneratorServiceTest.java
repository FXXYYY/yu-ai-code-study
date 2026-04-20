package com.study.yuaicodemother.ai;

import com.study.yuaicodemother.ai.model.HtmlCodeResult;
import com.study.yuaicodemother.ai.model.MultiFileCodeResult;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author fxy
 * @date 2026/4/20
 */
@SpringBootTest
public class AiCodeGeneratorServiceTest {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    @Test
    public void generateHtmlCode() {
        HtmlCodeResult htmls = aiCodeGeneratorService.generateHtmlCode("生成一个HTML页面，内容是“Hello World”");
        Assertions.assertNotNull(htmls);
    }

    @Test
    public void generateMultiFileCode() {
        MultiFileCodeResult htmlPages = aiCodeGeneratorService.generateMultiFileCode("生成一个HTML页面，内容是“Hello World”");
        Assertions.assertNotNull(htmlPages);
    }
}