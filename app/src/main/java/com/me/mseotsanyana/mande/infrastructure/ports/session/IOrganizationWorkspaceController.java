package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.framework.ports.base.IBaseView;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IController;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface IOrganizationController extends IController{
    interface IViewModel extends IBaseView {
        //void onClickCreateOrganization();
        //void onLongClickWorkspace(CWorkspaceModel workspaceModel);

        void onCreateOrganizationFailed(String msg);
        void onCreateOrganizationSucceeded(String msg);
        void onReadOrganizationFailed(String msg);
        void onReadOrganizationSucceeded(COrganizationModel organizationModel, String operation);
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
    void removeListener();

    //void signOutWithEmailAndPassword();

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void createOrganization(COrganizationModel organizationModel);

    void readOrganizationWorkspaces();
    //void readOrganizationMembers();
    //void switchOrganizationWorkspace(CWorkspaceModel workspaceModel);
}
