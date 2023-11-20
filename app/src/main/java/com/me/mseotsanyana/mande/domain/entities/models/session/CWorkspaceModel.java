package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.interfaces.IWorkspaceState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mseotsanyana on 2021/03/18.
 */

public class CWorkspaceModel {
    private IWorkspaceState activeState;

    private String compositeServerID;
    private String workspaceServerID;
    private String organizationServerID;

    private String userOwnerID;
    private String organizationOwnerID;
    private int workspaceOwnerBIT;
    private int unixpermBITS;
    private int statusBIT;

    private String name;
    private String description;

    private Date createdDate;
    private Date modifiedDate;

    private List<String> workspaceMembers;

    public CWorkspaceModel(){
        workspaceMembers = new ArrayList<>();
    }

    public String getCompositeServerID() {
        return compositeServerID;
    }

    public void setCompositeServerID(String compositeServerID) {
        this.compositeServerID = compositeServerID;
    }

    public String getWorkspaceServerID() {
        return workspaceServerID;
    }

    public void setWorkspaceServerID(String workspaceServerID) {
        this.workspaceServerID = workspaceServerID;
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

    public String getOrganizationServerID() {
        return organizationServerID;
    }

    public void setOrganizationServerID(String organizationServerID) {
        this.organizationServerID = organizationServerID;
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

    public List<String> getWorkspaceMembers() {
        return workspaceMembers;
    }

    public void setWorkspaceMembers(List<String> workspaceMembers) {
        this.workspaceMembers = workspaceMembers;
    }

    public IWorkspaceState getActiveState() {
        return activeState;
    }

    public void setActiveState(IWorkspaceState activeState) {
        this.activeState = activeState;
    }

    void createWorkspaceEvent(){
        activeState.createWorkspaceEvent();
    }
    void acceptWorkspaceEvent(){
        activeState.acceptWorkspaceEvent();
    }
    void rejectWorkspaceEvent(){
        activeState.rejectWorkspaceEvent();
    }
    void activateWorkspaceEvent(){
        activeState.activateWorkspaceEvent();
    }
    void deactivateWorkspaceEvent(){
        activeState.deactivateWorkspaceEvent();
    }
    void deleteWorkspaceEvent(){
        activeState.deleteWorkspaceEvent();
    }

    @Override
    public String toString() {
        return "CWorkspaceModel{" +
                "activeState=" + activeState +
                ", compositeServerID='" + compositeServerID + '\'' +
                ", workspaceServerID='" + workspaceServerID + '\'' +
                ", organizationServerID='" + organizationServerID + '\'' +
                ", userOwnerID='" + userOwnerID + '\'' +
                ", organizationOwnerID='" + organizationOwnerID + '\'' +
                ", workspaceOwnerBIT=" + workspaceOwnerBIT +
                ", unixpermBITS=" + unixpermBITS +
                ", statusBIT=" + statusBIT +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", workspaceMembers=" + workspaceMembers +
                '}';
    }
}
