package com.me.mseotsanyana.mande.application.interactors.session.userprofile;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.application.structures.CConstantModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;
import com.me.mseotsanyana.mande.application.structures.CResponseDTO;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.application.structures.enums.EAction;
import com.me.mseotsanyana.mande.domain.entities.models.session.CMenuModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CUserLoginInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements IInteractor {
    private final IPresenter<IResponseDTO<Object>> iPresenter;
    private final IUserProfileRepository userProfileRepository;

    private final String email;
    private final String password;

    public CUserLoginInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                    IPresenter<IResponseDTO<Object>> iPresenter,
                                    IUserProfileRepository userProfileRepository,
                                    String email, String password) {

        super(threadExecutor, mainThread, null);

        if (iPresenter == null || userProfileRepository == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        // callback to send data messages to the presentation layer
        this.iPresenter = iPresenter;
        // repository interface for shared preferences
        //this.sharedPreferenceRepository = sharedPreferenceRepository;
        // repository interface for loggedIn privileges
        //this.permissionRepository = permissionRepository;
        // repository interface for user profile - menu, user account
        this.userProfileRepository = userProfileRepository;

        this.email = email;
        this.password = password;
        //this.userProfileModel = new CUserProfileModel(email, password);
    }

    /**
     * send a login success message to the user
     *
     * @param resultMap result map
     */
    @Override
    public void postResult(IResponseDTO resultMap) {
        mainThread.post(() -> iPresenter.onSuccess(resultMap));
    }
    /**
     * send an error message to the user
     *
     * @param errorMessage error message
     */
    @Override
    public void postError(String errorMessage) {
        mainThread.post(() -> iPresenter.onError(new CGeneralException(errorMessage)));
    }

    @Override
    public void run() {
        /* sign in with email and password */
        userProfileRepository.signInWithEmailAndPassword(email, password,
                new CFirestoreCallBack() {
                    @Override
                    public void onFirebaseSuccess(Object object) {
                        //Map<String, Object> map = new HashMap<>();
                        //map.put(CConstantModel.SIGNIN, object);

                        IResponseDTO<Object> responseModel;
                        responseModel = new CResponseDTO<>(EAction.Signedin_USER, object);
                        postResult(responseModel);
                    }

                    @Override
                    public void onFirebaseFailure(Object object) {
                        postError((String) object);
                    }
                });
    }

    private void saveUserPermissions() {
        userProfileRepository.saveUserPermissions(null,
                new IUserProfileRepository.ISaveUserPermissionsCallback() {
            @Override
            public void onSaveUserPermissionsSucceeded(String msg) {

            }

            @Override
            public void onSaveUserPermissionsFailed(String msg) {

            }

            @Override
            public void onSaveOrganizationServerID(String organizationServerID) {

            }

            @Override
            public void onSaveOrganizationOwnerServerID(String organizationOwnerServerID) {

            }

            @Override
            public void onSaveCompositeServerID(String compositeServerID) {

            }

            @Override
            public void onSaveWorkspaceServerID(String workspaceServerID) {

            }

            @Override
            public void onSaveUserServerID(String userServerID) {

            }

            @Override
            public void onSaveOwnerServerID(String ownerServerID) {

            }

            @Override
            public void onSaveWorkspaceOwnerBIT(int workspaceOwnerBIT) {

            }

            @Override
            public void onSaveWorkspaceMembershipBITS(int workspaceMembershipBITS) {

            }

            @Override
            public void onSaveMyOrganizations(List<String> organizations) {

            }

            @Override
            public void onSaveMenuItems(List<CMenuModel> menuModels) {

            }

            @Override
            public void onSaveModuleBITS(int moduleBITS) {

            }

            @Override
            public void onSaveEntityBITS(String moduleKey, int entityBITS) {

            }

            @Override
            public void onSaveActionBITS(int moduleKey, int entityKey, int actionBITS) {

            }

            @Override
            public void onSaveStatusBITS(String moduleKey, String entityKey, String actionKey, int statusBITS) {

            }

            @Override
            public void onSavePermissionBITS(String moduleKey, String entityKey, int permBITS) {

            }
        });

    }
}

//    {
//        @Override
//        public void onSignInSucceeded(String msg) {
//        Map<String, Object> map = new HashMap<>();
//        map.put(CConstantModel.SIGNIN, msg);
//        userLoginSucceeded(map);
//
//        //saveLoggedInUserServerID();
//        //saveUserPermissions();
//    }
//
//        @Override
//        public void onSignInFailed(String msg) {
//        userLoginFailed(msg);
//    }
//    });

//    /**
//     * load default settings
//     */
//    private void loadDefaultSettings(String msg) {
//        mainThread.post(() -> callback.onUserLoginSucceeded(msg));
//    }

//
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
//}