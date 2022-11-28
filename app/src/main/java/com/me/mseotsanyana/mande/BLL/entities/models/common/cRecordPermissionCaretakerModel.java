package com.me.mseotsanyana.mande.BLL.entities.models.common;

public class cRecordPermissionCaretakerModel {
    protected cRecordPermissionMementoModel memento;

    public void saveMemento(cRecordPermissionMementoModel memento){
        this.memento = memento;
    }

    public cRecordPermissionMementoModel getMemento(){
        return this.memento;
    }
}
