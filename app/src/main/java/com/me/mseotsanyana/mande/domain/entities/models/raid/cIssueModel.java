package com.me.mseotsanyana.mande.domain.entities.models.raid;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cRaidModel;

public class cIssueModel extends cRaidModel {
    private cIssueRegisterModel issueRegisterModel;

    public cIssueModel(){
        issueRegisterModel = new cIssueRegisterModel();
    }

    public cIssueRegisterModel getIssueRegisterModel() {
        return issueRegisterModel;
    }

    public void setIssueRegisterModel(cIssueRegisterModel issueRegisterModel) {
        this.issueRegisterModel = issueRegisterModel;
    }
}
