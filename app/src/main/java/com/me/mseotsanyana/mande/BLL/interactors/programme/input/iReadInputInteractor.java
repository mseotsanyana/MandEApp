package com.me.mseotsanyana.mande.BLL.interactors.programme.input;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.entities.models.wpb.cExpenseModel;
import com.me.mseotsanyana.mande.BLL.entities.models.wpb.cHumanModel;
import com.me.mseotsanyana.mande.BLL.entities.models.wpb.cIncomeModel;
import com.me.mseotsanyana.mande.BLL.entities.models.wpb.cMaterialModel;

import java.util.ArrayList;

/**
 *  This interactor is responsible for retrieving a set inputs
 *  from the database.
 */
public interface iReadInputInteractor extends iInteractor {
    interface Callback {
        void onInputModelsRetrieved(ArrayList<cHumanModel> humanModels,
                                    ArrayList<cMaterialModel> materialModels,
                                    ArrayList<cIncomeModel> incomeModels,
                                    ArrayList<cExpenseModel> expenseModels);
        void onInputModelsFailed(String msg);
    }
}
