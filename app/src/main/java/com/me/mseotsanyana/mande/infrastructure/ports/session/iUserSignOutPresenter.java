package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

public interface iUserSignOutPresenter extends iPresenter {
    /* implemented in either Fragments and/or Activities. Called in PresenterImpl */
    interface View extends IBaseView {
        void onUserSignOutFailed(String msg);
        void onUserSignOutSucceeded(String msg);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void signOutWithEmailAndPassword();
}

