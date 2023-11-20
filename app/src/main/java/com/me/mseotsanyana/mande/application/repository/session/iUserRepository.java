package com.me.mseotsanyana.mande.application.repository.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.cUserModel;

import java.util.Set;

public interface iUserRepository {

    Set<cUserModel> getOwnerSet();
}
