package com.me.mseotsanyana.mande.application.interactors.session.userprofile.Impl;

import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.iResetPasswordInteractor;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;

public class cResetPasswordInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>> implements iResetPasswordInteractor {
    //private static String TAG = cResetPasswordInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final IUserProfileRepository userProfileRepository;
    private final CUserProfileModel userProfileModel;

    public cResetPasswordInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                        Callback callback,
                                        IUserProfileRepository userProfileRepository,
                                        CUserProfileModel userProfileModel) {
        super(threadExecutor, mainThread, null);

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
                new IUserProfileRepository.iResetPasswordRepositoryCallback() {
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

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}