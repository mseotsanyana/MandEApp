package com.me.mseotsanyana.mande.application.interactors.session.user;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iUpdateUserProfileInteractor extends IInteractor {
    interface Callback{
        void onUpdateUserProfileFailed(String msg);
        void onUpdateUserProfileRetrieved(String msg);
    }
}
