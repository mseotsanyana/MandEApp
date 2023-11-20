package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.abstracts.session.AWorkspaceAccountModel;
import com.me.mseotsanyana.mande.usecases.utils.CConstantModel;

public class CBlockedWorkspaceAccountModel extends AWorkspaceAccountModel {

    public CBlockedWorkspaceAccountModel(CWorkspaceAccountModel workspaceAccountModel) {
        super(workspaceAccountModel);
    }

    @Override
    public void activateAccountEvent() {
        workspaceAccountModel.setStatusBIT(CConstantModel.ACTIVATED_STATE);
        workspaceAccountModel.setActiveState(
                new CActivatedWorkspaceAccountModel(workspaceAccountModel));
    }

    @Override
    public void deleteAccountEvent() {
        workspaceAccountModel.setStatusBIT(CConstantModel.DELETED_STATE);
        workspaceAccountModel.setActiveState(
                new CDeletedWorkspaceAccountModel(workspaceAccountModel));
    }

}
