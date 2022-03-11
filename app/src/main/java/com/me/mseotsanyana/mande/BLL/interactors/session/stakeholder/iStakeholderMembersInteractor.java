package com.me.mseotsanyana.mande.BLL.interactors.session.stakeholder;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.List;

public interface iStakeholderMembersInteractor extends iInteractor {
    interface Callback{
        void onReadStakeholderMembersFailed(String msg);
        void onReadStakeholderMembersRetrieved(List<cUserProfileModel> userProfileModels);

//        void onReadSharedOrgsFailed(String msg);
//        void onSharedOrgsRetrieved(ArrayList<cOrganizationModel> organizationModels);
    }
}
