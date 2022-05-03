package com.me.mseotsanyana.mande.BLL.interactors.programme.project.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.programme.project.iProjectInteractor;
import com.me.mseotsanyana.mande.BLL.repository.programme.iProjectRepository;

public class cRemoveProjectListenerInteractorImpl extends cAbstractInteractor
        implements iProjectInteractor {
    private static String TAG = cRemoveProjectListenerInteractorImpl.class.getSimpleName();
    private final iProjectRepository projectRepository;
    private final Callback callback;

    public cRemoveProjectListenerInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                                iProjectRepository projectRepository,
                                                Callback callback) {
        super(threadExecutor, mainThread);

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
}
