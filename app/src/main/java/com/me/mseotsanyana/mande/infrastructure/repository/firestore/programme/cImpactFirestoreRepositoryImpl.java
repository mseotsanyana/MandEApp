package com.me.mseotsanyana.mande.interfaceadapters.repository.firestore.programme;

import android.content.Context;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cImpactModel;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.usecases.repository.programme.iImpactRepository;
import com.me.mseotsanyana.mande.framework.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.interfaceadapters.repository.cDatabaseUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cImpactFirestoreRepositoryImpl implements iImpactRepository {
    /* private static SimpleDateFormat sdf = cConstant.TIMESTAMP_FORMAT_DATE;
    private static String TAG = cImpactFirestoneRepositoryImpl.class.getSimpleName();*/

    private final FirebaseFirestore db;

    public cImpactFirestoreRepositoryImpl(Context context) {
        this.db = FirebaseFirestore.getInstance();
    }


    /* ##################################### CREATE ACTIONS ##################################### */



    /* ###################################### READ ACTIONS ###################################### */

    @Override
    public void readImpacts(String logframeServerID, String organizationServerID, String userServerID,
                            int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                            List<Integer> statusBITS, iReadImpactsCallback callback) {

        CollectionReference coImpactRef = db.collection(cRealtimeHelper.KEY_LOGFRAME_COMPONENTS);

        Query impactQuery = coImpactRef
                .whereEqualTo("projectServerID", logframeServerID)
                .whereEqualTo("type", "IMPACT")
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);

        impactQuery.get()
                .addOnCompleteListener(task -> {

                    List<cImpactModel> impactModels = new ArrayList<>();
                    List<String> sub_impact_ids = new ArrayList<>();
                    for (DocumentSnapshot impact_doc : Objects.requireNonNull(task.getResult())) {
                        cImpactModel impactModel = impact_doc.toObject(cImpactModel.class);

                        if (impactModel != null) {
                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                            perm.setUserOwnerID(impactModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(impactModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(impactModel.getUnixpermBITS());

//FIXME                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                                    secondaryTeamBITS)) {
//                                impactModels.add(impactModel);
//
//                                //sub_impact_ids = getSubImpactIDs(impactModel.getComponentServerID());
//                                //impactModel.setChildModels(getSubImpactModels(sub_impact_ids));
//                            }
                        }
                    }

                    // populate outcomes of the impact
                    populateOutcomeModels(logframeServerID, organizationServerID, userServerID,
                            primaryTeamBIT, secondaryTeamBITS, statusBITS, impactModels, callback);

                })
                .addOnFailureListener(e -> callback.onReadImpactFailed(
                        "Failed to read impacts"));
    }

    private void populateSubImpactModels(String logframeServerID, String organizationServerID,
                                       String userServerID, int primaryTeamBIT,
                                       List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
                                       List<cImpactModel> impactModels,
                                       iReadImpactsCallback callback) {

        CollectionReference coOutcomeRef = null;//db.collection(cRealtimeHelper.KEY_COMPONENT_OUTCOMES);

        Query outcomeQuery = coOutcomeRef
                .whereEqualTo("projectServerID", logframeServerID)
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);

        outcomeQuery.get()
                .addOnCompleteListener(task -> {
                    for (cImpactModel impactModel : impactModels) {
                        List<cOutcomeModel> outcomeModels = new ArrayList<>();
                        for (DocumentSnapshot outcome_doc : Objects.requireNonNull(task.getResult())) {
                            cOutcomeModel outcomeModel = outcome_doc.toObject(cOutcomeModel.class);
                            if (outcomeModel != null) {
                                String outcomeImpactID = outcomeModel.getImpactServerID();
                                if (outcomeImpactID.equals(impactModel.getComponentServerID())) {
                                    cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                                    perm.setUserOwnerID(outcomeModel.getUserOwnerID());
                                    perm.setTeamOwnerBIT(outcomeModel.getTeamOwnerBIT());
                                    perm.setUnixpermBITS(outcomeModel.getUnixpermBITS());

//FIXME                                    if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                                            secondaryTeamBITS)) {
//                                        outcomeModels.add(outcomeModel);
//                                    }
                                }
                            }
                        }

                        impactModel.setOutcomeModels(outcomeModels);
                    }

                    // call back on logframes
                    callback.onReadImpactSucceeded(impactModels);
                })
                .addOnFailureListener(e -> callback.onReadImpactFailed(
                        "Failed to read outcomes"));
    }

    private void populateOutcomeModels(String logframeServerID, String organizationServerID,
                                       String userServerID, int primaryTeamBIT,
                                       List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
                                       List<cImpactModel> impactModels,
                                       iReadImpactsCallback callback) {

        CollectionReference coOutcomeRef = db.collection(cRealtimeHelper.KEY_LOGFRAME_COMPONENTS);

        Query outcomeQuery = coOutcomeRef
                .whereEqualTo("projectServerID", logframeServerID)
                .whereEqualTo("type", "OUTCOME")
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);

        outcomeQuery.get()
                .addOnCompleteListener(task -> {
                    for (cImpactModel impactModel : impactModels) {
                        List<cOutcomeModel> outcomeModels = new ArrayList<>();
                        for (DocumentSnapshot outcome_doc : Objects.requireNonNull(task.getResult())) {
                            cOutcomeModel outcomeModel = outcome_doc.toObject(cOutcomeModel.class);
                            if (outcomeModel != null) {
                                String outcomeImpactID = outcomeModel.getImpactServerID();
                                if (outcomeImpactID.equals(impactModel.getComponentServerID())) {
                                    cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                                    perm.setUserOwnerID(outcomeModel.getUserOwnerID());
                                    perm.setTeamOwnerBIT(outcomeModel.getTeamOwnerBIT());
                                    perm.setUnixpermBITS(outcomeModel.getUnixpermBITS());

//FIXME                                    if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                                            secondaryTeamBITS)) {
//                                        outcomeModels.add(outcomeModel);
//                                    }
                                }
                            }
                        }

                        impactModel.setOutcomeModels(outcomeModels);
                    }

                    // call back on logframes
                    callback.onReadImpactSucceeded(impactModels);
                })
                .addOnFailureListener(e -> callback.onReadImpactFailed(
                        "Failed to read outcomes"));
    }

    /* ##################################### UPDATE ACTIONS ##################################### */


    /* ##################################### DELETE ACTIONS ##################################### */

}
