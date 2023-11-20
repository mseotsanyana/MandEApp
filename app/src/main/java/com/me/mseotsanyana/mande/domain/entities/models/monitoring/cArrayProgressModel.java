package com.me.mseotsanyana.mande.domain.entities.models.monitoring;

import com.me.mseotsanyana.mande.domain.entities.models.evaluation.cArrayChoiceModel;

public class cArrayProgressModel extends cQuantitativeProgressModel{
    private cArrayChoiceModel arrayChoiceModel;
    private double disaggregatedActual;

    public cArrayChoiceModel getArrayChoiceModel() {
        return arrayChoiceModel;
    }

    public void setArrayChoiceModel(cArrayChoiceModel arrayChoiceModel) {
        this.arrayChoiceModel = arrayChoiceModel;
    }

    public double getDisaggregatedActual() {
        return disaggregatedActual;
    }

    public void setDisaggregatedActual(double disaggregatedActual) {
        this.disaggregatedActual = disaggregatedActual;
    }
}
