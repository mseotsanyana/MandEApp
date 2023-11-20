package com.me.mseotsanyana.mande.application.interactors.session.workspace;

import android.util.Log;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreChildCallBack;
import com.me.mseotsanyana.mande.application.structures.CConstantModel;
import com.me.mseotsanyana.mande.application.structures.CResponseModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IWorkspaceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CReadWorkspacesInteractorImpl extends CAbstractInteractor implements IInteractor {
    private static final String TAG = CReadWorkspacesInteractorImpl.class.getSimpleName();

    private final IPresenter<Map<String, Object>> iPresenter;
    private final IWorkspaceRepository workspaceRepository;

    private final String userServerID;
    private final String organizationServerID, filterOrganizationID;
    private final int primaryWorkspaceBIT;
    private final List<Integer> secondaryWorkspaceBITS;
    private final List<Integer> statusBITS;

    private int position;

    private final int entityBITS;
    private final int entitypermBITS;

    public CReadWorkspacesInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                         ISessionManager sessionManager,
                                         IPresenter<Map<String, Object>> iPresenter,
                                         IWorkspaceRepository workspaceRepository,
                                         String organizationServerID) {

        super(threadExecutor, mainThread, sessionManager);

        if (iPresenter == null || workspaceRepository == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.iPresenter = iPresenter;
        this.workspaceRepository = workspaceRepository;

        this.filterOrganizationID = organizationServerID;

        // load user shared preferences
        this.userServerID = null;//sharedPreferenceRepository.loadUserID();
        this.organizationServerID = null;//sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryWorkspaceBIT = 0;//sharedPreferenceRepository.loadActiveWorkspaceBIT();
        this.secondaryWorkspaceBITS = null;//sharedPreferenceRepository.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = 0;//sharedPreferenceRepository.loadEntityBITS(CPreferenceConstant.SESSION_MODULE);
        this.entitypermBITS = 0;//sharedPreferenceRepository.loadEntityPermissionBITS(CPreferenceConstant.SESSION_MODULE, CPreferenceConstant.ORGANIZATION);
        this.statusBITS = null;//sharedPreferenceRepository.loadOperationStatuses(CPreferenceConstant.SESSION_MODULE, CPreferenceConstant.ORGANIZATION, CPreferenceConstant.READ);

        Log.d(TAG, " \n ORGANIZATION ID = " + this.filterOrganizationID );
//                " \n USER ID = " + this.userServerID +
//                " \n PRIMARY WORKSPACE BIT = " + this.primaryWorkspaceBIT +
//                " \n SECONDARY WORKSPACE BITS = " + this.secondaryWorkspaceBITS +
//                " \n ENTITY BITS = " + this.entityBITS +
//                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
//                " \n OPERATION STATUSES = " + this.statusBITS);
    }

//    /* */
//    private void notifyError(String msg) {
//    }
//
//    /* */
//    private void postWorkspace(CResponseModel responseModel) {
//
//    }

    @Override
    public void postResult(Map<String, Object> resultMap) {
        mainThread.post(() -> {
            iPresenter.onSuccess(resultMap);
        });
    }

    @Override
    public void postError(String errorMessage) {
        mainThread.post(() -> iPresenter.onError(new CGeneralException(errorMessage)));
    }

    @Override
    public void run() {
        this.workspaceRepository.readWorkspaces(filterOrganizationID, userServerID,
                primaryWorkspaceBIT, secondaryWorkspaceBITS, statusBITS,
                new CFirestoreChildCallBack() {
            @Override
            public void onChildAdded(Object object) {
                if (object != null) {
                    Map<String, Object> resultMap = new HashMap<>();

                    CResponseModel responseModel;
                    responseModel = new CResponseModel(CConstantModel.ADDED, object);
                    resultMap.put(CConstantModel.WORKSPACE, responseModel);
                    postResult(resultMap);
                } else {
                    postError("No workspace found!");
                }
            }

            @Override
            public void onChildChanged(Object object) {
                if (object != null) {
                    Map<String, Object> resultMap = new HashMap<>();

                    CResponseModel responseModel;
                    responseModel = new CResponseModel(CConstantModel.MODIFIED, object);
                    resultMap.put(CConstantModel.WORKSPACE, responseModel);
                    postResult(resultMap);
                } else {
                    postError("No workspace found!");
                }
            }

            @Override
            public void onChildRemoved(Object object) {
                if (object != null) {
                    Map<String, Object> resultMap = new HashMap<>();

                    CResponseModel responseModel;
                    responseModel = new CResponseModel(CConstantModel.DELETED, object);
                    resultMap.put(CConstantModel.WORKSPACE, responseModel);
                    postResult(resultMap);
                } else {
                    postError("No workspace found!");
                }
            }

            @Override
            public void onCancelled(Object object) {
                postError(object.toString());
            }
        });
    }
}
//        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
//                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
//            if ((this.entityBITS & CSharedPreference.ORGANIZATION) != 0) {
//                if ((this.entitypermBITS & CSharedPreference.READ) != 0) {



//                    this.workspaceRepository.readWorkspaces(organizationServerID, userServerID,
//                            primaryWorkspaceBIT, secondaryWorkspaceBITS, statusBITS,
//                            new IWorkspaceRepository.IReadWorkspacesCallback() {
//                                @Override
//                                public void onReadTeamsSucceeded(List<CWorkspaceModel> teamModels) {
//                                    //teamsMessage(teamModels);
//                                }
//
//                                @Override
//                                public void onReadTeamsFailed(String msg) {
//                                    notifyError(msg);
//                                }
//                            });
//
//                } else {
//                    notifyError("Permission denied! Please contact your administrator");
//                }
//            } else {
//                notifyError("No access to the entity! Please contact your administrator");
//            }
//        } else {
//            notifyError("Error in default settings");
//        }