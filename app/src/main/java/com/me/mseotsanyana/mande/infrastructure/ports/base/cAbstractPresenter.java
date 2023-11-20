package com.me.mseotsanyana.mande.OLD.PL.presenters.base;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;

public abstract class cAbstractPresenter {
    protected IExecutor executor;
    protected IMainThread mainThread;
    protected ISessionManager sessionManager;

    public cAbstractPresenter(IExecutor executor, IMainThread mainThread,
                              ISessionManager sessionManager) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.sessionManager = sessionManager;
    }
}