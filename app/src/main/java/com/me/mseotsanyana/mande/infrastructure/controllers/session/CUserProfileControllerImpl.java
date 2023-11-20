package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.session.user.Impl.cUserProfileInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.user.IUserProfileInteractor;
import com.me.mseotsanyana.mande.application.repository.session.iPrivilegeRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISharedPreferenceRepository;
import com.me.mseotsanyana.mande.application.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.OLD.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IUserLoginController;
import com.me.mseotsanyana.mande.OLD.cInputValidation;

public class CUserLoginControllerImpl extends cAbstractPresenter implements IUserLoginController,
        IUserProfileInteractor.IUserLoginPresenter {

    private IViewModel viewModel;
    private final ISharedPreferenceRepository sharedPreferenceRepository;
    private final iPrivilegeRepository privilegeRepository;
    private final iUserProfileRepository userProfileRepository;

    private final cInputValidation inputValidation;

    public CUserLoginControllerImpl(IExecutor executor, IMainThread mainThread, IViewModel viewModel,
                                    ISharedPreferenceRepository sharedPreferenceRepository,
                                    iPrivilegeRepository privilegeRepository,
                                    iUserProfileRepository userProfileRepository) {
        super(executor, mainThread);

        this.viewModel = viewModel;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.privilegeRepository = privilegeRepository;
        this.userProfileRepository = userProfileRepository;

        this.inputValidation = new cInputValidation();
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password) {
//        if (!inputValidation.isEditTextFilled(viewModel.getEmailEditText(),
//                viewModel.getResourceString(R.string.error_message_email))) {
//            return;
//        }
//
//        if (!inputValidation.isEditTextFilled(viewModel.getPasswordEditText(),
//                viewModel.getResourceString(R.string.error_message_email))) {
//            return;
//        }
//
//        if (!inputValidation.isEditTextEmail(viewModel.getEmailEditText(),
//                viewModel.getResourceString(R.string.error_message_email))) {
//            return;
//        }

        IUserProfileInteractor userLoginInteractor = new cUserProfileInteractorImpl(
                executor, mainThread,this,
                sharedPreferenceRepository,
                privilegeRepository,
                userProfileRepository,
                email, password);

        viewModel.showProgress();

        userLoginInteractor.execute();
    }

    @Override
    public void OnUserLoginSucceeded(String msg) {
        if(this.viewModel != null) {
            this.viewModel.OnUserLoginSucceeded(msg);
            this.viewModel.hideProgress();
        }
    }

    @Override
    public void OnUserLoginFailed(String msg) {
        if(this.viewModel != null) {
            this.viewModel.OnUserLoginFailed(msg);
            this.viewModel.hideProgress();
        }
    }

    /********************************** view life cycle methods ***********************************/

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if(this.viewModel != null){
            this.viewModel.hideProgress();
        }
    }

    @Override
    public void destroy() {
        this.viewModel = null;
    }

    @Override
    public void onError(String message) {

    }
}
