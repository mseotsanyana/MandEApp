package com.me.mseotsanyana.mande.application.interactors.raid;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iUploadRAIDInteractor extends IInteractor {
    interface Callback{
        void onUploadRAIDCompleted(String s);
    }
}
