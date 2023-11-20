package com.me.mseotsanyana.mande.application.interactors.session.menu.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.menu.iReadMenuInteractor;
import com.me.mseotsanyana.mande.application.repository.session.iMenuRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cReadMenuInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>> implements iReadMenuInteractor {
    private static final String TAG = cReadMenuInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iMenuRepository menuRepository;

    // permission data
    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadMenuInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                   ISessionManager sharedPreferenceRepository,
                                   iMenuRepository menuRepository,
                                   Callback callback) {
        super(threadExecutor, mainThread, null);

        if (sharedPreferenceRepository == null || menuRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.menuRepository = menuRepository;

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
                CPreferenceConstant.READ);

        Log.d(TAG, " \n USER ID = " + this.userServerID +
                " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadMenuFailed(msg));
    }

    /* */
    private void postMessage(List<cTreeModel> treeModels) {
        mainThread.post(() -> callback.onReadMenuSucceeded(treeModels));
    }


    @Override
    public void run() {
        if ((this.entityBITS & CPreferenceConstant.PRIVILEGE) != 0) {

//            if ((this.entitypermBITS & cSharedPreference.READ) != 0) {
//                this.menuRepository.readMenuPermissions(organizationServerID,
//                        userServerID, primaryTeamBIT, secondaryTeamBITS, statusBITS,
//                        new iMenuRepository.iReadMenuPermissionsCallback() {
//                            @Override
//                            public void onReadMenuPermissionsSucceeded(List<cTreeModel> treeModels) {
//                                postMessage(treeModels);
//                            }
//
//                            @Override
//                            public void onReadMenuPermissionsFailed(String msg) {
//                                notifyError(msg);
//                            }
//                        });
//            } else {
//                notifyError("Permission denied! Please contact your administrator");
//            }
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