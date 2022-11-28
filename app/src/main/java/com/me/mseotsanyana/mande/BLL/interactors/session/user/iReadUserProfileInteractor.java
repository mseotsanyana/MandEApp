package com.me.mseotsanyana.mande.BLL.interactors.session.user;

import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iReadUserProfileInteractor extends iInteractor {
    interface Callback{
        void onReadUserProfileFailed(String msg);
        void onReadUserProfileRetrieved(CUserProfileModel userProfileModel);
    }
}
