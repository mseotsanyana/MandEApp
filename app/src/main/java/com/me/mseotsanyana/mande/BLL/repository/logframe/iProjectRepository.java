package com.me.mseotsanyana.mande.BLL.repository.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cProjectModel;

import java.util.List;

public interface iProjectRepository {
    void upLoadProjectsFromExcel(String organizationServerID, String userServerID,
                                 int primaryTeamBIT, int statusBIT, String filePath,
                                 iUploadProjectCallback callback);

    interface iUploadProjectCallback {
        void onUploadProjectSucceeded(String msg);

        void onUploadProjectFailed(String msg);
    }

    void readProjects(String organizationServerID, String userServerID,
                      int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                      List<Integer> statusBITS, iReadProjectsCallback callback);

    interface iReadProjectsCallback {
        void onReadProjectSucceeded(List<cProjectModel> ProjectModels);

        void onReadProjectFailed(String msg);
    }

    void deleteProjects(String organizationServerID, String userServerID,
                        iDeleteProjectsCallback callback);

    interface iDeleteProjectsCallback {
        void onDeleteProjectsSucceeded(String msg);

        void onDeleteProjectFailed(String msg);
    }

}
