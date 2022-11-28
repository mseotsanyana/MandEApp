package com.me.mseotsanyana.mande.BLL.interactors.programme.logframe.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.cInteractorUtils;
import com.me.mseotsanyana.mande.BLL.interactors.programme.logframe.iLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.programme.iLogFrameRepository;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import java.util.List;

public class cDeleteLogFramesInteractorImpl extends cAbstractInteractor
        implements iLogFrameInteractor {
    private static final String TAG = cDeleteLogFramesInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iLogFrameRepository logFrameRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

//    private final List<String> logframe_ids;
//
//    private final List<String> logframe_components;
//
//    private final List<String> impact_components = new ArrayList<>();
//    private final List<String> outcome_components = new ArrayList<>();
//    private final List<String> output_components = new ArrayList<>();
//    private final List<String> activity_components = new ArrayList<>();
//    private final List<String> input_components = new ArrayList<>();
//
//    private final List<String> outcome_impacts = new ArrayList<>();
//    private final List<String> output_outcomes = new ArrayList<>();
//    private final List<String> activity_outputs = new ArrayList<>();
//    private final List<String> input_activities = new ArrayList<>();

    public cDeleteLogFramesInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                          iSharedPreferenceRepository sharedPreferenceRepository,
                                          iLogFrameRepository logFrameRepository,
                                          Callback callback) {
        super(threadExecutor, mainThread);

        if (logFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.logFrameRepository = logFrameRepository;
        this.callback = callback;
//        this.logframe_ids = logframe_ids;
//        this.logframe_components = components;

        // load user shared preferences
        this.organizationServerID = sharedPreferenceRepository.loadActiveOrganizationID();
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadActiveWorkspaceBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryWorkspaces();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.PROGRAMME_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.PROGRAMME_MODULE, cSharedPreference.LOGFRAME);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.PROGRAMME_MODULE, cSharedPreference.LOGFRAME,
                cSharedPreference.DELETE);

        Log.d(TAG, " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n USER ID = " + this.userServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY BITS = " + this.entityBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onDeleteLogFrameFailed(msg));
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(() -> callback.onDeleteLogFramesSucceeded(msg));
    }

//    Map<String, List<String>> getComponentsToDelete(Map<String, String> logframe_tree) {
//        // components to be deleted
//        Map<String, List<String>> component_partitions = new HashMap<>();
//
//        // logframes to be deleted
//        component_partitions.put("LOGFRAME", logframe_ids);
//
//        Log.d(TAG,"LOGFRAME COMPONENTS = "+logframe_components);
//        // compile logframe components to be deleted
///*        for (Map.Entry<String, String> map : logframe_components.entrySet()) {
//            switch (map.getValue()) {
//                case "IMPACT":
//                    impact_components.add(map.getKey());
//                    break;
//                case "OUTCOME":
//                    outcome_components.add(map.getKey());
//                    break;
//                case "OUTPUT":
//                    output_components.add(map.getKey());
//                    break;
//                case "ACTIVITY":
//                    activity_components.add(map.getKey());
//                    break;
//                case "INPUT":
//                    input_components.add(map.getKey());
//                    break;
//                default:
//                    break;
//            }
//        }*/
//        component_partitions.put("IMPACT", impact_components);
//        component_partitions.put("OUTCOME", outcome_components);
//        component_partitions.put("OUTPUT", output_components);
//        component_partitions.put("ACTIVITY", activity_components);
//        component_partitions.put("INPUT", input_components);
//
//        // compile logframe components to deleted
//        List<String> components = new ArrayList<>();
//        components.addAll(impact_components);
//        components.addAll(outcome_components);
//        components.addAll(output_components);
//        components.addAll(activity_components);
//        components.addAll(input_components);
//        component_partitions.put("COMPONENT", components);
//
//        // compile logframe components to be deleted
//        for (Map.Entry<String, String> map : logframe_tree.entrySet()) {
//            switch (map.getValue()) {
//                case "OUTCOME_IMPACT":
//                    outcome_impacts.add(map.getKey());
//                    break;
//                case "OUTPUT_OUTCOME":
//                    output_outcomes.add(map.getKey());
//                    break;
//                case "ACTIVITY_OUTPUT":
//                    activity_outputs.add(map.getKey());
//                    break;
//                case "INPUT_ACTIVITY":
//                    input_activities.add(map.getKey());
//                    break;
//                default:
//                    break;
//            }
//        }
//        component_partitions.put("OUTCOME_IMPACT", outcome_impacts);
//        component_partitions.put("OUTPUT_OUTCOME", output_outcomes);
//        component_partitions.put("ACTIVITY_OUTPUT", activity_outputs);
//        component_partitions.put("INPUT_ACTIVITY", input_activities);

        // compile preceding activities to be deleted
//                                List<String> preceding_activities = new ArrayList<>();
//                                for (int i = 0; i < activity_components.size() - 1; i++) {
//                                    String preceding = activity_components.get(i);
//                                    String succeeding = activity_components.get(i + 1);
//                                    preceding_activities.add(preceding + "_" + succeeding);
//                                    preceding_activities.add(succeeding + "_" + preceding);
//                                }
//                                component_partitions.put("PRECEDING_ACTIVITY",
//                                        preceding_activities);
//
//        return component_partitions;
//    }

//    void deleteLogFrameComponents(Map<String, List<String>> component_partitions) {
//        logFrameRepository.deleteLogFrames(organizationServerID, userServerID, primaryTeamBIT,
//                secondaryTeamBITS, statusBITS, component_partitions,
//                new iLogFrameRepository.iDeleteLogFramesCallback() {
//                    @Override
//                    public void onDeleteLogFramesSucceeded(String msg) {
//                        postMessage(msg);
//                    }
//
//                    @Override
//                    public void onDeleteLogFrameFailed(String msg) {
//                        notifyError(msg);
//                    }
//                });
//
//    }

    @Override
    public void run() {
        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
            if ((this.entityBITS & cSharedPreference.LOGFRAME) != 0) {
                if ((this.entitypermBITS & cSharedPreference.DELETE) != 0) {

                    logFrameRepository.deleteLogFrames(organizationServerID, userServerID,
                            new iLogFrameRepository.iDeleteLogFramesCallback() {
                        @Override
                        public void onDeleteLogFramesSucceeded(String msg) {
                            postMessage(msg);
                        }

                        @Override
                        public void onDeleteLogFrameFailed(String msg) {
                            notifyError(msg);
                        }
                    });


//                    Map<String, String> logframe_tree = new HashMap<>();
//
//                    //FIXME: 1. preceding and assignment activities
//                    //FIXME: 2. logframe list are sometimes empty?
//                    // How does the asynchronization callback return before the function complete?
//                    logFrameRepository.filterComponents(logframe_ids, new iLogFrameRepository.
//                            iFilterComponentsCallback() {
//                        @Override
//                        public void onFilterComponentsSucceeded(
//                                Map<String, String> logframetree, int num_calls) {
//
//                            logframe_tree.putAll(logframetree);
//
//                            if (logframe_ids.size() == num_calls) {
//
//                                Map<String, List<String>> component_partitions;
//                                component_partitions = getComponentsToDelete(logframe_tree);
//                                Log.d(TAG, " COMPONENTS = " + component_partitions);
//                                deleteLogFrameComponents(component_partitions);
//                            }
//                        }
//
//                        @Override
//                        public void onFilterComponentsFailed(String msg) {
//                            notifyError(msg);
//                        }
//                    });
                } else {
                    notifyError("Permission denied! Please contact your administrator");
                }
            } else {
                notifyError("No access to the entity! Please contact your administrator");
            }
        } else {
            notifyError("Error in default settings");
        }
    }
}
