package com.me.mseotsanyana.mande.application.interactors.monitoring;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iUploadMonitoringInteractor extends IInteractor {
    interface Callback{
        void onUploadMonitoringCompleted(String s);
    }
}
