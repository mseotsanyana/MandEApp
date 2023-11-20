package com.me.mseotsanyana.mande.infrastructure.services;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Set;

public class CEditorWrapper implements SharedPreferences.Editor {

    private final SharedPreferences.Editor wrappedEditor;
    private final CSessionManagerImpl sessionManager;

    protected CEditorWrapper(@NonNull CSessionManagerImpl sessionManager){
        this.wrappedEditor = sessionManager.getSharedPreferences().edit();
        this.sessionManager = sessionManager;
    }

    /************************** overridden methods from SharedPreferences *************************/

    @Override
    public SharedPreferences.Editor putString(String s, @Nullable String s1) {
        wrappedEditor.putString(s, s1);
        return this;
    }

    @Override
    public SharedPreferences.Editor putStringSet(String s, @Nullable Set<String> set) {
        wrappedEditor.putStringSet(s, set);
        return this;
    }

    @Override
    public SharedPreferences.Editor putInt(String s, int i) {
        wrappedEditor.putInt(s, i);
        return this;
    }

    @Override
    public SharedPreferences.Editor putLong(String s, long l) {
        wrappedEditor.putLong(s, l);
        return this;
    }

    @Override
    public SharedPreferences.Editor putFloat(String s, float v) {
        wrappedEditor.putFloat(s, v);
        return this;
    }

    @Override
    public SharedPreferences.Editor putBoolean(String s, boolean b) {
        wrappedEditor.putBoolean(s, b);
        return this;
    }

    @Override
    public SharedPreferences.Editor remove(String s) {
        wrappedEditor.remove(s);
        return this;
    }

    @Override
    public SharedPreferences.Editor clear() {
        wrappedEditor.clear();
        return this;
    }

    @Override
    public boolean commit() {
        if(wrappedEditor.commit()){
            sessionManager.push();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void apply() {
        wrappedEditor.apply();
        //sessionManager.push();
    }
}
