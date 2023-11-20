package com.me.mseotsanyana.mande.application.interactors.session.workspace;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.utils.cInteractorUtils;
import com.me.mseotsanyana.mande.domain.entities.models.session.cPrivilegeModel;
import com.me.mseotsanyana.mande.application.preference.ISharedPreferenceRepository;
import com.me.mseotsanyana.mande.application.repository.session.iWorkspaceRepository;
import com.me.mseotsanyana.mande.application.structures.CSharedPreference;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class cReadTeamsWithRolesInteractorImpl extends cAbstractInteractor
        implements iReadTeamsWithRolesInteractor {
    private static final String TAG = cReadTeamsWithRolesInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iWorkspaceRepository teamRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadTeamsWithRolesInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                             ISharedPreferenceRepository sharedPreferenceRepository,
                                             iWorkspaceRepository teamRepository, Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || teamRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.teamRepository = teamRepository;
        this.callback = callback;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                CSharedPreference.SESSION_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                CSharedPreference.SESSION_MODULE, CSharedPreference.TEAM);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                CSharedPreference.SESSION_MODULE, CSharedPreference.TEAM,
                CSharedPreference.READ);

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
        mainThread.post(() -> callback.onReadTeamsWithRolesFailed(msg));
    }

    /* */
    private void teamsWithRolesMessage(List<cTreeModel> teamsRolesTree) {
        mainThread.post(() -> callback.onReadTeamsWithRolesSucceeded(teamsRolesTree));
    }

    @Override
    public void run() {
        Gson gson = new Gson();

        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
            if ((this.entityBITS & CSharedPreference.ORGANIZATION) != 0) {
                if ((this.entitypermBITS & CSharedPreference.READ) != 0) {
                    this.teamRepository.readTeamsWithRoles(organizationServerID, userServerID,
                            primaryTeamBIT, secondaryTeamBITS, statusBITS,
                            new iWorkspaceRepository.iReadTeamsWithRolesCallback() {
                                @Override
                                public void onReadTeamsWithRolesSucceeded(Map<CWorkspaceModel,
                                        List<cPrivilegeModel>> teamRolesMap) {
                                    List<cTreeModel> teamsRolesTree = new ArrayList<>();
                                    int parentIndex = 0, childIndex;
                                    for (Map.Entry<CWorkspaceModel,
                                            List<cPrivilegeModel>> entry : teamRolesMap.entrySet()) {
                                        /* a team */
                                        CWorkspaceModel teamModel = entry.getKey();
                                        teamsRolesTree.add(new cTreeModel(parentIndex, -1,
                                                0, teamModel));

                                        /* a list of team roles under the team */
                                        Log.d(TAG, "teamRolesMap ==>> " +
                                                gson.toJson(entry.getValue()));
                                        childIndex = parentIndex;
                                        for (cPrivilegeModel roleModel : entry.getValue()) {
                                            childIndex = childIndex + 1;
                                            teamsRolesTree.add(new cTreeModel(childIndex,
                                                    parentIndex, 1, roleModel));
                                        }
                                        /* next parent index */
                                        parentIndex = childIndex + 1;
                                    }


                                    teamsWithRolesMessage(teamsRolesTree);
                                }

                                @Override
                                public void onReadTeamsWithRolesFailed(String msg) {
                                    notifyError(msg);
                                }
                            });
                } else {
                    notifyError("Permission denied! Please contact your administrator");
                }
            } else {
                notifyError("No access to the entity! Please contact your administrator");
            }
        } else {
            notifyError("Error in default settings");
        }
    }
}