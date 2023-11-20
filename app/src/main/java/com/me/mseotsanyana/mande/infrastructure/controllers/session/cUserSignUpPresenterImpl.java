package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.CUserSignUpInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.user.iUserSignUpInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iUserSignUpPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.OLD.cInputValidation;


public class cUserSignUpPresenterImpl extends cAbstractPresenter implements iUserSignUpPresenter,
        iUserSignUpInteractor.Callback {
    //private static String TAG = cUserSignUpPresenterImpl.class.getSimpleName();

    private View view;
    private final IUserProfileRepository userProfileRepository;

    private final cInputValidation inputValidation;

    public cUserSignUpPresenterImpl(IExecutor executor, IMainThread mainThread,
                                    ISessionManager sessionManager,
                                    View view,
                                    IUserProfileRepository userProfileRepository) {
        super(executor, mainThread, sessionManager);

        this.view = view;
        this.userProfileRepository = userProfileRepository;

        this.inputValidation = new cInputValidation();
    }


    @Override
    public void createUserWithEmailAndPassword(CUserProfileModel userProfileModel) {
        if (!inputValidation.isEditTextFilled(view.getEmailEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextFilled(view.getPasswordEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextFilled(view.getConfirmPasswordEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextEmail(view.getEmailEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextMatches(view.getPasswordEditText(),
                view.getConfirmPasswordEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        IInteractor iInteractor = new CUserSignUpInteractorImpl(
                executor,
                mainThread,
                userProfileRepository,
                null,
                userProfileModel);

        view.showProgress();

        iInteractor.execute();
    }

    @Override
    public void onUserSignUpFailed(String msg) {
        if(this.view != null) {
            this.view.onUserSignUpFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUserSignUpSucceeded(String msg) {
        if(this.view != null) {
            this.view.onUserSignUpSucceeded(msg);
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
