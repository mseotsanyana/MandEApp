package com.me.mseotsanyana.mande.infrastructure.ports.logframe;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cInputModel;
import com.me.mseotsanyana.mande.domain.entities.models.wpb.cExpenseModel;
import com.me.mseotsanyana.mande.domain.entities.models.wpb.cHumanModel;
import com.me.mseotsanyana.mande.domain.entities.models.wpb.cIncomeModel;
import com.me.mseotsanyana.mande.domain.entities.models.wpb.cMaterialModel;
import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

import java.util.ArrayList;

public interface iInputPresenter extends iPresenter {
    interface View extends IBaseView {
        /* pass data from presenter to the view */
        void onClickBMBInput(int menuIndex);
        void onClickCreateInput(cInputModel inputModel);
        void onClickUpdateInput(cInputModel inputModel, int position);
        void onClickDeleteInput(long outputID, int position);
        void onClickSyncInput(cInputModel inputModel);

        /* pass data from interactor to the view */
        void onInputModelsRetrieved(ArrayList<cHumanModel> humanModels,
                                    ArrayList<cMaterialModel> materialModels,
                                    ArrayList<cIncomeModel> incomeModels,
                                    ArrayList<cExpenseModel> expenseModels);
        void onInputModelsFailed(String msg);
    }

    /* pass data from view to the interactor */
    void readInputModels(long logFrameID);

    /*
    void createInputModel(cInputModel inputModel);
    void updateInputModel(cInputModel inputModel, int position);
    void deleteInputModel(long inputID, int position);
    void syncInputModel(cInputModel inputModel);
     */
}