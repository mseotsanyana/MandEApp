package com.me.mseotsanyana.mande.domain.entities.models.common;

import java.util.Date;

public class cRecordPermissionMementoModel {
    private final String userOwnerID;
    private final String organizationOwnerID;
    private final int teamOwnerBIT;
    private final int unixpermBITS;
    private final int statusBIT;
    private final Date createdDate;
    private final Date modifiedDate;

    public cRecordPermissionMementoModel(String userOwnerID,
                                         String organizationOwnerID,
                                         int teamOwnerBIT,
                                         int unixpermBITS,
                                         int statusBIT,
                                         Date createdDate,
                                         Date modifiedDate){
        this.userOwnerID = userOwnerID;
        this.organizationOwnerID = organizationOwnerID;
        this.teamOwnerBIT = teamOwnerBIT;
        this.statusBIT = statusBIT;
        this.unixpermBITS = unixpermBITS;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public String getUserOwnerID() {
        return userOwnerID;
    }

    public String getOrganizationOwnerID() {
        return organizationOwnerID;
    }

    public int getTeamOwnerBIT() {
        return teamOwnerBIT;
    }

    public int getUnixpermBITS() {
        return unixpermBITS;
    }

    public int getStatusBIT() {
        return statusBIT;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }
}
