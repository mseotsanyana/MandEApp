package com.me.mseotsanyana.mande.infrastructure.ports.logframe;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

import java.util.ArrayList;

public interface iQuestionPresenter extends iPresenter {
    interface View extends IBaseView {
        /* pass data from presenter to the view */
        void onClickDetailQuestion(cQuestionModel questionModel);
        void onClickUpdateQuestion(cQuestionModel questionModel, int position);
        void onClickDeleteQuestion(long questionID, int position);
        void onClickSyncQuestion(cQuestionModel questionModel);

        /* pass data from interactor to the view */
        void onQuestionModelsRetrieved(String logFrameName, ArrayList<cQuestionModel> questionModels);
        void onQuestionModelsFailed(String msg);
    }

    /* pass data from view to the interactor */
    void readQuestionModels(long logFrameID);

    /*
    void createQuestionModel(cQuestionModel questionModel);
    void updateQuestionModel(cQuestionModel questionModel, int position);
    void deleteQuestionModel(long questionID, int position);
    void syncQuestionModel(cQuestionModel questionModel);
     */
}