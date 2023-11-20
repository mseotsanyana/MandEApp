package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

public interface iMyUserProfilePresenter extends iPresenter {
    interface View extends IBaseView {
        void onReadUserProfileFailed(String msg);
        void onReadUserProfileSucceeded(CUserProfileModel userProfileModel);

        void onUpdateUserProfileFailed(String msg);
        void onUpdateUserProfileSucceeded(String msg);
    }

    void readUserProfile();
    void updateUserProfile(CUserProfileModel userProfileModel);
}

