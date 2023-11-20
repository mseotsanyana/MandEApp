package com.me.mseotsanyana.mande.application.interactors.session.user;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iReadUserProfileInteractor extends iInteractor {
    interface Callback{
        void onReadUserProfileFailed(String msg);
        void onReadUserProfileRetrieved(CUserProfileModel userProfileModel);
    }
}
