package com.me.mseotsanyana.mande.OLD.PL.presenters.common;

import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.OLD.PL.ui.iBaseView;

public interface iUploadGlobalPresenter extends iPresenter {
    interface View extends iBaseView {

        void onUploadCompleted(String title, String msg);
    }

    void uploadGlobalFromExcel();
}

