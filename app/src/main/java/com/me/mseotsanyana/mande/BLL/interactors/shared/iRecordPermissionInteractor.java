package com.me.mseotsanyana.mande.BLL.interactors.shared;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

import java.util.Map;

public interface iRecordPermissionInteractor extends iInteractor {
    interface Callback{
        // read record permissions
        void onReadRecordPermissionsSucceeded(Map<String, Object> userProfileModels);
        void onReadRecordPermissionsFailed(String msg);

        // update record permissions
        void onUpdateRecordPermissionsSucceeded(String msg);
        void onUpdateRecordPermissionsFailed(String msg);
    }
}
