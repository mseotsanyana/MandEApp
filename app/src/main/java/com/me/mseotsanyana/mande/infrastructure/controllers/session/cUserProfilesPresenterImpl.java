package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.Impl.cReadUserProfilesInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.Impl.cUpdateUserProfileImageInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.Impl.cUploadUserProfilesInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.iUserProfilesInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iUserProfilePresenter;
import com.me.mseotsanyana.mande.infrastructure.services.CSessionManagerImpl;

import java.util.List;

public class cUserProfilesPresenterImpl extends cAbstractPresenter implements iUserProfilePresenter,
        iUserProfilesInteractor.Callback {
    //private static final String TAG = cOrganizationPresenterImpl.class.getSimpleName();

    private View view;
    //private final ISessionManager sharedPreferenceRepository;
    private final IUserProfileRepository userProfileRepository;

    public cUserProfilesPresenterImpl(IExecutor executor, IMainThread mainThread,
                                      CSessionManagerImpl sessionManager,
                                      View view,
                                      IUserProfileRepository userProfileRepository) {
        super(executor, mainThread, sessionManager);

        this.view = view;
        //this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.userProfileRepository = userProfileRepository;
    }


    // READ USER PROFILES
    @Override
    public void readUserProfiles() {
        iUserProfilesInteractor readUserProfilesInteractor;
        readUserProfilesInteractor = new cReadUserProfilesInteractorImpl(
                executor,
                mainThread,
                null,
                userProfileRepository,
                this);

        view.showProgress();
        readUserProfilesInteractor.execute();
    }

    @Override
    public void onReadUserProfilesFailed(String msg) {
        if (this.view != null) {
            this.view.onReadUserProfilesFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadUserProfilesSucceeded(List<CUserProfileModel> userProfileModels) {
        if (this.view != null) {
            this.view.onReadUserProfilesSucceeded(userProfileModels);
            this.view.hideProgress();
        }
    }



    // UPLOAD or UPDATE

    @Override
    public void uploadUserProfilesFromExcel(String filename) {
        iUserProfilesInteractor userProfilesInteractor =
                new cUploadUserProfilesInteractorImpl(executor, mainThread,
                        null,
                        userProfileRepository,
                        this, filename);

        view.showProgress();
        userProfilesInteractor.execute();
    }

    @Override
    public void onUploadUserProfilesSucceeded(String msg) {
        if (this.view != null) {
            this.view.onUploadUserProfilesSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUploadUserProfilesFailed(String msg) {
        if (this.view != null) {
            this.view.onUploadUserProfilesFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void updateUserProfileImage(String userServerID, byte[] userProfileImageData) {
        iUserProfilesInteractor userProfilesInteractor =
                new cUpdateUserProfileImageInteractorImpl(executor, mainThread,
                        null,
                        userProfileRepository,
                        this, userServerID, userProfileImageData);

        view.showProgress();
        userProfilesInteractor.execute();
    }

    @Override
    public void onUpdateUserProfileImageSucceeded(String msg) {
        if (this.view != null) {
            this.view.onUpdateUserProfileImageSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUpdateUserProfileImageFailed(String msg) {
        if (this.view != null) {
            this.view.onUpdateUserProfileImageFailed(msg);
            this.view.hideProgress();
        }
    }

    // PRESENTER FUNCTIONS

    @Override
    public void resume() {
        readUserProfiles();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if (this.view != null) {
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
