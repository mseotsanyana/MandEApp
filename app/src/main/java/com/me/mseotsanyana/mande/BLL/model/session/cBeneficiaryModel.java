package com.me.mseotsanyana.mande.BLL.model.session;

public class cBeneficiaryModel extends cStakeholderModel {
    private String organizationServerID;

    public String getStakeholderServerID() {
        return organizationServerID;
    }

    public void setStakeholderServerID(String stakeholderServerID) {
        this.organizationServerID = stakeholderServerID;
    }
}
