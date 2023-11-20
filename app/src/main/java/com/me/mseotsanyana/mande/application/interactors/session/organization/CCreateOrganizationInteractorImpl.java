package com.me.mseotsanyana.mande.application.interactors.session.organization;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.application.structures.CConstantModel;
import com.me.mseotsanyana.mande.application.structures.CResponseDTO;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.application.structures.enums.EAction;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.application.repository.session.IOrganizationRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;

import java.util.HashMap;
import java.util.Map;

public class CCreateOrganizationInteractorImpl extends
        CAbstractInteractor<IResponseDTO<Object>> implements IInteractor {

    //private static String TAG = CCreateOrganizationInteractorImpl.class.getSimpleName();

    private final IPresenter<IResponseDTO<Object>> iPresenter;
    private final IOrganizationRepository organizationRepository;
    private final COrganizationModel organizationModel;

    public CCreateOrganizationInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                             ISessionManager sessionManager,
                                             IPresenter<IResponseDTO<Object>> iPresenter,
                                             IOrganizationRepository organizationRepository,
                                             COrganizationModel organizationModel) {

        super(threadExecutor, mainThread, sessionManager);

        if (organizationRepository == null || iPresenter == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.iPresenter = iPresenter;
        this.organizationRepository = organizationRepository;
        this.organizationModel = organizationModel;
    }

    @Override
    public void postResult(IResponseDTO<Object> resultMap) {
        mainThread.post(() -> iPresenter.onSuccess(resultMap));
    }

    @Override
    public void postError(String errorMessage) {
        mainThread.post(() -> iPresenter.onError(new CGeneralException(errorMessage)));
    }

    @Override
    public void run() {
        /* create a new organization object and insert it */
        organizationRepository.createOrganization(organizationModel, new CFirestoreCallBack() {
            @Override
            public void onFirebaseSuccess(Object object) {
                IResponseDTO<Object> responseModel;
                responseModel = new CResponseDTO<>(EAction.Created_ORGANIZATION, object);

                postResult(responseModel);
            }

            @Override
            public void onFirebaseFailure(Object object) {
                postError((String) object);
            }
        });
    }
}

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
