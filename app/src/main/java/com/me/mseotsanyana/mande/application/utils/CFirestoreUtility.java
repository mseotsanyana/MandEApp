package com.me.mseotsanyana.mande.application.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.me.mseotsanyana.mande.domain.entities.models.session.cEntityModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cMenuModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cPrivilegeModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cPlanModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cTransitionModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.domain.entities.models.utils.CCommonAttributeModel;
import com.me.mseotsanyana.mande.application.repository.preference.ISessionManager;
import com.me.mseotsanyana.mande.application.structures.CPreferenceConstant;
import com.me.mseotsanyana.mande.OLD.cConstant;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class cDatabaseUtils {
    private static final SimpleDateFormat sdf = cConstant.FORMAT_DATE;

    // Private constructor to prevent instantiation
    private cDatabaseUtils() {
        throw new UnsupportedOperationException();
    }

    // public static methods here
    public static @NonNull
    String loadJSONFromAsset(String jsonMenu, @NonNull AssetManager assetManager) {
        String json;
        try {
            //AssetManager assetManager = getContext().getAssets();
            InputStream is = assetManager.open(jsonMenu);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            ex.printStackTrace();
            return ex.toString();
        }

        //Log.e(TAG, "Json response " + json);
        return json;
    }

    /**
     * read administrator's default privilege from json file - during the creation of an organization
     *
     * @param context    context
     * @param properties common properties model
     * @return role model
     */
    @NonNull
    public static cPrivilegeModel createDefaultPrivilegeModel(
            @NonNull Context context, @NonNull CCommonAttributeModel properties) {

        cPrivilegeModel privilegeModel = new cPrivilegeModel();

        // 1. create admin privilege
        String privilege = "jsons/sys_admin_privilege.json";
        String privilegeJSONString = cDatabaseUtils.loadJSONFromAsset(privilege, context.getAssets());

        try {
            JSONObject jsonObjectPrivilege = new JSONObject(privilegeJSONString);
            JSONObject jsonPrivilege = jsonObjectPrivilege.getJSONObject("privilege");

            privilegeModel.setName(jsonPrivilege.getString("name"));
            privilegeModel.setDescription(jsonPrivilege.getString("description"));

            // update default dates
            Date currentDate = new Date();
            privilegeModel.setCreatedDate(currentDate);
            privilegeModel.setModifiedDate(currentDate);

            // update privilege's common columns
            privilegeModel.setUserOwnerID(properties.getOwnerID());
            privilegeModel.setOrganizationOwnerID(properties.getOrgOwnerID());
            privilegeModel.setWorkspaceOwnerBIT(properties.getWorkspaceOwnerBIT());
            privilegeModel.setUnixpermBITS(properties.getUnixpermBITS());
            privilegeModel.setStatusBIT(properties.getStatusBIT());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 1.1 create and add menu items to the privileges
        String json_menuitems = "jsons/sys_admin_privilege_menuitems.json";
        String menuitemsJSONString = cDatabaseUtils.loadJSONFromAsset(json_menuitems,
                context.getAssets());
        try {
            /* processing menu items */
            JSONObject jsonObjectMenuitems = new JSONObject(menuitemsJSONString);
            JSONArray jsonArrayMenuItem = jsonObjectMenuitems.getJSONArray("menuitems");
            Map<String, List<Integer>> menuitems = new HashMap<>();
            for (int i = 0; i < jsonArrayMenuItem.length(); i++) {
                JSONObject jsonObject = jsonArrayMenuItem.getJSONObject(i);
                JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");

                List<Integer> submenuitems = new ArrayList<>();
                for (int j = 0; j < jsonArraySubMenu.length(); j++) {
                    JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
                    submenuitems.add(jsonObjectSubItem.getInt("sub_id"));
                }

                // set the main menu item
                menuitems.put(jsonObject.getString("id"), submenuitems);
            }

            privilegeModel.setMenuitems(menuitems);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 1.2 create and add modules of the admin privilege
        String json_modules = "jsons/sys_admin_privilege_modules.json";
        String modulesJSONString;
        modulesJSONString = cDatabaseUtils.loadJSONFromAsset(json_modules, context.getAssets());

        try {
            JSONObject privilegeModules = new JSONObject(modulesJSONString);
            JSONArray privilege_modules = privilegeModules.getJSONArray("modules");

            Map<String, List<cEntityModel>> modules = new HashMap<>();
            for (int i = 0; i < privilege_modules.length(); i++) {
                JSONObject module = privilege_modules.getJSONObject(i);
                String module_id = module.getString("module_id");

                JSONArray entities = module.getJSONArray("entities");

                List<cEntityModel> entityModels = new ArrayList<>();

                for (int j = 0; j < entities.length(); j++) {
                    JSONObject entity = entities.getJSONObject(j);

                    JSONArray actionJSONArray = entity.getJSONArray("actions");
                    JSONArray transitionJSONArray = entity.getJSONArray("transitions");
                    JSONArray unixpermJSONArray = entity.getJSONArray("unixpermissions");

                    List<Integer> actions = new ArrayList<>();
                    for (int k = 0; k < actionJSONArray.length(); k++) {
                        int action = actionJSONArray.getInt(k);
                        actions.add(action);
                    }

                    List<Integer> permissions = new ArrayList<>();
                    for (int l = 0; l < unixpermJSONArray.length(); l++) {
                        int permission = unixpermJSONArray.getInt(l);
                        permissions.add(permission);
                    }

                    List<cTransitionModel> transitions = new ArrayList<>();
                    for (int m = 0; m < transitionJSONArray.length(); m++) {
                        JSONObject transitionJSONObject = transitionJSONArray.getJSONObject(m);
                        int source_id = transitionJSONObject.getInt("source_id");
                        int target_id = transitionJSONObject.getInt("target_id");
                        int event_id = transitionJSONObject.getInt("event_id");
                        int action_id = transitionJSONObject.getInt("action_id");

                        // create transition identification and its details
                        cTransitionModel transitionModel = new cTransitionModel();
                        transitionModel.setEventServerID(String.valueOf(event_id));
                        transitionModel.setActionServerID(String.valueOf(action_id));
                        transitionModel.setSourceServerID(String.valueOf(source_id));
                        transitionModel.setTargetServerID(String.valueOf(target_id));

                        transitions.add(transitionModel);
                    }

                    // an entity with actions and transitions
                    int entity_id = entity.getInt("entity_id");
                    String entity_name = entity.getString("entity_name");
                    String entity_description = entity.getString("entity_description");

                    cEntityModel entityModel = new cEntityModel();
                    entityModel.setEntityServerID(String.valueOf(entity_id));
                    entityModel.setName(entity_name);
                    entityModel.setDescription(entity_description);
                    entityModel.setActions(actions);
                    entityModel.setPermissions(permissions);
                    entityModel.setTransitions(transitions);

                    entityModels.add(entityModel);
                }

                // add modules of the privilege
                modules.put(module_id, entityModels);
            }

            // assign or grant permissions
            privilegeModel.setModules(modules);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return privilegeModel;
    }

    /**
     * build workspace privileges tree menu from json files and check items that are in the
     * database for corresponding fields.
     *
     * @param context           context
     * @param workspaceModelMap map of workspace privileges containing data from database.
     * @return tree model
     */
    @NonNull
    public static List<cTreeModel> buildWorkspacePrivileges(
            @NonNull Context context, @NonNull Map<String, cPrivilegeModel> workspaceModelMap) {
        List<cTreeModel> moduleTreeModels = new ArrayList<>();

//        /* menu item options */
//        String menuitems = "jsons/sys_admin_privilege_menuitems.json";
//        String menuitemsJSONString = cDatabaseUtils.loadJSONFromAsset(menuitems,
//                context.getAssets());
//
//        /* entity modules */
//        String modules = "jsons/sys_admin_privilege_modules.json";
//        String modulesSONString = cDatabaseUtils.loadJSONFromAsset(modules,
//                context.getAssets());
//
//        /* unix operations */
//        String unix_ops = "jsons/sys_unixperms.json";
//        String unixOpsJSONString = cDatabaseUtils.loadJSONFromAsset(unix_ops,
//                context.getAssets());
//
//        try {
//            /* LEVEL 0: add permission node as the root node */
//            int parentIndex = 0, childIndex = 0;
//            for (Map.Entry<String, cPrivilegeModel> entry : workspaceModelMap.entrySet()) {
//                moduleTreeModels.add(new cTreeModel(parentIndex, -1, 0,
//                        new cPrivilegeModel(entry.getValue())));
//                int parentPermissionIndex = childIndex;
//
//                /* MENU ITEM OPTIONS */
//                JSONObject jsonObjectMenuItems = new JSONObject(menuitemsJSONString);
//
//                String jsonObjectSection = jsonObjectMenuItems.getString("menuitem_options");
//                JSONArray jsonArrayMenuItems = jsonObjectMenuItems.getJSONArray("menuitems");
//
//                cSectionModel menuItemsSection = new cSectionModel();
//
//                // set the main menu item
//                menuItemsSection.setName(jsonObjectSection);
//
//                /* LEVEL 1: add child module node to the parent permission node */
//                childIndex = childIndex + 1;
//                moduleTreeModels.add(new cTreeModel(childIndex, parentPermissionIndex, 1,
//                        menuItemsSection));
//                int parentMenuItemIndex = childIndex;
//
//                List<String> main_menu_ids = getMainMenuIDs(entry.getValue());
//                for (int j = 0; j < jsonArrayMenuItems.length(); j++) {
//                    JSONObject jsonObjectItem = jsonArrayMenuItems.getJSONObject(j);
//
//                    cMenuModel menuModel = new cMenuModel();
//                    // set the main menu item
//                    menuModel.setMenuServerID(jsonObjectItem.getInt("id"));
//                    menuModel.setName(jsonObjectItem.getString("item"));
//                    menuModel.setDescription(jsonObjectItem.getString("description"));
//                    menuModel.setChecked(main_menu_ids.contains(jsonObjectItem.getString(
//                            "id")));
//
//                    /* LEVEL 2: add child item to the parent menu items section node */
//                    childIndex = childIndex + 1;
//                    moduleTreeModels.add(new cTreeModel(childIndex, parentMenuItemIndex, 2,
//                            menuModel));
//                    int parentItemIndex = childIndex;
//
//                    JSONArray jsonArraySubItem = jsonObjectItem.getJSONArray("sub_menu");
//                    List<String> sub_menu_items = getSubMenuIDs(entry.getValue(),
//                            String.valueOf(menuModel.getMenuServerID()));
//                    for (int k = 0; k < jsonArraySubItem.length(); k++) {
//
//                        JSONObject jsonObjectSubItem = jsonArraySubItem.getJSONObject(k);
//
//                        cMenuModel subMenuModel = new cMenuModel();
//                        // set sub menu item
//                        subMenuModel.setParentServerID(menuModel.getMenuServerID());
//                        subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_id"));
//                        subMenuModel.setName(jsonObjectSubItem.getString("sub_item"));
//                        subMenuModel.setDescription(jsonObjectSubItem.getString(
//                                "sub_description"));
//                        subMenuModel.setChecked(sub_menu_items.contains(jsonObjectSubItem.getString(
//                                "sub_id")));
//
//                        /* LEVEL 3: add child sub menu node to the parent main menu node */
//                        childIndex = childIndex + 1;
//                        moduleTreeModels.add(new cTreeModel(childIndex, parentItemIndex, 3,
//                                subMenuModel));
//                    }
//                }
//
//                /* ENTITY MODULES */
//                List<String> module_ids = getModuleIDs(entry.getValue());
//
//                JSONObject jsonObjectModules = new JSONObject(modulesSONString);
//                JSONArray jsonArrayModules = jsonObjectModules.getJSONArray("modules");
//                for (int i = 0; i < jsonArrayModules.length(); i++) {
//                    JSONObject objectModule = jsonArrayModules.getJSONObject(i);
//
//                    cSectionModel moduleSection = new cSectionModel();
//                    moduleSection.setModuleServerID(objectModule.getString("module_id"));
//                    moduleSection.setName(objectModule.getString("module_name"));
//                    moduleSection.setChecked(module_ids.contains(objectModule.getString(
//                            "module_id")));
//
//                    /* LEVEL 4: add child module node to the parent permission node */
//                    childIndex = childIndex + 1;
//                    moduleTreeModels.add(new cTreeModel(childIndex, parentPermissionIndex, 4,
//                            moduleSection));
//                    int parentModuleIndex = childIndex;
//
//                    /* entities */
//                    JSONArray jsonArrayEntities = objectModule.getJSONArray("entities");
//                    List<String> entity_ids = getEntityIDs(entry.getValue(),
//                            moduleSection.getModuleServerID());
//                    for (int j = 0; j < jsonArrayEntities.length(); j++) {
//                        JSONObject objectEntity = jsonArrayEntities.getJSONObject(j);
//
//                        cEntityModel entityModel = new cEntityModel();
//                        entityModel.setEntityServerID(objectEntity.getString("entity_id"));
//                        entityModel.setName(objectEntity.getString("entity_name"));
//                        entityModel.setDescription(objectEntity.getString("entity_description"));
//                        entityModel.setChecked(entity_ids.contains(objectEntity.getString(
//                                "entity_id")));
//                        //actions
//                        JSONArray jsonActions = objectEntity.getJSONArray("actions");
//                        List<Integer> actions = new ArrayList<>();
//                        for (int a = 0; a < jsonActions.length(); a++){
//                            actions.add(jsonActions.getInt(a));
//                        }
//                        entityModel.setActions(actions);
//                        // unixperms
//                        JSONArray jsonUnixperms = objectEntity.getJSONArray("unixpermissions");
//                        List<Integer> unixperms = new ArrayList<>();
//                        for (int u = 0; u < jsonUnixperms.length(); u++){
//                            unixperms.add(jsonUnixperms.getInt(u));
//                        }
//                        entityModel.setPermissions(unixperms);
//                        // transitions
//                        JSONArray jsonTransitions = objectEntity.getJSONArray("transitions");
//                        List<cTransitionModel> transitions = new ArrayList<>();
//                        for (int t = 0; t < jsonTransitions.length(); t++){
//                            JSONObject jsonObject = jsonTransitions.getJSONObject(t);
//
//                            cTransitionModel transitionModel = new cTransitionModel();
//                            transitionModel.setEventServerID(jsonObject.getString("event_id"));
//                            transitionModel.setActionServerID(jsonObject.getString("action_id"));
//                            transitionModel.setSourceServerID(jsonObject.getString("source_id"));
//                            transitionModel.setTargetServerID(jsonObject.getString("target_id"));
//
//                            transitions.add(transitionModel);
//                        }
//                        entityModel.setTransitions(transitions);
//
//                        /* LEVEL 5: add child entity node to the parent module node */
//                        childIndex = childIndex + 1;
//                        moduleTreeModels.add(new cTreeModel(childIndex, parentModuleIndex, 5,
//                                entityModel));
//                        int parentEntityIndex = childIndex;
//
//                        /* unix operations */
//                        List<cUnixOperationModel> collection = new ArrayList<>();
//                        cUnixOperationCollection unixOperationCollection;
//                        unixOperationCollection = new cUnixOperationCollection();
//
//                        JSONObject jsonObjectUnixOps = new JSONObject(unixOpsJSONString);
//                        JSONArray jsonArrayUnixOps = jsonObjectUnixOps.getJSONArray("unixperms");
//
//                        List<String> unix_op_ids = getUnixOperationIDs(entry.getValue(),
//                                moduleSection.getModuleServerID(), entityModel.getEntityServerID());
//
//                        for (int k = 0; k < jsonArrayUnixOps.length(); k++) {
//                            JSONObject objectUnixOps = jsonArrayUnixOps.getJSONObject(k);
//
//                            cUnixOperationModel unixOperationModel = new cUnixOperationModel();
//
//                            unixOperationModel.setOperationServerID(objectUnixOps.getString(
//                                    "id"));
//                            unixOperationModel.setName(objectUnixOps.getString(
//                                    "name"));
//                            unixOperationModel.setDescription(objectUnixOps.getString(
//                                    "description"));
//                            unixOperationModel.setChecked(unix_op_ids.contains(
//                                    objectUnixOps.getString("id")));
//
//                            collection.add(unixOperationModel);
//                        }
//
//                        unixOperationCollection.setUnixOperationModels(collection);
//
//                        // LEVEL 6: add child unix operation node to the parent entity node
//                        childIndex = childIndex + 1;
//                        moduleTreeModels.add(new cTreeModel(childIndex, parentEntityIndex,
//                                6, unixOperationCollection));
//
//                    }
//                }
//                parentIndex = childIndex + 1;
//                childIndex = parentIndex;
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        return moduleTreeModels;
    }

    @NonNull
    private static List<String> getMainMenuIDs(@NonNull cPrivilegeModel privilegeModel) {
        List<String> menu_ids = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : privilegeModel.getMenuitems().
                entrySet()) {
            menu_ids.add(entry.getKey());
        }
        return menu_ids;
    }

    @NonNull
    private static List<String> getSubMenuIDs(@NonNull cPrivilegeModel privilegeModel,
                                              String menuID) {
        List<String> sub_menu_ids = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : privilegeModel.getMenuitems().
                entrySet()) {
            if (entry.getKey().equals(menuID)) {
                for (Integer sub_menu : entry.getValue()) {
                    sub_menu_ids.add(String.valueOf(sub_menu));
                }
                break;
            }
        }
        return sub_menu_ids;
    }

    @NonNull
    private static List<String> getModuleIDs(@NonNull cPrivilegeModel privilegeModel) {
        Map<String, List<cEntityModel>> perm_modules = privilegeModel.getModules();
        return new ArrayList<>(perm_modules.keySet());
    }

    @NonNull
    private static List<String> getEntityIDs(@NonNull cPrivilegeModel privilegeModel,
                                             String moduleID) {
        List<String> entity_ids = new ArrayList<>();
        Map<String,List<cEntityModel>> modules = privilegeModel.getModules();
        for(Map.Entry<String, List<cEntityModel>> module: modules.entrySet()){
            String moduleKey = module.getKey();
            if (moduleKey.equals(moduleID)) {
                for (cEntityModel entityModel : module.getValue()) {
                    entity_ids.add(entityModel.getEntityServerID());
                }
            }
        }

        return entity_ids;
    }

    @NonNull
    private static List<String> getUnixOperationIDs(@NonNull cPrivilegeModel privilegeModel,
                                                    String moduleID, String entityID) {
        List<String> unix_ops_ids = new ArrayList<>();

        Map<String,List<cEntityModel>> modules = privilegeModel.getModules();
        for(Map.Entry<String, List<cEntityModel>> module: modules.entrySet()){
            String moduleKey = module.getKey();
            if (moduleKey.equals(moduleID)) {
                for (cEntityModel entityModel : module.getValue()) {
                    String entityKey = entityModel.getEntityServerID();
                    if (entityKey.equals(entityID)) {
                        for (Integer permissionID: entityModel.getPermissions()) {
                            unix_ops_ids.add(String.valueOf(permissionID));
                        }
                    }
                }
            }
        }

        return unix_ops_ids;
    }

    /**
     * read a default freemium plan of the new user or demotion to a lower plan due to non payment
     *
     * @param context context
     * @return plan model
     */
    @NonNull
    public static cPlanModel getDefaultPlanModel(@NonNull Context context) {
        cPlanModel planModel = new cPlanModel();
        // read json file
        String plans = "jsons/sys_plans.json";
        String planJSONString = cDatabaseUtils.loadJSONFromAsset(plans, context.getAssets());

        try {
            JSONObject jsonObjectPlan = new JSONObject(planJSONString);
            JSONArray jsonPlans = jsonObjectPlan.getJSONArray("plans");

            for (int i = 0; i < jsonPlans.length(); i++) {
                JSONObject jsonPlan = jsonPlans.getJSONObject(i);
                String default_plan = jsonPlan.getString("id");

                if (default_plan.equals("FREE_SUB")) {
                    planModel.setPlanServerID(jsonPlan.getString("id"));
                    planModel.setName(jsonPlan.getString("name"));
                    planModel.setDescription(jsonPlan.getString("description"));

                    planModel.setOrgLimit(jsonPlan.getInt("max_org_limit"));
                    planModel.setTeamLimit(jsonPlan.getInt("max_team_limit"));
                    planModel.setOrgUserLimit(jsonPlan.getInt("max_org_user_limit"));
                    planModel.setTeamUserLimit(jsonPlan.getInt("max_team_user_limit"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return planModel;
    }

    /**
     * read administrator's default workspace from json - during the creation of an organization
     *
     * @param context        context
     * @param organizationID organization identification
     * @param properties     common properties model
     * @return team model
     */
    @NonNull
    public static CWorkspaceModel getAdminWorkspaceModel(@NonNull Context context, String organizationID,
                                                         @NonNull CCommonAttributeModel properties) {
        CWorkspaceModel workspaceModel = new CWorkspaceModel();

        // read json file
        String team = "jsons/sys_admin_workspace.json";
        String teamJSONString = cDatabaseUtils.loadJSONFromAsset(team, context.getAssets());

        try {
            workspaceModel.setOrganizationServerID(organizationID);

            JSONObject jsonObjectTeam = new JSONObject(teamJSONString);
            JSONObject jsonTeam = jsonObjectTeam.getJSONObject("workspace");

            workspaceModel.setCompositeServerID(organizationID + "_" + jsonTeam.getString("id"));
            workspaceModel.setOrganizationServerID(organizationID);
            workspaceModel.setWorkspaceServerID(jsonTeam.getString("id"));

            workspaceModel.setName(jsonTeam.getString("name"));
            workspaceModel.setDescription(jsonTeam.getString("description"));

            // update default dates
            Date currentDate = new Date();
            workspaceModel.setCreatedDate(currentDate);
            workspaceModel.setModifiedDate(currentDate);

            // update team's common columns
            workspaceModel.setUserOwnerID(properties.getOwnerID());
            workspaceModel.setOrganizationOwnerID(properties.getOrgOwnerID());
            workspaceModel.setWorkspaceOwnerBIT(properties.getWorkspaceOwnerBIT());
            workspaceModel.setUnixpermBITS(properties.getUnixpermBITS());
            workspaceModel.setStatusBIT(properties.getStatusBIT());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return workspaceModel;
    }



//    /**
//     * read administrator's default privilege from json - during the creation of an organization
//     *
//     * @param context    context
//     * @param properties common properties model
//     * @return role model
//     */
//    @NonNull
//    public static cPrivilegeModel getAdminPrivilegeModel(@NonNull Context context,
//                                                         @NonNull cCommonPropertiesModel properties) {
//        cPrivilegeModel privilegeModel = new cPrivilegeModel();
//
//        // 1. create admin privilege
//        String privilege = "jsons/sys_admin_privilege.json";
//        String privilegeJSONString = cDatabaseUtils.loadJSONFromAsset(privilege, context.getAssets());
//
//        try {
//            JSONObject jsonObjectPrivilege = new JSONObject(privilegeJSONString);
//            JSONObject jsonPrivilege = jsonObjectPrivilege.getJSONObject("privilege");
//
//            privilegeModel.setName(jsonPrivilege.getString("name"));
//            privilegeModel.setDescription(jsonPrivilege.getString("description"));
//
//            // update default dates
//            Date currentDate = new Date();
//            privilegeModel.setCreatedDate(currentDate);
//            privilegeModel.setModifiedDate(currentDate);
//
//            // update privilege's common columns
//            privilegeModel.setUserOwnerID(properties.getOwnerID());
//            privilegeModel.setOrganizationOwnerID(properties.getOrgOwnerID());
//            privilegeModel.setWorkspaceOwnerBIT(properties.getWorkspaceOwnerBIT());
//            privilegeModel.setUnixpermBITS(properties.getUnixpermBITS());
//            privilegeModel.setStatusBIT(properties.getStatusBIT());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // 1.1 create and add menu items to the privileges
//        String json_menuitems = "jsons/sys_admin_privilege_menuitems.json";
//        String menuitemsJSONString = cDatabaseUtils.loadJSONFromAsset(json_menuitems,
//                context.getAssets());
//        try {
//            /* processing menu items */
//            JSONObject jsonObjectMenuitems = new JSONObject(menuitemsJSONString);
//            JSONArray jsonArrayMenuItem = jsonObjectMenuitems.getJSONArray("menuitems");
//            Map<String, List<Integer>> menuitems = new HashMap<>();
//            for (int i = 0; i < jsonArrayMenuItem.length(); i++) {
//                JSONObject jsonObject = jsonArrayMenuItem.getJSONObject(i);
//                JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");
//
//                List<Integer> submenuitems = new ArrayList<>();
//                for (int j = 0; j < jsonArraySubMenu.length(); j++) {
//                    JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
//                    submenuitems.add(jsonObjectSubItem.getInt("sub_id"));
//                }
//
//                // set the main menu item
//                menuitems.put(jsonObject.getString("id"), submenuitems);
//            }
//
//            privilegeModel.setMenuitems(menuitems);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // 1.2 create and add modules of the admin privilege
//        String json_modules = "jsons/sys_admin_privilege_modules.json";
//        String modulesJSONString;
//        modulesJSONString = cDatabaseUtils.loadJSONFromAsset(json_modules, context.getAssets());
//
//        // module identification with entity objects
//        Map<String, Object> modules = new HashMap<>();
//        try {
//            JSONObject privilegeModules = new JSONObject(modulesJSONString);
//            JSONArray privilege_modules = privilegeModules.getJSONArray("modules");
//
//            for (int i = 0; i < privilege_modules.length(); i++) {
//                JSONObject module = privilege_modules.getJSONObject(i);
//                String module_id = module.getString("module_id");
//
//                JSONArray entities = module.getJSONArray("entities");
//                Map<String, Object> entitiesMap = new HashMap<>();
//
//                for (int j = 0; j < entities.length(); j++) {
//                    JSONObject entity = entities.getJSONObject(j);
//                    int entity_id = entity.getInt("entity_id");
//
//                    JSONArray actionJSONArray = entity.getJSONArray("actions");
//                    JSONArray transitionJSONArray = entity.getJSONArray("transitions");
//                    JSONArray unixpermJSONArray = entity.getJSONArray("unixpermissions");
//
//                    List<Integer> actions = new ArrayList<>();
//                    List<Integer> permissions = new ArrayList<>();
//                    List<Object> transitions = new ArrayList<>();
//
//                    for (int k = 0; k < actionJSONArray.length(); k++) {
//                        int action = actionJSONArray.getInt(k);
//                        actions.add(action);
//                    }
//
//                    for (int l = 0; l < unixpermJSONArray.length(); l++) {
//                        int permission = unixpermJSONArray.getInt(l);
//                        permissions.add(permission);
//                    }
//
//                    for (int m = 0; m < transitionJSONArray.length(); m++) {
//                        JSONObject transitionJSONObject = transitionJSONArray.getJSONObject(m);
//                        int source_id = transitionJSONObject.getInt("source_id");
//                        int target_id = transitionJSONObject.getInt("target_id");
//                        int event_id = transitionJSONObject.getInt("event_id");
//                        int action_id = transitionJSONObject.getInt("action_id");
//
//                        // create transition identification and its details
//                        Map<String, Object> transitionMap = new HashMap<>();
//                        transitionMap.put("sourceServerID", source_id);
//                        transitionMap.put("targetServerID", target_id);
//                        transitionMap.put("eventServerID", event_id);
//                        transitionMap.put("actionServerID", action_id);
//
//                        transitions.add(transitionMap);
//                    }
//
//                    // an entity with actions and transitions
//                    Map<String, Object> entityMap = new HashMap<>();
//                    entityMap.put("actions", actions);
//                    entityMap.put("permissions", permissions);
//                    entityMap.put("transitions", transitions);
//                    entitiesMap.put(String.valueOf(entity_id), entityMap);
//                }
//
//                Map<String, Object> moduleMap = new HashMap<>();
//                moduleMap.put("entities", entitiesMap);
//
//                // add modules of the privilege
//                modules.put(module_id, moduleMap);
//            }
//
//            // assign or grant permissions
//            privilegeModel.setModules(modules);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return privilegeModel;
//    }

    /**
     * read default menu if the role based menu is not available.
     *
     * @param context context
     * @return list of default menu
     */
    @NonNull
    public static List<cMenuModel> getDefaultMenuModels(@NonNull Context context) {
        List<cMenuModel> menuModels = new ArrayList<>();
        String menu = "jsons/sys_default_menu_items.json";
        String menuJSONString = cDatabaseUtils.loadJSONFromAsset(menu, context.getAssets());

        try {
            JSONObject jsonObjectMenu = new JSONObject(menuJSONString);
            JSONArray jsonArrayMenu = jsonObjectMenu.getJSONArray("menu");
            for (int i = 0; i < jsonArrayMenu.length(); i++) {
                ArrayList<cMenuModel> subMenuModels = new ArrayList<>();
                JSONObject jsonObject = jsonArrayMenu.getJSONObject(i);
                JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");

                for (int j = 0; j < jsonArraySubMenu.length(); j++) {
                    cMenuModel subMenuModel = new cMenuModel();
                    JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
                    subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_id"));
                    subMenuModel.setName(jsonObjectSubItem.getString("sub_item"));
                    subMenuModels.add(subMenuModel);
                }

                cMenuModel menuModel = new cMenuModel();
                // set the main menu item
                menuModel.setMenuServerID(jsonObject.getInt("id"));
                menuModel.setName(jsonObject.getString("item"));

                // sub menu items to the main menu item
                menuModel.setSubmenu(subMenuModels);

                menuModels.add(menuModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menuModels;
    }

    /**
     * build the main menu with its corresponding sub menu model based on the permissions
     * allocated to the user through team membership and role assigned to the team
     *
     * @param context  context
     * @param menu_map menu map with main and sub menu items
     * @return list of menu models
     */
    @NonNull
    public static List<cMenuModel> getMenuModels(@NonNull Context context,
                                                 @NonNull Map<String, List<Integer>> menu_map) {
        List<cMenuModel> menuModels = new ArrayList<>();

        String menu = "jsons/sys_admin_privilege_menuitems.json";
        String menuJSONString = cDatabaseUtils.loadJSONFromAsset(menu, context.getAssets());

        try {

            JSONObject jsonObjectMenu = new JSONObject(menuJSONString);
            JSONArray jsonArrayMenu = jsonObjectMenu.getJSONArray("menuitems");

            Set<String> menu_set = menu_map.keySet();

            for (int i = 0; i < jsonArrayMenu.length(); i++) {

                ArrayList<cMenuModel> subMenuModels = new ArrayList<>();
                JSONObject jsonObject = jsonArrayMenu.getJSONObject(i);

                if (menu_set.contains(jsonObject.getString("id"))) {

                    cMenuModel menuModel = new cMenuModel();
                    // set the main menu item
                    menuModel.setMenuServerID(jsonObject.getInt("id"));
                    menuModel.setName(jsonObject.getString("item"));

                    JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");
                    List<Integer> sub_menu_items = menu_map.get(jsonObject.getString("id"));
                    for (int j = 0; j < jsonArraySubMenu.length(); j++) {

                        JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
                        assert sub_menu_items != null;
                        if (sub_menu_items.contains(jsonObjectSubItem.getInt("sub_id"))) {
                            cMenuModel subMenuModel = new cMenuModel();
                            subMenuModel.setParentServerID(menuModel.getMenuServerID());
                            subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_id"));
                            subMenuModel.setName(jsonObjectSubItem.getString("sub_item"));
                            subMenuModels.add(subMenuModel);
                        }
                    }

                    // sub menu items to the main menu item
                    menuModel.setSubmenu(subMenuModels);
                    // add menu items in the list
                    menuModels.add(menuModel);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menuModels;
    }

    /**
     * read default common properties of every entity
     *
     * @param context context
     * @return common properties model
     */
    @NonNull
    public static CCommonAttributeModel getCommonModel(@NonNull Context context) {
        CCommonAttributeModel commonPropertiesModel = new CCommonAttributeModel();

        // read json file
        String cproperties = "jsons/sys_common_properties.json";
        String cpropertiesJSONString = cDatabaseUtils.loadJSONFromAsset(cproperties,
                context.getAssets());

        try {
            //assert columns JSONString != null;
            JSONObject commonJsonObject = new JSONObject(cpropertiesJSONString);
            JSONObject commonJson = commonJsonObject.getJSONObject("cproperties");

            commonPropertiesModel.setWorkspaceOwnerBIT(commonJson.getInt("cworkspace"));
            commonPropertiesModel.setStatusBIT(commonJson.getInt("cstatus"));
            commonPropertiesModel.setUnixpermBITS(commonJson.getInt("cunixperms"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return commonPropertiesModel;
    }

    /**
     * An action that calls this function means that it is implemented by an entity represented
     * by a collection reference at the provided list of states (statuses).
     *
     * @param collectionRef        collection reference that represents an entity.
     * @param organizationServerID currently active organization.
     * @param myOrganizations      organization identifications that owns a resource in the collection.
     * @param statusBITS           status bits represent states in which the action is implemented.
     * @return list of snapshots of the query.
     */
    @NonNull
    public static Query filterResourcesByStatus(
            CollectionReference collectionRef, String organizationServerID,
            @NonNull List<String> myOrganizations, @NonNull List<Integer> statusBITS) {

        // add the active organization in the list of organizations if it's not a member
        if (!myOrganizations.contains(organizationServerID))
            myOrganizations.add(organizationServerID);

        // status query for sate and stateless entities
        Query statusQuery;
        if (statusBITS.size() == 1 && statusBITS.contains(0)) {
            statusQuery = collectionRef
                    .whereIn("organizationOwnerID", myOrganizations);
        } else {
            statusQuery = collectionRef
                    .whereIn("organizationServerID", myOrganizations)
                    .whereIn("statusBIT", statusBITS);
        }
        return statusQuery;
    }

    /**
     * checks whether the access is allowed or not based on the permission assigned on a resource
     *
     * @param pref                logged in user settings.
     * @param organizationOwnerID organization that owns a resource.
     * @param workspaceOwnerBIT   workspace which owns a resource.
     * @param userOwnerID         user who owns a resource.
     * @param actionPermBITS      action permissions computed from loaded settings.
     * @return boolean
     */
    public static boolean isReadPermitted(@NonNull ISessionManager pref,
                                          String organizationOwnerID, int workspaceOwnerBIT,
                                          String userOwnerID, int actionPermBITS) {

        boolean org_admin, wks_admin, cloud, village, house, room, own;

        /* 1. Organization Admin Role Permissions
        member of the 'Administrator' workspace is always allowed to do everything */
        org_admin = ((pref.loadWorkspaceMembershipBITS() & CPreferenceConstant.ADMIN_WORKSPACE) != 0);

        /* 2. Workspace Admin Role Permissions
        member who owns of an active workspace is allowed to do everything in the workspace */
        wks_admin = pref.loadWorkspaceServerID() == workspaceOwnerBIT;

        /* 3. Cloud Role Permissions FIXME
        member is allowed to read resources in active organization and his own organization */
        cloud = (pref.loadMyOrganizations().contains(pref.loadActiveOrganizationID()) &&
                pref.loadUserID().equals(userOwnerID) &&
                ((actionPermBITS & CPreferenceConstant.CLOUD_READ) != 0));

        /* 4. Village Role Permissions
        a user is allowed to read resources if he is (1) a member of an active organization
        and (2) the resources are owned by the active organization */
        village = (pref.loadMyOrganizations().contains(pref.loadActiveOrganizationID()) &&
                pref.loadActiveOrganizationID().equals(organizationOwnerID) &&
                ((actionPermBITS & CPreferenceConstant.VILLAGE_READ) != 0));

        /* 5. House Role Permissions
        a user is allowed to read resources active organization and its workspaces if he is a
        (1) member of the active organization and (2) the member of workspace that owns
        the resources */
        house = (pref.loadMyOrganizations().contains(pref.loadActiveOrganizationID()) &&
                ((pref.loadWorkspaceMembershipBITS() & workspaceOwnerBIT) != 0) &&
                (actionPermBITS & CPreferenceConstant.HOUSE_READ) != 0);

        /* 6. Room Role Permissions
        a user is allowed to read resources of an active organization and an active workspace
        if he is (1) member of an active organization, (2) member of active workspace and
        (3) the active workspace owns the resources */
        room = (pref.loadMyOrganizations().contains(pref.loadActiveOrganizationID()) &&
                ((pref.loadWorkspaceMembershipBITS() & pref.loadActiveWorkspaceBIT() &
                        workspaceOwnerBIT) != 0) && (actionPermBITS & CPreferenceConstant.ROOM_READ) != 0);

        /* 7. Own Permissions
        a user is allowed to read resources if (s)he is (1) member of an active organization,
        (2) member of an active workspace and (3) (s)he own the resources */
        own = (pref.loadMyOrganizations().contains(pref.loadActiveOrganizationID()) &&
                ((pref.loadWorkspaceMembershipBITS() & pref.loadActiveWorkspaceBIT()) != 0) &&
                pref.loadUserID().equals(userOwnerID) &&
                (actionPermBITS & CPreferenceConstant.OWNER_READ) != 0);

        return (org_admin || wks_admin || cloud || village || house || room || own);
    }

//    /**
//     * load a default administration module from json file into the database - during the
//     * creation of an organization
//     *
//     * @param context context
//     * @return permission model
//     */
//    public static cModuleModel createAdminModules(Context context) {
//
//        cPermissionModel permissionModel = null;
//        cModuleModel moduleModel = null;
//
//
//        String perms = "jsons_old_files/admin_perms.json";
//        String permsJSONString = cDatabaseUtils.loadJSONFromAsset(perms, context.getAssets());
//
//        try {
//            JSONObject jsonObjectPerms = new JSONObject(permsJSONString);
//
//            /* processing entity and unix permissions*/
//
//            JSONArray jsonArrayModule = jsonObjectPerms.getJSONArray("entitymodules");
//            Map<String, List<cEntityModel>> entitymodules = new HashMap<>();
//
//            for (int i = 0; i < jsonArrayModule.length(); i++) {
//                JSONObject jsonObjectModules = jsonArrayModule.getJSONObject(i);
//                String moduleID = jsonObjectModules.getString("module_id");
//                JSONArray jsonArrayEntity = jsonObjectModules.getJSONArray("entities");
//
//                List<cEntityModel> entityPermModels = new ArrayList<>();
//
//                for (int j = 0; j < jsonArrayEntity.length(); j++) {
//                    JSONObject jsonObjectEntity = jsonArrayEntity.getJSONObject(j);
//                    String entityID = jsonObjectEntity.getString("entity_id");
//
//                    // operation with list of statuses
//                    JSONArray jsonArrayOpStatus = jsonObjectEntity.getJSONArray("operations");
//                    Map<String, List<Integer>> entityperms = new HashMap<>();
//                    for (int k = 0; k < jsonArrayOpStatus.length(); k++) {
//                        JSONObject jsonObjectOps = jsonArrayOpStatus.getJSONObject(k);
//                        String operationID = jsonObjectOps.getString("op_id");
//
//                        JSONArray jsonArrayStatus = jsonObjectOps.getJSONArray("status_ids");
//                        List<Integer> statuses = new ArrayList<>();
//                        for (int l = 0; l < jsonArrayStatus.length(); l++) {
//                            int jsonObjectStatus = jsonArrayStatus.getInt(l);
//                            statuses.add(jsonObjectStatus);
//                        }
//
//                        entityperms.put(operationID, statuses);
//                    }
//
//                    // unix operations
//                    JSONArray jsonArrayUnixPerm = jsonObjectEntity.getJSONArray("unixoperations");
//                    List<Integer> unixperms = new ArrayList<>();
//                    for (int k = 0; k < jsonArrayUnixPerm.length(); k++) {
//                        JSONObject jsonObjectUnixOps = jsonArrayUnixPerm.getJSONObject(k);
//                        String unixPermID = jsonObjectUnixOps.getString("unix_op_id");
//                        unixperms.add(Integer.parseInt(unixPermID));
//                    }
//
//                    // create entity model
//                    cEntityModel entityPermModel = new cEntityModel(entityID, entityperms,
//                            unixperms);
//                    entityPermModels.add(entityPermModel);
//                }
//
//                entitymodules.put(moduleID, entityPermModels);
//            }
//
//            /* processing menu items */
//
//            JSONArray jsonArrayMenuItem = jsonObjectPerms.getJSONArray("menuitems");
//            Map<String, List<Integer>> menuitems = new HashMap<>();
//            for (int i = 0; i < jsonArrayMenuItem.length(); i++) {
//                JSONObject jsonObject = jsonArrayMenuItem.getJSONObject(i);
//                JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");
//
//                List<Integer> submenuitems = new ArrayList<>();
//                for (int j = 0; j < jsonArraySubMenu.length(); j++) {
//                    JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
//                    submenuitems.add(jsonObjectSubItem.getInt("sub_id"));
//                }
//
//                // set the main menu item
//                menuitems.put(jsonObject.getString("id"), submenuitems);
//            }
//
//            permissionModel = new cPermissionModel(entitymodules, menuitems);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return moduleModel;
//    }


//    /**
//     * load a default administration menu from json file into the database - during the
//     * creation of an organization
//     *
//     * @param context context
//     * @return permission model
//     */
//    public static cPermissionModel createAdminPermissions(Context context) {
//
//        cPermissionModel permissionModel = null;
//
//        String perms = "jsons_old_files/admin_perms.json";
//        String permsJSONString = cDatabaseUtils.loadJSONFromAsset(perms, context.getAssets());
//
//        try {
//            JSONObject jsonObjectPerms = new JSONObject(permsJSONString);
//
//            /* processing entity and unix permissions*/
//
//            JSONArray jsonArrayModule = jsonObjectPerms.getJSONArray("entitymodules");
//            Map<String, List<cEntityModel>> entitymodules = new HashMap<>();
//
//            for (int i = 0; i < jsonArrayModule.length(); i++) {
//                JSONObject jsonObjectModules = jsonArrayModule.getJSONObject(i);
//                String moduleID = jsonObjectModules.getString("module_id");
//                JSONArray jsonArrayEntity = jsonObjectModules.getJSONArray("entities");
//
//                List<cEntityModel> entityPermModels = new ArrayList<>();
//
//                for (int j = 0; j < jsonArrayEntity.length(); j++) {
//                    JSONObject jsonObjectEntity = jsonArrayEntity.getJSONObject(j);
//                    String entityID = jsonObjectEntity.getString("entity_id");
//
//                    // operation with list of statuses
//                    JSONArray jsonArrayOpStatus = jsonObjectEntity.getJSONArray("operations");
//                    Map<String, List<Integer>> entityperms = new HashMap<>();
//                    for (int k = 0; k < jsonArrayOpStatus.length(); k++) {
//                        JSONObject jsonObjectOps = jsonArrayOpStatus.getJSONObject(k);
//                        String operationID = jsonObjectOps.getString("op_id");
//
//                        JSONArray jsonArrayStatus = jsonObjectOps.getJSONArray("status_ids");
//                        List<Integer> statuses = new ArrayList<>();
//                        for (int l = 0; l < jsonArrayStatus.length(); l++) {
//                            int jsonObjectStatus = jsonArrayStatus.getInt(l);
//                            statuses.add(jsonObjectStatus);
//                        }
//
//                        entityperms.put(operationID, statuses);
//                    }
//
//                    // unix operations
//                    JSONArray jsonArrayUnixPerm = jsonObjectEntity.getJSONArray("unixoperations");
//                    List<Integer> unixperms = new ArrayList<>();
//                    for (int k = 0; k < jsonArrayUnixPerm.length(); k++) {
//                        JSONObject jsonObjectUnixOps = jsonArrayUnixPerm.getJSONObject(k);
//                        String unixPermID = jsonObjectUnixOps.getString("unix_op_id");
//                        unixperms.add(Integer.parseInt(unixPermID));
//                    }
//
//                    // create entity model
//                    cEntityModel entityPermModel = new cEntityModel(entityID, entityperms,
//                            unixperms);
//                    entityPermModels.add(entityPermModel);
//                }
//
//                entitymodules.put(moduleID, entityPermModels);
//            }
//
//            /* processing menu items */
//
//            JSONArray jsonArrayMenuItem = jsonObjectPerms.getJSONArray("menuitems");
//            Map<String, List<Integer>> menuitems = new HashMap<>();
//            for (int i = 0; i < jsonArrayMenuItem.length(); i++) {
//                JSONObject jsonObject = jsonArrayMenuItem.getJSONObject(i);
//                JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");
//
//                List<Integer> submenuitems = new ArrayList<>();
//                for (int j = 0; j < jsonArraySubMenu.length(); j++) {
//                    JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
//                    submenuitems.add(jsonObjectSubItem.getInt("sub_id"));
//                }
//
//                // set the main menu item
//                menuitems.put(jsonObject.getString("id"), submenuitems);
//            }
//
//            permissionModel = new cPermissionModel(entitymodules, menuitems);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return permissionModel;
//    }

//    /**
//     * read default common properties of every entity
//     *
//     * @param context context
//     * @return common properties model
//     */
//    public static cCommonPropertiesModel getCommonModel(Context context) {
//        cCommonPropertiesModel commonPropertiesModel = new cCommonPropertiesModel();
//
//        // read json file
//        String cproperties = "jsons/admin_common_properties.json";
//        String ccolumnsJSONString = cDatabaseUtils.loadJSONFromAsset(cproperties,
//                context.getAssets());
//
//        try {
//            //assert columns JSONString != null;
//            JSONObject jsonObjectColumn = new JSONObject(ccolumnsJSONString);
//            JSONObject jsonColumn = jsonObjectColumn.getJSONObject("cproperties");
//
//            commonPropertiesModel.setWorkspaceOwnerBIT(jsonColumn.getInt("cworkspace"));
//            commonPropertiesModel.setStatusBIT(jsonColumn.getInt("cstatus"));
//            JSONArray jsonArrayPerm = jsonColumn.getJSONArray("cunixperms");
//
//            List<Integer> unixperm = new ArrayList<>();
//            for (int j = 0; j < jsonArrayPerm.length(); j++) {
//                JSONObject jsonObjectPerm = jsonArrayPerm.getJSONObject(j);
//                unixperm.add(jsonObjectPerm.getInt("unix_op_id"));
//            }
//            commonPropertiesModel.setUnixpermBITS(unixperm);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return commonPropertiesModel;
//    }

//    /**
//     * apply read permissions on organization entity - this needs a special application of
//     * permissions since they have be applied from outside of the organization itself
//     *
//     * @param coRef                collection reference
//     * @param organizationServerID organization identification
//     * @param userServerID         user identification
//     * @param primaryTeamBIT       primary team identification
//     * @param secondaryTeamBITS    secondary teams identification
//     * @return list query results
//     */
//    @NonNull
//    public static Task<List<QuerySnapshot>> applyReadPermissions(@NonNull CollectionReference coRef,
//                                                                 String organizationServerID,
//                                                                 String userServerID,
//                                                                 int primaryTeamBIT,
//                                                                 List<Integer> secondaryTeamBITS) {
//
//        Task ownerTask, primaryTask, secondaryTask, workplaceTask;
//
//        /* 2. the owner permission bit is on, and the member is the owner */
//        ownerTask = coRef
//                .whereEqualTo("userOwnerID", userServerID)
//                .whereArrayContains("unixpermBITS", cSharedPreference.OWNER_READ)
//                .get();
//
//        /* 3. the primary team permission bit is on, and the member is in the team */
//        primaryTask = coRef
//                .whereEqualTo("organizationServerID", organizationServerID)
//                .whereEqualTo("teamOwnerBIT", primaryTeamBIT)
//                .whereArrayContains("unixpermBITS", cSharedPreference.PRIMARY_READ)
//                .get();
//
//        /* 4. the second team permission bit is on, and the member is in the team */
//        secondaryTask = coRef
//                .whereEqualTo("organizationServerID", organizationServerID)
//                .whereIn("teamOwnerBIT", secondaryTeamBITS)
//                .whereArrayContains("unixpermBITS", cSharedPreference.SECONDARY_READ)
//                .get();
//
//        /* 5. the other permission bit is on -
//              all members of an organization have access */
//        workplaceTask = coRef
//                .whereEqualTo("organizationServerID", organizationServerID)
//                .whereArrayContains("unixpermBITS", cSharedPreference.WORKPLACE_READ)
//                .get();
//
//        return Tasks.whenAllSuccess(ownerTask, primaryTask, secondaryTask, workplaceTask);
//    }

//    /**
//     * check whether the access is allowed or not based on the permission assigned to a role
//     *
//     * @param perm              common properties permissions for an entity
//     * @param userServerID      user identification for testing the ownership permissions
//     * @param primaryTeamBIT    primary team bit for testing primary team membership permissions
//     * @param secondaryTeamBITS secondary team bits for testing secondary teams membership permissions
//     * @param statusBITS        status bits for testing permission on an operation given the status bits
//     * @return boolean
//     */
//    public static boolean isPermitted(@NonNull cUnixPerm perm, String userServerID, int primaryTeamBIT,
//                                      List<Integer> secondaryTeamBITS, List<Integer> statusBITS) {
//
//        return statusBITS.contains(perm.getStatusBIT()) &&
//                /* 1. members of 'Administrator' team are always allowed to do everything */
//                ((perm.getTeamOwnerBIT() == 1) ||
//                        /* 2. the member is the owner and the owner permission bit is on */
//                        (perm.getUserOwnerID().equals(userServerID) &&
//                                perm.getUnixpermBITS().contains(cSharedPreference.OWNER_READ)) ||
//                        /* 3. the member is in the primary team and the primary team permission bit is on */
//                        (perm.getTeamOwnerBIT() == primaryTeamBIT &&
//                                perm.getUnixpermBITS().contains(cSharedPreference.PRIMARY_READ)) ||
//                        /* 4. the member is in the secondary team and the second team permission bit is on */
//                        (secondaryTeamBITS.contains(perm.getTeamOwnerBIT()) &&
//                                perm.getUnixpermBITS().contains(cSharedPreference.SECONDARY_READ)) ||
//                        /* 5. members is in the organization and the workplace permission bit is on */
//                        (perm.getUnixpermBITS().contains(cSharedPreference.WORKPLACE_READ)));
//    }

//    /**
//     * check whether the access is allowed or not based on the permission assigned to a role
//     * used only for organization entity
//     *
//     * @param perm              common properties permissions for an entity
//     * @param userServerID      user identification for testing the ownership permissions
//     * @param primaryTeamBIT    primary team bit for testing primary team membership permissions
//     * @param secondaryTeamBITS secondary team bits for testing secondary teams membership permissions
//     * @return boolean
//     */
//    public static boolean isPermitted(cUnixPerm perm, String userServerID, int primaryTeamBIT,
//                                      List<Integer> secondaryTeamBITS) {
//
//        return (
//                /* 1. the member is the owner and the owner permission bit is on */
//                (perm.getUserOwnerID().equals(userServerID) &&
//                        perm.getUnixpermBITS().contains(cSharedPreference.OWNER_READ)) ||
//                        /* 2. the member is in the primary team and the primary team permission bit is on */
//                        (perm.getTeamOwnerBIT() == primaryTeamBIT &&
//                                perm.getUnixpermBITS().contains(cSharedPreference.PRIMARY_READ)) ||
//                        /* 3. the member is in the secondary team and the second team permission bit is on */
//                        (secondaryTeamBITS.contains(perm.getTeamOwnerBIT()) &&
//                                perm.getUnixpermBITS().contains(cSharedPreference.SECONDARY_READ)) ||
//                        /* 4. members is in the organization and the workplace permission bit is on */
//                        (perm.getUnixpermBITS().contains(cSharedPreference.WORKPLACE_READ)));
//    }




    /**
     * static class that holds permissions of an entity
     */
    public static class cUnixPerm {
        private String userOwnerID;
        private int teamOwnerBIT;
        private int unixpermBITS;
        private int statusBIT;

        public String getUserOwnerID() {
            return userOwnerID;
        }

        public void setUserOwnerID(String userOwnerID) {
            this.userOwnerID = userOwnerID;
        }

        public int getTeamOwnerBIT() {
            return teamOwnerBIT;
        }

        public void setTeamOwnerBIT(int teamOwnerBIT) {
            this.teamOwnerBIT = teamOwnerBIT;
        }

        public int getUnixpermBITS() {
            return unixpermBITS;
        }

        public void setUnixpermBITS(int unixpermBITS) {
            this.unixpermBITS = unixpermBITS;
        }

        public int getStatusBIT() {
            return statusBIT;
        }

        public void setStatusBIT(int statusBIT) {
            this.statusBIT = statusBIT;
        }
    }

//    /**
//     * build permission menu tree
//     *
//     * @param context         context
//     * @param permissionModel permission models
//     * @return menu tree
//     */
//    public static List<cTreeModel> getMenuPermissions(Context context,
//                                                      cPermissionModel permissionModel) {
//        List<cTreeModel> menuTreeModels = new ArrayList<>();
//
//        /* menu items */
//        String menu_items = "jsons_old_files/sys_menu_items.json";
//        String menuItemsJSONString = cDatabaseUtils.loadJSONFromAsset(menu_items,
//                context.getAssets());
//
//        try {
//            /* LEVEL 0: add permission node as the root node */
//            int parentIndex = 0, childIndex = 0;
//            menuTreeModels.add(new cTreeModel(parentIndex, -1, 0,
//                    new cPermissionModel(permissionModel)));
//            int parentPermissionIndex = childIndex;
//
//            /* menu items */
//            JSONObject jsonObjectMenuItem = new JSONObject(menuItemsJSONString);
//            JSONArray jsonArrayMenu = jsonObjectMenuItem.getJSONArray("menuitems");
//            List<String> main_menu_ids = null;//getMainMenuIDs(permissionModel);
//            for (int m = 0; m < jsonArrayMenu.length(); m++) {
//                JSONObject jsonObjectMenu = jsonArrayMenu.getJSONObject(m);
//
//                cMenuModel menuModel = new cMenuModel();
//                // set the main menu item
//                menuModel.setMenuServerID(jsonObjectMenu.getInt("menu_id"));
//                menuModel.setName(jsonObjectMenu.getString("menu_name"));
//                menuModel.setDescription(jsonObjectMenu.getString("menu_description"));
//                menuModel.setChecked(main_menu_ids.contains(jsonObjectMenu.getString(
//                        "menu_id")));
//
//                /* LEVEL 1: add child main menu node to the parent permission node */
//                childIndex = childIndex + 1;
//                menuTreeModels.add(new cTreeModel(childIndex, parentPermissionIndex, 1,
//                        menuModel));
//                int parentMenuIndex = childIndex;
//
//                JSONArray jsonArraySubMenu = jsonObjectMenu.getJSONArray("sub_menu");
//                List<String> sub_menu_items = null;//getSubMenuIDs(permissionModel,
//                //String.valueOf(menuModel.getMenuServerID()));
//                for (int j = 0; j < jsonArraySubMenu.length(); j++) {
//
//                    JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
//                    //if (sub_menu_items.contains(jsonObjectSubItem.getString(
//                    //        "sub_menu_id"))) {
//                    cMenuModel subMenuModel = new cMenuModel();
//                    subMenuModel.setParentServerID(menuModel.getMenuServerID());
//                    subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_menu_id"));
//                    subMenuModel.setName(jsonObjectSubItem.getString("sub_menu_name"));
//                    subMenuModel.setDescription(jsonObjectSubItem.getString(
//                            "sub_menu_description"));
//                    subMenuModel.setChecked(sub_menu_items.contains(jsonObjectSubItem.getString(
//                            "sub_menu_id")));
//
//                    /* LEVEL 2: add child sub menu node to the parent main menu node */
//                    childIndex = childIndex + 1;
//                    menuTreeModels.add(new cTreeModel(childIndex, parentMenuIndex, 2,
//                            subMenuModel));
//                    // }
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return menuTreeModels;
//    }



    /**
     * get a string value from cell of an excel file.
     *
     * @param row    row of the sheet
     * @param column column of the sheet
     * @return return a string value
     */
    public static String getCellAsString(Row row, int column) {
        return row.getCell(column, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
    }

    /**
     * get a numeric value from cell of an excel file.
     *
     * @param row    row of the sheet
     * @param column column of the sheet
     * @return return a numeric value
     */
    public static int getCellAsNumeric(Row row, int column) {
        return (int) row.getCell(column, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
    }

    /**
     * get a date value from cell of an excel file.
     *
     * @param row    row of the sheet
     * @param column column of the sheet
     * @return return a numeric value
     */
    public static Date getCellAsDate(Row row, int column) {
        Cell cell = row.getCell(column);
        //CellValue cellValue = formulaEvaluator.evaluate(cell);
        if (HSSFDateUtil.isCellDateFormatted(cell)) {
            Date date = row.getCell(column, Row.CREATE_NULL_AS_BLANK).getDateCellValue();
            return date;
        }
        return new Date();
    }

    /**
     * get an extension of a file
     *
     * @param context context
     * @param uri     URI
     * @return extension
     */
    public static String getFileExtension(Context context, Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public static <T> List<List<T>> getPartitions(List<T> input, int chunkSize) {

        int inputSize = input.size();
        int chunkCount = (int) Math.ceil(inputSize / (double) chunkSize);

        Map<Integer, List<T>> map = new HashMap<>(chunkCount);
        List<List<T>> chunks = new ArrayList<>(chunkCount);

        for (int i = 0; i < inputSize; i++) {

            map.computeIfAbsent(i / chunkSize, (ignore) -> {

                List<T> chunk = new ArrayList<>();
                chunks.add(chunk);
                return chunk;

            }).add(input.get(i));
        }

        return chunks;
    }

}

//    public static List<cTreeModel> buildPermissionTree(Context context, cRoleModel roleModel,
//                                                       cPermissionModel permissionModel) {
//        List<cTreeModel> treeModels = new ArrayList<>();
//
//        Gson gson = new Gson();
//
//        /* permission tree */
//        String perm_tree = "jsons/sys_workspace_privilege_tree.json";
//        String permTreeJSONString = cDatabaseUtils.loadJSONFromAsset(perm_tree,
//                context.getAssets());
//
//        /* entity operations */
//        String entity_ops = "jsons/old_sys_entity_operations.json";
//        String entityOpsJSONString = cDatabaseUtils.loadJSONFromAsset(entity_ops,
//                context.getAssets());
//        /* operation status */
//        String op_status = "jsons/old_sys_op_statuses.json";
//        String opStatusJSONString = cDatabaseUtils.loadJSONFromAsset(op_status,
//                context.getAssets());
//
//        /* unix operations */
//        String unix_ops = "jsons/sys_unixperms.json";
//        String unixOpsJSONString = cDatabaseUtils.loadJSONFromAsset(unix_ops,
//                context.getAssets());
//
//        /* menu items */
//        String menu_items = "jsons/sys_menu_items.json";
//        String menuItemsJSONString = cDatabaseUtils.loadJSONFromAsset(menu_items,
//                context.getAssets());
//
//        try {
//            /* LEVEL 0: add root role node */
//            int parentIndex = 0, childIndex = 0;
//            //treeModels.add(new cTreeModel(childIndex, -1, 0,
//            //        new cRoleModel(roleModel)));
//
//            /* LEVEL 0: add child permission node to the parent role node */
//            //childIndex = parentIndex + 1;
//            treeModels.add(new cTreeModel(childIndex, -1, 0,
//                    new cPermissionModel(permissionModel)));
//            int parentPermissionIndex = childIndex;
//
//            /* modules */
//            List<String> module_ids = getModuleIDs(permissionModel);
//
//            JSONObject jsonObjectPermTree = new JSONObject(permTreeJSONString);
//            JSONArray jsonArrayModules = jsonObjectPermTree.getJSONArray("modules");
//            for (int i = 0; i < jsonArrayModules.length(); i++) {
//                JSONObject objectModule = jsonArrayModules.getJSONObject(i);
//
//                cSectionModel moduleModel = new cSectionModel();
//                moduleModel.setModuleServerID(objectModule.getString("module_id"));
//                moduleModel.setName(objectModule.getString("module_name"));
//                moduleModel.setChecked(module_ids.contains(objectModule.getString(
//                        "module_id")));
//
//                /* LEVEL 1: add child module node to the parent permission node */
//                childIndex = childIndex + 1;
//                treeModels.add(new cTreeModel(childIndex, parentPermissionIndex, 1,
//                        moduleModel));
//                int parentModuleIndex = childIndex;
//
//                /* entities */
//                JSONArray jsonArrayEntities = objectModule.getJSONArray("entities");
//                List<String> entity_ids = getEntityIDs(permissionModel,
//                        moduleModel.getModuleServerID());
//                for (int j = 0; j < jsonArrayEntities.length(); j++) {
//                    JSONObject objectEntity = jsonArrayEntities.getJSONObject(j);
//
//                    cEntityModel entityModel = new cEntityModel();
//                    entityModel.setEntityServerID(objectEntity.getString("entity_id"));
//                    entityModel.setName(objectEntity.getString("entity_name"));
//                    entityModel.setDescription(objectEntity.getString("entity_description"));
//                    entityModel.setChecked(entity_ids.contains(objectEntity.getString(
//                            "entity_id")));
//
//                    /* LEVEL 2: add child entity node to the parent module node */
//                    childIndex = childIndex + 1;
//                    treeModels.add(new cTreeModel(childIndex, parentModuleIndex, 2, entityModel));
//                    int parentEntityIndex = childIndex;
//
//                    /* entity operations */
//                    JSONObject jsonObjectEntityOps = new JSONObject(entityOpsJSONString);
//                    JSONArray jsonArrayOps = jsonObjectEntityOps.getJSONArray("operations");
//                    List<String> op_ids = getOperationIDs(permissionModel,
//                            moduleModel.getModuleServerID(), entityModel.getEntityServerID());
//                    for (int k = 0; k < jsonArrayOps.length(); k++) {
//                        JSONObject objectOps = jsonArrayOps.getJSONObject(k);
//
//                        cOperationModel operationModel = new cOperationModel();
//                        operationModel.setOperationServerID(objectOps.getString(
//                                "entity_op_id"));
//                        operationModel.setName(objectOps.getString(
//                                "entity_op_name"));
//                        operationModel.setDescription(objectOps.getString(
//                                "entity_op_description"));
//                        operationModel.setChecked(op_ids.contains(objectOps.getString(
//                                "entity_op_id")));
//
//                        /* LEVEL 3: add child entity operation node to the parent entity node */
//                        childIndex = childIndex + 1;
//                        treeModels.add(new cTreeModel(childIndex, parentEntityIndex, 3,
//                                operationModel));
//                        int parentEntityOperationIndex = childIndex;
//
//                        /* operation status */
//                        JSONObject jsonObjectOpStatus = new JSONObject(opStatusJSONString);
//                        JSONArray jsonArrayStatus = jsonObjectOpStatus.getJSONArray(
//                                "statuses");
//                        List<String> status_ids = getStatusIDs(permissionModel,
//                                moduleModel.getModuleServerID(), entityModel.getEntityServerID(),
//                                operationModel.getOperationServerID());
//                        for (int l = 0; l < jsonArrayStatus.length(); l++) {
//                            JSONObject objectStatus = jsonArrayStatus.getJSONObject(l);
//
//                            cStatusModel statusModel = new cStatusModel();
//                            statusModel.setStatusServerID(objectStatus.getString(
//                                    "status_id"));
//                            statusModel.setName(objectStatus.getString("status_name"));
//                            statusModel.setDescription(objectStatus.getString(
//                                    "status_description"));
//                            statusModel.setChecked(status_ids.contains(objectStatus.getString(
//                                    "status_id")));
//
//
//                            /* LEVEL 4: add child status node to the parent entity operation node */
//                            childIndex = childIndex + 1;
//                            treeModels.add(new cTreeModel(childIndex, parentEntityOperationIndex,
//                                    4, statusModel));
//                            //parentIndex = childIndex;
//                        }
//                    }
//
//                    /* unix operations */
//                    JSONObject jsonObjectUnixOps = new JSONObject(unixOpsJSONString);
//                    JSONArray jsonArrayUnixOps = jsonObjectUnixOps.getJSONArray(
//                            "unixoperations");
//                    List<String> unix_op_ids = getUnixOperationIDs(permissionModel,
//                            moduleModel.getModuleServerID(), entityModel.getEntityServerID());
//
//                    for (int k = 0; k < jsonArrayUnixOps.length(); k++) {
//                        JSONObject objectUnixOps = jsonArrayUnixOps.getJSONObject(k);
//
//                        cUnixOperationModel unixOperationModel = new cUnixOperationModel();
//                        unixOperationModel.setOperationServerID(objectUnixOps.getString(
//                                "unix_op_id"));
//                        unixOperationModel.setName(objectUnixOps.getString(
//                                "unix_op_name"));
//                        unixOperationModel.setDescription(objectUnixOps.getString(
//                                "unix_op_description"));
//                        unixOperationModel.setChecked(unix_op_ids.contains(objectUnixOps.getString(
//                                "unix_op_id")));
//
//                        /* LEVEL 5: add child unix operation node to the parent entity node */
//                        childIndex = childIndex + 1;
//                        treeModels.add(new cTreeModel(childIndex, parentEntityIndex, 5,
//                                unixOperationModel));
//                        //parentIndex = childIndex;
//                    }
//                }
//
//                /* menu items */
//                JSONObject jsonObjectMenuItem = new JSONObject(menuItemsJSONString);
//                JSONArray jsonArrayMenu = jsonObjectMenuItem.getJSONArray("menuitems");
//                List<String> main_menu_ids = getMainMenuIDs(permissionModel);
//                for (int m = 0; m < jsonArrayMenu.length(); m++) {
//                    JSONObject jsonObjectMenu = jsonArrayMenu.getJSONObject(m);
//
//                    cMenuModel menuModel = new cMenuModel();
//                    // set the main menu item
//                    menuModel.setMenuServerID(jsonObjectMenu.getInt("menu_id"));
//                    menuModel.setName(jsonObjectMenu.getString("menu_name"));
//                    menuModel.setDescription(jsonObjectMenu.getString("menu_description"));
//                    menuModel.setChecked(main_menu_ids.contains(jsonObjectMenu.getString(
//                            "menu_id")));
//
//                    /* LEVEL 6: add child main menu node to the parent permission node */
//                    childIndex = childIndex + 1;
//                    treeModels.add(new cTreeModel(childIndex, parentPermissionIndex, 6, menuModel));
//                    int parentMenuIndex = childIndex;
//
//                    JSONArray jsonArraySubMenu = jsonObjectMenu.getJSONArray("sub_menu");
//                    List<String> sub_menu_items = getSubMenuIDs(permissionModel,
//                            String.valueOf(menuModel.getMenuServerID()));
//                    for (int j = 0; j < jsonArraySubMenu.length(); j++) {
//
//                        JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
//                        if (sub_menu_items.contains(jsonObjectSubItem.getString(
//                                "sub_menu_id"))) {
//                            cMenuModel subMenuModel = new cMenuModel();
//                            subMenuModel.setParentServerID(menuModel.getMenuServerID());
//                            subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_menu_id"));
//                            subMenuModel.setName(jsonObjectSubItem.getString("sub_menu_name"));
//                            subMenuModel.setDescription(jsonObjectSubItem.getString(
//                                    "sub_menu_description"));
//
//                            /* LEVEL 7: add child sub menu node to the parent main menu node */
//                            childIndex = childIndex + 1;
//                            treeModels.add(new cTreeModel(childIndex, parentMenuIndex, 7,
//                                    subMenuModel));
//                            //parentIndex = childIndex;
//                            //Log.d("TREE MODEL ","RESULTS ==>> "+childIndex);
//                        }
//                    }
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return treeModels;
//    }