package com.me.mseotsanyana.mande.infrastructure.controllers.raid.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.raid.Impl.cUploadRAIDInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.raid.iUploadRAIDInteractor;
import com.me.mseotsanyana.mande.application.repository.raid.iUploadRAIDRepository;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.controllers.raid.iUploadRAIDPresenter;

public class cUploadRAIDPresenterImpl extends cAbstractPresenter implements iUploadRAIDPresenter,
        iUploadRAIDInteractor.Callback {
    private static String TAG = cUploadRAIDPresenterImpl.class.getSimpleName();

    private View view;
    private iUploadRAIDRepository uploadRAIDRepository;

    public cUploadRAIDPresenterImpl(IExecutor executor, IMainThread mainThread,
                                    View view, iUploadRAIDRepository uploadRAIDRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.uploadRAIDRepository = uploadRAIDRepository;
    }

    @Override
    public void uploadRAIDFromExcel() {

        iUploadRAIDInteractor uploadInteractor = new cUploadRAIDInteractorImpl(
                executor,
                mainThread,
                uploadRAIDRepository,
                this);

        view.showProgress();

        uploadInteractor.execute();
    }

    @Override
    public void onUploadRAIDCompleted(String msg) {
        if(this.view != null) {
            this.view.onUploadCompleted("Upload RAID",msg);
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
            this.view.onUploadCompleted("Upload RAID", message);
            this.view.hideProgress();
        }
    }
}
