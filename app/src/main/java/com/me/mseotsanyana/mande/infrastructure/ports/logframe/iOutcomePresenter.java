package com.me.mseotsanyana.mande.infrastructure.ports.logframe;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

public interface iOutcomePresenter extends iPresenter {
    interface View extends IBaseView {
        /* pass data from presenter to the view */
        void onClickBMBOutcome(int menuIndex);
        void onClickCreateOutcome(cOutcomeModel outcomeModel);
        void onClickUpdateOutcome(cOutcomeModel outcomeModel, int position);
        void onClickDeleteOutcome(long outcomeID, int position);
        void onClickSyncOutcome(cOutcomeModel outcomeModel);
        //void onClickDetailOutcome(cOutcomeModel[] outcomeModels);

        /* pass data from interactor to the view */
        void onOutcomeModelsRetrieved(String logFrameName, ArrayList<cTreeModel> outcomeModelSet);
        void onOutcomeModelsFailed(String msg);
    }

    /* pass data from view to the interactor */
    void readOutcomeModels(long logFrameID);

    /*
    void createOutcomeModel(cOutcomeModel outcomeModel);
    void updateOutcomeModel(cOutcomeModel outcomeModel, int position);
    void deleteOutcomeModel(long outcomeID, int position);
    void syncOutcomeModel(cOutcomeModel outcomeModel);
     */
}