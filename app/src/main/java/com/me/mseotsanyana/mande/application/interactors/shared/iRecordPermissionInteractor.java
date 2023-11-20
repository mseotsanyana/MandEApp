package com.me.mseotsanyana.mande.application.interactors.shared;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

import java.util.Map;

public interface iRecordPermissionInteractor extends IInteractor {
    interface Callback{
        // read record permissions
        void onReadRecordPermissionsSucceeded(Map<String, Object> userProfileModels);
        void onReadRecordPermissionsFailed(String msg);

        // update record permissions
        void onUpdateRecordPermissionsSucceeded(String msg);
        void onUpdateRecordPermissionsFailed(String msg);
    }
}
