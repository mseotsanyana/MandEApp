package com.me.mseotsanyana.mande.domain.entities.models.utils;

public class cCommonPropertiesModel {
    private String ownerID;
    private String orgOwnerID;
    private int workspaceOwnerBIT;
    private int unixpermBITS;
    private int statusBIT;

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getOrgOwnerID() {
        return orgOwnerID;
    }

    public void setOrgOwnerID(String orgOwnerID) {
        this.orgOwnerID = orgOwnerID;
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
}
