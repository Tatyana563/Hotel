package com.test.appplication;

import com.test.library.Executor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ExecutorWrapper implements Executor {
    private final Executor executor;

    @Override
    public void doAction() {
        long start = System.currentTimeMillis();
        executor.doAction();
        long end = System.currentTimeMillis();
        log.info("Execution time : {} ms",end-start);
    }
}
