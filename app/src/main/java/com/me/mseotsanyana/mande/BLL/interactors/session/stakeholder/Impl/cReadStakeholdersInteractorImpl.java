package com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.cInteractorUtils;
import com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.iStakeholderInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cStakeholderModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iStakeholderRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseChildCallBack;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import java.util.List;

public class cReadStakeholdersInteractorImpl extends cAbstractInteractor implements
        iStakeholderInteractor {
    private static final String TAG = cReadStakeholdersInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iStakeholderRepository organizationRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadStakeholdersInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                           iSharedPreferenceRepository sharedPreferenceRepository,
                                           iStakeholderRepository organizationRepository,
                                           Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || organizationRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        // initialise objects
        this.organizationRepository = organizationRepository;
        this.callback = callback;

        // load user shared preferences
        this.organizationServerID = sharedPreferenceRepository.loadOrganizationID();
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadPrimaryTeamBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryTeams();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.SESSION_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.ORGANIZATION);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.ORGANIZATION,
                cSharedPreference.READ);

        Log.d(TAG, " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n USER ID = " + this.userServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY BITS = " + this.entityBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadStakeholdersFailed(msg));
    }

    /* */
    private void postOrganization(cStakeholderModel organizationModels, String operation) {
        mainThread.post(() -> callback.onReadStakeholdersRetrieved(organizationModels, operation));
    }

    @Override
    public void run() {
        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
            if ((this.entityBITS & cSharedPreference.ORGANIZATION) != 0) {
                if ((this.entitypermBITS & cSharedPreference.READ) != 0) {

                    organizationRepository.readAllStakeholders(organizationServerID,
                            userServerID, primaryTeamBIT, secondaryTeamBITS, statusBITS,
                            new cFirebaseChildCallBack() {
                                @Override
                                public void onChildAdded(Object object) {
                                    if (object != null)
                                        postOrganization((cStakeholderModel) object,
                                                "ADD");
                                    else
                                        notifyError("No organization found");
                                }

                                @Override
                                public void onChildChanged(Object object) {
                                    if (object != null)
                                        postOrganization((cStakeholderModel) object,
                                                "UPDATE");
                                    else
                                        notifyError("No organization found");
                                }

                                @Override
                                public void onChildRemoved(Object object) {
                                    if (object != null)
                                        postOrganization((cStakeholderModel) object,
                                                "DELETE");
                                    else
                                        notifyError("No organization found");
                                }

                                @Override
                                public void onCancelled(Object object) {
                                    notifyError(object.toString());
                                }
                            });


//                    organizationRepository.readOrganizations(organizationServerID, userServerID,
//                            primaryTeamBIT, secondaryTeamBITS, statusBITS,
//                            new iOrganizationRepository.iReadOrganizationsCallback() {
//                                @Override
//                                public void onReadOrganizationsSucceeded(
//                                        List<cOrganizationModel> organizationModels) {
//                                    postMessage(organizationModels);
//                                }
//
//                                @Override
//                                public void onReadOrganizationsFailed(String msg) {
//                                    notifyError(msg);
//                                }
//                            });
                } else {
                    notifyError("Permission denied! Please contact your administrator");
                }
            } else {
                notifyError("No access to the entity! Please contact your administrator");
            }
        } else {
            notifyError("Error in default settings");
        }
    }
}
