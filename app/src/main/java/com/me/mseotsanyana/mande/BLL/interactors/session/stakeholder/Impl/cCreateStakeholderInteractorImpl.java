package com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.iStakeholderInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStakeholderModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iStakeholderRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import java.util.List;

public class cCreateStakeholderInteractorImpl extends cAbstractInteractor
        implements iStakeholderInteractor {
    //private static String TAG = cCreateOrganizationInteractorImpl.class.getSimpleName();

    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iPermissionRepository permissionRepository;
    private final iStakeholderRepository stakeholderRepository;
    private final Callback callback;
    private final cStakeholderModel organizationModel;

    public cCreateStakeholderInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                            iSharedPreferenceRepository sharedPreferenceRepository,
                                            iPermissionRepository permissionRepository,
                                            iStakeholderRepository stakeholderRepository,
                                            Callback callback, cStakeholderModel organizationModel) {
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
    private void createStakeholderFailed(String msg) {
        mainThread.post(() -> callback.onCreateStakeholderFailed(msg));
    }

    /* */
    private void createStakeholderSucceeded(String msg) {
        mainThread.post(() -> callback.onCreateStakeholderSucceeded(msg));
    }

    /**
     * delete old and save new user privileges of the loggedIn user
     */
    private void saveUserPermissions() {
        // delete old permissions
        this.sharedPreferenceRepository.deleteSettings();

        // save new permissions
        this.permissionRepository.saveUserPermissions(new iPermissionRepository.
                iSaveUserPermissionsCallback() {
            @Override
            public void onSaveUserPermissionsSucceeded(String successMessage) {
                createStakeholderSucceeded(successMessage);
            }

            @Override
            public void onSaveUserPermissionsFailed(String errorMessage) {
                createStakeholderFailed(errorMessage);
            }

            @Override
            public void onSaveOwnerID(String ownerServerID) {
                sharedPreferenceRepository.saveStringSetting(cSharedPreference.KEY_USER_ID,
                        ownerServerID);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveOrganizationServerID(String organizationServerID) {
                sharedPreferenceRepository.saveStringSetting(cSharedPreference.KEY_ORG_ID,
                        organizationServerID);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSavePrimaryTeamBIT(int primaryTeamBIT) {
                sharedPreferenceRepository.saveIntSetting(
                        cSharedPreference.KEY_PRIMARY_TEAM_BIT, primaryTeamBIT);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveSecondaryTeams(List<Integer> secondaryTeams) {
                sharedPreferenceRepository.saveListIntegerSetting(
                        cSharedPreference.KEY_SECONDARY_TEAMS, secondaryTeams);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveEntityBITS(String moduleKey, int entityBITS) {
                sharedPreferenceRepository.saveIntSetting(
                        cSharedPreference.KEY_MODULE_ENTITY_BITS + "-" + moduleKey,
                        entityBITS);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveEntityPermBITS(String moduleKey, String entityKey,
                                             int operationBITS) {
                sharedPreferenceRepository.saveIntSetting(
                        cSharedPreference.KEY_ENTITY_OPERATION_BITS + "-" + moduleKey + "-" +
                                entityKey, operationBITS);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveStatusBITS(String moduleKey, String entityKey, String operationKey,
                                         List<Integer> statuses) {
                if(statuses.isEmpty()){
                    statuses.add(0);
                }

                sharedPreferenceRepository.saveListIntegerSetting(
                        cSharedPreference.KEY_OPERATION_STATUS_BITS + "-" + moduleKey + "-" +
                                entityKey + "-" + operationKey, statuses);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveUnixPermBITS(String moduleKey, String entityKey, int unixpermBITS) {
                sharedPreferenceRepository.saveIntSetting(
                        cSharedPreference.KEY_UNIX_PERM_BITS + "-" + moduleKey + "-" + entityKey,
                        unixpermBITS);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveMenuItems(List<cMenuModel> menuModels) {
                sharedPreferenceRepository.saveMenuItems(
                        cSharedPreference.KEY_MENU_ITEM_BITS, menuModels);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onLoadDefaultSettings(String msg) {
                sharedPreferenceRepository.deleteSettings();
                createStakeholderFailed(msg);
            }
        });
    }


    @Override
    public void run() {

        /* create a new organization object and insert it */
        stakeholderRepository.createStakeholder(organizationModel,
                new iStakeholderRepository.iCreateStakeholderCallback() {
            @Override
            public void onCreateStakeholderSucceeded(String msg) {
                // clear preferences                // upload new preferences
                saveUserPermissions();
            }

            @Override
            public void onCreateStakeholderFailed(String msg) {
                createStakeholderFailed(msg);
            }
        });
    }
}
