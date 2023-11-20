package com.me.mseotsanyana.mande.application.interactors.programme.project.Impl;

import com.me.mseotsanyana.mande.application.executor.iExecutor;
import com.me.mseotsanyana.mande.application.executor.iMainThread;
import com.me.mseotsanyana.mande.application.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.iLogFrameInteractor;
import com.me.mseotsanyana.mande.application.repository.programme.iLogFrameRepository;

public class cDeleteProjectInteractorImpl extends cAbstractInteractor
        implements iLogFrameInteractor {
    private static String TAG = cDeleteProjectInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iLogFrameRepository logFrameRepository;
    private String logFrameID;
    private int position;

    public cDeleteProjectInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                        iLogFrameRepository logFrameRepository, Callback callback,
                                        String logFrameID) {
        super(threadExecutor, mainThread);

        if (logFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.logFrameRepository = logFrameRepository;
        this.callback = callback;
        this.logFrameID = logFrameID;
        //this.position = position;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                //callback.onLogFrameDeleteFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                //callback.onLogFrameDeleted(position, msg);
            }
        });
    }

    @Override
    public void run() {
        /* delete a logframe model */
        boolean result = logFrameRepository.deleteLogFrame(logFrameID);

        if(result){
            postMessage("Successfully deleted");
        }else {
            notifyError("Failed to delete !!");
        }
    }
}
