package com.me.mseotsanyana.mande.application.interactors.session.user;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iUserSignOutInteractor extends iInteractor {
    interface Callback{
        void onUserSignOutFailed(String msg);
        void onUserSignOutSucceeded(String msg);
    }
}
