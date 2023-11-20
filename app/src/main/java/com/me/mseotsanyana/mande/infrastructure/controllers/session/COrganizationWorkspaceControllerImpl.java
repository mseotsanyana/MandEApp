package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.interactors.session.organization.CCreateOrganizationInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.organization.CDeleteOrganizationInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.organization.CReadOrganizationInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.organization.CChooseWorkspaceInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.organization.CUpdateOrganizationInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.CUserSignOutInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.workspace.CCreateWorkspaceInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.workspace.CDeleteWorkspaceInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.workspace.CReadWorkspacesInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.workspace.CUpdateWorkspaceInteractorImpl;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IOrganizationRepository;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;
import com.me.mseotsanyana.mande.application.repository.session.IWorkspaceRepository;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.infrastructure.ports.base.CAbstractController;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationWorkspaceController;

public class COrganizationWorkspaceControllerImpl extends CAbstractController
        implements IOrganizationWorkspaceController {

    private final IOrganizationRepository organizationRepository;
    private final IWorkspaceRepository workspaceRepository;
    private final IUserProfileRepository userProfileRepository;

    private final IViewModel iViewModel;
    private final IInteractor.IPresenter<IResponseDTO<Object>> iPresenter;

    public COrganizationWorkspaceControllerImpl(IExecutor executor, IMainThread mainThread,
                                                ISessionManager sessionManager,
                                                IViewModel iViewModel,
                                                IInteractor.IPresenter<IResponseDTO<Object>>
                                                        iPresenter,
                                                IOrganizationRepository organizationRepository,
                                                IWorkspaceRepository workspaceRepository,
                                                IUserProfileRepository userProfileRepository) {

        super(executor, mainThread, sessionManager);

        this.iViewModel = iViewModel;
        this.iPresenter = iPresenter;
        this.organizationRepository = organizationRepository;
        this.workspaceRepository = workspaceRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public void createOrganization(COrganizationModel organizationModel) {
        IInteractor iInteractor = new CCreateOrganizationInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                organizationRepository,
                organizationModel);

        iViewModel.showProgress();
        iInteractor.execute();
    }

    @Override
    public void readOrganizations() {
        IInteractor iInteractor = new CReadOrganizationInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                organizationRepository);

        iViewModel.showProgress();
        iInteractor.execute();
    }

    public void deleteOrganization(String organizationServerID) {
        IInteractor iInteractor = new CDeleteOrganizationInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                organizationRepository,
                organizationServerID);

        iViewModel.showProgress();
        iInteractor.execute();
    }

    @Override
    public void createWorkspace(CWorkspaceModel workspaceModel) {
        IInteractor iInteractor = new CCreateWorkspaceInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                workspaceRepository,
                workspaceModel);

        iViewModel.showProgress();
        iInteractor.execute();
    }

    @Override
    public void updateOrganization(COrganizationModel organizationModel) {
        IInteractor iInteractor = new CUpdateOrganizationInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                organizationRepository,
                organizationModel);

        iViewModel.showProgress();
        iInteractor.execute();
    }

    @Override
    public void readWorkspaces(String organizationServerID) {
        IInteractor iInteractor = new CReadWorkspacesInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                workspaceRepository,
                organizationServerID);

        iViewModel.showProgress();
        iInteractor.execute();
    }

    public void updateWorkspace(CWorkspaceModel workspaceModel) {
        IInteractor iInteractor = new CUpdateWorkspaceInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                workspaceRepository,
                workspaceModel);

        iViewModel.showProgress();
        iInteractor.execute();
    }

    @Override
    public void deleteWorkspace(int workspaceBITS, CWorkspaceModel workspaceModel) {
        IInteractor iInteractor = new CDeleteWorkspaceInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                workspaceRepository,
                workspaceBITS,
                workspaceModel);

        iViewModel.showProgress();
        iInteractor.execute();
    }

    @Override
    public void chooseWorkspaceRequest(CWorkspaceModel workspaceModel) {
        IInteractor iInteractor;
        iInteractor = new CChooseWorkspaceInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                userProfileRepository,
                workspaceModel);

        iViewModel.showProgress();
        iInteractor.execute();
    }

    @Override
    public void signOutWithEmailAndPassword() {
        IInteractor iInteractor = new CUserSignOutInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                userProfileRepository);

        iViewModel.showProgress();

        iInteractor.execute();
    }

    @Override
    public void removeListener() {

    }

    /************************** organization workspace lifecycle methods **************************/

    @Override
    public void resume() {
        readOrganizations();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }
}
