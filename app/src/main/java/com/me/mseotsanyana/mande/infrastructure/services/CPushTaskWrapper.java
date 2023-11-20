package com.me.mseotsanyana.mande.infrastructure.repository.preference;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Executor;

public class CPushTaskWrapper extends Task<Void> {

    private final Task<Void> pushTaskWrapper;

    CPushTaskWrapper(@NonNull CSessionManagerImpl preferences){
        HashMap<String, Object> values = new HashMap<>(preferences.getAll());
        for (String key: values.keySet()) {
            if (values.get(key) instanceof Set) {
                values.put(key, new ArrayList<>((Set<String>) values.get(key)));
            }
        }

        for (String key: new ArrayList<>(values.keySet())){
            if(preferences.getOmmitedKeys().contains(key)){
                values.remove(key);
            }
        }

        // push the preferences to the Firestore database
        pushTaskWrapper = preferences.getDatabaseReference().updateChildren(values);
    }

    /***************************** overridden methods from Task<Void> *****************************/

    @NonNull
    @Override
    public Task<Void> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return pushTaskWrapper.addOnFailureListener(onFailureListener);
    }

    @NonNull
    @Override
    public Task<Void> addOnFailureListener(@NonNull Activity activity,
                                           @NonNull OnFailureListener onFailureListener) {
        return pushTaskWrapper.addOnFailureListener(activity, onFailureListener);
    }

    @NonNull
    @Override
    public Task<Void> addOnFailureListener(@NonNull Executor executor,
                                           @NonNull OnFailureListener onFailureListener) {
        return pushTaskWrapper.addOnFailureListener(executor, onFailureListener);
    }

    @NonNull
    @Override
    public Task<Void> addOnSuccessListener(
            @NonNull OnSuccessListener<? super Void> onSuccessListener) {
        return pushTaskWrapper.addOnSuccessListener(onSuccessListener);
    }

    @NonNull
    @Override
    public Task<Void> addOnSuccessListener(
            @NonNull Activity activity, @NonNull OnSuccessListener<? super Void> onSuccessListener) {
        return pushTaskWrapper.addOnSuccessListener(activity, onSuccessListener);
    }

    @NonNull
    @Override
    public Task<Void> addOnSuccessListener(
            @NonNull Executor executor, @NonNull OnSuccessListener<? super Void> onSuccessListener) {
        return pushTaskWrapper.addOnSuccessListener(executor, onSuccessListener);
    }

    @Nullable
    @Override
    public Exception getException() {
        return pushTaskWrapper.getException();
    }

    @Override
    public Void getResult() {
        return pushTaskWrapper.getResult();
    }

    @Override
    public <X extends Throwable> Void getResult(@NonNull Class<X> aClass) throws X {
        return pushTaskWrapper.getResult(aClass);
    }

    @Override
    public boolean isCanceled() {
        return pushTaskWrapper.isCanceled();
    }

    @Override
    public boolean isComplete() {
        return pushTaskWrapper.isComplete();
    }

    @Override
    public boolean isSuccessful() {
        return pushTaskWrapper.isSuccessful();
    }
}
