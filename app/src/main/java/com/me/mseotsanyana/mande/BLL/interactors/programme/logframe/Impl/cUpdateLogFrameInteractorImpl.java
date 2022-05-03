package com.me.mseotsanyana.mande.BLL.interactors.programme.logframe.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.programme.logframe.iLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.programme.iLogFrameRepository;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;

public class cUpdateLogFrameInteractorImpl extends cAbstractInteractor
        implements iLogFrameInteractor {
    private static String TAG = cUpdateLogFrameInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iLogFrameRepository logFrameRepository;

    private cLogFrameModel logFrameModel;
    private int position;

    public cUpdateLogFrameInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                         iLogFrameRepository logFrameRepository, Callback callback,
                                         cLogFrameModel logFrameModel, int position) {
        super(threadExecutor, mainThread);

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
}
