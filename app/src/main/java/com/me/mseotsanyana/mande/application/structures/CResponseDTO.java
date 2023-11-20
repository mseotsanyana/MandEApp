package com.me.mseotsanyana.mande.application.structures;

import com.me.mseotsanyana.mande.application.structures.enums.EAction;

public class CResponseDTO<T> implements IResponseDTO<T>{
    EAction action;
    T data;
    public CResponseDTO(EAction action, T data){
        this.action = action;
        this.data = data;
    }

    @Override
    public EAction getAction() {
        return action;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "CResponseModel{" +
                "operation='" + action + '\'' +
                ", object=" + data +
                '}';
    }
}
