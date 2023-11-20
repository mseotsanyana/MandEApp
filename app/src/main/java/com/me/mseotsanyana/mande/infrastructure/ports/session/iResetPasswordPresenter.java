package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

public interface iResetPasswordPresenter extends iPresenter {
    interface View extends IBaseView {
        void onResetPasswordSucceeded(String msg);
        void onResetPasswordFailed(String msg);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void sendPasswordResetEmail(CUserProfileModel userProfileModel);
}

