package com.me.mseotsanyana.mande.BLL.entities.models.session;

import com.me.mseotsanyana.mande.BLL.entities.abstracts.session.AUserProfileModel;

public class CSignInModel extends AUserProfileModel {
    int loginCount = 0;

    public CSignInModel(CUserProfileModel userProfileModel){
        super(userProfileModel);
    }

    @Override
    public void submitEvent() {
        loginCount= loginCount + 1;
        boolean isPending = (userProfileModel.getActiveState() instanceof CPendingModel);
        if (!(isPending) && (loginCount <= 3)) {
            userProfileModel.setActiveState(new CActivatedProfileModel(userProfileModel));
        }else if (loginCount > 3) {
            userProfileModel.setActiveState(new CBlockedProfileModel(userProfileModel));
        }
    }

    @Override
    public void signUpEvent() {
        userProfileModel.setActiveState(new CSignUpModel(userProfileModel));
    }

    @Override
    public void resetPasswordEvent() {
        userProfileModel.setActiveState(new CResetPasswordModel(userProfileModel));
    }

    @Override
    public void changePasswordEvent() {
        userProfileModel.setActiveState(new CChangePasswordModel(userProfileModel));
    }

    @Override
    public String getState() {
        return "SignIn State";
    }
}
