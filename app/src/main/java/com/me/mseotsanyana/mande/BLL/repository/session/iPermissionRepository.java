package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.entities.models.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cWorkspaceModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iPermissionRepository {

    void readWorkspacePrivileges(String userServerID, String organizationServerID,
                                 List<String> myOrganizations, int workspaceMembershipBITS,
                                 List<Integer> statusBITS, iReadWorkspacePrivilegesCallback callback);

    interface iReadWorkspacePrivilegesCallback {
        void onReadWorkspacePrivilegesSucceeded(List<cTreeModel> treeModels);

        void onReadWorkspacePrivilegesFailed(String msg);
    }

    void updateRolePermissions(String organizationServerID, String userServerID,
                               int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                               List<Integer> statusBITS, List<cNode> nodes,
                               iUpdateRolePermissionsCallback callback);

    interface iUpdateRolePermissionsCallback {
        void onUpdateRolePermissionsSucceeded(String msg);

        void onUpdateRolePermissionsFailed(String msg);
    }

    void saveLoggedInUserServerID(iSaveLoggedInUserServerIDCallback callback);

    interface iSaveLoggedInUserServerIDCallback {
        void onSaveLoggedInUserServerID(String userServerID);

        void onSaveLoggedInUserServerIDFailed(String msg);

        void onSaveLoggedInUserServerIDSucceeded(String msg);
    }


    void saveUserPrivilegePermissions(cWorkspaceModel workspaceModel,
                                      iSaveUserPrivilegePermissionsCallback callback);

    interface iSaveUserPrivilegePermissionsCallback {
        void onSaveUserPrivilegePermissionsSucceeded(String msg);

        void onSaveUserPrivilegePermissionsFailed(String msg);

        void onSaveOrganizationServerID(String organizationServerID);

        void onSaveOrganizationOwnerServerID(String organizationOwnerServerID);

        void onSaveCompositeServerID(String compositeServerID);

        void onSaveWorkspaceServerID(String workspaceServerID);

        void onSaveUserServerID(String userServerID);

        void onSaveOwnerID(String ownerServerID);

        void onSaveWorkspaceOwnerBIT(int workspaceOwnerBIT);

        void onSaveWorkspaceMembershipBITS(int workspaceMembershipBITS);

        void onSaveMyOrganizations(List<String> organizations);

        void onSaveMenuItems(List<cMenuModel> menuModels);

        void onSaveModuleBITS(int moduleBITS);

        void onSaveEntityBITS(String moduleKey, int entityBITS);

        void onSaveActionBITS(int moduleKey, int entityKey, int actionBITS);

        void onSaveStatusBITS(String moduleKey, String entityKey, String actionKey, int statusBITS);

        void onSavePermissionBITS(String moduleKey, String entityKey, int permBITS);

    }

    void saveUserPermissions(iSaveUserPermissionsCallback callback);

    interface iSaveUserPermissionsCallback {
        void onSaveUserPermissionsSucceeded(String msg);

        void onSaveUserPermissionsFailed(String msg);

        void onSaveOwnerID(String ownerServerID);

        void onSaveOrganizationServerID(String organizationServerID);

        void onSaveCurrentWorkspaceServerID(String workspaceServerID);

        void onSaveOrganizationName(String organization);

        void onSavePrimaryWorkspaceBIT(int primaryWorkspaceBIT);

        void onSavePrimaryWorkspace(String primaryWorkspace);

        void onSaveSecondaryWorkspaces(List<Integer> secondaryWorkspaces);

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
