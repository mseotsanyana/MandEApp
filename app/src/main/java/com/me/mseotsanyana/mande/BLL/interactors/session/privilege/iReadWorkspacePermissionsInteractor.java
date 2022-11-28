package com.me.mseotsanyana.mande.BLL.interactors.session.privilege;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iReadWorkspacePermissionsInteractor extends iInteractor {
    interface Callback{
        void onReadRolePermissionsFailed(String msg);
        void onReadRolePermissionsSucceeded(List<cTreeModel> treeModels);
    }
}
