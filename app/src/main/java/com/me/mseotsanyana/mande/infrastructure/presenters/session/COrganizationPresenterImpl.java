package com.me.mseotsanyana.mande.interfaceadapters.presenters.session;

import com.me.mseotsanyana.mande.framework.controllers.session.IOrganizationController;
import com.me.mseotsanyana.mande.usecases.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;


public class COrganizationPresenterImpl implements /*iOrganizationPresenter,*/
        iOrganizationInteractor.Callback/*, iUserSignOutInteractor.Callback*/ {
    //private static final String TAG = cOrganizationPresenterImpl.class.getSimpleName();

    private final IOrganizationController.IModelView modelView;

    public COrganizationPresenterImpl(IOrganizationController.IModelView modelView) {
        this.modelView = modelView;
    }

    @Override
    public void onCreateOrganizationSucceeded(String msg) {
        if (this.modelView != null) {
            this.modelView.onCreateOrganizationSucceeded(msg);
            this.modelView.hideProgress();
        }
    }

    @Override
    public void onCreateOrganizationFailed(String msg) {
        if (this.modelView != null) {
            this.modelView.onCreateOrganizationFailed(msg);
            this.modelView.hideProgress();
        }
    }

    @Override
    public void onReadOrganizationsSucceeded(List<cTreeModel> organizationModels) {
        if (this.modelView != null) {
            this.modelView.onReadOrganizationsSucceeded(organizationModels);
            this.modelView.hideProgress();
        }
    }

    @Override
    public void onReadOrganizationsFailed(String msg) {
        if (this.modelView != null) {
            this.modelView.onReadOrganizationsFailed(msg);
            this.modelView.hideProgress();
        }
    }

    @Override
    public void onSwitchOrganizationWorkspaceFailed(String msg) {
        if (this.modelView != null) {
            this.modelView.onSwitchOrganizationWorkspaceFailed(msg);
            this.modelView.hideProgress();
        }
    }

    @Override
    public void onSwitchOrganizationWorkspaceSucceeded(String msg) {
        if (this.modelView != null) {
            this.modelView.onSwitchOrganizationWorkspaceSucceeded(msg);
            this.modelView.hideProgress();
        }
    }
}
//    // CREATE ORGANIZATION
//    @Override
//    public void createOrganization(COrganizationModel organizationModel) {
//        /*if (!inputValidation.isEditTextFilled(view.getNameEditText(),
//                view.getResourceString(R.string.error_message_email))) {
//            return;
//        }
//
//        if (!inputValidation.isEditTextFilled(view.getEmailEditText(),
//                view.getResourceString(R.string.error_message_email))) {
//            return;
//        }
//
//        if (!inputValidation.isEditTextFilled(view.getWebsiteEditText(),
//                view.getResourceString(R.string.error_message_email))) {
//            return;
//        }
//
//        if (!inputValidation.isEditTextEmail(view.getEmailEditText(),
//                view.getResourceString(R.string.error_message_email))) {
//            return;
//        }*/
//
//        iOrganizationInteractor organizationInteractor;
//        organizationInteractor = new cCreateOrganizationInteractorImpl(
//                executor,
//                mainThread,
//                sharedPreferenceRepository,
//                permissionRepository,
//                organizationRepository,
//                this,
//                organizationModel);
//
//        view.showProgress();
//
//        organizationInteractor.execute();
//    }

//    // READ ORGANIZATIONS
//    @Override
//    public void readOrganizationWorkspaces() {
//        iOrganizationInteractor readOrganizationsInteractor;
//        readOrganizationsInteractor = new cReadOrganizationsInteractorImpl(
//                executor,
//                mainThread,
//                sharedPreferenceRepository,
//                organizationRepository,
//                this);
//
//        view.showProgress();
//        readOrganizationsInteractor.execute();
//    }

//    @Override
//    public void switchOrganizationWorkspace(CWorkspaceModel workspaceModel) {
//        iOrganizationInteractor switchOrganizationWorkspaceInteractor;
//        switchOrganizationWorkspaceInteractor = new cSwitchOrganizationWorkspaceInteractorImpl(
//                executor,
//                mainThread,
//                sharedPreferenceRepository,
//                userProfileRepository,
//                this, workspaceModel);
//
//        //view.showProgress();
//        switchOrganizationWorkspaceInteractor.execute();
//    }

//    @Override
//    public void removeListener() {
//        iOrganizationInteractor organizationInteractor;
//        organizationInteractor = new cRemoveListenerInteractorImpl(
//                executor,
//                mainThread,
//                organizationRepository,
//                this);
//
//        view.showProgress();
//        organizationInteractor.execute();
//    }

//    // LOGOUT USER
//    @Override
//    public void signOutWithEmailAndPassword() {
//        iUserSignOutInteractor userSignOutInteractor = new cUserSignOutInteractorImpl(
//                executor,
//                mainThread,this,
//                sharedPreferenceRepository,
//                userProfileRepository);
//
//        view.showProgress();
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

//    // PRESENTER FUNCTIONS
//    @Override
//    public void resume() {
//        readOrganizationWorkspaces();
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void stop() {
//        if (this.view != null) {
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void destroy() {
//        this.view = null;
//    }
//
//    @Override
//    public void onError(String message) {
//
//    }

