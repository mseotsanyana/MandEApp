package com.me.mseotsanyana.mande.application.interactors.monitoring;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iUploadMonitoringInteractor extends iInteractor {
    interface Callback{
        void onUploadMonitoringCompleted(String s);
    }
}
