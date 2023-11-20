package com.me.mseotsanyana.mande.application.interactors.programme.logframe.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.application.utils.cInteractorUtils;
import com.me.mseotsanyana.mande.application.interactors.programme.logframe.iLogFrameInteractor;
import com.me.mseotsanyana.mande.application.repository.programme.iLogFrameRepository;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

public class cReadLogFrameInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iLogFrameInteractor {
    private static final String TAG = cReadLogFrameInteractorImpl.class.getSimpleName();

    private final iLogFrameInteractor.Callback callback;
    private final iLogFrameRepository logFrameRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    String projectServerID;

    public cReadLogFrameInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                       ISessionManager sharedPreferenceRepository,
                                       iLogFrameRepository logFrameRepository,
                                       Callback callback, String projectServerID) {
        super(threadExecutor, mainThread, null);

        if (sharedPreferenceRepository == null || logFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.logFrameRepository = logFrameRepository;
        this.callback = callback;
        this.projectServerID = projectServerID;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadLoggedInUserServerID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                CPreferenceConstant.PROGRAMME_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                CPreferenceConstant.PROGRAMME_MODULE, CPreferenceConstant.LOGFRAME);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                CPreferenceConstant.PROGRAMME_MODULE, CPreferenceConstant.LOGFRAME,
                CPreferenceConstant.READ);

        Log.d(TAG, " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n USER ID = " + this.userServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n UNIX TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY BITS = " + this.entityBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onLogFramesRetrieveFailed(msg));
    }

    /* */
    private void postMessage(List<cTreeModel> treeModels) {
        mainThread.post(() -> callback.onLogFramesRetrieved(treeModels));
    }


    List<cTreeModel> getLogFrameTree(List<cLogFrameModel> logFrameModels) {
        List<cTreeModel> logFrameTreeModels = new ArrayList<>();
        int parentIndex = 0, childIndex;

        for (int i = 0; i < logFrameModels.size(); i++) {
            // create logframe without parent logframe
            if (logFrameModels.get(i).getParentServerID() == null) {
                //logFrameTreeModels.add(
                //        new cTreeModel(parentIndex, -1, 0, logFrameModels.get(i)));
            }

            // create parent logframe with child logframes
            childIndex = parentIndex;
            for (int j = 0; j < logFrameModels.size(); j++) {
                if (logFrameModels.get(i).getProjectServerID().equals(
                        logFrameModels.get(j).getParentServerID())) {
                    childIndex = childIndex + 1;
                    //logFrameTreeModels.add(new cTreeModel(
                    //        childIndex, parentIndex, 1, logFrameModels.get(j)));
                }
            }
            parentIndex = childIndex + 1;
        }
        //Gson gson = new Gson();
        //Log.d(TAG, "====================="+gson.toJson(logFrameTreeModels));
        /* sort the tree models */
        //logFrameTreeModels.sort(cTreeModel.childTreeModel);
        return logFrameTreeModels;
    }

    @Override
    public void run() {
        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
            if ((this.entityBITS & CPreferenceConstant.LOGFRAME) != 0) {
                if ((this.entitypermBITS & CPreferenceConstant.READ) != 0) {

                    logFrameRepository.readLogFrame(organizationServerID, userServerID,
                            primaryTeamBIT, secondaryTeamBITS, statusBITS,projectServerID,
                            new iLogFrameRepository.iReadLogFramesCallback() {
                                @Override
                                public void onReadLogFrameSucceeded(List<cLogFrameModel>
                                                                            logFrameModels) {

                                    List<cTreeModel> treeModels = getLogFrameTree(logFrameModels);
                                    postMessage(treeModels);
                                }

                                @Override
                                public void onReadLogFrameFailed(String msg) {
                                    notifyError(msg);
                                }
                            });

                } else {
                    notifyError("Permission denied! Please contact your administrator");
                }
            } else {
                notifyError("No access to the entity! Please contact your administrator");
            }
        } else {
            notifyError("Error in default settings");
        }
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}

//    private LinkedHashMap<String, List<String>> getExpandableMenu(Set<cMenuModel> menuModelSet) {
//        LinkedHashMap<String, List<String>> expandableMenuItems = new LinkedHashMap<>();
//        /* sort the menu model set */
//        SortedSet<cMenuModel> sortedMenuModelSet = new TreeSet<>(Comparator.comparing(
//                cMenuModel::getMenuServerID));
//        sortedMenuModelSet.addAll(menuModelSet);
//
//        for (cMenuModel menuModel : sortedMenuModelSet) {
//            if (menuModel.getParentServerID() == 0) { /* FIXME: the parent ID should default
//                                                   to zero in a database! */
//                List<String> subMenu = new ArrayList<String>();
//
//                SortedSet<cMenuModel> subMenuModelSet = new TreeSet<>(Comparator.comparing(
//                        cMenuModel::getMenuServerID));
//                //subMenuModelSet.addAll(menuModel.getMenuModelSet());
//
//                for (cMenuModel subMenuModel : subMenuModelSet) {
//                    subMenu.add(subMenuModel.getName());
//                }
//
//                expandableMenuItems.put(menuModel.getName(), subMenu);
//            }
//        }
//        return expandableMenuItems;
//    }

//        /* retrieve a set logFrames from the database */
//        Log.d(TAG, "USER ID = " + userID + " PRIMARY = " + primaryRoleBITS +
//                " SECONDARY = " + secondaryRoleBITS + " STATUS = " + statusLogFrameBITS);
//        Set<cLogFrameModel> logFrameModelSet = logFrameRepository.getLogFrameModelSet(
//                userID, primaryRoleBITS, secondaryRoleBITS, statusLogFrameBITS);
//
//        if (menuModelSet != null && logFrameModelSet != null) {
//            Gson gson = new Gson();
//            Log.d(TAG, "LOGFRAME = " + gson.toJson(logFrameModelSet.size()));
//
//            LinkedHashMap<String, List<String>> expandableMenuItems = getExpandableMenu(
//                    menuModelSet);
//
//
//            /* used to search for individual and\or organization owners */
//            // indOwners = getIndividualOwners();
//            // orgOwners = getOrganizationOwners();
//
//
//        } else {
//            notifyError("Failed to load main menu items and Logframes !!");
//        }