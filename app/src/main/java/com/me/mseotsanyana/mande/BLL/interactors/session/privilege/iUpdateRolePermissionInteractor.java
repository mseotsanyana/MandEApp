package com.me.mseotsanyana.mande.BLL.interactors.session.privilege;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iUpdateRolePermissionInteractor extends iInteractor {
    interface Callback {
        void onUpdateRolePermissionFailed(String msg);
        void onUpdateRolePermissionSucceeded(String msg);
    }
}
