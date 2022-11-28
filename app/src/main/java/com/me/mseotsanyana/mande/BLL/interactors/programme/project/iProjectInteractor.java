package com.me.mseotsanyana.mande.BLL.interactors.programme.project;

import com.me.mseotsanyana.mande.BLL.entities.models.session.cWorkspaceModel;
import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cProjectModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iProjectInteractor extends iInteractor {
    interface Callback{
        // create
        void onCreateProjectCompleted(cProjectModel projectModel, String msg);
        void onCreateProjectFailed(String msg);

        // read
        void onReadAllProjectsSucceeded(List<cTreeModel> treeModels);
        void onReadAllProjectsFailed(String msg);

        // update
        void onUpdateProjectSucceeded(cProjectModel projectModel, int position, String msg);
        void onUpdateProjectSucceeded(String msg);
        void onUpdateProjectFailed(String msg);

        // delete
        void onDeleteProjectsSucceeded(String msg);
        void onDeleteProjectsFailed(String msg);

        // upload
        void onUploadProjectsCompleted(String msg);
        void onUploadProjectsFailed(String msg);

        // update common properties
        void onReadUserProfilesSucceeded(List<CUserProfileModel> userProfileModels);
        void onReadTeamsSucceeded(List<cWorkspaceModel> teamModels);
        void onReadStakeholdersSucceeded(List<cOrganizationModel> stakeholderModels);
        void onReadCommonPropertiesFailed(String msg);
    }
}
