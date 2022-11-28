package com.me.mseotsanyana.mande.BLL.interactors.session.organization;

import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iOrganizationInteractor extends iInteractor {
    interface Callback{
        void onCreateOrganizationSucceeded(String msg);
        void onCreateOrganizationFailed(String msg);

        void onReadOrganizationsFailed(String msg);
        void onReadOrganizationsSucceeded(List<cTreeModel> organizationModels);

        void onSwitchOrganizationWorkspaceFailed(String msg);
        void onSwitchOrganizationWorkspaceSucceeded(String msg);

        //void onReadSharedOrgsFailed(String msg);
        //void onSharedOrgsRetrieved(ArrayList<cOrganizationModel> organizationModels);
    }

    interface MemberCallback {
        void onReadOrganizationMembersFailed(String msg);
        void onReadOrganizationMembersRetrieved(List<CUserProfileModel> userProfileModels);
    }
}
