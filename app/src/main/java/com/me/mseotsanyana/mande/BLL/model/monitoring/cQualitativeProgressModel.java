package com.me.mseotsanyana.mande.BLL.model.monitoring;

public class cQualitativeProgressModel extends cIndicatorProgressModel{
    cCriteriaScoreModel criteriaActualScoreModel;

    public cCriteriaScoreModel getCriteriaActualScoreModel() {
        return criteriaActualScoreModel;
    }

    public void setCriteriaActualScoreModel(cCriteriaScoreModel criteriaActualScoreModel) {
        this.criteriaActualScoreModel = criteriaActualScoreModel;
    }
}
