package com.me.mseotsanyana.mande.application.interactors.session.notification;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.cAddressModel;

import java.util.List;

public interface iGetNotificationsInteractor extends IInteractor {
    interface Callback{
        void onGetUserAddresses(List<cAddressModel> addressModels);
    }
}
