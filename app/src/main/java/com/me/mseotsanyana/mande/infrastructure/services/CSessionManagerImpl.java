package com.me.mseotsanyana.mande.infrastructure.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.domain.entities.models.session.CMenuModel;
import com.me.mseotsanyana.mande.infrastructure.ports.preference.OnPullCompleteListener;

import java.lang.reflect.Type;
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

    private static CSessionManagerImpl sInstance;
    private final SharedPreferences sharedPreferences;
    private final FirebaseFirestore databaseReference;
    private final CSyncAdapter syncAdapter;
    private DocumentReference documentReference;

    private final List<String> ommitedKeys = new ArrayList<>();

    private static final Map<String, CSessionManagerImpl> prefInstances = new HashMap<>();

    private static final String pathPattern = String.format(Locale.ENGLISH, "/%s/%s",
            CPreferenceConstant.KEY_USER_PREFS, CPreferenceConstant.KEY_USER_ID);

    protected CSessionManagerImpl(SharedPreferences sharedPreferences,
                                  DocumentReference documentReference) {
        this.sharedPreferences = sharedPreferences;
        this.databaseReference = FirebaseFirestore.getInstance();
        this.documentReference = documentReference;
        this.syncAdapter = new CSyncAdapter(this);
    }

    public synchronized static CSessionManagerImpl getInstance(Context context) {
        if (sInstance == null)
            sInstance = getInstance(context,
                    CPreferenceConstant.KEY_USER_PREFS, Context.MODE_PRIVATE);
        return sInstance;
    }

    public static CSessionManagerImpl getInstance(Context context, String name, int mode) {
        return getInstance(context, name, mode, FirebaseFirestore.getInstance());
    }

    private static CSessionManagerImpl getInstance(Context context, String name, int mode,
                                                   FirebaseFirestore db) {
        if (!prefInstances.containsKey(name)) {
            prefInstances.put(name, new CSessionManagerImpl(
                    context.getApplicationContext().getSharedPreferences(name, mode),
                    getDocumentReference(name, db)));
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
        Log.i(TAG, "onAuthStateChanged of " + firebaseAuth.getCurrentUser() + " triggered.");

        if (firebaseAuth.getCurrentUser() != null) {

            //keepSynced(true);

            registerOnSharedPreferenceChangeListener(this);

            pull().addOnPullCompleteListener(new OnPullCompleteListener() {
                @Override
                public void OnPullSucceeded(CSessionManagerImpl preferences) {
                    // go to the view model: FIXME
                    Log.i(TAG, "OnPullSucceeded of " + preferences.getAll() + " triggered.");
                }

                @Override
                public void OnPullFailed(Exception e) {
                    // go to the view model: FIXME
                    Log.e(TAG, "OnPullFailed of " + e.toString() + " failed", e);
                }
            });
        }
    }

    public Task<Void> push() {
        return new CPushTaskWrapper(this)
                .addOnSuccessListener(unused ->
                        Log.i(TAG, "Push of " + getDocumentReference().toString() + " succeeded"))
                .addOnFailureListener(e ->
                        Log.e(TAG, "Push of " + getDocumentReference().toString() + " failed", e));
    }

    public CPullEventListenerWrapper pull() {
        return new CPullEventListenerWrapper(this).
                addOnPullCompleteListener(new OnPullCompleteListener() {
                    @Override
                    public void OnPullSucceeded(CSessionManagerImpl preferences) {
                        Log.i(TAG, "Pull of " + getDocumentReference().toString() + "succeeded");
                    }

                    @Override
                    public void OnPullFailed(Exception e) {
                        Log.e(TAG, "Pull of " + getDocumentReference().toString() + "failed ", e);
                    }
                });
    }

    public DocumentReference getDocumentReference() {
        return documentReference;
    }

    public void setDocumentReference(DocumentReference documentReference){
        this.documentReference = documentReference;
    }

    private static DocumentReference getDocumentReference(String name, FirebaseFirestore db) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

//        assert firebaseUser != null;
//        Log.i(TAG, "Name = "+ formatFirebasePath(name) + ", Database = "+
//                db.collection(name).document("ttt") +", Document = "+
//                pathPattern.replace(CPreferenceConstant.KEY_USER_ID, firebaseUser.getUid())+", ALL = "+
//                db.collection(formatFirebasePath(name)).document(firebaseUser.getUid()));

        if (firebaseUser == null) {
            throw new IllegalStateException("No user signed in with firebase");
        }

        return db.collection(formatFirebasePath(name)).document(firebaseUser.getUid());
    }

//    public void keepSynced(boolean b) {
//        databaseReference.keepSynced(b);
//        if (b) {
//            databaseReference.keepSynced.addSnapshotListener(syncAdapter);
//        } else {
//            databaseReference.(syncAdapter);
//        }
//    }

    @NonNull
    private static String formatFirebasePath(@NonNull String pathPattern) {
        return pathPattern.
                replace('.', '-').
                replace('#', '-').
                replace('$', '-').
                replace('[', '-').
                replace(']', '-');
    }

    /************************** overridden methods from SharedPreferences *************************/

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // go to the view model: FIXME
        Log.i(TAG, "onSharedPreferenceChanged of " + s + " called");
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
    public boolean isCommitted() {
        return edit().commit();
    }

    @Override
    public void commitSettings() {
        edit().apply();
    }

    @Override
    public void deleteSettings() {

    }

    @Override
    public void clearApplied() {
        edit().clear().apply();
    }

    @Override
    public boolean isClearCommitted() {
        return edit().clear().commit();
    }

    public boolean updateDocumentReference(String documentID) {
        if (!documentID.isEmpty()){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            setDocumentReference(db.collection(formatFirebasePath(CPreferenceConstant.KEY_USER_PREFS)).
                    document(documentID));

            return true;
        } else {
            throw new IllegalStateException("Failed to update document identification!");
        }
    }

    /**
     *     Save and get HashMap in SharedPreference
     */
    @Override
    public void saveHashMap(String key , Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        edit().putString(key,json).apply();
    }

    @Override
    public HashMap<String, Object> loadHashMap(String key) {
        Gson gson = new Gson();
        String json = getString(key,"");
        java.lang.reflect.Type type = new TypeToken<HashMap<String,Object>>(){}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void removeSetting(String key) {

    }

    @Override
    public void saveWorkspaceMembershipBITS(String key, int value) {
        edit().putInt(key, value).apply();
    }

    @Override
    public void saveUserServerID(String key, String value) {
        edit().putString(key, value).apply();
    }

    @Override
    public void saveCompositeServerID(String key, String value) {
        edit().putString(key, value).apply();
    }

    @Override
    public void saveOwnerServerID(String key, String value) {
        edit().putString(key, value).apply();
    }

    @Override
    public void saveLoggedInUserServerID(String key, String value) {
        edit().putString(key, value).apply();
    }

    @Override
    public void saveOrganizationSeverID(String key, String value) {
        edit().putString(key, value).apply();
    }

    @Override
    public void saveOrganizationOwnerSeverID(String key, String value) {
        edit().putString(key, value).apply();
    }

    @Override
    public void saveWorkspaceOwnerBIT(String key, int value) {
        edit().putInt(key, value).apply();
    }

    @Override
    public void saveBooleanSetting(String key, Boolean value) {

    }

    @Override
    public void saveListOfIntegerSetting(String key, List<Integer> value) {

    }

    @Override
    public void saveMyOrganizations(String key, List<String> value) {
        String JSONString = new Gson().toJson(value);
        edit().putString(key, JSONString).apply();
    }

    @Override
    public void saveStatusBITS(String key, int value) {
        edit().putInt(key, value).apply();
    }

    @Override
    public void saveActionBITS(String key, int value) {
        edit().putInt(key, value).apply();
    }

    @Override
    public void savePermissionBITS(String key, int value) {
        edit().putInt(key, value).apply();
    }

    @Override
    public void saveEntityBITS(String key, int value) {
        edit().putInt(key, value).apply();
    }

    @Override
    public void saveModuleBITS(String key, int value) {
        edit().putInt(key, value).apply();
    }

    @Override
    public void saveMenuItems(String key, List<CMenuModel> value) {
        String menuJSONString = new Gson().toJson(value);
        edit().putString(key, menuJSONString).apply();
    }

    @Override
    public void saveCurrentWorkspace(String compositeServerID) {

    }

    @Override
    public String loadCompositeServerID() {
        return getSharedPreferences().getString(CPreferenceConstant.KEY_WORKSPACE_COMPOSITE_ID, null);
    }

    @Override
    public String loadLoggedInUserServerID() {
        return getSharedPreferences().getString(CPreferenceConstant.KEY_USER_ID, null);
    }

    @Override
    public String loadActiveOrganizationID() {
        return getSharedPreferences().getString(CPreferenceConstant.KEY_ORG_ID,null);
    }

    @Override
    public int loadActiveWorkspaceBIT() {
        return getSharedPreferences().getInt(CPreferenceConstant.KEY_WORKSPACE_OWNER_BIT, -1);
    }

    @Override
    public int loadUnixPermissionBITS(int moduleKey, int entityKey) {
        return getSharedPreferences().getInt(CPreferenceConstant.KEY_UNIX_PERM_BITS + "-" +
                moduleKey + "-" + entityKey, -1);
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
    public List<CMenuModel> loadMenuItems(String key) {
        String jsonString = getSharedPreferences().getString(key,null);
        Type type = new TypeToken<List<CMenuModel>>() {}.getType();
        return new Gson().fromJson(jsonString, type);
    }

    @Override
    public void deleteCurrentWorkspace() {

    }
}

//    private static final String pathPattern = String.format(Locale.ENGLISH, "/%s/%s/%s",
//            CPreferenceConstant.KEY_USER_PREFS, CPreferenceConstant.KEY_USER_ID,
//            CPreferenceConstant.KEY_USER_NAME);

//    @NonNull
//    private static CollectionReference getDatabaseReference(String name, FirebaseFirestore db) {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (firebaseUser == null) {
//            throw new IllegalStateException("No user signed in with firebase");
//        }
//
//        return db.collection(formatFirebasePath(pathPattern.
//                replace(CPreferenceConstant.KEY_USER_ID, firebaseUser.getUid()).
//                replace(CPreferenceConstant.KEY_USER_PREFS, name)));
//    }

//    public void keepSynced(boolean b) {
//        databaseReference.keepSynced(b);
//        if (b) {
//            databaseReference.addEventListener(syncAdapter);
//        } else {
//            databaseReference.removeEventListener(syncAdapter);
//        }
//    }