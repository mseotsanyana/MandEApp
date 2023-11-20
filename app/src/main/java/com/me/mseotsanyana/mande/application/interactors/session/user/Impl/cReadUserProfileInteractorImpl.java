package com.me.mseotsanyana.mande.application.interactors.session.user.Impl;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.executor.iExecutor;
import com.me.mseotsanyana.mande.application.executor.iMainThread;
import com.me.mseotsanyana.mande.application.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.user.iReadUserProfileInteractor;
import com.me.mseotsanyana.mande.application.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.application.repository.session.iUserProfileRepository;

public class cReadUserProfileInteractorImpl extends cAbstractInteractor
        implements iReadUserProfileInteractor {
    private static String TAG = cReadUserProfileInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iSharedPreferenceRepository preferenceRepository;
    private final iUserProfileRepository userProfileRepository;

    public cReadUserProfileInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                          iSharedPreferenceRepository preferenceRepository,
                                          iUserProfileRepository userProfileRepository,
                                          Callback callback) {
        super(threadExecutor, mainThread);

        if (preferenceRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.preferenceRepository = preferenceRepository;
        this.userProfileRepository = userProfileRepository;
        this.callback = callback;

    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadUserProfileFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(CUserProfileModel userProfileModel) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadUserProfileRetrieved(userProfileModel);
            }
        });
    }


    @Override
    public void run() {
        userProfileRepository.readMyUserProfile(
                new iUserProfileRepository.iReadMyUserProfileRepositoryCallback() {
                    @Override
                    public void onReadMyUserProfileSucceeded(CUserProfileModel userProfileModel) {
                        postMessage(userProfileModel);
                    }

                    @Override
                    public void onReadMyUserProfileFailed(String msg) {
                        notifyError(msg);
                    }
                });
    }
}
