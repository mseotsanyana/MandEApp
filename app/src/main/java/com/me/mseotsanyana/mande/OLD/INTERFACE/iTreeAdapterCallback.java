package com.me.mseotsanyana.mande.OLD.INTERFACE;


import com.me.mseotsanyana.mande.domain.entities.models.session.cPermissionModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cStatusModel;

public interface iTreeAdapterCallback {
    void onCreatePermissions(cStatusModel statusDomain);

    void onUpdatePermissions(cPermissionModel originalDomain, cPermissionModel modifiesDomain);

    void onRefreshPermissions();

    void onRefreshTreeAdapter();
}
