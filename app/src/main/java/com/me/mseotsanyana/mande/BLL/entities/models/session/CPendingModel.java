package com.me.mseotsanyana.mande.BLL.entities.models.session;

import com.me.mseotsanyana.mande.BLL.entities.abstracts.session.AUserProfileModel;

public class CPendingModel extends AUserProfileModel {

    public CPendingModel(CUserProfileModel userProfileModel) {
        super(userProfileModel);
    }

    @Override
    public void activateEvent() {
        userProfileModel.setActiveState(new CActivatedProfileModel(userProfileModel));
    }

    @Override
    public String getState() {
        return "Pending State";
    }
}