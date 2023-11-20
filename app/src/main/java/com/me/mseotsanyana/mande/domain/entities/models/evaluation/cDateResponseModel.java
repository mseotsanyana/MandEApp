package com.me.mseotsanyana.mande.domain.entities.models.evaluation;

import java.util.Date;

public class cDateResponseModel extends cEvaluationResponseModel{
    private Date dateResponse;

    public cDateResponseModel(){
        super();
    }

    public Date getDateResponse() {
        return dateResponse;
    }

    public void setDateResponse(Date dateResponse) {
        this.dateResponse = dateResponse;
    }
}
