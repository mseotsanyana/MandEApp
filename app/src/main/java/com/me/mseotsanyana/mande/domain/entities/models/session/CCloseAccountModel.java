package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AOrganizationAccountModel;

public class CCloseAccountModel extends AOrganizationAccountModel {

    public CCloseAccountModel(COrganizationAccountModel organizationAccountModel) {
        super(organizationAccountModel);
    }

    @Override
    public void closeAccountEvent() {
        super.closeAccountEvent();
    }

    @Override
    public void entry() {

    }

    @Override
    public void exit() {

    }

    @Override
    public String getState() {
        return "Activated Organization Account State";
    }
}
