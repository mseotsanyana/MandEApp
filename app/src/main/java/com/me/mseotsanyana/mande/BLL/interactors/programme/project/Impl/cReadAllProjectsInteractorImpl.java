package com.me.mseotsanyana.mande.BLL.interactors.programme.project.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.cInteractorUtils;
import com.me.mseotsanyana.mande.BLL.interactors.programme.project.iProjectInteractor;
import com.me.mseotsanyana.mande.BLL.model.logframe.cProjectModel;
import com.me.mseotsanyana.mande.BLL.repository.programme.iProjectRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

public class cReadAllProjectsInteractorImpl extends cAbstractInteractor
        implements iProjectInteractor {
    private static final String TAG = cReadAllProjectsInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iProjectRepository projectRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadAllProjectsInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                          iSharedPreferenceRepository sharedPreferenceRepository,
                                          iProjectRepository projectRepository,
                                          Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || projectRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.projectRepository = projectRepository;
        this.callback = callback;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadPrimaryTeamBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryTeams();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.PROGRAMME_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.PROGRAMME_MODULE, cSharedPreference.PROJECT);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.PROGRAMME_MODULE, cSharedPreference.PROJECT,
                cSharedPreference.READ);

        Log.d(TAG, " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n USER ID = " + this.userServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n UNIX TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY BITS = " + this.entityBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* return project tree models to the main thread */
    private void postMessage(List<cTreeModel> treeModels) {
        mainThread.post(() -> callback.onReadAllProjectsSucceeded(treeModels));
    }

    /* return failure message to the main thread */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadAllProjectsFailed(msg));
    }

    List<cTreeModel> getProjectTree(List<cProjectModel> projectModels) {
        List<cTreeModel> projectTreeModels = new ArrayList<>();
        int parentIndex = 0, childIndex;

        for (int i = 0; i < projectModels.size(); i++) {
            // create logframe without parent projects
            if (projectModels.get(i).getParentServerID() == null) {
                projectTreeModels.add(
                        new cTreeModel(parentIndex, -1, 0, projectModels.get(i)));
            }

            // create parent logframe with child projects
            childIndex = parentIndex;
            for (int j = 0; j < projectModels.size(); j++) {
                if (projectModels.get(i).getProjectServerID().equals(
                        projectModels.get(j).getParentServerID())) {
                    childIndex = childIndex + 1;
                    projectTreeModels.add(new cTreeModel(
                            childIndex, parentIndex, 1, projectModels.get(j)));
                }
            }
            parentIndex = childIndex + 1;
        }
        return projectTreeModels;
    }

    @Override
    public void run() {
        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
            if ((this.entityBITS & cSharedPreference.PROJECT) != 0) {
                if ((this.entitypermBITS & cSharedPreference.READ) != 0) {

                    projectRepository.readProjects(organizationServerID, userServerID,
                            primaryTeamBIT, secondaryTeamBITS, statusBITS,
                            new iProjectRepository.iReadProjectsCallback() {
                                @Override
                                public void onReadProjectsSucceeded(
                                        List<cProjectModel> projectModels) {
                                    List<cTreeModel> treeModels = getProjectTree(projectModels);
                                    postMessage(treeModels);
                                }

                                @Override
                                public void onReadProjectFailed(String msg) {
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
}