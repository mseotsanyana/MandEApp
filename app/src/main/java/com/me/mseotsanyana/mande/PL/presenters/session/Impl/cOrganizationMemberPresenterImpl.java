package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl.cReadOrganizationMembersInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iOrganizationMemberPresenter;

import java.util.List;

public class cOrganizationMemberPresenterImpl extends cAbstractPresenter implements
        iOrganizationMemberPresenter, iOrganizationInteractor.MemberCallback {
    //private static final String TAG = cOrganizationMemberPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iOrganizationRepository organizationRepository;

    //private final cInputValidation inputValidation;

    public cOrganizationMemberPresenterImpl(iExecutor executor, iMainThread mainThread,
                                            View view,
                                            iSharedPreferenceRepository sharedPreferenceRepository,
                                            iOrganizationRepository organizationRepository) {
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
        iOrganizationInteractor readOrganizationInteractor =
                new cReadOrganizationMembersInteractorImpl(
                        executor, mainThread, this,
                        sharedPreferenceRepository,
                        organizationRepository
                );

        view.showProgress();
        readOrganizationInteractor.execute();
    }

    @Override
    public void onReadOrganizationMembersRetrieved(List<CUserProfileModel> userProfileModels) {
        if (this.view != null) {
            this.view.onReadOrganizationMembersSucceeded(userProfileModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadOrganizationMembersFailed(String msg) {
        if (this.view != null) {
            this.view.onReadOrganizationMembersFailed(msg);
            this.view.hideProgress();
        }
    }

    // READ ORGANIZATION MEMBERS

//    @Override
//    public void readOrganizationMembers() {
//        iOrganizationInteractor readOrganizationsInteractor =
//                new cReadOrganizationMembersInteractorImpl(
//                        executor,
//                        mainThread, this,
//                        sharedPreferenceRepository,
//                        organizationRepository
//                );
//
//        view.showProgress();
//        readOrganizationsInteractor.execute();
//    }

//    @Override
//    public void onReadOrganizationMembersRetrieved(List<CUserProfileModel> userProfileModels) {
//        if (this.view != null) {
//            this.view.onReadOrganizationMembersSucceeded(userProfileModels);
//            this.view.hideProgress();
//        }
//    }

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
