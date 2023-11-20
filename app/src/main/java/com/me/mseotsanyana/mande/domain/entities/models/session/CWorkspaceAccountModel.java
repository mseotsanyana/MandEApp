package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.interfaces.IWorkspaceAccountState;

import java.util.Date;

public class CWorkspaceAccountModel {
    IWorkspaceAccountState activeState;

    // general attributes
    private String userAccountServerID;
    private String userServerID;
    private String organizationServerID;
    private String workspaceServerID;
    private String workspaceMemberServerID;

    // privilege attributes
    private String userOwnerID;
    private String organizationOwnerID;
    private int workspaceOwnerBIT;
    private int unixpermBITS;
    private int statusBIT;

    // date attributes
    private Date createdDate;
    private Date modifiedDate;


    public String getUserAccountServerID() {
        return userAccountServerID;
    }

    public void setUserAccountServerID(String userAccountServerID) {
        this.userAccountServerID = userAccountServerID;
    }

    public String getUserServerID() {
        return userServerID;
    }

    public void setUserServerID(String userServerID) {
        this.userServerID = userServerID;
    }

    public String getOrganizationServerID() {
        return organizationServerID;
    }

    public void setOrganizationServerID(String organizationServerID) {
        this.organizationServerID = organizationServerID;
    }

    public String getWorkspaceServerID() {
        return workspaceServerID;
    }

    public void setWorkspaceServerID(String workspaceServerID) {
        this.workspaceServerID = workspaceServerID;
    }

    public String getWorkspaceMemberServerID() {
        return workspaceMemberServerID;
    }

    public void setWorkspaceMemberServerID(String workspaceMemberServerID) {
        this.workspaceMemberServerID = workspaceMemberServerID;
    }

    public String getUserOwnerID() {
        return userOwnerID;
    }

    public void setUserOwnerID(String userOwnerID) {
        this.userOwnerID = userOwnerID;
    }

    public String getOrganizationOwnerID() {
        return organizationOwnerID;
    }

    public void setOrganizationOwnerID(String organizationOwnerID) {
        this.organizationOwnerID = organizationOwnerID;
    }

    public int getWorkspaceOwnerBIT() {
        return workspaceOwnerBIT;
    }

    public void setWorkspaceOwnerBIT(int workspaceOwnerBIT) {
        this.workspaceOwnerBIT = workspaceOwnerBIT;
    }

    public int getUnixpermBITS() {
        return unixpermBITS;
    }

    public void setUnixpermBITS(int unixpermBITS) {
        this.unixpermBITS = unixpermBITS;
    }

    public int getStatusBIT() {
        return statusBIT;
    }

    public void setStatusBIT(int statusBIT) {
        this.statusBIT = statusBIT;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public IWorkspaceAccountState getActiveState() {
        return activeState;
    }

    public void setActiveState(IWorkspaceAccountState activeState) {
        this.activeState = activeState;
    }

}
