package com.me.mseotsanyana.mande.framework.ports;

import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;

public interface IOrganizationWorkspaceListener {
    void OnCreateOrganization(Object object);
    void OnUpdateOrganization(Object object);
    void OnDeleteOrganization(String dialogAction);
    void OnCreateWorkspace(CWorkspaceModel workspaceModel);
    void OnUpdateWorkspace(Object object);
    void OnDeleteWorkspace(String dialogAction, int workspaceBITS, CWorkspaceModel workspaceModel);
}
