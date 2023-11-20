package com.me.mseotsanyana.mande.domain.entities.abstracts.session;

import com.me.mseotsanyana.mande.domain.entities.interfaces.IOrganizationAccountState;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationAccountModel;

abstract public class AOrganizationAccountModel implements IOrganizationAccountState {
    protected COrganizationAccountModel organizationAccountModel;

    public AOrganizationAccountModel(COrganizationAccountModel organizationAccountModel) {
        this.organizationAccountModel = organizationAccountModel;
    }

    @Override
    public void signInEvent(String email, String password) {

    }

    @Override
    public void createAccountEvent() {

    }

    @Override
    public void approveAccountEvent() {

    }

    @Override
    public void suspendAccountEvent() {

    }

    @Override
    public void activateAccountEvent() {

    }

    @Override
    public void deactivateAccountEvent() {

    }

    @Override
    public void closeAccountEvent() {

    }

    @Override
    public void enableAccountEvent() {

    }

    @Override
    public void selectWorkspaceEvent() {

    }

    @Override
    public void logoutEvent() {

    }

    @Override
    abstract public void entry();

    @Override
    abstract public void exit();

    @Override
    abstract public String getState();

}
