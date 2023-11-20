package com.me.mseotsanyana.mande.application.interactors.shared;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iUploadGlobalInteractor extends IInteractor {
    interface Callback{
        void onUploadGlobalCompleted(String s);
    }
}
