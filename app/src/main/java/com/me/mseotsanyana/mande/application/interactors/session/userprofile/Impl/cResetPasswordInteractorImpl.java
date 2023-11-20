package com.me.mseotsanyana.mande.application.interactors.session.userprofile.Impl;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.executor.iExecutor;
import com.me.mseotsanyana.mande.application.executor.iMainThread;
import com.me.mseotsanyana.mande.application.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.iResetPasswordInteractor;
import com.me.mseotsanyana.mande.application.repository.session.iUserProfileRepository;

public class cResetPasswordInteractorImpl extends cAbstractInteractor implements iResetPasswordInteractor {
    //private static String TAG = cResetPasswordInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iUserProfileRepository userProfileRepository;
    private final CUserProfileModel userProfileModel;

    public cResetPasswordInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                        Callback callback,
                                        iUserProfileRepository userProfileRepository,
                                        CUserProfileModel userProfileModel) {
        super(threadExecutor, mainThread);

        if (userProfileRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.userProfileRepository = userProfileRepository;
        this.userProfileModel = userProfileModel;
    }

    /* callback to the presenter */
    private void postFailedMessage(String msg) {
        mainThread.post(() -> callback.onResetPasswordFailed(msg));
    }

    /* callback to the presenter */
    private void postSucceededMessage(String msg) {
        mainThread.post(() -> callback.onResetPasswordSucceeded(msg));
    }

    /* sign up a new user */
    @Override
    public void run() {
        this.userProfileRepository.sendPasswordResetEmail(userProfileModel,
                new iUserProfileRepository.iResetPasswordRepositoryCallback() {
                    @Override
                    public void onResetPasswordSucceeded(String msg) {
                        postSucceededMessage(msg);
                    }

                    @Override
                    public void onResetPasswordFailed(String msg) {
                        postFailedMessage(msg);
                    }
                });
    }
}