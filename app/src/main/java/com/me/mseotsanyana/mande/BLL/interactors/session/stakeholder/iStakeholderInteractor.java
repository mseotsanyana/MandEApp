package com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cStakeholderModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.ArrayList;
import java.util.List;

public interface iStakeholderInteractor extends iInteractor {
    interface Callback{
        void onCreateStakeholderSucceeded(String msg);
        void onCreateStakeholderFailed(String msg);

        void onReadStakeholdersFailed(String msg);
        void onReadStakeholdersRetrieved(cStakeholderModel stakeholderModel, String operation);

        void onReadStakeholderMembersFailed(String msg);
        void onReadStakeholderMembersRetrieved(List<cUserProfileModel> userProfileModels);

        void onReadSharedOrgsFailed(String msg);
        void onSharedOrgsRetrieved(ArrayList<cStakeholderModel> organizationModels);
    }
}
