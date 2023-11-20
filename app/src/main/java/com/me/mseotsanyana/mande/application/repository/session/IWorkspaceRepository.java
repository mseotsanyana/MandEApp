package com.me.mseotsanyana.mande.application.repository.session;

import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreChildCallBack;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;

import java.util.List;

public interface IWorkspaceRepository {
    void createWorkspace(CWorkspaceModel workspaceModel, CFirestoreCallBack firestoreCallBack);

    void readWorkspaces(String organizationServerID, String userServerID,
                        int primaryWorkspaceBIT, List<Integer> secondaryWorkspaceBITS,
                        List<Integer> statusBITS, CFirestoreChildCallBack firestoreChildCallBack);


    void updateWorkspace(CWorkspaceModel workspaceModel, CFirestoreCallBack firestoreCallBack);

    void deleteWorkspace(int workspaceBITS, CWorkspaceModel workspaceModel,
                         CFirestoreCallBack firestoreCallBack);

    // Auxiliary methods
    void removeListener();
}

//    interface IReadWorkspacesCallback {
//        void onReadTeamsSucceeded(List<CWorkspaceModel> teamModels);
//
//        void onReadTeamsFailed(String msg);
//    }
//
//    void readTeamsWithMembers(String organizationServerID, String userServerID,
//                              int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                              List<Integer> statusBITS, iReadTeamsWithMembersCallback callback);
//
//    interface iReadTeamsWithMembersCallback {
//        void onReadTeamsWithMembersSucceeded(Map<CWorkspaceModel, List<CUserProfileModel>>
//                                                     teamMembersMap);
//
//        void onReadTeamsWithMembersFailed(String msg);
//    }
//
//    void readTeamsWithRoles(String organizationServerID, String userServerID,
//                            int primaryTeamBIT, List<Integer> secondaryTeamBITS,
//                            List<Integer> statusBITS, iReadTeamsWithRolesCallback callback);
//
//    interface iReadTeamsWithRolesCallback {
//        void onReadTeamsWithRolesSucceeded(Map<CWorkspaceModel, List<CPrivilegeModel>> teamRolesMap);
//
//        void onReadTeamsWithRolesFailed(String msg);
//    }
//
//
//    void readTeamRoles(String roleServerID, String organizationServerID,
//                       String userServerID, int primaryTeamBIT,
//                       List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
//                       iReadTeamRolesCallback callback);
//
//    interface iReadTeamRolesCallback {
//        void onReadTeamsSucceeded(List<CWorkspaceModel> teamModels);
//
//        void onReadTeamMembersSucceeded(String teamServerID, List<CUserProfileModel> roleModels);
//
//        void onReadTeamRolesSucceeded(List<CPrivilegeModel> roleModels);
//
//        void onReadTeamsFailed(String msg);
//
//        void onReadTeamRolesFailed(String msg);
//    }