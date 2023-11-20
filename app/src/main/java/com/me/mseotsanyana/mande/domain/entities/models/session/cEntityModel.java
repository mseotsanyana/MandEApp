package com.me.mseotsanyana.mande.domain.entities.models.session;

import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cEntityModel {
    private String entityServerID;

    private String name;
    private String description;
    private boolean checked;

    private List<Integer> actions;
    private List<Integer> permissions;
    private List<cTransitionModel> transitions;

    public cEntityModel() {
    }

    public cEntityModel(cEntityModel entityModel) {
        this.setEntityServerID(entityModel.getEntityServerID());
        this.setName(entityModel.getName());
        this.setDescription(entityModel.getDescription());
        this.setPermissions(entityModel.getPermissions());
        this.setTransitions(entityModel.getTransitions());
        this.setChecked(entityModel.isChecked());
    }

    public String getEntityServerID() {
        return entityServerID;
    }

    public void setEntityServerID(String entityServerID) {
        this.entityServerID = entityServerID;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<Integer> getActions() {
        return actions;
    }

    public void setActions(List<Integer> actions) {
        this.actions = actions;
    }

    public List<Integer> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Integer> permissions) {
        this.permissions = permissions;
    }

    public List<cTransitionModel> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<cTransitionModel> transitions) {
        this.transitions = transitions;
    }
}