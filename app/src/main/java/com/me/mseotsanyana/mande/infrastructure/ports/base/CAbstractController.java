package com.me.mseotsanyana.mande.infrastructure.ports.base;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;

public abstract class CAbstractController {
    protected IExecutor executor;
    protected IMainThread mainThread;
    protected ISessionManager sessionManager;

    public CAbstractController(IExecutor executor, IMainThread mainThread,
                               ISessionManager sessionManager) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.sessionManager = sessionManager;
    }
}