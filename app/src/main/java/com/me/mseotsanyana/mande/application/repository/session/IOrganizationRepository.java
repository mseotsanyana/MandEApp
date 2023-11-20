package com.me.mseotsanyana.mande.application.repository.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.framework.storage.base.cFirebaseCallBack;
import com.me.mseotsanyana.mande.framework.storage.base.cFirebaseChildCallBack;

import java.util.List;

public interface iOrganizationRepository {
    // CREATE
    void createOrganization(COrganizationModel organizationModel,
                            iCreateOrganizationCallback callback);

    interface iCreateOrganizationCallback {
        void onCreateOrganizationSucceeded(String msg);

        void onCreateOrganizationFailed(String msg);
    }

    // READ
    void readOrganizations(String organizationServerID, String userServerID, int primaryWorkspaceBIT,
                           List<Integer> secondaryWorkspaceBITS, List<Integer> statusBITS,
                           cFirebaseChildCallBack firebaseChildCallBack);

    void readOrganizationAccounts(String organizationServerID, String userServerID,
                                  int primaryWorkspaceBIT, List<Integer> secondaryWorkspaceBITS,
                                  List<Integer> statusBITS,
                                  cFirebaseChildCallBack firebaseChildCallBack);

    void readOrganizationAccounts(String organizationServerID, String userServerID,
                                  int primaryTeamBIT, List<Integer> secondaryWorkspaceBITS,
                                  List<Integer> statusBITS, cFirebaseCallBack firebaseCallBack);

    void readOrganizationAgreements(String organizationServerID, String userServerID,
                                  int primaryTeamBIT, List<Integer> secondaryWorkspaceBITS,
                                  List<Integer> statusBITS, cFirebaseCallBack firebaseCallBack);

    void readOrganizationWorkspaces(cFirebaseCallBack firebaseCallBack);

    void removeListener();

//    interface iReadOrganizationAccountsCallback {
//        void onReadOrganizationAccountsSucceeded(List<Map<String, String>> userAccountsMap);
//
//        void onReadOrganizationAccountsFailed(String msg);
//    }
//



//    interface iReadOrganizationAccountsCallback {
//        void onReadOrganizationAccountsSucceeded(List<COrganizationModel> stakeholderModels);
//
//        void onReadOrganizationAccountsFailed(String msg);
//    }

}
