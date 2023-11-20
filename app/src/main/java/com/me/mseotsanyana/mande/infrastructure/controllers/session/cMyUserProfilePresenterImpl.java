package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.session.user.Impl.cReadUserProfileInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.user.Impl.cUpdateUserProfileInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.user.iReadUserProfileInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.user.iUpdateUserProfileInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iMyUserProfilePresenter;
import com.me.mseotsanyana.mande.OLD.cInputValidation;

public class cMyUserProfilePresenterImpl extends cAbstractPresenter implements iMyUserProfilePresenter,
        iReadUserProfileInteractor.Callback, iUpdateUserProfileInteractor.Callback {
    private static String TAG = cMyUserProfilePresenterImpl.class.getSimpleName();

    private View view;
    private final ISessionManager sessionManagerRepository;
    private final IUserProfileRepository userProfileRepository;

    private final cInputValidation inputValidation;

    public cMyUserProfilePresenterImpl(IExecutor executor, IMainThread mainThread,
                                       View view,
                                       ISessionManager sessionManagerRepository,
                                       IUserProfileRepository userProfileRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.sessionManagerRepository = sessionManagerRepository;
        this.userProfileRepository = userProfileRepository;

        this.inputValidation = new cInputValidation();
    }

    // CREATE

    // READ

    @Override
    public void readUserProfile() {
        iReadUserProfileInteractor readUserInteractor = new cReadUserProfileInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                userProfileRepository,
                this);

        view.showProgress();
        readUserInteractor.execute();
    }

    @Override
    public void onReadUserProfileRetrieved(CUserProfileModel readUserProfile) {
        if(this.view != null) {
            this.view.onReadUserProfileSucceeded(readUserProfile);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadUserProfileFailed(String msg) {
        if(this.view != null) {
            this.view.onReadUserProfileFailed(msg);
            this.view.hideProgress();
        }
    }

    // UPDATE

    @Override
    public void updateUserProfile(CUserProfileModel userProfileModel) {

        iUpdateUserProfileInteractor updateUserInteractor = new cUpdateUserProfileInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                userProfileRepository,
                this,
                userProfileModel);

        view.showProgress();

        updateUserInteractor.execute();
    }

    @Override
    public void onUpdateUserProfileFailed(String msg) {
        if(this.view != null) {
            this.view.onUpdateUserProfileFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUpdateUserProfileRetrieved(String msg) {
        if(this.view != null) {
            this.view.onUpdateUserProfileSucceeded(msg);
            this.view.hideProgress();
        }
    }

    // PRESENTER FUNCTIONS

    @Override
    public void resume() {
        readUserProfile();
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
