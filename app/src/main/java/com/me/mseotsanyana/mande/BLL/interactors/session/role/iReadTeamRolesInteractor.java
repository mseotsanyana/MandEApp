package com.me.mseotsanyana.mande.BLL.interactors.session.role;

import com.me.mseotsanyana.mande.BLL.entities.models.session.cPrivilegeModel;
import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

import java.util.List;

public interface iReadTeamRolesInteractor extends iInteractor {
    interface Callback {
        void onReadTeamRolesFailed(String msg);
        void onReadTeamRolesSucceeded(List<cPrivilegeModel> roleModels);
    }
}
