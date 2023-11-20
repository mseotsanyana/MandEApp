package com.me.mseotsanyana.mande.application.interactors.programme.question;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cQuestionModel;

import java.util.ArrayList;

/**
 *  This interactor is responsible for retrieving a set outputs
 *  from the database.
 */
public interface iReadQuestionInteractor extends IInteractor {
    interface Callback {
        void onQuestionModelsRetrieved(String logFrameName, ArrayList<cQuestionModel> questionModels);
        void onQuestionModelsFailed(String msg);
    }
}
