package com.me.mseotsanyana.mande.infrastructure.repository.preference;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserAccountModel;
import com.me.mseotsanyana.mande.application.repository.preference.iRecordPermissionRepository;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreRepository;
import com.me.mseotsanyana.mande.application.structures.CFirestoreConstant;
import com.me.mseotsanyana.mande.application.utils.CFirestoreUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cRecordPermissionFirestoreRepositoryImpl extends CFirestoreRepository
        implements iRecordPermissionRepository {
    //private static final String TAG = cRecordPermissionFirestoreRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private final FirebaseFirestore database;

    public cRecordPermissionFirestoreRepositoryImpl() {
        this.database = FirebaseFirestore.getInstance();
    }

    /* ###################################### READ ACTIONS ###################################### */

    @Override
    public void onReadRecordPermissions(String organizationServerID, String userServerID,
                                        int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                        List<Integer> statusBITS,
                                        iReadRecordPermissionsCallback callback) {

        CollectionReference coStakeholderRef = database.collection(CFirestoreConstant.KEY_ORGANIZATIONS);
        Query stakeholderQuery = coStakeholderRef
                .whereEqualTo("stakeholderOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS)
                .orderBy("createdDate");

        readQueryDocuments(stakeholderQuery, new CFirestoreCallBack() {
            @Override
            public void onFirebaseSuccess(Object querySnapshot) {
                if (querySnapshot == null)
                    return;

                List<COrganizationModel> stakeholderModels = new ArrayList<>();
                QuerySnapshot documentSnapshots = (QuerySnapshot) querySnapshot;
                for (DocumentSnapshot stakeholder : documentSnapshots) {
                    COrganizationModel stakeholderModel;
                    stakeholderModel = stakeholder.toObject(COrganizationModel.class);
                    assert stakeholderModel != null;

                    CFirestoreUtility.cUnixPerm perm = new CFirestoreUtility.cUnixPerm();
                    perm.setUserOwnerID(stakeholderModel.getUserOwnerID());
                    perm.setTeamOwnerBIT(stakeholderModel.getWorkspaceOwnerBIT());
                    perm.setUnixpermBITS(stakeholderModel.getUnixpermBITS());
//FIXME                    if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                            secondaryTeamBITS)) {
//                        stakeholderModel.setOrganizationServerID(stakeholder.getId());
//                        stakeholderModels.add(stakeholderModel);
//                    }
                }
                onMergeTeams(organizationServerID, userServerID, primaryTeamBIT, secondaryTeamBITS,
                        statusBITS, stakeholderModels, callback);
            }

            @Override
            public void onFirebaseFailure(Object object) {
                callback.onReadRecordPermissionsFailed("Failed to read organizations!");

            }
        });
    }

    private void onMergeTeams(String organizationServerID, String userServerID,
                              int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                              List<Integer> statusBITS, List<COrganizationModel> stakeholderModels,
                              iReadRecordPermissionsCallback callback) {

        CollectionReference coTeamRef = database.collection(CFirestoreConstant.KEY_WORKSPACES);
        Query teamQuery = coTeamRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS)
                .orderBy("createdDate");

        readQueryDocuments(teamQuery, new CFirestoreCallBack() {
            @Override
            public void onFirebaseSuccess(Object querySnapshot) {
                if (querySnapshot == null)
                    return;

                List<CWorkspaceModel> teamModels = new ArrayList<>();
                QuerySnapshot documentSnapshots = (QuerySnapshot) querySnapshot;
                for (DocumentSnapshot team : documentSnapshots) {
                    CWorkspaceModel teamModel = team.toObject(CWorkspaceModel.class);
                    assert teamModel != null;

                    CFirestoreUtility.cUnixPerm perm = new CFirestoreUtility.cUnixPerm();
                    perm.setUserOwnerID(teamModel.getUserOwnerID());
                    perm.setTeamOwnerBIT(teamModel.getWorkspaceOwnerBIT());
                    perm.setUnixpermBITS(teamModel.getUnixpermBITS());
//FIXME                    if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                            secondaryTeamBITS)) {
//                        teamModel.setCompositeServerID(team.getId());
//                        teamModels.add(teamModel);
//                    }
                }

                onMergeUserAccounts(organizationServerID, userServerID, primaryTeamBIT,
                        secondaryTeamBITS, statusBITS, stakeholderModels, teamModels, callback);
            }

            @Override
            public void onFirebaseFailure(Object object) {
                callback.onReadRecordPermissionsFailed("Failed to read teams!");
            }
        });

    }

    private void onMergeUserAccounts(String organizationServerID, String userServerID,
                                     int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                     List<Integer> statusBITS,
                                     List<COrganizationModel> stakeholderModels,
                                     List<CWorkspaceModel> teamModels,
                                     iReadRecordPermissionsCallback callback) {

        CollectionReference coAccountRef = database.collection(CFirestoreConstant.KEY_ORGANIZATION_MEMBERS);
        Query userAccountQuery = coAccountRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS)
                .orderBy("createdDate");

        readQueryDocuments(userAccountQuery, new CFirestoreCallBack() {
            @Override
            public void onFirebaseSuccess(Object querySnapshot) {
                if (querySnapshot == null)
                    return;

                List<String> user_account_ids = new ArrayList<>();
                QuerySnapshot documentSnapshots = (QuerySnapshot) querySnapshot;
                for (DocumentSnapshot account : documentSnapshots) {
                    CUserAccountModel userAccountModel = account.toObject(CUserAccountModel.class);

                    assert userAccountModel != null;

                    CFirestoreUtility.cUnixPerm perm = new CFirestoreUtility.cUnixPerm();
                    perm.setUserOwnerID(userAccountModel.getUserOwnerID());
                    perm.setTeamOwnerBIT(userAccountModel.getWorkspaceOwnerBIT());
                    perm.setUnixpermBITS(userAccountModel.getUnixpermBITS());
//FIXME                    if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                            secondaryTeamBITS)) {
//                        userAccountModel.setUserAccountServerID(account.getId());
//                        user_account_ids.add(userAccountModel.getUserServerID());
//                    }
                }

                onMergeUserProfiles(stakeholderModels, teamModels, user_account_ids, callback);
            }

            @Override
            public void onFirebaseFailure(Object object) {
                callback.onReadRecordPermissionsFailed("Failed to read user accounts!");
            }
        });
    }

    private void onMergeUserProfiles(List<COrganizationModel> stakeholderModels,
                                     List<CWorkspaceModel> teamModels, List<String> user_account_ids,
                                     @NonNull iReadRecordPermissionsCallback callback) {

        CollectionReference coProfileRef = database.collection(CFirestoreConstant.KEY_USERPROFILES);

        Query userProfileQuery = coProfileRef
                .whereIn(FieldPath.documentId(), user_account_ids);

        readQueryDocuments(userProfileQuery, new CFirestoreCallBack() {
            @Override
            public void onFirebaseSuccess(Object querySnapshot) {
                if (querySnapshot == null)
                    return;

                List<CUserProfileModel> userProfileModels = new ArrayList<>();
                QuerySnapshot documentSnapshots = (QuerySnapshot) querySnapshot;
                for (DocumentSnapshot user : documentSnapshots) {
                    CUserProfileModel userProfileModel = user.toObject(CUserProfileModel.class);
                    assert userProfileModel != null;
                    userProfileModel.setUserServerID(user.getId());
                    userProfileModels.add(userProfileModel);
                }

                Map<String, Object> listMap = new HashMap<>();

                listMap.put("ORGANIZATIONS", stakeholderModels);
                listMap.put("TEAMS", teamModels);
                listMap.put("USERS", userProfileModels);

                callback.onReadRecordPermissionsSucceeded(listMap);
            }

            @Override
            public void onFirebaseFailure(Object object) {
                callback.onReadRecordPermissionsFailed("Failed to read user profiles!");
            }
        });
    }

    /* ##################################### UPDATE ACTIONS ##################################### */

}
