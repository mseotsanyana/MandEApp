package com.me.mseotsanyana.mande.application.repository.preference;

import java.util.List;
import java.util.Map;

public interface iRecordPermissionRepository {

    void onReadRecordPermissions(String organizationServerID, String userServerID,
                                 int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                 List<Integer> statusBITS, iReadRecordPermissionsCallback callback);

    interface iReadRecordPermissionsCallback {
        void onReadRecordPermissionsSucceeded(Map<String, Object> recordPermissions);

        void onReadRecordPermissionsFailed(String msg);
    }
}
