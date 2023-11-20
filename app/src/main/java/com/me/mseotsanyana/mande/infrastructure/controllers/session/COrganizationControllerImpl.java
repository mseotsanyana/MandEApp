package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.infrastructure.ports.base.CAbstractController;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationController;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.session.organization.CCreateOrganizationInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.organization.CRemoveListenerInteractorImpl;
import com.me.mseotsanyana.mande.application.repository.session.IOrganizationRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;

import java.util.Map;

public class COrganizationControllerImpl extends CAbstractController implements
        IOrganizationController {
    private static final String TAG = COrganizationControllerImpl.class.getSimpleName();

    private final IViewModel iViewModel;
    private final IInteractor.IPresenter<IResponseDTO<Object>> iPresenter;
    private final IOrganizationRepository organizationRepository;

    //private final cInputValidation inputValidation;

    public COrganizationControllerImpl(IExecutor executor, IMainThread mainThread,
                                       ISessionManager sessionManager,
                                       IViewModel iViewModel,
                                       IInteractor.IPresenter<IResponseDTO<Object>>
                                               iPresenter,
                                       IOrganizationRepository organizationRepository) {
        super(executor, mainThread, sessionManager);

        this.iViewModel = iViewModel;
        this.iPresenter = iPresenter;
        this.organizationRepository = organizationRepository;
        //this.inputValidation = new cInputValidation();
    }

    // CREATE ORGANIZATION

    @Override
    public void createOrganization(COrganizationModel organizationModel) {
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

        IInteractor iInteractor = new CCreateOrganizationInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                organizationRepository,
                organizationModel);

        iViewModel.showProgress();

        iInteractor.execute();
    }

    // READ ORGANIZATIONS

    @Override
    public void readOrganizationWorkspaces() {
//        IWorkspaceInteractor readOrganizationsInteractor;
//        readOrganizationsInteractor = new CReadOrganizationInteractorImpl(
//                new COrganizationWorkspacePresenterImpl(this.modelView),
//                executor,
//                mainThread,
//                sharedPreferenceRepository,
//                organizationRepository);
//
//        modelView.showProgress();
//        readOrganizationsInteractor.execute();
    }


    @Override
    public void removeListener() {
        IInteractor iInteractor = new CRemoveListenerInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                organizationRepository);

        iViewModel.showProgress();
        iInteractor.execute();
    }
//
//    @Override
//    public void switchOrganizationWorkspace(CWorkspaceModel workspaceModel) {
//        iOrganizationInteractor switchOrganizationWorkspaceInteractor;
//        switchOrganizationWorkspaceInteractor = new cSwitchOrganizationWorkspaceInteractorImpl(
//                executor,
//                mainThread,
//                sharedPreferenceRepository,
//                userProfileRepository,
//                new COrganizationPresenterImpl(this.modelView),
//                workspaceModel);
//
//        //view.showProgress();
//        switchOrganizationWorkspaceInteractor.execute();
//    }

//    @Override
//    public void onCreateOrganizationSucceeded(String msg) {
//        if (this.view != null) {
//            this.view.onCreateOrganizationSucceeded(msg);
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void onCreateOrganizationFailed(String msg) {
//        if (this.view != null) {
//            this.view.onCreateOrganizationFailed(msg);
//            this.view.hideProgress();
//        }
//    }


//    @Override
//    public void onReadOrganizationsSucceeded(List<cTreeModel> organizationModels) {
//        if (this.view != null) {
//            this.view.onReadOrganizationsSucceeded(organizationModels);
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void onReadOrganizationsFailed(String msg) {
//        if (this.view != null) {
//            this.view.onReadOrganizationsFailed(msg);
//            this.view.hideProgress();
//        }
//    }


//    @Override
//    public void onSwitchOrganizationWorkspaceFailed(String msg) {
//        if (this.view != null) {
//            this.view.onSwitchOrganizationWorkspaceFailed(msg);
//            //this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void onSwitchOrganizationWorkspaceSucceeded(String msg) {
//        if (this.view != null) {
//            this.view.onSwitchOrganizationWorkspaceSucceeded(msg);
//            //this.view.hideProgress();
//        }
//    }



    // LOGOUT USER



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


    // PRESENTER FUNCTIONS

    /********************************** view life cycle methods ***********************************/

    @Override
    public void resume() {
        readOrganizationWorkspaces();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if (this.iViewModel != null) {
            this.iViewModel.hideProgress();
        }
    }

    @Override
    public void destroy() {
        //this.modelView = null;
    }

    @Override
    public void onError(String message) {

    }
}
