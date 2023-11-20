package com.me.mseotsanyana.mande.infrastructure.repository.firestore.programme;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cProjectModel;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cResourceTypeModel;
import com.me.mseotsanyana.mande.domain.entities.models.utils.CCommonAttributeModel;
import com.me.mseotsanyana.mande.application.repository.programme.iLogFrameRepository;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreRepository;
import com.me.mseotsanyana.mande.application.structures.CFirestoreConstant;
import com.me.mseotsanyana.mande.OLD.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.application.utils.CFirestoreUtility;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cLogFrameFirestoreRepositoryImpl extends CFirestoreRepository
        implements iLogFrameRepository {
    private static final String TAG = cLogFrameFirestoreRepositoryImpl.class.getSimpleName();

    private final cExcelHelper excelHelper;
    // an object of the database helper
    private final FirebaseFirestore database;
    //private final FirebaseAuth firebaseAuth;
    private final Context context;

    public cLogFrameFirestoreRepositoryImpl(Context context) {
        this.context = context;
        //this.firebaseAuth = FirebaseAuth.getInstance();
        this.database = FirebaseFirestore.getInstance();

        excelHelper = new cExcelHelper(context);
    }

    /* ##################################### CREATE ACTIONS ##################################### */

    @Override
    public boolean createLogFrameModel(cLogFrameModel logFrameModel) {
        return false;
    }

    @Override
    public boolean createSubLogFrameModel(String logFrameID, cLogFrameModel logFrameModel) {
        return false;
    }

    /* ###################################### READ ACTIONS ###################################### */

    /**
     * read logframes
     *
      * @param organizationServerID organization identification
     * @param userServerID user identification
     * @param primaryTeamBIT primary team bit
     * @param secondaryTeamBITS secondary team bits
     * @param statusBITS status bits
     * @param callback call back
     */
    @Override
    public void readLogFrames(String organizationServerID, String userServerID,
                              int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                              List<Integer> statusBITS, iReadLogFramesCallback callback) {

        CollectionReference coLogFrameRef = database.collection(CFirestoreConstant.KEY_LOGFRAMES);

        Query logframeQuery = coLogFrameRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);

        logframeQuery.get()
                .addOnCompleteListener(task -> {
                    List<cLogFrameModel> logFrameModels = new ArrayList<>();
                    for (DocumentSnapshot logframe_doc : Objects.requireNonNull(task.getResult())) {
                        cLogFrameModel logFrameModel = logframe_doc.toObject(cLogFrameModel.class);

                        if (logFrameModel != null) {
                            CFirestoreUtility.cUnixPerm perm = new CFirestoreUtility.cUnixPerm();
                            perm.setUserOwnerID(logFrameModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(logFrameModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(logFrameModel.getUnixpermBITS());

//FIXME                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                                    secondaryTeamBITS)) {
//                                logFrameModels.add(logFrameModel);
//                            }
                        }
                    }


                    // call back on logframes
                    callback.onReadLogFrameSucceeded(logFrameModels);
                })
                .addOnFailureListener(e -> callback.onReadLogFrameFailed(
                        "Failed to read logframes"));
    }

    @Override
    public void readLogFrame(String organizationServerID, String userServerID, int primaryTeamBIT,
                             List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
                             String projectServerID, iReadLogFramesCallback callback) {
        CollectionReference coLogFrameRef = database.collection(CFirestoreConstant.KEY_LOGFRAMES);

        Query logframeQuery = coLogFrameRef
                .whereEqualTo("projectServerID", projectServerID)
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);

        logframeQuery.get()
                .addOnCompleteListener(task -> {
                    List<cLogFrameModel> logFrameModels = new ArrayList<>();
                    for (DocumentSnapshot logframe_doc : Objects.requireNonNull(task.getResult())) {
                        cLogFrameModel logFrameModel = logframe_doc.toObject(cLogFrameModel.class);

                        if (logFrameModel != null) {
                            CFirestoreUtility.cUnixPerm perm = new CFirestoreUtility.cUnixPerm();
                            perm.setUserOwnerID(logFrameModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(logFrameModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(logFrameModel.getUnixpermBITS());

//FIXME                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                                    secondaryTeamBITS)) {
//                                logFrameModels.add(logFrameModel);
//                            }
                        }
                    }

                    // call back on logframes
                    callback.onReadLogFrameSucceeded(logFrameModels);
                })
                .addOnFailureListener(e -> callback.onReadLogFrameFailed(
                        "Failed to read logframes"));
    }

    /* ##################################### UPDATE ACTIONS ##################################### */

    @Override
    public boolean updateLogFrameModel(cLogFrameModel logFrameModel) {
        return false;
    }

    /* ##################################### DELETE ACTIONS ##################################### */

    @Override
    public boolean deleteLogFrame(String logFrameID) {
        return false;
    }

    /**
     * delete programmes/projects or logframes and components
     *
     * @param organizationServerID organization identification
     * @param userServerID user identification
     * @param callback call back
     */
    @Override
    public void deleteLogFrames(String organizationServerID, String userServerID,
                                iDeleteLogFramesCallback callback) {

        CollectionReference coResourceTypeRef, coProjectRef, coLogFrameRef, coComponentRef;

        // resource types, projects, logframes with corresponding components
        coProjectRef = database.collection(CFirestoreConstant.KEY_PROJECTS);
        coLogFrameRef = database.collection(CFirestoreConstant.KEY_LOGFRAMES);
        coComponentRef = database.collection(CFirestoreConstant.KEY_LOGFRAME_COMPONENTS);
        coResourceTypeRef = database.collection(CFirestoreConstant.KEY_RESOURCETYPES);

        Task<QuerySnapshot> resources = coResourceTypeRef
                .whereEqualTo("userOwnerID", userServerID)
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereArrayContains("unixpermBITS", CPreferenceConstant.OWNER_DELETE)
                .get();

        Task<QuerySnapshot> projects = coProjectRef
                .whereEqualTo("userOwnerID", userServerID)
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereArrayContains("unixpermBITS", CPreferenceConstant.OWNER_DELETE)
                .get();

        Task<QuerySnapshot> logframes = coLogFrameRef
                .whereEqualTo("userOwnerID", userServerID)
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereArrayContains("unixpermBITS", CPreferenceConstant.OWNER_DELETE)
                .get();

        Task<QuerySnapshot> components = coComponentRef
                .whereEqualTo("userOwnerID", userServerID)
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereArrayContains("unixpermBITS", CPreferenceConstant.OWNER_DELETE)
                .get();

        Task<List<QuerySnapshot>> logframe_module;
        logframe_module = Tasks.whenAllSuccess(resources, projects, logframes, components);


        WriteBatch batch = database.batch();

        logframe_module
                .addOnCompleteListener(task -> {
                    for (QuerySnapshot result : Objects.requireNonNull(task.getResult())) {
                        if (!result.isEmpty()) {
                            for (QueryDocumentSnapshot ds : result) {
                                batch.delete(ds.getReference());
                            }
                        }
                    }

                    // delete a logframes with corresponding components
                    batch.commit().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            callback.onDeleteLogFramesSucceeded(
                                    "Logframe module successfully deleted");
                        } else {
                            callback.onDeleteLogFrameFailed(
                                    "Failed to delete Logframe module");
                        }
                    });
                })
                .addOnFailureListener(e ->
                        callback.onDeleteLogFrameFailed("Failed to filter logframe components")
                );

    }

    /* ##################################### UPLOAD ACTIONS ##################################### */

    /**
     * upload programmes and/or projects, logframes and components from excel file
     *
     * @param organizationServerID organization identification
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team bit
     * @param statusBIT            status bit
     * @param filePath             file path FIXME: fix selection files from a dialog
     * @param callback             call back
     */
    @Override
    public void upLoadProgrammeFromExcel(String organizationServerID, String userServerID,
                                         int primaryTeamBIT, int statusBIT, String filePath,
                                         iUploadLogFrameCallback callback) {

        Workbook workbook = excelHelper.getWorkbookPROJECT();

        Sheet resourceTypeSheet = workbook.getSheet(cExcelHelper.SHEET_tblRESOURCETYPE);
        Sheet projectSheet = workbook.getSheet(cExcelHelper.SHEET_tblPROJECT);
        Sheet logFrameSheet = workbook.getSheet(cExcelHelper.SHEET_tblLOGFRAME);

        Date now = new Date();

        /* resource types */

        List<cResourceTypeModel> resourceTypeModels = new ArrayList<>();
        for (Row resourceTypeRow : resourceTypeSheet) {
            //just skip the row if row number is 0
            if (resourceTypeRow.getRowNum() == 0) {
                continue;
            }

            cResourceTypeModel resourceTypeModel = new cResourceTypeModel();

            resourceTypeModel.setResourceTypeServerID(String.valueOf(
                    CFirestoreUtility.getCellAsNumeric(resourceTypeRow, 0)));
            resourceTypeModel.setName(CFirestoreUtility.getCellAsString(resourceTypeRow, 1));
            resourceTypeModel.setDescription(CFirestoreUtility.getCellAsString(resourceTypeRow, 2));

            // update common attributes
            CCommonAttributeModel commonPropertiesModel;
            commonPropertiesModel = CFirestoreUtility.getCommonModel(context);

            resourceTypeModel.setOrganizationOwnerID(organizationServerID);
            resourceTypeModel.setUserOwnerID(userServerID);
            resourceTypeModel.setTeamOwnerBIT(primaryTeamBIT);
            resourceTypeModel.setUnixpermBITS(commonPropertiesModel.getUnixpermBITS());
            resourceTypeModel.setStatusBIT(statusBIT);

            resourceTypeModel.setCreatedDate(now);
            resourceTypeModel.setModifiedDate(now);

            resourceTypeModels.add(resourceTypeModel);
        }

        /* programmes and/or projects */

        Map<cProjectModel, Map<cLogFrameModel, List<Map<String, Object>>>> projectLogframes;
        projectLogframes = new HashMap<>();

        Gson gson = new Gson();

        for (Row projectRow : projectSheet) {
            //just skip the row if row number is 0
            if (projectRow.getRowNum() == 0) {
                continue;
            }

            cProjectModel projectModel = new cProjectModel();

            projectModel.setProjectServerID(String.valueOf(
                    CFirestoreUtility.getCellAsNumeric(projectRow, 0)));
            projectModel.setParentServerID(String.valueOf(
                    CFirestoreUtility.getCellAsNumeric(projectRow, 1)));
            projectModel.setCode(CFirestoreUtility.getCellAsString(projectRow, 2));
            projectModel.setName(CFirestoreUtility.getCellAsString(projectRow, 3));
            projectModel.setDescription(CFirestoreUtility.getCellAsString(projectRow, 4));
            projectModel.setStatus(CFirestoreUtility.getCellAsString(projectRow, 5));
            projectModel.setLocation(CFirestoreUtility.getCellAsString(projectRow, 6));
            projectModel.setStartDate(CFirestoreUtility.getCellAsDate(projectRow, 7));
            projectModel.setEndDate(CFirestoreUtility.getCellAsDate(projectRow, 8));

//            projectModel.setCreatedDate(now);
//            projectModel.setModifiedDate(now);
//
//
//            projectModel.setProjectServerID(String.valueOf(
//                    cDatabaseUtils.getCellAsNumeric(projectRow, 0)));
//            projectModel.setParentServerID(String.valueOf(
//                    cDatabaseUtils.getCellAsNumeric(projectRow, 1)));
//            projectModel.setName(cDatabaseUtils.getCellAsString(projectRow, 2));
//            projectModel.setDescription(cDatabaseUtils.getCellAsString(projectRow, 3));
//            projectModel.setStartDate(cDatabaseUtils.getCellAsDate(projectRow, 4));
//            projectModel.setEndDate(cDatabaseUtils.getCellAsDate(projectRow, 5));

            if (projectModel.getParentServerID().equals("0")) {
                projectModel.setParentServerID(null);
            }

            // update child projects
            for (Row parentRow : projectSheet) {
                //just skip the row if row number is 0
                if (parentRow.getRowNum() == 0) {
                    continue;
                }

                String parentID = String.valueOf(
                        CFirestoreUtility.getCellAsNumeric(parentRow, 1));
                if (parentID.equals(projectModel.getProjectServerID())) {
                    String childID = String.valueOf(
                            CFirestoreUtility.getCellAsNumeric(parentRow, 0));
                    projectModel.getChildren().add(childID);
                }
            }

            // update common attributes
            CCommonAttributeModel commonPropertiesModel;
            commonPropertiesModel = CFirestoreUtility.getCommonModel(context);

            projectModel.setOrganizationOwnerID(organizationServerID);
            projectModel.setUserOwnerID(userServerID);
            projectModel.setTeamOwnerBIT(primaryTeamBIT);
            projectModel.setUnixpermBITS(commonPropertiesModel.getUnixpermBITS());
            projectModel.setStatusBIT(statusBIT);

            projectModel.setCreatedDate(now);
            projectModel.setModifiedDate(now);

            /* logframes */

            if (logFrameSheet != null) {

                Map<cLogFrameModel, List<Map<String, Object>>> logframeComponents;
                for (Row logframeRow : logFrameSheet) {
                    // just skip the row if row number is 0
                    if (logframeRow.getRowNum() == 0) {
                        continue;
                    }

                    // upload corresponding logframe from excel
                    logframeComponents = uploadLogFrameFromExcel(organizationServerID, userServerID,
                            primaryTeamBIT, statusBIT, projectModel, logframeRow, now, workbook);

                    if (!logframeComponents.isEmpty()) {
                        Log.d(TAG, "LOGFRAME = " + gson.toJson(logframeComponents));

                        projectLogframes.put(projectModel, logframeComponents);
                    }
                }

            } else {
                callback.onUploadLogFrameFailed("Failed to read excel file!");
            }
        }

        // add projects and corresponding logframes with its components as well as resource types
        if (!projectLogframes.isEmpty()) {
            createLogFrameFromExcel(resourceTypeModels, projectLogframes, callback);
        } else {
            callback.onUploadLogFrameFailed("No programmes and/or projects to upload!");
        }
    }

    /**
     * upload logframes from excel file
     *
     * @param organizationServerID organization identification
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team bit
     * @param statusBIT            status bit
     * @param projectModel         project model
     * @param logframeRow          logframe row
     * @param now                  date
     * @param workbook             excel workbook
     * @return map
     */
    @NonNull
    private Map<cLogFrameModel, List<Map<String, Object>>> uploadLogFrameFromExcel(
            String organizationServerID, String userServerID, int primaryTeamBIT,
            int statusBIT, @NonNull cProjectModel projectModel, Row logframeRow,
            Date now, @NonNull Workbook workbook) {

        Sheet logFrameTreeSheet = workbook.getSheet(cExcelHelper.SHEET_tblLOGFRAMETREE);
        Sheet componentSheet = workbook.getSheet(cExcelHelper.SHEET_tblCOMPONENT);
        Sheet impactSheet = workbook.getSheet(cExcelHelper.SHEET_tblIMPACT);
        Sheet outcomeSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME);
        Sheet outputSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT);
        Sheet activitySheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY);
        Sheet preActivitySheet = workbook.getSheet(cExcelHelper.SHEET_tblPRECEDINGACTIVITY);
        Sheet inputSheet = workbook.getSheet(cExcelHelper.SHEET_tblINPUT);
        Sheet humanSheet = workbook.getSheet(cExcelHelper.SHEET_tblHUMAN);
        Sheet materialSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATERIAL);
        Sheet expenseSheet = workbook.getSheet(cExcelHelper.SHEET_tblEXPENSE);
        Sheet incomeSheet = workbook.getSheet(cExcelHelper.SHEET_tblINCOME);
        Sheet outcomeImpactSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME_IMPACT);
        Sheet outputOutcomeSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT_OUTCOME);
        Sheet activityOutputSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY_OUTPUT);
        Sheet inputActivitySheet = workbook.getSheet(cExcelHelper.SHEET_tblINPUT_ACTIVITY);

        // map of logframe with its components
        Map<cLogFrameModel, List<Map<String, Object>>> logframeComponents = new HashMap<>();

        // logframe model for the project model
        cLogFrameModel logFrameModel = new cLogFrameModel();

        String projectServerID = String.valueOf(
                CFirestoreUtility.getCellAsNumeric(logframeRow, 0));

        if (projectServerID.equals(projectModel.getProjectServerID())) {
            logFrameModel.setProjectServerID(projectServerID);
            logFrameModel.setParentServerID(String.valueOf(
                    CFirestoreUtility.getCellAsNumeric(logframeRow, 1)));
            logFrameModel.setName(CFirestoreUtility.getCellAsString(logframeRow, 2));
            logFrameModel.setDescription(CFirestoreUtility.getCellAsString(logframeRow, 3));
            logFrameModel.setStartDate(CFirestoreUtility.getCellAsDate(logframeRow, 4));
            logFrameModel.setEndDate(CFirestoreUtility.getCellAsDate(logframeRow, 5));

            // update parent logframes
            if (logFrameModel.getParentServerID().equals("0")) {
                logFrameModel.setParentServerID(null);
            }

            // update child logframes
            if (logFrameTreeSheet != null) {
                for (Row cRowTree : logFrameTreeSheet) {
                    //just skip the row if row number is 0
                    if (cRowTree.getRowNum() == 0) {
                        continue;
                    }

                    String parentID = String.valueOf(
                            CFirestoreUtility.getCellAsNumeric(cRowTree, 0));
                    if (parentID.equals(logFrameModel.getProjectServerID())) {
                        String childID = String.valueOf(
                                CFirestoreUtility.getCellAsNumeric(cRowTree, 1));
                        logFrameModel.getChildren().add(childID);
                    }
                }
            }

            // update common attributes
            CCommonAttributeModel commonPropertiesModel;
            commonPropertiesModel = CFirestoreUtility.getCommonModel(context);

            logFrameModel.setOrganizationOwnerID(organizationServerID);
            logFrameModel.setUserOwnerID(userServerID);
            logFrameModel.setTeamOwnerBIT(primaryTeamBIT);
            logFrameModel.setUnixpermBITS(commonPropertiesModel.getUnixpermBITS());
            logFrameModel.setStatusBIT(statusBIT);

            logFrameModel.setCreatedDate(now);
            logFrameModel.setModifiedDate(now);

            /* logframe components */

            List<Map<String, Object>> componentModels = new ArrayList<>();
            for (Row componentRow : componentSheet) {
                //just skip the row if row number is 0
                if (componentRow.getRowNum() == 0) {
                    continue;
                }

                String logFrameID = String.valueOf(
                        CFirestoreUtility.getCellAsNumeric(componentRow, 1));
                if (logFrameID.equals(logFrameModel.getProjectServerID())) {

                    Map<String, Object> componentModel = new HashMap<>();

                    componentModel.put("componentServerID", String.valueOf(
                            CFirestoreUtility.getCellAsNumeric(componentRow, 0)));
                    componentModel.put("projectServerID", projectServerID);
                    componentModel.put("name",
                            CFirestoreUtility.getCellAsString(componentRow, 2));
                    componentModel.put("description",
                            CFirestoreUtility.getCellAsString(componentRow, 3));
                    componentModel.put("component_type",
                            CFirestoreUtility.getCellAsString(componentRow, 4));
                    componentModel.put("startDate",
                            CFirestoreUtility.getCellAsDate(componentRow, 5));
                    componentModel.put("endDate",
                            CFirestoreUtility.getCellAsDate(componentRow, 6));

                    // update common attributes
                    componentModel.put("organizationOwnerID", organizationServerID);
                    componentModel.put("userOwnerID", userServerID);
                    componentModel.put("teamOwnerBIT", primaryTeamBIT);
                    componentModel.put("unixpermBITS",
                            commonPropertiesModel.getUnixpermBITS());
                    componentModel.put("statusBIT", statusBIT);
                    componentModel.put("createdDate", now);
                    componentModel.put("modifiedDate", now);

                    // list of components of the logframe:
                    logFrameModel.getComponents().add(
                            Objects.requireNonNull(componentModel.get("componentServerID")).toString());

                    // compile linkages of impact and add to component
                    List<String> subimpacts = new ArrayList<>();
                    List<String> outcomes = new ArrayList<>();
                    for (Row impactRow : impactSheet) {
                        //just skip the row if row number is 0
                        if (impactRow.getRowNum() == 0) {
                            continue;
                        }

                        String componentID = String.valueOf(
                                CFirestoreUtility.getCellAsNumeric(impactRow, 0));
                        if (componentID.equals(componentModel.get("componentServerID"))) {
                            String parentID = String.valueOf(
                                    CFirestoreUtility.getCellAsNumeric(impactRow, 1));

                            String pID = !parentID.equals("0") ? parentID : null;

                            // populate sub impacts
                            if (pID == null) {
                                for (Row subimpactRow : impactSheet) {
                                    //just skip the row if row number is 0
                                    if (subimpactRow.getRowNum() == 0) {
                                        continue;
                                    }
                                    String p_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(subimpactRow, 1));
                                    if (componentID.equals(p_ID)) {
                                        String c_ID = String.valueOf(CFirestoreUtility.
                                                getCellAsNumeric(subimpactRow, 0));
                                        subimpacts.add(c_ID);
                                    }
                                }
                            }

                            // populate outcomes
                            for (Row outcomeRow : outcomeSheet) {
                                //just skip the row if row number is 0
                                if (outcomeRow.getRowNum() == 0) {
                                    continue;
                                }
                                String impactID = String.valueOf(CFirestoreUtility.
                                        getCellAsNumeric(outcomeRow, 2));
                                if (componentID.equals(impactID)) {
                                    String outcomeID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(outcomeRow, 0));
                                    outcomes.add(outcomeID);
                                }
                            }

                            componentModel.put("subimpacts", subimpacts);
                            componentModel.put("outcomes", outcomes);
                        }
                    }

                    // compile linkages of outcome and add to component
                    List<String> suboutcomes = new ArrayList<>();
                    List<String> outputs = new ArrayList<>();
                    Map<String, List<String>> outcome_impacts = new HashMap<>();
                    for (Row outcomeRow : outcomeSheet) {
                        //just skip the row if row number is 0
                        if (outcomeRow.getRowNum() == 0) {
                            continue;
                        }

                        String componentID = String.valueOf(
                                CFirestoreUtility.getCellAsNumeric(outcomeRow, 0));
                        if (componentID.equals(componentModel.get("componentServerID"))) {
                            String parentID = String.valueOf(
                                    CFirestoreUtility.getCellAsNumeric(outcomeRow, 1));
                            String impactServerID = String.valueOf(
                                    CFirestoreUtility.getCellAsNumeric(outcomeRow, 2));

                            // populate sub outcomes
                            String pID = !parentID.equals("0") ? parentID : null;
                            if (pID == null) {
                                for (Row suboutcomeRow : outcomeSheet) {
                                    //just skip the row if row number is 0
                                    if (suboutcomeRow.getRowNum() == 0) {
                                        continue;
                                    }
                                    String p_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(suboutcomeRow, 1));
                                    if (componentID.equals(p_ID)) {
                                        String c_ID = String.valueOf(CFirestoreUtility.
                                                getCellAsNumeric(suboutcomeRow, 0));
                                        suboutcomes.add(c_ID);
                                    }
                                }
                            }

                            // populate outputs
                            for (Row outputRow : outputSheet) {
                                //just skip the row if row number is 0
                                if (outputRow.getRowNum() == 0) {
                                    continue;
                                }
                                String outcomeID = String.valueOf(CFirestoreUtility.
                                        getCellAsNumeric(outputRow, 2));
                                if (componentID.equals(outcomeID)) {
                                    String outputID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(outputRow, 0));
                                    outputs.add(outputID);
                                }
                            }

                            // populate outcome impacts
                            List<String> sublogframe_impacts = new ArrayList<>();
                            for (Row outcomeImpactRow : outcomeImpactSheet) {
                                //just skip the row if row number is 0
                                if (outcomeImpactRow.getRowNum() == 0) {
                                    continue;
                                }
                                String outcomeID = String.valueOf(CFirestoreUtility.
                                        getCellAsNumeric(outcomeImpactRow, 2));
                                if (componentID.equals(outcomeID)) {
                                    String p_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(outcomeImpactRow, 0));
                                    String c_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(outcomeImpactRow, 1));
                                    String impact_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(outcomeImpactRow, 3));

                                    sublogframe_impacts.add(impact_ID);
                                    outcome_impacts.put(p_ID + "_" + c_ID + "_" +
                                            outcomeID, sublogframe_impacts);
                                }
                            }

                            componentModel.put("impactServerID", impactServerID);
                            componentModel.put("suboutcomes", suboutcomes);
                            componentModel.put("outputs", outputs);
                            componentModel.put("outcome_impacts", outcome_impacts);
                        }
                    }

                    // compile linkages of output and add to component
                    List<String> suboutputs = new ArrayList<>();
                    List<String> activities = new ArrayList<>();
                    Map<String, List<String>> output_outcomes = new HashMap<>();
                    for (Row outputRow : outputSheet) {
                        //just skip the row if row number is 0
                        if (outputRow.getRowNum() == 0) {
                            continue;
                        }

                        String componentID = String.valueOf(
                                CFirestoreUtility.getCellAsNumeric(outputRow, 0));
                        if (componentID.equals(componentModel.get("componentServerID"))) {
                            String parentID = String.valueOf(
                                    CFirestoreUtility.getCellAsNumeric(outputRow, 1));
                            String outcomeServerID = String.valueOf(
                                    CFirestoreUtility.getCellAsNumeric(outputRow, 2));

                            // populate sub outputs
                            String pID = !parentID.equals("0") ? parentID : null;
                            if (pID == null) {
                                for (Row suboutputRow : outputSheet) {
                                    //just skip the row if row number is 0
                                    if (suboutputRow.getRowNum() == 0) {
                                        continue;
                                    }
                                    String p_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(suboutputRow, 1));
                                    if (componentID.equals(p_ID)) {
                                        String c_ID = String.valueOf(CFirestoreUtility.
                                                getCellAsNumeric(suboutputRow, 0));
                                        suboutputs.add(c_ID);
                                    }
                                }
                            }

                            // populate activities
                            for (Row activityRow : activitySheet) {
                                //just skip the row if row number is 0
                                if (activityRow.getRowNum() == 0) {
                                    continue;
                                }
                                String outputID = String.valueOf(CFirestoreUtility.
                                        getCellAsNumeric(activityRow, 2));
                                if (componentID.equals(outputID)) {
                                    String activityID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(activityRow, 0));
                                    activities.add(activityID);
                                }
                            }

                            // populate output outcomes
                            List<String> sublogframe_outcomes = new ArrayList<>();
                            for (Row outputOutcomeRow : outputOutcomeSheet) {
                                //just skip the row if row number is 0
                                if (outputOutcomeRow.getRowNum() == 0) {
                                    continue;
                                }
                                String outputID = String.valueOf(CFirestoreUtility.
                                        getCellAsNumeric(outputOutcomeRow, 2));
                                if (componentID.equals(outputID)) {
                                    String p_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(outputOutcomeRow, 0));
                                    String c_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(outputOutcomeRow, 1));
                                    String outcome_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(outputOutcomeRow, 3));

                                    sublogframe_outcomes.add(outcome_ID);
                                    output_outcomes.put(p_ID + "_" + c_ID + "_" +
                                            outputID, sublogframe_outcomes);
                                }
                            }

                            componentModel.put("outcomeServerID", outcomeServerID);
                            componentModel.put("suboutputs", suboutputs);
                            componentModel.put("activities", activities);
                            componentModel.put("output_outcomes", output_outcomes);
                        }
                    }

                    // compile linkages of activity and add to component
                    List<String> subactivities = new ArrayList<>();
                    List<String> procedings = new ArrayList<>();
                    List<String> inputs = new ArrayList<>();
                    Map<String, List<String>> activity_outputs = new HashMap<>();
                    for (Row activityRow : activitySheet) {
                        //just skip the row if row number is 0
                        if (activityRow.getRowNum() == 0) {
                            continue;
                        }

                        String componentID = String.valueOf(
                                CFirestoreUtility.getCellAsNumeric(activityRow, 0));
                        if (componentID.equals(componentModel.get("componentServerID"))) {
                            String parentID = String.valueOf(
                                    CFirestoreUtility.getCellAsNumeric(activityRow, 1));

                            String outputServerID = String.valueOf(
                                    CFirestoreUtility.getCellAsNumeric(activityRow, 2));

                            // populate sub activities
                            String pID = !parentID.equals("0") ? parentID : null;
                            if (pID == null) {
                                for (Row subactivityRow : activitySheet) {
                                    //just skip the row if row number is 0
                                    if (subactivityRow.getRowNum() == 0) {
                                        continue;
                                    }
                                    String p_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(subactivityRow, 1));
                                    if (componentID.equals(p_ID)) {
                                        String c_ID = String.valueOf(CFirestoreUtility.
                                                getCellAsNumeric(subactivityRow, 0));
                                        subactivities.add(c_ID);
                                    }
                                }
                            }

                            // populate procedings
                            for (Row procedingRow : preActivitySheet) {
                                //just skip the row if row number is 0
                                if (procedingRow.getRowNum() == 0) {
                                    continue;
                                }
                                String activityID = String.valueOf(CFirestoreUtility.
                                        getCellAsNumeric(procedingRow, 0));
                                if (componentID.equals(activityID)) {
                                    String procedingID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(procedingRow, 1));
                                    procedings.add(procedingID);
                                }
                            }

                            // populate inputs
                            for (Row inputRow : inputSheet) {
                                //just skip the row if row number is 0
                                if (inputRow.getRowNum() == 0) {
                                    continue;
                                }
                                String activityID = String.valueOf(CFirestoreUtility.
                                        getCellAsNumeric(inputRow, 1));
                                if (componentID.equals(activityID)) {
                                    String inputID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(inputRow, 0));
                                    inputs.add(inputID);
                                }
                            }

                            // populate activity outputs
                            List<String> sublogframe_outputs = new ArrayList<>();
                            for (Row activityOutputRow : activityOutputSheet) {
                                //just skip the row if row number is 0
                                if (activityOutputRow.getRowNum() == 0) {
                                    continue;
                                }
                                String activityID = String.valueOf(CFirestoreUtility.
                                        getCellAsNumeric(activityOutputRow, 2));
                                if (componentID.equals(activityID)) {
                                    String p_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(activityOutputRow, 0));
                                    String c_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(activityOutputRow, 1));
                                    String output_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(activityOutputRow, 3));

                                    sublogframe_outputs.add(output_ID);
                                    activity_outputs.put(p_ID + "_" + c_ID + "_" +
                                            activityID, sublogframe_outputs);
                                }
                            }

                            componentModel.put("outputServerID", outputServerID);
                            componentModel.put("subactivities", subactivities);
                            componentModel.put("procedings", procedings);
                            componentModel.put("inputs", inputs);
                            componentModel.put("activity_outputs", activity_outputs);
                        }
                    }

                    // compile linkages of input and add to component
                    Map<String, List<String>> input_activities = new HashMap<>();
                    for (Row inputRow : inputSheet) {
                        //just skip the row if row number is 0
                        if (inputRow.getRowNum() == 0) {
                            continue;
                        }

                        String componentID = String.valueOf(
                                CFirestoreUtility.getCellAsNumeric(inputRow, 0));
                        if (componentID.equals(componentModel.get("componentServerID"))) {
                            String activityServerID = String.valueOf(
                                    CFirestoreUtility.getCellAsNumeric(inputRow, 1));
                            String resourceTypeID = String.valueOf(
                                    CFirestoreUtility.getCellAsNumeric(inputRow, 2));

                            // populate input activities
                            List<String> sublogframe_activities = new ArrayList<>();
                            for (Row inputActivityRow : inputActivitySheet) {
                                //just skip the row if row number is 0
                                if (inputActivityRow.getRowNum() == 0) {
                                    continue;
                                }
                                String inputID = String.valueOf(CFirestoreUtility.
                                        getCellAsNumeric(inputActivityRow, 2));
                                if (componentID.equals(inputID)) {
                                    String p_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(inputActivityRow, 0));
                                    String c_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(inputActivityRow, 1));
                                    String activity_ID = String.valueOf(CFirestoreUtility.
                                            getCellAsNumeric(inputActivityRow, 3));

                                    sublogframe_activities.add(activity_ID);
                                    input_activities.put(p_ID + "_" + c_ID + "_" +
                                            inputID, sublogframe_activities);
                                }
                            }

                            /* human inputs */
                            for (Row humanRow : humanSheet) {
                                //just skip the row if row number is 0
                                if (humanRow.getRowNum() == 0) {
                                    continue;
                                }

                                String inputID = String.valueOf(
                                        CFirestoreUtility.getCellAsNumeric(humanRow, 0));
                                if (inputID.equals(componentModel.get("componentServerID"))) {
                                    int humanQuantity = CFirestoreUtility.getCellAsNumeric(
                                            humanRow, 1);

                                    componentModel.put("input_type", "HUMAN");
                                    componentModel.put("humanQuantity", humanQuantity);
                                    break;
                                }
                            }

                            /* material inputs */
                            for (Row materialRow : materialSheet) {
                                //just skip the row if row number is 0
                                if (materialRow.getRowNum() == 0) {
                                    continue;
                                }

                                String inputID = String.valueOf(
                                        CFirestoreUtility.getCellAsNumeric(materialRow, 0));
                                if (inputID.equals(componentModel.get("componentServerID"))) {
                                    int materialQuantity = CFirestoreUtility.getCellAsNumeric(
                                            materialRow, 1);

                                    componentModel.put("input_type", "MATERIAL");
                                    componentModel.put("materialQuantity", materialQuantity);
                                    break;
                                }
                            }

                            /* expense inputs */
                            for (Row expenseRow : expenseSheet) {
                                //just skip the row if row number is 0
                                if (expenseRow.getRowNum() == 0) {
                                    continue;
                                }

                                String inputID = String.valueOf(
                                        CFirestoreUtility.getCellAsNumeric(expenseRow, 0));
                                if (inputID.equals(componentModel.get("componentServerID"))) {
                                    double expenditure = CFirestoreUtility.getCellAsNumeric(
                                            expenseRow, 1);
                                    String expense_type = CFirestoreUtility.getCellAsString(
                                            expenseRow, 2);

                                    componentModel.put("input_type", expense_type);
                                    componentModel.put("expenditure", expenditure);
                                    break;
                                }
                            }

                            /* income inputs */
                            for (Row incomeRow : incomeSheet) {
                                //just skip the row if row number is 0
                                if (incomeRow.getRowNum() == 0) {
                                    continue;
                                }

                                String inputID = String.valueOf(
                                        CFirestoreUtility.getCellAsNumeric(incomeRow, 0));
                                if (inputID.equals(componentModel.get("componentServerID"))) {
                                    String fundServerID = String.valueOf(
                                            CFirestoreUtility.getCellAsNumeric(incomeRow, 1));
                                    double revenue = CFirestoreUtility.getCellAsNumeric(
                                            incomeRow, 2);

                                    componentModel.put("input_type", "REVENUE");
                                    componentModel.put("fundServerID", fundServerID);
                                    componentModel.put("revenue", revenue);
                                    break;
                                }
                            }

                            componentModel.put("activityServerID", activityServerID);
                            componentModel.put("resourceTypeID", resourceTypeID);
                            componentModel.put("input_activities", input_activities);
                        }
                    }

                    componentModels.add(componentModel);
                }
            }
            logframeComponents.put(logFrameModel, componentModels);
        }
        return logframeComponents;
    }

    /**
     * create resource types, projects, logframes and components
     *
     * @param resourceTypeModels resource types
     * @param projectLogframes   project, logframes and components
     * @param callback           call back
     */
    private void createLogFrameFromExcel(@NonNull List<cResourceTypeModel> resourceTypeModels,
                                         Map<cProjectModel, Map<cLogFrameModel, List<Map<String, Object>>>> projectLogframes,
                                         iUploadLogFrameCallback callback) {

        CollectionReference coProjectRef, coLogFrameRef, coResourceTypeRef, coComponentRef;

        coResourceTypeRef = database.collection(CFirestoreConstant.KEY_RESOURCETYPES);
        coProjectRef = database.collection(CFirestoreConstant.KEY_PROJECTS);
        coLogFrameRef = database.collection(CFirestoreConstant.KEY_LOGFRAMES);
        coComponentRef = database.collection(CFirestoreConstant.KEY_LOGFRAME_COMPONENTS);

        /* create a batch object */
        WriteBatch batch = database.batch();

        /* add resource types  */
        for (cResourceTypeModel resourceTypeModel : resourceTypeModels) {
            batch.set(coResourceTypeRef.document(resourceTypeModel.getResourceTypeServerID()),
                    resourceTypeModel);
        }

        /* add corresponding components */
        for (Map.Entry<cProjectModel, Map<cLogFrameModel, List<Map<String, Object>>>> project :
                projectLogframes.entrySet()) {
            cProjectModel projectModel = project.getKey();
            batch.set(coProjectRef.document(projectModel.getProjectServerID()), projectModel);

            Map<cLogFrameModel, List<Map<String, Object>>> logframeComponents = project.getValue();
            for (Map.Entry<cLogFrameModel, List<Map<String, Object>>> logframe :
                    logframeComponents.entrySet()) {
                cLogFrameModel logFrameModel = logframe.getKey();
                batch.set(coLogFrameRef.document(logFrameModel.getProjectServerID()), logFrameModel);

                List<Map<String, Object>> componentModels = logframe.getValue();
                /* add corresponding components */
                for (Map<String, Object> componentModel : componentModels) {
                    String componentID = (String) componentModel.get("componentServerID");
                    assert componentID != null;
                    batch.set(coComponentRef.document(componentID), componentModel);
                }
            }
        }

        batch.commit().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onUploadLogFrameSucceeded("LogFrame module successfully uploaded");
            } else {
                callback.onUploadLogFrameFailed("Failed to update LogFrame module");
            }
        });
    }
}