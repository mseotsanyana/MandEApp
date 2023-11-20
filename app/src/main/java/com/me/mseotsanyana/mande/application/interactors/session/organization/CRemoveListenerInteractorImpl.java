package com.me.mseotsanyana.mande.application.interactors.session.organization.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.iExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.iMainThread;
import com.me.mseotsanyana.mande.application.ports.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.mande.application.repository.session.iOrganizationRepository;

public class cRemoveListenerInteractorImpl extends cAbstractInteractor
        implements iOrganizationInteractor {
    private static String TAG = cRemoveListenerInteractorImpl.class.getSimpleName();
    private final iOrganizationRepository organizationRepository;
    private final Callback callback;

    public cRemoveListenerInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                         iOrganizationRepository organizationRepository,
                                         Callback callback) {
        super(threadExecutor, mainThread);

        if (organizationRepository != null && callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }
        this.organizationRepository = organizationRepository;
        this.callback = callback;

    }

    @Override
    public void run() {
        organizationRepository.removeListener();
    }
}
