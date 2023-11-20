package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IController;
import com.me.mseotsanyana.mande.infrastructure.utils.responsemodel.CTreeModel;

public interface IOrganizationWorkspaceController extends IController {
    interface IViewModel extends IBaseView {
        /******************** methods sending data from presenter to view model ********************
         * @param response response
         **/

        void onShowTreeModel(IResponseDTO<CTreeModel> response);
        //void onUpdateTreeModels(IResponseDTO<String> response);

        void switchResponse(String response);

        /******************** methods sending data from adapter to view model *********************/

        void onClickCreateOrganization();

        void onLongClickWorkspace(CWorkspaceModel workspaceModel);

        void onClickUpdateOrganization(COrganizationModel organizationModel);

        void onClickDeleteOrganization(String organizationServerID);

        void onClickReadOrganizationWorkspaces(String organizationServerID);

        void onClickCreateWorkspace(CWorkspaceModel workspaceModel);

        void onClickDeleteWorkspace(int workspaceBITS, CWorkspaceModel workspaceModel);

        void onClickUpdateWorkspace(CWorkspaceModel workspaceModel);

        /******************** methods sending data from fragment to view model ********************/

        void onResumeView();

        void onViewCreated();

        void onRemoveListener();

        void onEmailUnverified();

        void onUserLogOut();

        void onUserLoggedOut();

        void onSearchAdapter(String query);
    }

    /********************* methods sending data from view model to controller *********************/

    void createOrganization(COrganizationModel organizationModel);

    void readOrganizations();

    void updateOrganization(COrganizationModel organizationModel);

    void deleteOrganization(String organizationServerID);

    void createWorkspace(CWorkspaceModel workspaceModel);

    void readWorkspaces(String organizationServerID);

    void deleteWorkspace(int workspaceBITS, CWorkspaceModel workspaceModel);

    void updateWorkspace(CWorkspaceModel workspaceModel);

    void chooseWorkspaceRequest(CWorkspaceModel workspaceModel);

    void signOutWithEmailAndPassword();

    void removeListener();
}
