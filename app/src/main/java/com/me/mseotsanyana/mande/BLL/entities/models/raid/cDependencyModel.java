package com.me.mseotsanyana.mande.BLL.entities.models.raid;

import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cRaidModel;

public class cDependencyModel extends cRaidModel {
    private String howValidated;
    private int validated;

    private cDependencyRegisterModel dependencyRegisterModel;

    public cDependencyModel(){
        dependencyRegisterModel = new cDependencyRegisterModel();
    }

    public String getHowValidated() {
        return howValidated;
    }

    public void setHowValidated(String howValidated) {
        this.howValidated = howValidated;
    }

    public int getValidated() {
        return validated;
    }

    public void setValidated(int validated) {
        this.validated = validated;
    }

    public cDependencyRegisterModel getDependencyRegisterModel() {
        return dependencyRegisterModel;
    }

    public void setDependencyRegisterModel(cDependencyRegisterModel dependencyRegisterModel) {
        this.dependencyRegisterModel = dependencyRegisterModel;
    }
}
