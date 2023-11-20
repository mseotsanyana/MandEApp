package com.me.mseotsanyana.mande.application.interactors.wpb;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iUploadAWPBInteractor extends IInteractor {
    interface Callback{
        void onUploadAWPBCompleted(String msg);
    }
}
