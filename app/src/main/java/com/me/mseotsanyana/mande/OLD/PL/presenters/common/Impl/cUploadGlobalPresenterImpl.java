package com.me.mseotsanyana.mande.OLD.PL.presenters.common.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.shared.Impl.cUploadGlobalInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.shared.iUploadGlobalInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.iUploadGlobalRepository;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.OLD.PL.presenters.common.iUploadGlobalPresenter;

public class cUploadGlobalPresenterImpl extends cAbstractPresenter implements iUploadGlobalPresenter,
        iUploadGlobalInteractor.Callback {
    private static String TAG = cUploadGlobalPresenterImpl.class.getSimpleName();

    private View view;
    private iUploadGlobalRepository uploadGlobalRepository;

    public cUploadGlobalPresenterImpl(IExecutor executor, IMainThread mainThread,
                                      View view, iUploadGlobalRepository uploadGlobalRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.uploadGlobalRepository = uploadGlobalRepository;
    }

    @Override
    public void uploadGlobalFromExcel() {

        iUploadGlobalInteractor uploadInteractor = new cUploadGlobalInteractorImpl(
                executor,
                mainThread,
                uploadGlobalRepository,
                this);

        view.showProgress();

        uploadInteractor.execute();
    }

    @Override
    public void onUploadGlobalCompleted(String msg) {
        if(this.view != null) {
            this.view.onUploadCompleted("Upload Global", msg);
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
            this.view.onUploadCompleted("Upload Global", message);
            this.view.hideProgress();
        }
    }
}