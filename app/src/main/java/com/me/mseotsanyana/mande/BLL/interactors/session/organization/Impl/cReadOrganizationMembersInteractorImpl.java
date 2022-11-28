package com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import java.util.List;

public class cReadOrganizationMembersInteractorImpl extends cAbstractInteractor implements
        iOrganizationInteractor {
    private static final String TAG = cReadOrganizationMembersInteractorImpl.class.getSimpleName();

    private final MemberCallback callback;
    private final iOrganizationRepository organizationRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadOrganizationMembersInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                                  MemberCallback callback,
                                                  iSharedPreferenceRepository sharedPreferenceRepository,
                                                  iOrganizationRepository organizationRepository) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || organizationRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        // initialise objects
        this.organizationRepository = organizationRepository;
        this.callback = callback;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.SESSION_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.USERACCOUNT);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.USERACCOUNT,
                cSharedPreference.READ);

        Log.d(TAG, " \n USER ID = " + this.userServerID +
                " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadOrganizationMembersFailed(msg));
    }

    /* */
    private void postMessage(List<CUserProfileModel> userProfileModels) {
        mainThread.post(() -> callback.onReadOrganizationMembersRetrieved(userProfileModels));
    }

    @Override
    public void run() {
        if ((this.entityBITS & cSharedPreference.USERACCOUNT) != 0) {
            if ((this.entitypermBITS & cSharedPreference.READ) != 0) {
                organizationRepository.readStakeholderMembers(organizationServerID,
                        userServerID, primaryTeamBIT, secondaryTeamBITS, statusBITS,
                        new iOrganizationRepository.iReadStakeholderMembersCallback() {
                            @Override
                            public void onReadStakeholderMembersSucceeded(
                                    List<CUserProfileModel> userProfileModels) {
                                postMessage(userProfileModels);
                            }

                            @Override
                            public void onReadStakeholderMembersFailed(String msg) {
                                notifyError(msg);
                            }
                        });
            } else {
                notifyError("Permission denied! Please contact your administrator");
            }
        } else {
            notifyError("No access to the entity! Please contact your administrator");
        }
    }
}
