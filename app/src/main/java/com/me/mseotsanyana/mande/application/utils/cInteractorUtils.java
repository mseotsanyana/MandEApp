package com.me.mseotsanyana.mande.application.utils;

import androidx.annotation.NonNull;

import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;

import java.util.List;

public final class cInteractorUtils {

    public static boolean isSettingsNonNull(String organizationServerID, String userServerID,
                                         int entityBITS, int entitypermBITS, int primaryTeamBIT,
                                         List<Integer> secondaryTeamBITS, List<Integer> statusBITS) {

        return (organizationServerID != null && userServerID != null && entityBITS != -1 &&
                entitypermBITS != -1 && primaryTeamBIT != -1 && secondaryTeamBITS != null &&
                statusBITS != null);
    }

    /**
     * Verifies access control to the module.
     *
     * @param preference preference settings
     * @param moduleBIT module bit
     * @return boolean
     */
    public static boolean isModulePermitted(@NonNull ISessionManager preference,
                                            int moduleBIT) {
        return ((preference.loadModuleBITS() & moduleBIT) != 0);
    }

    /**
     * Verifies access control to the entity.
     *
     * @param preference permission settings
     * @param moduleBIT module bit
     * @param entityBIT entity bit
     * @return boolean
     */
    public static boolean isEntityPermitted(@NonNull ISessionManager preference,
                                            int moduleBIT, int entityBIT) {
        return ((preference.loadEntityBITS(moduleBIT) & entityBIT) != 0);
    }

}
