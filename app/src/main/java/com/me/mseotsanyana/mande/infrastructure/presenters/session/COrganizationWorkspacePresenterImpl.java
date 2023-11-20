package com.me.mseotsanyana.mande.infrastructure.presenters.session;

import android.util.Log;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.structures.CResponseDTO;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.application.structures.enums.EAction;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationWorkspaceController;
import com.me.mseotsanyana.mande.infrastructure.utils.responsemodel.CTreeModel;

import java.util.Objects;

public class COrganizationWorkspacePresenterImpl implements
        IInteractor.IPresenter<IResponseDTO<Object>> {

    private static final String TAG = COrganizationWorkspacePresenterImpl.class.getSimpleName();
    private final IOrganizationWorkspaceController.IViewModel iViewModel;

    public COrganizationWorkspacePresenterImpl(
            IOrganizationWorkspaceController.IViewModel iViewModel) {
        this.iViewModel = iViewModel;
    }

    /*************************** organization workspace feedback methods **************************/

    @Override
    public void onSuccess(IResponseDTO<Object> response) {
        EAction operation = response.getAction();
        COrganizationModel organizationModel;
        CWorkspaceModel workspaceModel;

        switch (operation) {
            case Created_ORGANIZATION -> {
                if (this.iViewModel != null) {
                    Log.i(TAG, "Created organization.");
                    this.iViewModel.hideProgress();
                }
            }
            case Added_ORGANIZATION -> {
                if (this.iViewModel != null) {
                    organizationModel = (COrganizationModel) response.getData();
                    CTreeModel treeModel;
                    treeModel = new CTreeModel(organizationModel.getOrganizationServerID(),
                            null, 0, organizationModel);
                    this.iViewModel.onShowTreeModel(new CResponseDTO<>(EAction.Added_ORGANIZATION,
                            treeModel));
                    Log.i(TAG, "Added organization.");
                    this.iViewModel.hideProgress();
                }
            }
            case Modified_ORGANIZATION -> {
                if (this.iViewModel != null) {
                    if (response.getData() instanceof COrganizationModel) {
                        organizationModel = (COrganizationModel) response.getData();
                        CTreeModel treeModel;
                        treeModel = new CTreeModel(organizationModel.getOrganizationServerID(),
                                null, 0, organizationModel);
                        this.iViewModel.onShowTreeModel(new CResponseDTO<>(EAction.Modified_ORGANIZATION,
                                treeModel));
                        Log.i(TAG, "Modified organization.");
                    } else {
                        this.iViewModel.showMessage((String) response.getData());
                    }
                    this.iViewModel.hideProgress();
                }
            }
            case Deleted_ORGANIZATION -> {
                if (this.iViewModel != null) {
                    if (response.getData() instanceof COrganizationModel) {
                        organizationModel = (COrganizationModel) response.getData();
                        CTreeModel treeModel;
                        treeModel = new CTreeModel(organizationModel.getOrganizationServerID(),
                                null, 0, organizationModel);
                        this.iViewModel.onShowTreeModel(new CResponseDTO<>(
                                EAction.Deleted_ORGANIZATION, treeModel));
                        Log.i(TAG, "Deleted the organization.");
                    } else {
                        this.iViewModel.showMessage((String) response.getData());
                    }
                    this.iViewModel.hideProgress();
                }
            }
            case Created_WORKSPACE -> {
                if (this.iViewModel != null) {
                    Log.i(TAG, "Created Workspace under the organization.");
                    this.iViewModel.hideProgress();
                }
            }
            case Added_WORKSPACE -> {
                if (this.iViewModel != null) {
                    workspaceModel = (CWorkspaceModel) response.getData();
                    CTreeModel treeModel = new CTreeModel(workspaceModel.getCompositeServerID(),
                            workspaceModel.getOrganizationServerID(), 1, workspaceModel);
                    this.iViewModel.onShowTreeModel(new CResponseDTO<>(EAction.Added_WORKSPACE,
                            treeModel));
                    Log.i(TAG, "Added Workspace under the organization.");
                    this.iViewModel.hideProgress();
                }
            }
            case Modified_WORKSPACE -> {
                if (this.iViewModel != null) {
                    workspaceModel = (CWorkspaceModel) response.getData();
                    CTreeModel treeModel;
                    treeModel = new CTreeModel(workspaceModel.getCompositeServerID(),
                            workspaceModel.getOrganizationServerID(), 1, workspaceModel);
                    this.iViewModel.onShowTreeModel(new CResponseDTO<>(EAction.Modified_WORKSPACE,
                            treeModel));
                    Log.i(TAG, "Modified workspace under the organization.");
                    this.iViewModel.hideProgress();
                }
            }
            case Deleted_WORKSPACE -> {
                if (this.iViewModel != null) {
                    if (response.getData() instanceof CWorkspaceModel) {
                        workspaceModel = (CWorkspaceModel) response.getData();
                        CTreeModel treeModel = new CTreeModel(workspaceModel.getCompositeServerID(),
                                workspaceModel.getOrganizationServerID(), 1, workspaceModel);
                        this.iViewModel.onShowTreeModel(new CResponseDTO<>(EAction.Deleted_WORKSPACE,
                                treeModel));
                        Log.i(TAG, "Deleted workspace under the organization.");
                    } else {
                        this.iViewModel.showMessage((String) response.getData());
                    }
                    this.iViewModel.hideProgress();
                }
            }
            case Switched_WORKSPACE -> {
                if (this.iViewModel != null) {
                    this.iViewModel.switchResponse((String) response.getData());
                    this.iViewModel.hideProgress();
                    Log.i(TAG, "Switching the workspace.");
                }
            }
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (this.iViewModel != null) {
            this.iViewModel.showMessage(throwable.getMessage());
            Log.e(TAG, Objects.requireNonNull(throwable.getMessage()));
            this.iViewModel.hideProgress();
        }
    }
}

//    } else if(EModified.ORGANIZATION.equals(operation))
//
//    {
//        if (response.getData() instanceof COrganizationModel) {
//            Log.d(TAG, "Modified Organization");
//        }
//        if (response.getData() instanceof CWorkspaceModel) {
//            Log.d(TAG, "Modified Organization");
//        }
//    } else if(EDeleted.ORGANIZATION.equals(operation))
//
//    {
//        if (response.getData() instanceof COrganizationModel) {
//            Log.d(TAG, "Delete Organization");
//        }
//
//        if (response.getData() instanceof CWorkspaceModel) {
//            Log.d(TAG, "Delete Workspace");
//        }
//
//    } else if(CConstantModel.NONEENTITY.equals(operation))
//
//    {
//
//
//    } else if(CConstantModel.SIGNOUT.equals(operation))
//
//    {
//        if (this.iViewModel != null) {
//            this.iViewModel.showResponseMessage((String) response.getData());
//            this.iViewModel.hideProgress();
//        }
//    }
//
//}
//
//
//}

//        response.getOperation()
//
//        CResponseDTO responseModel;
//
//        for (Map.Entry<String, Object> entry : response.entrySet()) {
//
//            switch (entry.getKey()) {
//
//                case CConstantModel.ORGANIZATION:
//                    responseModel = (CResponseDTO) entry.getValue();
//
//                    COrganizationModel organizationModel;
//                    organizationModel = (COrganizationModel) responseModel.getData();
//                    if (this.iViewModel != null) {
//                        // update the response model
//                        if (!treeModels.containsKey(organizationModel.getOrganizationServerID())) {
//                            CTreeModel treeModel;
//                            treeModel = new CTreeModel(organizationModel.getOrganizationServerID(),
//                                    null, 0, organizationModel);
//                            treeModels.put(organizationModel.getOrganizationServerID(), treeModel);
//                        }
//                        this.iViewModel.showResponse(treeModels);
//                        this.iViewModel.hideProgress();
//                    }
//                    break;
//
//                case CConstantModel.WORKSPACE:
//
//                    responseModel = (CResponseDTO) entry.getValue();
//
//                    switch (responseModel.getOperation()) {
//                        case CConstantModel.CREATED:
//
//                            break;
//                        case CConstantModel.ADDED:
//
//                            CWorkspaceModel workspaceModel = (CWorkspaceModel) responseModel.getData();
//
//                            if (this.iViewModel != null) {
//                                // update the response model
//                                CTreeModel treeModel = new CTreeModel(workspaceModel.getCompositeServerID(),
//                                        workspaceModel.getOrganizationServerID(), 1, workspaceModel);
//                                treeModels.put(workspaceModel.getCompositeServerID(), treeModel);
//                                this.iViewModel.showResponse(treeModels);
//                                this.iViewModel.hideProgress();
//                            }
//                        case CConstantModel.DELETED:
//                            if (this.iViewModel != null) {
//                                this.iViewModel.hideProgress();
//                            }
//                            break;
//                    }
//
//                    break;
//
//                case CConstantModel.NONEENTITY:
//                    if (this.iViewModel != null) {
//                        this.iViewModel.switchResponse((String) entry.getValue());
//                        this.iViewModel.hideProgress();
//                    }
//                    break;
//
//                case CConstantModel.SIGNOUT:
//                    if (this.iViewModel != null) {
//                        this.iViewModel.showResponseMessage((String) entry.getValue());
//                        this.iViewModel.hideProgress();
//                    }
//                    break;
//            }
//        }


//    @Override
//    public void OnCreateOrganizationSucceeded(String msg) {
//
//    }
//
//    @Override
//    public void OnReadOrganizationSucceeded(COrganizationModel organizationModel, String operation) {
//        if (this.iViewModel != null) {
//            // update the response model
//            if (!treeModels.containsKey(organizationModel.getOrganizationServerID())) {
//                CTreeModel treeModel;
//                treeModel = new CTreeModel(organizationModel.getOrganizationServerID(),
//                        null, 0, organizationModel);
//                treeModels.add(organizationModel.getOrganizationServerID(), treeModel);
//            }
//            this.iViewModel.OnReadOrganizationSucceeded(treeModels);
//            this.iViewModel.hideProgress();
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
//
//    }


//    @Override
//    public void OnCreateWorkspaceSucceeded(String msg) {
//
//    }
//
//    @Override
//    public void OnReadWorkspaceSucceeded(CWorkspaceModel workspaceModel, String operation,
//                                         int position) {
//        if (this.iViewModel != null) {
//            // update the response model
//            CTreeModel treeModel = new CTreeModel(workspaceModel.getCompositeServerID(),
//                    workspaceModel.getOrganizationServerID(), 1, workspaceModel);
//
//            this.iViewModel.OnReadWorkspaceSucceeded(treeModel, position);
//            this.iViewModel.hideProgress();
//        }
//    }
//
//    @Override
//    public void OnUpdateWorkspaceSucceeded(String msg) {
//
//    }
//
//    @Override
//    public void OnDeleteWorkspaceSucceeded(String msg) {
//
//    }
//
//    @Override
//    public void OnInviteToWorkspaceSucceeded(String msg) {
//
//    }


//    @Override
//    public void OnPreferenceClearedSucceeded(String msg) {
//        if (this.iViewModel != null) {
//            this.iViewModel.OnPreferenceClearedSucceeded(msg);
//            this.iViewModel.hideProgress();
//        }
//    }
//
//    @Override
//    public void OnPreferenceClearedFailed(String msg) {
//        if (this.iViewModel != null) {
//            this.iViewModel.OnPreferenceClearedFailed(msg);
//            this.iViewModel.hideProgress();
//        }
//    }


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

