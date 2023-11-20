package com.me.mseotsanyana.mande.application.interactors.programme.impact;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

/**
 *  This interactor is responsible for retrieving a set impacts
 *  from the database.
 */
public interface iReadImpactInteractor extends IInteractor {
    interface Callback {
        void onImpactModelsRetrieved(String logframeServerID, List<cTreeModel> impactTreeModels);
        void onImpactModelsFailed(String msg);
    }
}
