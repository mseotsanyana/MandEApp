package com.me.mseotsanyana.mande.BLL.entities.models.evaluation;

public class cArrayResponseModel extends cEvaluationResponseModel{
    private long arrayChoiceID;

    private cArrayChoiceModel arrayChoiceResponse;

    public cArrayResponseModel(){
        super();
        arrayChoiceResponse = new cArrayChoiceModel();
    }

    public long getArrayChoiceID() {
        return arrayChoiceID;
    }

    public void setArrayChoiceID(long arrayChoiceID) {
        this.arrayChoiceID = arrayChoiceID;
    }

    public cArrayChoiceModel getArrayChoiceResponse() {
        return arrayChoiceResponse;
    }

    public void setArrayChoiceResponse(cArrayChoiceModel arrayChoiceResponse) {
        this.arrayChoiceResponse = arrayChoiceResponse;
    }
}
