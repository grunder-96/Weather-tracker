package org.edu.pet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class TestWebConfig {

    @Bean
    public MockMvcTester mockMvcTester(WebApplicationContext context) {
        return MockMvcTester.from(context);
    }
}