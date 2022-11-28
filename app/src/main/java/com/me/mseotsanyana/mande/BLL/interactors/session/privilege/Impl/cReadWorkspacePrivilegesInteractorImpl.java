package com.me.mseotsanyana.mande.BLL.interactors.session.privilege.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.privilege.iReadWorkspacePermissionsInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

public class cReadWorkspacePrivilegesInteractorImpl extends cAbstractInteractor
        implements iReadWorkspacePermissionsInteractor {
    private static final String TAG = cReadWorkspacePrivilegesInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iPermissionRepository permissionRepository;

    // permission data
    private final String userServerID;
    private final String organizationServerID;
    private final List<String> myOrganizations;
    private final int workspaceMembershipBITS;
    private final List<Integer> statusBITS = new ArrayList<>();

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadWorkspacePrivilegesInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                                  iSharedPreferenceRepository sharedPreferenceRepository,
                                                  iPermissionRepository permissionRepository,
                                                  Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || permissionRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.permissionRepository = permissionRepository;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.myOrganizations = sharedPreferenceRepository.loadMyOrganizations();
        this.workspaceMembershipBITS = sharedPreferenceRepository.loadWorkspaceMembershipBITS();
        this.statusBITS.add(0);// for stateless entity
        /*sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.PRIVILEGE,
                cSharedPreference.READ);*/

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.SESSION_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.PRIVILEGE);


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
                        new iPermissionRepository.iReadWorkspacePrivilegesCallback() {
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
}