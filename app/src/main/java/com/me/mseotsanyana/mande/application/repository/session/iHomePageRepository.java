package com.me.mseotsanyana.mande.application.repository.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CMenuModel;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;

import java.util.List;

public interface iHomePageRepository {
    void loadHomePage(boolean isPermissionLoaded, ISessionManager sharedPreferenceRepository, iHomePageCallback callback);

    interface iHomePageCallback {
        void onReadHomePageSucceeded(CUserProfileModel userProfileModel, List<CMenuModel> menuModels);
        void onReadHomePageFailed(String msg);
//        void onReadMenuItemsSucceeded(List<cMenuModel> menuModels);
//        void onDefaultHomePageSucceeded(List<cMenuModel> menuModels);
    }
}
