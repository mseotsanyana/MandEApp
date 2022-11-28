package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.entities.models.session.cUserModel;

import java.util.Set;

public interface iUserRepository {

    Set<cUserModel> getOwnerSet();
}
