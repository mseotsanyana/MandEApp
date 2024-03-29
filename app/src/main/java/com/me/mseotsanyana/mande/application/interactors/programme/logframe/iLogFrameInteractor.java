package com.me.mseotsanyana.mande.application.interactors.programme.logframe;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cLogFrameModel;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iLogFrameInteractor extends IInteractor {
    interface Callback{
        // create
        void onLogFrameCreated(cLogFrameModel logFrameModel, String msg);
        void onLogFrameCreateFailed(String msg);
        // read
        void onLogFramesRetrieved(List<cTreeModel> treeModels);
        void onLogFramesRetrieveFailed(String msg);
        // update
        void onLogFrameUpdated(cLogFrameModel logFrameModel, int position, String msg);
        void onLogFrameUpdateFailed(String msg);
        // delete
        void onDeleteLogFrameFailed(String msg);
        void onDeleteLogFramesSucceeded(String msg);
        // upload
        void onUploadLogFrameCompleted(String msg);
        void onUploadLogFrameFailed(String msg);
    }
}
