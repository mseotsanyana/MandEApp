package com.me.mseotsanyana.mande.interfaceadapters.controllers.session;

import com.me.mseotsanyana.mande.usecases.executor.iExecutor;
import com.me.mseotsanyana.mande.usecases.executor.iMainThread;
import com.me.mseotsanyana.mande.usecases.interactors.session.organization.Impl.cReadOrganizationAccountsInteractorImpl;
import com.me.mseotsanyana.mande.usecases.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.mande.usecases.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.usecases.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iOrganizationAccountPresenter;

import java.util.Map;

public class cOrganizationAccountsPresenterImpl extends cAbstractPresenter implements
        iOrganizationAccountPresenter, iOrganizationInteractor.AccountsCallback {
    //private static final String TAG = cOrganizationMemberPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iOrganizationRepository organizationRepository;

    //private final cInputValidation inputValidation;

    public cOrganizationAccountsPresenterImpl(iExecutor executor, iMainThread mainThread,
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
    public void readOrganizationAccounts() {
        iOrganizationInteractor readOrganizationInteractor =
                new cReadOrganizationAccountsInteractorImpl(
                        executor, mainThread, this,
                        sharedPreferenceRepository,
                        organizationRepository
                );

        view.showProgress();
        readOrganizationInteractor.execute();
    }

    @Override
    public void onReadOrganizationAccountsSucceeded(Map<String, Object> orgAccountsMap, String operation) {
        if (this.view != null) {
            this.view.onReadOrganizationAccountsSucceeded(orgAccountsMap, operation);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadOrganizationAccountsFailed(String msg) {
        if (this.view != null) {
            this.view.onReadOrganizationAccountsFailed(msg);
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
        readOrganizationAccounts();
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
