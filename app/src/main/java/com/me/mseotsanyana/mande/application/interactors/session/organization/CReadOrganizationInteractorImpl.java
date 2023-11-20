package com.me.mseotsanyana.mande.application.interactors.session.organization;

import com.me.mseotsanyana.mande.application.exceptions.CGeneralException;
import com.me.mseotsanyana.mande.application.ports.base.IInteractor;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreChildCallBack;
import com.me.mseotsanyana.mande.application.structures.CResponseDTO;
import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.repository.session.IOrganizationRepository;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.application.structures.enums.EAction;

public class CReadOrganizationInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements IInteractor {
    private static final String TAG = CReadOrganizationInteractorImpl.class.getSimpleName();

    private final IPresenter<IResponseDTO<Object>> iPresenter;
    private final IOrganizationRepository organizationRepository;

//    private final String organizationServerID;
//    private
//    private final int primaryWorkspaceBIT;
//    private final List<Integer> secondaryWorkspaceBITS;
//    private final List<Integer> statusBITS;

//    private final int entityBITS;
//    private final int entitypermBITS;

    public CReadOrganizationInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                           ISessionManager sessionManager,
                                           IPresenter<IResponseDTO<Object>> iPresenter,
                                           IOrganizationRepository organizationRepository) {
        super(threadExecutor, mainThread, sessionManager);

        if (iPresenter == null || organizationRepository == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        // initialise objects
        this.iPresenter = iPresenter;
        this.organizationRepository = organizationRepository;


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

    @Override
    public void postResult(IResponseDTO<Object> resultMap) {
        mainThread.post(() -> {
            iPresenter.onSuccess(resultMap);
        });
    }

    @Override
    public void postError(String errorMessage) {
        mainThread.post(() -> iPresenter.onError(new CGeneralException(errorMessage)));
    }

    @Override
    public void run() {
        /*if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
            if ((this.entityBITS & cSharedPreference.ORGANIZATION) != 0) {
                if ((this.entitypermBITS & cSharedPreference.READ) != 0) {*/

        organizationRepository.readOrganizations(null, null,
                0, null, null,
                new CFirestoreChildCallBack() {
                    @Override
                    public void onChildAdded(Object object) {
                        if (object != null) {
                            IResponseDTO<Object> responseModel;
                            responseModel = new CResponseDTO<>(EAction.Added_ORGANIZATION, object);
                            postResult(responseModel);
                        } else {
                            postError("No organization found!");
                        }
                    }

                    @Override
                    public void onChildChanged(Object object) {
                        if (object != null) {
                            IResponseDTO<Object> responseModel;
                            responseModel = new CResponseDTO<>(EAction.Modified_ORGANIZATION, object);
                            postResult(responseModel);
                        } else {
                            postError("No organization found!");
                        }
                    }

                    @Override
                    public void onChildRemoved(Object object) {
                        if (object != null) {
                            IResponseDTO<Object> responseModel;
                            responseModel = new CResponseDTO<> (EAction.Deleted_ORGANIZATION, object);
                            postResult(responseModel);
                        } else {
                            postError("No organization found!");
                        }
                    }

                    @Override
                    public void onCancelled(Object object) {
                        postError(object.toString());
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