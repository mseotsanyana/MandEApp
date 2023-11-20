package com.me.mseotsanyana.mande.application.ports.base.executor;

import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;

public interface IExecutor {
    /**
     * This method should call the interactor's run method and thus start the
     * interactor. This should be called on a background thread as interactors
     * might do lengthy operations.
     *
     * @param interactor The interactor to run.
     */
    void execute(final CAbstractInteractor interactor);
}
