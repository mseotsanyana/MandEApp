package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AWorkspaceModel;
import com.me.mseotsanyana.mande.usecases.utils.CConstantModel;

public class CBlockedWorkspaceModel extends AWorkspaceModel {

    public CBlockedWorkspaceModel(CWorkspaceModel workspaceModel) {
        super(workspaceModel);
    }

    @Override
    public void activateWorkspaceEvent() {
        workspaceModel.setStatusBIT(CConstantModel.ACTIVATED_STATE);
        workspaceModel.setActiveState(new CActivatedWorkspaceModel(workspaceModel));
    }

    @Override
    public void deleteWorkspaceEvent() {
        workspaceModel.setStatusBIT(CConstantModel.DELETED_STATE);
        workspaceModel.setActiveState(new CDeletedWorkspaceModel(workspaceModel));
    }

}
