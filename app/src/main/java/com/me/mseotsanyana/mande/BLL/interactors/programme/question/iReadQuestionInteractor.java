package com.me.mseotsanyana.mande.BLL.interactors.programme.question;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cQuestionModel;

import java.util.ArrayList;

/**
 *  This interactor is responsible for retrieving a set outputs
 *  from the database.
 */
public interface iReadQuestionInteractor extends iInteractor {
    interface Callback {
        void onQuestionModelsRetrieved(String logFrameName, ArrayList<cQuestionModel> questionModels);
        void onQuestionModelsFailed(String msg);
    }
}
