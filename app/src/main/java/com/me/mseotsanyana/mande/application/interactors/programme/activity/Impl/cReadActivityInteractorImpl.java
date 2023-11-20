package com.me.mseotsanyana.mande.application.interactors.programme.activity.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.application.executor.iExecutor;
import com.me.mseotsanyana.mande.application.executor.iMainThread;
import com.me.mseotsanyana.mande.application.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.programme.activity.iReadActivityInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.application.repository.programme.iActivityRepository;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cActivityModel;
import com.me.mseotsanyana.mande.application.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.framework.storage.preference.cBitwise;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Set;

public class cReadActivityInteractorImpl extends cAbstractInteractor
        implements iReadActivityInteractor {
    private static String TAG = cReadActivityInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iActivityRepository activityRepository;
    private cLogFrameModel logFrameModel; String userID;
    private int primaryRoleBITS, secondaryRoleBITS, operationBITS, statusBITS;

    private String logFrameName;

    public cReadActivityInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                       iSharedPreferenceRepository sessionManagerRepository,
                                       iActivityRepository activityRepository,
                                       Callback callback, cLogFrameModel logFrameModel) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || activityRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.activityRepository = activityRepository;
        this.callback = callback;

        this.logFrameModel = logFrameModel;

//        /* common attributes */
//        this.userID = sessionManagerRepository.loadUserID();
//        this.primaryRoleBITS = sessionManagerRepository.loadPrimaryRoleBITS();
//        this.secondaryRoleBITS = sessionManagerRepository.loadSecondaryRoleBITS();
//
//        /* attributes related to ACTIVITY entity */
//        this.operationBITS = sessionManagerRepository.loadOperationBITS(
//                cBitwise.ACTIVITY, cBitwise.LOGFRAME_MODULE);
//        this.statusBITS = sessionManagerRepository.loadStatusBITS(
//                cBitwise.ACTIVITY, cBitwise.LOGFRAME_MODULE, cBitwise.READ);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onActivityModelsFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String logFrameName, ArrayList<cTreeModel> activityTreeModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onActivityModelsRetrieved(logFrameName, activityTreeModels);
            }
        });
    }

    private ArrayList<cTreeModel> getActivityTree(Set<cActivityModel> activityModelSet) {
        ArrayList<cTreeModel> activityTreeModels = new ArrayList<>();
        int parentIndex = 0, childIndex;

        ArrayList<cActivityModel> activityModels = new ArrayList<>(activityModelSet);
        if (activityModelSet.size() > 0) {
//            logFrameName = activityModels.get(0).getLogFrameModel().getName();
        }

        for (int i = 0; i < activityModels.size(); i++) {
//            /* activity model */
//            cActivityModel activityModel = activityModels.get(i);
//            activityTreeModels.add(new cTreeModel(parentIndex, -1, 0, activityModel));
//
//            /* set of activities children under the activity */
//            childIndex = parentIndex;
//            ArrayList<cActivityModel> activities = new ArrayList<>(activityModel.getChildActivityModelSet());
//            for (int j = 0; j < activities.size(); j++) {
//                childIndex = childIndex + 1;
//                activityTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, activities));
//            }
//
//            /* next parent index */
//            parentIndex = childIndex + 1;

//            /* set of output children under the sub-logframe of the activity logframe */
//            ArrayList<cOutputModel> outputs = new ArrayList<>(activityModel.getChildOutputModelSet());
//            if (outputs.size() > 0) {
//                childIndex = parentIndex + 1;
//                activityTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, outputs));
//            }
//
//            /* set of inputs under the activity */
//            ArrayList<cActivityModel> inputs = new ArrayList<>(activityModel.getChildActivityModelSet());
//            if (activities.size() > 0) {
//                childIndex = parentIndex + 3;
//                activityTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, inputs));
//            }
//
//            /* set of questions under the activity */
//            ArrayList<cQuestionModel> questions = new ArrayList<>(activityModel.getQuestionModelSet());
//            if (questions.size() > 0) {
//                childIndex = parentIndex + 4;
//                activityTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, questions));
//            }
//
//            /* set of raids under the activity */
//            ArrayList<cRaidModel> raids = new ArrayList<>(activityModel.getRaidModelSet());
//            if (raids.size() > 0) {
//                childIndex = parentIndex + 5;
//                activityTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, raids));
//            }

        }
        return activityTreeModels;
    }

    @Override
    public void run() {
        if ((operationBITS & cBitwise.READ) != 0) {

            /* retrieve a set logFrames from the database */
            Log.d(TAG, "LOGFRAME ID = " + logFrameModel.getProjectServerID() + "; USER ID = " + userID +
                    "; PRIMARY = " + primaryRoleBITS + "; SECONDARY = " + secondaryRoleBITS +
                    "; STATUS = " + statusBITS);

            Set<cActivityModel> activityModelSet = null;//activityRepository.getActivityModelSet(logFrameID,
            // userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);

            if (activityModelSet != null) {
                Gson gson = new Gson();
                Log.d(TAG, "ACTIVITY = " + gson.toJson(activityModelSet.size()));

                ArrayList<cTreeModel> activityTreeModels = getActivityTree(activityModelSet);

                postMessage(logFrameName, activityTreeModels);
            } else {
                notifyError("Failed to read activities !!");
            }
        } else {
            notifyError("Failed due to reading access rights !!");
        }
    }
}
