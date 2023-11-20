package com.me.mseotsanyana.mande.application.interactors.programme.outcome;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

/**
 *  This interactor is responsible for retrieving a set impacts
 *  from the database.
 */
public interface iReadOutcomeInteractor extends IInteractor {
    interface Callback {
        void onOutcomeModelsRetrieved(String logFrameName, ArrayList<cTreeModel> outcomeTreeModels);
        void onOutcomeModelsFailed(String msg);
    }
}
