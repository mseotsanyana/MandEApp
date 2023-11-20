package com.me.mseotsanyana.mande.infrastructure.controllers.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CPrivilegeModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.session.role.Impl.cReadTeamRolesInteractorImpl;
import com.me.mseotsanyana.mande.application.interactors.session.role.iReadTeamRolesInteractor;
import com.me.mseotsanyana.mande.application.repository.session.iRoleRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.infrastructure.ports.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iRolePresenter;

import java.util.List;

public class cRolePresenterImpl extends cAbstractPresenter implements iRolePresenter,
        iReadTeamRolesInteractor.Callback{
    //private static String TAG = cTeamPresenterImpl.class.getSimpleName();

    private View view;
    private final ISessionManager sharedPreferenceRepository;
    private final iRoleRepository roleRepository;

    public cRolePresenterImpl(IExecutor executor, IMainThread mainThread,
                              View view,
                              ISessionManager sharedPreferenceRepository,
                              iRoleRepository roleRepository) {
        super(executor, mainThread, null);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.roleRepository = roleRepository;
    }

    // READ ROLES

    @Override
    public void readRoles() {
        iReadTeamRolesInteractor readTeamInteractor = new cReadTeamRolesInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
                roleRepository,
                this);

        view.showProgress();
        readTeamInteractor.execute();
    }

    @Override
    public void onReadTeamRolesSucceeded(List<CPrivilegeModel> roleModels) {
        if(this.view != null) {
            this.view.onReadRolesSucceeded(roleModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadTeamRolesFailed(String msg) {
        if(this.view != null) {
            this.view.onReadRolesFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ===================================== END PREFERENCE ===================================== */

    /* corresponding view functions */
    @Override
    public void resume() {
        readRoles();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if(this.view != null){
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
