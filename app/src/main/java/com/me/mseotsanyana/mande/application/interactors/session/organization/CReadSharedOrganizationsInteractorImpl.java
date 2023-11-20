package com.me.mseotsanyana.mande.application.interactors.session.organization;

import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.ports.session.IOrganizationInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;

import java.util.ArrayList;
import java.util.Set;

public class CReadSharedOrganizationsInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements IOrganizationInteractor {
    private static String TAG = CReadSharedOrganizationsInteractorImpl.class.getSimpleName();

    private IOrganizationPresenter IOrganizationPresenter;
    private ISessionManager sessionManagerRepository;

    public CReadSharedOrganizationsInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                                  ISessionManager sessionManagerRepository,
                                                  IOrganizationPresenter IOrganizationPresenter) {
        super(threadExecutor, mainThread, null);

        if (sessionManagerRepository == null || IOrganizationPresenter == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sessionManagerRepository = sessionManagerRepository;
        this.IOrganizationPresenter = IOrganizationPresenter;

    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                //callback.onReadSharedOrgsFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(ArrayList<COrganizationModel> organizationModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                //callback.onSharedOrgsRetrieved(organizationModels);
            }
        });
    }


    @Override
    public void run() {
        Set<COrganizationModel> organizationModels = null;//sessionManagerRepository.loadOrganizationOwners();

        if (organizationModels != null) {
                postMessage(new ArrayList<COrganizationModel>(organizationModels));
        } else {
            notifyError("Failed to load shared organization preferences !!");
        }
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}
