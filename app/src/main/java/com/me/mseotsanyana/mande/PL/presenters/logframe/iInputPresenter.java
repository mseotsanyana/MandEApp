package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cInputModel;
import com.me.mseotsanyana.mande.BLL.entities.models.wpb.cExpenseModel;
import com.me.mseotsanyana.mande.BLL.entities.models.wpb.cHumanModel;
import com.me.mseotsanyana.mande.BLL.entities.models.wpb.cIncomeModel;
import com.me.mseotsanyana.mande.BLL.entities.models.wpb.cMaterialModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

import java.util.ArrayList;

public interface iInputPresenter extends iPresenter {
    interface View extends iBaseView {
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