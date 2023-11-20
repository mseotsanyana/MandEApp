package com.me.mseotsanyana.mande.application.interactors.session.status;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iStatusInteractor extends iInteractor {
    interface Callback{
        void onStatusFailed(String msg);
        void onStatusSucceeded(String msg);
    }
}
