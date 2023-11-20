package com.me.mseotsanyana.mande.application.interactors.session.status.Impl;

import android.content.Context;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.status.iStatusInteractor;
import com.me.mseotsanyana.mande.application.repository.session.iStatusRepository;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cStatusInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iStatusInteractor {
    private static String TAG = cStatusInteractorImpl.class.getSimpleName();
    //private cStatusRepositoryImpl statusDBA;
    private Context context;

    private Callback callback;
    private iStatusRepository statusRepository;

    Gson gson = new Gson();

    public cStatusInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                 iStatusRepository statusRepository,
                                 Callback callback) {
        super(threadExecutor, mainThread, null);

        if (statusRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }
        this.statusRepository = statusRepository;
        this.callback = callback;
    }

    @Override
    public void postResult(IResponseDTO<Object> resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }

    @Override
    public void run() {

    }
}
