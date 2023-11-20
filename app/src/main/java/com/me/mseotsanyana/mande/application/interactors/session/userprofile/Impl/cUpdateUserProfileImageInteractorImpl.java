package com.me.mseotsanyana.mande.application.interactors.session.userprofile.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.iUserProfilesInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;

public class cUpdateUserProfileImageInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iUserProfilesInteractor {
    //private static final String TAG = cReadUserProfilesInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final IUserProfileRepository userProfileRepository;

    private final String userServerID;
    private final byte[] userProfileImageData;

    public cUpdateUserProfileImageInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                                 ISessionManager sharedPreferenceRepository,
                                                 IUserProfileRepository userProfileRepository,
                                                 Callback callback, String userServerID,
                                                 byte[] userProfileImageData) {
        super(threadExecutor, mainThread, null);

        if (sharedPreferenceRepository == null || userProfileRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.userProfileRepository = userProfileRepository;
        this.callback = callback;

        this.userServerID = userServerID;
        this.userProfileImageData = userProfileImageData;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onUpdateUserProfileImageFailed(msg));
    }

    /* */
    private void userProfilesMessage(String msg) {
        mainThread.post(() -> callback.onUpdateUserProfileImageSucceeded(msg));
    }

    @Override
    public void run() {
        this.userProfileRepository.updateUserProfileImage(userServerID, userProfileImageData,
                new IUserProfileRepository.iUpdateUserProfileImageRepositoryCallback() {
                    @Override
                    public void onUpdateUserProfileImageSucceeded(Object msg) {
                        userProfilesMessage(msg.toString());
                    }

                    @Override
                    public void onUpdateUserProfileImageFailed(Object msg) {
                        notifyError(msg.toString());
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