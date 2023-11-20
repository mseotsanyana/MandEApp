package com.me.mseotsanyana.mande.application.interactors.programme.project.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.programme.project.iProjectInteractor;
import com.me.mseotsanyana.mande.application.repository.programme.iProjectRepository;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;

public class cRemoveProjectListenerInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iProjectInteractor {
    private static String TAG = cRemoveProjectListenerInteractorImpl.class.getSimpleName();
    private final iProjectRepository projectRepository;
    private final Callback callback;

    public cRemoveProjectListenerInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                                iProjectRepository projectRepository,
                                                Callback callback) {
        super(threadExecutor, mainThread, null);

        if (projectRepository != null && callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }
        this.projectRepository = projectRepository;
        this.callback = callback;

    }

    @Override
    public void run() {
        projectRepository.removeListener();
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}
