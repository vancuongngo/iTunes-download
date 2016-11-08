package com.cnv.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:local.properties")
public class AppConfig {
}
