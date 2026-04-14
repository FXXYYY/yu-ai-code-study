package com.study.yuaicodemother;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class YuAiCodeMotherApplication {

	public static void main(String[] args) {
		SpringApplication.run(YuAiCodeMotherApplication.class, args);
	}

}
