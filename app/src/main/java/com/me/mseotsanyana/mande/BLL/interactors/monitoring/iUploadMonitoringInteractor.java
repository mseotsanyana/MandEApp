package com.me.mseotsanyana.mande.BLL.interactors.monitoring;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iUploadMonitoringInteractor extends iInteractor {
    interface Callback{
        void onUploadMonitoringCompleted(String s);
    }
}
