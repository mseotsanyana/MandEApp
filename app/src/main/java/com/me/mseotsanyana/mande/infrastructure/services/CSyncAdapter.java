package com.me.mseotsanyana.mande.infrastructure.services;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.me.mseotsanyana.mande.infrastructure.ports.preference.OnPullCompleteListener;

public class CSyncAdapter implements EventListener<DocumentSnapshot> {
    private static final String TAG = CSyncAdapter.class.getSimpleName();

    private final CSessionManagerImpl sessionManager;

    public CSyncAdapter(CSessionManagerImpl sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**************************** overridden methods from EventListener ***************************/

    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                        @Nullable FirebaseFirestoreException e) {
        new CPullEventListenerWrapper(sessionManager, documentSnapshot).
                addOnPullCompleteListener(new OnPullCompleteListener() {
                    @Override
                    public void OnPullSucceeded(CSessionManagerImpl preferences) {
                        Log.i(TAG, "Synced data");
                    }

                    @Override
                    public void OnPullFailed(Exception e) {
                        Log.e(TAG, "Error while syncing data ", e);
                    }
                });
    }
}

//    @Override
//    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//        new CPullValueEventListenerWrapper(preferences, dataSnapshot).
//                addOnPullCompleteListener(new OnPullCompleteListener() {
//                    @Override
//                    public void OnPullSucceeded(CSessionManagerImpl preferences) {
//                        Log.i(TAG, "Synced data");
//                    }
//
//                    @Override
//                    public void OnPullFailed(Exception e) {
//                        Log.e(TAG, "Error while syncing data ",e);
//                    }
//                });
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError databaseError) {
//        Log.e(TAG, "Error while syncing ", databaseError.toException());
//    }