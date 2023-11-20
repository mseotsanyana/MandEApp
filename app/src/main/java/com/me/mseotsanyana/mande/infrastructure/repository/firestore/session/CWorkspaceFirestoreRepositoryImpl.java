package com.me.mseotsanyana.mande.infrastructure.repository.firestore.session;

import static com.me.mseotsanyana.mande.application.structures.CFirestoreConstant.FAILURE;
import static com.me.mseotsanyana.mande.application.structures.CFirestoreConstant.SUCCESS;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreChildCallBack;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreRepository;
import com.me.mseotsanyana.mande.application.utils.CFirestoreUtility;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.application.repository.session.IWorkspaceRepository;
import com.me.mseotsanyana.mande.application.structures.CFirestoreConstant;
import com.me.mseotsanyana.mande.infrastructure.utils.CInfrastructureUtility;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class CWorkspaceFirestoreRepositoryImpl extends CFirestoreRepository
        implements IWorkspaceRepository {
    private static final String TAG = CWorkspaceFirestoreRepositoryImpl.class.getSimpleName();

    private final Context context;
    private final FirebaseFirestore db;
    private final CollectionReference organizationCollectionReference;
    private final CollectionReference workspaceCollectionReference;
    private final CollectionReference memberCollectionReference;

    private ListenerRegistration listenerRegistration;


    public CWorkspaceFirestoreRepositoryImpl(Context context) {
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
        this.organizationCollectionReference = db.collection(CFirestoreConstant.KEY_ORGANIZATIONS);
        this.workspaceCollectionReference = db.collection(CFirestoreConstant.KEY_WORKSPACES);
        this.memberCollectionReference = db.collection(CFirestoreConstant.KEY_WORKSPACE_MEMBERS);

    }

    /*************************************** Read Operations ***************************************
     *  @param workspaceModel workspace model
     * @param firestoreCallBack callback
     */
    @Override
    public void createWorkspace(@NonNull CWorkspaceModel workspaceModel,
                                CFirestoreCallBack firestoreCallBack) {
        int workspaceBITS = Integer.parseInt(workspaceModel.getWorkspaceServerID());

        Log.d(TAG, "Workspace BITS   ========  "+workspaceModel.getWorkspaceServerID());
        int workspaceServerID = CInfrastructureUtility.getWorkspaceServerID(workspaceBITS);
        Log.d(TAG, "Workspace Server ID   ========  "+workspaceServerID);

        if (workspaceServerID != 0) {
            String compositeServerID;
            compositeServerID = workspaceModel.getOrganizationServerID() + "_" + workspaceServerID;
            Date now = new Date();
            workspaceModel.setCompositeServerID(compositeServerID);
            workspaceModel.setCreatedDate(now);
            workspaceModel.setModifiedDate(now);
            workspaceModel.setWorkspaceServerID(String.valueOf(workspaceServerID));

            DocumentReference organizationDocumentReference, workspaceDocumentReference;
            organizationDocumentReference = organizationCollectionReference.document(
                    workspaceModel.getOrganizationServerID());

            workspaceDocumentReference = workspaceCollectionReference.document(compositeServerID);

            Map<String, Object> map = new HashMap<>();
            workspaceBITS |= workspaceServerID;
            map.put("workspaceBITS", workspaceBITS);

            WriteBatch batch = db.batch();
            batch.update(organizationDocumentReference, map);
            batch.set(workspaceDocumentReference, workspaceModel);

            batchWrite(batch, new CFirestoreCallBack() {
                @Override
                public void onFirebaseSuccess(Object object) {
                    firestoreCallBack.onFirebaseSuccess(CFirestoreConstant.SUCCESS);
                }

                @Override
                public void onFirebaseFailure(Object object) {
                    firestoreCallBack.onFirebaseFailure(object);
                }
            });
        } else {
            firestoreCallBack.onFirebaseFailure(FAILURE);
        }
    }

    /**************************************** READ ACTIONS *****************************************
     *
     * read workspaces
     *
     * @param organizationServerID   organization identification
     * @param userServerID           user identification
     * @param primaryWorkspaceBIT    primary workspace bit
     * @param secondaryWorkspaceBITS secondary workspace bits
     * @param statusBITS             status bits
     * @param callback               call back
     */
    @Override
    public void readWorkspaces(String organizationServerID, String userServerID,
                               int primaryWorkspaceBIT, List<Integer> secondaryWorkspaceBITS,
                               List<Integer> statusBITS, CFirestoreChildCallBack callback) {

        Query workspaceQuery = workspaceCollectionReference
                .whereEqualTo("organizationServerID", organizationServerID)
                .orderBy("createdDate");

        listenerRegistration = readQueryDocumentsByChildEventListener(
                workspaceQuery, new CFirestoreChildCallBack() {
                    @Override
                    public void onChildAdded(Object object) {
                        if (object != null) {
                            DocumentSnapshot document = (DocumentSnapshot) object;
                            if (document.exists()) {
                                CWorkspaceModel workspaceModel;
                                workspaceModel = document.toObject(CWorkspaceModel.class);
                                //workspaceModel.setOrganizationServerID(document.getId());
                                callback.onChildAdded(workspaceModel);
                            } else {
                                callback.onChildAdded(null);
                            }
                        } else {
                            callback.onChildAdded(null);
                        }
                    }

                    @Override
                    public void onChildChanged(Object object) {
                        if (object != null) {
                            DocumentSnapshot document = (DocumentSnapshot) object;
                            if (document.exists()) {
                                CWorkspaceModel workspaceModel;
                                workspaceModel = document.toObject(CWorkspaceModel.class);
                                //setOrganizationServerID(document.getId());
                                callback.onChildChanged(workspaceModel);
                            } else {
                                callback.onChildChanged(null);
                            }
                        } else {
                            callback.onChildChanged(null);
                        }
                    }

                    @Override
                    public void onChildRemoved(Object object) {
                        if (object != null) {
                            DocumentSnapshot document = (DocumentSnapshot) object;
                            if (document.exists()) {
                                CWorkspaceModel workspaceModel;
                                workspaceModel = document.toObject(CWorkspaceModel.class);
                                //workspaceModel.setOrganizationServerID(document.getId());
                                callback.onChildRemoved(workspaceModel);
                            } else {
                                callback.onChildRemoved(null);
                            }
                        } else {
                            callback.onChildRemoved(null);
                        }
                    }

                    @Override
                    public void onCancelled(Object object) {
                        callback.onCancelled(object);
                    }
                });
    }

    public void updateWorkspace(CWorkspaceModel workspaceModel, CFirestoreCallBack firestoreCallBack) {

        Map<String, Object> map = new HashMap<>();
        map.put("name", workspaceModel.getName());
        map.put("description",workspaceModel.getDescription());
        map.put("modifiedDate", new Date());

        if (!CFirestoreUtility.isEmptyOrNull(workspaceModel.getCompositeServerID())) {
            DocumentReference workspaceDocumentReference;
            workspaceDocumentReference = workspaceCollectionReference.document(
                    workspaceModel.getCompositeServerID());
                fireStoreUpdate(workspaceDocumentReference, map, new CFirestoreCallBack() {
                @Override
                public void onFirebaseSuccess(Object object) {
                    firestoreCallBack.onFirebaseSuccess(SUCCESS);
                }

                @Override
                public void onFirebaseFailure(Object object) {
                    firestoreCallBack.onFirebaseFailure(object);
                }
            });
        } else {
            firestoreCallBack.onFirebaseFailure(FAILURE);
        }
    }

    @Override
    public void deleteWorkspace(int workspaceBITS, CWorkspaceModel workspaceModel,
                                CFirestoreCallBack firestoreCallBack) {
        if (workspaceModel != null) {
            DocumentReference organizationDocumentReference, workspaceDocumentReference,
                    memberDocumentReference;

            organizationDocumentReference = organizationCollectionReference.document(
                    workspaceModel.getOrganizationServerID());
            workspaceDocumentReference = workspaceCollectionReference.document(
                    workspaceModel.getCompositeServerID());

            WriteBatch batch = db.batch();

            Map<String, Object> map = new HashMap<>();
            workspaceBITS -= Integer.parseInt(workspaceModel.getWorkspaceServerID());
            map.put("workspaceBITS", workspaceBITS);

            batch.update(organizationDocumentReference, map);
            batch.delete(workspaceDocumentReference);
            for (String memberServerID : workspaceModel.getWorkspaceMembers()) {
                memberDocumentReference = memberCollectionReference.document(memberServerID);
                batch.delete(memberDocumentReference);
            }

            batchWrite(batch, new CFirestoreCallBack() {
                @Override
                public void onFirebaseSuccess(Object object) {
                    firestoreCallBack.onFirebaseSuccess(CFirestoreConstant.SUCCESS);
                }

                @Override
                public void onFirebaseFailure(Object object) {
                    firestoreCallBack.onFirebaseFailure(object);
                }
            });
        } else {
            firestoreCallBack.onFirebaseFailure(FAILURE);
        }
    }

    /************************************** AUXILIARY ACTIONS *************************************/

    @Override
    public void removeListener() {
        if (listenerRegistration != null)
            listenerRegistration.remove();
    }

}


//
//    @Override
//    public void readTeamsWithMembers(String organizationServerID, String userServerID,
//                                     int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                                     List<Integer> statusBITS,
//                                     iReadTeamsWithMembersCallback callback) {
//
//        CollectionReference coTeamRef = db.collection(CFirebaseConstant.KEY_WORKSPACES);
//
//        Query teamQuery = coTeamRef
//                .whereEqualTo("organizationOwnerID", organizationServerID)
//                .whereIn("statusBIT", statusBITS);
//
//        teamQuery.get()
//                .addOnCompleteListener(task -> {
//                    Map<String, CWorkspaceModel> teamModelMap = new HashMap<>();
//                    for (DocumentSnapshot team_doc : Objects.requireNonNull(task.getResult())) {
//                        CWorkspaceModel teamModel = team_doc.toObject(CWorkspaceModel.class);
//
//                        if (teamModel != null) {
//                            teamModel.setCompositeServerID(team_doc.getId());
//
//                            CFirestoreUtility.cUnixPerm perm = new CFirestoreUtility.cUnixPerm();
//                            perm.setUserOwnerID(teamModel.getUserOwnerID());
//                            perm.setTeamOwnerBIT(teamModel.getWorkspaceOwnerBIT());
//                            perm.setUnixpermBITS(teamModel.getUnixpermBITS());
//
////FIXME                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
////                                    secondaryTeamBITS)) {
////                                teamModelMap.put(teamModel.getCompositeServerID(), teamModel);
////                            }
//                        }
//                    }
//
//                    filterTeamMembers(teamModelMap, organizationServerID, userServerID,
//                            primaryTeamBIT, secondaryTeamBITS, statusBITS, callback);
//
//                })
//                .addOnFailureListener(e -> callback.onReadTeamsWithMembersFailed(
//                        "Failed to read teams with members"));
//
//    }
//
//    private void filterTeamMembers(Map<String, CWorkspaceModel> teamModelMap,
//                                   String organizationServerID, String userServerID,
//                                   int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                                   List<Integer> statusBITS,
//                                   iReadTeamsWithMembersCallback callback) {
//
//        if(!teamModelMap.isEmpty()) {
//            CollectionReference coTeamMemberRef = db.collection(CFirebaseConstant.KEY_TEAM_MEMBERS);
//            List<String> team_id_set = new ArrayList<>(teamModelMap.keySet());
//            Query teamMemberQuery = coTeamMemberRef.whereIn("teamMemberServerID", team_id_set);
//
//            teamMemberQuery.get()
//                    .addOnCompleteListener(task -> {
//                        /* retrieve a pair of user account and team member ids */
//                        List<Pair<String, String>> team_acc_ids = new ArrayList<>();
//                        for (DocumentSnapshot acc_doc : Objects.requireNonNull(task.getResult())) {
//                            String accountID = acc_doc.getString("userAccountServerID");
//                            String teamID = acc_doc.getString("teamMemberServerID");
//
//                            if (teamID != null && accountID != null) {
//                                team_acc_ids.add(new Pair<>(teamID, accountID));
//                            }
//                        }
//
//                        /* map team model with a corresponding list of user account ids */
//                        Map<CWorkspaceModel, List<String>> team_user_acc_ids = new HashMap<>();
//                        for (Map.Entry<String, CWorkspaceModel> entry : teamModelMap.entrySet()) {
//                            CWorkspaceModel teamModel = entry.getValue();
//
//                            List<String> user_acc_ids = new ArrayList<>();
//                            for (Pair<String, String> pair : team_acc_ids) {
//                                if (teamModel.getCompositeServerID().equals(pair.first)) {
//                                    user_acc_ids.add(pair.second);
//                                }
//                            }
//                            team_user_acc_ids.put(teamModel, user_acc_ids);
//                        }
//
//                        // filter the account of the team member
//                        filterMemberAccounts(team_user_acc_ids, organizationServerID,
//                                userServerID, primaryTeamBIT, secondaryTeamBITS, statusBITS,
//                                callback);
//                    })
//                    .addOnFailureListener(e -> callback.onReadTeamsWithMembersFailed(
//                            "Failed to filter team members"));
//        }else {
//            callback.onReadTeamsWithMembersFailed("Failed to filter team members");
//        }
//    }
//
//    private void filterMemberAccounts(Map<CWorkspaceModel, List<String>> team_user_acc_ids,
//                                      String organizationServerID, String userServerID,
//                                      int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                                      List<Integer> statusBITS,
//                                      iReadTeamsWithMembersCallback callback) {
//
//        CollectionReference coUserAccountsRef = db.collection(CFirebaseConstant.KEY_ORGANIZATION_MEMBERS);
//
//        /* collapsing the lists of user account ids to a list of all user account ids */
//        Collection<List<String>> user_account_ids = team_user_acc_ids.values();
//        List<String> account_ids = new ArrayList<>();
//        for (List<String> acc_ids : user_account_ids) {
//            account_ids.addAll(acc_ids);
//        }
//
//        Query userAccountQuery = coUserAccountsRef
//                .whereEqualTo("organizationOwnerID", organizationServerID)
//                .whereIn("userAccountServerID", account_ids);
//
//        userAccountQuery.get()
//                .addOnCompleteListener(task -> {
//                    /* retrieve a pair of user account and profile ids */
//                    Map<String, String> user_acc_prof_map = new HashMap<>();
//                    for (DocumentSnapshot account : Objects.requireNonNull(task.getResult())) {
//                        CUserAccountModel accountModel;
//                        accountModel = account.toObject(CUserAccountModel.class);
//
//                        if (accountModel != null) {
//                            CFirestoreUtility.cUnixPerm perm = new CFirestoreUtility.cUnixPerm();
//                            perm.setUserOwnerID(accountModel.getUserOwnerID());
//                            perm.setTeamOwnerBIT(accountModel.getWorkspaceOwnerBIT());
//                            perm.setUnixpermBITS(accountModel.getUnixpermBITS());
//                            perm.setStatusBIT(accountModel.getStatusBIT());
//
////FIXME                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
////                                    secondaryTeamBITS, statusBITS)) {
////
////                                String userAccountID = accountModel.getUserAccountServerID();
////                                String userProfileID = accountModel.getUserServerID();
////                                user_acc_prof_map.put(userAccountID, userProfileID);
////                            }
//                        }
//                    }
//
//                    /* map team model with a corresponding list of user profile ids */
//                    Map<CWorkspaceModel, List<String>> team_user_profile_map = new HashMap<>();
//
//                    for (Map.Entry<CWorkspaceModel,
//                            List<String>> map_entry : team_user_acc_ids.entrySet()) {
//                        CWorkspaceModel teamModel = map_entry.getKey();
//
//                        List<String> user_profile_ids = new ArrayList<>();
//                        for (Map.Entry<String, String> pair_entry : user_acc_prof_map.entrySet()) {
//                            String user_account_id = pair_entry.getKey();
//                            String user_profile_id = pair_entry.getValue();
//                            if (map_entry.getValue().contains(user_account_id)) {
//                                user_profile_ids.add(user_profile_id);
//                            }
//                        }
//
//                        team_user_profile_map.put(teamModel, user_profile_ids);
//                    }
//
//                    /* filter user profiles of the corresponding teams */
//                    filterUserProfiles(team_user_profile_map, callback);
//                })
//                .addOnFailureListener(e -> callback.onReadTeamsWithMembersFailed(
//                        "Failed to filter member accounts"));
//    }
//
//    private void filterUserProfiles(Map<CWorkspaceModel, List<String>> team_user_profile_map,
//                                    iReadTeamsWithMembersCallback callback) {
//
//        CollectionReference coUserProfilesRef = db.collection(CFirebaseConstant.KEY_USERPROFILES);
//
//        Collection<List<String>> col_user_profile_ids = team_user_profile_map.values();
//        List<String> user_profile_ids = new ArrayList<>();
//        for (List<String> acc_ids : col_user_profile_ids) {
//            user_profile_ids.addAll(acc_ids);
//        }
//
//        Query userProfileQuery = coUserProfilesRef
//                .whereIn(FieldPath.documentId(), user_profile_ids);
//
//        userProfileQuery.get()
//                .addOnCompleteListener(task -> {
//                    /* retrieve corresponding user profiles to their ids */
//                    List<CUserProfileModel> userProfileModels = new ArrayList<>();
//                    for (QueryDocumentSnapshot doc :
//                            Objects.requireNonNull(task.getResult())) {
//                        CUserProfileModel user = doc.toObject(CUserProfileModel.class);
//                        user.setUserServerID(doc.getId());
//                        userProfileModels.add(user);
//                    }
//
//                    /* map team model with a corresponding list of user profile models */
//                    Map<CWorkspaceModel, List<CUserProfileModel>> teamMemberMap = new HashMap<>();
//                    for (Map.Entry<CWorkspaceModel,
//                            List<String>> entry : team_user_profile_map.entrySet()) {
//                        CWorkspaceModel teamModel = entry.getKey();
//                        List<CUserProfileModel> users = new ArrayList<>();
//                        for (CUserProfileModel user : userProfileModels) {
//                            if (entry.getValue().contains(user.getUserServerID())) {
//                                users.add(user);
//                            }
//                        }
//
//                        teamMemberMap.put(teamModel, users);
//                    }
//
//                    // call back on organization members
//                    callback.onReadTeamsWithMembersSucceeded(teamMemberMap);
//                })
//                .addOnFailureListener(e -> callback.onReadTeamsWithMembersFailed(
//                        "Failed to filter user profile"));
//    }
//
//    @Override
//    public void readTeamsWithRoles(String organizationServerID, String userServerID,
//                                   int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                                   List<Integer> statusBITS, iReadTeamsWithRolesCallback callback) {
//
//        CollectionReference coTeamRef = db.collection(CFirebaseConstant.KEY_WORKSPACES);
//
//        Query teamQuery = coTeamRef
//                .whereEqualTo("organizationOwnerID", organizationServerID)
//                .whereIn("statusBIT", statusBITS);
//
//        teamQuery.get()
//                .addOnCompleteListener(task -> {
//                    Map<String, CWorkspaceModel> teamModelMap = new HashMap<>();
//                    for (DocumentSnapshot team_doc : Objects.requireNonNull(task.getResult())) {
//                        CWorkspaceModel teamModel = team_doc.toObject(CWorkspaceModel.class);
//
//                        if (teamModel != null) {
//                            teamModel.setCompositeServerID(team_doc.getId());
//
//                            CFirestoreUtility.cUnixPerm perm = new CFirestoreUtility.cUnixPerm();
//                            perm.setUserOwnerID(teamModel.getUserOwnerID());
//                            perm.setTeamOwnerBIT(teamModel.getWorkspaceOwnerBIT());
//                            perm.setUnixpermBITS(teamModel.getUnixpermBITS());
//
////FIXME                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
////                                    secondaryTeamBITS)) {
////                                teamModelMap.put(teamModel.getCompositeServerID(), teamModel);
////                            }
//                        }
//                    }
//
//                    filterTeamsWithRoles(teamModelMap, organizationServerID, userServerID,
//                            primaryTeamBIT, secondaryTeamBITS, statusBITS, callback);
//
//                })
//                .addOnFailureListener(e -> callback.onReadTeamsWithRolesFailed(
//                        "Failed to read teams with roles"));
//
//    }
//
//    private void filterTeamsWithRoles(Map<String, CWorkspaceModel> teamModelMap,
//                                      String organizationServerID, String userServerID,
//                                      int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                                      List<Integer> statusBITS, iReadTeamsWithRolesCallback callback) {
//
//        CollectionReference coTeamRoleRef = db.collection(CFirebaseConstant.KEY_TEAM_ROLES);
//        List<String> team_id_set = new ArrayList<>(teamModelMap.keySet());
//        Query teamRoleQuery = coTeamRoleRef.whereIn("teamServerID", team_id_set);
//
//        teamRoleQuery.get()
//                .addOnCompleteListener(task -> {
//                    /* retrieve a pair of user account and team member ids */
//                    List<Pair<String, String>> team_role_pair = new ArrayList<>();
//                    for (DocumentSnapshot team_role_doc : Objects.requireNonNull(task.getResult())) {
//                        String roleID = team_role_doc.getString("roleServerID");
//                        String teamID = team_role_doc.getString("teamServerID");
//
//                        if (teamID != null && roleID != null) {
//                            team_role_pair.add(new Pair<>(teamID, roleID));
//                        }
//                    }
//
//                    /* map team model with a corresponding list of role ids */
//                    Map<CWorkspaceModel, List<String>> team_role_ids = new HashMap<>();
//                    for (Map.Entry<String, CWorkspaceModel> entry : teamModelMap.entrySet()) {
//                        CWorkspaceModel teamModel = entry.getValue();
//
//                        List<String> role_ids = new ArrayList<>();
//                        for (Pair<String, String> pair : team_role_pair) {
//                            if (teamModel.getCompositeServerID().equals(pair.first)) {
//                                role_ids.add(pair.second);
//                            }
//                        }
//                        team_role_ids.put(teamModel, role_ids);
//                    }
//
//                    // filter the roles of the corresponding teams
//                    mapRolesToTeams(team_role_ids, organizationServerID, userServerID,
//                            primaryTeamBIT, secondaryTeamBITS, statusBITS, callback);
//                })
//                .addOnFailureListener(e -> callback.onReadTeamsWithRolesFailed(
//                        "Failed to filter team roles"));
//    }
//
//
//    private void mapRolesToTeams(Map<CWorkspaceModel, List<String>> team_role_map,
//                                 String organizationServerID, String userServerID,
//                                 int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                                 List<Integer> statusBITS, iReadTeamsWithRolesCallback callback) {
//
//        CollectionReference coRolesRef = db.collection(CFirebaseConstant.KEY_ROLES);
//
//        Collection<List<String>> col_role_ids = team_role_map.values();
//        List<String> role_ids = new ArrayList<>();
//        for (List<String> acc_ids : col_role_ids) {
//            role_ids.addAll(acc_ids);
//        }
//
//        Query roleQuery = coRolesRef
//                .whereEqualTo("organizationOwnerID", organizationServerID)
//                .whereIn(FieldPath.documentId(), role_ids);
//
//        roleQuery.get()
//                .addOnCompleteListener(task -> {
//                    /* retrieve corresponding roles to their ids */
//                    List<CPrivilegeModel> roleModels = new ArrayList<>();
//                    for (QueryDocumentSnapshot doc :
//                            Objects.requireNonNull(task.getResult())) {
//                        CPrivilegeModel roleModel = doc.toObject(CPrivilegeModel.class);
//                        roleModel.setPrivilegeServerID(doc.getId());
//
//                        CFirestoreUtility.cUnixPerm perm = new CFirestoreUtility.cUnixPerm();
//                        perm.setUserOwnerID(roleModel.getUserOwnerID());
//                        perm.setTeamOwnerBIT(roleModel.getWorkspaceOwnerBIT());
//                        perm.setUnixpermBITS(roleModel.getUnixpermBITS());
//                        perm.setStatusBIT(roleModel.getStatusBIT());
//
////FIXME                        if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
////                                secondaryTeamBITS, statusBITS)) {
////                            roleModels.add(roleModel);
////                        }
//                    }
//
//                    /* map team model with a corresponding list of role models */
//                    Map<CWorkspaceModel, List<CPrivilegeModel>> teamRoleMap = new HashMap<>();
//                    for (Map.Entry<CWorkspaceModel, List<String>> entry : team_role_map.entrySet()) {
//                        CWorkspaceModel teamModel = entry.getKey();
//                        List<CPrivilegeModel> roles = new ArrayList<>();
//                        for (CPrivilegeModel roleModel : roleModels) {
//                            if (entry.getValue().contains(roleModel.getPrivilegeServerID())) {
//                                roles.add(roleModel);
//                            }
//                        }
//
//                        teamRoleMap.put(teamModel, roles);
//                    }
//
//                    // call back on organization members
//                    callback.onReadTeamsWithRolesSucceeded(teamRoleMap);
//                })
//                .addOnFailureListener(e -> callback.onReadTeamsWithRolesFailed(
//                        "Failed to map teams to roles"));
//    }
//
//
//    /**
//     * read role teams
//     *
//     * @param teamServerID         team identification
//     * @param organizationServerID organization identification
//     * @param userServerID         user identification
//     * @param primaryTeamBIT       primary team bit
//     * @param secondaryTeamBITS    secondary team bits
//     * @param statusBITS           status bits
//     * @param callback             call back
//     */
//    public void readTeamRoles(String teamServerID, String organizationServerID, String
//            userServerID,
//                              int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                              List<Integer> statusBITS, iReadTeamRolesCallback callback) {
//
//        CollectionReference coTeamRolesRef = db.collection(CFirebaseConstant.KEY_TEAM_ROLES);
//        Query teamRolesQuery = coTeamRolesRef.whereEqualTo("teamServerID", teamServerID);
//
//        teamRolesQuery.get()
//                .addOnCompleteListener(task -> {
//                    Set<String> role_id_set = new HashSet<>();
//                    for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
//                        String roleID = role_doc.getString("roleServerID");
//                        if (roleID != null) {
//                            role_id_set.add(roleID);
//                        }
//                    }
//                    // filter the roles of the team identification
//                    List<String> team_ids = new ArrayList<>(role_id_set);
//                    filterTeamRoles(team_ids, organizationServerID, userServerID, primaryTeamBIT,
//                            secondaryTeamBITS, statusBITS, callback);
//                })
//                .addOnFailureListener(e -> {
//                    callback.onReadTeamsFailed("Failed to read Organization." + e);
//                    Log.w(TAG, "Failed to read value.", e);
//                });
//
//    }
//
//    /**
//     * read teams
//     *
//     * @param role_ids             list of team identifications
//     * @param organizationServerID organization identification
//     * @param userServerID         user identification
//     * @param primaryTeamBIT       primary team bit
//     * @param secondaryTeamBITS    secondary team bits
//     * @param statusBITS           status bits
//     * @param callback             call back
//     */
//    private void filterTeamRoles(List<String> role_ids, String organizationServerID,
//                                 String userServerID, int primaryTeamBIT,
//                                 List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
//                                 iReadTeamRolesCallback callback) {
//
//        CollectionReference coTeamRef = db.collection(CFirebaseConstant.KEY_ROLES);
//
//        Query teamQuery = coTeamRef
//                .whereEqualTo("organizationOwnerID", organizationServerID)
//                .whereIn("teamServerID", role_ids);
//
//        teamQuery.get()
//                .addOnCompleteListener(task -> {
//                    List<CPrivilegeModel> roleModels = new ArrayList<>();
//                    for (DocumentSnapshot role_doc :
//                            Objects.requireNonNull(task.getResult())) {
//
//                        CPrivilegeModel roleModel = role_doc.toObject(CPrivilegeModel.class);
//                        if (roleModel != null) {
//                            CFirestoreUtility.cUnixPerm perm = new CFirestoreUtility.cUnixPerm();
//                            perm.setUserOwnerID(roleModel.getUserOwnerID());
//                            perm.setTeamOwnerBIT(roleModel.getWorkspaceOwnerBIT());
//                            perm.setUnixpermBITS(roleModel.getUnixpermBITS());
//                            perm.setStatusBIT(roleModel.getStatusBIT());
//
////FIXME                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
////                                    secondaryTeamBITS, statusBITS)) {
////                                roleModels.add(roleModel);
////                            }
//                        }
//                    }
//
//                    /* call back on role teams */
//                    callback.onReadTeamRolesSucceeded(roleModels);
//                })
//                .addOnFailureListener(e -> {
//                    callback.onReadTeamRolesFailed("Failed to read teams.");
//                    Log.w(TAG, "Failed to read value.", e);
//                });
//    }


//        workspaceQuery.get()
//                .addOnCompleteListener(task -> {
//                    List<CWorkspaceModel> teamModels = new ArrayList<>();
//                    for (DocumentSnapshot workspace_doc : Objects.requireNonNull(task.getResult())) {
//                        CWorkspaceModel workspaceModel;
//                        workspaceModel = workspace_doc.toObject(CWorkspaceModel.class);
//
//                        if (workspaceModel != null) {
//                            teamModels.add(teamModel);
////                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
////                            perm.setUserOwnerID(teamModel.getUserOwnerID());
////                            perm.setTeamOwnerBIT(teamModel.getWorkspaceOwnerBIT());
////                            perm.setUnixpermBITS(teamModel.getUnixpermBITS());
////
//////FIXME                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//////                                    secondaryTeamBITS)) {
//////                                teamModels.add(teamModel);
//////                            }
//                        }
//                    }
//
//                    // call back on teams
//                    callback.onReadTeamsSucceeded(teamModels);
//                })
//                .addOnFailureListener(e -> callback.onReadTeamsFailed(
//                        "Failed to read roles"));