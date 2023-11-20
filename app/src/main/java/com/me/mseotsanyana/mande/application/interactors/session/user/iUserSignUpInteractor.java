package com.me.mseotsanyana.mande.application.interactors.session.user;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iUserSignUpInteractor extends iInteractor {
    interface Callback{
        void onUserSignUpFailed(String msg);
        void onUserSignUpSucceeded(String msg);
    }
}
