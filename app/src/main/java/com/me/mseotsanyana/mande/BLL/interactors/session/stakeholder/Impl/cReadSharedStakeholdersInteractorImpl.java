package com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.iStakeholderInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cStakeholderModel;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;

import java.util.ArrayList;
import java.util.Set;

public class cReadSharedStakeholdersInteractorImpl extends cAbstractInteractor
        implements iStakeholderInteractor {
    private static String TAG = cReadSharedStakeholdersInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iSharedPreferenceRepository sessionManagerRepository;

    public cReadSharedStakeholdersInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                                 iSharedPreferenceRepository sessionManagerRepository,
                                                 Callback callback) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sessionManagerRepository = sessionManagerRepository;
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
        Set<cStakeholderModel> organizationModels = null;//sessionManagerRepository.loadOrganizationOwners();

        if (organizationModels != null) {
                postMessage(new ArrayList<cStakeholderModel>(organizationModels));
        } else {
            notifyError("Failed to load shared organization preferences !!");
        }
    }
}
