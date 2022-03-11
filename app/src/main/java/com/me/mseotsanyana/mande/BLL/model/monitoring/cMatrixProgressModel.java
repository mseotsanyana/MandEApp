package com.me.mseotsanyana.mande.BLL.model.monitoring;

import com.me.mseotsanyana.mande.BLL.model.evaluation.cColChoiceModel;
import com.me.mseotsanyana.mande.BLL.model.evaluation.cRowChoiceModel;

public class cMatrixProgressModel extends cQuantitativeProgressModel{
    private cRowChoiceModel rowChoiceModel;
    private cColChoiceModel colChoiceModel;
    private double disaggregatedActual;

    public cMatrixProgressModel(){
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

    public double getDisaggregatedActual() {
        return disaggregatedActual;
    }

    public void setDisaggregatedActual(double disaggregatedActual) {
        this.disaggregatedActual = disaggregatedActual;
    }
}
