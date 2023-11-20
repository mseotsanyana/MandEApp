package com.me.mseotsanyana.mande.application.interactors.session.status;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iStatusInteractor extends IInteractor {
    interface Callback{
        void onStatusFailed(String msg);
        void onStatusSucceeded(String msg);
    }
}
