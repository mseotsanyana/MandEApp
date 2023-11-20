package com.me.mseotsanyana.mande.infrastructure.ports.base;

import com.me.mseotsanyana.mande.infrastructure.ports.base.executor.iExecutor;
import com.me.mseotsanyana.mande.infrastructure.ports.base.executor.iMainThread;

public abstract class CAbstractController {
    protected iExecutor   executor;
    protected iMainThread mainThread;

    public CAbstractController(iExecutor executor, iMainThread mainThread) {
        this.executor = executor;
        this.mainThread = mainThread;
    }
}