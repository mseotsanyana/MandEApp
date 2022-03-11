package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.Impl.cCreateStakeholderInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.Impl.cReadStakeholderMembersInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.Impl.cReadStakeholdersInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.Impl.cRemoveListenerInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.iStakeholderInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.iStakeholderMembersInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cStakeholderModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iStakeholderRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iStakeholderPresenter;

import java.util.ArrayList;
import java.util.List;

public class cStakeholderPresenterImpl extends cAbstractPresenter implements iStakeholderPresenter,
        iStakeholderInteractor.Callback, iStakeholderMembersInteractor.Callback  {
    //private static final String TAG = cOrganizationPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iPermissionRepository permissionRepository;
    private final iStakeholderRepository stakeholderRepository;

    //private final cInputValidation inputValidation;

    public cStakeholderPresenterImpl(iExecutor executor, iMainThread mainThread,
                                     View view,
                                     iSharedPreferenceRepository sharedPreferenceRepository,
                                     iPermissionRepository permissionRepository,
                                     iStakeholderRepository stakeholderRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.permissionRepository = permissionRepository;
        this.stakeholderRepository = stakeholderRepository;

        //this.inputValidation = new cInputValidation();
    }

    // CREATE

    @Override
    public void createStakeholder(cStakeholderModel stakeholderModel) {
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

        iStakeholderInteractor createStakeholderInteractor;
        createStakeholderInteractor = new cCreateStakeholderInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
                permissionRepository,
                stakeholderRepository,
                this,
                stakeholderModel);

        view.showProgress();

        createStakeholderInteractor.execute();
    }

    @Override
    public void onCreateStakeholderSucceeded(String msg) {
        if (this.view != null) {
            this.view.onCreateStakeholderSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onCreateStakeholderFailed(String msg) {
        if (this.view != null) {
            this.view.onCreateStakeholderFailed(msg);
            this.view.hideProgress();
        }
    }

    // READ ORGANIZATIONS

    @Override
    public void readStakeholders() {
        iStakeholderInteractor readOrganizationsInteractor;
        readOrganizationsInteractor = new cReadStakeholdersInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
                stakeholderRepository,
                this);

        view.showProgress();
        readOrganizationsInteractor.execute();
    }


    @Override
    public void removeListener() {
        iStakeholderInteractor organizationInteractor;
        organizationInteractor = new cRemoveListenerInteractorImpl(
                executor,
                mainThread,
                stakeholderRepository,
                this);

        view.showProgress();
        organizationInteractor.execute();
    }

    @Override
    public void onReadStakeholdersRetrieved(cStakeholderModel organizationModels,
                                            String operation) {
        if (this.view != null) {
            this.view.onReadStakeholdersSucceeded(organizationModels, operation);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadStakeholdersFailed(String msg) {
        if (this.view != null) {
            this.view.onReadStakeholdersFailed(msg);
            this.view.hideProgress();
        }
    }

    // READ ORGANIZATION MEMBERS

    @Override
    public void readStakeholderMembers() {
        iStakeholderMembersInteractor readOrganizationMembersInteractor =
                new cReadStakeholderMembersInteractorImpl(
                        executor,
                        mainThread, this,
                        sharedPreferenceRepository,
                        stakeholderRepository
                );

        view.showProgress();
        readOrganizationMembersInteractor.execute();
    }

    @Override
    public void onReadStakeholderMembersRetrieved(List<cUserProfileModel> userProfileModels) {
        if (this.view != null) {
            this.view.onReadStakeholderMembersSucceeded(userProfileModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadSharedOrgsFailed(String msg) {

    }

    @Override
    public void onSharedOrgsRetrieved(ArrayList<cStakeholderModel> organizationModels) {

    }

    @Override
    public void onReadStakeholderMembersFailed(String msg) {
        if (this.view != null) {
            this.view.onReadStakeholderMembersFailed(msg);
            this.view.hideProgress();
        }
    }

    // PRESENTER FUNCTIONS

    @Override
    public void resume() {
        readStakeholders();
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
