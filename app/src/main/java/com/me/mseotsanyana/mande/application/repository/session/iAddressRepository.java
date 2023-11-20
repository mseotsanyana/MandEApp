package com.me.mseotsanyana.mande.application.repository.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.cAddressModel;

public interface iAddressRepository {
    boolean addAddressFromExcel(cAddressModel addressModel);
}
