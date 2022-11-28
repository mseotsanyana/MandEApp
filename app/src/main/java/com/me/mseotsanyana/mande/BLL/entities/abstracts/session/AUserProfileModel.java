package com.me.mseotsanyana.mande.BLL.entities.abstracts.session;

import com.me.mseotsanyana.mande.BLL.entities.interfaces.IUserProfileState;
import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;

abstract public class AUserProfileModel implements IUserProfileState {
    protected CUserProfileModel userProfileModel;

    public AUserProfileModel(CUserProfileModel userProfileModel){
        this.userProfileModel = userProfileModel;
    }

    @Override
    public void activateEvent() {}

    @Override
    public void submitEvent() {}

    @Override
    public void signInEvent(String email, String password) {}

    @Override
    public void signUpEvent() {}

    @Override
    public void cancelEvent() {}

    @Override
    public void resetPasswordEvent() {}

    @Override
    public void changePasswordEvent() {}

    @Override
    public void logoutEvent() {}

    @Override
    abstract public String getState();
}
