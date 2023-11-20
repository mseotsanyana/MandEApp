package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AUserProfileModel;

public class CSignUpModel extends AUserProfileModel {

    public CSignUpModel(CUserProfileModel userProfileModel) {
        super(userProfileModel);
    }

    @Override
    public void submitEvent() {
        userProfileModel.setActiveState(new CPendingModel(userProfileModel));
    }

    @Override
    public void signInEvent(String email, String password) {
        // action
        userProfileModel.setEmail(email);
        userProfileModel.setPassword(password);
        // make a transition
        userProfileModel.setActiveState(new CSignInModel(userProfileModel));
    }

    @Override
    public String getState() {
        return "SignU State";
    }
}
