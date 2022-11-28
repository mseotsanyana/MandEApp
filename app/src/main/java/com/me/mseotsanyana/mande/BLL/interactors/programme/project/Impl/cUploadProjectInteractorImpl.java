package com.me.mseotsanyana.mande.BLL.interactors.programme.project.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.programme.project.iProjectInteractor;
import com.me.mseotsanyana.mande.BLL.repository.programme.iProjectRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

public class cUploadProjectInteractorImpl extends cAbstractInteractor
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

    public cUploadProjectInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                        iSharedPreferenceRepository sharedPreferenceRepository,
                                        iProjectRepository projectRepository,
                                        Callback callback, String filePath) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || projectRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.projectRepository = projectRepository;

        this.filePath = filePath;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        //this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryTeams();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.PROGRAMME_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.PROGRAMME_MODULE, cSharedPreference.PROJECT);
        this.statusBIT = cSharedPreference.ACTIVE;

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
        if ((this.entityBITS & cSharedPreference.PROJECT) != 0) {
            if ((this.entitypermBITS & cSharedPreference.READ) != 0) {
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
}