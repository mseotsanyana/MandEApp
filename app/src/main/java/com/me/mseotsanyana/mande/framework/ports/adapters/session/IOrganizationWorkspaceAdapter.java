package com.me.mseotsanyana.mande.framework.ports.adapters.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;

public interface IOrganizationWorkspaceAdapter {
    void onLongClickWorkspace(int position);
    void onClickOrganization(String organizationServerID, int position);
    void onClickUpdateOrganization(COrganizationModel organizationModel);
    void onClickDeleteOrganization(String organizationServerID);
    void onClickCreateWorkspace(String organizationServerID, int workspaceBITS, int position);
    void onClickDeleteWorkspace(int workspaceBITS, CWorkspaceModel workspaceModel);
    void onClickUpdateWorkspace(CWorkspaceModel workspaceModel);
}
