package com.me.mseotsanyana.mande.application.interactors.session.userprofile.Impl;

import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.iUserProfilesInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;

import java.util.List;

public class cReadUserProfilesInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iUserProfilesInteractor {
    //private static final String TAG = cReadUserProfilesInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final IUserProfileRepository userProfileRepository;

    public cReadUserProfilesInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                           ISessionManager sharedPreferenceRepository,
                                           IUserProfileRepository userProfileRepository,
                                           Callback callback) {
        super(threadExecutor, mainThread, null);

        if (sharedPreferenceRepository == null || userProfileRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.userProfileRepository = userProfileRepository;
        this.callback = callback;
    }

    /* */
    public void postError(String msg) {
        mainThread.post(() -> callback.onReadUserProfilesFailed(msg));
    }

    /* */
    private void postResult(List<CUserProfileModel> userProfileModels) {
        mainThread.post(() -> callback.onReadUserProfilesSucceeded(userProfileModels));
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void run() {
//        this.userProfileRepository.readAllUserProfilesBySingleValueEvent(
//                new CFirebaseCallBack() {
//                    @Override
//                    public void onFirebaseSuccess(Object object) {
//                        //postResult(object);
//                    }
//
//                    @Override
//                    public void onFirebaseFailure(Object object) {
//                        //postError(object);
//                    }
//                });
    }
}

//{
//
//@Override
//public void onReadUserProfilesSucceeded(
//        List<CUserProfileModel> userProfileModels) {
//        userProfilesMessage(userProfileModels);
//        }
//
//@Override
//public void onReadUserProfilesFailed(String msg) {
//        notifyError(msg);
//        }
//        });