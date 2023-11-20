package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.session.organization.CReadOrganizationAccountsInteractorImpl;
import com.me.mseotsanyana.mande.application.ports.session.IOrganizationInteractor;
import com.me.mseotsanyana.mande.application.repository.session.IOrganizationRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iOrganizationAccountPresenter;

import java.util.Map;

public class cOrganizationAccountsPresenterImpl extends cAbstractPresenter implements
        iOrganizationAccountPresenter, IOrganizationInteractor.AccountsCallback {
    //private static final String TAG = cOrganizationMemberPresenterImpl.class.getSimpleName();

    private View view;
    private final ISessionManager sharedPreferenceRepository;
    private final IOrganizationRepository organizationRepository;

    //private final cInputValidation inputValidation;

    public cOrganizationAccountsPresenterImpl(IExecutor executor, IMainThread mainThread,
                                              View view,
                                              ISessionManager sharedPreferenceRepository,
                                              IOrganizationRepository organizationRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.organizationRepository = organizationRepository;

        //this.inputValidation = new cInputValidation();
    }

    // CREATE

    // READ ORGANIZATION MEMBERS

    @Override
    public void readOrganizationAccounts() {
        IOrganizationInteractor readOrganizationInteractor =
                new CReadOrganizationAccountsInteractorImpl(
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
