package com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;

import java.util.ArrayList;

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
    private void postMessage(ArrayList<cOrganizationModel> organizationModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                //callback.onSharedOrgsRetrieved(organizationModels);
            }
        });
    }


    @Override
    public void run() {
        organizationRepository.removeListener();
    }
}
