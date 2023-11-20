package com.me.mseotsanyana.mande.application.interactors.session.organization;

import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirebaseChildCallBack;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.application.ports.session.IOrganizationInteractor;
import com.me.mseotsanyana.mande.application.repository.session.IOrganizationRepository;
import com.me.mseotsanyana.mande.application.preference.ISharedPreferenceRepository;


public class CReadOrganizationsInteractorImpl extends cAbstractInteractor implements
        IOrganizationInteractor {
    private static final String TAG = CReadOrganizationsInteractorImpl.class.getSimpleName();

    private final IOrganizationPresenter organizationPresenter;
    private final IOrganizationRepository organizationRepository;

//    private final String organizationServerID;
//    private
//    private final int primaryWorkspaceBIT;
//    private final List<Integer> secondaryWorkspaceBITS;
//    private final List<Integer> statusBITS;

//    private final int entityBITS;
//    private final int entitypermBITS;

    public CReadOrganizationsInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                            ISharedPreferenceRepository sharedPreferenceRepository,
                                            IOrganizationRepository organizationRepository,
                                            IOrganizationPresenter organizationPresenter) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || organizationRepository == null ||
                organizationPresenter == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        // initialise objects
        this.organizationRepository = organizationRepository;
        this.organizationPresenter = organizationPresenter;


        // load user shared preferences
//        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
//        this.primaryWorkspaceBIT = sharedPreferenceRepository.loadWorkspaceServerID();
//        this.secondaryWorkspaceBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();

//        // load entity shared preferences
//        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
//                cSharedPreference.SESSION_MODULE);
//        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
//                cSharedPreference.SESSION_MODULE, cSharedPreference.ORGANIZATION);
//        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
//                cSharedPreference.SESSION_MODULE, cSharedPreference.ORGANIZATION,
//                cSharedPreference.READ);
//
//        Log.d(TAG, " \n ORGANIZATION ID = " + this.organizationServerID +
//                " \n USER ID = " + this.userServerID +
//                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
//                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
//                " \n ENTITY BITS = " + this.entityBITS +
//                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
//                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> organizationPresenter.OnReadOrganizationsFailed(msg));
    }

    /* */
    private void postOrganization(COrganizationModel organizationModel, String operation) {
        mainThread.post(() -> organizationPresenter.OnReadOrganizationsSucceeded(
                organizationModel, operation));
    }

    private void readOrganizationWorkspaces() {
    }

    @Override
    public void run() {
        /*if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
            if ((this.entityBITS & cSharedPreference.ORGANIZATION) != 0) {
                if ((this.entitypermBITS & cSharedPreference.READ) != 0) {*/

        organizationRepository.readOrganizations(null, null,
                0, null, null,
                new CFirebaseChildCallBack() {
                    @Override
                    public void onChildAdded(Object object) {
                        if (object != null) {
                            postOrganization((COrganizationModel) object, "ADD");
                        } else {
                            notifyError("No organization found!");
                        }
                    }

                    @Override
                    public void onChildChanged(Object object) {
                        if (object != null) {
                            postOrganization((COrganizationModel) object, "UPDATED");
                        } else {
                            notifyError("No organization found!");
                        }
                    }

                    @Override
                    public void onChildRemoved(Object object) {
                        if (object != null) {
                            postOrganization((COrganizationModel) object, "DELETED");
                        } else {
                            notifyError("No organization found!");
                        }
                    }

                    @Override
                    public void onCancelled(Object object) {
                        notifyError(object.toString());
                    }
                });

                /*} else {
                    notifyError("Permission denied! Please contact your administrator");
                }
            } else {
                notifyError("No access to the entity! Please contact your administrator");
            }
        } else {
            notifyError("Error in default settings");
        }*/
    }
}