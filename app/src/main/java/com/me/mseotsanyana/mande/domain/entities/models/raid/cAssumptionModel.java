package com.me.mseotsanyana.mande.domain.entities.models.raid;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cRaidModel;

public class cAssumptionModel extends cRaidModel {
    private cAssumptionRegisterModel assumptionRegisterModel;

    public cAssumptionModel(){
        assumptionRegisterModel = new cAssumptionRegisterModel();
    }

    public cAssumptionRegisterModel getAssumptionRegisterModel() {
        return assumptionRegisterModel;
    }

    public void setAssumptionRegisterModel(cAssumptionRegisterModel assumptionRegisterModel) {
        this.assumptionRegisterModel = assumptionRegisterModel;
    }
}
