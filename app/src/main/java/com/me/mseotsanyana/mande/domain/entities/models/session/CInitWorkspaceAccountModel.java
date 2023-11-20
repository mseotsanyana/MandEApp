package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AWorkspaceAccountModel;
import com.me.mseotsanyana.mande.usecases.utils.CConstantModel;

public class CInitWorkspaceAccountModel extends AWorkspaceAccountModel {

    public CInitWorkspaceAccountModel(CWorkspaceAccountModel workspaceAccountModel) {
        super(workspaceAccountModel);
    }

    @Override
    public void createAccountEvent(int requestType) {
        if (requestType == CConstantModel.ADD_REQUEST){
            workspaceAccountModel.setStatusBIT(CConstantModel.ACTIVATED_STATE);
            workspaceAccountModel.setActiveState(
                    new CActivatedWorkspaceAccountModel(workspaceAccountModel));
        }
        else if((requestType == CConstantModel.JOIN_REQUEST) ||
                (requestType == CConstantModel.INVITE_REQUEST)){
            workspaceAccountModel.setStatusBIT(CConstantModel.PENDING_STATE);
            workspaceAccountModel.setActiveState(
                    new CPendingWorkspaceAccountModel(workspaceAccountModel));
        }
    }
}
