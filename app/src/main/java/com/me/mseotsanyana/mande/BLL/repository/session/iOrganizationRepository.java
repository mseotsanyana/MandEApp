package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.entities.models.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseCallBack;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseChildCallBack;

import java.util.List;

public interface iOrganizationRepository {
    void createOrganization(cOrganizationModel organizationModel,
                           iCreateOrganizationCallback callback);

    interface iCreateOrganizationCallback {
        void onCreateOrganizationSucceeded(String msg);

        void onCreateOrganizationFailed(String msg);
    }

//    void createStakeholder(cOrganizationModel stakeholderModel,
//                           iCreateOrganizationCallback callback);
//
//    interface iCreateStakeholderCallback {
//        void onCreateStakeholderSucceeded(String msg);
//
//        void onCreateStakeholderFailed(String msg);
//    }

    void readAllStakeholders(String stakeholderServerID, String userServerID, int primaryTeamBIT,
                             List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
                             cFirebaseChildCallBack firebaseChildCallBack);

    void readOrganizationWorkspaces(cFirebaseCallBack firebaseCallBack);

    void removeListener();

    void readStakeholders(String stakeholderServerID, String userServerID,
                          int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                          List<Integer> statusBITS, iReadStakeholdersCallback callback);

    interface iReadStakeholdersCallback {
        void onReadStakeholdersSucceeded(List<cOrganizationModel> stakeholderModels);

        void onReadStakeholdersFailed(String msg);
    }

    void readStakeholderMembers(String stakeholderServerID, String userServerID,
                                int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                List<Integer> statusBITS, iReadStakeholderMembersCallback callback);

    interface iReadStakeholderMembersCallback {
        void onReadStakeholderMembersSucceeded(List<CUserProfileModel> userProfileModels);

        void onReadStakeholderMembersFailed(String msg);
    }
}
