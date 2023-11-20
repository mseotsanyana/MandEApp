package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iPrivilegePresenter extends iPresenter {
    interface View extends iBaseView {
        void onReadWorkspacePrivilegesFailed(String msg);
        void onReadWorkspacePrivilegesSucceeded(List<cTreeModel> treeModels);

        void onUpdateWorkspacePrivilegeFailed(String msg);
        void onUpdateWorkspacePrivilegeSucceeded(String msg);

        void onClickUpdateWorkspacePrivilege(cNode node);
        void onClickDeleteWorkspacePrivilege(String privilegeServerID);
    }

    void readWorkspacePrivileges();
    void updateWorkspacePrivileges(cNode node);
}

