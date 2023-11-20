package com.me.mseotsanyana.mande.application.structures;

public class CResponseModel {
    String operation;
    Object object;
    public CResponseModel(String operation, Object object){
        this.operation = operation;
        this.object = object;
    }

    public String getOperation() {
        return operation;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "CResponseModel{" +
                "operation='" + operation + '\'' +
                ", object=" + object +
                '}';
    }
}
