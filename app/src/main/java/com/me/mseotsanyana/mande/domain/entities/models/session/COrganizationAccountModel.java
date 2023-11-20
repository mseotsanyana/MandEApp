package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.interfaces.IOrganizationAccountState;

import java.util.Date;

public class COrganizationAccountModel {
    IOrganizationAccountState activeState;

    // general attributes
    private String userAccountServerID;
    private String userServerID;
    private String organizationServerID;
    private String workspaceServerID;
    private String planServerID;

    // privilege attributes
    private String userOwnerID;
    private String organizationOwnerID;
    private int workspaceOwnerBIT;
    private int unixpermBITS;
    private int statusBIT;

    // date attributes
    private Date createdDate;
    private Date modifiedDate;


    public IOrganizationAccountState getActiveState() {
        return activeState;
    }

    public void setActiveState(IOrganizationAccountState activeState) {
        this.activeState = activeState;
    }

    // events
    private void loginEvent(String email, String password){
        activeState.signInEvent(email, password);
    }

    private void approveAccountEvent(){
        activeState.approveAccountEvent();
    }
}
