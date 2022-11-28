package com.me.mseotsanyana.mande.BLL.entities.models.monitoring;

import com.me.mseotsanyana.mande.BLL.entities.models.evaluation.cArrayChoiceModel;

public class cArrayMilestoneModel extends cQuantitativeMilestoneModel{
    private cArrayChoiceModel arrayChoiceModel;
    private double disaggregatedBaseline;
    private double disaggregatedTarget;

    public cArrayMilestoneModel(){
        arrayChoiceModel = new cArrayChoiceModel();
    }

    public cArrayChoiceModel getArrayChoiceModel() {
        return arrayChoiceModel;
    }

    public void setArrayChoiceModel(cArrayChoiceModel arrayChoiceModel) {
        this.arrayChoiceModel = arrayChoiceModel;
    }

    public double getDisaggregatedBaseline() {
        return disaggregatedBaseline;
    }

    public void setDisaggregatedBaseline(double disaggregatedBaseline) {
        this.disaggregatedBaseline = disaggregatedBaseline;
    }

    public double getDisaggregatedTarget() {
        return disaggregatedTarget;
    }

    public void setDisaggregatedTarget(double disaggregatedTarget) {
        this.disaggregatedTarget = disaggregatedTarget;
    }
}
