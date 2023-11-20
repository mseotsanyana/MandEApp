package com.me.mseotsanyana.mande.infrastructure.services;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.me.mseotsanyana.mande.infrastructure.ports.preference.OnPullCompleteListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CPullValueEventListenerWrapper implements EventListener<DocumentSnapshot> {
    private static final String TAG = CPullValueEventListenerWrapper.class.getSimpleName();

    private final CSessionManagerImpl sessionManager;
    private final List<OnPullCompleteListener> listeners = new ArrayList<>();

    public CPullValueEventListenerWrapper(CSessionManagerImpl sessionManager){
        this.sessionManager = sessionManager;
        this.sessionManager.getDocumentReference().addSnapshotListener(this);

        //this.sessionManager.getDatabaseReference().addListenerForSingleValueEvent(this);
    }

    public CPullValueEventListenerWrapper(CSessionManagerImpl sessionManager,
                                          DocumentSnapshot documentSnapshot){
        this.sessionManager = sessionManager;
        onEvent(documentSnapshot, null);
        //onDataChange(dataSnapshot);
    }

    public CPullValueEventListenerWrapper addOnPullCompleteListener(OnPullCompleteListener listener){
        this.listeners.add(listener);
        return this;
    }

    private void dispatchFetchSucceeded(){
        for(OnPullCompleteListener listener: this.listeners){
            try {
                listener.OnPullSucceeded(this.sessionManager);
            }catch (Exception e){
                Log.e(TAG, "Error in dispatching onPullSucceeded event ", e);
            }
        }
    }

    private void dispatchFetchFailed(Exception e){
        for(OnPullCompleteListener listener: this.listeners){
            try {
                listener.OnPullFailed(e);
            }catch (Exception e1){
                Log.e(TAG, "Error in dispatching OnPullFailed event ", e);
            }
        }
    }

    /************************* overridden methods from ValueEventListener *************************/

    @Override
    public void onEvent(@Nullable DataSnapshot dataSnapshot, @Nullable FirebaseFirestoreException e) {

    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            return;
        }

        try {
            SharedPreferences.Editor editor = sessionManager.getSharedPreferences().edit().clear();
            for(DocumentSnapshot snapshot: dataSnapshot.getChildren()){
                String key = snapshot.getKey();
                Object value = snapshot.getValue();

                if (value instanceof String){
                    editor.putString(key, (String) value);
                }else if(value instanceof Long){
                    editor.putLong(key, (Long) value);
                }else if(value instanceof Integer){
                    editor.putInt(key, (Integer) value);
                }else if(value instanceof Boolean){
                    editor.putBoolean(key, (Boolean) value);
                }else if (value instanceof Float){
                    editor.putFloat(key, (Float) value);
                }else if (value instanceof List){
                    editor.putStringSet(key, new HashSet<>((List<String>) value));
                }
            }

            editor.apply();

        }catch (Exception exception){
            Log.e(TAG, "Error while processing fetched data", exception);
            dispatchFetchFailed(exception);
        }

        dispatchFetchSucceeded();
    }
}

//    @Override
//    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//        try {
//            @SuppressLint("CommitPrefEdits")
//            SharedPreferences.Editor editor = sessionManager.getSharedPreferences().edit().clear();
//            for(DataSnapshot snapshot: dataSnapshot.getChildren()){
//                String key = snapshot.getKey();
//                Object value = snapshot.getValue();
//
//                if (value instanceof String){
//                    editor.putString(key, (String) value);
//                }else if(value instanceof Long){
//                    editor.putLong(key, (Long) value);
//                }else if(value instanceof Integer){
//                    editor.putInt(key, (Integer) value);
//                }else if(value instanceof Boolean){
//                    editor.putBoolean(key, (Boolean) value);
//                }else if (value instanceof Float){
//                    editor.putFloat(key, (Float) value);
//                }else if (value instanceof List){
//                    editor.putStringSet(key, new HashSet<>((List<String>) value));
//                }
//            }
//
//            editor.apply();
//
//        }catch (Exception e){
//            Log.e(TAG, "Error while processing fetched data", e);
//            dispatchFetchFailed(e);
//        }
//
//        dispatchFetchSucceeded();
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError databaseError) {
//        dispatchFetchFailed(databaseError.toException());
//    }