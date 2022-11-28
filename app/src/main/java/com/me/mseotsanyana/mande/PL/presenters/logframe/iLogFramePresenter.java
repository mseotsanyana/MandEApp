package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.io.File;
import java.util.List;

public interface iLogFramePresenter extends iPresenter {
    interface View extends iBaseView {
        /* pass data from presenter to the view */
        void onClickBMBLogFrame(int menuIndex, cLogFrameModel logFrameModel);
        void onClickCreateLogFrame(cLogFrameModel logFrameModel);
        void onClickCreateSubLogFrame(String logFrameID, cLogFrameModel logFrameModel);
        void onClickUpdateLogFrame(int position, cLogFrameModel logFrameModel);
        void onClickDeleteLogFrame(String logframeID);
        void onClickDeleteLogFrames(List<String> logframe_ids);
        void onClickUploadLogFrame(File excelFile);

        void onLogFrameCreated(cLogFrameModel logFrameModel, String msg);
        void onLogFrameCreateFailed(String msg);
        //void onSubLogFrameCreated(cLogFrameModel logFrameModel, String msg);
        //void onSubLogFrameCreateFailed(String msg);

        /* pass data from interactor to the view */
        void onRetrieveLogFramesCompleted(List<cTreeModel> treeModels);
        void onRetrieveLogFramesFailed(String msg);

        void onLogFrameUpdated(cLogFrameModel logFrameModel, int position, String msg);
        void onLogFrameUpdateFailed(String msg);

        void onDeleteLogFramesSucceeded( String msg);
        void onDeleteLogFrameFailed(String msg);

        void onUploadLogFrameCompleted(String msg);
        void onUploadLogFrameFailed(String msg);

        //void onRequestPermissionsResult(Context context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

        /* shared preferences */
        //void onRetrieveSharedOrgsCompleted(ArrayList<cOrganizationModel> organizationModels);
    }

    /* pass data from view to the interactor */
    void createLogFrameModel(cLogFrameModel logFrameModel);
    void createSubLogFrameModel(String logFrameID, cLogFrameModel logSubFrameModel);
    void readLogFrames();
    void readLogFrame(String projectServerID);
    void updateLogFrame(cLogFrameModel logFrameModel, int position);
    void deleteLogFrameModel(String logFrameID);
    void deleteLogFrameModels();
    //void syncLogFrameModel(cLogFrameModel logFrameModel);
    //void readSharedOrganizations();

    void uploadLogFrameFromExcel(String filePath);
}