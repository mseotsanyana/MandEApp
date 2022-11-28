package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.entities.models.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cWorkspaceModel;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl.cCreateOrganizationInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl.cReadOrganizationsInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl.cRemoveListenerInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl.cSwitchOrganizationWorkspaceInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl.cUserSignOutInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUserSignOutInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iOrganizationPresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;


public class cOrganizationPresenterImpl extends cAbstractPresenter implements iOrganizationPresenter,
        iOrganizationInteractor.Callback, iUserSignOutInteractor.Callback {
    //private static final String TAG = cOrganizationPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iPermissionRepository permissionRepository;
    private final iOrganizationRepository organizationRepository;
    private final iUserProfileRepository userProfileRepository;

    //private final cInputValidation inputValidation;

    public cOrganizationPresenterImpl(iExecutor executor, iMainThread mainThread,
                                      View view,
                                      iSharedPreferenceRepository sharedPreferenceRepository,
                                      iPermissionRepository permissionRepository,
                                      iOrganizationRepository organizationRepository,
                                      iUserProfileRepository userProfileRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.permissionRepository = permissionRepository;
        this.organizationRepository = organizationRepository;
        this.userProfileRepository = userProfileRepository;
        //this.inputValidation = new cInputValidation();
    }

    // CREATE ORGANIZATION

    @Override
    public void createOrganization(cOrganizationModel organizationModel) {
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
                executor,
                mainThread,
                sharedPreferenceRepository,
                permissionRepository,
                organizationRepository,
                this,
                organizationModel);

        view.showProgress();

        organizationInteractor.execute();
    }

    @Override
    public void onCreateOrganizationSucceeded(String msg) {
        if (this.view != null) {
            this.view.onCreateOrganizationSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onCreateOrganizationFailed(String msg) {
        if (this.view != null) {
            this.view.onCreateOrganizationFailed(msg);
            this.view.hideProgress();
        }
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
                this);

        view.showProgress();
        readOrganizationsInteractor.execute();
    }


    @Override
    public void onReadOrganizationsSucceeded(List<cTreeModel> organizationModels) {
        if (this.view != null) {
            this.view.onReadOrganizationsSucceeded(organizationModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadOrganizationsFailed(String msg) {
        if (this.view != null) {
            this.view.onReadOrganizationsFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void switchOrganizationWorkspace(cWorkspaceModel workspaceModel) {
        iOrganizationInteractor switchOrganizationWorkspaceInteractor;
        switchOrganizationWorkspaceInteractor = new cSwitchOrganizationWorkspaceInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
                permissionRepository,
                this, workspaceModel);

        //view.showProgress();
        switchOrganizationWorkspaceInteractor.execute();
    }

    @Override
    public void onSwitchOrganizationWorkspaceFailed(String msg) {
        if (this.view != null) {
            this.view.onSwitchOrganizationWorkspaceFailed(msg);
            //this.view.hideProgress();
        }
    }

    @Override
    public void onSwitchOrganizationWorkspaceSucceeded(String msg) {
        if (this.view != null) {
            this.view.onSwitchOrganizationWorkspaceSucceeded(msg);
            //this.view.hideProgress();
        }
    }

    @Override
    public void removeListener() {
        iOrganizationInteractor organizationInteractor;
        organizationInteractor = new cRemoveListenerInteractorImpl(
                executor,
                mainThread,
                organizationRepository,
                this);

        view.showProgress();
        organizationInteractor.execute();
    }

    // LOGOUT USER

    @Override
    public void signOutWithEmailAndPassword() {
        iUserSignOutInteractor userSignOutInteractor = new cUserSignOutInteractorImpl(
                executor,
                mainThread,this,
                sharedPreferenceRepository,
                userProfileRepository);

        view.showProgress();

        userSignOutInteractor.execute();
    }

    @Override
    public void onUserSignOutFailed(String msg) {
        if (this.view != null) {
            this.view.onUserSignOutFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUserSignOutSucceeded(String msg) {
        if (this.view != null) {
            this.view.onUserSignOutSucceeded(msg);
            this.view.hideProgress();
        }
    }


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
