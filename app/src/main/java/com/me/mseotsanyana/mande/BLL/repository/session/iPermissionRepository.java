package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iPermissionRepository {

    void readRolePermissions(String organizationServerID, String userServerID,
                             int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                             List<Integer> statusBITS, iReadRolePermissionsCallback callback);

    interface iReadRolePermissionsCallback {
        void onReadRolePermissionsSucceeded(List<cTreeModel> treeModels);

        void onReadRolePermissionsFailed(String msg);
    }

    void updateRolePermissions(String organizationServerID, String userServerID,
                               int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                               List<Integer> statusBITS, List<cNode> nodes,
                               iUpdateRolePermissionsCallback callback);

    interface iUpdateRolePermissionsCallback {
        void onUpdateRolePermissionsSucceeded(String msg);

        void onUpdateRolePermissionsFailed(String msg);
    }

    void saveUserPermissions(iSaveUserPermissionsCallback callback);

    interface iSaveUserPermissionsCallback {
        void onSaveUserPermissionsSucceeded(String msg);

        void onSaveUserPermissionsFailed(String msg);

        void onSaveOwnerID(String ownerServerID);

        void onSaveOrganizationServerID(String organizationServerID);

        void onSavePrimaryTeamBIT(int primaryTeamBIT);

        void onSaveSecondaryTeams(List<Integer> secondaryTeams);

        void onSaveEntityBITS(String moduleKey, int entityBITS);

        void onSaveEntityPermBITS(String moduleKey, String entityKey, int operationBITS);

        void onSaveStatusBITS(String moduleKey, String entityKey, String operationKey,
                              List<Integer> statuses);

        void onSaveUnixPermBITS(String moduleKey, String entityKey, int unixpermBITS);

        void onSaveMenuItems(List<cMenuModel> menuModels);

        void onLoadDefaultSettings(String msg);
    }
    /*
    //    void readUserPermissions(String organizationServerID, String userServerID,
//                             int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                             List<Integer> statusBITS, iReadUserPermissionsCallback callback);
//
//    interface iReadUserPermissionsCallback {
//        void onReadUserPermissionsSucceeded(List<cTreeModel> treeModels);
//
//        void onReadUserPermissionsFailed(String msg);
//    }

    void readModulePermissions(String organizationServerID, String userServerID,
                             int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                             List<Integer> statusBITS,
                             iReadModulePermissionsCallback callback);

    interface iReadModulePermissionsCallback {
        void onReadModulePermissionsSucceeded(List<cTreeModel> treeModels);

        void onReadModulePermissionsFailed(String msg);
    }
*/

//    /* user shared preferences */
//    void commitSettings();
//    void deleteSettings();
//
//    /* save personal preferences of the loggedIn user */
//    void saveUserID(long userID);
//    void saveUserProfile(cUserModel userModel);
//    void saveCurrentOrganizationAndTeams();
//
//    void savePermissionBITS(Set<cPermissionModel> permissionSet);
//
//    //void saveOrganizationID(long organizationID);
//    //void savePrimaryTeamBIT(String teamID);
//    //void saveSecondaryTeamsBITS();
//    void saveDefaultPermsBITS(int bitNumber);
//    void saveDefaultStatusBITS(int bitNumber);
//
//
//
//    void saveStatusSet(Set<cStatusModel> statusModelSet);
//    void saveRoleSet(Set<cRoleModel> roleModelSet);
//    void saveIndividualOwners(Set<cUserModel> userModels);
//    void saveOrganizationOwners(Set<cOrganizationModel> organizationModels);
//
//    /* load personal preferences of the loggedIn user */
//    boolean isLoggedIn();
//    String loadUserID();
//    String loadOrganizationID();
//    int loadPrimaryRoleBITS();
//    int loadSecondaryRoleBITS();
//    int loadDefaultPermsBITS();
//    int loadDefaultStatusBITS();
//
//    String loadUserProfile();
//    int loadEntityBITS(long entityTypeID);
//    int loadOperationBITS(long entityID, long entityTypeID);
//    int loadStatusBITS(long entityID, long entityTypeID, long operationID);
//    Set loadStatusSet();
//    Set loadRoleSet();
//    Set loadIndividualOwners();
//    Set loadOrganizationOwners();
}
