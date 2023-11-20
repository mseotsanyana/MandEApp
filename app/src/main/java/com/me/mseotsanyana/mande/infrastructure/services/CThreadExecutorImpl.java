package com.me.mseotsanyana.mande.infrastructure.services;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CThreadExecutorImpl implements IExecutor {
    // This is a singleton
    private static volatile CThreadExecutorImpl threadExecutor;

    private static final int                     CORE_POOL_SIZE  = 5;//3;
    private static final int                     MAX_POOL_SIZE   = 10;//5;
    private static final int                     KEEP_ALIVE_TIME = 120;
    private static final TimeUnit                TIME_UNIT       = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE      = new LinkedBlockingQueue<>();

    private final ThreadPoolExecutor threadPoolExecutor;

    private CThreadExecutorImpl() {
        threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                WORK_QUEUE);
    }

    @Override
    public void execute(final CAbstractInteractor interactor) {
        threadPoolExecutor.submit(() -> {
            // run the main logic
            interactor.run();

            // mark it as finished
            interactor.onFinished();
        });
    }

    /**
     * Returns a singleton instance of this executor. If the executor is not
     * initialized then it initializes it and returns the instance.
     */
    public static IExecutor getInstance() {
        if (threadExecutor == null) {
            threadExecutor = new CThreadExecutorImpl();
        }
        return threadExecutor;
    }
}
