package com.me.mseotsanyana.mande.infrastructure.controllers.evaluator;

import com.me.mseotsanyana.mande.domain.entities.models.evaluation.cEvaluationModel;
import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

public interface iEvaluationPresenter extends iPresenter {
    interface View extends IBaseView {
        /* pass data from presenter to the view */
        void onEvaluationSelected(cEvaluationModel evaluationModel);

        /* void onClickBMBOutput(int menuIndex);
        void onClickCreateOutput(cOutputModel outputModel);
        void onClickUpdateOutput(cOutputModel outputModel, int position);
        void onClickDeleteOutput(long outputID, int position);
        void onClickSyncOutput(cOutputModel outputModel);*/

        /* pass data from interactor to the view */
        void onEvaluationModelsRetrieved(String logFrameName,
                                         ArrayList<cTreeModel> evaluationModelSet);
        void onEvaluationModelsFailed(String msg);
    }

    /* pass data from view to the interactor */
    void readEvaluationModels(long logFrameID);

    /*
    void createOutputModel(cOutputModel outputModel);
    void updateOutputModel(cOutputModel outputModel, int position);
    void deleteOutputModel(long outputID, int position);
    void syncOutputModel(cOutputModel outputModel);
     */
}
