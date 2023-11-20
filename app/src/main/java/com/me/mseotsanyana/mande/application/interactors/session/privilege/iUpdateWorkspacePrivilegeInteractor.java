package com.me.mseotsanyana.mande.application.interactors.session.privilege;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iUpdateWorkspacePrivilegeInteractor extends iInteractor {
    interface Callback {
        void onUpdateWorkspacePrivilegeFailed(String msg);
        void onUpdateWorkspacePrivilegeSucceeded(String msg);
    }
}
