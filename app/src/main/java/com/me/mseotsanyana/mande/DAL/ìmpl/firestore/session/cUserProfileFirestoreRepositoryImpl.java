package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseCallBack;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseChildCallBack;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cUserProfileFirestoreRepositoryImpl extends cFirebaseRepository
        implements iUserProfileRepository {
    private static final String TAG = cUserProfileFirestoreRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private final FirebaseFirestore database;
    private final FirebaseStorage storage;
    private final FirebaseAuth firebaseAuth;
    private final Context context;

    public cUserProfileFirestoreRepositoryImpl(Context context) {
        this.context = context;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.database = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
    }

    /* ##################################### CREATE ACTIONS ##################################### */

    /**
     * create user with email and password
     *
     * @param userProfileModel user profile model
     * @param callback         callback
     */
    @Override
    public void createUserWithEmailAndPassword(CUserProfileModel userProfileModel,
                                               iSignUpRepositoryCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(userProfileModel.getEmail().trim(),
                userProfileModel.getPassword().trim())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (firebaseUser != null) {
                            // update firebase user profile
                            updateFirebaseUser(firebaseUser, userProfileModel);

                            // send an email verification
                            sendEmailVerification(firebaseUser);

                            // create user profile in the database
                            createUserProfile(firebaseUser, userProfileModel, callback);
                        } else {
                            callback.onSignUpFailed("Failed to create a new user profile.");
                        }
                    } else {
                        Log.d(TAG, "createUserWithEmailAndPassword:failure",
                                task.getException());
                        callback.onSignUpFailed("Failed to create a new user profile. " +
                                "Try a different email.");
                    }
                });
    }

    /**
     * update firebase user
     *
     * @param firebaseUser     firebase user
     * @param userProfileModel user profile model
     */
    private void updateFirebaseUser(FirebaseUser firebaseUser, CUserProfileModel userProfileModel) {
        /* update the profile in the firebase */
        UserProfileChangeRequest profileUpdates;
        profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userProfileModel.getName() + " " + userProfileModel.getSurname())
                .build();

        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(updateProfileTask -> {
                    if (updateProfileTask.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmailAndPassword:success");
                    }
                })
                .addOnFailureListener(e ->
                        Log.d(TAG, Objects.requireNonNull(e.getLocalizedMessage())));
    }

    /**
     * send an email to verify the firebase user
     *
     * @param firebaseUser firebase user
     */
    private void sendEmailVerification(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Verification email sent to " +
                            firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
                } else {
                    Log.e(TAG, "sendEmailVerification", task.getException());
                    Toast.makeText(context, "Failed to send verification email.",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * create user profile
     *
     * @param firebaseUser     firebase user
     * @param userProfileModel user profile model
     * @param callback         callback
     */
    private void createUserProfile(@NonNull FirebaseUser firebaseUser,
                                   CUserProfileModel userProfileModel,
                                   iSignUpRepositoryCallback callback) {

        HashMap<String, Object> userProfile = new HashMap<>();
        userProfile.put("name", userProfileModel.getName());
        userProfile.put("surname", userProfileModel.getSurname());
        userProfile.put("designation", userProfileModel.getDesignation());
        userProfile.put("email", userProfileModel.getEmail());

        // create user profile image in the storage
        StorageReference profilePhotoRef = storage.getReference()
                .child(cRealtimeHelper.KEY_PROFILEPHOTOS)
                .child(firebaseUser.getUid());
        fireStoreImageData(profilePhotoRef, userProfileModel.getImageData(),
                new cFirebaseCallBack() {
                    @Override
                    public void onFirebaseSuccess(Object object) {
                        String downloadedUrl = object.toString();
                        String uid = firebaseUser.getUid();
                        Date currentDate = new Date();

                        userProfile.put("userServerID", uid);
                        userProfile.put("userOwnerID", uid);
                        userProfile.put("photoUrl", downloadedUrl);
                        userProfile.put("createdDate", currentDate);
                        userProfile.put("modifiedDate", currentDate);

                        // create user profile model to the database
                        CollectionReference coUsersRef;
                        coUsersRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);
                        DocumentReference doUsersRef = coUsersRef.document(uid);
                        fireStoreCreate(doUsersRef, userProfile, new cFirebaseCallBack() {
                            @Override
                            public void onFirebaseSuccess(Object object) {
                                //Log.d(TAG, "createUserWithEmailAndPassword:success");
                                callback.onSignUpSucceeded("User profile successfully created.");
                            }

                            @Override
                            public void onFirebaseFailure(Object object) {
                                // remove the user is fail to create user account
                                firebaseUser.delete().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()){
                                        Log.d(TAG, "Authenticated user deleted!");
                                    }else{
                                        Log.d(TAG, "Failed to deleted an authenticated user!");
                                    }
                                });

                                callback.onSignUpFailed("Failed to create user profile.");
                            }
                        });
                    }

                    @Override
                    public void onFirebaseFailure(Object object) {
                        //Log.d(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
                        callback.onSignUpFailed("Failed to store a profile photo.");
                    }
                });
    }

    /* ################################## AUTHORIZATION ACTIONS ################################# */

    /**
     * sign in with email and password
     *
     * @param email    email
     * @param password password
     * @param callback callback
     */
    @Override
    public void signInWithEmailAndPassword(String email, String password,
                                           iSignInRepositoryCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    if (!firebaseUser.isEmailVerified()) {
                        FirebaseAuth.getInstance().signOut();
                        resendEmailVerification(firebaseUser, callback);
                        //callback.onSignInFailed("Verification email resend to " +firebaseUser.getEmail());
                    } else {
                        Log.d(TAG, "signInWithEmail:success");
                        callback.onSignInSucceeded("Successfully Authenticated.");
                    }
                } else {
                    Log.d(TAG, "signInWithEmail:failure ", task.getException());
                    callback.onSignInFailed("Authentication failed!");
                }
            } else {
                Log.d(TAG, "signInWithEmail:failure ", task.getException());
                callback.onSignInFailed("Authentication failed!");
            }
        });
    }

    /**
     * send an email to verify the firebase user
     *
     * @param firebaseUser firebase user
     */
    private void resendEmailVerification(FirebaseUser firebaseUser,
                                         iSignInRepositoryCallback callback) {
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    callback.onSignInFailed("Verification email resend to " +
                            firebaseUser.getEmail());
                } else {
                    Log.e(TAG, "sendEmailVerification", task.getException());
                    callback.onSignInFailed("Failed to send verification email.");

                }
            });
        }
    }

    /**
     * sign out with email and password
     *
     * @param callback callback
     */
    @Override
    public void signOutWithEmailAndPassword(iSignOutRepositoryCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            firebaseAuth.signOut();
            callback.onSignOutSucceeded("Logged Out");
        } else {
            callback.onSignOutFailed("Already Logged Out !!!");
        }
    }

    @Override
    public void sendPasswordResetEmail(CUserProfileModel userProfileModel,
                                       iResetPasswordRepositoryCallback callback) {

        //Log.d(TAG,"User Email ===== "+userProfileModel.getEmail());
        firebaseAuth.sendPasswordResetEmail(userProfileModel.getEmail())
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        callback.onResetPasswordSucceeded(
                                "Instructions send to your email to reset your password!");
                    }else{
                        callback.onResetPasswordFailed("Failed to send reset email!");
                    }
                });
    }

    @Override
    public void changePassword(CUserProfileModel userProfileModel,
                               iChangePasswordRepositoryCallback callback) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(userProfileModel.getEmail(), userProfileModel.getPassword());

        // Prompt the user to re-provide their sign-in credentials
        assert firebaseUser != null;
        firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseUser.updatePassword(userProfileModel.getPassword()).addOnCompleteListener(
                        task1 -> {
                            if (task1.isSuccessful()) {
                                Log.d(TAG, "Password updated");
                            } else {
                                Log.d(TAG, "Error password not updated");
                            }
                        });
            } else {
                Log.d(TAG, "Error auth failed");
            }
        });
    }


    /* ###################################### READ ACTIONS ###################################### */

    @Override
    public void readMyUserProfile(iReadMyUserProfileRepositoryCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            CollectionReference coUserProfileRef;
            coUserProfileRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);
            coUserProfileRef.document(user.getUid()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc != null) {
                                CUserProfileModel userProfile;
                                userProfile = doc.toObject(CUserProfileModel.class);
                                callback.onReadMyUserProfileSucceeded(userProfile);
                            }
                        } else {
                            callback.onReadMyUserProfileFailed(
                                    "Undefined error! Please report to the developer.");
                        }
                    })
                    .addOnFailureListener(e ->
                            callback.onReadMyUserProfileFailed("Failure to read user profile!"));
        } else {
            callback.onReadMyUserProfileFailed("Failure to read user profile!");
        }
    }

    @Override
    public void readUserProfiles(iReadUserProfilesRepositoryCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            CollectionReference coUserProfileRef;
            coUserProfileRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);

            Query userProfileQuery = coUserProfileRef
                    .whereEqualTo("userOwnerID", user.getUid());

            userProfileQuery.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<CUserProfileModel> userProfileModels = new ArrayList<>();
                    for (DocumentSnapshot userprofile_doc : Objects.requireNonNull(
                            task.getResult())) {
                        CUserProfileModel userProfileModel;
                        userProfileModel = userprofile_doc.toObject(CUserProfileModel.class);

                        if (userProfileModel != null) {
                            userProfileModels.add(userProfileModel);
                        }
                    }
                    callback.onReadUserProfilesSucceeded(userProfileModels);
                } else {
                    callback.onReadUserProfilesFailed("Failed to upload user profiles.");
                }
            });
        } else {
            callback.onReadUserProfilesFailed("Failure to read user profile!");
        }
    }

    /**
     * listens to change to the user profile collection
     *
     * @param firebaseChildCallBack firebase child callback
     * @return listener registration
     */
    @Override
    public ListenerRegistration readAllUserProfilesByChildEvent(
            cFirebaseChildCallBack firebaseChildCallBack) {
        CollectionReference coUserProfileRef;
        coUserProfileRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);

        Query query = coUserProfileRef.orderBy("createdDate");

        return readQueryDocumentsByChildEventListener(query, new cFirebaseChildCallBack() {
            @Override
            public void onChildAdded(Object object) {
                if (object != null) {
                    DocumentSnapshot document = (DocumentSnapshot) object;
                    if (document.exists()) {
                        CUserProfileModel userProfileModel;
                        userProfileModel = document.toObject(CUserProfileModel.class);
                        firebaseChildCallBack.onChildAdded(userProfileModel);
                    } else {
                        firebaseChildCallBack.onChildAdded(null);
                    }
                } else {
                    firebaseChildCallBack.onChildAdded(null);
                }
            }

            @Override
            public void onChildChanged(Object object) {
                if (object != null) {
                    DocumentSnapshot document = (DocumentSnapshot) object;
                    if (document.exists()) {
                        CUserProfileModel userProfileModel;
                        userProfileModel = document.toObject(CUserProfileModel.class);
                        firebaseChildCallBack.onChildChanged(userProfileModel);
                    } else {
                        firebaseChildCallBack.onChildChanged(null);
                    }
                } else {
                    firebaseChildCallBack.onChildChanged(null);
                }
            }

            @Override
            public void onChildRemoved(Object object) {
                if (object != null) {
                    DocumentSnapshot document = (DocumentSnapshot) object;
                    if (document.exists()) {
                        CUserProfileModel userProfileModel;
                        userProfileModel = document.toObject(CUserProfileModel.class);
                        firebaseChildCallBack.onChildRemoved(userProfileModel);
                    } else {
                        firebaseChildCallBack.onChildRemoved(null);
                    }
                } else {
                    firebaseChildCallBack.onChildRemoved(null);
                }
            }

            @Override
            public void onCancelled(Object object) {
                firebaseChildCallBack.onCancelled(object);
            }
        });
    }


    /* ##################################### UPDATE ACTIONS ##################################### */

    @Override
    public void updateUserProfileImage(long userID, int primaryRole, int secondaryRoles, int statusBITS,
                                       CUserProfileModel userProfileModel,
                                       iUpdateUserProfileRepositoryCallback callback) {

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            userProfileModel.setEmail(user.getEmail());
            CollectionReference coUserProfileRef;
            coUserProfileRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);
            coUserProfileRef.document(user.getUid()).set(userProfileModel)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            callback.onUpdateUserProfileSucceeded("Successfully Updated");
                        } else {
                            callback.onUpdateUserProfileFailed(
                                    "Undefined error! Talk to the Admin");
                        }
                    })
                    .addOnFailureListener(e -> callback.onUpdateUserProfileFailed(
                            "Failed to update user profile " + e));
        } else {
            callback.onUpdateUserProfileFailed("Failed to update user profile!");
        }
    }

    /**
     * update a user's profile image
     *
     * @param userServerID         user identification
     * @param userProfileImageData user profile image data
     * @param callback             callback
     */
    @Override
    public void updateUserProfileImage(String userServerID, byte[] userProfileImageData,
                                       iUpdateUserProfileImageRepositoryCallback callback) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            // read the user's document
            CollectionReference coUserProfilesRef;
            coUserProfilesRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);
            Query userProfileQuery = coUserProfilesRef
                    .whereEqualTo("userServerID", userServerID)
                    .whereEqualTo("userOwnerID", firebaseUser.getUid());

            readQueryDocuments(userProfileQuery, new cFirebaseCallBack() {
                @Override
                public void onFirebaseSuccess(Object querySnapshot) {
                    QuerySnapshot documentSnapshots = (QuerySnapshot) querySnapshot;
                    CUserProfileModel userProfileModel = null;
                    for (DocumentSnapshot userProfile : documentSnapshots) {
                        userProfileModel = userProfile.toObject(CUserProfileModel.class);
                    }

                    if (userProfileModel != null) {
                        // update user's profile image data
                        userProfileModel.setImageData(userProfileImageData);

                        StorageReference profilePhotoRef = storage.getReference()
                                .child(cRealtimeHelper.KEY_PROFILEPHOTOS)
                                .child(userServerID);
                        fireStoreImageData(profilePhotoRef, userProfileModel.getImageData(),
                                new cFirebaseCallBack() {
                                    @Override
                                    public void onFirebaseSuccess(Object uri) {
                                        // update user's image URL
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("photoUrl", uri.toString());
                                        map.put("modifiedDate", new Date());

                                        DocumentReference doUserRef;
                                        doUserRef = coUserProfilesRef.document(userServerID);
                                        fireStoreUpdate(doUserRef, map, new cFirebaseCallBack() {
                                            @Override
                                            public void onFirebaseSuccess(Object object) {
                                                callback.onUpdateUserProfileImageSucceeded(
                                                        "User's profile image successfully updated.");
                                            }

                                            @Override
                                            public void onFirebaseFailure(Object object) {
                                                callback.onUpdateUserProfileImageFailed(
                                                        "Failed to update user's profile image.");
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFirebaseFailure(Object object) {
                                        callback.onUpdateUserProfileImageFailed(
                                                "Failed to save the image.");
                                    }
                                });
                    } else {
                        callback.onUpdateUserProfileImageFailed(
                                "Error! No permission to modify the user's profile image.");
                    }
                }

                @Override
                public void onFirebaseFailure(Object object) {
                    callback.onUpdateUserProfileImageFailed("Failed to read user's profile.");
                }
            });
        } else {
            callback.onUpdateUserProfileImageFailed("Failed to read logged in user.");
        }
    }



    /* ##################################### DELETE ACTIONS ##################################### */

    /* ##################################### UPLOAD ACTIONS ##################################### */

    /**
     * upload user profiles from excel file to a list.
     *
     * @param filename file name
     * @param callback callback
     */
    @Override
    public void uploadUserProfilesFromExcel(String filename,
                                            iUploadUserProfilesRepositoryCallback callback) {
        /* user who created user profiles */
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {

            File file = new File(filename);
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            assert fileInputStream != null;

            Workbook workbook = null;
            try {
                workbook = new XSSFWorkbook(fileInputStream);
            } catch (IOException e) {
                Log.d(TAG, " ERROR : "+e.getLocalizedMessage());
            }

            assert workbook != null;
            Sheet userProfileSheet = workbook.getSheet(cExcelHelper.SHEET_tblUSERPROFILE);

            // user profile
            List<CUserProfileModel> userProfileModels = new ArrayList<>();

            for (Row userProfileRow : userProfileSheet) {
                //just skip the row if row number is 0
                if (userProfileRow.getRowNum() == 0) {
                    continue;
                }

                CUserProfileModel userProfileModel = new CUserProfileModel();

                userProfileModel.setUserServerID(String.valueOf(
                        cDatabaseUtils.getCellAsNumeric(userProfileRow, 0)));
                userProfileModel.setName(cDatabaseUtils.getCellAsString(userProfileRow, 1));
                userProfileModel.setSurname(cDatabaseUtils.getCellAsString(userProfileRow, 2));
                userProfileModel.setDesignation(cDatabaseUtils.getCellAsString(userProfileRow, 3));
                userProfileModel.setLocation(cDatabaseUtils.getCellAsString(userProfileRow, 4));
                userProfileModel.setEmail(cDatabaseUtils.getCellAsString(userProfileRow, 5));
                userProfileModel.setWebsite(cDatabaseUtils.getCellAsString(userProfileRow, 6));
                userProfileModel.setPhone(cDatabaseUtils.getCellAsString(userProfileRow, 7));
                userProfileModel.setPassword(cDatabaseUtils.getCellAsString(userProfileRow, 8));

                userProfileModel.setUserOwnerID(firebaseUser.getUid());
                userProfileModel.setPhotoUrl("");
                Date now = new Date();
                userProfileModel.setCreatedDate(now);
                userProfileModel.setModifiedDate(now);

                userProfileModels.add(userProfileModel);
            }

            // add logframes
            createUserProfileFromExcel(userProfileModels, callback);
        } else {
            callback.onUploadUserProfilesFailed("Failed to create user profiles.");
        }

    }

    /**
     * create user profiles from a list.
     * FIXME: how to create firebaseUsers from database
     *
     * @param userProfileModels user profile models
     * @param callback          callback
     */
    private void createUserProfileFromExcel(List<CUserProfileModel> userProfileModels,
                                            iUploadUserProfilesRepositoryCallback callback) {

        CollectionReference coUserProfileRef;
        coUserProfileRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);

        // create a batch of user profiles from the list of users
        WriteBatch batch = database.batch();
        for (CUserProfileModel userProfileModel : userProfileModels) {
            DocumentReference documentReference = coUserProfileRef.document();
            userProfileModel.setUserServerID(documentReference.getId());
            batch.set(documentReference, userProfileModel);
        }

        // commit the batch file
        batchWrite(batch, new cFirebaseCallBack() {
            @Override
            public void onFirebaseSuccess(Object object) {
                callback.onUploadUserProfilesSucceeded(
                        "User profiles successfully uploaded.");
            }

            @Override
            public void onFirebaseFailure(Object object) {
                callback.onUploadUserProfilesFailed(
                        "Failed to upload a user profiles.");
            }
        });
    }
}

//                        assert user != null;
//                        if (!user.isEmailVerified()) {
//                            /* send an email verification */
//                            sendEmailVerification(user);
//
//                            /* update the profile in the firebase */
//                            UserProfileChangeRequest profileUpdates;
//                            profileUpdates = new UserProfileChangeRequest.Builder()
//                                    .setDisplayName(name + " " + surname).build();
//                            user.updateProfile(profileUpdates).addOnCompleteListener(
//                                    updateProfileTask -> {
//                                        if (updateProfileTask.isSuccessful()) {
//                                            Log.d(TAG, "User profile updated.");
//                                        }
//                                    });
//
//                            /* update the user profile in the database */
//                            cUserProfileModel userProfileModel = new cUserProfileModel(
//                                    user.getUid(), name, surname, designation, email);
//
//                            // update default date
//                            Date currentDate = new Date();
//                            userProfileModel.setCreatedDate(currentDate);
//                            userProfileModel.setModifiedDate(currentDate);
//
//                            CollectionReference coUsersRef;
//                            coUsersRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);
//                            coUsersRef.document(user.getUid())
//                                    .set(userProfileModel)
//                                    .addOnFailureListener(e -> Log.d(TAG,
//                                            Objects.requireNonNull(e.getLocalizedMessage())));
//
//                            Log.d(TAG, "createUserWithEmailAndPassword:success");
//                            callback.onSignUpSucceeded("Account successfully created.");
//                        }


//                        // create user profile
//                        StorageReference profilePhotoRef = storage.getReference();
//                        profilePhotoRef.child(cRealtimeHelper.KEY_PROFILEPHOTOS)
//                                .child(firebaseUser.getUid())
//                                .putBytes(photoUri)
//                                .addOnSuccessListener(taskSnapshot -> {
//                                    String url = taskSnapshot.getStorage().getPath();
//                                    /* update the user profile in the database */
//                                    String UID = firebaseUser.getUid();
//                                    cUserProfileModel userProfileModel = new cUserProfileModel(UID,
//                                            name, surname, designation, email, url);
//
//                                    // update default date
//                                    Date currentDate = new Date();
//                                    userProfileModel.setCreatedDate(currentDate);
//                                    userProfileModel.setModifiedDate(currentDate);
//                                    userProfileModel.setUserOwnerID(UID);
//
//                                    // add user profile to the database
//                                    CollectionReference coUsersRef;
//                                    coUsersRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);
//                                    coUsersRef.document(UID)
//                                            .set(userProfileModel)
//                                            .addOnCompleteListener(task -> {
//                                                Log.d(TAG, "createUserWithEmailAndPassword:success");
//                                                callback.onSignUpSucceeded("User profile successfully created.");
//                                            })
//                                            .addOnFailureListener(e -> {
//                                                Log.d(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
//                                                callback.onSignUpSucceeded("Failed to create user profile.");
//                                            });
//                                })
//                                .addOnFailureListener(e -> {
//                                    Log.d(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
//                                    callback.onSignUpFailed("Failed to store a profile photo.");
//                                });

//        // update the user profile in the database
//        if (userProfileModel.getUserServerID() == null) {
//            userProfileModel.setUserServerID(firebaseUser.getUid());
//        }
//        userProfileModel.setUserOwnerID(firebaseUser.getUid());
//
//        // update user profile
//        Date currentDate = new Date();
//        userProfileModel.setCreatedDate(currentDate);
//        userProfileModel.setModifiedDate(currentDate);
//
//        CollectionReference coUsersRef;
//        coUsersRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);
//        coUsersRef.document(firebaseUser.getUid())
//                .set(userProfileModel)
//                .addOnCompleteListener(task ->
//                        callback.onSignUpSucceeded("User profile successfully created."))
//                .addOnFailureListener(e ->
//                        callback.onSignUpSucceeded("Failed to create user profile."));


//                    .addOnSuccessListener(taskSnapshot -> {
//                        String URL = taskSnapshot.getStorage().getPath();
//                        // update remaining fields
//                        //Date currentDate = new Date();
//                        //userProfileModel.setCreatedDate(currentDate);
//                        //userProfileModel.setModifiedDate(currentDate);
//                        //userProfileModel.setUserOwnerID(firebaseUserID);
//                        userProfileModel.setPhotoUrl(URL);
//
//                        // add user profile to the database
//                        CollectionReference coUsersRef;
//                        coUsersRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);
//                        coUsersRef.document(userProfileID)
//                                .set(userProfileModel)
//                                .addOnCompleteListener(task -> {
//                                    Log.d(TAG, "createUserWithEmailAndPassword:success");
//                                    callback.onUploadUserProfilesSucceeded(
//                                            "User profile successfully created.");
//                                })
//                                .addOnFailureListener(e -> {
//                                    Log.d(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
//                                    callback.onUploadUserProfilesFailed(
//                                            "Failed to create user profile.");
//                                });
//                    })
//                    .addOnFailureListener(e -> {
//                        Log.d(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
//                        callback.onUploadUserProfilesFailed(
//                                "Failed to store a profile photo.");
//                    });


// create user profile
//            StorageReference profilePhotoRef = storage.getReference();
//
//
//            profilePhotoRef.child(cRealtimeHelper.KEY_PROFILEPHOTOS)
//                    .child(userProfileID)
//                    .putFile(URI)
//                    .addOnSuccessListener(taskSnapshot -> taskSnapshot.getStorage().getDownloadUrl()
//                            .addOnSuccessListener(uri -> {
//                                //if the upload is successful hiding the progress dialog
//                                /* progressDialog.dismiss(); */
//
//                                // upload and update user profile picture
//                                String downloadedURL = uri.toString();
//
//                                userProfileModel.setUserServerID(userProfileID);
//                                userProfileModel.setPhotoUrl(downloadedURL);
//                                //Date currentDate = new Date();
//                                //userProfileModel.setModifiedDate(currentDate);
//
//                                // update user profile to the database
//                                CollectionReference coUsersRef;
//                                coUsersRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);
//                                coUsersRef.document(userProfileID)
//                                        .set(userProfileModel)
//                                        .addOnCompleteListener(task -> {
//                                            if (task.isSuccessful()) {
//                                                callback.onUploadUserProfilesSucceeded(
//                                                        "User profile picture successfully upload.");
//                                            } else {
//                                                callback.onUploadUserProfilesFailed(
//                                                        "Undefined error! Talk to the Admin");
//                                            }
//                                        })
//                                        .addOnFailureListener(e ->
//                                                callback.onUploadUserProfilesFailed(
//                                                        "Failed to upload user profile pictures."));
//
//                            })
//                            .addOnFailureListener(e -> {
//                                //if the upload is not successful hiding the progress dialog
//                                /* progressDialog.dismiss(); */
//                                callback.onUploadUserProfilesFailed(
//                                        "Failed to upload a user profile pictures.");
//                            }))
//                    .addOnProgressListener(snapshot -> {
//                        //calculating progress percentage
//                        /* double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount(); */
//                        //displaying percentage in progress dialog
//                        /* progressDialog.setMessage("Uploaded " + ((int) progress) + "%..."); */
//                    })
//                    .addOnFailureListener(e -> {
//                        //if the upload is not successful hiding the progress dialog
//                        /* progressDialog.dismiss(); */
//                        callback.onUploadUserProfilesFailed(
//                                "Failed to update a user profile picture.");
//                    });

//        profilePhotoRef.child(cRealtimeHelper.KEY_PROFILEPHOTOS)
//                .child(firebaseUser.getUid())
//                .putBytes(userProfileModel.getImageData())
//                .addOnSuccessListener(taskSnapshot -> {
//                    /* update the user profile in the database */
//                    String URL = taskSnapshot.getStorage().getDownloadUrl().toString();
//
//                    String UID = firebaseUser.getUid();
//                    userProfileModel.setUserServerID(UID);
//                    userProfileModel.setUserOwnerID(UID);
//                    userProfileModel.setPhotoUrl(URL);
//
//                    // update default date
//                    Date currentDate = new Date();
//                    userProfileModel.setCreatedDate(currentDate);
//                    userProfileModel.setModifiedDate(currentDate);
//
//                    // add user profile to the database
//                    CollectionReference coUsersRef;
//                    coUsersRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);
//                    coUsersRef.document(UID)
//                            .set(userProfileModel)
//                            .addOnCompleteListener(task -> {
//                                Log.d(TAG, "createUserWithEmailAndPassword:success");
//                                callback.onSignUpSucceeded("User profile successfully created.");
//                            })
//                            .addOnFailureListener(e -> {
//                                Log.d(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
//                                callback.onSignUpSucceeded("Failed to create user profile.");
//                            });
//                })
//                .addOnFailureListener(e -> {
//                    Log.d(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
//                    callback.onSignUpFailed("Failed to store a profile photo.");
//                });


//        StorageReference profilePhotoRef;
//        profilePhotoRef = storage.getReference().child(cRealtimeHelper.KEY_PROFILEPHOTOS);
//
//
//        /* add user profiles  */
//        for (cUserProfileModel userProfileModel : userProfileModels) {
//            // create firebase account
//            String userProfileID = coUserProfileRef.document().getId();
//
//            String email = userProfileModel.getEmail().trim();
//            String password = userProfileModel.getPassword().trim();
//            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    // when the registration of a user is complete, store default user image
//                    StorageReference storageReference;
//                    storageReference = profilePhotoRef.child(userProfileID);
//                    Uri URI = Uri.parse("android.resource://com.me.mseotsanyana.mande" + "/" +
//                            R.drawable.me_default_avatar);
//                    fireStoreImageUri(storageReference, URI, new cFirebaseCallBack() {
//                        @Override
//                        public void onFirebaseSuccess(Object uri) {
//                            // update user profile to the database
//                            userProfileModel.setPhotoUrl(uri.toString());
//
//                            CollectionReference coUsersRef;
//                            coUsersRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);
//                            DocumentReference documentReference;
//                            documentReference = coUsersRef.document(userProfileID);
//                            fireStoreCreate(documentReference, userProfileModel,
//                                    new cFirebaseCallBack() {
//                                        @Override
//                                        public void onFirebaseSuccess(Object success) {
//                                            callback.onUploadUserProfilesSucceeded(
//                                                    "User profile successfully uploaded.");
//                                        }
//
//                                        @Override
//                                        public void onFirebaseFailure(Object failure) {
//                                            callback.onUploadUserProfilesFailed(
//                                                    "Failed to upload user profile pictures.");
//                                        }
//                                    });
//                        }
//
//                        @Override
//                        public void onFirebaseFailure(Object failure) {
//                            callback.onUploadUserProfilesFailed(
//                                    "Failed to upload a user profile image.");
//                        }
//                    });
//
//                } else {
//                    Log.d(TAG, "Error ==== " + task.getException());
//                    callback.onUploadUserProfilesFailed(
//                            "Failed to create a user profile.");
//                }
//            });
//        }


//displaying a progress dialog while upload is going on
//        /*
//        final ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setTitle("Uploading");
//        progressDialog.show();
//        */
//
//        StorageReference profilePhotoRef = storage.getReference();
//        profilePhotoRef.child(cRealtimeHelper.KEY_PROFILEPHOTOS)
//                .child(userServerID)
//                .putBytes(userProfileImageData)
//                .addOnSuccessListener(taskSnapshot -> taskSnapshot.getStorage().getDownloadUrl()
//                        .addOnSuccessListener(uri -> {
//                            //if the upload is successful hiding the progress dialog
//                            /* progressDialog.dismiss(); */
//
//                            // upload and update user profile picture
//                            String downloadedURL = uri.toString();
//                            Date currentDate = new Date();
//
//                            Map<String, Object> userProfileImage = new HashMap<>();
//                            userProfileImage.put("photoUrl", downloadedURL);
//                            userProfileImage.put("modifiedDate", currentDate);
//
//                            // update user profile to the database
//                            CollectionReference coUsersRef;
//                            coUsersRef = database.collection(cRealtimeHelper.KEY_USERPROFILES);
//                            coUsersRef.document(userServerID)
//                                    .update(userProfileImage)
//                                    .addOnCompleteListener(task -> {
//                                        if (task.isSuccessful()) {
//                                            callback.onUpdateUserProfileSucceeded(
//                                                    "User profile picture successfully updated.");
//                                        } else {
//                                            callback.onUpdateUserProfileFailed(
//                                                    "Undefined error! Talk to the Admin");
//                                        }
//                                    })
//                                    .addOnFailureListener(e ->
//                                            callback.onUpdateUserProfileFailed(
//                                                    "Failed to update user profile."));
//
//                        })
//                        .addOnFailureListener(e -> {
//                            //if the upload is not successful hiding the progress dialog
//                            /* progressDialog.dismiss(); */
//                            callback.onUpdateUserProfileFailed(
//                                    "Failed to update a user profile picture.");
//                        }))
//                .addOnProgressListener(snapshot -> {
//                    //calculating progress percentage
//                    /* double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount(); */
//                    //displaying percentage in progress dialog
//                    /* progressDialog.setMessage("Uploaded " + ((int) progress) + "%..."); */
//                })
//                .addOnFailureListener(e -> {
//                    //if the upload is not successful hiding the progress dialog
//                    /* progressDialog.dismiss(); */
//                    callback.onUpdateUserProfileFailed(
//                            "Failed to update a user profile picture.");
//                });