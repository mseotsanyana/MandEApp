package com.me.mseotsanyana.mande.application.interactors.session.userprofile;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iResetPasswordInteractor extends IInteractor {
    interface Callback {
        /* reset user profile use case */
        void onResetPasswordSucceeded(String msg);
        void onResetPasswordFailed(String msg);
    }
}
