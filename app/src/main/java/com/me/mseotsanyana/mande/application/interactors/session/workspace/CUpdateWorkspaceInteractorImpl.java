package com.me.mseotsanyana.mande.application.interactors.session.workspace;

import android.util.Log;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IWorkspaceRepository;
import com.me.mseotsanyana.mande.application.structures.CResponseDTO;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.application.structures.enums.EAction;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;

import java.util.Map;

public class CUpdateWorkspaceInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements IInteractor {

    private static final String TAG = CUpdateWorkspaceInteractorImpl.class.getSimpleName();

    private final IPresenter<IResponseDTO<Object>> iPresenter;
    private final IWorkspaceRepository workspaceRepository;

    CWorkspaceModel workspaceModel;

    public CUpdateWorkspaceInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                          ISessionManager sessionManager,
                                          IPresenter<IResponseDTO<Object>> iPresenter,
                                          IWorkspaceRepository workspaceRepository,
                                          CWorkspaceModel workspaceModel) {

        super(threadExecutor, mainThread, sessionManager);

        if (iPresenter == null || workspaceRepository == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.iPresenter = iPresenter;
        this.workspaceRepository = workspaceRepository;
        this.workspaceModel = workspaceModel;

        // load user shared preferences
//        this.workspaceModel.setUserOwnerID(sessionManager.loadLoggedInUserServerID());
//        this.workspaceModel.setOrganizationOwnerID(sessionManager.loadActiveOrganizationID());
//        this.workspaceModel.setWorkspaceOwnerBIT(sessionManager.loadActiveWorkspaceBIT());
//        this.workspaceModel.setUnixpermBITS(sessionManager.loadUnixPermissionBITS(
//                CPreferenceConstant.SESSION, CPreferenceConstant.WORKSPACE));
//        this.workspaceModel.setStatusBIT(CPreferenceConstant.CREATE);
    }

    @Override
    public void postResult(IResponseDTO<Object> resultMap) {
        mainThread.post(() -> iPresenter.onSuccess(resultMap));
    }

    @Override
    public void postError(String errorMessage) {
        mainThread.post(() -> iPresenter.onError(new CGeneralException(errorMessage)));
    }

    @Override
    public void run() {
        this.workspaceRepository.updateWorkspace(workspaceModel, new CFirestoreCallBack() {
            @Override
            public void onFirebaseSuccess(Object object) {
                if (object != null) {
                    IResponseDTO<Object> responseModel;
                    responseModel = new CResponseDTO<>(EAction.Modified_WORKSPACE,
                            "Successfully updated workspace.");
                    postResult(responseModel);
                } else {
                    postError("No workspace found!");
                }
            }

            @Override
            public void onFirebaseFailure(Object object) {
                postError("Failed to update workspace!");
            }
        });
    }
}