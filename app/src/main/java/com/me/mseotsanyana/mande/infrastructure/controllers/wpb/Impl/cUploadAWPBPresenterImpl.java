package com.me.mseotsanyana.mande.infrastructure.controllers.wpb.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.wpb.Impl.cUploadAWPBInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.wpb.iUploadAWPBInteractor;
import com.me.mseotsanyana.mande.application.repository.awpb.iUploadAWPBRepository;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.controllers.wpb.iUploadAWPBPresenter;

public class cUploadAWPBPresenterImpl extends cAbstractPresenter implements iUploadAWPBPresenter,
        iUploadAWPBInteractor.Callback {
    private static String TAG = cUploadAWPBPresenterImpl.class.getSimpleName();

    private View view;
    private iUploadAWPBRepository uploadAWPBRepository;

    public cUploadAWPBPresenterImpl(IExecutor executor, IMainThread mainThread,
                                    View view, iUploadAWPBRepository uploadAWPBRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.uploadAWPBRepository = uploadAWPBRepository;
    }

    @Override
    public void uploadAWPBFromExcel() {

        iUploadAWPBInteractor uploadInteractor = new cUploadAWPBInteractorImpl(
                executor,
                mainThread,
                uploadAWPBRepository,
                this);

        view.showProgress();

        uploadInteractor.execute();
    }

    @Override
    public void onUploadAWPBCompleted(String msg) {
        if(this.view != null) {
            this.view.onUploadCompleted("Upload AWPB", msg);
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
            this.view.onUploadCompleted("Upload AWPB", message);
            this.view.hideProgress();
        }
    }
}
