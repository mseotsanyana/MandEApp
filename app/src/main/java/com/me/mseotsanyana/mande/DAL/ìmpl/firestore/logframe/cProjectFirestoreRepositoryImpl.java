package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.logframe;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.me.mseotsanyana.mande.BLL.model.logframe.cProjectModel;
import com.me.mseotsanyana.mande.BLL.model.utils.cCommonPropertiesModel;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iProjectRepository;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cProjectFirestoreRepositoryImpl extends cFirebaseRepository
        implements iProjectRepository {
    //private static final String TAG = cProjectFirestoreRepositoryImpl.class.getSimpleName();

    private final cExcelHelper excelHelper;
    // an object of the database helper
    private final FirebaseFirestore database;
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
     * read projects
     *
     * @param stakeholderServerID organization identification
     * @param userServerID        user identification
     * @param primaryTeamBIT      primary team bit
     * @param secondaryTeamBITS   secondary team bits
     * @param statusBITS          status bits
     * @param callback            call back
     */
    @Override
    public void readProjects(String stakeholderServerID, String userServerID, int primaryTeamBIT,
                             List<Integer> secondaryTeamBITS, List<Integer> statusBITS,
                             iReadProjectsCallback callback) {

        CollectionReference coProjectRef = database.collection(cRealtimeHelper.KEY_PROJECTS);

        Query projectQuery = coProjectRef
                .whereEqualTo("organizationOwnerID", stakeholderServerID)
                .whereIn("statusBIT", statusBITS);

        projectQuery.get()
                .addOnCompleteListener(task -> {
                    List<cProjectModel> projectModels = new ArrayList<>();
                    for (DocumentSnapshot project_doc : Objects.requireNonNull(task.getResult())) {
                        cProjectModel projectModel = project_doc.toObject(cProjectModel.class);

                        if (projectModel != null) {
                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                            perm.setUserOwnerID(projectModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(projectModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(projectModel.getUnixpermBITS());

                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
                                    secondaryTeamBITS)) {
                                projectModels.add(projectModel);
                            }
                        }
                    }


                    // call back on Projects
                    callback.onReadProjectSucceeded(projectModels);
                })
                .addOnFailureListener(e -> callback.onReadProjectFailed(
                        "Failed to read Projects"));
    }

    /* ##################################### UPDATE ACTIONS ##################################### */

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
        coProjectRef = database.collection(cRealtimeHelper.KEY_PROJECTS);

        Query projectQuery = coProjectRef
                .whereEqualTo("userOwnerID", userServerID)
                .whereEqualTo("organizationOwnerID", stakeholderServerID)
                .whereArrayContains("unixpermBITS", cSharedPreference.OWNER_DELETE);

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
                                        iUploadProjectCallback callback) {

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
                    cDatabaseUtils.getCellAsNumeric(projectRow, 0)));
            projectModel.setParentServerID(String.valueOf(
                    cDatabaseUtils.getCellAsNumeric(projectRow, 1)));
            projectModel.setCode(cDatabaseUtils.getCellAsString(projectRow, 2));
            projectModel.setName(cDatabaseUtils.getCellAsString(projectRow, 3));
            projectModel.setDescription(cDatabaseUtils.getCellAsString(projectRow, 4));
            projectModel.setStatus(cDatabaseUtils.getCellAsString(projectRow, 5));
            projectModel.setLocation(cDatabaseUtils.getCellAsString(projectRow, 6));
            projectModel.setStartDate(cDatabaseUtils.getCellAsDate(projectRow, 7));
            projectModel.setEndDate(cDatabaseUtils.getCellAsDate(projectRow, 8));

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
                        cDatabaseUtils.getCellAsNumeric(parentRow, 1));
                if (parentID.equals(projectModel.getProjectServerID())) {
                    String childID = String.valueOf(
                            cDatabaseUtils.getCellAsNumeric(parentRow, 0));
                    projectModel.getChildren().add(childID);
                }
            }

            // update common attributes
            cCommonPropertiesModel commonPropertiesModel;
            commonPropertiesModel = cDatabaseUtils.getCommonModel(context);

            projectModel.setOrganizationOwnerID(stakeholderServerID);
            projectModel.setUserOwnerID(userServerID);
            projectModel.setTeamOwnerBIT(primaryTeamBIT);
            projectModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
            projectModel.setStatusBIT(statusBIT);

            projectModels.add(projectModel);
        }

        // add projects and corresponding Projects with its components as well as resource types
        if (!projectModels.isEmpty()) {
            createProjectFromExcel(projectModels, callback);
        } else {
            callback.onUploadProjectFailed("No programmes and/or projects to upload!");
        }
    }

    /**
     * create programmes or projects
     *
     * @param projectModels projects
     * @param callback      call back
     */
    private void createProjectFromExcel(@NonNull List<cProjectModel> projectModels,
                                        iUploadProjectCallback callback) {

        CollectionReference coProjectRef;

        coProjectRef = database.collection(cRealtimeHelper.KEY_PROJECTS);

        /* create a batch object */
        WriteBatch batch = database.batch();

        /* add projects  */
        for (cProjectModel projectModel : projectModels) {
            batch.set(coProjectRef.document(projectModel.getProjectServerID()), projectModel);
        }

        batch.commit().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onUploadProjectSucceeded("Projects successfully uploaded.");
            } else {
                callback.onUploadProjectFailed("Failed to update projects.");
            }
        });
    }
}