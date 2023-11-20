package com.me.mseotsanyana.mande.application.interactors.session.organization;

import android.util.Log;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.structures.CConstantModel;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.domain.entities.models.session.CMenuModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSwitchOrganizationWorkspaceInteractorImpl extends CAbstractInteractor
        implements IInteractor {
    private static final String TAG = CSwitchOrganizationWorkspaceInteractorImpl.class.getSimpleName();

    private final IPresenter<Map<String, Object>> iPresenter;
    private final ISessionManager sessionManager;
    private final IUserProfileRepository userProfileRepository;

    private final CWorkspaceModel workspaceModel;

    public CSwitchOrganizationWorkspaceInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                                      ISessionManager sessionManager,
                                                      IPresenter<Map<String, Object>> iPresenter,
                                                      IUserProfileRepository userProfileRepository,
                                                      CWorkspaceModel workspaceModel) {
        super(threadExecutor, mainThread, sessionManager);

        if (sessionManager == null || userProfileRepository == null || workspaceModel == null ||
                iPresenter == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        // initialise objects
        this.sessionManager = sessionManager;
        this.userProfileRepository = userProfileRepository;
        this.iPresenter = iPresenter;

        this.workspaceModel = workspaceModel;
    }

    @Override
    public void postResult(Map<String, Object> resultMap) {
        mainThread.post(() -> iPresenter.onSuccess(resultMap));
    }

    @Override
    public void postError(String errorMessage) {
        mainThread.post(() -> iPresenter.onError(new CGeneralException(errorMessage)));
    }

    @Override
    public void run() {
        userProfileRepository.saveUserPermissions(workspaceModel,
                new IUserProfileRepository.ISaveUserPermissionsCallback() {
                    @Override
                    public void onSaveUserPermissionsSucceeded(String msg) {
                        if (sessionManager.updateDocumentReference(
                                sessionManager.loadCompositeServerID()) &&
                                sessionManager.isCommitted()) {
                            Map<String, Object> map = new HashMap<>();
                            map.put(CConstantModel.NONEENTITY, msg);
                            postResult(map);
                        } else {
                            postError("Failed to save permissions!");
                        }
                    }

                    @Override
                    public void onSaveUserPermissionsFailed(String msg) {
                        sessionManager.clearAllSettings();
                        postError(msg);
                        Log.e(TAG, "permission failed to save", new Throwable());
                    }

                    // save organization settings
                    @Override
                    public void onSaveOrganizationServerID(String organizationServerID) {
                        sessionManager.saveOrganizationSeverID(CPreferenceConstant.KEY_ORG_ID,
                                organizationServerID);
                    }

                    @Override
                    public void onSaveOrganizationOwnerServerID(String organizationOwnerServerID) {
                        sessionManager.saveOrganizationOwnerSeverID(
                                CPreferenceConstant.KEY_ORG_OWNER_ID, organizationOwnerServerID);
                    }

                    // save workspace settings
                    @Override
                    public void onSaveCompositeServerID(String compositeServerID) {
                        sessionManager.saveCompositeServerID(
                                CPreferenceConstant.KEY_WORKSPACE_COMPOSITE_ID,
                                compositeServerID);
                    }

                    @Override
                    public void onSaveWorkspaceServerID(String workspaceServerID) {
                        sessionManager.saveOrganizationOwnerSeverID(
                                CPreferenceConstant.KEY_WORKSPACE_ID, workspaceServerID);
                    }

                    @Override
                    public void onSaveWorkspaceOwnerBIT(int workspaceOwnerBIT) {
                        sessionManager.saveWorkspaceOwnerBIT(
                                CPreferenceConstant.KEY_WORKSPACE_OWNER_BIT, workspaceOwnerBIT);
                    }

                    @Override
                    public void onSaveWorkspaceMembershipBITS(int workspaceMembershipBITS) {
                        sessionManager.saveWorkspaceMembershipBITS(
                                CPreferenceConstant.KEY_USER_WORKSPACE_MEMBERSHIP_BITS,
                                workspaceMembershipBITS);
                    }

                    @Override
                    public void onSaveMyOrganizations(List<String> organizations) {
                        sessionManager.saveMyOrganizations(
                                CPreferenceConstant.KEY_USER_ORGANIZATIONS, organizations);
                    }

                    // save user settings
                    @Override
                    public void onSaveUserServerID(String userServerID) {
                        sessionManager.saveUserServerID(
                                CPreferenceConstant.KEY_USER_ID, userServerID);
                    }

                    @Override
                    public void onSaveOwnerServerID(String ownerServerID) {
                        sessionManager.saveOwnerServerID(
                                CPreferenceConstant.KEY_USER_OWNER_ID, ownerServerID);
                    }

                    @Override
                    public void onSaveMenuItems(List<CMenuModel> menuModels) {
                        sessionManager.saveMenuItems(
                        CPreferenceConstant.KEY_MENU_ITEM_BITS, menuModels);
                    }

                    @Override
                    public void onSaveModuleBITS(int moduleBITS) {
                        sessionManager.saveModuleBITS(
                                CPreferenceConstant.KEY_MODULE_BITS, moduleBITS);
                    }

                    @Override
                    public void onSaveEntityBITS(String moduleKey, int entityBITS) {
                        sessionManager.saveEntityBITS(
                                CPreferenceConstant.KEY_MODULE_ENTITY_BITS+"-"+moduleKey, entityBITS);
                    }

                    @Override
                    public void onSaveActionBITS(int moduleKey, int entityKey,
                                                 int actionBITS) {
                        sessionManager.saveActionBITS(
                                CPreferenceConstant.KEY_MODULE_ENTITY_ACTION_BITS +"-"+
                                        moduleKey+"-"+entityKey, actionBITS);
                    }

                    @Override
                    public void onSaveStatusBITS(String moduleKey, String entityKey,
                                                 String actionKey, int statusBITS) {
                        sessionManager.saveStatusBITS(
                                CPreferenceConstant.KEY_MODULE_ENTITY_ACTION_STATUS_BITS +"-"+
                                        moduleKey+"-"+entityKey+"-"+actionKey, statusBITS);
                    }

                    @Override
                    public void onSavePermissionBITS(String moduleKey, String entityKey,
                                                     int permBITS) {
                        sessionManager.savePermissionBITS(
                                CPreferenceConstant.KEY_MODULE_ENTITY_PERM_BITS +"-"+
                                        moduleKey+"-"+entityKey, permBITS);
                    }
                });
    }
}

//    /* */
//    private void notifyError(String msg) {
//
//        //mainThread.post(() -> IOrganizationPresenter.onSwitchOrganizationWorkspaceFailed(msg));
//    }
//
//    /* */
//    private void postOrganization(String msg) {
//        Map<String, Object> map = new HashMap<>();
//        map.put(CConstantModel.NONEENTITY, msg);
//
//        //mainThread.post(() -> IOrganizationPresenter.onSwitchOrganizationWorkspaceSucceeded(msg));
//    }


        /*if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
//                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
//            if ((this.entityBITS & cSharedPreference.ORGANIZATION) != 0) {
//                if ((this.entitypermBITS & cSharedPreference.READ) != 0) {*/
//
//                    organizationRepository.readOrganizationWorkspaces(new cFirebaseCallBack() {
//                                @Override
//                                public void onFirebaseSuccess(Object object) {
//                                    if (object != null) {
//                                        Map<cOrganizationModel, List<cWorkspaceModel>> organizationModelMap;
//                                        organizationModelMap = (Map<cOrganizationModel, List<cWorkspaceModel>>) object;
//
//                                        List<cTreeModel> orgWorkspacesTree = new ArrayList<>();
//                                        int parentIndex = 0, childIndex;
//                                        for (Map.Entry<cOrganizationModel, List<cWorkspaceModel>> entry :
//                                                organizationModelMap.entrySet()) {
//
//                                            /* an organization */
//                                            cOrganizationModel organizationModel = entry.getKey();
//                                            orgWorkspacesTree.add(new cTreeModel(parentIndex,
//                                                    -1, 0, organizationModel));
//
//                                            /* a list of workspaces under the organization */
//                                            childIndex = parentIndex;
//                                            for (cWorkspaceModel teamModel : entry.getValue()) {
//                                                childIndex = childIndex + 1;
//                                                orgWorkspacesTree.add(new cTreeModel(childIndex,
//                                                        parentIndex, 1, teamModel));
//                                            }
//
//                                            /* next parent index */
//                                            parentIndex = childIndex + 1;
//                                        }
//
//                                        postOrganization(orgWorkspacesTree);
//                                    } else {
//                                        notifyError("No organization found!");
//                                    }
//                                }
//
//                                @Override
//                                public void onFirebaseFailure(Object object) {
//                                    notifyError("No organization found!!");
//                                }
//                            });
//
//                /*} else {
//                    notifyError("Permission denied! Please contact your administrator");
//                }
//            } else {
//                notifyError("No access to the entity! Please contact your administrator");
//            }
//        } else {
//            notifyError("Error in default settings");
//        }*/


//        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
//                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
//            if ((this.entityBITS & cSharedPreference.ORGANIZATION) != 0) {
//                if ((this.entitypermBITS & cSharedPreference.READ) != 0) {
//
//                    organizationRepository.readAllStakeholders(organizationServerID,
//                            userServerID, primaryTeamBIT, secondaryTeamBITS, statusBITS,
//                            new cFirebaseChildCallBack() {
//                                @Override
//                                public void onChildAdded(Object object) {
//                                    if (object != null)
//                                        postOrganization((cOrganizationModel) object,
//                                                "ADD");
//                                    else
//                                        notifyError("No organization found");
//                                }
//
//                                @Override
//                                public void onChildChanged(Object object) {
//                                    if (object != null)
//                                        postOrganization((cOrganizationModel) object,
//                                                "UPDATE");
//                                    else
//                                        notifyError("No organization found");
//                                }
//
//                                @Override
//                                public void onChildRemoved(Object object) {
//                                    if (object != null)
//                                        postOrganization((cOrganizationModel) object,
//                                                "DELETE");
//                                    else
//                                        notifyError("No organization found");
//                                }
//
//                                @Override
//                                public void onCancelled(Object object) {
//                                    notifyError(object.toString());
//                                }
//                            });
//
//
////                    organizationRepository.readOrganizations(organizationServerID, userServerID,
////                            primaryTeamBIT, secondaryTeamBITS, statusBITS,
////                            new iOrganizationRepository.iReadOrganizationsCallback() {
////                                @Override
////                                public void onReadOrganizationsSucceeded(
////                                        List<cOrganizationModel> organizationModels) {
////                                    postMessage(organizationModels);
////                                }
////
////                                @Override
////                                public void onReadOrganizationsFailed(String msg) {
////                                    notifyError(msg);
////                                }
////                            });
//                } else {
//                    notifyError("Permission denied! Please contact your administrator");
//                }
//            } else {
//                notifyError("No access to the entity! Please contact your administrator");
//            }
//        } else {
//            notifyError("Error in default settings");
//        }
//    }