package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cMenuModel;
import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

import java.util.List;

public interface iHomePagePresenter extends iPresenter {
    /* implemented in either Fragments and/or Activities. Called in PresenterImpl */
    interface View extends IBaseView {
        void onReadHomePageSucceeded(CUserProfileModel userProfileModel, List<cMenuModel> menuModels);
//        void onReadMenuItemsSucceeded(List<cMenuModel> menuModels);
//        void onDefaultHomePageSucceeded(List<cMenuModel> menuModels);
        void onReadHomePageFailed(String msg);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void loadHomePage();
}

