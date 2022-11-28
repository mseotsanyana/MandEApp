package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

import java.util.List;

public interface iUserProfilePresenter extends iPresenter {
    interface View extends iBaseView {
        void onClickUserProfileImage(CUserProfileModel userProfileModel);

        void onUploadUserProfilesFailed(String msg);
        void onUploadUserProfilesSucceeded(String msg);

        void onReadUserProfilesFailed(String msg);
        void onReadUserProfilesSucceeded (List<CUserProfileModel> userProfileModels);

        void onUpdateUserProfileImageFailed(String msg);
        void onUpdateUserProfileImageSucceeded(String msg);
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void uploadUserProfilesFromExcel(String filename);
    void readUserProfiles();
    void updateUserProfileImage(String userServerID, byte[] userProfileImageData);
}

