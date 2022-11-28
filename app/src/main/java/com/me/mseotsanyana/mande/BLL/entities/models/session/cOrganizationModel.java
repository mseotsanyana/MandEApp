package com.me.mseotsanyana.mande.BLL.entities.models.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class cOrganizationModel {
    private String organizationServerID;

    private String userOwnerID;
    private String organizationOwnerID;
    private int workspaceOwnerBIT;
    private int unixpermBITS;
    private int statusBIT;

    private String name;
    private String email;
    private String website;
    private Date createdDate;
    private Date modifiedDate;

    private int numStakeholders;

    private int typeID;
    private String type;

    List<cUserModel> userModels;
    List<cOrganizationModel> childOrganizationModels;

    public cOrganizationModel(){
        userModels = new ArrayList<>();
        childOrganizationModels = new ArrayList<>();
    }

    public cOrganizationModel(int typeID, String name, String email,
                              String website){
        this.typeID = typeID;
        this.name = name;
        this.email = email;
        this.website = website;
    }

    public cOrganizationModel(cOrganizationModel organizationModel){
        this.setOrganizationServerID(organizationModel.getOrganizationServerID());
        this.setUserOwnerID(organizationModel.getUserOwnerID());
        this.setOrganizationOwnerID(organizationModel.getOrganizationOwnerID());
        this.setWorkspaceOwnerBIT(organizationModel.getWorkspaceOwnerBIT());
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

    public String getOrganizationServerID() {
        return organizationServerID;
    }

    public void setOrganizationServerID(String organizationServerID) {
        this.organizationServerID = organizationServerID;
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

    public List<cOrganizationModel> getChildOrganizationModels() {
        return childOrganizationModels;
    }

    public void setChildOrganizationModels(List<cOrganizationModel> childOrganizationModels) {
        this.childOrganizationModels = childOrganizationModels;
    }

    public List<cUserModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(List<cUserModel> userModels) {
        this.userModels = userModels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cOrganizationModel)) return false;
        cOrganizationModel that = (cOrganizationModel) o;
        return getWorkspaceOwnerBIT() == that.getWorkspaceOwnerBIT() &&
                getStatusBIT() == that.getStatusBIT() &&
                getTypeID() == that.getTypeID() &&
                getNumStakeholders() == that.getNumStakeholders() &&
                Objects.equals(getOrganizationServerID(), that.getOrganizationServerID()) &&
                Objects.equals(getUserOwnerID(), that.getUserOwnerID()) &&
                Objects.equals(getOrganizationOwnerID(), that.getOrganizationOwnerID()) &&
                Objects.equals(getUnixpermBITS(), that.getUnixpermBITS()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getWebsite(), that.getWebsite()) &&
                Objects.equals(getCreatedDate(), that.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), that.getModifiedDate()) &&
                Objects.equals(getUserModels(), that.getUserModels());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrganizationServerID(), getUserOwnerID(), getOrganizationOwnerID(),
                getWorkspaceOwnerBIT(), getUnixpermBITS(), getStatusBIT(), getName(), getEmail(),
                getWebsite(), getCreatedDate(), getModifiedDate(), getTypeID(),
                getNumStakeholders(), getUserModels());
    }
}
