package com.me.mseotsanyana.mande.application.interactors.session.userprofile;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;

import java.util.List;

public interface iUserProfilesInteractor extends iInteractor {
    interface Callback {
        /* read user profiles use case */
        void onReadUserProfilesFailed(String msg);
        void onReadUserProfilesSucceeded(List<CUserProfileModel> userProfileModels);

        /* upload user profile use case */
        void onUploadUserProfilesSucceeded(String msg);
        void onUploadUserProfilesFailed(String msg);

        /* update user profile use case */
        void onUpdateUserProfileImageSucceeded(String msg);
        void onUpdateUserProfileImageFailed(String msg);
    }
}
