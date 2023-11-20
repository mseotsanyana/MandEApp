package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iDashboardPresenter extends iPresenter {
    interface View extends iBaseView {
        /* pass data from interactor to the view */
        void onReadLogFramesSucceeded(List<cTreeModel> treeModels);
        void onReadLogFramesFailed(String msg);
    }

    /* pass data from view to the interactor */
    void readLogFrames();
}