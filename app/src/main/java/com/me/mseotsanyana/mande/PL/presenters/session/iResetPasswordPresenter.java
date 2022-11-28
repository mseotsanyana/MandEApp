package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iResetPasswordPresenter extends iPresenter {
    interface View extends iBaseView {
        void onResetPasswordSucceeded(String msg);
        void onResetPasswordFailed(String msg);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void sendPasswordResetEmail(CUserProfileModel userProfileModel);
}

