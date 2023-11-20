package com.me.mseotsanyana.mande.application.interactors.session.organization.Impl;

import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.iExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.iMainThread;
import com.me.mseotsanyana.mande.application.ports.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.mande.application.preference.iSharedPreferenceRepository;

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
}
