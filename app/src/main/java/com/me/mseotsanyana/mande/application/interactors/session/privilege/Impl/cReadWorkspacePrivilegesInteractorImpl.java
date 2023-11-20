package com.me.mseotsanyana.mande.application.interactors.session.privilege.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.privilege.iReadWorkspacePrivilegesInteractor;
import com.me.mseotsanyana.mande.application.repository.session.IPermissionRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

public class cReadWorkspacePrivilegesInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iReadWorkspacePrivilegesInteractor {
    private static final String TAG = cReadWorkspacePrivilegesInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final IPermissionRepository permissionRepository;

    // permission data
    private final String userServerID;
    private final String organizationServerID;
    private final List<String> myOrganizations;
    private final int workspaceMembershipBITS;
    private final List<Integer> statusBITS = new ArrayList<>();

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadWorkspacePrivilegesInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                                  ISessionManager sharedPreferenceRepository,
                                                  IPermissionRepository permissionRepository,
                                                  Callback callback) {
        super(threadExecutor, mainThread, null);

        if (sharedPreferenceRepository == null || permissionRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.permissionRepository = permissionRepository;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadLoggedInUserServerID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.myOrganizations = sharedPreferenceRepository.loadMyOrganizations();
        this.workspaceMembershipBITS = sharedPreferenceRepository.loadWorkspaceMembershipBITS();
        this.statusBITS.add(0);// for stateless entity
        /*sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.PRIVILEGE,
                cSharedPreference.READ);*/

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                CPreferenceConstant.SESSION);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                CPreferenceConstant.SESSION, CPreferenceConstant.PRIVILEGE);


        Log.d(TAG, " \n USER ID = " + this.userServerID +
                " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n MY ORGANIZATIONS = " + this.myOrganizations +
                " \n WORKSPACE MEMBERSHIP BITS = " + this.workspaceMembershipBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n ACTION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadRolePermissionsFailed(msg));
    }

    /* */
    private void postMessage(List<cTreeModel> treeModels) {
        mainThread.post(() -> callback.onReadRolePermissionsSucceeded(treeModels));
    }


    @Override
    public void run() {
        //if ((this.entityBITS & cSharedPreference.PERMISSION) != 0) {
        //    if ((this.entitypermBITS & cSharedPreference.READ) != 0) {
                this.permissionRepository.readWorkspacePrivileges(userServerID, organizationServerID,
                         myOrganizations, workspaceMembershipBITS, statusBITS,
                        new IPermissionRepository.iReadWorkspacePrivilegesCallback() {
                            @Override
                            public void onReadWorkspacePrivilegesSucceeded(List<cTreeModel> treeModels) {
                                postMessage(treeModels);
                            }

                            @Override
                            public void onReadWorkspacePrivilegesFailed(String msg) {
                                notifyError(msg);
                            }
                        });
//            } else {
//                notifyError("Permission denied! Please contact your administrator");
//            }
//        } else {
//            notifyError("No access to the entity! Please contact your administrator");
//        }
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}