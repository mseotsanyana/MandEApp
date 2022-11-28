package com.me.mseotsanyana.mande.BLL.interactors.session.userprofile;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iResetPasswordInteractor extends iInteractor {
    interface Callback {
        /* reset user profile use case */
        void onResetPasswordSucceeded(String msg);
        void onResetPasswordFailed(String msg);
    }
}
