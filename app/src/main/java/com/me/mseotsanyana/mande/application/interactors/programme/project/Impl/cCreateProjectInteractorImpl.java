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

public class cCreateProjectInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iLogFrameInteractor {
    private static String TAG = cCreateProjectInteractorImpl.class.getSimpleName();

    private iLogFrameRepository logFrameRepository;
    private Callback callback;
    private cLogFrameModel logFrameModel;

    public cCreateProjectInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                        ISessionManager sessionManagerRepository,
                                        iLogFrameRepository logFrameRepository, Callback callback,
                                        cLogFrameModel logFrameModel) {
        super(threadExecutor, mainThread, null);

        if (logFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.logFrameRepository = logFrameRepository;
        this.callback = callback;
        this.logFrameModel = logFrameModel;

//        /* add common attributes */
//        this.logFrameModel.setOwnerID(sessionManagerRepository.loadUserID());
//        this.logFrameModel.setOrgID(sessionManagerRepository.loadOrganizationID());
//        this.logFrameModel.setGroupBITS(sessionManagerRepository.loadPrimaryRoleBITS()|
//                sessionManagerRepository.loadSecondaryRoleBITS());
//        this.logFrameModel.setPermsBITS(sessionManagerRepository.loadDefaultPermsBITS());
//        this.logFrameModel.setStatusBITS(sessionManagerRepository.loadDefaultStatusBITS());
        this.logFrameModel.setCreatedDate(new Date());
        this.logFrameModel.setModifiedDate(new Date());
        //this.logFrameModel.setSyncedDate(new Date());
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
                callback.onLogFrameCreated(logFrameModel, msg);
            }
        });
    }

    @Override
    public void run() {

        /* create a new logFrame object and insert it */
        boolean result = logFrameRepository.createLogFrameModel(logFrameModel);

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
