package com.me.mseotsanyana.mande.application.interactors.session.user.Impl;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirebaseCallBack;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;
import com.me.mseotsanyana.mande.application.structures.CConstantModel;

import java.util.HashMap;
import java.util.Map;

public class CUserSignOutInteractorImpl extends CAbstractInteractor implements IInteractor {
    //private static String TAG = cUserSignOutInteractorImpl.class.getSimpleName();

    private final IPresenter<Map<String, Object>> iPresenter;
    private final IUserProfileRepository userProfileRepository;

    public CUserSignOutInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                      ISessionManager sessionManager,
                                      IPresenter<Map<String, Object>> iPresenter,
                                      IUserProfileRepository userProfileRepository) {

        super(threadExecutor, mainThread, sessionManager);

        if (userProfileRepository == null || iPresenter == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        //this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.iPresenter = iPresenter;
        this.userProfileRepository = userProfileRepository;
    }

    /* */
    private void postError(String msg) {
        mainThread.post(() -> iPresenter.onError(new CGeneralException(msg)));
    }

    /* */
    private void postResult(Map<String, Object> map) {
        mainThread.post(() -> iPresenter.onSuccess(map));
    }

    /* sign up a new user */
    @Override
    public void run() {
        this.userProfileRepository.signOutWithEmailAndPassword(
                new CFirebaseCallBack() {
                    @Override
                    public void onFirebaseSuccess(Object object) {
                        // FIXME: replace with logout that synchronizes preferences and firebase
                        sessionManager.clearAllSettings();

                        Map<String, Object> map = new HashMap<>();
                        map.put(CConstantModel.SIGNOUT, object);
                        postResult(map);
                    }

                    @Override
                    public void onFirebaseFailure(Object object) {
                        postError((String) object);
                    }
                });
    }
}

//        {
///* logged user successfully sign out with firebase */
//@Override
//public void onSignOutSucceeded(String msg) {
//        // FIXME: replace with logout that synchronizes preferences and firebase
//        sessionManager.clearAllSettings();
//
//        Map<String, Object> map = new HashMap<>();
//        map.put(CConstantModel.SIGNOUT, msg);
//        postMessage(map);
//        }
///* logged user failed to register with firebase */
//@Override
//public void onSignOutFailed(String msg) {
//        notifyError(msg);
//        }