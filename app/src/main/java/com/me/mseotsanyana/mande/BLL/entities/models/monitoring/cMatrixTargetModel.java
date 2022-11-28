package com.me.mseotsanyana.mande.BLL.entities.models.monitoring;

import com.me.mseotsanyana.mande.BLL.entities.models.evaluation.cColChoiceModel;
import com.me.mseotsanyana.mande.BLL.entities.models.evaluation.cRowChoiceModel;

public class cMatrixTargetModel extends cQuantitativeTargetModel{
    private cRowChoiceModel rowChoiceModel;
    private cColChoiceModel colChoiceModel;
    private double disaggregatedBaseline;
    private double disaggregatedTarget;

    public cMatrixTargetModel(){
        rowChoiceModel = new cRowChoiceModel();
        colChoiceModel = new cColChoiceModel();
    }

    public cRowChoiceModel getRowChoiceModel() {
        return rowChoiceModel;
    }

    public void setRowChoiceModel(cRowChoiceModel rowChoiceModel) {
        this.rowChoiceModel = rowChoiceModel;
    }

    public cColChoiceModel getColChoiceModel() {
        return colChoiceModel;
    }

    public void setColChoiceModel(cColChoiceModel colChoiceModel) {
        this.colChoiceModel = colChoiceModel;
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
