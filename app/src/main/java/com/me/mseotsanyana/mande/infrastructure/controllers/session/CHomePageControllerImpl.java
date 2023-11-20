package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;

import com.me.mseotsanyana.mande.application.interactors.session.session.Impl.cHomePageInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.session.iHomePageInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.cMenuModel;
import com.me.mseotsanyana.mande.application.repository.session.iHomePageRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iHomePagePresenter;

import java.util.List;

public class cHomePagePresenterImpl extends cAbstractPresenter implements iHomePagePresenter,
        iHomePageInteractor.Callback{

    private View view;
    private final ISessionManager sharedPreferenceRepository;
    private final iHomePageRepository homePageRepository;

    public cHomePagePresenterImpl(IExecutor executor, IMainThread mainThread, View view,
                                  ISessionManager sharedPreferenceRepository,
                                  iHomePageRepository homePageRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.homePageRepository = homePageRepository;
    }

    @Override
    public void onReadHomePageFailed(String msg) {
        if(this.view != null) {
            this.view.onReadHomePageFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadHomePageSucceeded(CUserProfileModel userProfileModel,
                                        List<cMenuModel> menuModels) {
        if(this.view != null) {
            this.view.onReadHomePageSucceeded(userProfileModel, menuModels);
            this.view.hideProgress();
        }
    }

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

    @Override
    public void loadHomePage() {
        iHomePageInteractor homePageInteractor = new cHomePageInteractorImpl(
                executor,
                mainThread,this,
                sharedPreferenceRepository,
                homePageRepository);

        view.showProgress();
        homePageInteractor.execute();
    }

    /* general presentation methods */

    @Override
    public void resume() {
        loadHomePage();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if(this.view != null){
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
