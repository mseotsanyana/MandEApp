package com.me.mseotsanyana.mande.infrastructure.controllers.logframe;

import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.OLD.PL.ui.iBaseView;

public interface iUploadLogFramePresenter extends iPresenter {
    interface View extends iBaseView {

        void onUploadCompleted(String title, String msg);
    }

    void uploadLogFrameFromExcel();
}

