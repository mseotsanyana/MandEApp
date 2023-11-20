package com.me.mseotsanyana.mande.domain.entities.abstracts.session;

import com.me.mseotsanyana.mande.domain.entities.interfaces.IActivatedOrganizationState;
import com.me.mseotsanyana.mande.domain.entities.models.session.CActivatedOrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;

abstract public class AActivatedOrganizationModel implements IActivatedOrganizationState {
    protected COrganizationModel organizationModel;
    protected CActivatedOrganizationModel activatedOrganizationModel;

    public AActivatedOrganizationModel(CActivatedOrganizationModel activatedOrganizationModel) {
        this.activatedOrganizationModel = activatedOrganizationModel;
    }
}
