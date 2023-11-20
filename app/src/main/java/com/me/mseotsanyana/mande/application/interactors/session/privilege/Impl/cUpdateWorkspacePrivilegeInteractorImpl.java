package com.me.mseotsanyana.mande.application.interactors.session.privilege.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.application.executor.iExecutor;
import com.me.mseotsanyana.mande.application.executor.iMainThread;
import com.me.mseotsanyana.mande.application.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.privilege.iUpdateWorkspacePrivilegeInteractor;
import com.me.mseotsanyana.mande.application.repository.session.iPrivilegeRepository;
import com.me.mseotsanyana.mande.application.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.framework.storage.preference.cSharedPreference;
import com.me.mseotsanyana.treeadapterlibrary.cNode;

import java.util.List;

public class cUpdateWorkspacePrivilegeInteractorImpl extends cAbstractInteractor implements
        iUpdateWorkspacePrivilegeInteractor {
    private static final String TAG = cUpdateWorkspacePrivilegeInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iPrivilegeRepository privilegeRepository;

    // permission data
    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    private final cNode node;

    public cUpdateWorkspacePrivilegeInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                                   iSharedPreferenceRepository sharedPreferenceRepository,
                                                   iPrivilegeRepository privilegeRepository,
                                                   Callback callback, cNode node) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || privilegeRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.privilegeRepository = privilegeRepository;
        this.node = node;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.SESSION_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.PRIVILEGE);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.PRIVILEGE,
                cSharedPreference.UPDATE);

        Log.d(TAG, " \n USER ID = " + this.userServerID +
                " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onUpdateWorkspacePrivilegeFailed(msg));
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(() -> callback.onUpdateWorkspacePrivilegeSucceeded(msg));
    }

    @Override
    public void run() {
        //if ((this.entityBITS & cSharedPreference.PRIVILEGE) != 0) {
        //    if ((this.entitypermBITS & cSharedPreference.UPDATE) != 0) {
                this.privilegeRepository.updateWorkspacePrivilege(organizationServerID,
                        userServerID, primaryTeamBIT, secondaryTeamBITS, statusBITS, node,
                        new iPrivilegeRepository.iUpdateWorkspacePrivilegeCallback() {
                            @Override
                            public void onUpdateWorkspacePrivilegeSucceeded(String msg) {
                                postMessage(msg);
                            }

                            @Override
                            public void onUpdateWorkspacePrivilegeFailed(String msg) {
                                notifyError(msg);
                            }
                        });
            //} else {
            //    notifyError("Permission denied! Please contact your administrator");
            //}
        //} else {
        //    notifyError("No access to the entity! Please contact your administrator");
        //}
    }
}