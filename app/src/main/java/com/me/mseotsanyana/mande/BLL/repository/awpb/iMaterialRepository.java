package com.me.mseotsanyana.mande.BLL.repository.awpb;

import com.me.mseotsanyana.mande.BLL.entities.models.wpb.cMaterialModel;

import java.util.Set;

public interface iMaterialRepository {
    /* the read functions of the Material entity */
    Set<cMaterialModel> getMaterialModelSet(long inputID, long userID, int primaryRoleBITS,
                                           int secondaryRoleBITS, int statusBITS);
}
