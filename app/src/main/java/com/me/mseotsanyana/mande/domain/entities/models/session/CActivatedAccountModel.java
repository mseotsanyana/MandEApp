package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AOrganizationAccountModel;

public class CActivatedAccountModel extends AOrganizationAccountModel {

    public CActivatedAccountModel(COrganizationAccountModel organizationAccountModel) {
        super(organizationAccountModel);
    }

    @Override
    public void deactivateAccountEvent() {
        organizationAccountModel.setActiveState(new CEnabledAccountModel(organizationAccountModel));
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
