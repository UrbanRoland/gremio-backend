package com.gremio.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class EmailPropertyConfig {

    @Value("${forgot.password.prefix}")
    private String forgotPasswordPrefix;
}