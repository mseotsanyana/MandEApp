package com.me.mseotsanyana.mande.application.repository.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cPrivilegeModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;

import java.util.List;
import java.util.Map;

public interface iWorkspaceRepository {
    void readTeams(String organizationServerID, String userServerID,
                   int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                   List<Integer> statusBITS, iReadTeamsCallback callback);

    interface iReadTeamsCallback {
        void onReadTeamsSucceeded(List<CWorkspaceModel> teamModels);

        void onReadTeamsFailed(String msg);
    }

    void readTeamsWithMembers(String organizationServerID, String userServerID,
                              int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                              List<Integer> statusBITS, iReadTeamsWithMembersCallback callback);

    interface iReadTeamsWithMembersCallback {
        void onReadTeamsWithMembersSucceeded(Map<CWorkspaceModel, List<CUserProfileModel>>
                                                     teamMembersMap);

        void onReadTeamsWithMembersFailed(String msg);
    }

    void readTeamsWithRoles(String organizationServerID, String userServerID,
                            int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                            List<Integer> statusBITS, iReadTeamsWithRolesCallback callback);

    interface iReadTeamsWithRolesCallback {
        void onReadTeamsWithRolesSucceeded(Map<CWorkspaceModel, List<cPrivilegeModel>> teamRolesMap);

        void onReadTeamsWithRolesFailed(String msg);
    }


    void readTeamRoles(String roleServerID, String organizationServerID,
                       String userServerID, int primaryTeamBIT,
                       List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
                       iReadTeamRolesCallback callback);

    interface iReadTeamRolesCallback {
        void onReadTeamsSucceeded(List<CWorkspaceModel> teamModels);

        void onReadTeamMembersSucceeded(String teamServerID, List<CUserProfileModel> roleModels);

        void onReadTeamRolesSucceeded(List<cPrivilegeModel> roleModels);

        void onReadTeamsFailed(String msg);

        void onReadTeamRolesFailed(String msg);
    }
}
