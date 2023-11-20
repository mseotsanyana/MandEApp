package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AWorkspaceModel;

public class CInitWorkspaceModel extends AWorkspaceModel {

    public CInitWorkspaceModel(CWorkspaceModel workspaceModel) {
        super(workspaceModel);
    }

    @Override
    public void createWorkspaceEvent() {
        workspaceModel.setActiveState(new CPendingWorkspaceModel(workspaceModel));
    }
}
