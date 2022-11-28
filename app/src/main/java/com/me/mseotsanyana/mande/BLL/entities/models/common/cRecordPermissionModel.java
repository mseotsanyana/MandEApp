package com.me.mseotsanyana.mande.BLL.entities.models.common;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.me.mseotsanyana.mande.DAL.storage.preference.cEntityType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class cRecordPermissionModel implements Parcelable {
    // common attributes
    private String userOwnerID;
    private String organizationOwnerID;
    private int teamOwnerBIT;
    private int unixpermBITS;
    private int statusBIT;

    private Date createdDate;
    private Date modifiedDate;

    private cEntityType entityIdentity;

    public cRecordPermissionModel() {
    }

    public cRecordPermissionMementoModel createMemento(){
        return new cRecordPermissionMementoModel(
                userOwnerID,
                organizationOwnerID,
                teamOwnerBIT,
                unixpermBITS,
                statusBIT,
                createdDate,
                modifiedDate);
    }

    public void restoreMemento(@NonNull cRecordPermissionMementoModel commonMemento){
        this.userOwnerID = commonMemento.getUserOwnerID();
        this.organizationOwnerID = commonMemento.getOrganizationOwnerID();
        this.teamOwnerBIT = commonMemento.getTeamOwnerBIT();
        this.unixpermBITS = commonMemento.getUnixpermBITS();
        this.statusBIT = commonMemento.getStatusBIT();
        this.createdDate = commonMemento.getCreatedDate();
        this.modifiedDate = commonMemento.getModifiedDate();
    }

    public static final Creator<cRecordPermissionModel> CREATOR = new Creator<cRecordPermissionModel>() {
        @Override
        public cRecordPermissionModel createFromParcel(Parcel in) {
            return new cRecordPermissionModel(in);
        }

        @Override
        public cRecordPermissionModel[] newArray(int size) {
            return new cRecordPermissionModel[size];
        }
    };

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

    public cEntityType getEntityIdentity() {
        return entityIdentity;
    }

    public void setEntityIdentity(cEntityType entityIdentity) {
        this.entityIdentity = entityIdentity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected cRecordPermissionModel(Parcel in) {
        userOwnerID = in.readString();
        organizationOwnerID = in.readString();
        teamOwnerBIT = in.readInt();
        statusBIT = in.readInt();
        unixpermBITS = in.readInt();
        createdDate = new Date(in.readLong());
        modifiedDate = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userOwnerID);
        parcel.writeString(organizationOwnerID);
        parcel.writeInt(teamOwnerBIT);
        parcel.writeInt(statusBIT);
        parcel.writeInt(unixpermBITS);
        parcel.writeLong(createdDate.getTime());
        parcel.writeLong(modifiedDate.getTime());
    }
}
