package com.me.mseotsanyana.mande.application.ports.base.firebase;

import static com.me.mseotsanyana.mande.application.structures.CFirestoreConstant.FAILURE;
import static com.me.mseotsanyana.mande.application.structures.CFirestoreConstant.SUCCESS;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public abstract class CFirestoreRepository {
    private static final String TAG = CFirestoreRepository.class.getSimpleName();

    /**
     * Insert Uri image in FireStorage
     *
     * @param storageReference Storage reference of data to be add
     * @param imageUri         Uri to insert into Storage
     * @param callback         callback for event handling
     */
    protected final void fireStoreImageUri(final StorageReference storageReference,
                                           final Uri imageUri, final CFirestoreCallBack callback) {
        storageReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> taskSnapshot.getStorage().getDownloadUrl()
                        .addOnSuccessListener(callback::onFirebaseSuccess)
                        .addOnFailureListener(e -> {
                            callback.onFirebaseFailure(null);
                        }))
                .addOnProgressListener(snapshot -> {
                    //calculating progress percentage
                    double progress = (100.0 * snapshot.getBytesTransferred()) /
                            snapshot.getTotalByteCount();
                    //displaying percentage in progress dialog
                    Log.d(TAG, (int) progress + "% Uploading...");
                })
                .addOnFailureListener(e -> {
                    callback.onFirebaseFailure(null);
                });
    }

    /**
     * Insert Uri image in FireStorage
     *
     * @param storageReference Storage reference of data to be add
     * @param imageData        Uri to insert into Storage
     * @param callback         callback for event handling
     */
    protected final void fireStoreImageData(final StorageReference storageReference,
                                            final byte[] imageData, final CFirestoreCallBack callback) {
        storageReference.putBytes(imageData)
                .addOnSuccessListener(taskSnapshot -> {
                            taskSnapshot.getStorage().getDownloadUrl()
                                    .addOnSuccessListener(callback::onFirebaseSuccess)
                                    .addOnFailureListener(e -> {
                                        callback.onFirebaseFailure(null);
                                    });
                        }

                )
                .addOnProgressListener(snapshot -> {
                    //calculating progress percentage
                    double progress = (100.0 * snapshot.getBytesTransferred()) /
                            snapshot.getTotalByteCount();
                    //displaying percentage in progress dialog
                    Log.d(TAG, (int) progress + "% Uploading...");
                })
                .addOnFailureListener(e -> {
                    callback.onFirebaseFailure(null);
                });
    }

    /**
     * Insert data on FireStore
     *
     * @param documentReference Document reference of data to be add
     * @param model             Model to insert into Document
     * @param callback          callback for event handling
     */
    protected final void fireStoreCreate(@NonNull final DocumentReference documentReference,
                                         final Object model, final CFirestoreCallBack callback) {
        documentReference.set(model)
                .addOnSuccessListener(aVoid -> callback.onFirebaseSuccess(SUCCESS))
                .addOnFailureListener(callback::onFirebaseFailure);
    }

    /**
     * Update data to FireStore
     *
     * @param documentReference Document reference of data to update
     * @param map               Data map to update
     * @param callback          callback for event handling
     */
    protected final void fireStoreUpdate(final DocumentReference documentReference,
                                         final Map<String, Object> map,
                                         final CFirestoreCallBack callback) {
        documentReference.update(map)
                .addOnSuccessListener(aVoid -> callback.onFirebaseSuccess(SUCCESS))
                .addOnFailureListener(callback::onFirebaseFailure);
    }

    /**
     * FireStore Create or Merge
     *
     * @param documentReference Document reference of data to create update
     * @param model             Model to create or update into Document
     */
    protected final void fireStoreCreateOrMerge(final DocumentReference documentReference,
                                                final Object model,
                                                final CFirestoreCallBack callback) {
        documentReference.set(model, SetOptions.merge())
                .addOnSuccessListener(aVoid -> callback.onFirebaseSuccess(SUCCESS))
                .addOnFailureListener(callback::onFirebaseFailure);
    }

    /**
     * Delete data from FireStore
     *
     * @param documentReference Document reference of data to delete
     * @param callback          callback for event handling
     */
    protected final void fireStoreDelete(final DocumentReference documentReference,
                                         final CFirestoreCallBack callback) {
        documentReference.delete()
                .addOnSuccessListener(aVoid -> callback.onFirebaseSuccess(SUCCESS))
                .addOnFailureListener(callback::onFirebaseFailure);
    }

    /**
     * FireStore Batch write
     *
     * @param batch    Document reference of data to delete
     * @param callback callback for event handling
     */
    protected final void batchWrite(WriteBatch batch, final CFirestoreCallBack callback) {
        batch.commit()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        callback.onFirebaseSuccess(SUCCESS);
                    else
                        callback.onFirebaseFailure(FAILURE);
                });
    }

    /**
     * One time data fetch from FireStore with Document reference
     *
     * @param documentReference query of Document reference to fetch data
     * @param callBack          callback for event handling
     */
    protected final void readDocument(final DocumentReference documentReference,
                                      final CFirestoreCallBack callBack) {
        documentReference.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            callBack.onFirebaseSuccess(document);
                        } else {
                            callBack.onFirebaseSuccess(null);
                        }
                    } else {
                        callBack.onFirebaseFailure(task.getException());
                    }
                });
    }

    /**
     * Data fetch listener with Document reference
     *
     * @param documentReference to add childEvent listener
     * @param callBack          callback for event handling
     * @return EventListener
     */
    protected final ListenerRegistration readDocumentByListener(
            final DocumentReference documentReference, final CFirestoreCallBack callBack) {
        return documentReference.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                callBack.onFirebaseFailure(e);
                return;
            }
            if (snapshot != null && snapshot.exists()) {
                callBack.onFirebaseSuccess(snapshot);
            } else {
                callBack.onFirebaseSuccess(null);
            }
        });
    }

    /**
     * One time data fetch from FireStore with Query reference
     *
     * @param query    query of Document reference to fetch data
     * @param callBack callback for event handling
     */
    protected final void readQueryDocuments(@NonNull final Query query,
                                            @NonNull final CFirestoreCallBack callBack) {
        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            callBack.onFirebaseSuccess(querySnapshot);
                        } else {
                            callBack.onFirebaseSuccess(null);
                        }
                    } else {
                        callBack.onFirebaseFailure(task.getException());
                    }
                })
                .addOnFailureListener(callBack::onFirebaseFailure);
    }

    /**
     * Data fetch listener with Query reference
     *
     * @param query    query of Document reference to fetch data
     * @param callback callback for event handling
     */
    @NonNull
    protected final ListenerRegistration readQueryDocumentsByListener(
            final Query query, final CFirestoreCallBack callback) {
        return query.addSnapshotListener((snapshots, e) -> {
            if (e != null) {
                callback.onFirebaseFailure(e);
                return;
            }
            callback.onFirebaseSuccess(snapshots);
        });
    }

    /**
     * Data fetch ChildEventListener with Query reference
     *
     * @param query    to add childEvent listener
     * @param callBack callback for event handling
     * @return ChildEventListener
     */
    @NonNull
    protected final ListenerRegistration readQueryDocumentsByChildEventListener(
            @NonNull final Query query, final CFirestoreChildCallBack callBack) {
        return query.addSnapshotListener((snapshots, e) -> {
            if (e != null || snapshots == null || snapshots.isEmpty()) {
                callBack.onCancelled(e);
                return;
            }
            for (DocumentChange documentChange : snapshots.getDocumentChanges()) {
                switch (documentChange.getType()) {
                    case ADDED:
                        callBack.onChildAdded(documentChange.getDocument());
                        break;
                    case MODIFIED:
                        callBack.onChildChanged(documentChange.getDocument());
                        break;
                    case REMOVED:
                        callBack.onChildRemoved(documentChange.getDocument());
                        break;
                }
            }
        });
    }

    /**
     * Read offline data from FireBase
     *
     * @param query Document reference of data to create
     */
    protected final void fireStoreOfflineRead(final Query query, final CFirestoreCallBack callBack) {
        query.addSnapshotListener(MetadataChanges.INCLUDE, (querySnapshot, e) -> {
            if (e != null) {
                callBack.onFirebaseFailure(e);
                return;
            }
            callBack.onFirebaseSuccess(querySnapshot);
        });
    }
}

