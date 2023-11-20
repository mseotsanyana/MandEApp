package com.me.mseotsanyana.mande.application.interactors.session.privilege;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iUpdateWorkspacePrivilegeInteractor extends IInteractor {
    interface Callback {
        void onUpdateWorkspacePrivilegeFailed(String msg);
        void onUpdateWorkspacePrivilegeSucceeded(String msg);
    }
}
