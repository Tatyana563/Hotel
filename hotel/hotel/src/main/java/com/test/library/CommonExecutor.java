package com.test.library;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class CommonExecutor implements Executor{

    @Override
    public void doAction()  {
        log.info("Doing some action");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
