package com.test.appplication;

import com.test.library.CommonExecutor;
import com.test.library.Executor;
import com.test.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TODO: measure time with AOP instead of (dont change service, library, main not required to change, add new component to TestConf)
@Slf4j
public class Main {
    public static void main(String[] args) {
//        Executor executor = () -> {
//            log.info("Anonymous class");
//            System.out.println(args);
//        };
//        ExecutorWrapper wrapper = new ExecutorWrapper(executor);
//        MyService myService = new MyService(wrapper);
//        myService.doServiceWork();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        context.start();
        context.getBean(MyService.class).doServiceWork();
        context.stop();
    }
}
