package com.me.mseotsanyana.mande.application.structures;

import java.util.List;

public class CDeleteWorkspaceDTO implements IOrganizationWorkspaceDTO {
    private String workspaceServerID;
    private List<String> workspaceMembers;

    public CDeleteWorkspaceDTO(String workspaceServerID, List<String> workspaceMembers){
        this.workspaceServerID = workspaceServerID;
        this.workspaceMembers = workspaceMembers;
    }

    public String getWorkspaceServerID() {
        return workspaceServerID;
    }

    public void setWorkspaceServerID(String workspaceServerID) {
        this.workspaceServerID = workspaceServerID;
    }

    public List<String> getWorkspaceMembers() {
        return workspaceMembers;
    }

    public void setWorkspaceMembers(List<String> workspaceMembers) {
        this.workspaceMembers = workspaceMembers;
    }
}
