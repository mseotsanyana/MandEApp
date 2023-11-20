package com.me.mseotsanyana.mande.application.repository.awpb;

import com.me.mseotsanyana.mande.domain.entities.models.wpb.cMaterialModel;

import java.util.Set;

public interface iMaterialRepository {
    /* the read functions of the Material entity */
    Set<cMaterialModel> getMaterialModelSet(long inputID, long userID, int primaryRoleBITS,
                                           int secondaryRoleBITS, int statusBITS);
}
