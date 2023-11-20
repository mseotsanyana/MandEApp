package com.me.mseotsanyana.mande.domain.entities.models.monitoring;

import com.me.mseotsanyana.mande.domain.entities.models.evaluation.cArraySetModel;

public class cArrayIndicatorModel extends cQuantitativeIndicatorModel{
    private cArraySetModel arraySetModel;

    public cArraySetModel getArraySetModel() {
        return arraySetModel;
    }

    public void setArraySetModel(cArraySetModel arraySetModel) {
        this.arraySetModel = arraySetModel;
    }
}
