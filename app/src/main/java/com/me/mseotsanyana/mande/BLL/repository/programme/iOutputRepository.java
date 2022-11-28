package com.me.mseotsanyana.mande.BLL.repository.programme;

import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cOutputModel;

import java.util.Set;

public interface iOutputRepository {
    /* the create function of the Impact entity */
    boolean addOutput(cOutputModel outputModel);

    /* the read functions of the Output entity */
    Set<cOutputModel> getOutputModelSet(long logFrameID, long userID, int primaryRoleBITS,
                                          int secondaryRoleBITS, int statusBITS);
}
