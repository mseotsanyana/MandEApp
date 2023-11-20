package com.me.mseotsanyana.mande.infrastructure.repository.firestore.session;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;
import com.me.mseotsanyana.mande.application.repository.session.iMenuRepository;

public class cMenuFirestoreRepositoryImpl implements iMenuRepository {
    //private static final String TAG = cMenuFirestoreRepositoryImpl.class.getSimpleName();

    private final Context context;
    private final FirebaseFirestore db;

    public cMenuFirestoreRepositoryImpl(Context context) {
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    /* ###################################### READ ACTIONS ###################################### */
//
//    @Override
//    public void readMenuPermissions(String organizationServerID, String userServerID,
//                                    int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                                    List<Integer> statusBITS,
//                                    iReadMenuPermissionsCallback callback) {

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
//                    filterMenuPermissions(roleModelMap, callback);
//                })
//                .addOnFailureListener(e ->
//                        callback.onReadMenuPermissionsFailed("Failed to read roles"));

    }

//    private void filterMenuPermissions(Map<String, cPrivilegeModel> roleModelMap,
//                                       iReadMenuPermissionsCallback callback) {
//        CollectionReference coPermissionRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
//        List<String> role_ids = new ArrayList<>(roleModelMap.keySet());
//        Query permissionQuery = coPermissionRef.whereIn(FieldPath.documentId(), role_ids);
//
//        permissionQuery.get()
//                .addOnCompleteListener(task -> {
//                    cPrivilegeModel roleModel; cPermissionModel permissionModel;
//                    List<cTreeModel> treeModels = new ArrayList<>();
//                    for (DocumentSnapshot perm_doc : Objects.requireNonNull(task.getResult())) {
//
//                        permissionModel = perm_doc.toObject(cPermissionModel.class);
//
//                        if (permissionModel != null) {
//                            permissionModel.setRoleServerID(perm_doc.getId());
//                            for (Map.Entry<String, cPrivilegeModel> entry : roleModelMap.entrySet()) {
//                                if (entry.getKey().equals(permissionModel.getRoleServerID())) {
//                                    // rolePermissionModels.put(entry.getValue(), permissionModel);
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
//                                    treeModels = cDatabaseUtils.getMenuPermissions(context,
//                                            permissionModel);
//
//                                    break;
//                                }
//                            }
//                        }
//                    }
//
//                    /* call back on list of user permissions by roles */
//                    callback.onReadMenuPermissionsSucceeded(treeModels);
//                })
//                .addOnFailureListener(e ->
//                        callback.onReadMenuPermissionsFailed("Failed to read permissions"));
//    }

    /* ##################################### UPDATE ACTIONS ##################################### */

//}