package com.me.mseotsanyana.mande.interfaceadapters.controllers.session;

import com.me.mseotsanyana.mande.usecases.executor.iExecutor;
import com.me.mseotsanyana.mande.usecases.executor.iMainThread;
import com.me.mseotsanyana.mande.usecases.interactors.session.privilege.Impl.cReadWorkspacePrivilegesInteractorImpl;
import com.me.mseotsanyana.mande.usecases.interactors.session.privilege.Impl.cUpdateWorkspacePrivilegeInteractorImpl;
import com.me.mseotsanyana.mande.usecases.interactors.session.privilege.iReadWorkspacePrivilegesInteractor;
import com.me.mseotsanyana.mande.usecases.interactors.session.privilege.iUpdateWorkspacePrivilegeInteractor;
import com.me.mseotsanyana.mande.usecases.repository.session.iPrivilegeRepository;
import com.me.mseotsanyana.mande.usecases.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iPrivilegePresenter;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cPrivilegePresenterImpl extends cAbstractPresenter implements iPrivilegePresenter,
        iReadWorkspacePrivilegesInteractor.Callback, iUpdateWorkspacePrivilegeInteractor.Callback {
    //private static String TAG = cTeamPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iPrivilegeRepository privilegeRepository;

    public cPrivilegePresenterImpl(iExecutor executor, iMainThread mainThread,
                                   View view,
                                   iSharedPreferenceRepository sharedPreferenceRepository,
                                   iPrivilegeRepository privilegeRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.privilegeRepository = privilegeRepository;
    }

    // READ PERMISSIONS

    @Override
    public void readWorkspacePrivileges() {
        iReadWorkspacePrivilegesInteractor readWorkspacePrivilegesInteractor =
                new cReadWorkspacePrivilegesInteractorImpl(
                        executor,
                        mainThread,
                        sharedPreferenceRepository,
                        privilegeRepository,
                        this);

        view.showProgress();
        readWorkspacePrivilegesInteractor.execute();
    }



    @Override
    public void onReadRolePermissionsFailed(String msg) {
        if (this.view != null) {
            this.view.onReadWorkspacePrivilegesFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadRolePermissionsSucceeded(List<cTreeModel> treeModels) {
        if (this.view != null) {
            this.view.onReadWorkspacePrivilegesSucceeded(treeModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void updateWorkspacePrivileges(cNode node) {
        iUpdateWorkspacePrivilegeInteractor updateWorkspacePrivilegeInteractor =
                new cUpdateWorkspacePrivilegeInteractorImpl(
                        executor,
                        mainThread,
                        sharedPreferenceRepository,
                        privilegeRepository,
                        this,
                        node);

        view.showProgress();
        updateWorkspacePrivilegeInteractor.execute();
    }

    @Override
    public void onUpdateWorkspacePrivilegeSucceeded(String msg) {
        if (this.view != null) {
            this.view.onUpdateWorkspacePrivilegeSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUpdateWorkspacePrivilegeFailed(String msg) {
        if (this.view != null) {
            this.view.onUpdateWorkspacePrivilegeFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ===================================== END PREFERENCE ===================================== */

    /* corresponding view functions */
    @Override
    public void resume() {
        readWorkspacePrivileges();
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
