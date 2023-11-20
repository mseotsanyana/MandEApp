package com.me.mseotsanyana.mande.application.repository.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.cStatusModel;

import java.util.Set;

public interface iStatusRepository {
    Set<cStatusModel> getStatusSet();
}
