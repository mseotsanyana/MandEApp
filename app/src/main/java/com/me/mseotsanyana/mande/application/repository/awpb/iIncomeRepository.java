package com.me.mseotsanyana.mande.application.repository.awpb;

import com.me.mseotsanyana.mande.domain.entities.models.wpb.cIncomeModel;

import java.util.Set;

public interface iIncomeRepository {
    /* the read functions of the Income entity */
    Set<cIncomeModel> getIncomeModelSet(long inputID, long userID, int primaryRoleBITS,
                                        int secondaryRoleBITS, int statusBITS);
}
