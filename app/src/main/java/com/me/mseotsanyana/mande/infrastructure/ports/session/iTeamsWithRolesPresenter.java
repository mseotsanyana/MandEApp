package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iTeamsWithRolesPresenter extends iPresenter {
    interface View extends IBaseView {
        void onCreateTeamFailed(String msg);
        void onCreateTeamSucceeded(String msg);

        void onReadTeamsWithRolesFailed(String msg);
        void onReadTeamsWithRolesSucceeded (List<cTreeModel> teamsRolesTree);

        /*EditText getNameEditText();
        EditText getEmailEditText();
        EditText getWebsiteEditText();

        String getResourceString(int resourceID);*/
    }

    void readTeamsWithRoles();
}

