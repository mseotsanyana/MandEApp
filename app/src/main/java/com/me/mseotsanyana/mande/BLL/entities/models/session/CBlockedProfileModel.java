package com.me.mseotsanyana.mande.BLL.entities.models.session;

import com.me.mseotsanyana.mande.BLL.entities.abstracts.session.AUserProfileModel;

public class CBlockedProfileModel extends AUserProfileModel {

    public CBlockedProfileModel(CUserProfileModel userProfileModel) {
        super(userProfileModel);
    }

    @Override
    public void resetPasswordEvent() {
        userProfileModel.setActiveState(new CSignInModel(userProfileModel));
    }

    @Override
    public String getState() {
        return "BlockedProfile State";
    }
}
