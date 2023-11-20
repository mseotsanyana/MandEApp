package com.me.mseotsanyana.mande.application.interactors.programme.logframe.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.iLogFrameInteractor;
import com.me.mseotsanyana.mande.application.repository.programme.iLogFrameRepository;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cLogFrameModel;

public class cUpdateLogFrameInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iLogFrameInteractor {
    private static String TAG = cUpdateLogFrameInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iLogFrameRepository logFrameRepository;

    private cLogFrameModel logFrameModel;
    private int position;

    public cUpdateLogFrameInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                         iLogFrameRepository logFrameRepository, Callback callback,
                                         cLogFrameModel logFrameModel, int position) {
        super(threadExecutor, mainThread, null);

        if (logFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.logFrameRepository = logFrameRepository;
        this.callback = callback;

        this.logFrameModel = logFrameModel;
        this.position = position;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLogFrameUpdateFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLogFrameUpdated(logFrameModel, position, msg);
            }
        });
    }

    @Override
    public void run() {
        /* update the logFrame object and insert it */
        if(logFrameRepository.updateLogFrameModel(logFrameModel)){
            postMessage("Successfully updated");
        }else {
            notifyError("Failed to update !!");
        }
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}
