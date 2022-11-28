package com.me.mseotsanyana.mande.BLL.entities.models.session;

import com.google.firebase.firestore.Exclude;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cMenuModel{
    private int menuServerID;
    private int parentServerID;

    private String name;
    private String description;
    private boolean checked;

    private List<cMenuModel> submenu;

    public cMenuModel(){}

    public cMenuModel(String name) {
        this.name = name;
    }

    @Exclude
    public int getMenuServerID() {
        return menuServerID;
    }

    public void setMenuServerID(int menuServerID) {
        this.menuServerID = menuServerID;
    }

    @Exclude
    public int getParentServerID() {
        return parentServerID;
    }

    public void setParentServerID(int parentServerID) {
        this.parentServerID = parentServerID;
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

    @Exclude
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<cMenuModel> getSubmenu() {
        return submenu;
    }

    public void setSubmenu(List<cMenuModel> submenu) {
        this.submenu = submenu;
    }
}
