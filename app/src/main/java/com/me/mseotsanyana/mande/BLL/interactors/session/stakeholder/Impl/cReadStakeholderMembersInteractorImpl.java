package com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder.iStakeholderMembersInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iStakeholderRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import java.util.List;

public class cReadStakeholderMembersInteractorImpl extends cAbstractInteractor implements
        iStakeholderMembersInteractor {
    private static final String TAG = cReadStakeholderMembersInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iStakeholderRepository organizationRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadStakeholderMembersInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                                 Callback callback,
                                                 iSharedPreferenceRepository sharedPreferenceRepository,
                                                 iStakeholderRepository organizationRepository) {
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
        this.organizationServerID = sharedPreferenceRepository.loadOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadPrimaryTeamBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryTeams();

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
        mainThread.post(() -> callback.onReadStakeholderMembersFailed(msg));
    }

    /* */
    private void postMessage(List<cUserProfileModel> userProfileModels) {
        mainThread.post(() -> callback.onReadStakeholderMembersRetrieved(userProfileModels));
    }

    @Override
    public void run() {
        if ((this.entityBITS & cSharedPreference.USERACCOUNT) != 0) {
            if ((this.entitypermBITS & cSharedPreference.READ) != 0) {
                organizationRepository.readStakeholderMembers(organizationServerID,
                        userServerID, primaryTeamBIT, secondaryTeamBITS, statusBITS,
                        new iStakeholderRepository.iReadStakeholderMembersCallback() {
                            @Override
                            public void onReadStakeholderMembersSucceeded(
                                    List<cUserProfileModel> userProfileModels) {
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
