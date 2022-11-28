package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.programme.logframe.Impl.cReadLogFramesInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.programme.logframe.iLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.BLL.repository.programme.iLogFrameRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iDashboardPresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cDashboardPresenterImpl extends cAbstractPresenter implements iDashboardPresenter,
        iLogFrameInteractor.Callback {

    //private static final String TAG = cLogFramePresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iLogFrameRepository logFrameRepository;

    public cDashboardPresenterImpl(iExecutor executor, iMainThread mainThread,
                                   View view,
                                   iSharedPreferenceRepository sharedPreferenceRepository,
                                   iLogFrameRepository logFrameRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.logFrameRepository = logFrameRepository;
    }

    /* ======================================= END CREATE ======================================= */

    /* ======================================= START READ ======================================= */
    @Override
    public void readLogFrames() {
        iLogFrameInteractor readLogFrameInteractor = new cReadLogFramesInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
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
