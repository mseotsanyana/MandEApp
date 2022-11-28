package com.me.mseotsanyana.mande.BLL.interactors.shared.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.cInteractorUtils;
import com.me.mseotsanyana.mande.BLL.interactors.shared.iRecordPermissionInteractor;
import com.me.mseotsanyana.mande.BLL.repository.common.iRecordPermissionRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import java.util.List;
import java.util.Map;

public class cReadRecordPermissionInteractorImpl extends cAbstractInteractor implements iRecordPermissionInteractor {
    private static final String TAG = cReadRecordPermissionInteractorImpl.class.getSimpleName();

    private final iRecordPermissionRepository commonPropertyRepository;
    private final Callback callback;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    Gson gson = new Gson();

    public cReadRecordPermissionInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                               iSharedPreferenceRepository sharedPreferenceRepository,
                                               iRecordPermissionRepository commonPropertyRepository,
                                               Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || commonPropertyRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.commonPropertyRepository = commonPropertyRepository;
        this.callback = callback;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.PROGRAMME_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.PROGRAMME_MODULE, cSharedPreference.LOGFRAME);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.PROGRAMME_MODULE, cSharedPreference.LOGFRAME,
                cSharedPreference.READ);

        Log.d(TAG, " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n USER ID = " + this.userServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n UNIX TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY BITS = " + this.entityBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* read user profiles */
    private void postPropertyLists(Map<String, Object> propertyLists) {
        mainThread.post(() -> callback.onReadRecordPermissionsSucceeded(propertyLists));
    }

    /* notify user when error occurs */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadRecordPermissionsFailed(msg));
    }

    @Override
    public void run() {
        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {

            commonPropertyRepository.onReadRecordPermissions(organizationServerID, userServerID,
                    primaryTeamBIT, secondaryTeamBITS, statusBITS,
                    new iRecordPermissionRepository.iReadRecordPermissionsCallback() {
                        @Override
                        public void onReadRecordPermissionsSucceeded(
                                Map<String, Object> commonProperties) {

                            postPropertyLists(commonProperties);
                        }

                        @Override
                        public void onReadRecordPermissionsFailed(String msg) {
                            notifyError(msg);
                        }
                    });
        } else {
            notifyError("Error in default settings");
        }
    }
}
