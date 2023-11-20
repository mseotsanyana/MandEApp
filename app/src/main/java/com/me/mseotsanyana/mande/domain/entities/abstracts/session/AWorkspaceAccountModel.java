package com.me.mseotsanyana.mande.domain.entities.abstracts.session;

import com.me.mseotsanyana.mande.domain.entities.interfaces.IWorkspaceAccountState;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceAccountModel;

abstract public class AWorkspaceAccountModel implements IWorkspaceAccountState {
    protected CWorkspaceAccountModel workspaceAccountModel;

    public AWorkspaceAccountModel(CWorkspaceAccountModel workspaceAccountModel) {
        this.workspaceAccountModel = workspaceAccountModel;
    }

    @Override
    public void createAccountEvent(int requestType) {

    }

    @Override
    public void approveAccountEvent() {

    }

    @Override
    public void rejectedAccountEvent() {

    }

    @Override
    public void activateAccountEvent() {

    }

    @Override
    public void deactivateAccountEvent() {

    }

    @Override
    public void deleteAccountEvent() {

    }
}
