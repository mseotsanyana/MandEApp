package com.me.mseotsanyana.mande.infrastructure.controllers.raid;

import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

public interface iUploadRAIDPresenter extends iPresenter {
    interface View extends IBaseView {

        void onUploadCompleted(String title, String msg);
    }

    void uploadRAIDFromExcel();
}

