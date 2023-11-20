package com.me.mseotsanyana.mande.application.interactors.session.userprofile.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.iUserProfilesInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;

public class cUploadUserProfilesInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iUserProfilesInteractor {

    //private static final String TAG = cUploadUserProfilesInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final IUserProfileRepository userProfileRepository;

    private final String filename;
    public cUploadUserProfilesInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                             ISessionManager sharedPreferenceRepository,
                                             IUserProfileRepository userProfileRepository,
                                             Callback callback, String filename) {
        super(threadExecutor, mainThread, null);

        if (sharedPreferenceRepository == null || userProfileRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.userProfileRepository = userProfileRepository;
        this.filename = filename;
    }

    /* notify on the main thread */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onUploadUserProfilesFailed(msg));
    }

    /* notify on the main thread */
    private void postMessage(String msg) {
        mainThread.post(() -> callback.onUploadUserProfilesSucceeded(msg));
    }

    @Override
    public void run() {
        this.userProfileRepository.uploadUserProfilesFromExcel(filename,
                new IUserProfileRepository.iUploadUserProfilesRepositoryCallback() {
                    @Override
                    public void onUploadUserProfilesSucceeded(String msg) {
                        postMessage(msg);
                    }

                    @Override
                    public void onUploadUserProfilesFailed(String msg) {
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