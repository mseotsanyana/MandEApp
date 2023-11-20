package com.me.mseotsanyana.mande.application.repository.programme;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cLogFrameModel;

import java.util.List;

public interface iLogFrameRepository {
    void upLoadProgrammeFromExcel(String organizationServerID, String userServerID,
                                  int primaryTeamBIT,
                                  int statusBIT, String filePath,
                                  iUploadLogFrameCallback callback);

    interface iUploadLogFrameCallback {
        void onUploadLogFrameSucceeded(String msg);

        void onUploadLogFrameFailed(String msg);
    }

    void readLogFrames(String organizationServerID, String userServerID,
                       int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                       List<Integer> statusBITS, iReadLogFramesCallback callback);

    void readLogFrame(String organizationServerID, String userServerID,
                       int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                       List<Integer> statusBITS, String projectServerID,
                      iReadLogFramesCallback callback);

    interface iReadLogFramesCallback {
        void onReadLogFrameSucceeded(List<cLogFrameModel> logFrameModels);

        void onReadLogFrameFailed(String msg);
    }
    /* the create function of the LogFrame entity */

    boolean createLogFrameModel(cLogFrameModel logFrameModel);

    boolean createSubLogFrameModel(String logFrameID, cLogFrameModel logFrameModel);

    boolean updateLogFrameModel(cLogFrameModel logFrameModel);

    boolean deleteLogFrame(String logFrameID);

    void deleteLogFrames(String organizationServerID, String userServerID,
                         iDeleteLogFramesCallback callback);

    interface iDeleteLogFramesCallback {
        void onDeleteLogFramesSucceeded(String msg);

        void onDeleteLogFrameFailed(String msg);
    }

//    void filterComponents(List<String> logframe_ids, iFilterComponentsCallback callback);
//    interface iFilterComponentsCallback {
//        void onFilterComponentsSucceeded(Map<String, String> logframetree, int num_calls);
//        void onFilterComponentsFailed(String msg);
//    }
}
