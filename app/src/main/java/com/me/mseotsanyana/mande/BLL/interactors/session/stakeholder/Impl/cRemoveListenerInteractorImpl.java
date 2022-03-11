package com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.iStakeholderInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cStakeholderModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iStakeholderRepository;

import java.util.ArrayList;

public class cRemoveListenerInteractorImpl extends cAbstractInteractor
        implements iStakeholderInteractor {
    private static String TAG = cRemoveListenerInteractorImpl.class.getSimpleName();
    private final iStakeholderRepository organizationRepository;
    private final Callback callback;

    public cRemoveListenerInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                         iStakeholderRepository organizationRepository,
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
                callback.onReadSharedOrgsFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(ArrayList<cStakeholderModel> organizationModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSharedOrgsRetrieved(organizationModels);
            }
        });
    }


    @Override
    public void run() {
        organizationRepository.removeListener();
    }
}
