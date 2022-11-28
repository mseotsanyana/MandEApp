package com.me.mseotsanyana.mande.BLL.interactors.session.session;

import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cMenuModel;

import java.util.List;

public interface iHomePageInteractor extends iInteractor {
    interface Callback {
        void onReadHomePageSucceeded(CUserProfileModel userProfileModel, List<cMenuModel> menuModels);
        //void onReadMenuItemsSucceeded(List<cMenuModel> menuModels);
        //void onDefaultHomePageSucceeded(List<cMenuModel> menuModels);
        void onReadHomePageFailed(String msg);
    }
}
