package com.me.mseotsanyana.mande.application.interactors.shared;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

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
