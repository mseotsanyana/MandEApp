package com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.entities.models.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cWorkspaceModel;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseCallBack;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class cReadOrganizationsInteractorImpl extends cAbstractInteractor implements
        iOrganizationInteractor {
    private static final String TAG = cReadOrganizationsInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iOrganizationRepository organizationRepository;

//    private final String organizationServerID;
//    private final int primaryTeamBIT;
//    private final List<Integer> secondaryTeamBITS;
//    private final List<Integer> statusBITS;
//
//    private final int entityBITS;
//    private final int entitypermBITS;

    public cReadOrganizationsInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                            iSharedPreferenceRepository sharedPreferenceRepository,
                                            iOrganizationRepository organizationRepository,
                                            Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || organizationRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        // initialise objects
        this.organizationRepository = organizationRepository;
        this.callback = callback;


//        // load user shared preferences
//        this.organizationServerID = sharedPreferenceRepository.loadOrganizationID();
//        this.primaryTeamBIT = sharedPreferenceRepository.loadPrimaryWorkspaceBIT();
//        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();
//
//        // load entity shared preferences
//        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
//                cSharedPreference.SESSION_MODULE);
//        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
//                cSharedPreference.SESSION_MODULE, cSharedPreference.ORGANIZATION);
//        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
//                cSharedPreference.SESSION_MODULE, cSharedPreference.ORGANIZATION,
//                cSharedPreference.READ);
//
//        Log.d(TAG, " \n ORGANIZATION ID = " + this.organizationServerID +
//                " \n USER ID = " + this.userServerID +
//                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
//                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
//                " \n ENTITY BITS = " + this.entityBITS +
//                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
//                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadOrganizationsFailed(msg));
    }

    /* */
    private void postOrganization(List<cTreeModel> treeModels) {
        //mainThread.post(() -> callback.onReadOrganizationsSucceeded(organizationModels, operation));
        mainThread.post(() -> callback.onReadOrganizationsSucceeded(treeModels));
    }

    private void readOrganizationWorkspaces() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        /*if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
            if ((this.entityBITS & cSharedPreference.ORGANIZATION) != 0) {
                if ((this.entitypermBITS & cSharedPreference.READ) != 0) {*/

                    organizationRepository.readOrganizationWorkspaces(new cFirebaseCallBack() {
                                @Override
                                public void onFirebaseSuccess(Object object) {
                                    if (object != null) {
                                        Map<cOrganizationModel, List<cWorkspaceModel>> organizationModelMap;
                                        organizationModelMap = (Map<cOrganizationModel, List<cWorkspaceModel>>) object;

                                        List<cTreeModel> orgWorkspacesTree = new ArrayList<>();
                                        int parentIndex = 0, childIndex;
                                        for (Map.Entry<cOrganizationModel, List<cWorkspaceModel>> entry :
                                                organizationModelMap.entrySet()) {

                                            /* an organization */
                                            cOrganizationModel organizationModel = entry.getKey();
                                            orgWorkspacesTree.add(new cTreeModel(parentIndex,
                                                    -1, 0, organizationModel));

                                            /* a list of workspaces under the organization */
                                            childIndex = parentIndex;
                                            for (cWorkspaceModel teamModel : entry.getValue()) {
                                                childIndex = childIndex + 1;
                                                orgWorkspacesTree.add(new cTreeModel(childIndex,
                                                        parentIndex, 1, teamModel));
                                            }

                                            /* next parent index */
                                            parentIndex = childIndex + 1;
                                        }

                                        postOrganization(orgWorkspacesTree);
                                    } else {
                                        notifyError("No organization found!");
                                    }
                                }

                                @Override
                                public void onFirebaseFailure(Object object) {
                                    notifyError("No organization found!!");
                                }
                            });

                /*} else {
                    notifyError("Permission denied! Please contact your administrator");
                }
            } else {
                notifyError("No access to the entity! Please contact your administrator");
            }
        } else {
            notifyError("Error in default settings");
        }*/
    }
}

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