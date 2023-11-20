package com.me.mseotsanyana.mande.application.interactors.session.menu;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iReadMenuInteractor extends IInteractor {
    interface Callback {
        void onReadMenuFailed(String msg);
        void onReadMenuSucceeded(List<cTreeModel> treeModels);
    }
}
