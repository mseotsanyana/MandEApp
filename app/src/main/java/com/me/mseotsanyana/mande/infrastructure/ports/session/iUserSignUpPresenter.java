package com.me.mseotsanyana.mande.infrastructure.ports.session;

import android.widget.EditText;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

public interface iUserSignUpPresenter extends iPresenter {
    /* implemented in either Fragments and/or Activities. Called in PresenterImpl */
    interface View extends IBaseView {
        void onUserSignUpFailed(String msg);
        void onUserSignUpSucceeded(String msg);

        EditText getEmailEditText();
        EditText getPasswordEditText();
        EditText getConfirmPasswordEditText();

        String getResourceString(int resourceID);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void createUserWithEmailAndPassword(CUserProfileModel userProfileModel);
}

