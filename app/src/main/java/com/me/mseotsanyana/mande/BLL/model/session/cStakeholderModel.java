package com.me.mseotsanyana.mande.BLL.model.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class cStakeholderModel implements Parcelable {
    private String stakeholderServerID;

    private String userOwnerID;
    private String stakeholderOwnerID;
    private int teamOwnerBIT;
    private List<Integer> unixpermBITS;
    private int statusBIT;

    private String name;
    private String email;
    private String website;
    private Date createdDate;
    private Date modifiedDate;

    private int numStakeholders;

    private int typeID;
    private String type;

    Set<cUserModel> userModelSet;
    Set<cRoleModel> roleModelSet;

    public cStakeholderModel(){}

    public cStakeholderModel(int typeID, String name, String email,
                             String website){
        this.typeID = typeID;
        this.name = name;
        this.email = email;
        this.website = website;
    }

    public cStakeholderModel(cStakeholderModel organizationModel){
        this.setStakeholderServerID(organizationModel.getStakeholderServerID());
        this.setUserOwnerID(organizationModel.getUserOwnerID());
        this.setStakeholderOwnerID(organizationModel.getStakeholderOwnerID());
        this.setTeamOwnerBIT(organizationModel.getTeamOwnerBIT());
        this.setUnixpermBITS(organizationModel.getUnixpermBITS());
        this.setStatusBIT(organizationModel.getStatusBIT());
        this.setTypeID(organizationModel.getTypeID());
        this.setType(organizationModel.getType());
        this.setName(organizationModel.getName());
        this.setEmail(organizationModel.getEmail());
        this.setWebsite(organizationModel.getWebsite());
        this.setCreatedDate(organizationModel.getCreatedDate());
        this.setModifiedDate(organizationModel.getModifiedDate());
    }

    protected cStakeholderModel(Parcel in) {
        stakeholderServerID = in.readString();
        userOwnerID = in.readString();
        stakeholderOwnerID = in.readString();
        teamOwnerBIT = in.readInt();
        //unixpermsBITS = in.readInt();
        statusBIT = in.readInt();
        type = in.readString();
        name = in.readString();
        email = in.readString();
        website = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stakeholderServerID);
        dest.writeString(userOwnerID);
        dest.writeString(stakeholderOwnerID);
        dest.writeInt(teamOwnerBIT);
        //dest.writeInt(unixpermsBITS);
        dest.writeInt(statusBIT);
        dest.writeInt(typeID);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(website);
    }

    @Exclude
    public String getStakeholderServerID() {
        return stakeholderServerID;
    }

    public void setStakeholderServerID(String stakeholderServerID) {
        this.stakeholderServerID = stakeholderServerID;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserOwnerID() {
        return userOwnerID;
    }

    public void setUserOwnerID(String userOwnerID) {
        this.userOwnerID = userOwnerID;
    }

    public String getStakeholderOwnerID() {
        return stakeholderOwnerID;
    }

    public void setStakeholderOwnerID(String stakeholderOwnerID) {
        this.stakeholderOwnerID = stakeholderOwnerID;
    }

    public int getTeamOwnerBIT() {
        return teamOwnerBIT;
    }

    public void setTeamOwnerBIT(int teamOwnerBIT) {
        this.teamOwnerBIT = teamOwnerBIT;
    }

    public List<Integer> getUnixpermBITS() {
        return unixpermBITS;
    }

    public void setUnixpermBITS(List<Integer> unixpermBITS) {
        this.unixpermBITS = unixpermBITS;
    }

    public int getStatusBIT() {
        return statusBIT;
    }

    public void setStatusBIT(int statusBIT) {
        this.statusBIT = statusBIT;
    }

    public int getNumStakeholders() {
        return numStakeholders;
    }

    public void setNumStakeholders(int numStakeholders) {
        this.numStakeholders = numStakeholders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    @Exclude
    public Set<cUserModel> getUserModelSet() {
        return userModelSet;
    }

    public void setUserModelSet(Set<cUserModel> userModelSet) {
        this.userModelSet = userModelSet;
    }

    @Exclude
    public Set<cRoleModel> getRoleModelSet() {
        return roleModelSet;
    }

    public void setRoleModelSet(Set<cRoleModel> roleModelSet) {
        this.roleModelSet = roleModelSet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cStakeholderModel> CREATOR = new Creator<cStakeholderModel>() {
        @Override
        public cStakeholderModel createFromParcel(Parcel in) {
            return new cStakeholderModel(in);
        }

        @Override
        public cStakeholderModel[] newArray(int size) {
            return new cStakeholderModel[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cStakeholderModel)) return false;
        cStakeholderModel that = (cStakeholderModel) o;
        return getTeamOwnerBIT() == that.getTeamOwnerBIT() &&
                getStatusBIT() == that.getStatusBIT() &&
                getTypeID() == that.getTypeID() &&
                getNumStakeholders() == that.getNumStakeholders() &&
                Objects.equals(getStakeholderServerID(), that.getStakeholderServerID()) &&
                Objects.equals(getUserOwnerID(), that.getUserOwnerID()) &&
                Objects.equals(getStakeholderOwnerID(), that.getStakeholderOwnerID()) &&
                Objects.equals(getUnixpermBITS(), that.getUnixpermBITS()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getWebsite(), that.getWebsite()) &&
                Objects.equals(getCreatedDate(), that.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), that.getModifiedDate()) &&
                Objects.equals(getUserModelSet(), that.getUserModelSet()) &&
                Objects.equals(getRoleModelSet(), that.getRoleModelSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStakeholderServerID(), getUserOwnerID(), getStakeholderOwnerID(),
                getTeamOwnerBIT(), getUnixpermBITS(), getStatusBIT(), getName(), getEmail(),
                getWebsite(), getCreatedDate(), getModifiedDate(), getTypeID(),
                getNumStakeholders(), getUserModelSet(), getRoleModelSet());
    }
}
