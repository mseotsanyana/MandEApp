package com.me.mseotsanyana.mande.domain.entities.models.evaluation;

public class cTextResponseModel extends cEvaluationResponseModel{
    private String textResponse;

    public cTextResponseModel(){
        super();
    }

    public String getTextResponse() {
        return textResponse;
    }

    public void setTextResponse(String textResponse) {
        this.textResponse = textResponse;
    }
}
