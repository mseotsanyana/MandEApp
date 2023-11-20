package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CMenuModel;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IController;

import java.util.List;

public interface IHomePageController extends IController {
    interface IViewModel extends IBaseView {
        void showMenuItemsResponse(List<CMenuModel> menuModels);
        void showWorkspacesResponse(String message);
    }

    /* requests send to the controller */
    void loadHomePageRequest();
    void switchWorkspaceRequest();
    void signOutRequest();
}

