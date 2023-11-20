package com.me.mseotsanyana.mande.application.interactors.session.workspace;

import android.util.Log;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IWorkspaceRepository;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class CReadWorkspacesWithRolesInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        /*implements IReadWorkspacesWithRolesInteractor*/ {
    private static final String TAG = CReadWorkspacesWithRolesInteractorImpl.class.getSimpleName();

    //private final Callback callback;
    private final IWorkspaceRepository teamRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public CReadWorkspacesWithRolesInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                                  ISessionManager sharedPreferenceRepository,
                                                  IWorkspaceRepository teamRepository, Object o){
        super(threadExecutor, mainThread, null);

        if (sharedPreferenceRepository == null || teamRepository == null ) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.teamRepository = teamRepository;
        //this.callback = callback;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadLoggedInUserServerID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                CPreferenceConstant.SESSION);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                CPreferenceConstant.SESSION, CPreferenceConstant.WORKSPACE);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                CPreferenceConstant.SESSION, CPreferenceConstant.WORKSPACE,
                CPreferenceConstant.READ);

        Log.d(TAG, " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n USER ID = " + this.userServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY BITS = " + this.entityBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        //mainThread.post(() -> callback.onReadTeamsWithRolesFailed(msg));
    }

    /* */
    private void teamsWithRolesMessage(List<cTreeModel> teamsRolesTree) {
        //mainThread.post(() -> callback.onReadTeamsWithRolesSucceeded(teamsRolesTree));
    }

    @Override
    public void run() {
//        Gson gson = new Gson();
//
//        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
//                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
//            if ((this.entityBITS & CPreferenceConstant.ORGANIZATION) != 0) {
//                if ((this.entitypermBITS & CPreferenceConstant.READ) != 0) {
//                    this.teamRepository.readTeamsWithRoles(organizationServerID, userServerID,
//                            primaryTeamBIT, secondaryTeamBITS, statusBITS,
//                            new IWorkspaceRepository.iReadTeamsWithRolesCallback() {
//                                @Override
//                                public void onReadTeamsWithRolesSucceeded(Map<CWorkspaceModel,
//                                        List<CPrivilegeModel>> teamRolesMap) {
//                                    List<cTreeModel> teamsRolesTree = new ArrayList<>();
//                                    int parentIndex = 0, childIndex;
//                                    for (Map.Entry<CWorkspaceModel,
//                                            List<CPrivilegeModel>> entry : teamRolesMap.entrySet()) {
//                                        /* a team */
//                                        CWorkspaceModel teamModel = entry.getKey();
//                                        //teamsRolesTree.add(new cTreeModel(parentIndex, -1,
//                                        //        0, teamModel));
//
//                                        /* a list of team roles under the team */
//                                        Log.d(TAG, "teamRolesMap ==>> " +
//                                                gson.toJson(entry.getValue()));
//                                        childIndex = parentIndex;
//                                        for (CPrivilegeModel roleModel : entry.getValue()) {
//                                            childIndex = childIndex + 1;
//                                            //teamsRolesTree.add(new cTreeModel(childIndex,
//                                            //        parentIndex, 1, roleModel));
//                                        }
//                                        /* next parent index */
//                                        parentIndex = childIndex + 1;
//                                    }
//
//
//                                    teamsWithRolesMessage(teamsRolesTree);
//                                }
//
//                                @Override
//                                public void onReadTeamsWithRolesFailed(String msg) {
//                                    notifyError(msg);
//                                }
//                            });
//                } else {
//                    notifyError("Permission denied! Please contact your administrator");
//                }
//            } else {
//                notifyError("No access to the entity! Please contact your administrator");
//            }
//        } else {
//            notifyError("Error in default settings");
//        }
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}