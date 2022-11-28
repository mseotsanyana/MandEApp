package com.me.mseotsanyana.mande.BLL.interactors.session.notification;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cAddressModel;

import java.util.List;

public interface iGetNotificationsInteractor extends iInteractor {
    interface Callback{
        void onGetUserAddresses(List<cAddressModel> addressModels);
    }
}
