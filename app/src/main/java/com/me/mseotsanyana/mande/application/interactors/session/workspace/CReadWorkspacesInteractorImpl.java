package com.me.mseotsanyana.mande.application.interactors.session.workspace;

import android.util.Log;

import com.me.mseotsanyana.mande.application.ports.session.IWorkspaceInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.utils.cInteractorUtils;
import com.me.mseotsanyana.mande.application.preference.ISharedPreferenceRepository;
import com.me.mseotsanyana.mande.application.repository.session.iWorkspaceRepository;
import com.me.mseotsanyana.mande.application.structures.CSharedPreference;

import java.util.List;

public class cReadTeamsInteractorImpl extends cAbstractInteractor
        implements IWorkspaceInteractor {
    private static final String TAG = cReadTeamsInteractorImpl.class.getSimpleName();

    private final IWorkspacePresenter IWorkspacePresenter;
    private final iWorkspaceRepository teamRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadTeamsInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                    ISharedPreferenceRepository sharedPreferenceRepository,
                                    iWorkspaceRepository teamRepository,
                                    IWorkspacePresenter IWorkspacePresenter) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || teamRepository == null || IWorkspacePresenter == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.teamRepository = teamRepository;
        this.IWorkspacePresenter = IWorkspacePresenter;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                CSharedPreference.SESSION_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                CSharedPreference.SESSION_MODULE, CSharedPreference.ORGANIZATION);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                CSharedPreference.SESSION_MODULE, CSharedPreference.ORGANIZATION,
                CSharedPreference.READ);

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
        mainThread.post(() -> IWorkspacePresenter.OnReadWorkspacesFailed(msg));
    }

    /* */
    private void teamsMessage(List<CWorkspaceModel> teamModels) {
        mainThread.post(() -> IWorkspacePresenter.OnReadWorkspacesSucceeded(teamModels));
    }

    @Override
    public void run() {
        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
            if ((this.entityBITS & CSharedPreference.ORGANIZATION) != 0) {
                if ((this.entitypermBITS & CSharedPreference.READ) != 0) {

                    this.teamRepository.readTeams(organizationServerID, userServerID,
                            primaryTeamBIT, secondaryTeamBITS, statusBITS,
                            new iWorkspaceRepository.iReadTeamsCallback() {
                                @Override
                                public void onReadTeamsSucceeded(List<CWorkspaceModel> teamModels) {
                                    teamsMessage(teamModels);
                                }

                                @Override
                                public void onReadTeamsFailed(String msg) {
                                    notifyError(msg);
                                }
                            });

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