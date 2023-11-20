package com.me.mseotsanyana.mande.infrastructure.repository.firestore.session;

import static com.me.mseotsanyana.mande.application.structures.CFirestoreConstant.FAILURE;
import static com.me.mseotsanyana.mande.application.structures.CFirestoreConstant.SUCCESS;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

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
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cPlanModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CPrivilegeModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserAccountModel;
import com.me.mseotsanyana.mande.domain.entities.models.utils.CCommonAttributeModel;
import com.me.mseotsanyana.mande.application.repository.session.IOrganizationRepository;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreChildCallBack;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreRepository;
import com.me.mseotsanyana.mande.application.structures.CFirestoreConstant;
import com.me.mseotsanyana.mande.application.utils.CFirestoreUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class COrganizationFirestoreRepositoryImpl extends CFirestoreRepository
        implements IOrganizationRepository {
    private static final String TAG = COrganizationFirestoreRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private final Context context;
    private final FirebaseFirestore database;
    private final CollectionReference organizationCollectionReference, workspacesCollectionReference,
            workspaceMembersReference, organizationMembersReference, privilegeCollectionReference;

    private ListenerRegistration listenerRegistration;

    public COrganizationFirestoreRepositoryImpl(Context context) {
        this.context = context;
        this.database = FirebaseFirestore.getInstance();
        this.organizationCollectionReference = database.collection(
                CFirestoreConstant.KEY_ORGANIZATIONS);
        this.workspacesCollectionReference = database.collection(
                CFirestoreConstant.KEY_WORKSPACES);
        this.workspaceMembersReference = database.collection(
                CFirestoreConstant.KEY_WORKSPACE_MEMBERS);
        this.organizationMembersReference = database.collection(
                CFirestoreConstant.KEY_ORGANIZATION_MEMBERS);
        this.privilegeCollectionReference = database.collection(
                CFirestoreConstant.KEY_WORKSPACE_PRIVILEGES);
    }

    /*************************************** CREATE ACTIONS ****************************************
     *
     * create organization
     *
     * @param organizationModel organization model
     * @param callback          call back
     */
    @Override
    public void createOrganization(COrganizationModel organizationModel,
                                   CFirestoreCallBack callback) {
        // get current logged in user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userServerID = currentUser.getUid();
            /* create a batch object for creation of an organization */
            WriteBatch batch = database.batch();

            // create new organization
            DocumentReference organizationDocRef = organizationCollectionReference.document();
            String organizationServerID = organizationDocRef.getId();

            Map<String, Object> organization = new HashMap<>();

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
            CCommonAttributeModel properties = CFirestoreUtility.getCommonModel(context);

            organization.put("userOwnerID", userServerID);
            organization.put("organizationOwnerID", organizationServerID);
            organization.put("workspaceOwnerBIT", properties.getWorkspaceOwnerBIT());
            organization.put("unixpermBITS", properties.getUnixpermBITS());
            organization.put("statusBIT", properties.getStatusBIT());
            organization.put("workspaceBITS", properties.getWorkspaceOwnerBIT());

            switch (organizationModel.getTypeID()) {
                case 0 -> organizationModel.setType("PARTNER");
                case 1 -> organizationModel.setType("DONOR");
                case 2 -> organizationModel.setType("BENEFICIARY");
                case 3 -> organizationModel.setType("IMPLEMENTING AGENCY");
                default -> Log.d(TAG, "Error in creating an organization");
            }

            organization.put("type", organizationModel.getType());

            // 1. adding newly created parent organization to the batch
            batch.set(organizationDocRef, organization);

            // 2. adding the workspace to the organization just created to the batch

            /* create a default administrator workspace during the creation of an organization */
            CWorkspaceModel workspaceModel = CFirestoreUtility.getAdminWorkspaceModel(context,
                    organizationServerID, properties);

            String compositeServerID = workspaceModel.getCompositeServerID();
            String workspaceServerID = workspaceModel.getWorkspaceServerID();
            String userAccountServerID = organizationServerID + "_" + userServerID;

            workspaceModel.setUserOwnerID(userServerID);
            workspaceModel.setOrganizationOwnerID(organizationServerID);
            workspaceModel.getWorkspaceMembers().add(userAccountServerID);

            DocumentReference workspaceDocRef;
            workspaceDocRef = workspacesCollectionReference.document(compositeServerID);

            batch.set(workspaceDocRef, workspaceModel);

            // 3. adding admin member to the admin workspace just created to the batch

            // add the current user to the team of the organization just created
            Map<String, Object> workspace_members = new HashMap<>();
            workspace_members.put("userAccountServerID", userAccountServerID);
            workspace_members.put("workspaceMemberServerID", compositeServerID);
            workspace_members.put("organizationServerID", organizationServerID);
            workspace_members.put("workspaceServerID", workspaceServerID);
            workspace_members.put("userServerID", userServerID);
            workspace_members.put("workspaceType", true);

            DocumentReference workspaceMemberDocRef = workspaceMembersReference.document(
                    organizationServerID + "_" + userServerID + "_" + compositeServerID);
            batch.set(workspaceMemberDocRef, workspace_members);

            // 4. adding new account of admin member of an organization to the batch.

            /* read a default freemium plan from json associated with the administrator of
               the organization just created */
            cPlanModel freemiumPlanModel = CFirestoreUtility.getDefaultPlanModel(context);
            /* create a user account of the administrator with the organization just created */
            String planSeverID = freemiumPlanModel.getPlanServerID();
            CUserAccountModel userAccountModel = new CUserAccountModel(organizationServerID,
                    userServerID, workspaceServerID, planSeverID, properties);

            DocumentReference userAccountDocRef;
            userAccountDocRef = organizationMembersReference.document(
                    organizationServerID + "_" + userServerID);
            batch.set(userAccountDocRef, userAccountModel);

            // 5. adding admin privileges of the admin workspace to the batch.

            /* create a default workspace privileges during the creation of the organization */
            CPrivilegeModel privilegeModel = CFirestoreUtility.createDefaultPrivilegeModel(context,
                    properties);

            privilegeModel.setPrivilegeServerID(compositeServerID);
            privilegeModel.setUserOwnerID(userServerID);
            privilegeModel.setOrganizationOwnerID(organizationServerID);

            DocumentReference privilegeDocRef;
            privilegeDocRef = privilegeCollectionReference.document(compositeServerID);
            batch.set(privilegeDocRef, privilegeModel);

            // commit the batch file
            batchWrite(batch, new CFirestoreCallBack() {
                @Override
                public void onFirebaseSuccess(Object object) {
                    /* once organization successfully created, make sure that it is activated
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
                    });*/
                    callback.onFirebaseSuccess(
                            "Successfully created an organization.");
                }

                @Override
                public void onFirebaseFailure(Object object) {
                    callback.onFirebaseFailure(
                            "Failed to create an organization");
                }
            });
        } else {
            callback.onFirebaseFailure("Error, Failed to create an organization.");
        }
    }

    /***************************************** READ ACTIONS ****************************************
     *
     * @param organizationServerID   organization identification
     * @param userServerID           user identification
     * @param primaryWorkspaceBIT    primary workspace bit
     * @param secondaryWorkspaceBITS secondary workspace bit
     * @param statusBITS             status bits
     * @param firebaseChildCallBack  firebase callback
     */
    @Override
    public void readOrganizations(String organizationServerID, String userServerID,
                                  int primaryWorkspaceBIT, List<Integer> secondaryWorkspaceBITS,
                                  List<Integer> statusBITS,
                                  CFirestoreChildCallBack firebaseChildCallBack) {

        Query query = organizationCollectionReference.orderBy("createdDate");

        listenerRegistration = readQueryDocumentsByChildEventListener(
                query, new CFirestoreChildCallBack() {
                    @Override
                    public void onChildAdded(Object object) {
                        if (object != null) {
                            DocumentSnapshot document = (DocumentSnapshot) object;
                            if (document.exists()) {
                                COrganizationModel organizationModel;
                                organizationModel = document.toObject(COrganizationModel.class);
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
                                COrganizationModel organizationModel;
                                organizationModel = document.toObject(COrganizationModel.class);
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
                                COrganizationModel organizationModel;
                                organizationModel = document.toObject(COrganizationModel.class);
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
    public void readOrganizationWorkspaces(CFirestoreCallBack firebaseCallBack) {
        CollectionReference coUserOrganizationsRef;
        coUserOrganizationsRef = database.collection(CFirestoreConstant.KEY_ORGANIZATION_MEMBERS);

        String userServerID;
        userServerID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        Query query = coUserOrganizationsRef
                .whereEqualTo("userServerID", userServerID).orderBy("createdDate");

        listenerRegistration = readQueryDocumentsByListener(query, new CFirestoreCallBack() {
            @Override
            public void onFirebaseSuccess(Object object) {
                Log.d(TAG, "I AM HERE AGAIN");
                if (object != null) {
                    QuerySnapshot querySnapshot = (QuerySnapshot) object;
                    List<String> organization_ids = new ArrayList<>();
                    for (DocumentSnapshot ds : querySnapshot.getDocuments()) {
                        String organizationServerID = ds.getString("organizationServerID");
                        if (organizationServerID != null)
                            organization_ids.add(organizationServerID);
                    }
                    filterOrganizationDetails(organization_ids, firebaseCallBack);
                } else
                    firebaseCallBack.onFirebaseFailure(null);
            }

            @Override
            public void onFirebaseFailure(Object object) {
                firebaseCallBack.onFirebaseFailure(null);
            }
        });
    }

    private void filterOrganizationDetails(@NonNull List<String> organization_ids,
                                           CFirestoreCallBack firebaseCallBack) {
        if (!organization_ids.isEmpty()) {
            CollectionReference coOrganizationRef;
            coOrganizationRef = database.collection(CFirestoreConstant.KEY_ORGANIZATIONS);
            Query query = coOrganizationRef
                    .whereIn("organizationServerID", organization_ids);

            readQueryDocuments(query, new CFirestoreCallBack() {
                @Override
                public void onFirebaseSuccess(Object object) {
                    if (object != null) {
                        QuerySnapshot querySnapshot = (QuerySnapshot) object;
                        Map<String, COrganizationModel> organizationModelMap = new HashMap<>();
                        for (DocumentSnapshot ds : querySnapshot.getDocuments()) {
                            COrganizationModel organizationModel;
                            organizationModel = ds.toObject(COrganizationModel.class);

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
        } else {
            Map<COrganizationModel, List<CWorkspaceModel>> organizationWorkspaceMap;
            organizationWorkspaceMap = new HashMap<>();
            firebaseCallBack.onFirebaseSuccess(organizationWorkspaceMap);
        }
    }

    private void filterWorkspaceDetails(@NonNull Map<String, COrganizationModel> organizationModelMap,
                                        CFirestoreCallBack firebaseCallBack) {
        if (!organizationModelMap.isEmpty()) {
            CollectionReference coWorkspacesRef;
            coWorkspacesRef = database.collection(CFirestoreConstant.KEY_WORKSPACES);
            List<String> organization_ids = new ArrayList<>(organizationModelMap.keySet());
            Query orgWorkspacesQuery = coWorkspacesRef.
                    whereIn("organizationServerID", organization_ids);

            readQueryDocuments(orgWorkspacesQuery,
                    new CFirestoreCallBack() {
                        @Override
                        public void onFirebaseSuccess(Object object) {
                            if (object != null) {
                                QuerySnapshot querySnapshot = (QuerySnapshot) object;
                                Map<COrganizationModel, List<CWorkspaceModel>> organizationWorkspaceMap;
                                organizationWorkspaceMap = new HashMap<>();

                                for (COrganizationModel organizationModel : organizationModelMap.values()) {
                                    String organizationID = organizationModel.getOrganizationServerID();
                                    List<CWorkspaceModel> workspaceModels = new ArrayList<>();
                                    for (DocumentSnapshot ds : querySnapshot.getDocuments()) {
                                        CWorkspaceModel workspaceModel = ds.toObject(CWorkspaceModel.class);
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

    /**
     * read organization members
     *
     * @param organizationServerID  organization identification
     * @param userServerID          user identification
     * @param primaryTeamBIT        primary team bit
     * @param secondaryTeamBITS     secondary team bits
     * @param statusBITS            status bits
     * @param firebaseChildCallBack call back
     */
    @Override
    public void readOrganizationAccounts(String organizationServerID, String userServerID,
                                         int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                         List<Integer> statusBITS,
                                         CFirestoreChildCallBack firebaseChildCallBack) {

        CollectionReference coUserAccountsRef;
        coUserAccountsRef = database.collection(CFirestoreConstant.KEY_ORGANIZATION_MEMBERS);

        Query userAccountQuery = coUserAccountsRef
                .whereEqualTo("userServerID", userServerID);

        listenerRegistration = readQueryDocumentsByChildEventListener(userAccountQuery,
                new CFirestoreChildCallBack() {
                    @Override
                    public void onChildAdded(Object object) {
                        if (object != null) {
                            Map<String, Object> userAccount = new HashMap<>();
                            DocumentSnapshot document = (DocumentSnapshot) object;
                            if (document.exists()) {
                                CUserAccountModel accountModel;
                                accountModel = document.toObject(CUserAccountModel.class);
                                if (accountModel != null) {
                                    userAccount.put("userAccountServerID",
                                            accountModel.getUserAccountServerID());
                                    userAccount.put("userServerID",
                                            accountModel.getUserServerID());
                                    userAccount.put("organizationServerID",
                                            accountModel.getOrganizationServerID());
                                    userAccount.put("planServerID",
                                            accountModel.getPlanServerID());
                                    userAccount.put("statusBIT",
                                            accountModel.getStatusBIT());
                                    userAccount.put("createdDate",
                                            accountModel.getCreatedDate());
                                    userAccount.put("modifiedDate",
                                            accountModel.getModifiedDate());
                                    filterOrganizations(userAccount, firebaseChildCallBack);
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
                            Map<String, Object> userAccount = new HashMap<>();
                            DocumentSnapshot document = (DocumentSnapshot) object;
                            if (document.exists()) {
                                CUserAccountModel accountModel;
                                accountModel = document.toObject(CUserAccountModel.class);
                                if (accountModel != null) {
                                    userAccount.put("userAccountServerID",
                                            accountModel.getUserAccountServerID());
                                    userAccount.put("userServerID",
                                            accountModel.getUserServerID());
                                    userAccount.put("organizationServerID",
                                            accountModel.getOrganizationServerID());
                                    userAccount.put("planServerID",
                                            accountModel.getPlanServerID());
                                    userAccount.put("statusBIT",
                                            accountModel.getStatusBIT());
                                    userAccount.put("createdDate",
                                            accountModel.getCreatedDate());
                                    userAccount.put("modifiedDate",
                                            accountModel.getModifiedDate());
                                    firebaseChildCallBack.onChildAdded(userAccount);
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
                    public void onChildRemoved(Object object) {
                        if (object != null) {
                            Map<String, Object> userAccount = new HashMap<>();
                            DocumentSnapshot document = (DocumentSnapshot) object;
                            if (document.exists()) {
                                CUserAccountModel accountModel;
                                accountModel = document.toObject(CUserAccountModel.class);
                                if (accountModel != null) {
                                    userAccount.put("userAccountServerID",
                                            accountModel.getUserAccountServerID());
                                    userAccount.put("userServerID",
                                            accountModel.getUserServerID());
                                    userAccount.put("organizationServerID",
                                            accountModel.getOrganizationServerID());
                                    userAccount.put("planServerID",
                                            accountModel.getPlanServerID());
                                    userAccount.put("statusBIT",
                                            accountModel.getStatusBIT());
                                    userAccount.put("createdDate",
                                            accountModel.getCreatedDate());
                                    userAccount.put("modifiedDate",
                                            accountModel.getModifiedDate());
                                    firebaseChildCallBack.onChildAdded(userAccount);
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
                    public void onCancelled(Object object) {
                        firebaseChildCallBack.onCancelled(object);
                    }
                });
    }

    /**
     * filter user profiles
     *
     * @param userAccount          list of user identifications
     * @param firebaseChildCallBack call back
     */
    private void filterOrganizations(@NonNull Map<String, Object> userAccount,
                                     CFirestoreChildCallBack firebaseChildCallBack) {

        CollectionReference coOrganizationRef;
        coOrganizationRef = database.collection(CFirestoreConstant.KEY_ORGANIZATIONS);

        Query organizationQuery = coOrganizationRef
                .whereEqualTo(FieldPath.documentId(), userAccount.get("organizationServerID"));

        organizationQuery.get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                        Map<String, Object> account = doc.getData();
                        userAccount.put("name", account.get("name"));
                        userAccount.put("email", account.get("email"));
                        userAccount.put("typeID", account.get("typeID"));

                    }
                    firebaseChildCallBack.onChildAdded(userAccount);
                })
                .addOnFailureListener(e ->
                        firebaseChildCallBack.onChildAdded(
                                "Failed to read organization accounts"));
    }

    @Override
    public void readOrganizationAccounts(String organizationServerID, String userServerID,
                                         int primaryTeamBIT, List<Integer> secondaryWorkspaceBITS,
                                         List<Integer> statusBITS,
                                         CFirestoreCallBack firebaseCallBack) {

    }

    @Override
    public void readOrganizationAgreements(String organizationServerID, String userServerID,
                                           int primaryTeamBIT, List<Integer> secondaryWorkspaceBITS,
                                           List<Integer> statusBITS,
                                           CFirestoreCallBack firebaseCallBack) {

    }

    /*************************************** UPDATE ACTIONS ***************************************/

    @Override
    public void updateOrganization(COrganizationModel organizationModel,
                                   CFirestoreCallBack firestoreCallBack) {
        Map<String, Object> map = new HashMap<>();
        map.put("typeID", organizationModel.getTypeID());
        map.put("type", organizationModel.getType());
        map.put("name", organizationModel.getName());
        map.put("email",organizationModel.getEmail());
        map.put("website",organizationModel.getWebsite());
        map.put("modifiedDate", new Date());

        if (!CFirestoreUtility.isEmptyOrNull(organizationModel.getOrganizationServerID())) {
            DocumentReference organizationDocumentReference;
            organizationDocumentReference = organizationCollectionReference.document(
                    organizationModel.getOrganizationServerID());
            fireStoreUpdate(organizationDocumentReference, map, new CFirestoreCallBack() {
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

    /*************************************** DELETE ACTIONS ***************************************/

    @Override
    public void deleteOrganization(String organizationServerID,
                                   CFirestoreCallBack firestoreCallBack) {
        if (!CFirestoreUtility.isEmptyOrNull(organizationServerID)) {
            DocumentReference organizationDocumentReference;
            organizationDocumentReference = organizationCollectionReference.document(
                    organizationServerID);
            fireStoreDelete(organizationDocumentReference, new CFirestoreCallBack() {
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

    /************************************** AUXILIARY ACTIONS *************************************/

    @Override
    public void removeListener() {
        if (listenerRegistration != null)
            listenerRegistration.remove();
    }

}