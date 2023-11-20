package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AUserProfileModel;

public class CChangePasswordModel extends AUserProfileModel {

    public CChangePasswordModel(CUserProfileModel userProfileModel) {
        super(userProfileModel);
    }

    @Override
    public void submitEvent() {
        userProfileModel.setActiveState(new CSignInModel(userProfileModel));
    }

    @Override
    public void cancelEvent() {
        userProfileModel.setActiveState(new CSignInModel(userProfileModel));
    }

    @Override
    public String getState() {
        return "ChangePassword State";
    }
}
