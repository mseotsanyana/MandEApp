package com.me.mseotsanyana.mande.PL.presenters.logframe.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.shared.iRecordPermissionInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.shared.Impl.cReadRecordPermissionInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.programme.project.Impl.cUpdateRecordPermissionInteractorImpl;
import com.me.mseotsanyana.mande.BLL.entities.models.common.cRecordPermissionModel;
import com.me.mseotsanyana.mande.BLL.repository.common.iRecordPermissionRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.programme.iProjectRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cEntityType;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iRecordPermissionPresenter;

import java.util.Map;

public class cRecordPermissionPresenterImpl extends cAbstractPresenter implements
        iRecordPermissionPresenter, iRecordPermissionInteractor.Callback {

    //private static final String TAG = cProjectPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iRecordPermissionRepository recordPermissionRepository;
    private final iProjectRepository projectRepository;

    public cRecordPermissionPresenterImpl(iExecutor executor, iMainThread mainThread,
                                          View view,
                                          iSharedPreferenceRepository sharedPreferenceRepository,
                                          iRecordPermissionRepository recordPermissionRepository,
                                          iProjectRepository projectRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.recordPermissionRepository = recordPermissionRepository;
        this.projectRepository = projectRepository;
    }

    /* ================================== UPDATE COMMON MODEL =================================== */

    public void readRecordPermissions() {
        iRecordPermissionInteractor readPermissionInteractor;
        readPermissionInteractor = new cReadRecordPermissionInteractorImpl(
                executor, mainThread,
                sharedPreferenceRepository, recordPermissionRepository,
                this);

        view.showProgress();

        readPermissionInteractor.execute();
    }

    @Override
    public void onReadRecordPermissionsSucceeded(Map<String, Object> propertyLists) {
        if (this.view != null) {
            this.view.onReadRecordPermissionsSucceeded(propertyLists);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadRecordPermissionsFailed(String msg) {
        if (this.view != null) {
            this.view.onReadRecordPermissionsFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ======================================= END UPDATE ======================================= */

    /* ============================ UPDATE RECORD PERMISSIONS MODEL ============================= */

    @Override
    public void updateRecordPermissions(String entityServerID,
                                        cRecordPermissionModel recordPermissionModel) {
        Gson gson = new Gson();
        switch (recordPermissionModel.getEntityIdentity()) {
            case PROJECT:
                iRecordPermissionInteractor recordPermissionInteractor;
                recordPermissionInteractor = new cUpdateRecordPermissionInteractorImpl(
                        executor, mainThread,
                        sharedPreferenceRepository, recordPermissionRepository,
                        projectRepository, this, entityServerID, recordPermissionModel);

                view.showProgress();

                recordPermissionInteractor.execute();
                Log.d("TAG ====================>>>>>>>>>> ", gson.toJson(recordPermissionModel));
                break;
            case LOGFRAME:
                Log.d("TAG ====================>>>>>>>>> ", gson.toJson(recordPermissionModel));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + cEntityType.PROJECT);
        }
    }

    @Override
    public void onUpdateRecordPermissionsSucceeded(String msg) {
        if (this.view != null) {
            this.view.onUpdateRecordPermissionsSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUpdateRecordPermissionsFailed(String msg) {
        if (this.view != null) {
            this.view.onUpdateRecordPermissionsFailed(msg);
            this.view.hideProgress();
        }
    }

    //    @Override
//    public void updateRecordPermissions(String projectServerID,
//                                        c recordPermissionModel) {
//        iProjectInteractor projectInteractor;
//        projectInteractor = new cUpdateProjectInteractorImpl(
//                executor, mainThread,
//                sharedPreferenceRepository, recordPermissionRepository,
//                this);
//
//        view.showProgress();
//
//        projectInteractor.execute();
//    }

    /* ======================================= END UPDATE ======================================= */


    /* ================================== START BASE PRESENTER ================================== */

    @Override
    public void resume() {
        readRecordPermissions();
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

    /* =================================== END BASE PRESENTER =================================== */
}
