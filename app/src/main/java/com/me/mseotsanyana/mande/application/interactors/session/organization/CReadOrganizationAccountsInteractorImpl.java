package com.me.mseotsanyana.mande.application.interactors.session.organization.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.application.ports.base.executor.iExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.iMainThread;
import com.me.mseotsanyana.mande.application.ports.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.session.organization.iOrganizationInteractor;
import com.me.mseotsanyana.mande.application.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.application.preference.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.framework.db.firebase.cFirebaseChildCallBack;
import com.me.mseotsanyana.mande.infrastructure.preference.CSharedPreference;

import java.util.List;
import java.util.Map;

public class cReadOrganizationAccountsInteractorImpl extends cAbstractInteractor implements
        iOrganizationInteractor {
    private static final String TAG = cReadOrganizationAccountsInteractorImpl.class.getSimpleName();

    private final AccountsCallback callback;
    private final iOrganizationRepository organizationRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadOrganizationAccountsInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                                   AccountsCallback callback,
                                                   iSharedPreferenceRepository sharedPreferenceRepository,
                                                   iOrganizationRepository organizationRepository) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || organizationRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        // initialise objects
        this.organizationRepository = organizationRepository;
        this.callback = callback;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                CSharedPreference.SESSION_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                CSharedPreference.SESSION_MODULE, CSharedPreference.USERACCOUNT);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                CSharedPreference.SESSION_MODULE, CSharedPreference.USERACCOUNT,
                CSharedPreference.READ);

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
                new cFirebaseChildCallBack() {
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
}
