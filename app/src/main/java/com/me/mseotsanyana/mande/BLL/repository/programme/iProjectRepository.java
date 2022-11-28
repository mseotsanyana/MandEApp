package com.me.mseotsanyana.mande.BLL.repository.programme;

import com.me.mseotsanyana.mande.BLL.entities.models.common.cRecordPermissionModel;
import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cProjectModel;

import java.util.List;

public interface iProjectRepository {
    void upLoadProjectsFromExcel(String organizationServerID, String userServerID,
                                 int primaryTeamBIT, int statusBIT, String filePath,
                                 iUploadProjectsCallback callback);

    interface iUploadProjectsCallback {
        void onUploadProjectsSucceeded(String msg);

        void onUploadProjectsFailed(String msg);
    }

    void readProjects(String organizationServerID, String userServerID,
                      int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                      List<Integer> statusBITS, iReadProjectsCallback callback);

    void removeListener();

    interface iReadProjectsCallback {
        void onReadProjectsSucceeded(List<cProjectModel> ProjectModels);

        void onReadProjectFailed(String msg);
    }

    void deleteProjects(String organizationServerID, String userServerID,
                        iDeleteProjectsCallback callback);

    interface iDeleteProjectsCallback {
        void onDeleteProjectsSucceeded(String msg);

        void onDeleteProjectFailed(String msg);
    }


    void updateRecordPermissions(String projectServerID, cRecordPermissionModel recordPermission,
                                 iUpdateRecordPermissionsCallback callback);

    interface iUpdateRecordPermissionsCallback {
        void onUpdateRecordPermissionsSucceeded(String msg);

        void onUpdateRecordPermissionsFailed(String msg);
    }
}
