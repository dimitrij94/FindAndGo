package com.example.neo_services;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dmitrij on 03.01.2016.
 */
@Component
@Scope("prototype")
public class MyExecutorService implements DisposableBean {

    private ExecutorService executor;

    public MyExecutorService() {
        this.executor = Executors.newWorkStealingPool();
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    @Override
    public void destroy() throws Exception {
        try {
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            if (!executor.isTerminated()) executor.shutdownNow();
        }
    }


}
