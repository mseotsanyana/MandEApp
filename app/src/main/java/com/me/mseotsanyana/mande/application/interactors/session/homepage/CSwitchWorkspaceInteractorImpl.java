package com.me.mseotsanyana.mande.application.interactors.session.homepage;

import android.util.Log;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.structures.CConstantModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.utils.cInteractorUtils;
import com.me.mseotsanyana.mande.domain.entities.models.session.CMenuModel;
import com.me.mseotsanyana.mande.application.repository.session.iHomePageRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CHomePageInteractorImpl extends CAbstractInteractor implements IInteractor {
    private static final String TAG = CHomePageInteractorImpl.class.getSimpleName();

    private final IPresenter<Map<String, Object>> iPresenter;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    private List<CMenuModel> menuModels;

    public CHomePageInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                   ISessionManager sessionManager,
                                   IPresenter<Map<String, Object>> iPresenter) {
        super(threadExecutor, mainThread, sessionManager);

        if (iPresenter == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.iPresenter = iPresenter;

        this.setMenuModels(null);

        // load user shared preferences
        this.userServerID = sessionManager.loadUserID();
        this.organizationServerID = sessionManager.loadActiveOrganizationID();
        this.primaryTeamBIT = sessionManager.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sessionManager.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = sessionManager.loadEntityBITS(
                CPreferenceConstant.SESSION_MODULE);
        this.entitypermBITS = sessionManager.loadEntityPermissionBITS(
                CPreferenceConstant.SESSION_MODULE, CPreferenceConstant.ORGANIZATION);
        this.statusBITS = sessionManager.loadOperationStatuses(
                CPreferenceConstant.SESSION_MODULE, CPreferenceConstant.ORGANIZATION,
                CPreferenceConstant.READ);

        Log.d(TAG, " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n USER ID = " + this.userServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY BITS = " + this.entityBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* call back on update home page failed */
    private void notifyError(String msg) {
    }

    /* call back on user profile */
    private void postUserProfileAndMenu(CUserProfileModel userProfileModel, List<CMenuModel> menuModels) {
    }

    public void setMenuModels(List<CMenuModel> menuModels) {
        this.menuModels = menuModels;
    }

    @Override
    public void postResult(Map<String, Object> resultMap) {
        mainThread.post(() -> iPresenter.onSuccess(resultMap));
    }

    @Override
    public void postError(String errorMessage) {
        mainThread.post(() -> iPresenter.onError(new CGeneralException(errorMessage)));
    }

    @Override
    public void run() {

        List<CMenuModel> menuModels;
        menuModels = sessionManager.loadMenuItems(CPreferenceConstant.KEY_MENU_ITEM_BITS);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(CConstantModel.MENUITEM, menuModels);
        postResult(resultMap);

//        /* establish whether the user has permissions or not */
//        boolean isPermissionLoaded = cInteractorUtils.isSettingsNonNull(
//                organizationServerID, userServerID, entityBITS, entitypermBITS, primaryTeamBIT,
//                secondaryTeamBITS, statusBITS);

        /* load user profile and menu items */
//        this.homePageRepository.loadHomePage(isPermissionLoaded, sharedPreferenceRepository,
//                new iHomePageRepository.iHomePageCallback() {
//                    @Override
//                    public void onReadHomePageSucceeded(CUserProfileModel userProfileModel,
//                                                        List<CMenuModel> menuModels) {
//                        userProfileMessage(userProfileModel, menuModels);
//                    }
//
//                    @Override
//                    public void onReadMenuItemsSucceeded(List<CMenuModel> menuModels) {
//                        setMenuModels(menuModels);
//                        //menuItemsMessage(menuModels);
//                    }
//
//                    @Override
//                    public void onDefaultHomePageSucceeded(List<CMenuModel> menuModels) {
//                        setMenuModels(menuModels);
//                        //defaultProfileMessage(menuModels);
//                    }
//
//                    @Override
//                    public void onReadHomePageFailed(String msg) {
//                        notifyError(msg);
//                    }
//                });

    }

}


