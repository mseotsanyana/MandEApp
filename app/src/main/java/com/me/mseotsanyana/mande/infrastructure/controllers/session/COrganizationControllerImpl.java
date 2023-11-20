package com.me.mseotsanyana.mande.interfaceadapters.controllers.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.framework.controllers.base.CAbstractController;
import com.me.mseotsanyana.mande.framework.controllers.session.IOrganizationController;
import com.me.mseotsanyana.mande.interfaceadapters.presenters.session.COrganizationPresenterImpl;
import com.me.mseotsanyana.mande.usecases.executor.iExecutor;
import com.me.mseotsanyana.mande.usecases.executor.iMainThread;
import com.me.mseotsanyana.mande.usecases.interactors.session.organization.Impl.cCreateOrganizationInteractorImpl;
import com.me.mseotsanyana.mande.usecases.interactors.session.organization.Impl.cReadOrganizationsInteractorImpl;
import com.me.mseotsanyana.mande.usecases.interactors.session.organization.Impl.cRemoveListenerInteractorImpl;
import com.me.mseotsanyana.mande.usecases.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.mande.usecases.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.usecases.repository.session.iPrivilegeRepository;
import com.me.mseotsanyana.mande.usecases.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.usecases.repository.session.iUserProfileRepository;


public class COrganizationControllerImpl extends CAbstractController implements IOrganizationController
        /*iOrganizationInteractor.Callback, iUserSignOutInteractor.Callback*/ {
    //private static final String TAG = cOrganizationPresenterImpl.class.getSimpleName();

    private final IModelView modelView;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iPrivilegeRepository permissionRepository;
    private final iOrganizationRepository organizationRepository;
    private final iUserProfileRepository userProfileRepository;

    //private final cInputValidation inputValidation;

    public COrganizationControllerImpl(iExecutor executor, iMainThread mainThread,
                                       IModelView modelView,
                                       iSharedPreferenceRepository sharedPreferenceRepository,
                                       iPrivilegeRepository permissionRepository,
                                       iOrganizationRepository organizationRepository,
                                       iUserProfileRepository userProfileRepository) {
        super(executor, mainThread);

        this.modelView = modelView;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.permissionRepository = permissionRepository;
        this.organizationRepository = organizationRepository;
        this.userProfileRepository = userProfileRepository;
        //this.inputValidation = new cInputValidation();
    }

    // CREATE ORGANIZATION

    @Override
    public void createOrganization(COrganizationModel organizationModel) {
        /*if (!inputValidation.isEditTextFilled(view.getNameEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextFilled(view.getEmailEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextFilled(view.getWebsiteEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isEditTextEmail(view.getEmailEditText(),
                view.getResourceString(R.string.error_message_email))) {
            return;
        }*/

        iOrganizationInteractor organizationInteractor;
        organizationInteractor = new cCreateOrganizationInteractorImpl(
                new COrganizationPresenterImpl(this.modelView),
                executor,
                mainThread,
                sharedPreferenceRepository,
                permissionRepository,
                organizationRepository,

                organizationModel);

        modelView.showProgress();

        organizationInteractor.execute();
    }

    // READ ORGANIZATIONS

    @Override
    public void readOrganizationWorkspaces() {
        iOrganizationInteractor readOrganizationsInteractor;
        readOrganizationsInteractor = new cReadOrganizationsInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
                organizationRepository,
                new COrganizationPresenterImpl(this.modelView));

        modelView.showProgress();
        readOrganizationsInteractor.execute();
    }


    @Override
    public void removeListener() {
        iOrganizationInteractor organizationInteractor;
        organizationInteractor = new cRemoveListenerInteractorImpl(
                executor,
                mainThread,
                organizationRepository,
                new COrganizationPresenterImpl(this.modelView));

        modelView.showProgress();
        organizationInteractor.execute();
    }
//
//    @Override
//    public void switchOrganizationWorkspace(CWorkspaceModel workspaceModel) {
//        iOrganizationInteractor switchOrganizationWorkspaceInteractor;
//        switchOrganizationWorkspaceInteractor = new cSwitchOrganizationWorkspaceInteractorImpl(
//                executor,
//                mainThread,
//                sharedPreferenceRepository,
//                userProfileRepository,
//                new COrganizationPresenterImpl(this.modelView),
//                workspaceModel);
//
//        //view.showProgress();
//        switchOrganizationWorkspaceInteractor.execute();
//    }

//    @Override
//    public void onCreateOrganizationSucceeded(String msg) {
//        if (this.view != null) {
//            this.view.onCreateOrganizationSucceeded(msg);
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void onCreateOrganizationFailed(String msg) {
//        if (this.view != null) {
//            this.view.onCreateOrganizationFailed(msg);
//            this.view.hideProgress();
//        }
//    }


//    @Override
//    public void onReadOrganizationsSucceeded(List<cTreeModel> organizationModels) {
//        if (this.view != null) {
//            this.view.onReadOrganizationsSucceeded(organizationModels);
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void onReadOrganizationsFailed(String msg) {
//        if (this.view != null) {
//            this.view.onReadOrganizationsFailed(msg);
//            this.view.hideProgress();
//        }
//    }


//    @Override
//    public void onSwitchOrganizationWorkspaceFailed(String msg) {
//        if (this.view != null) {
//            this.view.onSwitchOrganizationWorkspaceFailed(msg);
//            //this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void onSwitchOrganizationWorkspaceSucceeded(String msg) {
//        if (this.view != null) {
//            this.view.onSwitchOrganizationWorkspaceSucceeded(msg);
//            //this.view.hideProgress();
//        }
//    }



    // LOGOUT USER

//    @Override
//    public void signOutWithEmailAndPassword() {
//        iUserSignOutInteractor userSignOutInteractor = new cUserSignOutInteractorImpl(
//                executor,
//                mainThread,this.modelView,
//                sharedPreferenceRepository,
//                userProfileRepository);
//
//        modelView.showProgress();
//
//        userSignOutInteractor.execute();
//    }

//    @Override
//    public void onUserSignOutFailed(String msg) {
//        if (this.view != null) {
//            this.view.onUserSignOutFailed(msg);
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void onUserSignOutSucceeded(String msg) {
//        if (this.view != null) {
//            this.view.onUserSignOutSucceeded(msg);
//            this.view.hideProgress();
//        }
//    }


    // PRESENTER FUNCTIONS

    @Override
    public void resume() {
        readOrganizationWorkspaces();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if (this.modelView != null) {
            this.modelView.hideProgress();
        }
    }

    @Override
    public void destroy() {
        //this.modelView = null;
    }

    @Override
    public void onError(String message) {

    }
}
