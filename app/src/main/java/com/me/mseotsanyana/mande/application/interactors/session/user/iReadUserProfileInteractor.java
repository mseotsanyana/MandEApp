package com.me.mseotsanyana.mande.application.interactors.session.user;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iReadUserProfileInteractor extends IInteractor {
    interface Callback{
        void onReadUserProfileFailed(String msg);
        void onReadUserProfileRetrieved(CUserProfileModel userProfileModel);
    }
}
