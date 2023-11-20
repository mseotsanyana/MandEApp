package com.me.mseotsanyana.mande.application.interactors.session.value;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.cAddressModel;

import java.util.List;

public interface iGetValuesInteractor extends iInteractor {
    interface Callback{
        void onGetUserAddresses(List<cAddressModel> addressModels);
    }
}
