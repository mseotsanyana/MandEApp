package com.me.mseotsanyana.mande.infrastructure.repository.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.domain.entities.models.session.cMenuModel;
import com.me.mseotsanyana.mande.infrastructure.ports.preference.OnPullCompleteListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class CSessionManagerImpl implements SharedPreferences,
        SharedPreferences.OnSharedPreferenceChangeListener, FirebaseAuth.AuthStateListener,
        ISessionManager {
    private static final String TAG = CSessionManagerImpl.class.getSimpleName();

    private final SharedPreferences sharedPreferences;
    private final DatabaseReference databaseReference;
    private final CSyncAdapter syncAdapter;

    private final List<String> ommitedKeys = new ArrayList<>();

    private static final Map<String, CSessionManagerImpl> prefInstances = new HashMap<>();

    private static final String pathPattern = String.format(Locale.ENGLISH,"/%s/%s/%s",
            CPreferenceConstant.KEY_USER_PREFS, CPreferenceConstant.KEY_USER_ID,
            CPreferenceConstant.KEY_USER_NAME);

    protected CSessionManagerImpl(SharedPreferences sharedPreferences,
                                  DatabaseReference databaseReference){
        this.sharedPreferences = sharedPreferences;
        this.databaseReference = databaseReference;
        this.syncAdapter = new CSyncAdapter(this);
    }

    public synchronized static CSessionManagerImpl getInstance(Context context, String name,
                                                               int mode){
        return getInstance(context, name, mode, FirebaseDatabase.getInstance());
    }

    private static CSessionManagerImpl getInstance(Context context, String name, int mode,
                                                   FirebaseDatabase firebaseDatabase) {
        if (!prefInstances.containsKey(name)) {
            prefInstances.put(name, new CSessionManagerImpl(
                    context.getApplicationContext().getSharedPreferences(name, mode),
                    getDatabaseReference(name, firebaseDatabase)));
        }
        return prefInstances.get(name);
    }

    /*************************************** getter methods ***************************************/

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void ommitKeys(String... keys) {
        this.ommitedKeys.addAll(Arrays.asList(keys));
    }

    public List<String> getOmmitedKeys() {
        return ommitedKeys;
    }

    /************************************** Firebase methods **************************************/

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        Log.i(TAG, "onAuthStateChanged of "+firebaseAuth.getCurrentUser() +" called");

        if(firebaseAuth.getCurrentUser() != null){

            //sharedPreferences = CSharedFirebasePreferences.getInstance(this.context,
            //        CPreferenceConstant.KEY_USER_PREFS, Context.MODE_PRIVATE);

            keepSynced(true);

            registerOnSharedPreferenceChangeListener(this);

            pull().addOnPullCompleteListener(new OnPullCompleteListener() {
                @Override
                public void OnPullSucceeded(CSessionManagerImpl preferences) {
                    // go to the view model: FIXME
                    Log.i(TAG, "OnPullSucceeded of "+preferences.getAll() +" called");
                }

                @Override
                public void OnPullFailed(Exception e) {
                    // go to the view model: FIXME
                    Log.e(TAG, "OnPullFailed of "+ e.toString() +" failed", e);
                }
            });
        }
    }

    public Task<Void> push() {
        return new CPushTaskWrapper(this)
                .addOnSuccessListener(unused ->
                        Log.i(TAG, "Push of "+getDatabaseReference().toString() +" succeeded"))
                .addOnFailureListener(e ->
                        Log.e(TAG, "Push of "+getDatabaseReference().toString() +" failed", e));
    }

    public CPullValueEventListenerWrapper pull(){
        return new CPullValueEventListenerWrapper(this).
                addOnPullCompleteListener(new OnPullCompleteListener() {
                    @Override
                    public void OnPullSucceeded(CSessionManagerImpl preferences) {
                        Log.i(TAG, "Pull of "+getDatabaseReference().toString() + "succeeded");
                    }

                    @Override
                    public void OnPullFailed(Exception e) {
                        Log.e(TAG, "Pull of "+getDatabaseReference().toString() + "failed ", e);
                    }
                });
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    @NonNull
    private static DatabaseReference getDatabaseReference(String name, FirebaseDatabase db){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser == null) {
            throw new IllegalStateException("No user signed in with firebase");
        }

        return db.getReference(formatFirebasePath(pathPattern.
                replace(CPreferenceConstant.KEY_USER_ID, firebaseUser.getUid()).
                replace(CPreferenceConstant.KEY_USER_PREFS, name)));
    }

    public void keepSynced(boolean b){
        databaseReference.keepSynced(b);
        if(b){
            databaseReference.addValueEventListener(syncAdapter);
        } else {
            databaseReference.removeEventListener(syncAdapter);
        }
    }


    @NonNull
    private static String formatFirebasePath(@NonNull String pathPattern){
        return pathPattern.
                replace('.','-').
                replace('#','-').
                replace('$','-').
                replace('[','-').
                replace(']','-');
    }

    /************************** overridden methods from SharedPreferences *************************/

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // go to the view model: FIXME
        Log.i(TAG, "onSharedPreferenceChanged of "+ s +" called");
    }

    @Override
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    @Nullable
    @Override
    public String getString(String s, @Nullable String s1) {
        return sharedPreferences.getString(s, s1);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String s, @Nullable Set<String> set) {
        return null;
    }

    @Override
    public int getInt(String s, int i) {
        return sharedPreferences.getInt(s, i);
    }

    @Override
    public long getLong(String s, long l) {
        return sharedPreferences.getLong(s, l);
    }

    @Override
    public float getFloat(String s, float v) {
        return sharedPreferences.getFloat(s, v);
    }

    @Override
    public boolean getBoolean(String s, boolean b) {
        return sharedPreferences.getBoolean(s, b);
    }

    @Override
    public boolean contains(String s) {
        return sharedPreferences.contains(s);
    }

    @Override
    public Editor edit() {
        return new CEditorWrapper(this);
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(
            OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(
            OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    /************************************ user session methods ************************************/

    @Override
    public void commitSettings() {

    }

    @Override
    public void deleteSettings() {

    }

    @Override
    public void clearAllSettings() {

    }

    @Override
    public void removeSetting(String key) {

    }

    @Override
    public void saveIntSetting(String key, int value) {

    }

    @Override
    public void saveStringSetting(String key, String value) {

    }

    @Override
    public void saveBooleanSetting(String key, Boolean value) {

    }

    @Override
    public void saveListOfIntegerSetting(String key, List<Integer> value) {

    }

    @Override
    public void saveListOfStringSetting(String key, List<String> value) {

    }

    @Override
    public void saveMenuItems(String key, List<cMenuModel> value) {

    }

    @Override
    public void saveCurrentWorkspace(String compositeServerID) {

    }

    @Override
    public String loadUserID() {
        return null;
    }

    @Override
    public String loadActiveOrganizationID() {
        return null;
    }

    @Override
    public String loadCurrentWorkspace() {
        return null;
    }

    @Override
    public int loadWorkspaceServerID() {
        return 0;
    }

    @Override
    public int loadWorkspaceMembershipBITS() {
        return 0;
    }

    @Override
    public List<String> loadMyOrganizations() {
        return null;
    }

    @Override
    public int loadActiveWorkspaceBIT() {
        return 0;
    }

    @Override
    public List<Integer> loadSecondaryWorkspaces() {
        return null;
    }

    @Override
    public int loadModuleBITS() {
        return 0;
    }

    @Override
    public int loadEntityBITS(int moduleKey) {
        return 0;
    }

    @Override
    public int loadEntityPermissionBITS(int moduleKey, int entityKey) {
        return 0;
    }

    @Override
    public int loadActionPermissionBITS(int moduleKey, int entityKey, String actionKey) {
        return 0;
    }

    @Override
    public List<Integer> loadOperationStatuses(int moduleKey, int entityKey, int operationKey) {
        return null;
    }

    @Override
    public int loadUnixPermissionBITS(int moduleKey, int entityKey) {
        return 0;
    }

    @Override
    public List<cMenuModel> loadMenuItems() {
        return null;
    }

    @Override
    public void deleteCurrentWorkspace() {

    }
}
