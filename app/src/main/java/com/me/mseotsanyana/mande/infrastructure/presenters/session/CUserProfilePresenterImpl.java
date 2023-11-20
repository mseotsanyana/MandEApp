package com.me.mseotsanyana.mande.infrastructure.presenters.session;

import android.util.Log;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IUserProfileController;

public class CUserProfilePresenterImpl implements IInteractor.IPresenter<IResponseDTO<Object>> {
    private static final String TAG = CUserProfilePresenterImpl.class.getSimpleName();

    private final IUserProfileController.IViewModel<String> iViewModel;

    public CUserProfilePresenterImpl(IUserProfileController.IViewModel<String> iViewModel) {
        this.iViewModel = iViewModel;
    }

    /******************************* user logout feedback functions *******************************/

    @Override
    public void onError(Throwable throwable) {
        if (this.iViewModel != null) {
            this.iViewModel.showMessage(throwable.getMessage());
            this.iViewModel.hideProgress();
            Log.i(TAG, " " + throwable.getMessage());
        }
    }

    @Override
    public void onSuccess(IResponseDTO<Object> response) {
        String msg = (String) response.getData();
        if (this.iViewModel != null) {
            this.iViewModel.showResponseMessage(msg);
            this.iViewModel.hideProgress();
        }
//        for (Map.Entry<String, Object> entry : response.entrySet()) {
//            //String key = entry.getKey();
//            String msg = (String) entry.getValue();
//            if (this.iViewModel != null) {
//                this.iViewModel.showResponseMessage(msg);
//                this.iViewModel.hideProgress();
//            }
//        }
    }
}

