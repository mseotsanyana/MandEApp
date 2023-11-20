package com.me.mseotsanyana.mande.application.interactors.raid;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iUploadRAIDInteractor extends iInteractor {
    interface Callback{
        void onUploadRAIDCompleted(String s);
    }
}
