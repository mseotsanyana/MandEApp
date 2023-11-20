package com.me.mseotsanyana.mande.application.interactors.wpb;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iUploadAWPBInteractor extends iInteractor {
    interface Callback{
        void onUploadAWPBCompleted(String msg);
    }
}
