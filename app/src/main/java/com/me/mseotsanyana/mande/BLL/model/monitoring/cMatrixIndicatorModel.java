package com.me.mseotsanyana.mande.BLL.model.monitoring;

import com.me.mseotsanyana.mande.BLL.model.evaluation.cMatrixSetModel;

public class cMatrixIndicatorModel extends cQuantitativeIndicatorModel{
    private cMatrixSetModel matrixSetModel;

    public cMatrixSetModel getMatrixSetModel() {
        return matrixSetModel;
    }

    public void setMatrixSetModel(cMatrixSetModel matrixSetModel) {
        this.matrixSetModel = matrixSetModel;
    }
}
