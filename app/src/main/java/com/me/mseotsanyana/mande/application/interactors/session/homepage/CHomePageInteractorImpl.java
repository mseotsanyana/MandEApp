package com.me.mseotsanyana.mande.application.interactors.session.homepage;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.structures.CConstantModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.structures.CResponseDTO;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.application.structures.enums.EAction;
import com.me.mseotsanyana.mande.domain.entities.models.session.CMenuModel;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CHomePageInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements IInteractor {
    //private static final String TAG = CHomePageInteractorImpl.class.getSimpleName();

    private final IPresenter<IResponseDTO<Object>> iPresenter;

    public CHomePageInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                   ISessionManager sessionManager,
                                   IPresenter<IResponseDTO<Object>> iPresenter) {
        super(threadExecutor, mainThread, sessionManager);

        if (iPresenter == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.iPresenter = iPresenter;
    }


    @Override
    public void postResult(IResponseDTO<Object> resultMap) {
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
        if (menuModels != null && !menuModels.isEmpty()) {
            //Map<String, Object> resultMap = new HashMap<>();
            //resultMap.put(CConstantModel.MENUITEM, menuModels);
            IResponseDTO<Object> responseModel;
            responseModel = new CResponseDTO<>(EAction.Loaded_MENU, menuModels);
            postResult(responseModel);
        } else {
            postError("Failed to load home page");
        }
    }
}

//    private final String userServerID;
//    private final String organizationServerID;
//    private final int primaryTeamBIT;
//    private final List<Integer> secondaryTeamBITS;
//    private final List<Integer> statusBITS;
//
//    private final int entityBITS;
//    private final int entitypermBITS;
//
//    private List<CMenuModel> menuModels;

//    public void setMenuModels(List<CMenuModel> menuModels) {
//        this.menuModels = menuModels;
//    }

//        this.setMenuModels(null);
//
//        // load user shared preferences
//        this.userServerID = sessionManager.loadLoggedInUserServerID();
//        this.organizationServerID = sessionManager.loadActiveOrganizationID();
//        this.primaryTeamBIT = sessionManager.loadActiveWorkspaceBIT();
//        this.secondaryTeamBITS = sessionManager.loadSecondaryWorkspaces();
//
//        // load entity shared preferences
//        this.entityBITS = sessionManager.loadEntityBITS(
//                CPreferenceConstant.SESSION_MODULE);
//        this.entitypermBITS = sessionManager.loadEntityPermissionBITS(
//                CPreferenceConstant.SESSION_MODULE, CPreferenceConstant.ORGANIZATION);
//        this.statusBITS = sessionManager.loadOperationStatuses(
//                CPreferenceConstant.SESSION_MODULE, CPreferenceConstant.ORGANIZATION,
//                CPreferenceConstant.READ);
//
//        Log.d(TAG, " \n ORGANIZATION ID = " + this.organizationServerID +
//                " \n USER ID = " + this.userServerID +
//                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
//                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
//                " \n ENTITY BITS = " + this.entityBITS +
//                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
//                " \n OPERATION STATUSES = " + this.statusBITS);

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

