package com.me.mseotsanyana.mande.application.interactors.programme.project;

import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cProjectModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iProjectInteractor extends IInteractor {
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
        void onReadTeamsSucceeded(List<CWorkspaceModel> teamModels);
        void onReadStakeholdersSucceeded(List<COrganizationModel> stakeholderModels);
        void onReadCommonPropertiesFailed(String msg);
    }
}
