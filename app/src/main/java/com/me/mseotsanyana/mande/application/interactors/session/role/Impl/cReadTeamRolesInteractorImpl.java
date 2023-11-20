package com.me.mseotsanyana.mande.application.interactors.session.role.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.application.executor.iExecutor;
import com.me.mseotsanyana.mande.application.executor.iMainThread;
import com.me.mseotsanyana.mande.application.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.role.iReadTeamRolesInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.cPrivilegeModel;
import com.me.mseotsanyana.mande.application.repository.session.iRoleRepository;
import com.me.mseotsanyana.mande.application.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.framework.storage.preference.cSharedPreference;

import java.util.List;

public class cReadTeamRolesInteractorImpl extends cAbstractInteractor
        implements iReadTeamRolesInteractor {
    private static String TAG = cReadTeamRolesInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iRoleRepository roleRepository;

    // permission data
    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;


    public cReadTeamRolesInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                        iSharedPreferenceRepository sharedPreferencesRepository,
                                        iRoleRepository roleRepository,
                                        Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferencesRepository == null || roleRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.roleRepository = roleRepository;
        this.callback = callback;

        // load user shared preferences
        this.userServerID = sharedPreferencesRepository.loadUserID();
        this.organizationServerID = sharedPreferencesRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferencesRepository.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sharedPreferencesRepository.loadSecondaryWorkspaces();
        if (this.secondaryTeamBITS.isEmpty())
            this.secondaryTeamBITS.add(0);//FIXME

        // load entity shared preferences
        this.entityBITS = sharedPreferencesRepository.loadEntityBITS(
                cSharedPreference.SESSION_MODULE);
        this.entitypermBITS = sharedPreferencesRepository.loadEntityPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.ROLE);
        this.statusBITS = sharedPreferencesRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.ROLE,
                cSharedPreference.READ);

        Log.d(TAG, " \n USER ID = " + this.userServerID +
                " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);

    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadTeamRolesFailed(msg));
    }

    /* */
    private void postMessage(List<cPrivilegeModel> roleModels) {
        mainThread.post(() -> callback.onReadTeamRolesSucceeded(roleModels));
    }

    @Override
    public void run() {
        if ((this.entityBITS & cSharedPreference.ROLE) != 0) {
            if ((this.entitypermBITS & cSharedPreference.READ) != 0) {

                this.roleRepository.readTeamRoles(organizationServerID, userServerID,
                        primaryTeamBIT, secondaryTeamBITS, statusBITS,
                        new iRoleRepository.iReadTeamRolesCallback() {
                            @Override
                            public void onReadTeamRolesSucceeded(List<cPrivilegeModel> roleModels) {
                                postMessage(roleModels);
                            }

                            @Override
                            public void onReadTeamRolesFailed(String msg) {
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