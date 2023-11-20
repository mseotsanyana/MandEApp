package com.me.mseotsanyana.mande.application.repository.session;

import com.google.firebase.firestore.ListenerRegistration;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CMenuModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreChildCallBack;

import java.util.HashMap;
import java.util.List;

public interface IUserProfileRepository {
    //void createEmployee(Employee employee, CFireCallBack callBack);

    /*void updateEmployee(String employeeKey, HashMap map, CallBack callBack);

    void deleteEmployee(String employeeKey, CallBack callBack);

    void readEmployeeByKey(String employeeKey, CallBack callBack);

    void readEmployeeByName(String employeeName, CallBack callBack);

    void readAllEmployeesBySingleValueEvent(CallBack callBack);

    FirebaseRequestModel readAllEmployeesByDataChangeEvent(CallBack callBack);

    FirebaseRequestModel readAllEmployeesByChildEvent(FirebaseChildCallBack firebaseChildCallBack);*/


    void signOutWithEmailAndPassword(CFirestoreCallBack callback);

    void signInWithEmailAndPassword(String email, String password, CFirestoreCallBack callback);

    void createUserWithEmailAndPassword(CUserProfileModel userProfileModel, CFirestoreCallBack callback);

    void readUserProfileByID(String userServerID, CFirestoreCallBack callback);

    ListenerRegistration readAllUserProfilesByChildEvent(CFirestoreChildCallBack firebaseChildCallBack);

    void updateUserProfile(String userServerID, HashMap<String, Object> map,
                           CFirestoreCallBack callBack);

    void deleteUserProfile(String userServerID, CFirestoreCallBack callback);

    // save user permissions to preference file
    void saveUserPermissions(CWorkspaceModel workspaceModel, ISaveUserPermissionsCallback callback);

    interface ISaveUserPermissionsCallback {
        void onSaveUserPermissionsSucceeded(String msg);

        void onSaveUserPermissionsFailed(String msg);

        void onSaveOrganizationServerID(String organizationServerID);

        void onSaveOrganizationOwnerServerID(String organizationOwnerServerID);

        void onSaveCompositeServerID(String compositeServerID);

        void onSaveWorkspaceServerID(String workspaceServerID);

        void onSaveUserServerID(String userServerID);

        void onSaveOwnerServerID(String ownerServerID);

        void onSaveWorkspaceOwnerBIT(int workspaceOwnerBIT);

        void onSaveWorkspaceMembershipBITS(int workspaceMembershipBITS);

        void onSaveMyOrganizations(List<String> organizations);

        void onSaveMenuItems(List<CMenuModel> menuModels);

        void onSaveModuleBITS(int moduleBITS);

        void onSaveEntityBITS(String moduleKey, int entityBITS);

        void onSaveActionBITS(int moduleKey, int entityKey, int actionBITS);

        void onSaveStatusBITS(String moduleKey, String entityKey, String actionKey, int statusBITS);

        void onSavePermissionBITS(String moduleKey, String entityKey, int permBITS);

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

//    // clear user permissions from preference file
//    void clearUserPermissions(CWorkspaceModel workspaceModel,
//                              iClearUserPermissionsCallback callback);
//
//    interface iClearUserPermissionsCallback {
//        void onClearUserPermissionsSucceeded(String msg);
//
//        void onClearUserPermissionsFailed(String msg);
//
//        void onClearUserPermissions();
//    }
}
