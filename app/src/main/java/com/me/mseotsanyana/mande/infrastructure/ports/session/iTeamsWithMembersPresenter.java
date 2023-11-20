package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iTeamsWithMembersPresenter extends iPresenter {
    interface View extends IBaseView {
        void onClickCreateTeam();

        void onCreateTeamFailed(String msg);
        void onCreateTeamSucceeded(String msg);

        void onReadTeamsWithMembersFailed(String msg);
        void onReadTeamsWithMembersSucceeded (List<cTreeModel> teamsMembersTree);
        //void onReadTeamsSucceeded (List<cTeamModel> teamsMembersTree);

        /*EditText getNameEditText();
        EditText getEmailEditText();
        EditText getWebsiteEditText();

        String getResourceString(int resourceID);*/
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void createTeam(CWorkspaceModel teamModel);

    void readTeamsWithMembers();
    //void readTeams();
}

