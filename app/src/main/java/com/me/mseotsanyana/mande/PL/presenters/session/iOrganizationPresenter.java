package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.BLL.entities.models.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cWorkspaceModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iOrganizationPresenter extends iPresenter {

    interface View extends iBaseView {
        void onClickCreateOrganization();
        void onLongClickWorkspace(cWorkspaceModel workspaceModel);

        void onCreateOrganizationFailed(String msg);
        void onCreateOrganizationSucceeded(String msg);
        void onReadOrganizationFailed(String msg);
        void onReadOrganizationSucceeded(cOrganizationModel organizationModel, String operation);
        void onReadOrganizationsFailed(String msg);
        void onReadOrganizationsSucceeded(List<cTreeModel> treeModels);

        void onSwitchOrganizationWorkspaceFailed(String msg);
        void onSwitchOrganizationWorkspaceSucceeded(String msg);


        void onUserSignOutFailed(String msg);
        void onUserSignOutSucceeded(String msg);


        void onReadOrganizationMembersFailed(String msg);
        void onReadOrganizationMembersSucceeded(List<CUserProfileModel> userProfileModels);

        /*EditText getNameEditText();
        EditText getEmailEditText();
        EditText getWebsiteEditText();

        String getResourceString(int resourceID);*/
    }

    void signOutWithEmailAndPassword();

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void createOrganization(cOrganizationModel organizationModel);

    void readOrganizationWorkspaces();
    //void readOrganizationMembers();
    void switchOrganizationWorkspace(cWorkspaceModel workspaceModel);

    void removeListener();
}

