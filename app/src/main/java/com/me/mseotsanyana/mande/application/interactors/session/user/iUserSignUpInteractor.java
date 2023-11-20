package com.me.mseotsanyana.mande.application.interactors.session.user;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iUserSignUpInteractor extends IInteractor {
    interface Callback{
        void onUserSignUpFailed(String msg);
        void onUserSignUpSucceeded(String msg);
    }
}
