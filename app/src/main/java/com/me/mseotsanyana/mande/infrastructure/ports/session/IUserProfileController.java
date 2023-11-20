package com.me.mseotsanyana.mande.infrastructure.ports.session;

import android.widget.EditText;
import android.widget.TextView;

import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IController;

public interface IUserLoginController extends IController {
    /* implemented in either Fragments and/or Activities. Called in PresenterImpl */
    interface IViewModel extends IBaseView {
        void OnUserLoginSucceeded(String msg);
        void OnUserLoginFailed(String msg);

//        EditText getEmailEditText();
//        EditText getPasswordEditText();
//        TextView getResetPasswordTextView();
//
//        String getResourceString(int resourceID);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void signInWithEmailAndPassword(String email, String password);
}

