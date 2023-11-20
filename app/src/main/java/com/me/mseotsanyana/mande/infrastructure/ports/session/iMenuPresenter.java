package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iMenuPresenter extends iPresenter {
    interface View extends IBaseView {
        void onReadMenuFailed(String msg);
        void onReadMenuSucceeded(List<cTreeModel> treeModels);
    }

    void readMenuItems();
}

