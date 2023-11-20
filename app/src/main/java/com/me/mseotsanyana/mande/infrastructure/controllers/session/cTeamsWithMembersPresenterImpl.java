package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IWorkspaceRepository;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iTeamsWithMembersPresenter;

public class cTeamsWithMembersPresenterImpl extends cAbstractPresenter implements
        iTeamsWithMembersPresenter/*, iReadTeamsWithMembersInteractor.Callback*/ {
    //private static String TAG = cTeamPresenterImpl.class.getSimpleName();

    private View view;
    private final ISessionManager preferenceRepository;
    private final IWorkspaceRepository teamRepository;

    public cTeamsWithMembersPresenterImpl(IExecutor executor, IMainThread mainThread,
                                          View view,
                                          ISessionManager preferenceRepository,
                                          IWorkspaceRepository teamRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.preferenceRepository = preferenceRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void createTeam(CWorkspaceModel teamModel) {

    }

    @Override
    public void readTeamsWithMembers() {
//        iReadTeamsWithMembersInteractor readTeamInteractor = new CReadWorkspacesWithMembersInteractorImpl(
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
//    public void onReadTeamsWithMembersFailed(String msg) {
//        if (this.view != null) {
//            this.view.onReadTeamsWithMembersFailed(msg);
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void onReadTeamsWithMembersSucceeded(List<cTreeModel> teamsMembersTree) {
//        if (this.view != null) {
//            this.view.onReadTeamsWithMembersSucceeded(teamsMembersTree);
//            this.view.hideProgress();
//        }
//    }

    /* ===================================== END PREFERENCE ===================================== */

    /* corresponding view functions */
    @Override
    public void resume() {
        readTeamsWithMembers();
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
