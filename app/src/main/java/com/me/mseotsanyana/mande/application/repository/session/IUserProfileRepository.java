package com.me.mseotsanyana.mande.application.repository.session;

import com.google.firebase.firestore.ListenerRegistration;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cMenuModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.framework.storage.base.cFirebaseChildCallBack;

import java.util.List;

public interface iUserProfileRepository {
    void createUserWithEmailAndPassword(CUserProfileModel userProfileModel,
                                        iSignUpRepositoryCallback callback);
    interface iSignUpRepositoryCallback{
        void onSignUpSucceeded(String msg);
        void onSignUpFailed(String msg);
    }

    void signOutWithEmailAndPassword(iSignOutRepositoryCallback callback);
    interface iSignOutRepositoryCallback{
        void onSignOutSucceeded(String msg);
        void onSignOutFailed(String msg);
    }

    void readMyUserProfile(iReadMyUserProfileRepositoryCallback callback);
    interface iReadMyUserProfileRepositoryCallback {
        void onReadMyUserProfileSucceeded(CUserProfileModel userProfileModel);
        void onReadMyUserProfileFailed(String msg);
    }

    void readUserProfiles(iReadUserProfilesRepositoryCallback callback);
    interface iReadUserProfilesRepositoryCallback {
        void onReadUserProfilesSucceeded(List<CUserProfileModel> userProfileModels);
        void onReadUserProfilesFailed(String msg);
    }

    ListenerRegistration readAllUserProfilesByChildEvent(cFirebaseChildCallBack firebaseChildCallBack);

    void signInWithEmailAndPassword(String email, String password,
                                    iSignInRepositoryCallback callback);
    interface iSignInRepositoryCallback{
        void onSignInSucceeded(String msg);
        void onSignInFailed(String msg);
    }

    void updateUserProfileImage(long userID, int primaryRole, int secondaryRoles, int statusBITS,
                                CUserProfileModel userProfileModel,
                                iUpdateUserProfileRepositoryCallback callback);

    interface iUpdateUserProfileRepositoryCallback{
        void onUpdateUserProfileSucceeded(String msg);
        void onUpdateUserProfileFailed(String msg);
    }

    void updateUserProfileImage(String userServerID, byte[] userProfileImageData,
                                iUpdateUserProfileImageRepositoryCallback callback);

    interface iUpdateUserProfileImageRepositoryCallback{
        void onUpdateUserProfileImageSucceeded(Object object);
        void onUpdateUserProfileImageFailed(Object object);
    }

    void uploadUserProfilesFromExcel(String filename,
                                     iUploadUserProfilesRepositoryCallback callback);

    interface iUploadUserProfilesRepositoryCallback {
        void onUploadUserProfilesSucceeded(String msg);
        void onUploadUserProfilesFailed(String msg);
    }

    void sendPasswordResetEmail(CUserProfileModel userProfileModel,
                                iResetPasswordRepositoryCallback callback);

    interface iResetPasswordRepositoryCallback{
        void onResetPasswordSucceeded(String msg);
        void onResetPasswordFailed(String msg);
    }

    void changePassword(CUserProfileModel userProfileModel,
                        iChangePasswordRepositoryCallback callback);

    interface iChangePasswordRepositoryCallback{
        void onChangePasswordSucceeded(String msg);
        void onChangePasswordFailed(String msg);
    }

    // save user permissions to preference file
    void saveUserPermissions(CWorkspaceModel workspaceModel, iSaveUserPermissionsCallback callback);

    interface iSaveUserPermissionsCallback {
        void onSaveUserPermissionsSucceeded(String msg);

        void onSaveUserPermissionsFailed(String msg);

        void onSaveOrganizationServerID(String organizationServerID);

        void onSaveOrganizationOwnerServerID(String organizationOwnerServerID);

        void onSaveCompositeServerID(String compositeServerID);

        void onSaveWorkspaceServerID(String workspaceServerID);

        void onSaveUserServerID(String userServerID);

        void onSaveOwnerID(String ownerServerID);

        void onSaveWorkspaceOwnerBIT(int workspaceOwnerBIT);

        void onSaveWorkspaceMembershipBITS(int workspaceMembershipBITS);

        void onSaveMyOrganizations(List<String> organizations);

        void onSaveMenuItems(List<cMenuModel> menuModels);

        void onSaveModuleBITS(int moduleBITS);

        void onSaveEntityBITS(String moduleKey, int entityBITS);

        void onSaveActionBITS(int moduleKey, int entityKey, int actionBITS);

        void onSaveStatusBITS(String moduleKey, String entityKey, String actionKey, int statusBITS);

        void onSavePermissionBITS(String moduleKey, String entityKey, int permBITS);

    }

    // clear user permissions from preference file
    void clearUserPermissions(CWorkspaceModel workspaceModel,
                              iClearUserPermissionsCallback callback);

    interface iClearUserPermissionsCallback {
        void onClearUserPermissionsSucceeded(String msg);

        void onClearUserPermissionsFailed(String msg);

        void onClearUserPermissions();
    }
}
