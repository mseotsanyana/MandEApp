package com.me.mseotsanyana.mande.PL.presenters.raid.Impl;

import com.me.mseotsanyana.mande.application.executor.iExecutor;
import com.me.mseotsanyana.mande.application.executor.iMainThread;
import com.me.mseotsanyana.mande.application.interactors.raid.Impl.cUploadRAIDInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.raid.iUploadRAIDInteractor;
import com.me.mseotsanyana.mande.application.repository.raid.iUploadRAIDRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.raid.iUploadRAIDPresenter;

public class cUploadRAIDPresenterImpl extends cAbstractPresenter implements iUploadRAIDPresenter,
        iUploadRAIDInteractor.Callback {
    private static String TAG = cUploadRAIDPresenterImpl.class.getSimpleName();

    private View view;
    private iUploadRAIDRepository uploadRAIDRepository;

    public cUploadRAIDPresenterImpl(iExecutor executor, iMainThread mainThread,
                                    View view, iUploadRAIDRepository uploadRAIDRepository) {
        super(executor, mainThread);

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
