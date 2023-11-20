package com.me.mseotsanyana.mande.infrastructure.repository.firestore.session;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.me.mseotsanyana.mande.domain.entities.models.session.CPrivilegeModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.application.repository.session.iRoleRepository;
import com.me.mseotsanyana.mande.application.structures.CFirestoreConstant;
import com.me.mseotsanyana.mande.application.utils.CFirestoreUtility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cRoleFirestoreRepositoryImpl implements iRoleRepository {
    private static String TAG = cRoleFirestoreRepositoryImpl.class.getSimpleName();

    private final FirebaseFirestore db;

    public cRoleFirestoreRepositoryImpl(Context context) {
        this.db = FirebaseFirestore.getInstance();
    }

    /* ###################################### READ ACTIONS ###################################### */

    /**
     * read roles
     *
     * @param userServerID         user identification
     * @param organizationServerID organization identification
     * @param primaryTeamBIT       primary team bit
     * @param secondaryTeamBITS    secondary team bits
     * @param statusBITS           status bits
     * @param callback             call back
     */

    @Override
    public void readTeamRoles(String organizationServerID, String userServerID, int primaryTeamBIT,
                              List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
                              iReadTeamRolesCallback callback) {

        CollectionReference coRoleRef = db.collection(CFirestoreConstant.KEY_ROLES);

        Query roleQuery = coRoleRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);

        roleQuery.get()
                .addOnCompleteListener(task -> {
                    List<CPrivilegeModel> roleModels = new ArrayList<>();
                    for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
                        CPrivilegeModel roleModel = role_doc.toObject(CPrivilegeModel.class);

                        if (roleModel != null) {
                            CFirestoreUtility.cUnixPerm perm = new CFirestoreUtility.cUnixPerm();
                            perm.setUserOwnerID(roleModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(roleModel.getWorkspaceOwnerBIT());
                            perm.setUnixpermBITS(roleModel.getUnixpermBITS());

//FIXME                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                                    secondaryTeamBITS)) {
//                                roleModels.add(roleModel);
//                            }
                        }
                    }

                    // call back on roles
                    callback.onReadTeamRolesSucceeded(roleModels);
                })
                .addOnFailureListener(e -> callback.onReadTeamRolesFailed(
                        "Failed to read roles"));
    }

    /**
     * read role teams
     *
     * @param roleServerID         role identification
     * @param organizationServerID organization identification
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team bit
     * @param secondaryTeamBITS    secondary team bits
     * @param statusBITS           status bits
     * @param callback             call back
     */
    public void readRoleTeams(String roleServerID, String organizationServerID, String userServerID,
                              int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                              List<Integer> statusBITS, iReadRoleTeamsCallback callback) {

        CollectionReference coRoleTeamsRef = db.collection(CFirestoreConstant.KEY_TEAM_ROLES);
        Query roleTeamsQuery = coRoleTeamsRef.whereEqualTo("roleServerID", roleServerID);

        roleTeamsQuery.get()
                .addOnCompleteListener(task -> {
                    Set<String> team_id_set = new HashSet<>();
                    for (DocumentSnapshot team_doc : Objects.requireNonNull(task.getResult())) {
                        String teamID = team_doc.getString("teamServerID");
                        if (teamID != null) {
                            team_id_set.add(teamID);
                        }
                    }
                    // filter the teams of an role identification
                    List<String> team_ids = new ArrayList<>(team_id_set);
                    filterRoleTeams(team_ids, organizationServerID, userServerID, primaryTeamBIT,
                            secondaryTeamBITS, statusBITS, callback);

                })
                .addOnFailureListener(e -> {
                    callback.onReadRoleTeamsFailed("Failed to read Organization." + e);
                    Log.w(TAG, "Failed to read value.", e);
                });
    }

    /**
     * read teams
     *
     * @param team_ids             list of team identifications
     * @param organizationServerID organization identification
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team bit
     * @param secondaryTeamBITS    secondary team bits
     * @param statusBITS           status bits
     * @param callback             call back
     */
    private void filterRoleTeams(List<String> team_ids, String organizationServerID,
                                 String userServerID, int primaryTeamBIT,
                                 List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
                                 iReadRoleTeamsCallback callback) {

        CollectionReference coTeamRef = db.collection(CFirestoreConstant.KEY_WORKSPACES);

        Query teamQuery = coTeamRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("teamServerID", team_ids);

        teamQuery.get()
                .addOnCompleteListener(task -> {
                    List<CWorkspaceModel> teamModels = new ArrayList<>();

                    for (DocumentSnapshot team_doc :
                            Objects.requireNonNull(task.getResult())) {

                        CWorkspaceModel teamModel = team_doc.toObject(CWorkspaceModel.class);
                        if (teamModel != null) {
                            CFirestoreUtility.cUnixPerm perm = new CFirestoreUtility.cUnixPerm();
                            perm.setUserOwnerID(teamModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(teamModel.getWorkspaceOwnerBIT());
                            perm.setUnixpermBITS(teamModel.getUnixpermBITS());
                            perm.setStatusBIT(teamModel.getStatusBIT());

//FIXME                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                                    secondaryTeamBITS, statusBITS)) {
//                                teamModels.add(teamModel);
//                            }
                        }
                    }

                    /* call back on role teams */
                    callback.onReadRoleTeamsSucceeded(teamModels);
                })
                .addOnFailureListener(e -> {
                    callback.onReadRoleTeamsFailed("Failed to read teams.");
                    Log.w(TAG, "Failed to read value.", e);
                });
    }
}