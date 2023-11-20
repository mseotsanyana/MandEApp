package com.me.mseotsanyana.mande.application.interactors.session.homepage;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.CConstantModel;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.application.structures.CResponseDTO;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.application.structures.enums.EAction;

import java.util.HashMap;
import java.util.Map;

public class CSwitchWorkspaceInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements IInteractor {
    //private static final String TAG = CSwitchWorkspaceInteractorImpl.class.getSimpleName();

    private final IPresenter<IResponseDTO<Object>> iPresenter;

    public CSwitchWorkspaceInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                          ISessionManager sessionManager,
                                          IPresenter<IResponseDTO<Object>> iPresenter) {
        super(threadExecutor, mainThread, sessionManager);

        if (iPresenter == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.iPresenter = iPresenter;
    }

    @Override
    public void postResult(IResponseDTO<Object> resultMap) {
        mainThread.post(() -> iPresenter.onSuccess(resultMap));
    }

    @Override
    public void postError(String errorMessage) {
        mainThread.post(() -> iPresenter.onError(new CGeneralException(errorMessage)));
    }

    @Override
    public void run() {
        String loggedInUser = sessionManager.loadLoggedInUserServerID();
        if (sessionManager.isClearCommitted()){
            //Map<String, Object> resultMap = new HashMap<>();
            sessionManager.saveLoggedInUserServerID(CPreferenceConstant.KEY_USER_ID, loggedInUser);
            //resultMap.put(CConstantModel.SWITCH,"Switch workspace succeeded.");

            IResponseDTO<Object> responseModel;
            responseModel = new CResponseDTO<>(EAction.Switched_WORKSPACE,
                    "Switch workspace succeeded.");

            postResult(responseModel);
        }else {
            postError("Failed to switch workspace!");
        }
    }
}


