package com.me.mseotsanyana.mande.BLL.entities.models.logframe;

import com.me.mseotsanyana.mande.BLL.entities.models.common.cPrimitiveChartModel;
import com.me.mseotsanyana.mande.BLL.entities.models.evaluation.cDateResponseModel;
import com.me.mseotsanyana.mande.BLL.entities.models.evaluation.cNumericResponseModel;
import com.me.mseotsanyana.mande.BLL.entities.models.evaluation.cTextResponseModel;

import java.util.Set;

public class cPrimitiveQuestionModel extends cQuestionModel{
    private long primitiveChartID;
    private cPrimitiveChartModel primitiveChartModel;

    /* set of evaluation responses for the question */
    private Set<cDateResponseModel> dateResponseModelSet;
    private Set<cNumericResponseModel> numericResponseModelSet;
    private Set<cTextResponseModel> textResponseModelSet;

    public cPrimitiveQuestionModel(cQuestionModel questionModel,
                                   cPrimitiveQuestionModel primitiveQuestionModel){
        super(questionModel);
        primitiveChartID = primitiveQuestionModel.getPrimitiveChartID();

    }

    public cPrimitiveQuestionModel() {

    }


    public long getPrimitiveChartID() {
        return primitiveChartID;
    }

    public void setPrimitiveChartID(long primitiveChartID) {
        this.primitiveChartID = primitiveChartID;
    }

    public cPrimitiveChartModel getPrimitiveChartModel() {
        return primitiveChartModel;
    }

    public void setPrimitiveChartModel(cPrimitiveChartModel primitiveChartModel) {
        this.primitiveChartModel = primitiveChartModel;
    }

    public Set<cDateResponseModel> getDateResponseModelSet() {
        return dateResponseModelSet;
    }

    public void setDateResponseModelSet(Set<cDateResponseModel> dateResponseModelSet) {
        this.dateResponseModelSet = dateResponseModelSet;
    }

    public Set<cNumericResponseModel> getNumericResponseModelSet() {
        return numericResponseModelSet;
    }

    public void setNumericResponseModelSet(Set<cNumericResponseModel> numericResponseModelSet) {
        this.numericResponseModelSet = numericResponseModelSet;
    }

    public Set<cTextResponseModel> getTextResponseModelSet() {
        return textResponseModelSet;
    }

    public void setTextResponseModelSet(Set<cTextResponseModel> textResponseModelSet) {
        this.textResponseModelSet = textResponseModelSet;
    }
}
