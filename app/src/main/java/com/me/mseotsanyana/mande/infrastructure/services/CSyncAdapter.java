package com.me.mseotsanyana.mande.infrastructure.repository.preference;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.me.mseotsanyana.mande.infrastructure.ports.preference.OnPullCompleteListener;

public class CSyncAdapter implements ValueEventListener {
    private static final String TAG = CSyncAdapter.class.getSimpleName();

    private final CSessionManagerImpl preferences;

    public CSyncAdapter(CSessionManagerImpl preferences){
        this.preferences = preferences;
    }

    /************************* overridden methods from ValueEventListener *************************/

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        new CPullValueEventListenerWrapper(preferences, dataSnapshot).
                addOnPullCompleteListener(new OnPullCompleteListener() {
                    @Override
                    public void OnPullSucceeded(CSessionManagerImpl preferences) {
                        Log.i(TAG, "Synced data");
                    }

                    @Override
                    public void OnPullFailed(Exception e) {
                        Log.e(TAG, "Error while syncing data ",e);
                    }
                });
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.e(TAG, "Error while syncing ", databaseError.toException());
    }
}
