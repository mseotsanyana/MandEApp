package com.me.mseotsanyana.mande.domain.entities.models.session;

import java.util.List;
import java.util.Map;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cModuleModel {
    private String moduleServerID;
    private String privilegeServerID;

    private String name;
    private String description;

    /**
     * What is a Permission ?
     *
     * A permission is a declaration of an action that can be
     * executed on an entity as a result of a trigger event.
     *
     * What are Privileges ?
     *
     * Privileges are the assigned (or granted) permissions
     * to the user of a system.
     */

    // Map 1 : entity identifications that belong to the module.
    // Map 2 : pair of source and target states which form transitions.
    // Map 3 : pair of events and actions whose its guard is a list of permissions.
    private Map<String, Map<String, Map<String, List<Integer>>>> entities;

    public cModuleModel() {
    }

    public cModuleModel(cModuleModel moduleModel) {
        this.setPrivilegeServerID(moduleModel.getPrivilegeServerID());
        this.setModuleServerID(moduleModel.getModuleServerID());
        this.setName(moduleModel.getName());
        this.setDescription(moduleModel.getDescription());
        this.setEntities(moduleModel.getEntities());
    }

    public String getPrivilegeServerID() {
        return privilegeServerID;
    }

    public void setPrivilegeServerID(String privilegeServerID) {
        this.privilegeServerID = privilegeServerID;
    }

    public String getModuleServerID() {
        return moduleServerID;
    }

    public void setModuleServerID(String moduleServerID) {
        this.moduleServerID = moduleServerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Map<String, Map<String, List<Integer>>>> getEntities() {
        return entities;
    }

    public void setEntities(Map<String, Map<String, Map<String, List<Integer>>>> entities) {
        this.entities = entities;
    }
}