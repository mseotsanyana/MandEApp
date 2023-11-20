package com.me.mseotsanyana.mande.infrastructure.presenters.session;

import com.me.mseotsanyana.mande.application.ports.session.IOrganizationInteractor;
import com.me.mseotsanyana.mande.application.ports.session.IOrganizationWorkspaceInteractor;
import com.me.mseotsanyana.mande.application.ports.session.IWorkspaceInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.application.structures.CIndexedLinkedHashMap;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationWorkspaceController;
import com.me.mseotsanyana.mande.infrastructure.utils.responsemodel.CTreeModel;


public class COrganizationWorkspacePresenterImpl implements
        IOrganizationWorkspaceInteractor.IOrganizationWorkspacePresenter,
        IOrganizationInteractor.IOrganizationPresenter, IWorkspaceInteractor.IWorkspacePresenter {
    //private static final String TAG = COrganizationWorkspacePresenterImpl.class.getSimpleName();

    private final IOrganizationWorkspaceController.IViewModel iViewModel;

    private final CIndexedLinkedHashMap<String, CTreeModel> treeModels;

    public COrganizationWorkspacePresenterImpl(
            IOrganizationWorkspaceController.IViewModel iViewModel) {
        this.iViewModel = iViewModel;
        this.treeModels = new CIndexedLinkedHashMap<>();
    }

    /*********************************** organization functions ***********************************/

    @Override
    public void OnCreateOrganizationSucceeded(String msg) {

    }

    @Override
    public void OnCreateOrganizationFailed(String msg) {

    }

    @Override
    public void OnReadOrganizationSucceeded(COrganizationModel organizationModel, String operation) {
        if (this.iViewModel != null) {
            // update the response model
            if (!treeModels.containsKey(organizationModel.getOrganizationServerID())) {
                CTreeModel treeModel;
                treeModel = new CTreeModel(organizationModel.getOrganizationServerID(),
                        null, 0, organizationModel);
                treeModels.add(organizationModel.getOrganizationServerID(), treeModel);
            }
            this.iViewModel.OnReadOrganizationSucceeded(treeModels);
            this.iViewModel.hideProgress();
        }
    }

    @Override
    public void OnReadOrganizationFailed(String msg) {
        if (this.iViewModel != null) {
            this.iViewModel.OnReadOrganizationWorkspaceFailed(msg);
            this.iViewModel.hideProgress();
        }
    }

    @Override
    public void OnUpdateOrganizationSucceeded(String msg) {

    }

    @Override
    public void OnUpdateOrganizationFailed(String msg) {

    }

    @Override
    public void OnDeleteOrganizationSucceeded(String msg) {

    }

    @Override
    public void OnDeleteOrganizationFailed(String msg) {

    }

    @Override
    public void OnInviteToOrganizationSucceeded(String msg) {

    }

    @Override
    public void OnInviteToOrganizationFailed(String msg) {

    }

    /************************************* workspace functions ************************************/

    @Override
    public void OnCreateWorkspaceSucceeded(String msg) {

    }

    @Override
    public void OnCreateWorkspaceFailed(String msg) {

    }

    @Override
    public void OnReadWorkspaceSucceeded(CWorkspaceModel workspaceModel, String operation,
                                         int position) {
        if (this.iViewModel != null) {
            // update the response model
            CTreeModel treeModel = new CTreeModel(workspaceModel.getCompositeServerID(),
                    workspaceModel.getOrganizationServerID(), 1, workspaceModel);

            this.iViewModel.OnReadWorkspaceSucceeded(treeModel, position);
            this.iViewModel.hideProgress();
        }
    }

    @Override
    public void OnReadWorkspaceFailed(String msg) {
        if (this.iViewModel != null) {
            this.iViewModel.OnReadOrganizationWorkspaceFailed(msg);
            this.iViewModel.hideProgress();
        }
    }

    @Override
    public void OnUpdateWorkspaceSucceeded(String msg) {

    }

    @Override
    public void OnUpdateWorkspaceFailed(String msg) {

    }

    @Override
    public void OnDeleteWorkspaceSucceeded(String msg) {

    }

    @Override
    public void OnDeleteWorkspaceFailed(String msg) {

    }

    @Override
    public void OnInviteToWorkspaceSucceeded(String msg) {

    }

    @Override
    public void OnInviteToWorkspaceFailed(String msg) {

    }

    /******************************* organization workspace functions *****************************/

    @Override
    public void OnPreferenceClearedSucceeded(String msg) {
        if (this.iViewModel != null) {
            this.iViewModel.OnPreferenceClearedSucceeded(msg);
            this.iViewModel.hideProgress();
        }
    }

    @Override
    public void OnPreferenceClearedFailed(String msg) {
        if (this.iViewModel != null) {
            this.iViewModel.OnPreferenceClearedFailed(msg);
            this.iViewModel.hideProgress();
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

