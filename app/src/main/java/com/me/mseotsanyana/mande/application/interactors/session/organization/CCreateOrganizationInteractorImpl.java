package com.me.mseotsanyana.mande.application.interactors.session.organization.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.iExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.iMainThread;
import com.me.mseotsanyana.mande.application.ports.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.application.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.application.repository.session.iPrivilegeRepository;
import com.me.mseotsanyana.mande.application.preference.iSharedPreferenceRepository;

public class cCreateOrganizationInteractorImpl extends cAbstractInteractor
        implements iOrganizationInteractor {
    //private static String TAG = cCreateOrganizationInteractorImpl.class.getSimpleName();

    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iPrivilegeRepository permissionRepository;
    private final iOrganizationRepository stakeholderRepository;
    private final Callback callback;
    private final COrganizationModel organizationModel;

    public cCreateOrganizationInteractorImpl(Callback callback, iExecutor threadExecutor,
                                             iMainThread mainThread,
                                             iSharedPreferenceRepository sharedPreferenceRepository,
                                             iPrivilegeRepository permissionRepository,
                                             iOrganizationRepository stakeholderRepository,
                                             COrganizationModel organizationModel) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || permissionRepository == null ||
                stakeholderRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.permissionRepository = permissionRepository;
        this.stakeholderRepository = stakeholderRepository;
        this.callback = callback;
        this.organizationModel = organizationModel;
    }


    /* */
    private void createOrganizationFailed(String msg) {
        mainThread.post(() -> callback.onCreateOrganizationFailed(msg));
    }

    /* */
    private void createOrganizationSucceeded(String msg) {
        mainThread.post(() -> callback.onCreateOrganizationSucceeded(msg));
    }

    @Override
    public void run() {

        /* create a new organization object and insert it */
        stakeholderRepository.createOrganization(organizationModel,
                new iOrganizationRepository.iCreateOrganizationCallback() {
                    @Override
                    public void onCreateOrganizationSucceeded(String msg) {
                        // clear preferences
                        // upload new preferences
                        //saveUserPermissions();
                        createOrganizationSucceeded(msg);
                    }

                    @Override
                    public void onCreateOrganizationFailed(String msg) {
                        createOrganizationFailed(msg);
                    }
                });
    }
}

/**
 * delete old and save new user privileges of the loggedIn user
 */
//    private void saveUserPermissions() {
//        // delete old permissions
//        this.sharedPreferenceRepository.deleteSettings();
//
//        // save new permissions
//        this.permissionRepository.saveUserPermissions(new iPermissionRepository.
//                iSaveUserPermissionsCallback() {
//            @Override
//            public void onSaveUserPermissionsSucceeded(String successMessage) {
//                createOrganizationSucceeded(successMessage);
//            }
//
//            @Override
//            public void onSaveUserPermissionsFailed(String errorMessage) {
//                createOrganizationFailed(errorMessage);
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
//                        cSharedPreference.KEY_WORKSPACE_ID, workspaceServerID);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onSaveOrganizationName(String organization) {
//
//            }
//
//            @Override
//            public void onSavePrimaryWorkspaceBIT(int primaryWorkspaceBIT) {
//                sharedPreferenceRepository.saveIntSetting(
//                        cSharedPreference.KEY_WORKSPACE_OWNER_BIT, primaryWorkspaceBIT);
//                sharedPreferenceRepository.commitSettings();
//            }
//
//            @Override
//            public void onSavePrimaryWorkspace(String primaryWorkspace) {
//
//            }
//
//            @Override
//            public void onSaveSecondaryWorkspaces(List<Integer> secondaryTeams) {
//                sharedPreferenceRepository.saveListIntegerSetting(
//                        cSharedPreference.KEY_SECONDARY_WORKSPACES, secondaryTeams);
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
//                if(statuses.isEmpty()){
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
//                createOrganizationFailed(msg);
//            }
//        });
//    }
