package com.me.mseotsanyana.mande.infrastructure.repository.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.domain.entities.models.session.cMenuModel;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManagerRepository;
import com.me.mseotsanyana.mande.infrastructure.ports.preference.OnPullCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CSessionManagerRepositoryImpl_old
        implements ISessionManagerRepository, FirebaseAuth.AuthStateListener,
        SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = CSessionManagerRepositoryImpl_old.class.getSimpleName();

    private final Context context;
    private CSharedFirebasePreferences preferences;
    private CEditorWrapper editor;

    public CSessionManagerRepositoryImpl_old(@NonNull Context context) {
//        (context.getSharedPreferences(CPreferenceConstant.KEY_USER_PREFS, Context.MODE_PRIVATE));FIXME
//        setEditor(preferences.getSharedPreferences());

//        SharedPreferences preferences = context.getSharedPreferences(
//                CPreferenceConstant.KEY_USER_PREFS, Context.MODE_PRIVATE);

//        SharedPreferences.Editor e = preferences.edit();
//        e.clear();
//        e.apply();
//        FirebaseAuth.getInstance().signOut();
//        FirebaseFirestore.getInstance().clearPersistence();

        this.context = context;
        FirebaseAuth.getInstance().addAuthStateListener(this);
        //editor = new CEditorWrapper(preferences);
    }

//    public CEditorWrapper getEditor() {
//        return editor;
//    }
//
//    public void setEditor(CEditorWrapper editor) {
//        this.editor = editor;
//    }
//
//    public SharedPreferences getSettings() {
//        return preferences;
//    }
//
//    public void setSettings(CSharedFirebasePreferences preferences) {
//        this.preferences = preferences;
//    }

    /******************* overridden methods from FirebaseAuth.AuthStateListener *******************/

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        Log.i(TAG, "onAuthStateChanged of "+firebaseAuth.getCurrentUser() +" called");

        if(firebaseAuth.getCurrentUser() != null){
            preferences = CSharedFirebasePreferences.getInstance(this.context,
                    CPreferenceConstant.KEY_USER_PREFS, Context.MODE_PRIVATE);
            preferences.keepSynced(true);
            preferences.registerOnSharedPreferenceChangeListener(this);

            preferences.pull().addOnPullCompleteListener(new OnPullCompleteListener() {
                @Override
                public void OnPullSucceeded(CSharedFirebasePreferences preferences) {
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

    /********* overridden methods from SharedPreferences.OnSharedPreferenceChangeListener *********/

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // go to the view model: FIXME
        Log.i(TAG, "onSharedPreferenceChanged of "+ s +" called");

    }

    /* ###################### FUNCTIONS FOR CRUD AND LOG IN/OUT OPERATIONS ###################### */

    /**
     * This saves the preference of the loggedIn user. Should be called
     * after every preference setting function.
     */
    @Override
    public void commitSettings() {
        editor.commit();
    }

    /**
     * This deletes all the saved settings of the loggedIn user.
     */
    @Override
    public void deleteSettings() {
        // clear all saved preferences
        editor.clear();
    }

    /**
     * This clears and commits all settings of the loggedIn user
     */
    @Override
    public void clearAllSettings() {
        editor.clear();
        editor.commit();
    }

    /**
     * This takes a key parameter and removes the corresponding preference.
     *
     * @param key key
     */
    @Override
    public void removeSetting(String key) {
        editor.remove(key);
    }

    /**
     * This takes the key and value parameters of an integer setting and
     * updates the corresponding preference.
     *
     * @param key   key
     * @param value value
     */
    @Override
    public void saveIntSetting(String key, int value) {
        editor.putInt(key, value);
    }

    /**
     * This takes the key and value parameters of an string setting and
     * updates the corresponding preference.
     *
     * @param key   key
     * @param value value
     */
    @Override
    public void saveStringSetting(String key, String value) {
        editor.putString(key, value);
    }

    /**
     * This takes the key and value parameters of an boolean setting and
     * updates the corresponding preference.
     *
     * @param key   key
     * @param value value
     */
    @Override
    public void saveBooleanSetting(String key, Boolean value) {
        editor.putBoolean(key, value);
    }

    /**
     * This takes the key and value parameters of an integer list setting and
     * updates the corresponding preference.
     *
     * @param key   key
     * @param value value
     */
    @Override
    public void saveListOfIntegerSetting(String key, List<Integer> value) {
        String intString;

        JSONArray jsonArr = new JSONArray();
        for (int i : value) {
            jsonArr.put(i);
        }

        intString = jsonArr.toString();
        editor.putString(key, intString);
    }

    @Override
    public void saveListOfStringSetting(String key, List<String> value) {
        String JSONString = new Gson().toJson(value);
        editor.putString(key, JSONString);
    }

    @Override
    public void saveMenuItems(String key, List<cMenuModel> value) {
        String menuJSONString = new Gson().toJson(value);
        editor.putString(CPreferenceConstant.KEY_MENU_ITEM_BITS, menuJSONString);
    }

    // LOAD PREFERENCES

    /**
     * load an active organization identification.
     *
     * @return identification
     */
    @Override
    public String loadActiveOrganizationID() {
        return preferences.getString(CPreferenceConstant.KEY_ORG_ID, null);
    }

    @Override
    public List<String> loadMyOrganizations() {
        String jsonString;
        jsonString = preferences.getString(CPreferenceConstant.KEY_USER_ORGANIZATIONS,null);
        Type type = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(jsonString, type);
    }

    /**
     * load a bit of an active workspace of an active organization.
     *
     * @return bit
     */
    @Override
    public int loadActiveWorkspaceBIT() {
        return preferences.getInt(CPreferenceConstant.KEY_WORKSPACE_OWNER_BIT, -1);
    }

    /**
     * load bits of workspaces of an active organization.
     *
     * @return bits
     */
    @Override
    public List<Integer> loadSecondaryWorkspaces() {
        String intString;
        intString = preferences.getString(CPreferenceConstant.KEY_SECONDARY_WORKSPACES, "");
        try {
            JSONArray jsonArr = new JSONArray(new JSONTokener(intString));
            List<Integer> result = new ArrayList<>();

            for (int i = 0; i < jsonArr.length(); i++) {
                result.add(jsonArr.getInt(i));
            }
            return result;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public String loadCurrentWorkspace() {
        return preferences.getString(CPreferenceConstant.KEY_WORKSPACE_ID, null);
    }

    @Override
    public int loadWorkspaceServerID() {
        return 0;
    }

    /**
     * load identification number of an active user.
     *
     * @return identification.
     */
    @Override
    public String loadUserID() {
        return preferences.getString(CPreferenceConstant.KEY_USER_ID, null);
    }

    @Override
    public int loadWorkspaceMembershipBITS() {
        return preferences.getInt(CPreferenceConstant.KEY_USER_WORKSPACE_MEMBERSHIP_BITS,-1);
    }

    /**
     * load a bit of all modules selected.
     *
     * @return bit
     */
    @Override
    public int loadModuleBITS() {
        return preferences.getInt(CPreferenceConstant.KEY_MODULE_BITS, -1);
    }

    /**
     * load a bit of all entities under a module.
     *
     * @param moduleKey module identification.
     * @return bit
     */
    @Override
    public int loadEntityBITS(int moduleKey) {
        return preferences.getInt(CPreferenceConstant.KEY_MODULE_ENTITY_BITS + "-" +
                moduleKey, -1);
    }

    @Override
    public int loadActionPermissionBITS(int moduleKey, int entityKey, String actionKey) {
        return 0;
    }

    @Override
    public int loadEntityPermissionBITS(int moduleKey, int entityKey) {
        return preferences.getInt(CPreferenceConstant.KEY_ENTITY_OPERATION_BITS + "-" +
                moduleKey + "-" + entityKey, -1);
    }

    @Override
    public List<Integer> loadOperationStatuses(int moduleKey, int entityKey, int operationKey) {
        String intString = preferences.getString(CPreferenceConstant.KEY_OPERATION_STATUS_BITS + "-" +
                moduleKey + "-" + entityKey + "-" + operationKey, "");
        try {
            JSONArray jsonArr = new JSONArray(new JSONTokener(intString));
            List<Integer> result = new ArrayList<>();

            for (int i = 0; i < jsonArr.length(); i++) {
                result.add(jsonArr.getInt(i));
            }
            return result;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public int loadUnixPermissionBITS(int moduleKey, int entityKey) {
        return preferences.getInt(CPreferenceConstant.KEY_UNIX_PERM_BITS + "-" +
                moduleKey + "-" + entityKey, -1);
    }

    @Override
    public List<cMenuModel> loadMenuItems() {
        String jsonString = preferences.getString(CPreferenceConstant.KEY_MENU_ITEM_BITS,null);
        Type type = new TypeToken<List<cMenuModel>>() {}.getType();
        return new Gson().fromJson(jsonString, type);
    }

    @Override
    public void deleteCurrentWorkspace() {
        removeSetting(CPreferenceConstant.KEY_WORKSPACE_ID);
        commitSettings();
    }

    @Override
    public void saveCurrentWorkspace(String compositeServerID) {
        saveStringSetting(
                CPreferenceConstant.KEY_WORKSPACE_ID, compositeServerID);
        commitSettings();
    }
}