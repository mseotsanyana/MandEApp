package com.me.mseotsanyana.mande.application.ports.session;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;

import java.util.Map;

public interface IOrganizationInteractor extends IInteractor {
    interface IOrganizationPresenter extends IPresenter {
        void OnCreateOrganizationSucceeded(String msg);
        void OnReadOrganizationSucceeded(COrganizationModel organizationModel, String operation);
        void OnUpdateOrganizationSucceeded(String msg);
        void OnDeleteOrganizationSucceeded(String msg);
        void OnInviteToOrganizationSucceeded(String msg);
    }



    interface IOrganizationWorkspacePresenter {
        void OnReadOrganizationSucceeded(COrganizationModel organizationModel, String operation);
        void OnReadOrganizationFailed(String msg);

        void OnReadWorkspaceSucceeded(CWorkspaceModel workspaceModel, String operation);
        void OnReadWorkspaceFailed(String msg);
    }

    interface AccountsCallback {
        void onReadOrganizationAccountsFailed(String msg);
        void onReadOrganizationAccountsSucceeded(Map<String, Object> orgAccountMap, String operation);
    }
}
