package com.me.mseotsanyana.mande.application.interactors.session.menu;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iReadMenuInteractor extends iInteractor {
    interface Callback {
        void onReadMenuFailed(String msg);
        void onReadMenuSucceeded(List<cTreeModel> treeModels);
    }
}
