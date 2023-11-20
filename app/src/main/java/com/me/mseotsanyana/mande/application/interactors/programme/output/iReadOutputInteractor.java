package com.me.mseotsanyana.mande.application.interactors.programme.output;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

/**
 *  This interactor is responsible for retrieving a set outputs
 *  from the database.
 */
public interface iReadOutputInteractor extends IInteractor {
    interface Callback {
        void onOutputModelsRetrieved(String logFrameName, ArrayList<cTreeModel> outputTreeModels);
        void onOutputModelsFailed(String msg);
    }
}
