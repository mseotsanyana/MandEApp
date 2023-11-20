package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CPrivilegeModel;
import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

import java.util.List;

public interface iRolePresenter extends iPresenter {
    interface View extends IBaseView {
        void onReadRolesFailed(String msg);
        void onReadRolesSucceeded(List<CPrivilegeModel> roleModels);
    }
    void readRoles();
}

