package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.common;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cWorkspaceModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.repository.common.iRecordPermissionRepository;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseCallBack;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cRecordPermissionFirestoreRepositoryImpl extends cFirebaseRepository
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

        CollectionReference coStakeholderRef = database.collection(cRealtimeHelper.KEY_STAKEHOLDERS);
        Query stakeholderQuery = coStakeholderRef
                .whereEqualTo("stakeholderOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS)
                .orderBy("createdDate");

        readQueryDocuments(stakeholderQuery, new cFirebaseCallBack() {
            @Override
            public void onFirebaseSuccess(Object querySnapshot) {
                if (querySnapshot == null)
                    return;

                List<cOrganizationModel> stakeholderModels = new ArrayList<>();
                QuerySnapshot documentSnapshots = (QuerySnapshot) querySnapshot;
                for (DocumentSnapshot stakeholder : documentSnapshots) {
                    cOrganizationModel stakeholderModel;
                    stakeholderModel = stakeholder.toObject(cOrganizationModel.class);
                    assert stakeholderModel != null;

                    cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
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
                              List<Integer> statusBITS, List<cOrganizationModel> stakeholderModels,
                              iReadRecordPermissionsCallback callback) {

        CollectionReference coTeamRef = database.collection(cRealtimeHelper.KEY_TEAMS);
        Query teamQuery = coTeamRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS)
                .orderBy("createdDate");

        readQueryDocuments(teamQuery, new cFirebaseCallBack() {
            @Override
            public void onFirebaseSuccess(Object querySnapshot) {
                if (querySnapshot == null)
                    return;

                List<cWorkspaceModel> teamModels = new ArrayList<>();
                QuerySnapshot documentSnapshots = (QuerySnapshot) querySnapshot;
                for (DocumentSnapshot team : documentSnapshots) {
                    cWorkspaceModel teamModel = team.toObject(cWorkspaceModel.class);
                    assert teamModel != null;

                    cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
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
                                     List<cOrganizationModel> stakeholderModels,
                                     List<cWorkspaceModel> teamModels,
                                     iReadRecordPermissionsCallback callback) {

        CollectionReference coAccountRef = database.collection(cRealtimeHelper.KEY_ORGANIZATION_MEMBERS);
        Query userAccountQuery = coAccountRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS)
                .orderBy("createdDate");

        readQueryDocuments(userAccountQuery, new cFirebaseCallBack() {
            @Override
            public void onFirebaseSuccess(Object querySnapshot) {
                if (querySnapshot == null)
                    return;

                List<String> user_account_ids = new ArrayList<>();
                QuerySnapshot documentSnapshots = (QuerySnapshot) querySnapshot;
                for (DocumentSnapshot account : documentSnapshots) {
                    cUserAccountModel userAccountModel = account.toObject(cUserAccountModel.class);

                    assert userAccountModel != null;

                    cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
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

    private void onMergeUserProfiles(List<cOrganizationModel> stakeholderModels,
                                     List<cWorkspaceModel> teamModels, List<String> user_account_ids,
                                     @NonNull iReadRecordPermissionsCallback callback) {

        CollectionReference coProfileRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);

        Query userProfileQuery = coProfileRef
                .whereIn(FieldPath.documentId(), user_account_ids);

        readQueryDocuments(userProfileQuery, new cFirebaseCallBack() {
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
