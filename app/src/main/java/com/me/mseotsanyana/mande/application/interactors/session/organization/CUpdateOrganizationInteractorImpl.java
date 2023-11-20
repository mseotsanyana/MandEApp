package com.me.mseotsanyana.mande.application.interactors.session.organization;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IOrganizationRepository;
import com.me.mseotsanyana.mande.application.repository.session.IWorkspaceRepository;
import com.me.mseotsanyana.mande.application.structures.CResponseDTO;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.application.structures.enums.EAction;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;

public class CUpdateOrganizationInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements IInteractor {
    private static final String TAG = CUpdateOrganizationInteractorImpl.class.getSimpleName();
    private final IPresenter<IResponseDTO<Object>> iPresenter;
    private final IOrganizationRepository organizationRepository;
    private final COrganizationModel organizationModel;

    public CUpdateOrganizationInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                             ISessionManager sessionManager,
                                             IPresenter<IResponseDTO<Object>> iPresenter,
                                             IOrganizationRepository organizationRepository,
                                             COrganizationModel organizationModel) {

        super(threadExecutor, mainThread, sessionManager);

        if (iPresenter == null || organizationRepository == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.iPresenter = iPresenter;
        this.organizationRepository = organizationRepository;
        this.organizationModel = organizationModel;

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
        this.organizationRepository.updateOrganization(organizationModel, new CFirestoreCallBack() {
            @Override
            public void onFirebaseSuccess(Object object) {
                if (object != null) {
                    IResponseDTO<Object> responseModel;
                    responseModel = new CResponseDTO<>(EAction.Modified_ORGANIZATION,
                            "Successfully updated organization.");
                    postResult(responseModel);
                } else {
                    postError("No organization found!");
                }
            }

            @Override
            public void onFirebaseFailure(Object object) {
                postError("Failed to update organization!");
            }
        });
    }
}