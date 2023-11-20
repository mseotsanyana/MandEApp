package com.me.mseotsanyana.mande.application.interactors.session.role;

import com.me.mseotsanyana.mande.domain.entities.models.session.cPrivilegeModel;
import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

import java.util.List;

public interface iReadTeamRolesInteractor extends iInteractor {
    interface Callback {
        void onReadTeamRolesFailed(String msg);
        void onReadTeamRolesSucceeded(List<cPrivilegeModel> roleModels);
    }
}
