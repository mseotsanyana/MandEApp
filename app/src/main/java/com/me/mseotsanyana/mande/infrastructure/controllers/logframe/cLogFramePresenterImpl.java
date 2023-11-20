package com.me.mseotsanyana.mande.infrastructure.controllers.logframe;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.Impl.cCreateLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.Impl.cDeleteLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.Impl.cDeleteLogFramesInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.Impl.cReadLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.Impl.cReadLogFramesInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.Impl.cUpdateLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.Impl.cUploadLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.iLogFrameInteractor;
import com.me.mseotsanyana.mande.application.repository.programme.iLogFrameRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.logframe.iLogFramePresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cLogFramePresenterImpl extends cAbstractPresenter implements iLogFramePresenter,
        iLogFrameInteractor.Callback {

    //private static final String TAG = cLogFramePresenterImpl.class.getSimpleName();

    private View view;
    private final ISessionManager sharedPreferenceRepository;
    private final iLogFrameRepository logFrameRepository;

    public cLogFramePresenterImpl(IExecutor executor, IMainThread mainThread,
                                  View view,
                                  ISessionManager sharedPreferenceRepository,
                                  iLogFrameRepository logFrameRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.logFrameRepository = logFrameRepository;
    }

    /* ====================================== START CREATE ====================================== */
    /* create a sub-logframe model */
    @Override
    public void createLogFrameModel(cLogFrameModel logFrameModel) {
        iLogFrameInteractor createLogFrameInteractor =
                new cCreateLogFrameInteractorImpl(executor, mainThread,
                        sharedPreferenceRepository,
                        logFrameRepository,
                        this,
                        logFrameModel);

        view.showProgress();
        createLogFrameInteractor.execute();
    }

    @Override
    public void createSubLogFrameModel(String logFrameID, cLogFrameModel logSubFrameModel) {

    }

    /* create success  response to the view */
    @Override
    public void onLogFrameCreated(cLogFrameModel logFrameModel, String msg) {
        if (this.view != null) {
            this.view.onLogFrameCreated(logFrameModel, msg);
            this.view.hideProgress();
        }
    }

    /* create failure response to the view */
    @Override
    public void onLogFrameCreateFailed(String msg) {
        if (this.view != null) {
            this.view.onLogFrameCreateFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ======================================= END CREATE ======================================= */

    /* ======================================= START READ ======================================= */
    @Override
    public void readLogFrames() {
        iLogFrameInteractor readLogFramesInteractor = new cReadLogFramesInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
                logFrameRepository,
                this);

        view.showProgress();
        readLogFramesInteractor.execute();
    }

    @Override
    public void readLogFrame(String projectServerID) {
        iLogFrameInteractor readLogFrameInteractor = new cReadLogFrameInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
                logFrameRepository,
                this, projectServerID);

        view.showProgress();
        readLogFrameInteractor.execute();
    }

    @Override
    public void onLogFramesRetrieved(List<cTreeModel> treeModels) {
        if (this.view != null) {
            this.view.onRetrieveLogFramesCompleted(treeModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onLogFramesRetrieveFailed(String msg) {
        if (this.view != null) {
            this.view.onRetrieveLogFramesFailed(msg);
            this.view.hideProgress();
        }
    }




    /* ======================================== END READ ======================================== */

    /* ====================================== START UPDATE ====================================== */
    @Override
    public void updateLogFrame(cLogFrameModel logFrameModel, int position) {
        iLogFrameInteractor updateLogFrameInteractor =
                new cUpdateLogFrameInteractorImpl(executor, mainThread,
                        logFrameRepository,
                        this,
                        logFrameModel,
                        position);

        view.showProgress();
        updateLogFrameInteractor.execute();
    }

    @Override
    public void onLogFrameUpdated(cLogFrameModel logFrameModel, int position, String msg) {
        if (this.view != null) {
            this.view.onLogFrameUpdated(logFrameModel, position, msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onLogFrameUpdateFailed(String msg) {

    }
    /* ======================================= END UPDATE ======================================= */

    /* ====================================== START DELETE ====================================== */
    @Override
    public void deleteLogFrameModel(String logFrameID) {
        iLogFrameInteractor deleteLogFrameInteractor =
                new cDeleteLogFrameInteractorImpl(executor, mainThread,
                        logFrameRepository,
                        this,
                        logFrameID);

        view.showProgress();
        deleteLogFrameInteractor.execute();
    }

    @Override
    public void deleteLogFrameModels() {
        iLogFrameInteractor deleteLogFramesInteractor =
                new cDeleteLogFramesInteractorImpl(executor, mainThread,
                        sharedPreferenceRepository,
                        logFrameRepository,
                        this);

        view.showProgress();
        deleteLogFramesInteractor.execute();
    }

    @Override
    public void onDeleteLogFramesSucceeded(String msg) {
        if (this.view != null) {
            this.view.onDeleteLogFramesSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onDeleteLogFrameFailed(String msg) {
        if (this.view != null) {
            this.view.onDeleteLogFrameFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ======================================= END DELETE ======================================= */

    /* ====================================== START UPLOAD ====================================== */
    @Override
    public void uploadLogFrameFromExcel(String filePath) {
        iLogFrameInteractor uploadLogFrameInteractor =
                new cUploadLogFrameInteractorImpl(executor, mainThread,
                        sharedPreferenceRepository,
                        logFrameRepository,
                        this,
                        filePath);

        view.showProgress();
        uploadLogFrameInteractor.execute();
    }

    /* ======================================= END UPLOAD ======================================= */

    /* ==================================== START PREFERENCE ==================================== */

    @Override
    public void onUploadLogFrameCompleted(String msg) {
        if (this.view != null) {
            this.view.onUploadLogFrameCompleted(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUploadLogFrameFailed(String msg) {
        if (this.view != null) {
            this.view.onUploadLogFrameFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ===================================== END PREFERENCE ===================================== */


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
    public void onError(String message) {

    }
}
