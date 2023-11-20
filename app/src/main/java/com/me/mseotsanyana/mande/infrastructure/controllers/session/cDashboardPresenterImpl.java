package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.Impl.cReadLogFramesInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.iLogFrameInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.application.repository.programme.iLogFrameRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iDashboardPresenter;
import com.me.mseotsanyana.mande.infrastructure.services.CSessionManagerImpl;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cDashboardPresenterImpl extends cAbstractPresenter implements iDashboardPresenter,
        iLogFrameInteractor.Callback {

    //private static final String TAG = cLogFramePresenterImpl.class.getSimpleName();

    private View view;
    //private final ISessionManager sharedPreferenceRepository;
    private final iLogFrameRepository logFrameRepository;

    public cDashboardPresenterImpl(IExecutor executor, IMainThread mainThread,
                                   ISessionManager sessionManager,
                                   View view,
                                   iLogFrameRepository logFrameRepository) {
        super(executor, mainThread, sessionManager);

        this.view = view;
        //this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.logFrameRepository = logFrameRepository;
    }

    /* ======================================= END CREATE ======================================= */

    /* ======================================= START READ ======================================= */
    @Override
    public void readLogFrames() {
        iLogFrameInteractor readLogFrameInteractor = new cReadLogFramesInteractorImpl(
                executor,
                mainThread,
                null,
                logFrameRepository,
                this);

        view.showProgress();
        readLogFrameInteractor.execute();
    }

    @Override
    public void onLogFramesRetrieved(List<cTreeModel> treeModels) {
        if (this.view != null) {
            this.view.onReadLogFramesSucceeded(treeModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onLogFramesRetrieveFailed(String msg) {
        if (this.view != null) {
            this.view.onReadLogFramesFailed(msg);
            this.view.hideProgress();
        }
    }
    /* ======================================== END READ ======================================== */

    @Override
    public void onLogFrameCreated(cLogFrameModel logFrameModel, String msg) {

    }

    @Override
    public void onLogFrameCreateFailed(String msg) {

    }

    @Override
    public void onLogFrameUpdated(cLogFrameModel logFrameModel, int position, String msg) { }

    @Override
    public void onLogFrameUpdateFailed(String msg) { }

    @Override
    public void onDeleteLogFrameFailed(String msg) { }

    @Override
    public void onDeleteLogFramesSucceeded(String msg) { }

    @Override
    public void onUploadLogFrameCompleted(String msg) { }

    @Override
    public void onUploadLogFrameFailed(String msg) { }

    /* corresponding view functions */
    @Override
    public void resume() {
        readLogFrames();
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
    public void onError(String message) { }
}
