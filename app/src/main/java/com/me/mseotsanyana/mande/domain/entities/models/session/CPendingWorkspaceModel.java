package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AWorkspaceModel;
import com.me.mseotsanyana.mande.application.structures.CConstantModel;

public class CPendingWorkspaceModel extends AWorkspaceModel {

    public CPendingWorkspaceModel(CWorkspaceModel workspaceModel) {
        super(workspaceModel);
    }

    @Override
    public void acceptWorkspaceEvent() {
        workspaceModel.setStatusBIT(CConstantModel.ACTIVATED_STATE);
        workspaceModel.setActiveState(new CActivatedWorkspaceModel(workspaceModel));
    }

    @Override
    public void rejectWorkspaceEvent() {
        workspaceModel.setStatusBIT(CConstantModel.REJECTED_STATE);
        workspaceModel.setActiveState(new CDeletedWorkspaceModel(workspaceModel));
    }
}
