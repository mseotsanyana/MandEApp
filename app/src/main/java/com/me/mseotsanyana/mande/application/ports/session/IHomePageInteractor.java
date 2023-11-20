package com.me.mseotsanyana.mande.application.interactors.session.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.cMenuModel;

import java.util.List;

public interface iHomePageInteractor extends IInteractor {
    interface Callback {
        void onReadHomePageSucceeded(CUserProfileModel userProfileModel, List<cMenuModel> menuModels);
        //void onReadMenuItemsSucceeded(List<cMenuModel> menuModels);
        //void onDefaultHomePageSucceeded(List<cMenuModel> menuModels);
        void onReadHomePageFailed(String msg);
    }
}
