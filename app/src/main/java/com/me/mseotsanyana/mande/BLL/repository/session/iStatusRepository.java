package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.entities.models.session.cStatusModel;

import java.util.Set;

public interface iStatusRepository {
    Set<cStatusModel> getStatusSet();
}
