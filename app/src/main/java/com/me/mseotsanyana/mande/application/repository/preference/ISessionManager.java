package com.me.mseotsanyana.mande.application.repository.preference;

import com.me.mseotsanyana.mande.domain.entities.models.session.CMenuModel;

import java.util.List;
import java.util.Map;

public interface ISessionManager {
    /* user shared preferences */
    boolean isCommitted();
    void clearApplied();
    boolean isClearCommitted();
    boolean updateDocumentReference(String documentID);

    void commitSettings();
    void deleteSettings();
    void removeSetting(String key);

    void saveWorkspaceMembershipBITS(String key, int value);
    void saveUserServerID(String key, String value);
    void saveCompositeServerID(String key, String value);
    void saveOwnerServerID(String key, String value);
    void saveLoggedInUserServerID(String key, String value);
    void saveOrganizationSeverID(String key, String value);
    void saveOrganizationOwnerSeverID(String key, String value);
    void saveWorkspaceOwnerBIT(String key, int value);
    void saveMyOrganizations(String key, List<String> value);
    void saveStatusBITS(String key, int value);
    void saveActionBITS(String key, int value);
    void savePermissionBITS(String key, int value);
    void saveEntityBITS(String key, int value);
    void saveModuleBITS(String key, int value);

    void saveBooleanSetting(String key, Boolean value);
    void saveListOfIntegerSetting(String key, List<Integer> value);
    void saveMenuItems(String key, List<CMenuModel> value);

    void saveCurrentWorkspace(String compositeServerID);

    /* load personal preferences of the loggedIn user */
    /**
     *     Save and get HashMap in SharedPreference
     */

    void saveHashMap(String key , Object obj);
    Map<String, Object> loadHashMap(String key);


    String loadCompositeServerID();

    String loadLoggedInUserServerID();
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
    List<CMenuModel> loadMenuItems(String key);

    /* delete personal preferences of the loggedIn user */

    void deleteCurrentWorkspace();

//    /* save personal preferences of the loggedIn user */
//    void saveUserID(long userID);
//    void saveUserProfile(cUserModel userModel);
//    void saveCurrentOrganizationAndTeams();
//
//    void savePermissionBITS(Set<cPermissionModel> permissionSet);
//
//
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
