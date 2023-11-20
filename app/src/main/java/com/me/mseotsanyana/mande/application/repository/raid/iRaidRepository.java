package com.me.mseotsanyana.mande.application.repository.raid;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cRaidModel;

import java.util.Set;

public interface iRaidRepository {

    /* the read functions of the Input entity */
    Set<cRaidModel> getRaidModelSetByID(long logFrameID, long userID, int primaryRoleBITS,
                                            int secondaryRoleBITS, int statusBITS);
}
