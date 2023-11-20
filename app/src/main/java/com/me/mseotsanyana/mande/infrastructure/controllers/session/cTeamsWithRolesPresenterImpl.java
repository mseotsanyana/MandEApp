package com.me.mseotsanyana.mande.interfaceadapters.controllers.session;

import com.me.mseotsanyana.mande.usecases.executor.iExecutor;
import com.me.mseotsanyana.mande.usecases.executor.iMainThread;
import com.me.mseotsanyana.mande.usecases.interactors.session.team.Impl.cReadTeamsWithRolesInteractorImpl;
import com.me.mseotsanyana.mande.usecases.interactors.session.team.iReadTeamsWithRolesInteractor;
import com.me.mseotsanyana.mande.usecases.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.usecases.repository.session.iWorkspaceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iTeamsWithRolesPresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cTeamsWithRolesPresenterImpl extends cAbstractPresenter implements
        iTeamsWithRolesPresenter, iReadTeamsWithRolesInteractor.Callback {
    //private static String TAG = cTeamsWithRolesPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository preferenceRepository;
    private final iWorkspaceRepository teamRepository;

    public cTeamsWithRolesPresenterImpl(iExecutor executor, iMainThread mainThread,
                                        View view,
                                        iSharedPreferenceRepository preferenceRepository,
                                        iWorkspaceRepository teamRepository) {
        super(executor, mainThread);

        this.view = view;
        this.preferenceRepository = preferenceRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void readTeamsWithRoles() {
        iReadTeamsWithRolesInteractor readTeamInteractor = new cReadTeamsWithRolesInteractorImpl(
                executor,
                mainThread,
                preferenceRepository,
                teamRepository,
                this);

        view.showProgress();
        readTeamInteractor.execute();
    }

    @Override
    public void onReadTeamsWithRolesFailed(String msg) {
        if (this.view != null) {
            this.view.onReadTeamsWithRolesFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadTeamsWithRolesSucceeded(List<cTreeModel> teamsRolesTree) {
        if (this.view != null) {
            this.view.onReadTeamsWithRolesSucceeded(teamsRolesTree);
            this.view.hideProgress();
        }
    }

    /* ===================================== END PREFERENCE ===================================== */

    /* corresponding view functions */
    @Override
    public void resume() {
        readTeamsWithRoles();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if (this.view != null) {
            this.view.hideProgress();
        }
    }

    @Override
    public void destroy() {
        this.view = null;
    }

    @Override
    public void onError(String message) {

    }
}
