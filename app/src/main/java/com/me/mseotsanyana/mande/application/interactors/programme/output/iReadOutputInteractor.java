package com.me.mseotsanyana.mande.application.interactors.programme.output;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

/**
 *  This interactor is responsible for retrieving a set outputs
 *  from the database.
 */
public interface iReadOutputInteractor extends iInteractor {
    interface Callback {
        void onOutputModelsRetrieved(String logFrameName, ArrayList<cTreeModel> outputTreeModels);
        void onOutputModelsFailed(String msg);
    }
}
