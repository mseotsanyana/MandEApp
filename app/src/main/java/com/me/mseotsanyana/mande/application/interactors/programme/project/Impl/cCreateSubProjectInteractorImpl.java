package com.me.mseotsanyana.mande.application.interactors.programme.project.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.iLogFrameInteractor;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.application.repository.programme.iLogFrameRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;

import java.util.Date;

public class cCreateSubProjectInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iLogFrameInteractor {
    private static String TAG = cCreateSubProjectInteractorImpl.class.getSimpleName();

    private iLogFrameRepository logSubFrameRepository;
    private Callback callback;
    private String logFrameID;
    private cLogFrameModel logSubFrameModel;

    public cCreateSubProjectInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                           ISessionManager sessionManagerRepository,
                                           iLogFrameRepository logSubFrameRepository,
                                           Callback callback, String logFrameID,
                                           cLogFrameModel logSubFrameModel) {
        super(threadExecutor, mainThread, null);

        if (logSubFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.logSubFrameRepository = logSubFrameRepository;
        this.callback = callback;
        this.logFrameID = logFrameID;
        this.logSubFrameModel = logSubFrameModel;

        /* add common attributes */
//        this.logSubFrameModel.setOwnerID(sessionManagerRepository.loadUserID());
//        this.logSubFrameModel.setOrgID(sessionManagerRepository.loadOrganizationID());
//        this.logSubFrameModel.setGroupBITS(sessionManagerRepository.loadPrimaryRoleBITS()|
//                sessionManagerRepository.loadSecondaryRoleBITS());
//        this.logSubFrameModel.setPermsBITS(sessionManagerRepository.loadDefaultPermsBITS());
//        this.logSubFrameModel.setStatusBITS(sessionManagerRepository.loadDefaultStatusBITS());
        this.logSubFrameModel.setCreatedDate(new Date());
        this.logSubFrameModel.setModifiedDate(new Date());
        //this.logSubFrameModel.setSyncedDate(new Date());
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLogFrameCreateFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLogFrameCreated(logSubFrameModel, msg);
            }
        });
    }

    @Override
    public void run() {

        /* create a new logFrame object and insert it */
        boolean result = logSubFrameRepository.createSubLogFrameModel(logFrameID, logSubFrameModel);

        if(result){
            postMessage("Successfully added");
        }else {
            notifyError("Failed to add !!");
        }
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}
