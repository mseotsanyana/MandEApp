package com.me.mseotsanyana.mande.application.interactors.session.privilege;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iReadWorkspacePrivilegesInteractor extends iInteractor {
    interface Callback{
        void onReadRolePermissionsFailed(String msg);
        void onReadRolePermissionsSucceeded(List<cTreeModel> treeModels);
    }
}
