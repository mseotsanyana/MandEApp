package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iDashboardPresenter extends iPresenter {
    interface View extends IBaseView {
        /* pass data from interactor to the view */
        void onReadLogFramesSucceeded(List<cTreeModel> treeModels);
        void onReadLogFramesFailed(String msg);
    }

    /* pass data from view to the interactor */
    void readLogFrames();
}