package com.me.mseotsanyana.mande.BLL.model.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class cRecordPermissionMementoModel {
    private final String userOwnerID;
    private final String organizationOwnerID;
    private final int teamOwnerBIT;
    private final List<Integer> unixpermBITS;
    private final int statusBIT;
    private final Date createdDate;
    private final Date modifiedDate;

    public cRecordPermissionMementoModel(String userOwnerID,
                                         String organizationOwnerID,
                                         int teamOwnerBIT,
                                         List<Integer> unixpermBITS,
                                         int statusBIT,
                                         Date createdDate,
                                         Date modifiedDate){
        this.userOwnerID = userOwnerID;
        this.organizationOwnerID = organizationOwnerID;
        this.teamOwnerBIT = teamOwnerBIT;
        this.statusBIT = statusBIT;
        this.unixpermBITS = new ArrayList<>(unixpermBITS);
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

    public List<Integer> getUnixpermBITS() {
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
