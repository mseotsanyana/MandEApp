package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cProjectModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.io.File;
import java.util.List;

public interface iProjectPresenter extends iPresenter {
    interface View extends iBaseView {
        /* pass data from presenter to the view */
        void onClickProject(cProjectModel projectModel);
        void onClickCreateProject(cProjectModel projectModel);
        void onClickCreateSubProject(String projectID, cProjectModel projectModel);
        void onClickUpdateProject(int position, cProjectModel projectModel);
        void onClickDeleteProject(String projectID);
        void onClickDeleteProjects(List<String> project_ids);
        void onClickUploadProject(File excelFile);

        void onCreateProjectCompleted(cProjectModel projectModel, String msg);
        void onCreateProjectFailed(String msg);

        /* pass data from interactor to the view */
        void onReadProjectsCompleted(List<cTreeModel> treeModels);
        void onReadProjectsFailed(String msg);

        void onProjectUpdated(cProjectModel projectModel, int position, String msg);
        void onProjectUpdateFailed(String msg);

        void onDeleteProjectsSucceeded(String msg);
        void onDeleteProjectsFailed(String msg);

        void onUploadProjectCompleted(String msg);
        void onUploadProjectFailed(String msg);
     }

    /* pass data from view to the interactor */
    void createProjectModel(cProjectModel projectModel);
    void createSubProjectModel(String ProjectID, cProjectModel subProjectModel);
    void readProjects();
    void updateProject(cProjectModel projectModel, int position);
    void deleteProjectModel(String projectID);
    void deleteProjectModels();
    void uploadProjectFromExcel(String filePath);
}