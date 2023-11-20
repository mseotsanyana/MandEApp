package com.me.mseotsanyana.mande.infrastructure.repository.firestore.programme;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.me.mseotsanyana.mande.domain.entities.models.common.cRecordPermissionModel;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cProjectModel;
import com.me.mseotsanyana.mande.domain.entities.models.utils.CCommonAttributeModel;
import com.me.mseotsanyana.mande.application.repository.programme.iProjectRepository;
import com.me.mseotsanyana.mande.application.ports.base.firebase.CFirestoreCallBack;
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
public class cProjectFirestoreRepositoryImpl extends CFirestoreRepository
        implements iProjectRepository {
    private static final String TAG = cProjectFirestoreRepositoryImpl.class.getSimpleName();

    private final cExcelHelper excelHelper;
    // an object of the database helper
    private final FirebaseFirestore database;
    private ListenerRegistration listenerRegistration;
    //private final FirebaseAuth firebaseAuth;
    private final Context context;


    public cProjectFirestoreRepositoryImpl(Context context) {
        this.context = context;
        //this.firebaseAuth = FirebaseAuth.getInstance();
        this.database = FirebaseFirestore.getInstance();

        excelHelper = new cExcelHelper(context);
    }

    /* ##################################### CREATE ACTIONS ##################################### */


    /* ###################################### READ ACTIONS ###################################### */

    /**
     * read all projects
     *
     * @param organizationServerID organization identification
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team bit
     * @param secondaryTeamBITS    secondary team bits
     * @param statusBITS           status bits
     * @param callback             firebase call back
     */
    @Override
    public void readProjects(String organizationServerID, String userServerID, int primaryTeamBIT,
                             List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
                             iReadProjectsCallback callback) {

        CollectionReference coProjectRef = database.collection(CFirestoreConstant.KEY_PROJECTS);
        Query query = coProjectRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS)
                .orderBy("createdDate");

        if (listenerRegistration != null)
            listenerRegistration.remove();

        listenerRegistration = readQueryDocumentsByListener(query, new CFirestoreCallBack() {
            @Override
            public void onFirebaseSuccess(Object object) {
                List<cProjectModel> projectModels = new ArrayList<>();
                if (object != null) {
                    QuerySnapshot snapshots = (QuerySnapshot) object;
                    for (DocumentChange documentChange : snapshots.getDocumentChanges()) {
                        DocumentSnapshot document = documentChange.getDocument();
                        cProjectModel projectModel = document.toObject(cProjectModel.class);
                        CFirestoreUtility.cUnixPerm perm = new CFirestoreUtility.cUnixPerm();
                        perm.setUserOwnerID(projectModel.getUserOwnerID());
                        perm.setTeamOwnerBIT(projectModel.getTeamOwnerBIT());
                        perm.setUnixpermBITS(projectModel.getUnixpermBITS());

//FIXME                        if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                                secondaryTeamBITS)) {
//                            projectModels.add(projectModel);
//                        }
                    }
                }
                // call back on Projects
                callback.onReadProjectsSucceeded(projectModels);
            }

            @Override
            public void onFirebaseFailure(Object object) {
                FirebaseException e = (FirebaseException) object;
                Log.d(TAG, "ERROR = " + e.getLocalizedMessage());
                callback.onReadProjectFailed("Failed to read Projects");
            }
        });

//        CollectionReference coProjectRef = database.collection(cRealtimeHelper.KEY_PROJECTS);
//
//        Query projectQuery = coProjectRef
//                .whereEqualTo("organizationOwnerID", stakeholderServerID)
//                .whereIn("statusBIT", statusBITS);
//
//        projectQuery.get()
//                .addOnCompleteListener(task -> {
//                    List<cProjectModel> projectModels = new ArrayList<>();
//                    for (DocumentSnapshot project_doc : Objects.requireNonNull(task.getResult())) {
//                        cProjectModel projectModel = project_doc.toObject(cProjectModel.class);
//
//                        if (projectModel != null) {
//                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
//                            perm.setUserOwnerID(projectModel.getUserOwnerID());
//                            perm.setTeamOwnerBIT(projectModel.getTeamOwnerBIT());
//                            perm.setUnixpermBITS(projectModel.getUnixpermBITS());
//
//                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
//                                    secondaryTeamBITS)) {
//                                projectModels.add(projectModel);
//                            }
//                        }
//                    }
//
//
//                    // call back on Projects
//                    callback.onReadProjectSucceeded(projectModels);
//                })
//                .addOnFailureListener(e -> callback.onReadProjectFailed(
//                        "Failed to read Projects"));
    }

    @Override
    public void removeListener() {
        if (listenerRegistration != null)
            listenerRegistration.remove();
    }

    /* ##################################### UPDATE ACTIONS ##################################### */

    @Override
    public void updateRecordPermissions(String projectServerID,
                                        @NonNull cRecordPermissionModel recordPermissions,
                                        iUpdateRecordPermissionsCallback callback) {

        CollectionReference coProjectsRef;
        coProjectsRef = database.collection(CFirestoreConstant.KEY_PROJECTS);

        Map<String, Object> map = new HashMap<>();

        map.put("userOwnerID", recordPermissions.getUserOwnerID());
        map.put("teamOwnerBIT", recordPermissions.getTeamOwnerBIT());
        map.put("organizationOwnerID", recordPermissions.getOrganizationOwnerID());
        map.put("statusBIT", recordPermissions.getStatusBIT());
        map.put("unixpermBITS", recordPermissions.getUnixpermBITS());
        map.put("createdDate", recordPermissions.getCreatedDate());
        map.put("modifiedDate", recordPermissions.getModifiedDate());

        fireStoreUpdate(coProjectsRef.document(projectServerID), map, new CFirestoreCallBack() {
            @Override
            public void onFirebaseSuccess(Object object) {
                callback.onUpdateRecordPermissionsSucceeded(
                        "Record permission successfully updated.");
            }

            @Override
            public void onFirebaseFailure(Object object) {
                callback.onUpdateRecordPermissionsFailed("Failed to update record permission.");
            }
        });
    }

    /* ##################################### DELETE ACTIONS ##################################### */

    /**
     * delete programmes/projects or Projects and components
     *
     * @param stakeholderServerID organization identification
     * @param userServerID        user identification
     * @param callback            call back
     */
    @Override
    public void deleteProjects(String stakeholderServerID, String userServerID,
                               iDeleteProjectsCallback callback) {

        CollectionReference coProjectRef;
        coProjectRef = database.collection(CFirestoreConstant.KEY_PROJECTS);

        Query projectQuery = coProjectRef
                .whereEqualTo("userOwnerID", userServerID)
                .whereEqualTo("organizationOwnerID", stakeholderServerID)
                .whereArrayContains("unixpermBITS", CPreferenceConstant.OWNER_DELETE);

        WriteBatch batch = database.batch();

        projectQuery.get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot ds : Objects.requireNonNull(task.getResult())) {
                        batch.delete(ds.getReference());
                    }
                });

        // batch delete projects
        batch.commit().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onDeleteProjectsSucceeded("Projects successfully deleted.");
            } else {
                callback.onDeleteProjectFailed("Failed to delete project.");
            }
        });
    }

    /* ##################################### UPLOAD ACTIONS ##################################### */

    /**
     * upload programmes and/or projects, Projects and components from excel file
     *
     * @param stakeholderServerID organization identification
     * @param userServerID        user identification
     * @param primaryTeamBIT      primary team bit
     * @param statusBIT           status bit
     * @param filePath            file path FIXME: fix selection files from a dialog
     * @param callback            call back
     */
    @Override
    public void upLoadProjectsFromExcel(String stakeholderServerID, String userServerID,
                                        int primaryTeamBIT, int statusBIT, String filePath,
                                        iUploadProjectsCallback callback) {

        Workbook workbook = excelHelper.getWorkbookPROJECT();
        Sheet projectSheet = workbook.getSheet(cExcelHelper.SHEET_tblPROJECT);

        Date now = new Date();

        /* programmes and/or projects */
        List<cProjectModel> projectModels = new ArrayList<>();

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

            projectModel.setCreatedDate(now);
            projectModel.setModifiedDate(now);

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

            projectModel.setOrganizationOwnerID(stakeholderServerID);
            projectModel.setUserOwnerID(userServerID);
            projectModel.setTeamOwnerBIT(primaryTeamBIT);
            projectModel.setUnixpermBITS(commonPropertiesModel.getUnixpermBITS());
            projectModel.setStatusBIT(statusBIT);

            projectModels.add(projectModel);
        }

        // add projects and corresponding Projects with its components as well as resource types
        if (!projectModels.isEmpty()) {
            createProjectFromExcel(projectModels, callback);
        } else {
            callback.onUploadProjectsFailed("No programmes and/or projects to upload!");
        }
    }

    /**
     * create programmes or projects
     *
     * @param projectModels projects
     * @param callback      call back
     */
    private void createProjectFromExcel(@NonNull List<cProjectModel> projectModels,
                                        iUploadProjectsCallback callback) {

        CollectionReference coProjectRef;

        coProjectRef = database.collection(CFirestoreConstant.KEY_PROJECTS);

        /* create a batch object */
        WriteBatch batch = database.batch();

        /* add projects  */
        for (cProjectModel projectModel : projectModels) {
            batch.set(coProjectRef.document(projectModel.getProjectServerID()), projectModel);
        }

        batch.commit().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onUploadProjectsSucceeded("Projects successfully uploaded.");
            } else {
                callback.onUploadProjectsFailed("Failed to update projects.");
            }
        });
    }
}