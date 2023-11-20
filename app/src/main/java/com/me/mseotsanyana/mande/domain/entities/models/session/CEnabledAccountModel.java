package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AOrganizationAccountModel;

public class CEnabledAccountModel extends AOrganizationAccountModel {

    public CEnabledAccountModel(COrganizationAccountModel organizationAccountModel) {
        super(organizationAccountModel);
    }

    @Override
    public void activateAccountEvent() {
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
        return "Pending Organization Account State";
    }
}
