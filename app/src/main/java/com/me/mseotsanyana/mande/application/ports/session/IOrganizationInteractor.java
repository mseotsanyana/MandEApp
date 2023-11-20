package com.me.mseotsanyana.mande.application.ports.organization;

import com.me.mseotsanyana.mande.application.ports.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;
import java.util.Map;

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

    interface AccountsCallback {
        void onReadOrganizationAccountsFailed(String msg);
        void onReadOrganizationAccountsSucceeded(Map<String, Object> orgAccountMap, String operation);
    }

    interface OrganizationWorkspacePresenter {
        void onReadOrganizationWorkspacesFailed(String msg);
        void onReadOrganizationWorkspacesSucceeded(List<cTreeModel> organizationModels);
    }
}
