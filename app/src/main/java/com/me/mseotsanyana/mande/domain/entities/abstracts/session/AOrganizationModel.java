package com.me.mseotsanyana.mande.domain.entities.abstracts.session;

import com.me.mseotsanyana.mande.domain.entities.interfaces.IOrganizationState;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;

abstract public class AOrganizationModel implements IOrganizationState {
    protected COrganizationModel organizationModel;

    public AOrganizationModel(COrganizationModel organizationModel) {
        this.organizationModel = organizationModel;
    }

    @Override
    public void createOrganizationEvent() {}

    @Override
    public void activateOrganizationEvent() {}

    @Override
    public void deactivateOrganizationEvent() {
    }

    @Override
    public void deleteOrganizationEvent() {}
}
