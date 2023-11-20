package com.me.mseotsanyana.mande.application.ports.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface IWorkspaceInteractor extends IInteractor {
    interface IWorkspacePresenter extends IPresenter {
        void OnCreateWorkspaceSucceeded(String msg);
        void OnReadWorkspaceSucceeded(CWorkspaceModel workspaceModel,
                                      String operation, int position);
        void OnUpdateWorkspaceSucceeded(String msg);
        void OnDeleteWorkspaceSucceeded(String msg);
        void OnInviteToWorkspaceSucceeded(String msg);
    }
}

/*, IOrganizationWorkspaceInteractor.IOrganizationWorkspacePresenter*/
//{
//        void OnCreateWorkspaceSucceeded(String msg);
//        void OnCreateWorkspaceFailed(String msg);
//
//        void OnReadWorkspaceSucceeded(CWorkspaceModel workspaceModel,
//                                      String operation, int position);
//        void OnReadWorkspaceFailed(String msg);
//
//        void OnUpdateWorkspaceSucceeded(String msg);
//        void OnUpdateWorkspaceFailed(String msg);
//
//        void OnDeleteWorkspaceSucceeded(String msg);
//        void OnDeleteWorkspaceFailed(String msg);
//
//        void OnInviteToWorkspaceSucceeded(String msg);
//        void OnInviteToWorkspaceFailed(String msg);
//    }
//}
