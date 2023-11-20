package com.me.mseotsanyana.mande.application.interactors.session.userprofile;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.application.structures.CResponseDTO;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.application.structures.enums.EAction;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.application.repository.session.IUserProfileRepository;

public class CUserSignUpInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>> implements IInteractor {
    //private static String TAG = cUserSignUpInteractorImpl.class.getSimpleName();

    private final IPresenter<IResponseDTO<Object>> iPresenter;
    private final IUserProfileRepository userProfileRepository;

    private final CUserProfileModel userProfileModel;

    public CUserSignUpInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                     IUserProfileRepository userProfileRepository,
                                     IPresenter<IResponseDTO<Object>> iPresenter,
                                     CUserProfileModel userProfileModel) {
        super(threadExecutor, mainThread, null);

        if (userProfileRepository == null || iPresenter == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.userProfileRepository = userProfileRepository;
        this.iPresenter = iPresenter;

        this.userProfileModel = userProfileModel;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> iPresenter.onError(new CGeneralException(msg)));
    }

    /* */
    public void postResult(IResponseDTO msg) {
        mainThread.post(() -> iPresenter.onSuccess(msg));
    }

    /* sign up a new user */
    @Override
    public void run() {
        this.userProfileRepository.createUserWithEmailAndPassword(userProfileModel,
                new CFirestoreCallBack() {
            @Override
            public void onFirebaseSuccess(Object object) {
                postResult(new CResponseDTO<>(EAction.Created_USER, object));
            }

            @Override
            public void onFirebaseFailure(Object object) {
                notifyError((String) object);
            }
        });
    }


    @Override
    public void postError(String errorMessage) {

    }
}

/* new user successfully registered with firebase */
//@Override
//public void onSignUpSucceeded(String msg) {
//        postMessage(msg);
//        }
//
///* new user failed to register with firebase */
//@Override
//public void onSignUpFailed(String msg) {
//        notifyError(msg);
//        }
//        });