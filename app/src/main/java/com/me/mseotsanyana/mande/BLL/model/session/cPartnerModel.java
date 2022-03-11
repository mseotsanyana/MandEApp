package com.me.mseotsanyana.mande.BLL.model.session;

public class cPartnerModel extends cStakeholderModel {
    String organizationServerID;

    @Override
    public String getStakeholderServerID() {
        return organizationServerID;
    }

    @Override
    public void setStakeholderServerID(String stakeholderServerID) {
        this.organizationServerID = stakeholderServerID;
    }
}
