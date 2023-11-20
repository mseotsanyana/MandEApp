package com.me.mseotsanyana.mande.application.ports.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.CMenuModel;

import java.util.List;

public interface IHomePageInteractor extends IInteractor {
    interface Callback {
        void onReadHomePageSucceeded(CUserProfileModel userProfileModel, List<CMenuModel> menuModels);
        //void onReadMenuItemsSucceeded(List<cMenuModel> menuModels);
        //void onDefaultHomePageSucceeded(List<cMenuModel> menuModels);
        void onReadHomePageFailed(String msg);
    }
}
