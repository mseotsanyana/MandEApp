package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AWorkspaceAccountModel;

public class CPendingWorkspaceAccountModel extends AWorkspaceAccountModel {

    public CPendingWorkspaceAccountModel(CWorkspaceAccountModel workspaceAccountModel) {
        super(workspaceAccountModel);
    }




//    @Override
//    public void acceptWorkspaceEvent() {
//        workspaceModel.setStatusBIT(CConstantModel.ACTIVATED_STATE);
//        workspaceModel.setActiveState(new CActivatedWorkspaceModel(workspaceModel));
//    }
//
//    @Override
//    public void rejectWorkspaceEvent() {
//        workspaceModel.setStatusBIT(CConstantModel.REJECTED_STATE);
//        workspaceModel.setActiveState(new CDeletedWorkspaceModel(workspaceModel));
//    }
}
