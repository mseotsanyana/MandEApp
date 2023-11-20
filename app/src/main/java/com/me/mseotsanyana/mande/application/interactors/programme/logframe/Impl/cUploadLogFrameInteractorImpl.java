package com.me.mseotsanyana.mande.application.interactors.programme.logframe.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.iLogFrameInteractor;
import com.me.mseotsanyana.mande.application.repository.programme.iLogFrameRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;

public class cUploadLogFrameInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iLogFrameInteractor {

    private static final String TAG = cUploadLogFrameInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iLogFrameRepository logFrameRepository;


    // permission data
    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final int statusBIT;

    private final int entityBITS;
    private final int entitypermBITS;

    private final String filePath;

    public cUploadLogFrameInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                         ISessionManager sharedPreferenceRepository,
                                         iLogFrameRepository logFrameRepository,
                                         Callback callback, String filePath) {
        super(threadExecutor, mainThread, null);

        if (sharedPreferenceRepository == null || logFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.logFrameRepository = logFrameRepository;

        this.filePath = filePath;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadLoggedInUserServerID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        //this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryTeams();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                CPreferenceConstant.PROGRAMME_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                CPreferenceConstant.PROGRAMME_MODULE, CPreferenceConstant.LOGFRAME);
        this.statusBIT = CPreferenceConstant.ACTIVE;

        Log.d(TAG, " \n USER ID = " + this.userServerID +
                " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUS = " + this.statusBIT);

    }

    /* notify on the main thread */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onUploadLogFrameFailed(msg));
    }

    /* notify on the main thread */
    private void postMessage(String msg) {
        mainThread.post(() -> callback.onUploadLogFrameCompleted(msg));
    }

    @Override
    public void run() {
        if ((this.entityBITS & CPreferenceConstant.LOGFRAME) != 0) {
            if ((this.entitypermBITS & CPreferenceConstant.CREATE) != 0) {
                this.logFrameRepository.upLoadProgrammeFromExcel(organizationServerID, userServerID,
                        primaryTeamBIT, statusBIT, filePath,
                        new iLogFrameRepository.iUploadLogFrameCallback() {
                            @Override
                            public void onUploadLogFrameSucceeded(String msg) {
                                postMessage(msg);
                            }

                            @Override
                            public void onUploadLogFrameFailed(String msg) {
                                notifyError(msg);
                            }
                        });
            } else {
                notifyError("Permission denied! Please contact your administrator");
            }
        } else {
            notifyError("No access to the entity! Please contact your administrator");
        }
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}
        /* delete all logframe module records
        uploadLMRepository.deleteLogFrame();

        uploadLMRepository.deleteLogFrameTree();

        uploadLMRepository.deleteResourceTypes();

        uploadLMRepository.deleteComponents();
        uploadLMRepository.deleteImpacts();
        uploadLMRepository.deleteOutcomeImpacts();
        uploadLMRepository.deleteOutcomes();
        uploadLMRepository.deleteOutputOutcomes();
        uploadLMRepository.deleteOutputs();

        uploadLMRepository.deleteActivities();
        uploadLMRepository.deletePrecedingActivities();
        uploadLMRepository.deleteActivityAssignments();
        uploadLMRepository.deleteActivityOutputs();

        uploadLMRepository.deleteInputs();
        uploadLMRepository.deleteInputActivities();
        uploadLMRepository.deleteHumans();
        uploadLMRepository.deleteHumanSets();
        uploadLMRepository.deleteMaterials();
        uploadLMRepository.deleteIncomes();
        uploadLMRepository.deleteFunds();
        uploadLMRepository.deleteExpenses();

        uploadLMRepository.deleteCriteria();
        uploadLMRepository.deleteQuestionGroupings();
        uploadLMRepository.deleteQuestionTypes();
        uploadLMRepository.deleteQuestions();
        uploadLMRepository.deleteQuestionCriteria();

        uploadLMRepository.deletePrimitiveQuestions();
        uploadLMRepository.deleteArrayQuestions();
        uploadLMRepository.deleteMatrixQuestions();

        uploadLMRepository.deleteRaidCategories();
        uploadLMRepository.deleteRaids();
        uploadLMRepository.deleteComponentRaids();*/

        /* upload all logframe module records
        if (uploadLMRepository.addLogFrameFromExcel()) {
            postMessage("LogFrame Entity Added Successfully!");
        } else {
            notifyError("Failed to Add LogFrame Entity");
        }

        if (uploadLMRepository.addResourceTypeFromExcel()) {
            postMessage("ResourceType Entity Added Successfully!");
        } else {
            notifyError("Failed to Add ResourceType Entity");
        }

        if (uploadLMRepository.addComponentFromExcel()) {
            postMessage("Component Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Component Entity");
        }

        if (uploadLMRepository.addCriteriaFromExcel()) {
            postMessage("Criteria Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Criteria Entity");
        }
        if (uploadLMRepository.addQuestionGroupingFromExcel()) {
            postMessage("QuestionGrouping Entity Added Successfully!");
        } else {
            notifyError("Failed to Add QuestionGrouping Entity");
        }
        if (uploadLMRepository.addQuestionTypeFromExcel()) {
            postMessage("QuestionType Entity Added Successfully!");
        } else {
            notifyError("Failed to Add QuestionType Entity");
        }
        if (uploadLMRepository.addQuestionFromExcel()) {
            postMessage("Question Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Question Entity");
        }

        if (uploadLMRepository.addRaidCategoryFromExcel()) {
            postMessage("Raid Category Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Raid Category Entity");
        }
        if (uploadLMRepository.addRaidFromExcel()) {
            postMessage("Raid Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Raid Entity");
        }*/