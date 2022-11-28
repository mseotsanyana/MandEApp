package com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl;

import com.me.mseotsanyana.mande.BLL.entities.models.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cWorkspaceModel;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import java.util.List;

public class cSwitchOrganizationWorkspaceInteractorImpl extends cAbstractInteractor implements
        iOrganizationInteractor {
    //private static final String TAG = cSwitchOrganizationWorkspaceInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iPermissionRepository permissionRepository;

    private final cWorkspaceModel workspaceModel;

    public cSwitchOrganizationWorkspaceInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                                      iSharedPreferenceRepository sharedPreferenceRepository,
                                                      iPermissionRepository permissionRepository,
                                                      Callback callback, cWorkspaceModel workspaceModel) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || permissionRepository == null ||
                workspaceModel == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        // initialise objects
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.permissionRepository = permissionRepository;
        this.callback = callback;

        this.workspaceModel = workspaceModel;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onSwitchOrganizationWorkspaceFailed(msg));
    }

    /* */
    private void postOrganization(String msg) {
        mainThread.post(() -> callback.onSwitchOrganizationWorkspaceSucceeded(msg));
    }

    @Override
    public void run() {
        permissionRepository.saveUserPrivilegePermissions(workspaceModel,
                new iPermissionRepository.iSaveUserPrivilegePermissionsCallback() {
                    @Override
                    public void onSaveUserPrivilegePermissionsSucceeded(String msg) {
                        postOrganization(msg);
                    }

                    @Override
                    public void onSaveUserPrivilegePermissionsFailed(String msg) {
                        sharedPreferenceRepository.deleteSettings();
                        sharedPreferenceRepository.commitSettings();
                        notifyError(msg);
                    }

                    // save organization settings
                    @Override
                    public void onSaveOrganizationServerID(String organizationServerID) {
                        sharedPreferenceRepository.saveStringSetting(cSharedPreference.KEY_ORG_ID,
                                organizationServerID);
                        sharedPreferenceRepository.commitSettings();
                    }

                    @Override
                    public void onSaveOrganizationOwnerServerID(String organizationOwnerServerID) {
                        sharedPreferenceRepository.saveStringSetting(cSharedPreference.KEY_ORG_OWNER_ID,
                                organizationOwnerServerID);
                        sharedPreferenceRepository.commitSettings();
                    }

                    // save workspace settings
                    @Override
                    public void onSaveCompositeServerID(String compositeServerID) {
                        sharedPreferenceRepository.saveStringSetting(
                                cSharedPreference.KEY_WORKSPACE_COMPOSITE_ID,
                                compositeServerID);
                        sharedPreferenceRepository.commitSettings();
                    }

                    @Override
                    public void onSaveWorkspaceServerID(String workspaceServerID) {
                        sharedPreferenceRepository.saveStringSetting(
                                cSharedPreference.KEY_WORKSPACE_ID, workspaceServerID);
                        sharedPreferenceRepository.commitSettings();
                    }

                    @Override
                    public void onSaveWorkspaceOwnerBIT(int workspaceOwnerBIT) {
                        sharedPreferenceRepository.saveIntSetting(
                                cSharedPreference.KEY_WORKSPACE_OWNER_BIT, workspaceOwnerBIT);
                        sharedPreferenceRepository.commitSettings();
                    }


                    @Override
                    public void onSaveWorkspaceMembershipBITS(int workspaceMembershipBITS) {
                        sharedPreferenceRepository.saveIntSetting(
                                cSharedPreference.KEY_USER_WORKSPACE_MEMBERSHIP_BITS,
                                workspaceMembershipBITS);
                        sharedPreferenceRepository.commitSettings();
                    }

                    @Override
                    public void onSaveMyOrganizations(List<String> organizations) {
                        sharedPreferenceRepository.saveListOfStringSetting(
                                cSharedPreference.KEY_USER_ORGANIZATIONS, organizations);
                        sharedPreferenceRepository.commitSettings();
                    }

                    // save user settings
                    @Override
                    public void onSaveUserServerID(String userServerID) {
                        sharedPreferenceRepository.saveStringSetting(
                                cSharedPreference.KEY_USER_ID, userServerID);
                        sharedPreferenceRepository.commitSettings();
                    }

                    @Override
                    public void onSaveOwnerID(String ownerServerID) {
                        sharedPreferenceRepository.saveStringSetting(
                                cSharedPreference.KEY_USER_OWNER_ID, ownerServerID);
                        sharedPreferenceRepository.commitSettings();
                    }

                    @Override
                    public void onSaveMenuItems(List<cMenuModel> menuModels) {
                        sharedPreferenceRepository.saveMenuItems(
                        cSharedPreference.KEY_MENU_ITEM_BITS, menuModels);
                        sharedPreferenceRepository.commitSettings();
                    }

                    @Override
                    public void onSaveModuleBITS(int moduleBITS) {
                        sharedPreferenceRepository.saveIntSetting(
                                cSharedPreference.KEY_MODULE_BITS, moduleBITS);
                        sharedPreferenceRepository.commitSettings();
                    }

                    @Override
                    public void onSaveEntityBITS(String moduleKey, int entityBITS) {
                        sharedPreferenceRepository.saveIntSetting(
                                cSharedPreference.KEY_MODULE_ENTITY_BITS+"-"+moduleKey, entityBITS);
                        sharedPreferenceRepository.commitSettings();
                    }

                    @Override
                    public void onSaveActionBITS(int moduleKey, int entityKey,
                                                 int actionBITS) {
                        sharedPreferenceRepository.saveIntSetting(
                                cSharedPreference.KEY_MODULE_ENTITY_ACTION_BITS +"-"+
                                        moduleKey+"-"+entityKey, actionBITS);
                        sharedPreferenceRepository.commitSettings();
                    }

                    @Override
                    public void onSaveStatusBITS(String moduleKey, String entityKey,
                                                 String actionKey, int statusBITS) {
                        sharedPreferenceRepository.saveIntSetting(
                                cSharedPreference.KEY_MODULE_ENTITY_ACTION_STATUS_BITS +"-"+
                                        moduleKey+"-"+entityKey+"-"+actionKey, statusBITS);
                        sharedPreferenceRepository.commitSettings();
                    }

                    @Override
                    public void onSavePermissionBITS(String moduleKey, String entityKey,
                                                     int permBITS) {
                        sharedPreferenceRepository.saveIntSetting(
                                cSharedPreference.KEY_MODULE_ENTITY_PERM_BITS +"-"+
                                        moduleKey+"-"+entityKey, permBITS);
                        sharedPreferenceRepository.commitSettings();
                    }
                });
    }
}

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