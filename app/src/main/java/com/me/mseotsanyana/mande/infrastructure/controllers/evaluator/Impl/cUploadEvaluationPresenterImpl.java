package com.me.mseotsanyana.mande.infrastructure.controllers.evaluator.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.evaluation.Impl.cUploadEvaluationInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.evaluation.iUploadEvaluationInteractor;
import com.me.mseotsanyana.mande.application.repository.evaluator.iUploadEvaluationRepository;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.controllers.evaluator.iUploadEvaluationPresenter;

public class cUploadEvaluationPresenterImpl extends cAbstractPresenter implements iUploadEvaluationPresenter,
        iUploadEvaluationInteractor.Callback {
    private static String TAG = cUploadEvaluationPresenterImpl.class.getSimpleName();

    private View view;
    private iUploadEvaluationRepository uploadEvaluationRepository;

    public cUploadEvaluationPresenterImpl(IExecutor executor, IMainThread mainThread,
                                          View view, iUploadEvaluationRepository uploadEvaluationRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.uploadEvaluationRepository = uploadEvaluationRepository;
    }

    @Override
    public void uploadEvaluationFromExcel() {

        iUploadEvaluationInteractor uploadInteractor = new cUploadEvaluationInteractorImpl(
                executor,
                mainThread,
                uploadEvaluationRepository,
                this);

        view.showProgress();

        uploadInteractor.execute();
    }

    @Override
    public void onUploadEvaluationCompleted(String msg) {
        if(this.view != null) {
            this.view.onUploadCompleted("Upload Evaluation", msg);
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
        if(this.view != null) {
            this.view.onUploadCompleted("Upload Evaluation", message);
            this.view.hideProgress();
        }
    }
}
