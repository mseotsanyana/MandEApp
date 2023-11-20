package com.me.mseotsanyana.mande.application.interactors.session.organization;

import android.util.Log;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.ports.session.IOrganizationInteractor;
import com.me.mseotsanyana.mande.application.repository.session.IOrganizationRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreChildCallBack;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;

import java.util.List;
import java.util.Map;

public class CReadOrganizationAccountsInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>> implements
        IOrganizationInteractor {
    private static final String TAG = CReadOrganizationAccountsInteractorImpl.class.getSimpleName();

    private final AccountsCallback callback;
    private final IOrganizationRepository organizationRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public CReadOrganizationAccountsInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                                   AccountsCallback callback,
                                                   ISessionManager sharedPreferenceRepository,
                                                   IOrganizationRepository organizationRepository) {
        super(threadExecutor, mainThread, null);

        if (sharedPreferenceRepository == null || organizationRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        // initialise objects
        this.organizationRepository = organizationRepository;
        this.callback = callback;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadLoggedInUserServerID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                CPreferenceConstant.SESSION);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                CPreferenceConstant.SESSION, CPreferenceConstant.USERACCOUNT);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                CPreferenceConstant.SESSION, CPreferenceConstant.USERACCOUNT,
                CPreferenceConstant.READ);

        Log.d(TAG, " \n USER ID = " + this.userServerID +
                " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadOrganizationAccountsFailed(msg));
    }

    /* */
    private void postMessage(Map<String, Object> orgAccountMap, String operation) {
        mainThread.post(() -> callback.onReadOrganizationAccountsSucceeded(orgAccountMap, operation));
    }

    @Override
    public void run() {
        //if ((this.entityBITS & cSharedPreference.USERACCOUNT) != 0) {
        //    if ((this.entitypermBITS & cSharedPreference.READ) != 0) {
        organizationRepository.readOrganizationAccounts(organizationServerID,
                userServerID, primaryTeamBIT, secondaryTeamBITS, statusBITS,
                new CFirestoreChildCallBack() {
                    @Override
                    public void onChildAdded(Object object) {
                        if (object != null) {
                            Map<String, Object> orgAccountMap;
                            orgAccountMap = (Map<String, Object>) object;
                            postMessage((Map<String, Object>) orgAccountMap, "ADD");
                        }
                    }

                    @Override
                    public void onChildChanged(Object object) {

                    }

                    @Override
                    public void onChildRemoved(Object object) {

                    }

                    @Override
                    public void onCancelled(Object object) {

                    }
                });
//                {
//                    @Override
//                    public void onReadOrganizationAccountsSucceeded(
//                            List<Map<String, String>> orgAccountsMap) {
//                        postMessage(orgAccountsMap);
//                    }
//
//                    @Override
//                    public void onReadOrganizationAccountsFailed(String msg) {
//                        notifyError(msg);
//                    }
//                });
        // } else {
        //     notifyError("Permission denied! Please contact your administrator");
        //}
        //} else {
        //    notifyError("No access to the entity! Please contact your administrator");
        //}
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}
