package com.test.appplication;

import com.test.library.CommonExecutor;
import com.test.library.Executor;
import com.test.service.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {
    @Bean
    Executor executor() {
        return new CommonExecutor();
    }

    @Bean
    MyService myService(Executor executor) {
        return new MyService(executor);
    }
}
