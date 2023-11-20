package com.me.mseotsanyana.mande.infrastructure.presenters.session;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.structures.CResponseDTO;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationController;
import com.me.mseotsanyana.mande.application.ports.session.IOrganizationInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class COrganizationPresenterImpl implements IInteractor.IPresenter<IResponseDTO<Object>> {
    //private static final String TAG = cOrganizationPresenterImpl.class.getSimpleName();

    private final IOrganizationController.IViewModel modelView;
    //private List<cTreeModel> treeModels;
    private int index;

    public COrganizationPresenterImpl(IOrganizationController.IViewModel modelView) {
        this.modelView = modelView;
        this.index = 0;
        //this.treeModels = new ArrayList<>();
    }

    @Override
    public void onError(Throwable throwable) {
        if (this.modelView != null) {
            //this.modelView.onReadOrganizationsFailed(msg);
            this.modelView.hideProgress();
        }
    }

    @Override
    public void onSuccess(IResponseDTO<Object> response) {

    }
}

//    @Override
//    public void OnCreateOrganizationSucceeded(String msg) {
//        if (this.modelView != null) {
//            //this.modelView.onCreateOrganizationSucceeded(msg);
//            this.modelView.hideProgress();
//        }
//    }
//
//    @Override
//    public void OnReadOrganizationSucceeded(COrganizationModel organizationModel,
//                                            String operation) {
//        if (this.modelView != null) {
//            if (operation.equals("ADD")) {
//                this.treeModels.add(new cTreeModel(index, -1, 0, organizationModel));
//                index = index + 1;
//                this.modelView.onReadOrganizationsSucceeded(organizationModels);
//            }
//            this.modelView.hideProgress();
//        }

//            Map<COrganizationModel, List<CWorkspaceModel>> organizationModelMap;
//            organizationModelMap = (Map<COrganizationModel, List<CWorkspaceModel>>) object;
//
//            int parentIndex = 0, childIndex;
//            for (Map.Entry<COrganizationModel, List<CWorkspaceModel>> entry :
//                    organizationModelMap.entrySet()) {
//
//                /* an organization */
//                COrganizationModel organizationModel = entry.getKey();
//
//
//                /* a list of workspaces under the organization */
//                childIndex = parentIndex;
//                for (CWorkspaceModel teamModel : entry.getValue()) {
//                    childIndex = childIndex + 1;
//                    orgWorkspacesTree.add(new cTreeModel(childIndex,
//                            parentIndex, 1, teamModel));
//                }
//
//                /* next parent index */
//                parentIndex = childIndex + 1;
//            }
//
//            postOrganization(orgWorkspacesTree);
//
//        }
//    }
//
//    @Override
//    public void OnUpdateOrganizationSucceeded(String msg) {
//
//    }
//
//    @Override
//    public void OnDeleteOrganizationSucceeded(String msg) {
//
//    }
//
//    @Override
//    public void OnInviteToOrganizationSucceeded(String msg) {
//    }


//    @Override
//    public void OnPreferenceClearedSucceeded(String msg) {
//
//    }
//
//    @Override
//    public void OnPreferenceClearedFailed(String msg) {
//
//    }




    //    @Override
//    public void onSwitchOrganizationWorkspaceFailed(String msg) {
//        if (this.modelView != null) {
//            //this.modelView.onSwitchOrganizationWorkspaceFailed(msg);
//            this.modelView.hideProgress();
//        }
//    }
//
//    @Override
//    public void onSwitchOrganizationWorkspaceSucceeded(String msg) {
//        if (this.modelView != null) {
//            //this.modelView.onSwitchOrganizationWorkspaceSucceeded(msg);
//            this.modelView.hideProgress();
//        }
//    }
//}
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

