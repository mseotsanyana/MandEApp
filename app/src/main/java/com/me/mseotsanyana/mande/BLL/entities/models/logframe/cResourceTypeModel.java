package com.me.mseotsanyana.mande.BLL.entities.models.logframe;

import java.util.Date;
import java.util.List;

public class cResourceTypeModel {
    private String resourceTypeServerID;

    // common attributes
    private String userOwnerID;
    private String organizationOwnerID;
    private int teamOwnerBIT;
    private int unixpermBITS;
    private int statusBIT;

    // meta attributes
    private String name;
    private String description;
    private Date createdDate;
    private Date modifiedDate;

    public String getResourceTypeServerID() {
        return resourceTypeServerID;
    }

    public void setResourceTypeServerID(String resourceTypeServerID) {
        this.resourceTypeServerID = resourceTypeServerID;
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

    public int getTeamOwnerBIT() {
        return teamOwnerBIT;
    }

    public void setTeamOwnerBIT(int teamOwnerBIT) {
        this.teamOwnerBIT = teamOwnerBIT;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
