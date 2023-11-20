package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AOrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.interfaces.IActivatedOrganizationState;

public class CActivatedOrganizationModel extends AOrganizationModel {
    IActivatedOrganizationState activeModeState;

    public CActivatedOrganizationModel(COrganizationModel organizationModel) {
        super(organizationModel);
    }

    @Override
    public void deactivateOrganizationEvent() {
        super.deactivateOrganizationEvent();
    }

    @Override
    public void deleteOrganizationEvent() {
        super.deleteOrganizationEvent();
    }
}
