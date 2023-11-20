package com.me.mseotsanyana.mande.application.ports.base;


import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;

public abstract class CAbstractInteractor<R> implements IInteractor {
    protected IExecutor threadExecutor;
    protected IMainThread mainThread;
    protected ISessionManager sessionManager;

    protected volatile boolean isCanceled;
    protected volatile boolean isRunning;

    public CAbstractInteractor(IExecutor threadExecutor, IMainThread mainThread,
                               ISessionManager sessionManager) {
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.sessionManager = sessionManager;
    }

    /**
     * This method contains the actual business logic of the interactor.
     * It SHOULD NOT BE USED DIRECTLY but, instead, a developer should call
     * the execute() method of an interactor to make sure the operation is
     * done on a background thread.
     * <p/>
     * This method should only be called directly while doing unit/integration
     * tests. That is the only reason it is declared public as to help with
     * easier testing.
     */
    public abstract void run();
    public abstract void postResult(R resultMap);
    public abstract void postError(String errorMessage);

    public void cancel() {
        isCanceled = true;
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void onFinished() {
        isRunning = false;
        isCanceled = false;
    }

    public void execute() {
         // mark this interactor as running
        this.isRunning = true;

        // start running this interactor in a background thread
        threadExecutor.execute(this);
    }
}
