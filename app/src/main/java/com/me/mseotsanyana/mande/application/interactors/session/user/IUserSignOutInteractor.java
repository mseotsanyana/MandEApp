package com.me.mseotsanyana.mande.application.interactors.session.user;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface IUserSignOutInteractor extends IInteractor {
    interface Callback{
        void onUserSignOutFailed(String msg);
        void onUserSignOutSucceeded(String msg);
    }
}
