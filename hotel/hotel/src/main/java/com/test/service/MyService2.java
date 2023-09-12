package com.test.service;

import com.test.library.Executor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MyService2 {
    private final Executor executor;

    public void doServiceWork(){
        log.info("Starting Executor");

        executor.doAction();
    }
}
