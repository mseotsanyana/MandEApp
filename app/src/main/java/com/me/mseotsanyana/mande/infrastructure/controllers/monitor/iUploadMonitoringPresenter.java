package com.me.mseotsanyana.mande.infrastructure.controllers.monitor;

import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

public interface iUploadMonitoringPresenter extends iPresenter {
    interface View extends IBaseView {

        void onUploadCompleted(String title, String msg);
    }

    void uploadMonitoringFromExcel();
}

