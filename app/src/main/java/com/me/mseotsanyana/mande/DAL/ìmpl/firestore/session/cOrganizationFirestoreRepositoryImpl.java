package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cPlanModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cPrivilegeModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cWorkspaceModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.entities.models.utils.cCommonPropertiesModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseCallBack;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseChildCallBack;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cOrganizationFirestoreRepositoryImpl extends cFirebaseRepository
        implements iOrganizationRepository {
    private static final String TAG = cOrganizationFirestoreRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private final FirebaseFirestore database;
    private final Context context;

    private ListenerRegistration listenerRegistration;

    public cOrganizationFirestoreRepositoryImpl(Context context) {
        this.database = FirebaseFirestore.getInstance();
        this.context = context;
    }

    /* ##################################### CREATE ACTIONS ##################################### */

    /**
     * create organization
     *
     * @param organizationModel organization model
     * @param callback          call back
     */
    @Override
    public void createOrganization(cOrganizationModel organizationModel,
                                   iCreateOrganizationCallback callback) {
        // get current logged in user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userServerID = currentUser.getUid();
            /* create a batch object for creation of an organization */
            WriteBatch batch = database.batch();

            // create new organization
            CollectionReference collectionStakeholderRef = database.collection(
                    cRealtimeHelper.KEY_ORGANIZATIONS);
            DocumentReference organizationDocRef = collectionStakeholderRef.document();

            Map<String, Object> organization = new HashMap<>();

            String organizationServerID = organizationDocRef.getId();
            organization.put("organizationServerID", organizationServerID);
            organization.put("name", organizationModel.getName());
            organization.put("email", organizationModel.getEmail());
            organization.put("website", organizationModel.getWebsite());
            organization.put("typeID", organizationModel.getTypeID());
            organization.put("childorganizations", organizationModel.getChildOrganizationModels());

            // update organization default dates
            Date currentDate = new Date();
            organization.put("createdDate", currentDate);
            organization.put("modifiedDate", currentDate);

            // update organization model with default values
            cCommonPropertiesModel properties = cDatabaseUtils.getCommonModel(context);

            organization.put("userOwnerID", userServerID);
            organization.put("organizationOwnerID", organizationServerID);
            organization.put("workspaceOwnerBIT", properties.getWorkspaceOwnerBIT());
            organization.put("unixpermBITS", properties.getUnixpermBITS());
            organization.put("statusBIT", properties.getStatusBIT());

            switch (organizationModel.getTypeID()) {
                case 0:
                    organizationModel.setType("PARTNER");
                    break;
                case 1:
                    organizationModel.setType("DONOR");
                    break;
                case 2:
                    organizationModel.setType("BENEFICIARY");
                    break;
                case 3:
                    organizationModel.setType("IMPLEMENTING AGENCY");
                    break;
                default:
                    Log.d(TAG, "Error in creating an organization");
            }
            organization.put("type", organizationModel.getType());

            // 1. adding newly created parent organization to the batch
            batch.set(organizationDocRef, organization);

            // 2. adding the workspace to the organization just created to the batch

            /* create a default administrator workspace during the creation of an organization */
            cWorkspaceModel workspaceModel = cDatabaseUtils.getAdminWorkspaceModel(context,
                    organizationServerID, properties);
            workspaceModel.setUserOwnerID(userServerID);
            workspaceModel.setOrganizationOwnerID(organizationServerID);
            String compositeServerID = workspaceModel.getCompositeServerID();
            String workspaceServerID = workspaceModel.getWorkspaceServerID();

            CollectionReference coWorkspacesRef;
            coWorkspacesRef = database.collection(cRealtimeHelper.KEY_WORKSPACES);
            DocumentReference workspaceDocRef = coWorkspacesRef.document(compositeServerID);
            batch.set(workspaceDocRef, workspaceModel);

            // 3. adding admin member to the admin workspace just created to the batch

            // add the current user to the team of the organization just created
            CollectionReference coWorkspaceMembersRef;
            coWorkspaceMembersRef = database.collection(cRealtimeHelper.KEY_WORKSPACE_MEMBERS);
            Map<String, Object> workspace_members = new HashMap<>();
            workspace_members.put("userAccountServerID", organizationServerID + "_" + userServerID);
            workspace_members.put("workspaceMemberServerID", compositeServerID);
            workspace_members.put("organizationServerID", organizationServerID);
            workspace_members.put("workspaceServerID", workspaceServerID);
            workspace_members.put("userServerID", userServerID);

            DocumentReference workspaceMemberDocRef = coWorkspaceMembersRef.document(
                    organizationServerID + "_" + userServerID + "_" + compositeServerID);
            batch.set(workspaceMemberDocRef, workspace_members);

            // 4. adding new account of admin member of an organization to the batch.

            /* read a default freemium plan from json associated with the administrator of
               the organization just created */
            cPlanModel freemiumPlanModel = cDatabaseUtils.getDefaultPlanModel(context);
            /* create a user account of the administrator with the organization just created */
            String planSeverID = freemiumPlanModel.getPlanServerID();
            cUserAccountModel userAccountModel = createUserAccount(organizationServerID,
                    userServerID, workspaceServerID, planSeverID, properties);
            CollectionReference coUserAccountsRef;
            coUserAccountsRef = database.collection(cRealtimeHelper.KEY_ORGANIZATION_MEMBERS);
            DocumentReference userAccountDocRef;
            userAccountDocRef = coUserAccountsRef.document(organizationServerID + "_" +
                    userServerID);
            batch.set(userAccountDocRef, userAccountModel);

            // 5. adding admin privileges of the admin workspace to the batch.

            /* create a default workspace privileges during the creation of the organization */
            cPrivilegeModel privilegeModel = cDatabaseUtils.getAdminPrivilegeModel(context,
                    properties);

            privilegeModel.setPrivilegeServerID(compositeServerID);
            privilegeModel.setUserOwnerID(userServerID);
            privilegeModel.setOrganizationOwnerID(organizationServerID);

            CollectionReference coPrivilegeRef;
            coPrivilegeRef = database.collection(cRealtimeHelper.KEY_WORKSPACE_PRIVILEGES);
            DocumentReference privilegeDocRef = coPrivilegeRef.document(compositeServerID);
            batch.set(privilegeDocRef, privilegeModel);

            // commit the batch file
            batchWrite(batch, new cFirebaseCallBack() {
                @Override
                public void onFirebaseSuccess(Object object) {
                    // once organization successfully created, make sure that it is activated
                    /*Query query = coUserAccountsRef.whereEqualTo("userServerID", userServerID);
                    query.get().addOnCompleteListener(task -> {
                        for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                            cUserAccountModel useraccount = doc.toObject(cUserAccountModel.class);
                            if (!(useraccount.getOrganizationServerID().equals(organizationServerID))) {
                                useraccount.setCurrentOrganization(false);
                                coUserAccountsRef.document(
                                        useraccount.getOrganizationServerID() + "_" +
                                                userServerID).set(useraccount);
                            }
                        }
                    });*/
                    callback.onCreateOrganizationSucceeded(
                            "Successfully created an organization.");
                }

                @Override
                public void onFirebaseFailure(Object object) {
                    callback.onCreateOrganizationFailed(
                            "Failed to create an organization.");
                }
            });
        } else {
            callback.onCreateOrganizationFailed("Error, Failed to create an organization.");
        }
    }

//    /**
//     * create organization
//     *
//     * @param stakeholderModel stakeholder model
//     * @param callback         call back
//     */
//    @Override
//    public void createStakeholder(cOrganizationModel stakeholderModel,
//                                  iCreateOrganizationCallback callback) {
//        // get current logged in user
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) {
//            String userServerID = currentUser.getUid();
//            /* create a batch object for creation of an organization */
//            WriteBatch batch = database.batch();
//
//            // create new organization
//            CollectionReference collectionStakeholderRef = database.collection(
//                    cRealtimeHelper.KEY_STAKEHOLDERS);
//            DocumentReference stakeholderDocRef = collectionStakeholderRef.document();
//            String organizationServerID = stakeholderDocRef.getId();
//
//            // update organization default dates
//            Date currentDate = new Date();
//            stakeholderModel.setCreatedDate(currentDate);
//            stakeholderModel.setModifiedDate(currentDate);
//
//            // update organization model with default values
//            cCommonPropertiesModel commonPropertiesModel = cDatabaseUtils.getCommonModel(context);
//            stakeholderModel.setUserOwnerID(userServerID);
//            stakeholderModel.setOrganizationOwnerID(organizationServerID);
//            stakeholderModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
//            stakeholderModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
//            stakeholderModel.setStatusBIT(commonPropertiesModel.getCstatusBIT());
//
//            switch (stakeholderModel.getTypeID()) {
//                case 0:
//                    stakeholderModel.setType("MY ORGANIZATION");
//                    break;
//                case 1:
//                    stakeholderModel.setType("PARTNER");
//                    break;
//
//                case 2:
//                    stakeholderModel.setType("FUNDER");
//                    break;
//                case 3:
//                    stakeholderModel.setType("BENEFICIARY");
//                    break;
//                case 4:
//                    stakeholderModel.setType("IMPLEMENTING AGENCY");
//                    break;
//                default:
//                    Log.d(TAG, "Error in creating an organization");
//            }
//
//            // 1. adding newly created parent organization to the batch
//            batch.set(stakeholderDocRef, stakeholderModel);
//
//            // 2. adding the team to the organization just created to the batch
//
//            /* create a default administrator team during the creation of an organization */
//            cWorkspaceModel teamModel = cDatabaseUtils.getAdminWorkspaceModel(context, organizationServerID,
//                    commonPropertiesModel);
//            teamModel.setUserOwnerID(userServerID);
//            teamModel.setOrganizationOwnerID(organizationServerID);
//            String compositeServerID = teamModel.getCompositeServerID();
//            CollectionReference coTeamsRef = database.collection(cRealtimeHelper.KEY_TEAMS);
//            DocumentReference teamDocRef = coTeamsRef.document(compositeServerID);
//            batch.set(teamDocRef, teamModel);
//
//            // 3. adding admin team member to the team just created to the batch
//
//            // add the current user to the team of the organization just created
//            CollectionReference coTeamMembersRef;
//            coTeamMembersRef = database.collection(cRealtimeHelper.KEY_TEAM_MEMBERS);
//            Map<String, Object> team_members = new HashMap<>();
//            team_members.put("userAccountServerID", organizationServerID + "_" + userServerID);
//            team_members.put("teamMemberServerID", compositeServerID);
//            DocumentReference teamMemberDocRef = coTeamMembersRef.document(
//                    organizationServerID + "_" + userServerID + "_" + compositeServerID);
//            batch.set(teamMemberDocRef, team_members);
//
//            // 4. adding new account of admin member of an organization to the batch.
//
//            /* read a default freemium plan from json associated with the administrator of
//               the organization just created */
//            cPlanModel freemiumPlanModel = cDatabaseUtils.getDefaultPlanModel(context);
//            /* create a user account of the administrator with the organization just created */
//            String teamServerID = teamModel.getWorkspaceServerID();
//            String planSeverID = freemiumPlanModel.getPlanServerID();
//            cUserAccountModel userAccountModel = createUserAccount(organizationServerID,
//                    userServerID, teamServerID, planSeverID, commonPropertiesModel);
//            CollectionReference coUserAccountsRef;
//            coUserAccountsRef = database.collection(cRealtimeHelper.KEY_USERACCOUNTS);
//            DocumentReference userAccountDocRef;
//            userAccountDocRef = coUserAccountsRef.document(organizationServerID + "_" +
//                    userServerID);
//            batch.set(userAccountDocRef, userAccountModel);
//
//            // 5. adding role of the organization to the batch.
//
//            /* create a default administrator role during the creation of an organization */
//            cPrivilegeModel roleModel = null;//cDatabaseUtils.getAdminRoleModel(context, commonPropertiesModel);
//            roleModel.setUserOwnerID(userServerID);
//            roleModel.setOrganizationOwnerID(organizationServerID);
//
//            CollectionReference coRoleRef = database.collection(cRealtimeHelper.KEY_ROLES);
//            DocumentReference roleDocRef = coRoleRef.document();
//            String roleServerID = roleDocRef.getId();
//            batch.set(roleDocRef, roleModel);
//
//            // 6. adding team roles to the batch.
//
//            // add admin role to the user of the organization
//            CollectionReference coTeamRolesRef = database.collection(cRealtimeHelper.KEY_TEAM_ROLES);
//            Map<String, Object> team_roles = new HashMap<>();
//            team_roles.put("teamServerID", compositeServerID);
//            team_roles.put("roleServerID", roleServerID);
//            DocumentReference teamRoleDocRef;
//            teamRoleDocRef = coTeamRolesRef.document(compositeServerID + "_" +
//                    roleServerID);
//            batch.set(teamRoleDocRef, team_roles);
//
//            // 7. adding role permissions to the batch.
//
//            /* read a default permissions associated with the administrator role during
//               the creation of an organization */
//            cPermissionModel permissionModel = cDatabaseUtils.createAdminPermissions(context);
//            permissionModel.setRoleServerID(roleServerID);
//            permissionModel.setName(roleModel.getName());
//
//            // add permissions to the role
//            permissionModel.setDescription("Administrator permissions for both entity and " +
//                    "property level access control");
//            CollectionReference coRolePermsRef = database.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
//            DocumentReference rolePermDocRef = coRolePermsRef.document(roleServerID);
//            batch.set(rolePermDocRef, permissionModel);
//
//            // commit the batch file
//            batchWrite(batch, new cFirebaseCallBack() {
//                @Override
//                public void onFirebaseSuccess(Object object) {
//                    // make sure the just created organization is activated
//                    Query query = coUserAccountsRef.whereEqualTo("userServerID", userServerID);
//                    query.get().addOnCompleteListener(task -> {
//                        for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
//                            cUserAccountModel useraccount = doc.toObject(cUserAccountModel.class);
//                            if (!(useraccount.getOrganizationServerID().equals(organizationServerID))) {
//                                useraccount.setCurrentOrganization(false);
//                                coUserAccountsRef.document(
//                                        useraccount.getOrganizationServerID() + "_" +
//                                                userServerID).set(useraccount);
//                            }
//                        }
//                    });
//                    callback.onCreateStakeholderSucceeded(
//                            "Successfully created an organization.");
//                }
//
//                @Override
//                public void onFirebaseFailure(Object object) {
//                    callback.onCreateStakeholderFailed(
//                            "Failed to create an organization.");
//                }
//            });
//        } else {
//            callback.onCreateStakeholderFailed("Error, Failed to create an organization.");
//        }
//    }

    /**
     * create user account in an organization
     *
     * @param organizationServerID  organization identification
     * @param userServerID          user identification
     * @param teamServerID          team identification
     * @param planServerID          plan identification
     * @param commonPropertiesModel common properties model
     * @return user account
     */
    private cUserAccountModel createUserAccount(String organizationServerID, String userServerID,
                                                String teamServerID, String planServerID,
                                                cCommonPropertiesModel commonPropertiesModel) {
        cUserAccountModel userAccountModel = new cUserAccountModel();

        userAccountModel.setUserAccountServerID(organizationServerID + "_" + userServerID);
        userAccountModel.setOrganizationServerID(organizationServerID);
        userAccountModel.setWorkspaceServerID(teamServerID);
        userAccountModel.setUserServerID(userServerID);
        userAccountModel.setPlanServerID(planServerID);
        userAccountModel.setCurrentOrganization(true);

        // current date
        Date currentDate = new Date();
        userAccountModel.setCreatedDate(currentDate);
        userAccountModel.setModifiedDate(currentDate);

        // update user account model with default values
        userAccountModel.setUserOwnerID(userServerID);
        userAccountModel.setOrganizationOwnerID(organizationServerID);
        userAccountModel.setWorkspaceOwnerBIT(commonPropertiesModel.getWorkspaceOwnerBIT());
        userAccountModel.setUnixpermBITS(commonPropertiesModel.getUnixpermBITS());
        userAccountModel.setStatusBIT(commonPropertiesModel.getStatusBIT());

        return userAccountModel;
    }

    /* ###################################### READ ACTIONS ###################################### */

    @Override
    public void readOrganizationWorkspaces(cFirebaseCallBack firebaseCallBack) {
        CollectionReference coUserOrganizationsRef;
        coUserOrganizationsRef = database.collection(cRealtimeHelper.KEY_ORGANIZATION_MEMBERS);

        String userServerID;
        userServerID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        Query query = coUserOrganizationsRef
                .whereEqualTo("userServerID", userServerID);

        listenerRegistration = readQueryDocumentsByListener(query, new cFirebaseCallBack() {
            @Override
            public void onFirebaseSuccess(Object object) {
                if (object != null) {
                    QuerySnapshot querySnapshot = (QuerySnapshot) object;
                    List<String> organization_ids = new ArrayList<>();
                    for (DocumentSnapshot ds : querySnapshot.getDocuments()) {
                        String organizationServerID = ds.getString("organizationServerID");
                        if (organizationServerID != null)
                            organization_ids.add(organizationServerID);
                    }
                    filterOrganizationDetails(organization_ids, firebaseCallBack);
                }else
                    firebaseCallBack.onFirebaseFailure(null);
            }

            @Override
            public void onFirebaseFailure(Object object) {
                firebaseCallBack.onFirebaseFailure(null);
            }
        });

    }

    private void filterOrganizationDetails(List<String> organization_ids,
                                           cFirebaseCallBack firebaseCallBack) {
        if (!organization_ids.isEmpty()) {
            CollectionReference coOrganizationRef;
            coOrganizationRef = database.collection(cRealtimeHelper.KEY_ORGANIZATIONS);
            Query query = coOrganizationRef
                    .whereIn("organizationServerID", organization_ids)
                    .orderBy("createdDate");

            listenerRegistration = readQueryDocumentsByListener(query, new cFirebaseCallBack() {
                @Override
                public void onFirebaseSuccess(Object object) {
                    if (object != null) {
                        QuerySnapshot querySnapshot = (QuerySnapshot) object;
                        Map<String, cOrganizationModel> organizationModelMap = new HashMap<>();
                        for (DocumentSnapshot ds : querySnapshot.getDocuments()) {
                            cOrganizationModel organizationModel;
                            organizationModel = ds.toObject(cOrganizationModel.class);

                            if (organizationModel != null) {
                                organizationModel.setOrganizationServerID(ds.getId());
                                organizationModelMap.put(organizationModel.getOrganizationServerID(),
                                        organizationModel);
                            }
                        }
                        filterWorkspaceDetails(organizationModelMap, firebaseCallBack);
                    } else {
                        firebaseCallBack.onFirebaseFailure(null);
                    }
                }

                @Override
                public void onFirebaseFailure(Object object) {
                    firebaseCallBack.onFirebaseFailure(null);
                }
            });
        }else {
            Map<cOrganizationModel, List<cWorkspaceModel>> organizationWorkspaceMap;
            organizationWorkspaceMap = new HashMap<>();
            firebaseCallBack.onFirebaseSuccess(organizationWorkspaceMap);
        }
    }

    private void filterWorkspaceDetails(@NonNull Map<String, cOrganizationModel> organizationModelMap,
                                        cFirebaseCallBack firebaseCallBack) {
        if (!organizationModelMap.isEmpty()) {
            CollectionReference coWorkspacesRef;
            coWorkspacesRef = database.collection(cRealtimeHelper.KEY_WORKSPACES);
            List<String> organization_ids = new ArrayList<>(organizationModelMap.keySet());
            Query orgWorkspacesQuery = coWorkspacesRef.
                    whereIn("organizationServerID", organization_ids);

            listenerRegistration = readQueryDocumentsByListener(orgWorkspacesQuery,
                    new cFirebaseCallBack() {
                        @Override
                        public void onFirebaseSuccess(Object object) {
                            if (object != null) {
                                QuerySnapshot querySnapshot = (QuerySnapshot) object;
                                Map<cOrganizationModel, List<cWorkspaceModel>> organizationWorkspaceMap;
                                organizationWorkspaceMap = new HashMap<>();

                                for (cOrganizationModel organizationModel : organizationModelMap.values()) {
                                    String organizationID = organizationModel.getOrganizationServerID();
                                    List<cWorkspaceModel> workspaceModels = new ArrayList<>();
                                    for (DocumentSnapshot ds : querySnapshot.getDocuments()) {
                                        cWorkspaceModel workspaceModel = ds.toObject(cWorkspaceModel.class);
                                        assert workspaceModel != null;
                                        if (organizationID.equals(workspaceModel.getOrganizationServerID())) {
                                            workspaceModels.add(workspaceModel);
                                        }
                                    }
                                    organizationWorkspaceMap.put(organizationModel, workspaceModels);
                                }

                                firebaseCallBack.onFirebaseSuccess(organizationWorkspaceMap);
                            } else {
                                firebaseCallBack.onFirebaseFailure(null);
                            }
                        }

                        @Override
                        public void onFirebaseFailure(Object object) {
                            firebaseCallBack.onFirebaseFailure(null);
                        }
                    });
        }
    }

    @Override
    public void readAllStakeholders(String organizationServerID, String userServerID,
                                    int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                    List<Integer> statusBITS,
                                    cFirebaseChildCallBack firebaseChildCallBack) {

        CollectionReference coOrganizationRef;
        coOrganizationRef = database.collection(cRealtimeHelper.KEY_STAKEHOLDERS);
        Query query = coOrganizationRef.orderBy("createdDate");

        listenerRegistration = readQueryDocumentsByChildEventListener(
                query, new cFirebaseChildCallBack() {
                    @Override
                    public void onChildAdded(Object object) {
                        if (object != null) {
                            DocumentSnapshot document = (DocumentSnapshot) object;
                            if (document.exists()) {
                                cOrganizationModel organizationModel;
                                organizationModel = document.toObject(cOrganizationModel.class);
                                if (organizationModel != null) {
                                    organizationModel.setOrganizationServerID(document.getId());
                                    firebaseChildCallBack.onChildAdded(organizationModel);
                                } else {
                                    firebaseChildCallBack.onChildAdded(null);
                                }
                            } else {
                                firebaseChildCallBack.onChildAdded(null);
                            }
                        } else {
                            firebaseChildCallBack.onChildAdded(null);
                        }
                    }

                    @Override
                    public void onChildChanged(Object object) {
                        if (object != null) {
                            DocumentSnapshot document = (DocumentSnapshot) object;
                            if (document.exists()) {
                                cOrganizationModel organizationModel;
                                organizationModel = document.toObject(cOrganizationModel.class);
                                if (organizationModel != null) {
                                    organizationModel.setOrganizationServerID(document.getId());
                                    firebaseChildCallBack.onChildChanged(organizationModel);
                                } else {
                                    firebaseChildCallBack.onChildChanged(null);
                                }
                            } else {
                                firebaseChildCallBack.onChildChanged(null);
                            }
                        } else {
                            firebaseChildCallBack.onChildChanged(null);
                        }
                    }

                    @Override
                    public void onChildRemoved(Object object) {
                        if (object != null) {
                            DocumentSnapshot document = (DocumentSnapshot) object;
                            if (document.exists()) {
                                cOrganizationModel organizationModel;
                                organizationModel = document.toObject(cOrganizationModel.class);
                                if (organizationModel != null) {
                                    organizationModel.setOrganizationServerID(document.getId());
                                    firebaseChildCallBack.onChildRemoved(organizationModel);
                                } else {
                                    firebaseChildCallBack.onChildRemoved(null);
                                }
                            } else {
                                firebaseChildCallBack.onChildRemoved(null);
                            }
                        } else {
                            firebaseChildCallBack.onChildRemoved(null);
                        }
                    }

                    @Override
                    public void onCancelled(Object object) {
                        firebaseChildCallBack.onCancelled(object);
                    }
                });
    }

    @Override
    public void removeListener() {
        if (listenerRegistration != null)
            listenerRegistration.remove();
    }


    /**
     * read organizations
     *
     * @param stakeholderServerID organization identifications that are aligned to the user
     *                            account of the loggedin user - FIXME
     * @param userServerID        user identification
     * @param primaryTeamBIT      primary team bit
     * @param secondaryTeamBITS   secondary team bits
     * @param statusBITS          status bits
     * @param callback            call back
     */
    @Override
    public void readStakeholders(String stakeholderServerID, String userServerID,
                                 int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                 List<Integer> statusBITS, iReadStakeholdersCallback callback) {


        CollectionReference coStakeholderRef = database.collection(cRealtimeHelper.KEY_STAKEHOLDERS);

        Task<List<QuerySnapshot>> org_perm = null;//FIXME cDatabaseUtils.applyReadPermissions(coStakeholderRef, stakeholderServerID, userServerID, primaryTeamBIT, secondaryTeamBITS);

        org_perm
                .addOnCompleteListener(task -> {
                    Set<cOrganizationModel> stakeholderSet = new HashSet<>();
                    for (QuerySnapshot result : Objects.requireNonNull(task.getResult())) {
                        for (QueryDocumentSnapshot ds : result) {
                            cOrganizationModel stakeholderModel = ds.toObject(
                                    cOrganizationModel.class);

                            if (statusBITS.contains(stakeholderModel.getStatusBIT())) {
                                stakeholderModel.setOrganizationServerID(ds.getId());
                                stakeholderSet.add(stakeholderModel);
                            }
                        }
                    }

                    ArrayList<cOrganizationModel> organizationModels;
                    organizationModels = new ArrayList<>(stakeholderSet);
                    callback.onReadStakeholdersSucceeded(organizationModels);
                })
                .addOnFailureListener(e -> {
                    callback.onReadStakeholdersFailed("Failed to read Organization.");
                    Log.w(TAG, "Failed to read value.", e);
                });
    }

    /**
     * read organization members
     *
     * @param stakeholderServerID organization identification
     * @param userServerID        user identification
     * @param primaryTeamBIT      primary team bit
     * @param secondaryTeamBITS   secondary team bits
     * @param statusBITS          status bits
     * @param callback            call back
     */
    @Override
    public void readStakeholderMembers(String stakeholderServerID, String userServerID,
                                       int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                       List<Integer> statusBITS,
                                       iReadStakeholderMembersCallback callback) {

        CollectionReference coUserAccountsRef;
        coUserAccountsRef = database.collection(cRealtimeHelper.KEY_ORGANIZATION_MEMBERS);

        Query userAccountQuery = coUserAccountsRef
                .whereEqualTo("organizationOwnerID", stakeholderServerID)
                .whereIn("statusBIT", statusBITS);

        userAccountQuery.get()
                .addOnCompleteListener(task -> {
                    Set<String> user_ids_set = new HashSet<>();
                    for (DocumentSnapshot useraccount : Objects.requireNonNull(task.getResult())) {
                        cUserAccountModel accountModel;
                        accountModel = useraccount.toObject(cUserAccountModel.class);

                        if (accountModel != null) {
                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                            perm.setUserOwnerID(accountModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(accountModel.getWorkspaceOwnerBIT());
                            perm.setUnixpermBITS(accountModel.getUnixpermBITS());

//FIXME                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                                    secondaryTeamBITS)) {
//                                String userID = accountModel.getUserServerID();
//                                user_ids_set.add(userID);
//                            }
                        } else {
                            callback.onReadStakeholderMembersFailed(
                                    " due to user account entity!");
                        }
                    }

                    /* read organization members */
                    List<String> user_ids = new ArrayList<>(user_ids_set);
                    if (!user_ids.isEmpty()) {
                        filterUserProfiles(user_ids, callback);
                    } else {
                        callback.onReadStakeholderMembersFailed(
                                "No organization members found!");
                    }
                })
                .addOnFailureListener(e -> callback.onReadStakeholderMembersFailed(
                        "Failed to read organization members"));
    }

    /**
     * filter user profiles
     *
     * @param user_ids list of user identifications
     * @param callback call back
     */
    private void filterUserProfiles(List<String> user_ids,
                                    iReadStakeholderMembersCallback callback) {

        CollectionReference coUserProfilesRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);

        Query userProfileQuery = coUserProfilesRef
                .whereIn(FieldPath.documentId(), user_ids);

        userProfileQuery.get()
                .addOnCompleteListener(task -> {
                    List<CUserProfileModel> userProfileModels = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                        CUserProfileModel user = doc.toObject(CUserProfileModel.class);
                        userProfileModels.add(user);
                    }

                    // call back on organization members
                    callback.onReadStakeholderMembersSucceeded(userProfileModels);
                })
                .addOnFailureListener(e -> callback.onReadStakeholderMembersFailed(
                        "Failed to read organization members"));
    }

    /* ##################################### UPDATE ACTIONS ##################################### */


    /* ##################################### DELETE ACTIONS ##################################### */

}