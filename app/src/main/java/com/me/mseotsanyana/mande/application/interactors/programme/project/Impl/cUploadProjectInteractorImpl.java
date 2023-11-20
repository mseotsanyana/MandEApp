package com.me.mseotsanyana.mande.application.interactors.programme.project.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.programme.project.iProjectInteractor;
import com.me.mseotsanyana.mande.application.repository.programme.iProjectRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;

public class cUploadProjectInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iProjectInteractor {

    private static final String TAG = cUploadProjectInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iProjectRepository projectRepository;

    // permission data
    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final int statusBIT;

    private final int entityBITS;
    private final int entitypermBITS;

    private final String filePath;

    public cUploadProjectInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                        ISessionManager sharedPreferenceRepository,
                                        iProjectRepository projectRepository,
                                        Callback callback, String filePath) {
        super(threadExecutor, mainThread, null);

        if (sharedPreferenceRepository == null || projectRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.projectRepository = projectRepository;

        this.filePath = filePath;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadLoggedInUserServerID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        //this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryTeams();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                CPreferenceConstant.PROGRAMME_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                CPreferenceConstant.PROGRAMME_MODULE, CPreferenceConstant.PROJECT);
        this.statusBIT = CPreferenceConstant.ACTIVE;

        Log.d(TAG, " \n USER ID = " + this.userServerID +
                " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUS = " + this.statusBIT);

    }

    /* notify on the main thread */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onUploadProjectsFailed(msg));
    }

    /* notify on the main thread */
    private void postMessage(String msg) {
        mainThread.post(() -> callback.onUploadProjectsCompleted(msg));
    }

    @Override
    public void run() {
        if ((this.entityBITS & CPreferenceConstant.PROJECT) != 0) {
            if ((this.entitypermBITS & CPreferenceConstant.READ) != 0) {
                this.projectRepository.upLoadProjectsFromExcel(organizationServerID, userServerID,
                        primaryTeamBIT, statusBIT, filePath,
                        new iProjectRepository.iUploadProjectsCallback() {
                            @Override
                            public void onUploadProjectsSucceeded(String msg) {
                                postMessage(msg);
                            }

                            @Override
                            public void onUploadProjectsFailed(String msg) {
                                notifyError(msg);
                            }
                        });
            } else {
                notifyError("Permission denied! Please contact your administrator");
            }
        } else {
            notifyError("No access to the entity! Please contact your administrator");
        }
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}