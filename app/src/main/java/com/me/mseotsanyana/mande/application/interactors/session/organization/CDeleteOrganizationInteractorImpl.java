package com.me.mseotsanyana.mande.application.interactors.session.organization;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IOrganizationRepository;
import com.me.mseotsanyana.mande.application.structures.CResponseDTO;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.application.structures.enums.EAction;

public class CDeleteOrganizationInteractorImpl extends
        CAbstractInteractor<IResponseDTO<Object>> implements IInteractor {

    //private static String TAG = CCreateOrganizationInteractorImpl.class.getSimpleName();

    private final IPresenter<IResponseDTO<Object>> iPresenter;
    private final IOrganizationRepository organizationRepository;
    private final String organizationServerID;

    public CDeleteOrganizationInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                             ISessionManager sessionManager,
                                             IPresenter<IResponseDTO<Object>> iPresenter,
                                             IOrganizationRepository organizationRepository,
                                             String organizationServerID) {

        super(threadExecutor, mainThread, sessionManager);

        if (organizationRepository == null || iPresenter == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.iPresenter = iPresenter;
        this.organizationRepository = organizationRepository;
        this.organizationServerID = organizationServerID;
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
        /* create a new organization object and insert it */
        organizationRepository.deleteOrganization(organizationServerID, new CFirestoreCallBack() {
            @Override
            public void onFirebaseSuccess(Object object) {
                IResponseDTO<Object> responseModel;
                responseModel = new CResponseDTO<>(EAction.Deleted_ORGANIZATION, object);
                postResult(responseModel);
            }
            @Override
            public void onFirebaseFailure(Object object) {
                postError((String) object);
            }
        });
    }
}

