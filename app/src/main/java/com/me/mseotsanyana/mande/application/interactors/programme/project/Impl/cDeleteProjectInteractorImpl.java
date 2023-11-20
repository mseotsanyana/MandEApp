package com.me.mseotsanyana.mande.application.interactors.programme.project.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.iLogFrameInteractor;
import com.me.mseotsanyana.mande.application.repository.programme.iLogFrameRepository;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;

public class cDeleteProjectInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iLogFrameInteractor {
    private static String TAG = cDeleteProjectInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iLogFrameRepository logFrameRepository;
    private String logFrameID;
    private int position;

    public cDeleteProjectInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                        iLogFrameRepository logFrameRepository, Callback callback,
                                        String logFrameID) {
        super(threadExecutor, mainThread, null);

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

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}
