package com.me.mseotsanyana.mande.application.interactors.session.user.Impl;

import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.user.iReadUserProfileInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;

public class cReadUserProfileInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iReadUserProfileInteractor {
    private static String TAG = cReadUserProfileInteractorImpl.class.getSimpleName();

    private Callback callback;
    private ISessionManager preferenceRepository;
    private final IUserProfileRepository userProfileRepository;

    private String userServerID;

    public cReadUserProfileInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                          ISessionManager preferenceRepository,
                                          IUserProfileRepository userProfileRepository,
                                          Callback callback) {
        super(threadExecutor, mainThread, null);

        if (preferenceRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.preferenceRepository = preferenceRepository;
        this.userProfileRepository = userProfileRepository;
        this.callback = callback;
    }

    /* */
    public void postError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadUserProfileFailed(msg);
            }
        });
    }

    /* */
    private void postResult(CUserProfileModel userProfileModel) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadUserProfileRetrieved(userProfileModel);
            }
        });
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void run() {
        userProfileRepository.readUserProfileByID(userServerID, new CFirestoreCallBack() {
            @Override
            public void onFirebaseSuccess(Object object) {
                postResult((CUserProfileModel) object);
            }

            @Override
            public void onFirebaseFailure(Object object) {
                postError((String) object);
            }
        });
    }
}

//{
//@Override
//public void onReadMyUserProfileSucceeded(CUserProfileModel userProfileModel) {
//        postMessage(userProfileModel);
//        }
//
//@Override
//public void onReadMyUserProfileFailed(String msg) {
//        notifyError(msg);
//        }
//        });