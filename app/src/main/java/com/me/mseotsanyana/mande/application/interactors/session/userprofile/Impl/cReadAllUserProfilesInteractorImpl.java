package com.me.mseotsanyana.mande.application.interactors.session.userprofile.Impl;

import com.google.firebase.firestore.ListenerRegistration;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.iUserProfilesInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreChildCallBack;

import java.util.List;

public class cReadAllUserProfilesInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iUserProfilesInteractor {
    //private static final String TAG = cReadUserProfilesInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final IUserProfileRepository userProfileRepository;
    private ListenerRegistration listenerRegistration;

    public cReadAllUserProfilesInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
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
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadUserProfilesFailed(msg));
    }

    /* */
    private void userProfilesMessage(List<CUserProfileModel> userProfileModels) {
        mainThread.post(() -> callback.onReadUserProfilesSucceeded(userProfileModels));
    }

    @Override
    public void run() {
        listenerRegistration = userProfileRepository.readAllUserProfilesByChildEvent(
                new CFirestoreChildCallBack() {
            @Override
            public void onChildAdded(Object object) {
                if(object != null){
                    CUserProfileModel userProfileModel = (CUserProfileModel) object;

                }
            }

            @Override
            public void onChildChanged(Object object) {

            }

            @Override
            public void onChildRemoved(Object object) {

            }

            @Override
            public void onCancelled(Object object) {

            }
        });


//        this.userProfileRepository.readUserProfiles(
//                new iUserProfileRepository.iReadUserProfilesRepositoryCallback() {
//
//                    @Override
//                    public void onReadUserProfilesSucceeded(
//                            List<cUserProfileModel> userProfileModels) {
//                        userProfilesMessage(userProfileModels);
//                    }
//
//                    @Override
//                    public void onReadUserProfilesFailed(String msg) {
//                        notifyError(msg);
//                    }
//                });
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}