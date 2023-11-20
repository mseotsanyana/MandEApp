package com.me.mseotsanyana.mande.application.interactors.session.privilege;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iReadWorkspacePrivilegesInteractor extends IInteractor {
    interface Callback{
        void onReadRolePermissionsFailed(String msg);
        void onReadRolePermissionsSucceeded(List<cTreeModel> treeModels);
    }
}
