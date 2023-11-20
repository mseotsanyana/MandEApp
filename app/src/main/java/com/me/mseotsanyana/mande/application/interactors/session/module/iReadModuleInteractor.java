package com.me.mseotsanyana.mande.application.interactors.session.module;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iReadModuleInteractor extends iInteractor {
    interface Callback {
        void onReadModuleFailed(String msg);
        void onReadModuleSucceeded(List<cTreeModel> treeModels);
    }
}
