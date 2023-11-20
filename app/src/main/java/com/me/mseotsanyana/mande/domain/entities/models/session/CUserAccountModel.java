package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.models.utils.CCommonAttributeModel;

import java.util.Date;
import java.util.Objects;

public class CUserAccountModel {
    private String userAccountServerID;

    private String organizationServerID;
    private String workspaceServerID;
    private String userServerID;
    private String planServerID;
    private boolean currentOrganization;

    private String userOwnerID;
    private String organizationOwnerID;
    private int workspaceOwnerBIT;
    private int unixpermBITS;
    private int statusBIT;

    private Date createdDate;
    private Date modifiedDate;

    private int numUserAccounts;

    public CUserAccountModel() {
    }

    public CUserAccountModel(String userAccountServerID, String organizationServerID,
                             String workspaceServerID, String userServerID, String planServerID,
                             boolean currentOrganization) {
        this.userAccountServerID = userAccountServerID;
        this.userServerID = userServerID;
        this.workspaceServerID = workspaceServerID;
        this.organizationServerID = organizationServerID;
        this.planServerID = planServerID;
        this.currentOrganization = currentOrganization;
    }

    public CUserAccountModel(String organizationServerID, String userServerID,
                              String teamServerID, String planServerID,
                              CCommonAttributeModel commonAttributeModel) {

        this.setUserAccountServerID(organizationServerID + "_" + userServerID);
        this.setOrganizationServerID(organizationServerID);
        this.setWorkspaceServerID(teamServerID);
        this.setUserServerID(userServerID);
        this.setPlanServerID(planServerID);
        this.setCurrentOrganization(true);

        // current date
        Date currentDate = new Date();
        this.setCreatedDate(currentDate);
        this.setModifiedDate(currentDate);

        // update user account model with default values
        this.setUserOwnerID(userServerID);
        this.setOrganizationOwnerID(organizationServerID);
        this.setWorkspaceOwnerBIT(commonAttributeModel.getWorkspaceOwnerBIT());
        this.setUnixpermBITS(commonAttributeModel.getUnixpermBITS());
        this.setStatusBIT(commonAttributeModel.getStatusBIT());
    }

    public int getNumUserAccounts() {
        return numUserAccounts;
    }

    public void setNumUserAccounts(int numUserAccounts) {
        this.numUserAccounts = numUserAccounts;
    }

    public String getUserAccountServerID() {
        return userAccountServerID;
    }

    public void setUserAccountServerID(String userAccountServerID) {
        this.userAccountServerID = userAccountServerID;
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

    public String getUserServerID() {
        return userServerID;
    }

    public void setUserServerID(String userServerID) {
        this.userServerID = userServerID;
    }

    public String getPlanServerID() {
        return planServerID;
    }

    public void setPlanServerID(String planServerID) {
        this.planServerID = planServerID;
    }

    public boolean isCurrentOrganization() {
        return currentOrganization;
    }

    public void setCurrentOrganization(boolean currentOrganization) {
        this.currentOrganization = currentOrganization;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CUserAccountModel)) return false;
        CUserAccountModel that = (CUserAccountModel) o;
        return isCurrentOrganization() == that.isCurrentOrganization() &&
                getUserAccountServerID().equals(that.getUserAccountServerID()) &&
                getOrganizationServerID().equals(that.getOrganizationServerID()) &&
                getWorkspaceServerID().equals(that.getWorkspaceServerID()) &&
                getUserServerID().equals(that.getUserServerID()) &&
                getPlanServerID().equals(that.getPlanServerID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserAccountServerID(),
                getOrganizationServerID(), getWorkspaceServerID(),
                getUserServerID(), getPlanServerID(),
                isCurrentOrganization());
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
}
