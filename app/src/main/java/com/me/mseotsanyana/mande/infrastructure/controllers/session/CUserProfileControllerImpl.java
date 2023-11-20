package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.interactors.session.userprofile.CUserSignOutInteractorImpl;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.CUserLoginInteractorImpl;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IUserProfileController;

import java.util.Map;

public class CUserProfileControllerImpl extends cAbstractPresenter implements IUserProfileController {

    private IViewModel iViewModel;
    private final IInteractor.IPresenter<IResponseDTO<Object>> iPresenter;
    //private final IPermissionRepository privilegeRepository;
    private final IUserProfileRepository userProfileRepository;

    //private final cInputValidation inputValidation;

    public CUserProfileControllerImpl(IExecutor executor, IMainThread mainThread,
                                      IViewModel iViewModel,
                                      IInteractor.IPresenter<IResponseDTO<Object>> iPresenter,
                                      IUserProfileRepository userProfileRepository) {
        super(executor, mainThread, null);

        this.iViewModel = iViewModel;
        this.iPresenter = iPresenter;
        this.userProfileRepository = userProfileRepository;

        //this.inputValidation = new cInputValidation();
    }

    @Override
    public void createUserWithEmailAndPassword(CUserProfileModel userProfileModel) {

    }

    @Override
    public void signOutWithEmailAndPassword() {
        IInteractor iInteractor = new CUserSignOutInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                userProfileRepository);

        iViewModel.showProgress();

        iInteractor.execute();
    }

    @Override
    public void readUserProfile() {

    }

    @Override
    public void readUserProfiles() {

    }

    @Override
    public void updateUserProfile(CUserProfileModel userProfileModel) {

    }

    @Override
    public void updateUserProfileImage(String userServerID, byte[] userProfileImageData) {

    }

    @Override
    public void uploadUserProfilesFromExcel(String filename) {

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

        IInteractor iInteractor = new CUserLoginInteractorImpl(
                executor, mainThread,
                iPresenter,
                userProfileRepository,
                email, password);

        iViewModel.showProgress();

        iInteractor.execute();
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
        if(this.iViewModel != null){
            this.iViewModel.hideProgress();
        }
    }

    @Override
    public void destroy() {
        this.iViewModel = null;
    }

    @Override
    public void onError(String message) {

    }
}
