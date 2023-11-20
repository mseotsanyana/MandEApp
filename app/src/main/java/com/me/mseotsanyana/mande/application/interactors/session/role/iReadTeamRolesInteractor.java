package com.me.mseotsanyana.mande.application.interactors.session.role;

import com.me.mseotsanyana.mande.domain.entities.models.session.CPrivilegeModel;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

import java.util.List;

public interface iReadTeamRolesInteractor extends IInteractor {
    interface Callback {
        void onReadTeamRolesFailed(String msg);
        void onReadTeamRolesSucceeded(List<CPrivilegeModel> roleModels);
    }
}
