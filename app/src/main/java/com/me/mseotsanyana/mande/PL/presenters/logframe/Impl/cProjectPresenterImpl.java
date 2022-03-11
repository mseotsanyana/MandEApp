package com.me.mseotsanyana.mande.PL.presenters.logframe.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.programme.project.Impl.cReadProjectInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.programme.project.iProjectInteractor;
import com.me.mseotsanyana.mande.BLL.model.logframe.cProjectModel;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iProjectRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iProjectPresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cProjectPresenterImpl extends cAbstractPresenter implements iProjectPresenter,
        iProjectInteractor.Callback {

    //private static final String TAG = cProjectPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iProjectRepository projectRepository;

    public cProjectPresenterImpl(iExecutor executor, iMainThread mainThread,
                                 View view,
                                 iSharedPreferenceRepository sharedPreferenceRepository,
                                 iProjectRepository projectRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.projectRepository = projectRepository;
    }

    /* ====================================== START CREATE ====================================== */
    /* create a sub-logframe model */
    @Override
    public void createProjectModel(cProjectModel projectModel) {
//        iLogFrameInteractor createLogFrameInteractor =
//                new cCreateLogFrameInteractorImpl(executor, mainThread,
//                        sharedPreferenceRepository,
//                        projectRepository,
//                        this,
//                        projectModel);
//
//        view.showProgress();
//        createLogFrameInteractor.execute();
    }

    @Override
    public void createSubProjectModel(String projectID, cProjectModel subProjectModel) {

    }

    /* create success  response to the view */

    @Override
    public void onCreateProjectCompleted(cProjectModel projectModel, String msg) {
        if (this.view != null) {
            //this.view.onCreateProjectCompleted(projectModel, msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onCreateProjectFailed(String msg) {
        if (this.view != null) {
            //this.view.onCreateProjectFailed(msg);
            this.view.hideProgress();
        }
    }
    /* ======================================= END CREATE ======================================= */

    /* ======================================= START READ ======================================= */
    @Override
    public void readProjects() {
        iProjectInteractor readProjectInteractor = new cReadProjectInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
                projectRepository,
                this);

        view.showProgress();
        readProjectInteractor.execute();
    }

    @Override
    public void onReadProjectsCompleted(List<cTreeModel> treeModels) {
        if (this.view != null) {
            this.view.onReadProjectsCompleted(treeModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadProjectsFailed(String msg) {
        if (this.view != null) {
            this.view.onReadProjectsFailed(msg);
            this.view.hideProgress();
        }
    }
    /* ======================================== END READ ======================================== */

    /* ====================================== START UPDATE ====================================== */
    @Override
    public void updateProject(cProjectModel logFrameModel, int position) {
//        iLogFrameInteractor updateLogFrameInteractor =
//                new cUpdateLogFrameInteractorImpl(executor, mainThread,
//                        logFrameRepository,
//                        this,
//                        logFrameModel,
//                        position);
//
//        view.showProgress();
//        updateLogFrameInteractor.execute();
    }

    @Override
    public void onUpdateProjectSucceeded(cProjectModel projectModel, int position, String msg) {
        if (this.view != null) {
            //this.view.onUpdateProjectSucceeded(projectModel, position, msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUpdateProjectFailed(String msg) {
        if (this.view != null) {
            //this.view.onReadProjectsFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ======================================= END UPDATE ======================================= */

    /* ====================================== START DELETE ====================================== */
    @Override
    public void deleteProjectModel(String logFrameID) {
//        iLogFrameInteractor deleteLogFrameInteractor =
//                new cDeleteLogFrameInteractorImpl(executor, mainThread,
//                        logFrameRepository,
//                        this,
//                        logFrameID);
//
//        view.showProgress();
//        deleteLogFrameInteractor.execute();
    }

    @Override
    public void deleteProjectModels() {
//        iLogFrameInteractor deleteLogFramesInteractor =
//                new cDeleteLogFramesInteractorImpl(executor, mainThread,
//                        sharedPreferenceRepository,
//                        logFrameRepository,
//                        this);
//
//        view.showProgress();
//        deleteLogFramesInteractor.execute();
    }

    @Override
    public void onDeleteProjectsSucceeded(String msg) {
        if (this.view != null) {
            this.view.onDeleteProjectsSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onDeleteProjectsFailed(String msg) {
        if (this.view != null) {
            //this.view.onDeleteProjectsFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ======================================= END DELETE ======================================= */

    /* ====================================== START UPLOAD ====================================== */

    @Override
    public void uploadProjectFromExcel(String filePath) {
//        iLogFrameInteractor uploadLogFrameInteractor =
//                new cUploadProjectInteractorImpl(executor, mainThread,
//                        sharedPreferenceRepository,
//                        projectRepository,
//                        this,
//                        filePath);
//
//        view.showProgress();
//        uploadLogFrameInteractor.execute();
    }

    @Override
    public void onUploadProjectsCompleted(String msg) {
        if (this.view != null) {
            //this.view.onUploadProjectsCompleted(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUploadProjectsFailed(String msg) {
        if (this.view != null) {
            //this.view.onUploadProjectsFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ======================================= END UPLOAD ======================================= */

    /* ================================== START BASE PRESENTER ================================== */

    @Override
    public void resume() {
        readProjects();
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
