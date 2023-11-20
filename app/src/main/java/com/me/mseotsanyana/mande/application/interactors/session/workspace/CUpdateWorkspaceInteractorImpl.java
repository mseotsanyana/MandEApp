package com.me.mseotsanyana.mande.application.interactors.session.workspace;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IWorkspaceRepository;
import com.me.mseotsanyana.mande.application.structures.CConstantModel;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.application.structures.CResponseModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;

import java.util.HashMap;
import java.util.Map;

public class CCreateWorkspaceInteractorImpl extends CAbstractInteractor implements IInteractor {
    //private static final String TAG = CCreateWorkspaceInteractorImpl.class.getSimpleName();

    private final IPresenter<Map<String, Object>> iPresenter;
    private final IWorkspaceRepository workspaceRepository;

    private final String organizationServerID;
    private final int workspaceBITS;
    private final CWorkspaceModel workspaceModel;

    public CCreateWorkspaceInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                          ISessionManager sessionManager,
                                          IPresenter<Map<String, Object>> iPresenter,
                                          IWorkspaceRepository workspaceRepository,
                                          String organizationServerID, int workspaceBITS,
                                          CWorkspaceModel workspaceModel) {

        super(threadExecutor, mainThread, sessionManager);

        if (iPresenter == null || workspaceRepository == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.iPresenter = iPresenter;
        this.workspaceRepository = workspaceRepository;

        this.organizationServerID = organizationServerID;
        this.workspaceBITS = workspaceBITS;
        this.workspaceModel = workspaceModel;

        // load user shared preferences
        this.workspaceModel.setUserOwnerID(sessionManager.loadLoggedInUserServerID());
        this.workspaceModel.setOrganizationOwnerID(sessionManager.loadActiveOrganizationID());
        this.workspaceModel.setWorkspaceOwnerBIT(sessionManager.loadActiveWorkspaceBIT());
        this.workspaceModel.setUnixpermBITS(sessionManager.loadUnixPermissionBITS(
                CPreferenceConstant.SESSION, CPreferenceConstant.WORKSPACE));
        this.workspaceModel.setStatusBIT(CPreferenceConstant.CREATE);
    }

    @Override
    public void postResult(Map<String, Object> resultMap) {
        mainThread.post(() -> iPresenter.onSuccess(resultMap));
    }

    @Override
    public void postError(String errorMessage) {
        mainThread.post(() -> iPresenter.onError(new CGeneralException(errorMessage)));
    }

    @Override
    public void run() {
        this.workspaceRepository.createWorkspace(organizationServerID, workspaceBITS,
                workspaceModel, new CFirestoreCallBack() {
                    @Override
                    public void onFirebaseSuccess(Object object) {
                        if (object != null) {
                            Map<String, Object> resultMap = new HashMap<>();
                            CResponseModel responseModel;
                            responseModel = new CResponseModel(CConstantModel.CREATED,
                                    "Successfully created workspace.");
                            resultMap.put(CConstantModel.WORKSPACE, responseModel);
                            postResult(resultMap);
                        } else {
                            postError("No workspace found!");
                        }
//                        Map<String, Object> map = new HashMap<>();
//                        map.put(CConstantModel.CREATED, "Successfully created workspace.");
//                        postResult(map);
                    }

                    @Override
                    public void onFirebaseFailure(Object object) {
                        postError("Failed to create workspace!");
                    }
                });
    }
}