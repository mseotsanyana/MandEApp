package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;

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
import com.me.mseotsanyana.mande.BLL.model.session.cStakeholderModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPlanModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.model.utils.cCommonPropertiesModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iStakeholderRepository;
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
public class                                                                                                                                                          cStakeholderFirestoreRepositoryImpl extends cFirebaseRepository
        implements iStakeholderRepository {
    private static final String TAG = cStakeholderFirestoreRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private final FirebaseFirestore database;
    private final Context context;

    private ListenerRegistration listenerRegistration;

    public cStakeholderFirestoreRepositoryImpl(Context context) {
        this.database = FirebaseFirestore.getInstance();
        this.context = context;
    }

    /* ##################################### CREATE ACTIONS ##################################### */

    /**
     * create organization
     *
     * @param stakeholderModel stakeholder model
     * @param callback          call back
     */
    @Override
    public void createStakeholder(cStakeholderModel stakeholderModel,
                                  iCreateStakeholderCallback callback) {
        // get current logged in user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userServerID = currentUser.getUid();
            /* create a batch object for creation of an organization */
            WriteBatch batch = database.batch();

            // create new organization
            CollectionReference collectionStakeholderRef = database.collection(
                    cRealtimeHelper.KEY_STAKEHOLDERS);
            DocumentReference stakeholderDocRef = collectionStakeholderRef.document();
            String organizationServerID = stakeholderDocRef.getId();

            // update organization default dates
            Date currentDate = new Date();
            stakeholderModel.setCreatedDate(currentDate);
            stakeholderModel.setModifiedDate(currentDate);

            // update organization model with default values
            cCommonPropertiesModel commonPropertiesModel = cDatabaseUtils.getCommonModel(context);
            stakeholderModel.setUserOwnerID(userServerID);
            stakeholderModel.setStakeholderOwnerID(organizationServerID);
            stakeholderModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
            stakeholderModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
            stakeholderModel.setStatusBIT(commonPropertiesModel.getCstatusBIT());

            switch (stakeholderModel.getTypeID()) {
                case 0:
                    stakeholderModel.setType("MY ORGANIZATION");
                    break;
                case 1:
                    stakeholderModel.setType("PARTNER");
                    break;

                case 2:
                    stakeholderModel.setType("FUNDER");
                    break;
                case 3:
                    stakeholderModel.setType("BENEFICIARY");
                    break;
                case 4:
                    stakeholderModel.setType("IMPLEMENTING AGENCY");
                    break;
                default:
                    Log.d(TAG, "Error in creating an organization");
            }

            // 1. adding newly created parent organization to the batch
            batch.set(stakeholderDocRef, stakeholderModel);

            // 2. adding the team to the organization just created to the batch

            /* create a default administrator team during the creation of an organization */
            cTeamModel teamModel = cDatabaseUtils.getAdminTeamModel(context, organizationServerID,
                    commonPropertiesModel);
            teamModel.setUserOwnerID(userServerID);
            teamModel.setOrganizationOwnerID(organizationServerID);
            String compositeServerID = teamModel.getCompositeServerID();
            CollectionReference coTeamsRef = database.collection(cRealtimeHelper.KEY_TEAMS);
            DocumentReference teamDocRef = coTeamsRef.document(compositeServerID);
            batch.set(teamDocRef, teamModel);

            // 3. adding admin team member to the team just created to the batch

            // add the current user to the team of the organization just created
            CollectionReference coTeamMembersRef;
            coTeamMembersRef = database.collection(cRealtimeHelper.KEY_TEAM_MEMBERS);
            Map<String, Object> team_members = new HashMap<>();
            team_members.put("userAccountServerID", organizationServerID + "_" + userServerID);
            team_members.put("teamMemberServerID", compositeServerID);
            DocumentReference teamMemberDocRef = coTeamMembersRef.document(
                    organizationServerID + "_" + userServerID + "_" + compositeServerID);
            batch.set(teamMemberDocRef, team_members);

            // 4. adding new account of admin member of an organization to the batch.

            /* read a default freemium plan from json associated with the administrator of
               the organization just created */
            cPlanModel freemiumPlanModel = cDatabaseUtils.getDefaultPlanModel(context);
            /* create a user account of the administrator with the organization just created */
            String teamServerID = teamModel.getTeamServerID();
            String planSeverID = freemiumPlanModel.getPlanServerID();
            cUserAccountModel userAccountModel = createUserAccount(organizationServerID,
                    userServerID, teamServerID, planSeverID, commonPropertiesModel);
            CollectionReference coUserAccountsRef;
            coUserAccountsRef = database.collection(cRealtimeHelper.KEY_USERACCOUNTS);
            DocumentReference userAccountDocRef;
            userAccountDocRef = coUserAccountsRef.document(organizationServerID + "_" +
                    userServerID);
            batch.set(userAccountDocRef, userAccountModel);

            // 5. adding role of the organization to the batch.

            /* create a default administrator role during the creation of an organization */
            cRoleModel roleModel = cDatabaseUtils.getAdminRoleModel(context, commonPropertiesModel);
            roleModel.setUserOwnerID(userServerID);
            roleModel.setOrganizationOwnerID(organizationServerID);

            CollectionReference coRoleRef = database.collection(cRealtimeHelper.KEY_ROLES);
            DocumentReference roleDocRef = coRoleRef.document();
            String roleServerID = roleDocRef.getId();
            batch.set(roleDocRef, roleModel);

            // 6. adding team roles to the batch.

            // add admin role to the user of the organization
            CollectionReference coTeamRolesRef = database.collection(cRealtimeHelper.KEY_TEAM_ROLES);
            Map<String, Object> team_roles = new HashMap<>();
            team_roles.put("teamServerID", compositeServerID);
            team_roles.put("roleServerID", roleServerID);
            DocumentReference teamRoleDocRef;
            teamRoleDocRef = coTeamRolesRef.document(compositeServerID + "_" +
                    roleServerID);
            batch.set(teamRoleDocRef, team_roles);

            // 7. adding role permissions to the batch.

            /* read a default permissions associated with the administrator role during
               the creation of an organization */
            cPermissionModel permissionModel = cDatabaseUtils.createAdminPermissions(context);
            permissionModel.setRoleServerID(roleServerID);
            permissionModel.setName(roleModel.getName());

            // add permissions to the role
            permissionModel.setDescription("Administrator permissions for both entity and " +
                    "property level access control");
            CollectionReference coRolePermsRef = database.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
            DocumentReference rolePermDocRef = coRolePermsRef.document(roleServerID);
            batch.set(rolePermDocRef, permissionModel);

            // commit the batch file
            batchWrite(batch, new cFirebaseCallBack() {
                @Override
                public void onFirebaseSuccess(Object object) {
                    // make sure the just created organization is activated
                    Query query = coUserAccountsRef.whereEqualTo("userServerID", userServerID);
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
                    });
                    callback.onCreateStakeholderSucceeded(
                            "Successfully created an organization.");
                }

                @Override
                public void onFirebaseFailure(Object object) {
                    callback.onCreateStakeholderFailed(
                            "Failed to create an organization.");
                }
            });
        } else {
            callback.onCreateStakeholderFailed("Error, Failed to create an organization.");
        }
    }

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
        userAccountModel.setTeamServerID(teamServerID);
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
        userAccountModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
        userAccountModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
        userAccountModel.setStatusBIT(commonPropertiesModel.getCstatusBIT());

        return userAccountModel;
    }

    /* ###################################### READ ACTIONS ###################################### */

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
                                cStakeholderModel organizationModel;
                                organizationModel = document.toObject(cStakeholderModel.class);
                                if (organizationModel != null) {
                                    organizationModel.setStakeholderServerID(document.getId());
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
                                cStakeholderModel organizationModel;
                                organizationModel = document.toObject(cStakeholderModel.class);
                                if (organizationModel != null) {
                                    organizationModel.setStakeholderServerID(document.getId());
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
                                cStakeholderModel organizationModel;
                                organizationModel = document.toObject(cStakeholderModel.class);
                                if (organizationModel != null) {
                                    organizationModel.setStakeholderServerID(document.getId());
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
     *                             account of the loggedin user - FIXME
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team bit
     * @param secondaryTeamBITS    secondary team bits
     * @param statusBITS           status bits
     * @param callback             call back
     */
    @Override
    public void readStakeholders(String stakeholderServerID, String userServerID,
                                 int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                 List<Integer> statusBITS, iReadStakeholdersCallback callback) {


        CollectionReference coStakeholderRef = database.collection(cRealtimeHelper.KEY_STAKEHOLDERS);

        Task<List<QuerySnapshot>> org_perm = cDatabaseUtils.applyReadPermissions(coStakeholderRef,
                stakeholderServerID, userServerID, primaryTeamBIT, secondaryTeamBITS);

        org_perm
                .addOnCompleteListener(task -> {
                    Set<cStakeholderModel> stakeholderSet = new HashSet<>();
                    for (QuerySnapshot result : Objects.requireNonNull(task.getResult())) {
                        for (QueryDocumentSnapshot ds : result) {
                            cStakeholderModel stakeholderModel = ds.toObject(
                                    cStakeholderModel.class);

                            if (statusBITS.contains(stakeholderModel.getStatusBIT())) {
                                stakeholderModel.setStakeholderServerID(ds.getId());
                                stakeholderSet.add(stakeholderModel);
                            }
                        }
                    }

                    ArrayList<cStakeholderModel> organizationModels;
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
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team bit
     * @param secondaryTeamBITS    secondary team bits
     * @param statusBITS           status bits
     * @param callback             call back
     */
    @Override
    public void readStakeholderMembers(String stakeholderServerID, String userServerID,
                                       int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                       List<Integer> statusBITS,
                                       iReadStakeholderMembersCallback callback) {

        CollectionReference coUserAccountsRef;
        coUserAccountsRef = database.collection(cRealtimeHelper.KEY_USERACCOUNTS);

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
                            perm.setTeamOwnerBIT(accountModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(accountModel.getUnixpermBITS());

                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
                                    secondaryTeamBITS)) {
                                String userID = accountModel.getUserServerID();
                                user_ids_set.add(userID);
                            }
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
                    List<cUserProfileModel> userProfileModels = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                        cUserProfileModel user = doc.toObject(cUserProfileModel.class);
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