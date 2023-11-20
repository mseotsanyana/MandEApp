package com.me.mseotsanyana.mande.domain.entities.models.session;

public class CPartnerModel extends CBenefactorModel {
    String organizationServerID;

    @Override
    public String getOrganizationServerID() {
        return organizationServerID;
    }

    @Override
    public void setOrganizationServerID(String organizationServerID) {
        this.organizationServerID = organizationServerID;
    }
}
