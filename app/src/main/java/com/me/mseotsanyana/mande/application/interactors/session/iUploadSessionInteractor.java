package com.me.mseotsanyana.mande.application.interactors.session;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iUploadSessionInteractor extends iInteractor {
    /* implemented in PresenterImpl and called in InteractorImpl */
    interface Callback{
        void onUploadingSessionFailed(String msg);
        void onUploadingSessionSucceeded(String msg);
    }
}
