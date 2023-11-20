package com.me.mseotsanyana.mande.application.interactors.session;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iUploadSessionInteractor extends IInteractor {
    /* implemented in PresenterImpl and called in InteractorImpl */
    interface Callback{
        void onUploadingSessionFailed(String msg);
        void onUploadingSessionSucceeded(String msg);
    }
}
