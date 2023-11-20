package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AOrganizationModel;
import com.me.mseotsanyana.mande.application.structures.CConstantModel;

public class CInitOrganizationModel extends AOrganizationModel {

    public CInitOrganizationModel(COrganizationModel organizationModel) {
        super(organizationModel);
    }

    @Override
    public void createOrganizationEvent() {
        organizationModel.setStatusBIT(CConstantModel.ACTIVATED_STATE);
        organizationModel.setActiveState(new CActivatedOrganizationModel(organizationModel));
    }
}
