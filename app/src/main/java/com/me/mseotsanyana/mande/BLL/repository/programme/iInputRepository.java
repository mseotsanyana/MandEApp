package com.me.mseotsanyana.mande.BLL.repository.programme;

import com.me.mseotsanyana.mande.BLL.model.logframe.cInputModel;

import java.util.Set;

public interface iInputRepository {
    /* the create function of the Input entity */
    boolean addInput(cInputModel inputModel);

    /* the read functions of the Input entity */
    Set<cInputModel> getInputModelSet(long logFrameID, long userID, int primaryRoleBITS,
                                      int secondaryRoleBITS, int statusBITS);
}
