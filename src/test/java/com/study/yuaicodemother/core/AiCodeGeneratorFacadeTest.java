package com.study.yuaicodemother.core;

import com.study.yuaicodemother.module.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.List;

/**
 * @author fxy
 * @date 2026/4/20
 */
@SpringBootTest
public class AiCodeGeneratorFacadeTest {

    @Resource
    private AiCodeGeneratorFacade aiCodeGeneratorFacade;

    @Test
    public void generateAndSaveCode() {
        File file = aiCodeGeneratorFacade.generateAndSaveCode("你好，世界🌍。不超过30个字符", CodeGenTypeEnum.HTML);
        Assertions.assertNotNull(file);
    }
    @Test
    public void generateAndSaveCodeStream() {
        Flux<String> fileFlux = aiCodeGeneratorFacade.generateAndSaveCodeStream("你好，世界🌍。不超过30个字符", CodeGenTypeEnum.MULTI_FILE, -1L);
        List<String> result = fileFlux.collectList().block();
        Assertions.assertNotNull(result);
    }

    @Test
    void generateVueProjectCodeStream() {
        Flux<String> codeStream = aiCodeGeneratorFacade.generateAndSaveCodeStream(
                "简单的任务记录网站，总代码量不超过 200 行",
                CodeGenTypeEnum.VUE_PROJECT, 1L);
        // 阻塞等待所有数据收集完成
        List<String> result = codeStream.collectList().block();
        // 验证结果
        Assertions.assertNotNull(result);
        String completeContent = String.join("", result);
        Assertions.assertNotNull(completeContent);
    }

}