package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cStakeholderModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseChildCallBack;

import java.util.List;

public interface iStakeholderRepository {
    void createStakeholder(cStakeholderModel stakeholderModel,
                           iCreateStakeholderCallback callback);

    interface iCreateStakeholderCallback {
        void onCreateStakeholderSucceeded(String msg);

        void onCreateStakeholderFailed(String msg);
    }

    void readAllStakeholders(String stakeholderServerID, String userServerID, int primaryTeamBIT,
                             List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
                             cFirebaseChildCallBack firebaseChildCallBack);

    void removeListener();

    void readStakeholders(String stakeholderServerID, String userServerID,
                          int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                          List<Integer> statusBITS, iReadStakeholdersCallback callback);

    interface iReadStakeholdersCallback {
        void onReadStakeholdersSucceeded(List<cStakeholderModel> stakeholderModels);

        void onReadStakeholdersFailed(String msg);
    }

    void readStakeholderMembers(String stakeholderServerID, String userServerID,
                                int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                List<Integer> statusBITS, iReadStakeholderMembersCallback callback);

    interface iReadStakeholderMembersCallback {
        void onReadStakeholderMembersSucceeded(List<cUserProfileModel> userProfileModels);

        void onReadStakeholderMembersFailed(String msg);
    }
}
