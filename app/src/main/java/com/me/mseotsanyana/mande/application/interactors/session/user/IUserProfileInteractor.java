package com.me.mseotsanyana.mande.application.interactors.session.user;

import com.me.mseotsanyana.mande.application.ports.base.iInteractor;

public interface iUserLoginInteractor extends iInteractor {
    interface Callback{
        void onUserLoginFailed(String msg);
        void onUserLoginSucceeded(String msg);
    }
}
