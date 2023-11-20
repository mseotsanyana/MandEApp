package com.me.mseotsanyana.mande.application.interactors.shared;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iUploadGlobalInteractor extends iInteractor {
    interface Callback{
        void onUploadGlobalCompleted(String s);
    }
}
