package com.me.mseotsanyana.mande.BLL.entities.models.session;

import com.google.firebase.firestore.Exclude;

import java.util.List;
import java.util.Map;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cEntityModel {
    private String entityServerID;

    private String name;
    private String description;
    private boolean checked;

    private List<Integer> unixperms;
    // operation identification with its status identifications
    private Map<String, List<Integer>> entityperms;

    public cEntityModel() {
    }

    public cEntityModel(String entityServerID, Map<String, List<Integer>> entityperms,
                        List<Integer> unixperms) {
        this.entityServerID = entityServerID;
        this.entityperms = entityperms;
        this.unixperms = unixperms;
    }

    public String getEntityServerID() {
        return entityServerID;
    }

    public void setEntityServerID(String entityServerID) {
        this.entityServerID = entityServerID;
    }

    @Exclude
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
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

    public List<Integer> getUnixperms() {
        return unixperms;
    }

    public void setUnixperms(List<Integer> unixperms) {
        this.unixperms = unixperms;
    }

    public Map<String, List<Integer>> getEntityperms() {
        return entityperms;
    }

    public void setEntityperms(Map<String, List<Integer>> entityperms) {
        this.entityperms = entityperms;
    }
}