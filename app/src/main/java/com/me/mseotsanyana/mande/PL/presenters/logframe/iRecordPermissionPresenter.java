package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.BLL.model.common.cRecordPermissionModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

import java.util.Map;

public interface iRecordPermissionPresenter extends iPresenter {
    interface View extends iBaseView {
        /* pass data from interactor to the view */
        void onReadRecordPermissionsSucceeded(Map<String, Object> propertyLists);

        void onReadRecordPermissionsFailed(String msg);

        void onUpdateRecordPermissionsSucceeded(String msg);

        void onUpdateRecordPermissionsFailed(String msg);
    }

    /* pass data from view to the interactor */
    void readRecordPermissions();

    void updateRecordPermissions(String entityServerID,
                                 cRecordPermissionModel recordPermissionModel);
}