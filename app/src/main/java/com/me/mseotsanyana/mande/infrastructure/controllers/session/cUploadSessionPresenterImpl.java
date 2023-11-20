package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.session.Impl.cUploadSessionInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.iUploadSessionInteractor;
import com.me.mseotsanyana.mande.application.repository.session.iUploadSessionRepository;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iUploadSessionPresenter;

public class cUploadSessionPresenterImpl extends cAbstractPresenter implements iUploadSessionPresenter,
        iUploadSessionInteractor.Callback {
    private static String TAG = cUploadSessionPresenterImpl.class.getSimpleName();

    private View view;
    private iUploadSessionRepository uploadSessionRepository;

    public cUploadSessionPresenterImpl(IExecutor executor, IMainThread mainThread,
                                       View view, iUploadSessionRepository uploadSessionRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.uploadSessionRepository = uploadSessionRepository;
    }

    @Override
    public void uploadSessionFromExcel() {

        iUploadSessionInteractor uploadInteractor = new cUploadSessionInteractorImpl(
                executor,
                mainThread,
                uploadSessionRepository,
                this); /* PresenterImpl passed to InteractorImpl */

        view.showProgress();

        uploadInteractor.execute();
    }

    @Override
    public void onUploadingSessionFailed(String msg) {
        if(this.view != null) {
            this.view.onUploadCompleted("Uploading Session",msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUploadingSessionSucceeded(String message) {
        if(this.view != null) {
            this.view.onUploadCompleted("Uploading Session", message);
            this.view.hideProgress();
        }
    }

    @Override
    public void resume() {

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
