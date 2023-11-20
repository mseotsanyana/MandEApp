package com.me.mseotsanyana.mande.application.repository.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.cPrivilegeModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;

import java.util.List;

public interface iRoleRepository {
    void readTeamRoles(String organizationServerID, String userServerID,
                       int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                       List<Integer> statusBITS, iReadTeamRolesCallback callback);

    interface iReadTeamRolesCallback {
        void onReadTeamRolesSucceeded(List<cPrivilegeModel> roleModels);

        void onReadTeamRolesFailed(String msg);
    }

    void readRoleTeams(String roleServerID, String organizationServerID, String userServerID,
                       int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                       List<Integer> statusBITS, iReadRoleTeamsCallback callback);

    interface iReadRoleTeamsCallback {
        void onReadRoleTeamsSucceeded(List<CWorkspaceModel> teamModels);

        void onReadRoleTeamsFailed(String msg);
    }
}