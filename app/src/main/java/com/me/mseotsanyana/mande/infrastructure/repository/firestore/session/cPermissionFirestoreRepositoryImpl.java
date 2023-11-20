package com.me.mseotsanyana.mande.infrastructure.repository.firestore.session;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.domain.entities.models.session.cEntityModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CMenuModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CPrivilegeModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cSectionModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cUnixOperationCollection;
import com.me.mseotsanyana.mande.domain.entities.models.session.cUnixOperationModel;
import com.me.mseotsanyana.mande.application.repository.session.IPermissionRepository;
import com.me.mseotsanyana.mande.application.structures.CFirestoreConstant;
import com.me.mseotsanyana.mande.application.utils.CFirestoreUtility;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class cPermissionFirestoreRepositoryImpl implements IPermissionRepository {
    private static final String TAG = cPermissionFirestoreRepositoryImpl.class.getSimpleName();

    private static final int MENUITEM_SECTION = 1;
    private static final int ENTITYMODULE_SECTION = 4;
    private static final int ENTITY_OPS_SECTION = 6;
    private static final int UNIX_OPS_SECTION = 9;

    private final Context context;
    private final FirebaseFirestore db;

    Gson gson = new Gson();

    public cPermissionFirestoreRepositoryImpl(Context context) {
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    /* ##################################### CREATE ACTIONS ##################################### */

    @Override
    public void createWorkspacePrivilege(String workspaceServerID,
                                         iCreateWorkspacePrivilegeCallback callback) {

    }


    /* ###################################### READ ACTIONS ###################################### */

    @Override
    public void readWorkspacePrivileges(String userServerID, String organizationServerID,
                                        @NonNull List<String> myOrganizations,
                                        int workspaceMembershipBITS, List<Integer> statusBITS,
                                        iReadWorkspacePrivilegesCallback callback) {

        CollectionReference coWorkspacePrivilegesRef;
        coWorkspacePrivilegesRef = db.collection(CFirestoreConstant.KEY_WORKSPACE_PRIVILEGES);

        Query workspaceQuery;
        workspaceQuery = CFirestoreUtility.filterResourcesByStatus(coWorkspacePrivilegesRef,
                organizationServerID, myOrganizations, statusBITS);

        workspaceQuery.get()
                .addOnCompleteListener(task -> {
                    Map<String, CPrivilegeModel> workspaceModelMap = new HashMap<>();
                    List<cTreeModel> treeModels;
                    CPrivilegeModel privilegeModel;

                    for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                        privilegeModel = doc.toObject(CPrivilegeModel.class);
                        workspaceModelMap.put(doc.getId(), privilegeModel);
                    }

                    // add maps of menu items and entities not in the db
                    // return all menus
                    treeModels = CFirestoreUtility.buildWorkspacePrivileges(context, workspaceModelMap);
                    /* call back on list of user permissions by roles */
                    callback.onReadWorkspacePrivilegesSucceeded(treeModels);
                })
                .addOnFailureListener(e -> {
                    callback.onReadWorkspacePrivilegesFailed("Failed to read permissions");
                });
    }

    /* ##################################### UPDATE ACTIONS ##################################### */

    @Override
    public void updateWorkspacePrivilege(String organizationServerID, String userServerID,
                                         int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                         List<Integer> statusBITS, @NonNull cNode node,
                                         iUpdateWorkspacePrivilegeCallback callback) {



        /* find the root of the permission tree */
//        cNode root = null;
//        for (cNode node : nodes) {
//            if (node.isRoot()) {
//                root = node;
//                break;
//            }
//        }

        /* use the root to build the tree of the selected permissions */
        CPrivilegeModel privilegeModel = null;

        if (node != null) {
            privilegeModel = updateWorkspacePrivilegePermissions(node);
        }

        /* update permissions in the cloud */
        if (privilegeModel != null) {
            CollectionReference coWorkspacePrivilegeRef = db.collection(
                    CFirestoreConstant.KEY_WORKSPACE_PRIVILEGES);
            coWorkspacePrivilegeRef.document(privilegeModel.getPrivilegeServerID()).set(privilegeModel)
                    .addOnSuccessListener(unused -> callback.onUpdateWorkspacePrivilegeSucceeded(
                            "Permissions successfully updated"))
                    .addOnFailureListener(e -> callback.onUpdateWorkspacePrivilegeFailed(
                            "Error! Failed to update permissions "));

        }
    }

    /**
     * update the role permission which contains menu items and entity modules
     *
     * @param node root of the permission tree
     * @return root node
     */
    @NonNull
    private CPrivilegeModel updateWorkspacePrivilegePermissions(@NonNull cNode node) {


        //privilegeModel.setPrivilegeServerID(privilege.getPrivilegeServerID());
        //privilegeModel.setName(privilege.getName());
        //privilegeModel.setDescription(privilege.getDescription());

        //Gson gson = new Gson();

        /* update the privilege model */
        cTreeModel treeModel = (cTreeModel) node.getObj();
        CPrivilegeModel privilege = (CPrivilegeModel) treeModel.getModelObject();
        CPrivilegeModel privilegeModel = new CPrivilegeModel(privilege);
        privilegeModel.setModifiedDate(new Date());


        Map<String, List<Integer>> menuitems = new HashMap<>();
        Map<String, List<cEntityModel>> dbModules = new HashMap<>();

        for (cNode module : node.getChildren()) {
            cTreeModel treeModelPrivilege = (cTreeModel) module.getObj();
            cSectionModel sectionModel = (cSectionModel) treeModelPrivilege.getModelObject();
            String moduleServerID = sectionModel.getModuleServerID();

            /* MENU ITEM SECTION */
            if (treeModelPrivilege.getType() == MENUITEM_SECTION) {
                for (cNode mainmenu : module.getChildren()) {

                    cTreeModel mainmenuTreeModel = (cTreeModel) mainmenu.getObj();
                    CMenuModel menuModel = (CMenuModel) mainmenuTreeModel.getModelObject();

                    List<Integer> submenu_ids = new ArrayList<>();
                    if (menuModel.isChecked()) {
                        for (cNode submenu : mainmenu.getChildren()) {
                            cTreeModel submenuTreeModel = (cTreeModel) submenu.getObj();
                            CMenuModel submenuModel = (CMenuModel) submenuTreeModel.getModelObject();
                            if (submenuModel.isChecked()) {
                                submenu_ids.add(submenuModel.getMenuServerID());
                            }
                        }
                        menuitems.put(String.valueOf(menuModel.getMenuServerID()), submenu_ids);
                    }
                }
            }

            /* ENTITY MODULE SECTION */
            if (treeModelPrivilege.getType() == ENTITYMODULE_SECTION) {

                List<cEntityModel> dbEntities = new ArrayList<>();

                for (cNode entity : module.getChildren()) {
                    cTreeModel entityTreeModel = (cTreeModel) entity.getObj();
                    cEntityModel entityModel = (cEntityModel) entityTreeModel.getModelObject();

                    /* is the entity selected? */
                    if (entityModel.isChecked()) {
                        List<Integer> unixperms = new ArrayList<>();
                        for (cNode unixpermcollection : entity.getChildren()) {
                            cTreeModel unixpermTreeModel = (cTreeModel) unixpermcollection.getObj();
                            cUnixOperationCollection unixops;
                            unixops = (cUnixOperationCollection) unixpermTreeModel.getModelObject();
                            for (cUnixOperationModel unixperm : unixops.getUnixOperationModels()) {
                                if (unixperm.isChecked()) {
                                    unixperms.add(Integer.parseInt(unixperm.getOperationServerID()));
                                }
                            }
                            entityModel.setPermissions(unixperms);
                        }
                        dbEntities.add(entityModel);
                    }
                }

                dbModules.put(moduleServerID,dbEntities);
            }

        }
        //Log.d(TAG, " MODULES = "+privilegeModel.getModules().);
        // update the menu items
        privilegeModel.setMenuitems(menuitems);
        // update the entity modules
        privilegeModel.setModules(dbModules);

        return privilegeModel;
    }
//
//                            /* ENTITY OPERATIONS SECTION */
//
//                            if (sectionTreeModel.getType() == ENTITY_OPS_SECTION) {
//
//                                for (cNode operation : section.getChildren()) {
//                                    cTreeModel operationTreeModel = (cTreeModel) operation.getObj();
//                                    cOperationModel operationModel = (cOperationModel)
//                                            operationTreeModel.getModelObject();
//
//                                    /* is the entity operation selected? */
//                                    if (operationModel.isChecked()) {
//
//                                        for (cNode entity_op : operation.getChildren()) {
//                                            cTreeModel entityOpTreeModel;
//                                            entityOpTreeModel = (cTreeModel) entity_op.getObj();
//                                            cOperationStatusCollection collection;
//                                            collection = (cOperationStatusCollection)
//                                                    entityOpTreeModel.getModelObject();
//
//                                            List<Integer> statuses = new ArrayList<>();
//                                            for (cStatusModel statusModel : collection.
//                                                    getStatusCollection()) {
//                                                /* are the operation status selected? */
//                                                if (statusModel.isChecked()) {
//                                                    statuses.add(Integer.parseInt(
//                                                            statusModel.getStatusServerID()));
//                                                }
//                                            }
//
//                                            entityperms.put(operationModel.getOperationServerID(),
//                                                    statuses);
//                                        }
//                                    }
//                                }
//                            }
//
//                            /* UNIX OPERATIONS SECTION */
//
//                            if (sectionTreeModel.getType() == UNIX_OPS_SECTION) {
//                                for (cNode unix_ops : section.getChildren()) {
//                                    cTreeModel unixTreeModel = (cTreeModel) unix_ops.getObj();
//                                    cUnixOperationCollection collection = (cUnixOperationCollection)
//                                            unixTreeModel.getModelObject();
//
//                                    for (cUnixOperationModel unix : collection.getUnixOperationModels()) {
//                                        /* are unix permissions selected? */
//                                        if (unix.isChecked()) {
//                                            unixperms.add(Integer.parseInt(unix.getOperationServerID()));
//                                        }
//                                    }
//                                }
//                            }
//
//                        }
//                        // create entity model
//                        entityModels.add(new cEntityModel(entityModel.getEntityServerID(),
//                                entityperms, unixperms));
//                    }
//            }
//
//            //           modules.put(sectionModel.getModuleServerID(), entityModels);
//        }
//
//
//        // update the menu items
//        privilegeModel.setMenuitems(menuitems);
//        // update the entity modules
//        //privilegeModel.setModules(entitymodules);
//
//        return privilegeModel;
//    }


//    /**
//     * update the role permission which contains menu items and entity modules
//     *
//     * @param node root of the permission tree
//     * @return root node
//     */
//    private cPermissionModel updateWorkspacePrivilegePermissions(cNode node) {
//        cPermissionModel permissionModel = new cPermissionModel();
//        cTreeModel permTreeModel = (cTreeModel) node.getObj();
//        cPermissionModel perm = (cPermissionModel) permTreeModel.getModelObject();
//
//        /* update the permission model */
//        permissionModel.setRoleServerID(perm.getRoleServerID());
//        permissionModel.setName(perm.getName());
//        permissionModel.setDescription(perm.getDescription());
//
//
//
//        //Gson gson = new Gson();
//        Log.d(TAG, "=======================ROOT ====================== "+ perm.getRoleServerID());
//
//
//        Map<String, List<Integer>> menuitems = new HashMap<>();
//        Map<String, List<cEntityModel>> entitymodules = new HashMap<>();
//        for (cNode module : node.getChildren()) {
//            cTreeModel permSectionTreeModel = (cTreeModel) module.getObj();
//            cSectionModel sectionModel = (cSectionModel) permSectionTreeModel.getModelObject();
//
//            /* MENU ITEM SECTION */
//
//            if (permSectionTreeModel.getType() == MENUITEM_SECTION) {
//
//                for (cNode mainmenu : module.getChildren()) {
//
//                    cTreeModel mainmenuTreeModel = (cTreeModel) mainmenu.getObj();
//                    cMenuModel menuModel = (cMenuModel) mainmenuTreeModel.getModelObject();
//
//                    List<Integer> submenu_ids = new ArrayList<>();
//                    if (menuModel.isChecked()) {
//                        for (cNode submenu : mainmenu.getChildren()) {
//                            cTreeModel submenuTreeModel = (cTreeModel) submenu.getObj();
//                            cMenuModel submenuModel = (cMenuModel)
//                                    submenuTreeModel.getModelObject();
//                            if (submenuModel.isChecked()) {
//                                submenu_ids.add(submenuModel.getMenuServerID());
//                            }
//                        }
//                        menuitems.put(String.valueOf(menuModel.getMenuServerID()), submenu_ids);
//                    }
//                }
//            }
//
//            /* ENTITY MODULE SECTION */
//
//            if (permSectionTreeModel.getType() == ENTITYMODULE_SECTION) {
//
//                List<cEntityModel> entityModels = new ArrayList<>();
//                for (cNode entity : module.getChildren()) {
//                    cTreeModel entityTreeModel = (cTreeModel) entity.getObj();
//                    cEntityModel entityModel = (cEntityModel) entityTreeModel.getModelObject();
//
//                    /* is the entity selected? */
//                    if (entityModel.isChecked()) {
//                        Map<String, List<Integer>> entityperms = new HashMap<>();
//                        List<Integer> unixperms = new ArrayList<>();
//                        for (cNode section : entity.getChildren()) {
//                            cTreeModel sectionTreeModel = (cTreeModel) section.getObj();
//
//                            /* ENTITY OPERATIONS SECTION */
//
//                            if (sectionTreeModel.getType() == ENTITY_OPS_SECTION) {
//
//                                for (cNode operation : section.getChildren()) {
//                                    cTreeModel operationTreeModel = (cTreeModel) operation.getObj();
//                                    cOperationModel operationModel = (cOperationModel)
//                                            operationTreeModel.getModelObject();
//
//                                    /* is the entity operation selected? */
//                                    if (operationModel.isChecked()) {
//
//                                        for (cNode entity_op : operation.getChildren()) {
//                                            cTreeModel entityOpTreeModel;
//                                            entityOpTreeModel = (cTreeModel) entity_op.getObj();
//                                            cOperationStatusCollection collection;
//                                            collection = (cOperationStatusCollection)
//                                                    entityOpTreeModel.getModelObject();
//
//                                            List<Integer> statuses = new ArrayList<>();
//                                            for (cStatusModel statusModel : collection.
//                                                    getStatusCollection()) {
//                                                /* are the operation status selected? */
//                                                if (statusModel.isChecked()) {
//                                                    statuses.add(Integer.parseInt(
//                                                            statusModel.getStatusServerID()));
//                                                }
//                                            }
//
//                                            entityperms.put(operationModel.getOperationServerID(),
//                                                    statuses);
//                                        }
//                                    }
//                                }
//                            }
//
//                            /* UNIX OPERATIONS SECTION */
//
//                            if (sectionTreeModel.getType() == UNIX_OPS_SECTION) {
//                                for (cNode unix_ops : section.getChildren()) {
//                                    cTreeModel unixTreeModel = (cTreeModel) unix_ops.getObj();
//                                    cUnixOperationCollection collection = (cUnixOperationCollection)
//                                            unixTreeModel.getModelObject();
//
//                                    for (cUnixOperationModel unix : collection.getUnixOperationModels()) {
//                                        /* are unix permissions selected? */
//                                        if (unix.isChecked()) {
//                                            unixperms.add(Integer.parseInt(unix.getOperationServerID()));
//                                        }
//                                    }
//                                }
//                            }
//
//                        }
//                        // create entity model
//                        entityModels.add(new cEntityModel(entityModel.getEntityServerID(),
//                                entityperms, unixperms));
//                    }
//                }
//
//                entitymodules.put(sectionModel.getModuleServerID(), entityModels);
//            }
//        }
//
//        // update the menu items
//        permissionModel.setMenuitems(menuitems);
//        // update the entity modules
//        permissionModel.setEntitymodules(entitymodules);
//
//        return permissionModel;
//    }

    /* ##################################### DELETE ACTIONS ##################################### */

    @Override
    public void deleteWorkspacePrivilege(String userServerID, String organizationServerID, List<String> myOrganizations, int workspaceMembershipBITS, List<Integer> statusBITS, iDeleteWorkspacePrivilegeCallback callback) {

    }
}

//    @Override
//    public void saveLoggedInUserServerID(iSaveLoggedInUserServerIDCallback callback) {
//        String userServerID = FirebaseAuth.getInstance().getUid();
//        if (userServerID != null) {
//            callback.onSaveLoggedInUserServerID(userServerID);
//            callback.onSaveLoggedInUserServerIDSucceeded("successfully logged in");
//        } else
//            callback.onSaveLoggedInUserServerIDFailed("Failed to login!!");
//    }


//        WriteBatch batch = database.batch();
//
//        logframe_module
//                .addOnCompleteListener(task -> {
//                    for (QuerySnapshot result : Objects.requireNonNull(task.getResult())) {
//                        if (!result.isEmpty()) {
//                            for (QueryDocumentSnapshot ds : result) {
//                                batch.delete(ds.getReference());
//                            }
//                        }
//                    }
//
//                    // delete a logframes with corresponding components
//                    batch.commit().addOnCompleteListener(task1 -> {
//                        if (task1.isSuccessful()) {
//                            callback.onDeleteLogFramesSucceeded(
//                                    "Logframe module successfully deleted");
//                        } else {
//                            callback.onDeleteLogFrameFailed(
//                                    "Failed to delete Logframe module");
//                        }
//                    });
//                })
//                .addOnFailureListener(e ->
//                        callback.onDeleteLogFrameFailed("Failed to filter logframe components")
//                );
//
//
//        Query roleQuery = coRoleRef
//                .whereEqualTo("organizationOwnerID", organizationServerID)
//                .whereIn("statusBIT", statusBITS);
//
//
//        cDatabaseUtils.filterOrganizationsByStatus(null, null, statusBITS);
//
//        roleQuery.get()
//                .addOnCompleteListener(task -> {
//                    //List<String> role_ids = new ArrayList<>();
//                    Map<String, cPrivilegeModel> roleModelMap = new HashMap<>();
//                    cPrivilegeModel roleModel;
//                    for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
//                        roleModel = role_doc.toObject(cPrivilegeModel.class);
//
//                        if (roleModel != null) {
//                            roleModel.setPrivilegeServerID(role_doc.getId());
//
//                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
//                            perm.setUserOwnerID(roleModel.getUserOwnerID());
//                            perm.setTeamOwnerBIT(roleModel.getWorkspaceOwnerBIT());
//                            perm.setUnixpermBITS(roleModel.getUnixpermBITS());
//
////FIXME                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
////                                    secondaryTeamBITS)) {
////                                //role_ids.add(roleModel.getRoleServerID());
////                                roleModelMap.put(roleModel.getPrivilegeServerID(), roleModel);
////                            }
//                        }
//                    }
//
//                    /* filtering the permissions by roles */
//                    filterRolePermissions(roleModelMap, callback);
//                })
//                .addOnFailureListener(e ->
//                        callback.onReadWorkspacePermissionsFailed("Failed to read roles"));

//   }

//    @Override
//    public void readWorkspacePermissions(String organizationServerID, String userServerID,
//                                         int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                                         List<Integer> statusBITS, iReadWorkspacePermissionsCallback callback) {
//
//        CollectionReference coRoleRef = db.collection(cRealtimeHelper.KEY_ROLES);
//
//        Query roleQuery = coRoleRef
//                .whereEqualTo("organizationOwnerID", organizationServerID)
//                .whereIn("statusBIT", statusBITS);
//
//        roleQuery.get()
//                .addOnCompleteListener(task -> {
//                    //List<String> role_ids = new ArrayList<>();
//                    Map<String, cPrivilegeModel> roleModelMap = new HashMap<>();
//                    cPrivilegeModel roleModel;
//                    for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
//                        roleModel = role_doc.toObject(cPrivilegeModel.class);
//
//                        if (roleModel != null) {
//                            roleModel.setPrivilegeServerID(role_doc.getId());
//
//                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
//                            perm.setUserOwnerID(roleModel.getUserOwnerID());
//                            perm.setTeamOwnerBIT(roleModel.getWorkspaceOwnerBIT());
//                            perm.setUnixpermBITS(roleModel.getUnixpermBITS());
//
////FIXME                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
////                                    secondaryTeamBITS)) {
////                                //role_ids.add(roleModel.getRoleServerID());
////                                roleModelMap.put(roleModel.getPrivilegeServerID(), roleModel);
////                            }
//                        }
//                    }
//
//                    /* filtering the permissions by roles */
//                    filterRolePermissions(roleModelMap, callback);
//                })
//                .addOnFailureListener(e ->
//                        callback.onReadWorkspacePermissionsFailed("Failed to read roles"));
//
//    }

//    private void filterRolePermissions(Map<String, cPrivilegeModel> roleModelMap,
//                                       iReadWorkspacePrivilegesCallback callback) {
//        CollectionReference coPermissionRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
//        List<String> role_ids = new ArrayList<>(roleModelMap.keySet());
//        Query permissionQuery = coPermissionRef.whereIn(FieldPath.documentId(), role_ids);
//
//        permissionQuery.get()
//                .addOnCompleteListener(task -> {
//                    cPrivilegeModel roleModel;
//                    cPermissionModel permissionModel;
//                    List<cTreeModel> treeModels = new ArrayList<>();
//                    for (DocumentSnapshot perm_doc : Objects.requireNonNull(task.getResult())) {
//
//                        permissionModel = perm_doc.toObject(cPermissionModel.class);
//
//                        if (permissionModel != null) {
//                            permissionModel.setRoleServerID(perm_doc.getId());
//                            for (Map.Entry<String, cPrivilegeModel> entry : roleModelMap.entrySet()) {
//                                if (entry.getKey().equals(permissionModel.getRoleServerID())) {
//                                    // FIXME: what about if the user has list of multiple roles
//                                    //  and permissions
//                                    roleModel = entry.getValue();
//                                    roleModel.setPrivilegeServerID(entry.getKey());
//                                    permissionModel.setRoleServerID(perm_doc.getId());
//                                    permissionModel.setName(roleModel.getName());
//
//                                    //Log.d(TAG, "RESULT = " + gson.toJson(permissionModel));
//
//                                    // add maps of menu items and entities not in the db
//                                    // return all menus
//                                    treeModels = cDatabaseUtils.buildWorkspacePrivileges(context,
//                                            null);
//
//                                    break;
//                                }
//                            }
//                        }
//                    }
//
//                    /* call back on list of user permissions by roles */
//                    callback.onReadWorkspacePrivilegesSucceeded(treeModels);
//                })
//                .addOnFailureListener(e ->
//                        callback.onReadWorkspacePrivilegesFailed("Failed to read permissions"));
//    }

//    private void realtimeReadRolePermissions(){
//        CollectionReference coPermissionRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
//        coPermissionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value,
//                                @Nullable FirebaseFirestoreException error) {
//                if(error != null){
//                    Log.d(TAG, "ERROR");
//                }
//
//                if(value != null){
//                    Log.d(TAG, "================= IT WORKED ================");
//                }
//            }
//        });
//    }


//        /* module entities */
//        String moduleID;
//        for (Map.Entry<String, List<cEntityModel>> module_entities : entitymodules.entrySet()) {
//            moduleID = module_entities.getKey();
//
//            /* entities */
//            List<cEntityModel> entities = module_entities.getValue();
//            String entityID;
//            int entityBITS = 0;
//            for (cEntityModel entity : entities) {
//                entityID = entity.getEntityServerID();
//                entityBITS |= Integer.parseInt(entityID);
//
//                /* entity permissions */
//                Map<String, List<Integer>> entityperms = entity.getEntityperms();
//                String operationID;
//                int operationBITS = 0;
//                for (Map.Entry<String, List<Integer>> ops : entityperms.entrySet()) {
//                    operationID = ops.getKey();
//                    operationBITS |= Integer.parseInt(ops.getKey());
//                    /* statuses */
//                    List<Integer> statuses = new ArrayList<>(ops.getValue());
//
//                    // call back on saving status bits
//                    callback.onSaveStatusBITS(moduleID, entityID, operationID,
//                            statuses);
//                }
//                // call back on saving entity permission bits
//                callback.onSaveEntityPermBITS(moduleID, entityID, operationBITS);
//
//                // unix permissions
//                List<Integer> unixperms = entity.getUnixperms();
//                int unixpermBITS = 0;
//                for (Integer unix_perm : unixperms) {
//                    unixpermBITS |= unix_perm;
//                }
//                // call back on saving unix permission bits
//                callback.onSaveUnixPermBITS(moduleID, entityID, unixpermBITS);
//            }
//            // call back on saving entity bits
//            callback.onSaveEntityBITS(moduleID, entityBITS);
//        }
//    }

//
//    /**
//     * save user permissions locally for quick access control to the system resources
//     *
//     * @param callback call back
//     */
//    @Override
//    public void saveUserPermissions(iSaveUserPermissionsCallback callback) {
//
//        String userServerID = FirebaseAuth.getInstance().getUid();
//
//        CollectionReference coUserAccountsRef = db.collection(cRealtimeHelper.KEY_ORGANIZATION_MEMBERS);
//        Query userAccountQuery = coUserAccountsRef
//                .whereEqualTo("userServerID", userServerID)
//                .whereEqualTo("currentOrganization", true);
//
//        userAccountQuery.get()
//                .addOnCompleteListener(task -> {
//                    /* find the current account of the logged in user */
//                    String userAccountID = null, organizationID = null, primaryWorkspaceID = null;
//                    for (DocumentSnapshot useraccount : Objects.requireNonNull(task.getResult())) {
//                        cUserAccountModel userAccountModel;
//                        userAccountModel = useraccount.toObject(cUserAccountModel.class);
//                        if (userAccountModel != null && userAccountModel.isCurrentOrganization()) {
//                            userAccountID = userAccountModel.getUserAccountServerID();
//                            organizationID = userAccountModel.getOrganizationServerID();
//                            primaryWorkspaceID = userAccountModel.getWorkspaceServerID();
//                            break;
//                        }
//                    }
//
//                    /* save ownerID, organizationServerID, primaryTeamID and filter secondary teams,
//                       otherwise clear preferences and load default login details */
//                    if (userAccountID != null && organizationID != null && primaryWorkspaceID != null) {
//                        /* call back on saving logged in identification */
//                        callback.onSaveOwnerID(userServerID);
//                        /* call back on saving organization identification */
//                        callback.onSaveOrganizationServerID(organizationID);
//                        /* call back on saving primary bit */
//                        callback.onSavePrimaryWorkspaceBIT(Integer.parseInt(primaryWorkspaceID));
//
//                        //filterSecondaryWorkspaces(userAccountID, primaryWorkspaceID, callback);
//
//                        filterCurrentWorkspace(userAccountID, callback);
//
//                    } else {
//                        //FIXME: callback.onClearPreferences();
//                        callback.onLoadDefaultSettings(
//                                "No user account found! Loading default settings...");
//                    }
//                })
//                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
//                        "Failed due to user account entity!"));
//    }
//
//    /**
//     * This filters secondary teams bits of the loggedIn user and saves it.
//     *
//     * @param accountServerID    user account identification
//     * @param primaryWorkspaceID primary workspace identification
//     * @param callback           call back
//     */
//    public void filterSecondaryWorkspaces(String accountServerID, String primaryWorkspaceID,
//                                          iSaveUserPermissionsCallback callback) {
//
//        CollectionReference coWorkspaceMembersRef;
//        coWorkspaceMembersRef = db.collection(cRealtimeHelper.KEY_WORKSPACE_MEMBERS);
//
//        Query workspaceMemberQuery = coWorkspaceMembersRef
//                .whereEqualTo("userAccountServerID", accountServerID);
//
//        workspaceMemberQuery.get()
//                .addOnCompleteListener(task -> {
//                    Set<String> workspace_id_set = new HashSet<>();
//                    for (DocumentSnapshot workspace : Objects.requireNonNull(task.getResult())) {
//                        String workspaceID;
//                        workspaceID = Objects.requireNonNull(
//                                workspace.get("workspaceMemberServerID")).toString();
//                        workspace_id_set.add(workspaceID);
//                    }
//                    Log.d("TAG", "USER ACCOUNT = " + gson.toJson(workspace_id_set));
//
//                    if (!workspace_id_set.isEmpty()) {
//                        List<String> workspace_ids = new ArrayList<>(workspace_id_set);
//                        filterWorkspaces(workspace_ids, primaryWorkspaceID, callback);
//                    } else {
//                        //FIXME: callback.onClearPreferences();
//                        callback.onLoadDefaultSettings(
//                                "Not a member of any workspace! Loading default settings...");
//                    }
//                })
//                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
//                        "Failed due to workspace membership!"));
//    }
//
//    /**
//     * read the workspaces of the loggedIn user
//     *
//     * @param workspace_ids      workspace identifications
//     * @param primaryWorkspaceID primary team identification
//     * @param callback           call back
//     */
//    private void filterWorkspaces(List<String> workspace_ids, String primaryWorkspaceID,
//                                  iSaveUserPermissionsCallback callback) {
//
//        CollectionReference coWorkspacesRef = db.collection(cRealtimeHelper.KEY_WORKSPACES);
//
//        Query workspaceQuery = coWorkspacesRef
//                .whereIn("compositeServerID", workspace_ids);
//
//        workspaceQuery.get()
//                .addOnCompleteListener(task -> {
//                    Set<Integer> secondary_workspace_set = new HashSet<>();
//                    String workspaceID = null;
//                    for (DocumentSnapshot workspace : Objects.requireNonNull(task.getResult())) {
//                        workspaceID = Objects.requireNonNull(workspace.get("workspaceServerID")).toString();
//                        secondary_workspace_set.add(Integer.parseInt(workspaceID));
//                    }
//
//                    /* remove primary team if it is also in the set */
//                    secondary_workspace_set.remove(Integer.parseInt(primaryWorkspaceID));
//
//                    /* call back on saving secondary team bits */
//                    List<Integer> secondaryWorkspaces = new ArrayList<>(secondary_workspace_set);
//                    if (secondaryWorkspaces.isEmpty()) {
//                        secondaryWorkspaces.add(0);//FIXME: how to save empty list in preference
//                    }
//
//                    callback.onSaveSecondaryWorkspaces(secondaryWorkspaces);
//
//                    // Workspace Privileges - this is to get privileges for extracting
//                    // modules which contains: entities, transitions, events, action and
//                    // unix permissions, as well as menu items
//                    //-filterWorkspacePrivileges(workspaceID, callback);
//                })
//                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
//                        "Failed due to team entity!"));
//    }
//
//    /**
//     * read privileges of the workspaces the user belongs to
//     *
//     * @param userAccountID workspace identification
//     * @param callback      call back
//     */
//    private void filterCurrentWorkspace(String userAccountID, iSaveUserPermissionsCallback
//            callback) {
//        CollectionReference coWorkspaceMembersRef;
//        coWorkspaceMembersRef = db.collection(cRealtimeHelper.KEY_WORKSPACE_MEMBERS);
//
//        Query workspaceMembersQuery;
//        workspaceMembersQuery = coWorkspaceMembersRef.whereEqualTo("userAccountServerID",
//                userAccountID);
//
//        workspaceMembersQuery.get().addOnCompleteListener(task -> {
//            String workspaceID = null;
//            Log.d("TAG ================>>>>>>>>>> ", "" + task.getResult().size());
//
//            for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
//                workspaceID = Objects.requireNonNull(role_doc.get("workspaceMemberServerID")).
//                        toString();
//
//                callback.onSaveCurrentWorkspaceServerID(workspaceID);
//                break;
//            }
//
//            callback.onSaveUserPermissionsSucceeded("successfully logged in");
//
//        }).addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
//                "Failed due to workspace privileges!"));

//        coWorkspaceMembersRef.document(workspaceID).get()
//                .addOnCompleteListener(task -> {
//                    DocumentSnapshot privilege = Objects.requireNonNull(task.getResult());
//                    Log.d("TAG ================>>>>>>>>>> ", "" + workspaceID);
//
//                    cPrivilegeModel
//                            privilegeModel = privilege.toObject(cPrivilegeModel.class);
//                    assert privilegeModel != null;
//                    callback.onSavePrimaryWorkspace(privilegeModel.getName());
//
//                })
//                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
//                        "Failed due to workspace privileges!"));
//

//        coWorkspacePrivilegesRef.document(workspaceID).get()
//                .addOnCompleteListener(task -> {
//                    Set<String> role_id_set = new HashSet<>();
//                    for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
//                        String roleID = Objects.requireNonNull(role_doc.get("roleServerID")).
//                                toString();
//                        role_id_set.add(roleID);
//                    }
//
//                    if (!role_id_set.isEmpty()) {
//                        List<String> role_ids = new ArrayList<>(role_id_set);
//                        // filter roles permissions for saving purposes
//                        filterRolesPermissions(role_ids, callback);
//                    } else {
//                        //FIXME: callback.onClearPreferences();
//                        callback.onLoadDefaultSettings(
//                                "No role(s) assigned to your team(s)! " +
//                                        "Loading default settings...");
//                    }
//                })
//                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
//                        "Failed due to team roles!"));
//    }

//    private void filterWorkspacePrivileges(List<String> team_ids, iSaveUserPermissionsCallback
//            callback) {
//        CollectionReference coTeamsRolesRef = db.collection(cRealtimeHelper.KEY_WORKSPACE_PRIVILEGES);
//
//        Query roleQuery = coTeamsRolesRef
//                .whereIn("teamServerID", team_ids);
//
//        roleQuery.get()
//                .addOnCompleteListener(task -> {
//                    Set<String> role_id_set = new HashSet<>();
//                    for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
//                        String roleID = Objects.requireNonNull(role_doc.get("roleServerID")).
//                                toString();
//                        role_id_set.add(roleID);
//                    }
//
//                    if (!role_id_set.isEmpty()) {
//                        List<String> role_ids = new ArrayList<>(role_id_set);
//                        // filter roles permissions for saving purposes
//                        filterRolesPermissions(role_ids, callback);
//                    } else {
//                        //FIXME: callback.onClearPreferences();
//                        callback.onLoadDefaultSettings(
//                                "No role(s) assigned to your team(s)! " +
//                                        "Loading default settings...");
//                    }
//                })
//                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
//                        "Failed due to team roles!"));
//    }


//    /**
//     * filter roles for permissions - both entity and unix permissions
//     *
//     * @param roles_ids role identifications
//     * @param callback  call back
//     */
//    private void filterRolesPermissions(List<String> roles_ids,
//                                        iSaveUserPermissionsCallback callback) {
//        CollectionReference coRolePermsRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
//
//        Query permissionQuery = coRolePermsRef.whereIn(FieldPath.documentId(), roles_ids);
//
//        permissionQuery.get()
//                .addOnCompleteListener(task -> {
//
//                    QuerySnapshot perm_snapshot = task.getResult();
//
//                    if (perm_snapshot != null) {
//                        Map<String, List<Integer>> menuitems = new HashMap<>();
//                        Map<String, List<cEntityModel>> entitymodules = new HashMap<>();
//                        for (DocumentSnapshot perm_doc : perm_snapshot.getDocuments()) {
//                            cPermissionModel permissionModel = perm_doc.toObject(
//                                    cPermissionModel.class);
//                            if (permissionModel != null) {
//
//                                // add all role menu item permissions to a map
//                                menuitems.putAll(permissionModel.getMenuitems());
//
//                                // add all role entity and unix permissions to a map
//                                entitymodules.putAll(permissionModel.getEntitymodules());
//
//                            } else {
//                                //FIXME: callback.onClearPreferences();
//                                callback.onLoadDefaultSettings(
//                                        "No permission(s) assigned to your role(s)! " +
//                                                "Loading default settings...");
//                            }
//                        }
//
//                        // save menu item permissions
//                        saveMenuPermissions(menuitems, callback);
//                        // save entity and unix permissions
//                        saveEntityAndUnixPermissions(entitymodules, callback);
//                    } else {
//                        //FIXME: callback.onClearPreferences();
//                        callback.onLoadDefaultSettings(
//                                "No permission(s) assigned to your role(s)! " +
//                                        "Loading default settings...");
//                        return;
//                    }
//
//                    callback.onSaveUserPermissionsSucceeded("successfully logged in");
//                })
//                .addOnFailureListener(e -> callback.onSaveUserPermissionsFailed(
//                        "Failed due to permissions!"));
//    }
//
//    /**
//     * save user menu permissions
//     *
//     * @param menuitems a map of main menu items with their sub menu items
//     * @param callback  call back
//     */
//    private void saveMenuPermissions(Map<String, List<Integer>> menuitems,
//                                     iSaveUserPermissionsCallback callback) {
//        // build menu models from json data
//        List<cMenuModel> menu_items = cDatabaseUtils.getMenuModels(context, menuitems);
//        // save menu models to the shared preferences
//        callback.onSaveMenuItems(menu_items);
//    }
//
//    /**
//     * save user permissions
//     *
//     * @param entitymodules a map of modules with their entities
//     * @param callback      call back
//     */
//    private void saveEntityAndUnixPermissions
//    (Map<String, List<cEntityModel>> entitymodules,
//     iSaveUserPermissionsCallback callback) {
//        /* module entities */
//        String moduleID;
//        for (Map.Entry<String, List<cEntityModel>> module_entities : entitymodules.entrySet()) {
//            moduleID = module_entities.getKey();
//
//            /* entities */
//            List<cEntityModel> entities = module_entities.getValue();
//            String entityID;
//            int entityBITS = 0;
//            for (cEntityModel entity : entities) {
//                entityID = entity.getEntityServerID();
//                entityBITS |= Integer.parseInt(entityID);
//
//                /* entity permissions */
//                Map<String, List<Integer>> entityperms = entity.getEntityperms();
//                String operationID;
//                int operationBITS = 0;
//                for (Map.Entry<String, List<Integer>> ops : entityperms.entrySet()) {
//                    operationID = ops.getKey();
//                    operationBITS |= Integer.parseInt(ops.getKey());
//                    /* statuses */
//                    List<Integer> statuses = new ArrayList<>(ops.getValue());
//
//                    // call back on saving status bits
//                    callback.onSaveStatusBITS(moduleID, entityID, operationID,
//                            statuses);
//                }
//                // call back on saving entity permission bits
//                callback.onSaveEntityPermBITS(moduleID, entityID, operationBITS);
//
//                // unix permissions
//                List<Integer> unixperms = entity.getUnixperms();
//                int unixpermBITS = 0;
//                for (Integer unix_perm : unixperms) {
//                    unixpermBITS |= unix_perm;
//                }
//                // call back on saving unix permission bits
//                callback.onSaveUnixPermBITS(moduleID, entityID, unixpermBITS);
//            }
//            // call back on saving entity bits
//            callback.onSaveEntityBITS(moduleID, entityBITS);
//        }
//    }
//}

//@Override
//    public void readModulePermissions(String organizationServerID, String userServerID,
//                                      int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                                      List<Integer> statusBITS,
//                                      iReadModulePermissionsCallback callback) {
//
//    }
//
//
//    /**
//     * read user permissions
//     *
//     * @param organizationServerID organization identification
//     * @param userServerID         user identification
//     * @param primaryTeamBIT       primary team bit
//     * @param secondaryTeamBITS    secondary team bits
//     * @param statusBITS           status bits
//     * @param callback             call back
//     */
//    public void readUserPermissions(String organizationServerID, String userServerID,
//                                    int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                                    List<Integer> statusBITS,
//                                    iReadUserPermissionsCallback callback) {
//
//        CollectionReference coRoleRef = db.collection(cRealtimeHelper.KEY_ROLES);
//
//        Query roleQuery = coRoleRef
//                .whereEqualTo("organizationOwnerID", organizationServerID)
//                .whereIn("statusBIT", statusBITS);
//
//        roleQuery.get()
//                .addOnCompleteListener(task -> {
//                    //List<String> role_ids = new ArrayList<>();
//                    Map<String, cRoleModel> roleModelMap = new HashMap<>();
//                    cRoleModel roleModel;
//                    for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
//                        roleModel = role_doc.toObject(cRoleModel.class);
//
//                        if (roleModel != null) {
//                            roleModel.setRoleServerID(role_doc.getId());
//
//                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
//                            perm.setUserOwnerID(roleModel.getUserOwnerID());
//                            perm.setTeamOwnerBIT(roleModel.getTeamOwnerBIT());
//                            perm.setUnixpermBITS(roleModel.getUnixpermBITS());
//
//                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                                    secondaryTeamBITS)) {
//                                //role_ids.add(roleModel.getRoleServerID());
//                                roleModelMap.put(roleModel.getRoleServerID(), roleModel);
//                            }
//                        }
//                    }
//
//                    /* filtering the permissions by roles */
//                    filterRolePermissions(roleModelMap, callback);
//                })
//                .addOnFailureListener(e ->
//                        callback.onReadUserPermissionsFailed("Failed to read roles"));
//    }

//    /**
//     * filter user permissions by roles
//     *
//     * @param roleModelMap map of role identification and corresponding models
//     * @param callback     call back
//     */
//    private void filterRolePermissions(Map<String, cRoleModel> roleModelMap,
//                                       iReadUserPermissionsCallback callback) {
//
//        CollectionReference coPermissionRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
//        List<String> role_ids = new ArrayList<>(roleModelMap.keySet());
//        Query permissionQuery = coPermissionRef.whereIn(FieldPath.documentId(), role_ids);
//
//        permissionQuery.get()
//                .addOnCompleteListener(task -> {
//                    cRoleModel roleModel = null;
//                    cPermissionModel permissionModel = null;
//                    for (DocumentSnapshot perm_doc : Objects.requireNonNull(task.getResult())) {
//
//                        permissionModel = perm_doc.toObject(cPermissionModel.class);
//
//                        if (permissionModel != null) {
//                            permissionModel.setRoleServerID(perm_doc.getId());
//                            for (Map.Entry<String, cRoleModel> entry : roleModelMap.entrySet()) {
//                                //Log.d(TAG, " Role Model = "+entry.getKey()+" = "+permissionModel.getRoleServerID());
//                                if (entry.getKey().equals(permissionModel.getRoleServerID())) {
//
//                                    // rolePermissionModels.put(entry.getValue(), permissionModel);
//                                    // FIXME: what about if the user has list of multiple roles
//                                    //  and permissions
//                                    roleModel = entry.getValue();
//                                    roleModel.setRoleServerID(entry.getKey());
//
//                                    permissionModel.setName(roleModel.getName());
//                                    break;
//                                }
//                            }
//                        }
//                    }
//
//                    // add maps of menu items and entities not in the db
//                    // return all menus
//                    List<cTreeModel> treeModels = new ArrayList<>();
//                    if(roleModel != null) {
//
//                        //Log.d(TAG, " Type = "+roleModel.getName()+" -> "+permissionModel.getName());
//
//                        //treeModels = cDatabaseUtils.buildPermissionTree(context, roleModel,
//                        //        permissionModel);
//                    }
//
////                    Gson gson = new Gson();
////                    for (cTreeModel treeModel: treeModels){
////                        if(treeModel.getType() == 0) {
////                            Log.d(TAG, " Type = "+treeModel.getType()+" -> "+gson.toJson(treeModel));
////                        }
////                    }
//
//                    /* call back on list of user permissions by roles */
//                    callback.onReadUserPermissionsSucceeded(treeModels);
//                })
//                .addOnFailureListener(e ->
//                        callback.onReadUserPermissionsFailed("Failed to read permissions"));
//    }