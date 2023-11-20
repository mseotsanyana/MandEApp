package com.me.mseotsanyana.mande.application.interactors.session.privilege.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.privilege.iUpdateWorkspacePrivilegeInteractor;
import com.me.mseotsanyana.mande.application.repository.session.IPermissionRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.treeadapterlibrary.cNode;

import java.util.List;

public class cUpdateWorkspacePrivilegeInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>> implements
        iUpdateWorkspacePrivilegeInteractor {
    private static final String TAG = cUpdateWorkspacePrivilegeInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final IPermissionRepository privilegeRepository;

    // permission data
    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    private final cNode node;

    public cUpdateWorkspacePrivilegeInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                                   ISessionManager sharedPreferenceRepository,
                                                   IPermissionRepository privilegeRepository,
                                                   Callback callback, cNode node) {
        super(threadExecutor, mainThread, null);

        if (sharedPreferenceRepository == null || privilegeRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.privilegeRepository = privilegeRepository;
        this.node = node;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadLoggedInUserServerID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                CPreferenceConstant.SESSION);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                CPreferenceConstant.SESSION, CPreferenceConstant.PRIVILEGE);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                CPreferenceConstant.SESSION, CPreferenceConstant.PRIVILEGE,
                CPreferenceConstant.UPDATE);

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
                        new IPermissionRepository.iUpdateWorkspacePrivilegeCallback() {
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

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}