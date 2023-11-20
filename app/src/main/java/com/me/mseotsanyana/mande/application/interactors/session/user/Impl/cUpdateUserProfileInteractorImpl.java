package com.me.mseotsanyana.mande.application.interactors.session.user.Impl;

import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.user.iUpdateUserProfileInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;

public class cUpdateUserProfileInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iUpdateUserProfileInteractor {
    private static String TAG = cUpdateUserProfileInteractorImpl.class.getSimpleName();

    private Callback callback;
    private ISessionManager sessionManagerRepository;
    private IUserProfileRepository userProfileRepository;
    private CUserProfileModel userProfileModel;

    public cUpdateUserProfileInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                            ISessionManager sessionManagerRepository,
                                            IUserProfileRepository userProfileRepository,
                                            Callback callback, CUserProfileModel userProfileModel) {
        super(threadExecutor, mainThread, null);

        if (sessionManagerRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sessionManagerRepository = sessionManagerRepository;
        this.userProfileRepository = userProfileRepository;
        this.callback = callback;
        this.userProfileModel = userProfileModel;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUpdateUserProfileFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUpdateUserProfileRetrieved(msg);
            }
        });
    }


    @Override
    public void run() {
        userProfileRepository.updateUserProfileImage(0, 0, 0, 0,
                userProfileModel, new IUserProfileRepository.iUpdateUserProfileRepositoryCallback() {
                    @Override
                    public void onUpdateUserProfileSucceeded(String msg) {
                        postMessage(msg);
                    }

                    @Override
                    public void onUpdateUserProfileFailed(String msg) {
                        notifyError(msg);
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
