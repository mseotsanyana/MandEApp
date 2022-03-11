package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.Impl.cReadStakeholderMembersInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.iStakeholderMembersInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iStakeholderRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iOrganizationMemberPresenter;

import java.util.List;

public class cOrganizationMemberPresenterImpl extends cAbstractPresenter implements
        iOrganizationMemberPresenter, iStakeholderMembersInteractor.Callback {
    //private static final String TAG = cOrganizationMemberPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iStakeholderRepository organizationRepository;

    //private final cInputValidation inputValidation;

    public cOrganizationMemberPresenterImpl(iExecutor executor, iMainThread mainThread,
                                            View view,
                                            iSharedPreferenceRepository sharedPreferenceRepository,
                                            iStakeholderRepository organizationRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.organizationRepository = organizationRepository;

        //this.inputValidation = new cInputValidation();
    }

    // CREATE

    // READ ORGANIZATION MEMBERS

    @Override
    public void readOrganizationMembers() {
        iStakeholderMembersInteractor readOrganizationMembersInteractor =
                new cReadStakeholderMembersInteractorImpl(
                        executor, mainThread, this,
                        sharedPreferenceRepository,
                        organizationRepository
                );

        view.showProgress();
        readOrganizationMembersInteractor.execute();
    }

    @Override
    public void onReadStakeholderMembersRetrieved(List<cUserProfileModel> userProfileModels) {
        if (this.view != null) {
            this.view.onReadOrganizationMembersSucceeded(userProfileModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadStakeholderMembersFailed(String msg) {
        if (this.view != null) {
            this.view.onReadOrganizationMembersFailed(msg);
            this.view.hideProgress();
        }
    }

    // PRESENTER FUNCTIONS

    @Override
    public void resume() {
        readOrganizationMembers();
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
