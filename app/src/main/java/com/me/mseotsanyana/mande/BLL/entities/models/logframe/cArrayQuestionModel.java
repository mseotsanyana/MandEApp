package com.me.mseotsanyana.mande.BLL.entities.models.logframe;

import com.me.mseotsanyana.mande.BLL.entities.models.common.cArrayChartModel;
import com.me.mseotsanyana.mande.BLL.entities.models.evaluation.cArrayResponseModel;
import com.me.mseotsanyana.mande.BLL.entities.models.evaluation.cArraySetModel;

import java.util.Set;

public class cArrayQuestionModel extends cQuestionModel {
    private long arraySetID;
    private long arrayChartID;

    private cArraySetModel arraySetModel;
    private cArrayChartModel arrayChartModel;

    private Set<cArrayResponseModel> arrayResponseModelSet;

    public long getArraySetID() {
        return arraySetID;
    }

    public void setArraySetID(long arraySetID) {
        this.arraySetID = arraySetID;
    }

    public long getArrayChartID() {
        return arrayChartID;
    }

    public void setArrayChartID(long arrayChartID) {
        this.arrayChartID = arrayChartID;
    }

    public cArraySetModel getArraySetModel() {
        return arraySetModel;
    }

    public void setArraySetModel(cArraySetModel arraySetModel) {
        this.arraySetModel = arraySetModel;
    }

    public cArrayChartModel getArrayChartModel() {
        return arrayChartModel;
    }

    public void setArrayChartModel(cArrayChartModel arrayChartModel) {
        this.arrayChartModel = arrayChartModel;
    }

    public Set<cArrayResponseModel> getArrayResponseModelSet() {
        return arrayResponseModelSet;
    }

    public void setArrayResponseModelSet(Set<cArrayResponseModel> arrayResponseModelSet) {
        this.arrayResponseModelSet = arrayResponseModelSet;
    }
}
