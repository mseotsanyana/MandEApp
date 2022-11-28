package com.me.mseotsanyana.mande.BLL.repository.common;

import com.me.mseotsanyana.mande.BLL.entities.models.session.cMenuModel;

import java.util.List;

public interface iSharedPreferenceRepository {
    /* user shared preferences */
    void commitSettings();
    void deleteSettings();
    void removeSetting(String key);

    void saveIntSetting(String key, int value);
    void saveStringSetting(String key, String value);
    void saveBooleanSetting(String key, Boolean value);
    void saveListOfIntegerSetting(String key, List<Integer> value);
    void saveListOfStringSetting(String key, List<String> value);
    void saveMenuItems(String key, List<cMenuModel> value);

    void saveCurrentWorkspace(String compositeServerID);

    /* load personal preferences of the loggedIn user */

    String loadUserID();
    String loadActiveOrganizationID();
    String loadCurrentWorkspace();
    int loadWorkspaceServerID();

    int loadWorkspaceMembershipBITS();

    List<String> loadMyOrganizations();

    int loadActiveWorkspaceBIT();
    List<Integer> loadSecondaryWorkspaces();

    int loadModuleBITS();
    int loadEntityBITS(int moduleKey);
    int loadEntityPermissionBITS(int moduleKey, int entityKey);
    int loadActionPermissionBITS(int moduleKey, int entityKey, String actionKey);

    List<Integer> loadOperationStatuses(int moduleKey, int entityKey, int operationKey);
    int loadUnixPermissionBITS(int moduleKey, int entityKey);
    List<cMenuModel> loadMenuItems();

    /* delete personal preferences of the loggedIn user */

    void deleteCurrentWorkspace();

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
//    int loadPrimaryTeamBIT();
//    int loadSecondaryTeamBITS();
//    int loadEntityPermissionBITS(int moduleKey, int entityKey);
//    List<Integer> loadOperationStatuses(int moduleKey, int entityKey, int operationKey);
//    int loadUnixPermissionBITS(int moduleKey, int entityKey);

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
