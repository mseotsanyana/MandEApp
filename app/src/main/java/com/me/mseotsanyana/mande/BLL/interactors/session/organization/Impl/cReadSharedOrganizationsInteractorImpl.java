package com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl;

import com.me.mseotsanyana.mande.BLL.entities.models.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;

import java.util.ArrayList;
import java.util.Set;

public class cReadSharedOrganizationsInteractorImpl extends cAbstractInteractor
        implements iOrganizationInteractor {
    private static String TAG = cReadSharedOrganizationsInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iSharedPreferenceRepository sessionManagerRepository;

    public cReadSharedOrganizationsInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
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
        Set<cOrganizationModel> organizationModels = null;//sessionManagerRepository.loadOrganizationOwners();

        if (organizationModels != null) {
                postMessage(new ArrayList<cOrganizationModel>(organizationModels));
        } else {
            notifyError("Failed to load shared organization preferences !!");
        }
    }
}
