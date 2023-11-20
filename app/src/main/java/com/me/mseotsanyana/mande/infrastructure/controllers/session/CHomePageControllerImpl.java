package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.interactors.session.homepage.CSwitchWorkspaceInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.userprofile.CUserSignOutInteractorImpl;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;

import com.me.mseotsanyana.mande.application.interactors.session.homepage.CHomePageInteractorImpl;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IHomePageController;

import java.util.Map;

public class CHomePageControllerImpl extends cAbstractPresenter implements IHomePageController {

    private IHomePageController.IViewModel iViewModel;
    private final IInteractor.IPresenter<IResponseDTO<Object>> iPresenter;
    private final IUserProfileRepository userProfileRepository;


    public CHomePageControllerImpl(IExecutor executor, IMainThread mainThread,
                                   ISessionManager sessionManager,
                                   IHomePageController.IViewModel iViewModel,
                                   IInteractor.IPresenter<IResponseDTO<Object>> iPresenter,
                                   IUserProfileRepository userProfileRepository) {
        super(executor, mainThread, sessionManager);

        this.iViewModel = iViewModel;
        this.iPresenter = iPresenter;
        this.userProfileRepository = userProfileRepository;
    }

    /************************************* executing use cases ************************************/

    @Override
    public void loadHomePageRequest() {
        IInteractor iInteractor = new CHomePageInteractorImpl(
                executor,
                mainThread,
                sessionManager,
                iPresenter);

        iViewModel.showProgress();
        iInteractor.execute();
    }

    @Override
    public void switchWorkspaceRequest() {
        IInteractor iInteractor = new CSwitchWorkspaceInteractorImpl(
                executor,
                mainThread,
                sessionManager,
                iPresenter);

        iViewModel.showProgress();
        iInteractor.execute();
    }

    @Override
    public void signOutRequest() {
        IInteractor iInteractor = new CUserSignOutInteractorImpl(
                executor, mainThread, sessionManager,
                iPresenter,
                userProfileRepository);

        iViewModel.showProgress();

        iInteractor.execute();
    }

    /***************************** homepage fragment lifecycle methods ****************************/

    @Override
    public void resume() {
        loadHomePageRequest();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if(this.iViewModel != null){
            this.iViewModel.hideProgress();
        }
    }

    @Override
    public void destroy() {
        this.iViewModel = null;
    }

    @Override
    public void onError(String message) {

    }
}
//    @Override
//    public void onReadHomePageFailed(String msg) {
//        if(this.view != null) {
//            this.view.onReadHomePageFailed(msg);
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void onReadHomePageSucceeded(CUserProfileModel userProfileModel,
//                                        List<cMenuModel> menuModels) {
//        if(this.view != null) {
//            this.view.onReadHomePageSucceeded(userProfileModel, menuModels);
//            this.view.hideProgress();
//        }
//    }

//    @Override
//    public void onReadMenuItemsSucceeded(List<cMenuModel> menuModels) {
//        if(this.view != null) {
//            this.view.onReadMenuItemsSucceeded(menuModels);
//        }
//    }
//
//    @Override
//    public void onDefaultHomePageSucceeded(List<cMenuModel> menuModels) {
//        if(this.view != null) {
//            this.view.onDefaultHomePageSucceeded(menuModels);
//        }
//    }