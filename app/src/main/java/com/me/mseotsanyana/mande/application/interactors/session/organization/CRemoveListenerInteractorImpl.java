package com.me.mseotsanyana.mande.application.interactors.session.organization;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.repository.session.IOrganizationRepository;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;

import java.util.Map;

public class CRemoveListenerInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>implements IInteractor {
    private static String TAG = CRemoveListenerInteractorImpl.class.getSimpleName();
    private final IOrganizationRepository organizationRepository;
    private final IPresenter<IResponseDTO<Object>> iPresenter;

    public CRemoveListenerInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                         ISessionManager sessionManager,
                                         IPresenter<IResponseDTO<Object>> iPresenter,
                                         IOrganizationRepository organizationRepository) {

        super(threadExecutor, mainThread, sessionManager);

        if (organizationRepository != null && iPresenter == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.iPresenter = iPresenter;
        this.organizationRepository = organizationRepository;

    }

    @Override
    public void run() {
        //FIXME: include callbacks to get out thread to the main one.
        organizationRepository.removeListener();
    }

    @Override
    public void postResult(IResponseDTO<Object> resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}
