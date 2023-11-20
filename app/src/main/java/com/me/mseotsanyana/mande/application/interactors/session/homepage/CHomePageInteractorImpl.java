package com.me.mseotsanyana.mande.application.interactors.session.session.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.utils.cInteractorUtils;
import com.me.mseotsanyana.mande.application.interactors.session.session.iHomePageInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.cMenuModel;
import com.me.mseotsanyana.mande.application.repository.session.iHomePageRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;

import java.util.List;

public class cHomePageInteractorImpl extends CAbstractInteractor
        implements iHomePageInteractor {
    private static final String TAG = cHomePageInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final ISessionManager sharedPreferenceRepository;
    private final iHomePageRepository homePageRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    private List<cMenuModel> menuModels;

    public cHomePageInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                   Callback callback,
                                   ISessionManager sharedPreferenceRepository,
                                   iHomePageRepository homePageRepository) {
        super(threadExecutor, mainThread, null);

        if (sharedPreferenceRepository == null || homePageRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.homePageRepository = homePageRepository;
        this.callback = callback;

        this.setMenuModels(null);

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                CPreferenceConstant.SESSION_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                CPreferenceConstant.SESSION_MODULE, CPreferenceConstant.ORGANIZATION);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
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
        mainThread.post(() -> callback.onReadHomePageFailed(msg));
    }

    /* call back on user profile */
    private void userProfileMessage(CUserProfileModel userProfileModel, List<cMenuModel> menuModels) {
        //Gson gson = new Gson();
        //Log.d(TAG, "MENU ITEMS ================================================== "+gson.toJson(menuModels));

        mainThread.post(() -> callback.onReadHomePageSucceeded(userProfileModel, menuModels));
    }

//    /* call back on role menu items */
//    private void menuItemsMessage(List<cMenuModel> menuModels) {
//        mainThread.post(() -> callback.onReadMenuItemsSucceeded(menuModels));
//    }
//
//    /* call back on user profile and default menu items */
//    private void defaultProfileMessage(List<cMenuModel> menuModels) {
//        mainThread.post(() -> callback.onDefaultHomePageSucceeded(menuModels));
//    }

//    public List<cMenuModel> getMenuModels() {
//        return menuModels;
//    }

    public void setMenuModels(List<cMenuModel> menuModels) {
        this.menuModels = menuModels;
    }

    @Override
    public void run() {
        /* establish whether the user has permissions or not */
        boolean isPermissionLoaded = cInteractorUtils.isSettingsNonNull(
                organizationServerID, userServerID, entityBITS, entitypermBITS, primaryTeamBIT,
                secondaryTeamBITS, statusBITS);

        /* load user profile and menu items */
        this.homePageRepository.loadHomePage(isPermissionLoaded, sharedPreferenceRepository,
                new iHomePageRepository.iHomePageCallback() {
                    @Override
                    public void onReadHomePageSucceeded(CUserProfileModel userProfileModel,
                                                        List<cMenuModel> menuModels) {
                        userProfileMessage(userProfileModel, menuModels);
                    }

//                    @Override
//                    public void onReadMenuItemsSucceeded(List<cMenuModel> menuModels) {
//                        setMenuModels(menuModels);
//                        //menuItemsMessage(menuModels);
//                    }
//
//                    @Override
//                    public void onDefaultHomePageSucceeded(List<cMenuModel> menuModels) {
//                        setMenuModels(menuModels);
//                        //defaultProfileMessage(menuModels);
//                    }

                    @Override
                    public void onReadHomePageFailed(String msg) {
                        notifyError(msg);
                    }
                });

    }
}


