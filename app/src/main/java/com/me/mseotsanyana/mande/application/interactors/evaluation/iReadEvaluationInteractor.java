package com.me.mseotsanyana.mande.application.interactors.evaluation;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

/**
 *  This interactor is responsible for retrieving a set inputs
 *  from the database.
 */
public interface iReadEvaluationInteractor extends IInteractor {
    interface Callback {
        void onEvaluationModelsRetrieved(String logFrameName, ArrayList<cTreeModel> evaluationTreeModels);
        void onEvaluationModelsFailed(String msg);
    }
}
