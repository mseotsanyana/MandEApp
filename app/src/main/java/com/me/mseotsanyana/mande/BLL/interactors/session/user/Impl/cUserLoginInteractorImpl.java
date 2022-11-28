package com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUserLoginInteractor;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import java.util.List;

public class cUserLoginInteractorImpl extends cAbstractInteractor implements iUserLoginInteractor {
    private final Callback callback;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iPermissionRepository permissionRepository;
    private final iUserProfileRepository userProfileRepository;

    private final String email;
    private final String password;

    public cUserLoginInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                    Callback callback,
                                    iSharedPreferenceRepository sharedPreferenceRepository,
                                    iPermissionRepository permissionRepository,
                                    iUserProfileRepository userProfileRepository,
                                    String email, String password) {
        super(threadExecutor, mainThread);

        if (permissionRepository == null || sharedPreferenceRepository == null ||
                userProfileRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        // callback to send data messages to the presentation layer
        this.callback = callback;
        // repository interface for shared preferences
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        // repository interface for loggedIn privileges
        this.permissionRepository = permissionRepository;
        // repository interface for user profile - menu, user account
        this.userProfileRepository = userProfileRepository;

        this.email = email;
        this.password = password;
    }

    /**
     * send an error message to the user
     *
     * @param msg error message
     */
    private void userLoginFailed(String msg) {
        mainThread.post(() -> callback.onUserLoginFailed(msg));
    }

    /**
     * send a login success message to the user
     *
     * @param msg success message
     */
    private void userLoginSucceeded(String msg) {
        mainThread.post(() -> callback.onUserLoginSucceeded(msg));
    }

    @Override
    public void run() {
        /* sign in with email and password */
        userProfileRepository.signInWithEmailAndPassword(email, password,
                new iUserProfileRepository.iSignInRepositoryCallback() {
                    @Override
                    public void onSignInSucceeded(String msg) {
                        userLoginSucceeded(msg);
                        //saveLoggedInUserServerID();
                        //saveUserPermissions();
                    }

                    @Override
                    public void onSignInFailed(String msg) {
                        userLoginFailed(msg);
                    }
                });
    }

//    /**
//     * load default settings
//     */
//    private void loadDefaultSettings(String msg) {
//        mainThread.post(() -> callback.onUserLoginSucceeded(msg));
//    }


//    private void saveLoggedInUserServerID() {
//        this.permissionRepository.saveLoggedInUserServerID(
//                new iPermissionRepository.iSaveLoggedInUserServerIDCallback() {
//                    @Override
//                    public void onSaveLoggedInUserServerID(String userServerID) {
//                        sharedPreferenceRepository.saveStringSetting(cSharedPreference.KEY_USER_ID,
//                                userServerID);
//                        sharedPreferenceRepository.commitSettings();
//                    }
//
//                    @Override
//                    public void onSaveLoggedInUserServerIDSucceeded(String msg) {
//                        userLoginSucceeded(msg);
//                    }
//
//                    @Override
//                    public void onSaveLoggedInUserServerIDFailed(String msg) {
//                        userLoginFailed(msg);
//                    }
//                });
//    }
//
//
//    /**
//     * delete old and save new user privileges of the loggedIn user
//     */
//    private void saveUserPermissions() {
//        this.permissionRepository.saveUserPermissions(new iPermissionRepository.
//                iSaveUserPermissionsCallback() {
//            @Override
//            public void onSaveUserPermissionsSucceeded(String successMessage) {
//                userLoginSucceeded(successMessage);
//            }
//
//            @Override
//            public void onSaveUserPermissionsFailed(String errorMessage) {
//                userLoginFailed(errorMessage);
//            }
//
//            @Override
//            public void onSaveOwnerID(String ownerServerID) {
//                sharedPreferenceRepository.saveStringSetting(cSharedPreference.KEY_USER_ID,
//                        ownerServerID);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onSaveOrganizationServerID(String organizationServerID) {
//                sharedPreferenceRepository.saveStringSetting(cSharedPreference.KEY_ORG_ID,
//                        organizationServerID);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onSaveCurrentWorkspaceServerID(String workspaceServerID) {
//                sharedPreferenceRepository.saveStringSetting(
//                        cSharedPreference.KEY_CURRENT_WORKSPACE_ID, workspaceServerID);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onSaveOrganizationName(String organization) {
//                sharedPreferenceRepository.saveStringSetting(
//                        cSharedPreference.KEY_PRIMARY_WORKSPACE_NAME, organization);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onSavePrimaryWorkspaceBIT(int primaryWorkspaceBIT) {
//                sharedPreferenceRepository.saveIntSetting(
//                        cSharedPreference.KEY_PRIMARY_WORKSPACE_BIT, primaryWorkspaceBIT);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onSavePrimaryWorkspace(String primaryWorkspace) {
//                sharedPreferenceRepository.saveStringSetting(
//                        cSharedPreference.KEY_PRIMARY_WORKSPACE_NAME, primaryWorkspace);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onSaveSecondaryWorkspaces(List<Integer> secondaryWorkspaces) {
//                sharedPreferenceRepository.saveListIntegerSetting(
//                        cSharedPreference.KEY_SECONDARY_WORKSPACES, secondaryWorkspaces);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onSaveEntityBITS(String moduleKey, int entityBITS) {
//                sharedPreferenceRepository.saveIntSetting(
//                        cSharedPreference.KEY_MODULE_ENTITY_BITS + "-" + moduleKey,
//                        entityBITS);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onSaveEntityPermBITS(String moduleKey, String entityKey,
//                                             int operationBITS) {
//                sharedPreferenceRepository.saveIntSetting(
//                        cSharedPreference.KEY_ENTITY_OPERATION_BITS + "-" + moduleKey + "-" +
//                                entityKey, operationBITS);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onSaveStatusBITS(String moduleKey, String entityKey, String operationKey,
//                                         List<Integer> statuses) {
//                if (statuses.isEmpty()) {
//                    statuses.add(0);
//                }
//
//                sharedPreferenceRepository.saveListIntegerSetting(
//                        cSharedPreference.KEY_OPERATION_STATUS_BITS + "-" + moduleKey + "-" +
//                                entityKey + "-" + operationKey, statuses);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onSaveUnixPermBITS(String moduleKey, String entityKey, int unixpermBITS) {
//                sharedPreferenceRepository.saveIntSetting(
//                        cSharedPreference.KEY_UNIX_PERM_BITS + "-" + moduleKey + "-" + entityKey,
//                        unixpermBITS);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onSaveMenuItems(List<cMenuModel> menuModels) {
//                sharedPreferenceRepository.saveMenuItems(
//                        cSharedPreference.KEY_MENU_ITEM_BITS, menuModels);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onLoadDefaultSettings(String msg) {
//                sharedPreferenceRepository.deleteSettings();
//                loadDefaultSettings(msg);
//            }
//        });
//    }
}