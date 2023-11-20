package com.me.mseotsanyana.mande.application.interactors.programme.activity;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

/**
 *  This interactor is responsible for retrieving a set activities
 *  from the database.
 */
public interface iReadActivityInteractor extends IInteractor {
    interface Callback {
        void onActivityModelsRetrieved(String logFrameName, ArrayList<cTreeModel> activityTreeModels);
        void onActivityModelsFailed(String msg);
    }
}
