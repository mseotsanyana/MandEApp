package com.me.mseotsanyana.mande.BLL.interactors.session.user;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iUpdateUserProfileInteractor extends iInteractor {
    interface Callback{
        void onUpdateUserProfileFailed(String msg);
        void onUpdateUserProfileRetrieved(String msg);
    }
}
