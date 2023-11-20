package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.session.user.IUserSignOutInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iUserSignOutPresenter;

public class cUserSignOutPresenterImpl extends cAbstractPresenter implements iUserSignOutPresenter,
        IUserSignOutInteractor.Callback {
    //private static String TAG = cUserSignOutPresenterImpl.class.getSimpleName();

    private View view;
    private final ISessionManager sharedPreferenceRepository;
    private final IUserProfileRepository userProfileRepository;

    public cUserSignOutPresenterImpl(IExecutor executor, IMainThread mainThread,
                                     View view,
                                     ISessionManager sharedPreferenceRepository,
                                     IUserProfileRepository userProfileRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public void signOutWithEmailAndPassword() {
//        IUserSignOutInteractor userSignOutInteractor = new CUserSignOutInteractorImpl(
//                executor,
//                mainThread,this,
//                sharedPreferenceRepository,
//                userProfileRepository);
//
//        view.showProgress();
//
//        userSignOutInteractor.execute();
    }

    @Override
    public void onUserSignOutFailed(String msg) {
        if(this.view != null) {
            this.view.onUserSignOutFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUserSignOutSucceeded(String msg) {
        if(this.view != null) {
            this.view.onUserSignOutSucceeded(msg);
            this.view.hideProgress();
        }
    }

    /*============================= General Presenter methods ============================= */

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if(this.view != null){
            this.view.hideProgress();
        }
    }

    @Override
    public void destroy() {
        this.view = null;
    }

    @Override
    public void onError(String message) {

    }
}
