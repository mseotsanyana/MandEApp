package com.me.mseotsanyana.mande.application.interactors.preference;


//public class CClearAllPreferenceSettingsInteractorImpl extends cAbstractInteractor
//        implements IOrganizationWorkspaceInteractor {
//    //private static String TAG = CClearAllPreferenceSettingsInteractorImpl.class.getSimpleName();
//    private final ISessionManager sharedPreferenceRepository;
//    //private final IOrganizationWorkspacePresenter organizationWorkspacePresenter;
//
//    public CClearAllPreferenceSettingsInteractorImpl(
//            IExecutor threadExecutor, IMainThread mainThread,
//            IOrganizationWorkspacePresenter organizationWorkspacePresenter,
//            ISessionManager sharedPreferenceRepository) {
//        super(threadExecutor, mainThread, null);
//
//        if (sharedPreferenceRepository != null && organizationWorkspacePresenter == null) {
//            throw new IllegalArgumentException("Arguments can not be null!");
//        }
//        this.sharedPreferenceRepository = sharedPreferenceRepository;
//        this.organizationWorkspacePresenter = organizationWorkspacePresenter;
//    }
//
//    /* preference failed to be cleared */
//    private void notifyError() {
//        mainThread.post(() -> organizationWorkspacePresenter.
//                OnPreferenceClearedFailed("Preference Settings Clearance Failed!"));
//    }
//
//    /* preference successfully cleared */
//    private void postMessage() {
//        mainThread.post(() -> organizationWorkspacePresenter.
//                OnPreferenceClearedSucceeded("Preference Settings Successfully Cleared."));
//    }
//
//    @Override
//    public void run() {
//        sharedPreferenceRepository.clearAllSettings();
//        if (sharedPreferenceRepository.loadUserID() == null){
//            postMessage();
//        }else {
//            notifyError();
//        }
//    }
//}
