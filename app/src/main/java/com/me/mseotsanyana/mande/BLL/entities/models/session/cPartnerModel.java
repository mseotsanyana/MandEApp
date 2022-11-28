package com.me.mseotsanyana.mande.BLL.entities.models.session;

public class cPartnerModel extends cOrganizationModel {
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
