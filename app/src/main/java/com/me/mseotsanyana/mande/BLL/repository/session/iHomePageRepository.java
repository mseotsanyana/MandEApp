package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;

import java.util.List;

public interface iHomePageRepository {
    void loadHomePage(boolean isPermissionLoaded, iSharedPreferenceRepository sharedPreferenceRepository, iHomePageCallback callback);

    interface iHomePageCallback {
        void onReadHomePageSucceeded(CUserProfileModel userProfileModel, List<cMenuModel> menuModels);
        void onReadHomePageFailed(String msg);
//        void onReadMenuItemsSucceeded(List<cMenuModel> menuModels);
//        void onDefaultHomePageSucceeded(List<cMenuModel> menuModels);
    }
}
