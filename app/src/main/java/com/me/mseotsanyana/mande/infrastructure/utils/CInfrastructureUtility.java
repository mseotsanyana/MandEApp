package com.me.mseotsanyana.mande.infrastructure.utils;

public final class CInfrastructureUtility {

    public static final int[] ids = {1, 2, 4, 8, 16, 32};

    public static int getWorkspaceServerID(int workspaceBITS) {
        int workspaceServerID = 0;
        for (int id : ids) {
            if ((id & workspaceBITS) == 0) {
                workspaceServerID = id;
                break;
            }
        }

        return workspaceServerID;
    }
}
