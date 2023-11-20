package com.me.mseotsanyana.mande.application.interactors.programme.project.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.shared.iRecordPermissionInteractor;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.domain.entities.models.common.cRecordPermissionModel;
import com.me.mseotsanyana.mande.application.repository.preference.iRecordPermissionRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.programme.iProjectRepository;

public class cUpdateRecordPermissionInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iRecordPermissionInteractor {
    //private static String TAG = cUpdateProjectInteractorImpl.class.getSimpleName();

    private final ISessionManager sharedPreferenceRepository;
    private final iRecordPermissionRepository recordPermissionRepository;
    private final iProjectRepository projectRepository;
    private final Callback callback;

    private final String projectServerID;
    private final cRecordPermissionModel recordPermissionModel;

    public cUpdateRecordPermissionInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                                 ISessionManager sharedPreferenceRepository,
                                                 iRecordPermissionRepository recordPermissionRepository,
                                                 iProjectRepository projectRepository, Callback callback,
                                                 String projectServerID,
                                                 cRecordPermissionModel recordPermissionModel) {
        super(threadExecutor, mainThread, null);

        if (sharedPreferenceRepository == null ||projectRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.recordPermissionRepository = recordPermissionRepository;
        this.projectRepository = projectRepository;
        this.callback = callback;

        this.projectServerID = projectServerID;
        this.recordPermissionModel = recordPermissionModel;
    }

    /* post a failure message */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onUpdateRecordPermissionsFailed(msg));
    }

    /* post a success message */
    private void postMessage(String msg) {
        mainThread.post(() -> callback.onUpdateRecordPermissionsSucceeded(msg));
    }

    @Override
    public void run() {
        /* update the logFrame object and insert it */
        projectRepository.updateRecordPermissions(projectServerID, recordPermissionModel,
                new iProjectRepository.iUpdateRecordPermissionsCallback() {
                    @Override
                    public void onUpdateRecordPermissionsSucceeded(String msg) {
                        postMessage(msg);
                    }

                    @Override
                    public void onUpdateRecordPermissionsFailed(String msg) {
                        notifyError(msg);
                    }
                });
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}
