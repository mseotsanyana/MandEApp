package com.me.mseotsanyana.mande.application.repository.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreChildCallBack;

import java.util.List;

public interface IOrganizationRepository {
    // create methods
    void createOrganization(COrganizationModel organizationModel,
                            CFirestoreCallBack firestoreCallBack);

    // read methods
    void readOrganizations(String organizationServerID, String userServerID,
                           int primaryWorkspaceBIT, List<Integer> secondaryWorkspaceBITS,
                           List<Integer> statusBITS, CFirestoreChildCallBack firebaseChildCallBack);

    void updateOrganization(COrganizationModel organizationModel, CFirestoreCallBack firestoreCallBack);

    // delete methods
    void deleteOrganization(String organizationServerID, CFirestoreCallBack firestoreCallBack);

    void readOrganizationAccounts(String organizationServerID, String userServerID,
                                  int primaryWorkspaceBIT, List<Integer> secondaryWorkspaceBITS,
                                  List<Integer> statusBITS,
                                  CFirestoreChildCallBack firebaseChildCallBack);

    void readOrganizationAccounts(String organizationServerID, String userServerID,
                                  int primaryTeamBIT, List<Integer> secondaryWorkspaceBITS,
                                  List<Integer> statusBITS, CFirestoreCallBack firebaseCallBack);

    void readOrganizationAgreements(String organizationServerID, String userServerID,
                                  int primaryTeamBIT, List<Integer> secondaryWorkspaceBITS,
                                  List<Integer> statusBITS, CFirestoreCallBack firebaseCallBack);

    void readOrganizationWorkspaces(CFirestoreCallBack firebaseCallBack);

    // update methods
//    void updateOrganization(String organizationServerID, String userServerID,
//                            int primaryWorkspaceBIT, List<Integer> secondaryWorkspaceBITS,
//                            List<Integer> statusBITS, CFirestoreChildCallBack firebaseChildCallBack);
//

    // Auxiliary methods
    void removeListener();
}
