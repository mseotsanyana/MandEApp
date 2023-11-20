package com.me.mseotsanyana.mande.infrastructure.ports.session;

import android.widget.EditText;
import android.widget.TextView;

import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

public interface iChangePasswordPresenter extends iPresenter {
    interface View extends IBaseView {
        void onChangePasswordSucceeded(String msg);
        void onChangePasswordFailed(String msg);

        EditText getOldPasswordEditText();
        EditText getNewPasswordEditText();
        TextView getConfirmPasswordTextView();
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void changePassword(String password);
}

