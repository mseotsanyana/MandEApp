package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IController;

public interface IUserProfileController extends IController {
    /* implemented in either Fragments and/or Activities. Called in PresenterImpl */
    interface IViewModel<R> extends IBaseView{
        void showResponse(R response);
//        void OnUserLoginSucceeded(String msg);
//        void OnUserSignOutSucceeded(String msg);

//        EditText getEmailEditText();
//        EditText getPasswordEditText();
//        TextView getResetPasswordTextView();
//
//        String getResourceString(int resourceID);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void createUserWithEmailAndPassword(CUserProfileModel userProfileModel);
    void signInWithEmailAndPassword(String email, String password);
    void signOutWithEmailAndPassword();

    void readUserProfile();
    void readUserProfiles();
    void updateUserProfile(CUserProfileModel userProfileModel);
    void updateUserProfileImage(String userServerID, byte[] userProfileImageData);

    void uploadUserProfilesFromExcel(String filename);

}

