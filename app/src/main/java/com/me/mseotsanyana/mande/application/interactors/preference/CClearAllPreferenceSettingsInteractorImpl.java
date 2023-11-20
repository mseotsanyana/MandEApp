package com.me.mseotsanyana.mande.application.interactors.session.organization;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.ports.session.IOrganizationInteractor;
import com.me.mseotsanyana.mande.application.repository.session.IOrganizationRepository;

public class CRemoveListenerInteractorImpl extends cAbstractInteractor
        implements IOrganizationInteractor {
    private static String TAG = CRemoveListenerInteractorImpl.class.getSimpleName();
    private final IOrganizationRepository organizationRepository;
    private final IOrganizationPresenter IOrganizationPresenter;

    public CRemoveListenerInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                         IOrganizationRepository organizationRepository,
                                         IOrganizationPresenter IOrganizationPresenter) {
        super(threadExecutor, mainThread);

        if (organizationRepository != null && IOrganizationPresenter == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }
        this.organizationRepository = organizationRepository;
        this.IOrganizationPresenter = IOrganizationPresenter;

    }

    @Override
    public void run() {
        organizationRepository.removeListener();
    }
}
