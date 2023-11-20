package com.me.mseotsanyana.mande.domain.entities.models.session;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cPrivilegeModel {
    private String privilegeServerID;

    private String userOwnerID;
    private String organizationOwnerID;
    private int workspaceOwnerBIT;
    private int unixpermBITS;
    private int statusBIT;

    private String name;
    private String description;

    private Date createdDate;
    private Date modifiedDate;

    private Map<String, List<Integer>> menuitems;
    private Map<String, List<cEntityModel>> modules;

    public cPrivilegeModel(){
    }

    public cPrivilegeModel(cPrivilegeModel privilegeModel){
        this.setPrivilegeServerID(privilegeModel.getPrivilegeServerID());
        this.setUserOwnerID(privilegeModel.getUserOwnerID());
        this.setOrganizationOwnerID(privilegeModel.getOrganizationOwnerID());
        this.setWorkspaceOwnerBIT(privilegeModel.getWorkspaceOwnerBIT());
        this.setUnixpermBITS(privilegeModel.getUnixpermBITS());
        this.setStatusBIT(privilegeModel.getStatusBIT());

        this.setName(privilegeModel.getName());
        this.setDescription(privilegeModel.getDescription());

        this.setCreatedDate(privilegeModel.getCreatedDate());
        this.setModifiedDate(privilegeModel.getModifiedDate());

        this.setMenuitems(privilegeModel.getMenuitems());
        this.setModules(privilegeModel.getModules());

    }

    public String getPrivilegeServerID() {
        return privilegeServerID;
    }

    public void setPrivilegeServerID(String privilegeServerID) {
        this.privilegeServerID = privilegeServerID;
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

    public void setUnixpermBITS(int unixpermBITS) {
        this.unixpermBITS = unixpermBITS;
    }

    public int getUnixpermBITS() {
        return unixpermBITS;
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

    public Map<String, List<Integer>> getMenuitems() {
        return menuitems;
    }

    public void setMenuitems(Map<String, List<Integer>> menuitems) {
        this.menuitems = menuitems;
    }

    public Map<String, List<cEntityModel>> getModules() {
        return modules;
    }

    public void setModules(Map<String, List<cEntityModel>> modules) {
        this.modules = modules;
    }
}
