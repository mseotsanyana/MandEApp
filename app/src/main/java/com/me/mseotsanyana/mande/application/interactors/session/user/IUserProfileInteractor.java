package com.me.mseotsanyana.mande.application.interactors.session.user;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface IUserProfileInteractor extends IInteractor {
    interface IUserProfilePresenter extends IPresenter {
        void OnUserLoginSucceeded(String msg);
        void OnUserSignOutSucceeded(String msg);
    }
}
