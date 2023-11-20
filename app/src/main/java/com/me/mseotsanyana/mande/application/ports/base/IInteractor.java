package com.me.mseotsanyana.mande.application.ports.base;

import com.me.mseotsanyana.mande.application.structures.IResponseDTO;

public interface IInteractor {
    interface IPresenter<R> {
        void onSuccess(R response);
        void onError(Throwable throwable);
    }

    /**
     * This is the main method that starts an interactor. It will make sure that the
     * interactor operation is done on a background thread.
     */
    void execute();
}
