package com.gsd.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.gsd.**.mapper")
public class ApplicationConfig {
}
