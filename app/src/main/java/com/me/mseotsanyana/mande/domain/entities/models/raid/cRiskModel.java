package com.me.mseotsanyana.mande.domain.entities.models.raid;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cRaidModel;

public class cRiskModel extends cRaidModel {
    private cRiskRegisterModel riskRegisterModel;

    public cRiskModel(){
        riskRegisterModel = new cRiskRegisterModel();
    }

    public cRiskRegisterModel getRiskRegisterModel() {
        return riskRegisterModel;
    }

    public void setRiskRegisterModel(cRiskRegisterModel riskRegisterModel) {
        this.riskRegisterModel = riskRegisterModel;
    }
}
