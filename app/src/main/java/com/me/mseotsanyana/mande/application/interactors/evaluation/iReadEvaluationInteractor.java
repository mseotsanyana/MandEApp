package com.me.mseotsanyana.mande.application.interactors.evaluation;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

/**
 *  This interactor is responsible for retrieving a set inputs
 *  from the database.
 */
public interface iReadEvaluationInteractor extends iInteractor {
    interface Callback {
        void onEvaluationModelsRetrieved(String logFrameName, ArrayList<cTreeModel> evaluationTreeModels);
        void onEvaluationModelsFailed(String msg);
    }
}
