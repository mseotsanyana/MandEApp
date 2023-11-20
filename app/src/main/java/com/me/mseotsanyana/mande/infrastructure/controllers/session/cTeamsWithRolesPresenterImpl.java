package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IWorkspaceRepository;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iTeamsWithRolesPresenter;

public class cTeamsWithRolesPresenterImpl extends cAbstractPresenter implements
        iTeamsWithRolesPresenter/*, iReadTeamsWithRolesInteractor.Callback*/ {
    //private static String TAG = cTeamsWithRolesPresenterImpl.class.getSimpleName();

    private View view;
    private final ISessionManager preferenceRepository;
    private final IWorkspaceRepository teamRepository;

    public cTeamsWithRolesPresenterImpl(IExecutor executor, IMainThread mainThread,
                                        View view,
                                        ISessionManager preferenceRepository,
                                        IWorkspaceRepository teamRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.preferenceRepository = preferenceRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void readTeamsWithRoles() {
//        iReadTeamsWithRolesInteractor readTeamInteractor = new CReadWorkspacesWithRolesInteractorImpl(
//                executor,
//                mainThread,
//                preferenceRepository,
//                teamRepository,
//                this);
//
//        view.showProgress();
//        readTeamInteractor.execute();
    }

//    @Override
//    public void onReadTeamsWithRolesFailed(String msg) {
//        if (this.view != null) {
//            this.view.onReadTeamsWithRolesFailed(msg);
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void onReadTeamsWithRolesSucceeded(List<cTreeModel> teamsRolesTree) {
//        if (this.view != null) {
//            this.view.onReadTeamsWithRolesSucceeded(teamsRolesTree);
//            this.view.hideProgress();
//        }
//    }

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
