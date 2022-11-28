package com.me.mseotsanyana.mande.PL.presenters.session;

import android.widget.EditText;
import android.widget.TextView;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iChangePasswordPresenter extends iPresenter {
    interface View extends iBaseView {
        void onChangePasswordSucceeded(String msg);
        void onChangePasswordFailed(String msg);

        EditText getOldPasswordEditText();
        EditText getNewPasswordEditText();
        TextView getConfirmPasswordTextView();
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void changePassword(String password);
}

