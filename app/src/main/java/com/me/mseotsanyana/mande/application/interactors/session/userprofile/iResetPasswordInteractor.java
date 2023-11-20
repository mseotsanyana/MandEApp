package com.me.mseotsanyana.mande.application.interactors.session.userprofile;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iResetPasswordInteractor extends iInteractor {
    interface Callback {
        /* reset user profile use case */
        void onResetPasswordSucceeded(String msg);
        void onResetPasswordFailed(String msg);
    }
}
