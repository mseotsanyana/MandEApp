package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.session.menu.Impl.cReadMenuInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.menu.iReadMenuInteractor;
import com.me.mseotsanyana.mande.application.repository.session.iMenuRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iMenuPresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cMenuPresenterImpl extends cAbstractPresenter implements iMenuPresenter,
        iReadMenuInteractor.Callback{
    //private static final String TAG = cMenuPresenterImpl.class.getSimpleName();

    private View view;
    private final ISessionManager sessionManagerRepository;
    private final iMenuRepository menuRepository;

    public cMenuPresenterImpl(IExecutor executor, IMainThread mainThread,
                              View view,
                              ISessionManager sessionManagerRepository,
                              iMenuRepository menuRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.sessionManagerRepository = sessionManagerRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public void onReadMenuFailed(String msg) {
        if(this.view != null) {
            this.view.onReadMenuFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadMenuSucceeded(List<cTreeModel> treeModels) {
        if(this.view != null) {
            this.view.onReadMenuSucceeded(treeModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void readMenuItems() {
        iReadMenuInteractor readMenuInteractor = new cReadMenuInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                menuRepository,
                this);

        view.showProgress();
        readMenuInteractor.execute();
    }

    /* ===================================== END PREFERENCE ===================================== */

    /* corresponding view functions */
    @Override
    public void resume() {
        readMenuItems();
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
