package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AWorkspaceModel;
import com.me.mseotsanyana.mande.application.structures.CConstantModel;

public class CActivatedWorkspaceModel extends AWorkspaceModel {

    public CActivatedWorkspaceModel(CWorkspaceModel workspaceModel) {
        super(workspaceModel);
    }

    @Override
    public void deactivateWorkspaceEvent() {
        workspaceModel.setStatusBIT(CConstantModel.BLOCKED_STATE);
        workspaceModel.setActiveState(new CBlockedWorkspaceModel(workspaceModel));
    }

    @Override
    public void deleteWorkspaceEvent() {
        workspaceModel.setStatusBIT(CConstantModel.DELETED_STATE);
        workspaceModel.setActiveState(new CDeletedWorkspaceModel(workspaceModel));
    }
}
