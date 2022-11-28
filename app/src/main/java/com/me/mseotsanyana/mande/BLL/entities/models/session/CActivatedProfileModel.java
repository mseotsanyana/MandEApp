package com.me.mseotsanyana.mande.BLL.entities.models.session;

import com.me.mseotsanyana.mande.BLL.entities.abstracts.session.AUserProfileModel;

public class CActivatedProfileModel extends AUserProfileModel {

    public CActivatedProfileModel(CUserProfileModel userProfileModel) {
        super(userProfileModel);
    }

    @Override
    public void logoutEvent() {
        userProfileModel.setActiveState(new CSignInModel(userProfileModel));
    }

    @Override
    public String getState() {
        return "ActivatedProfile State";
    }


}