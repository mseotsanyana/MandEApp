package com.me.mseotsanyana.mande.BLL.model.evaluation;

public class cNumericResponseModel extends cEvaluationResponseModel{
    private long numericResponse;

    public cNumericResponseModel(){
        super();
    }

    public long getNumericResponse() {
        return numericResponse;
    }

    public void setNumericResponse(long numericResponse) {
        this.numericResponse = numericResponse;
    }
}
