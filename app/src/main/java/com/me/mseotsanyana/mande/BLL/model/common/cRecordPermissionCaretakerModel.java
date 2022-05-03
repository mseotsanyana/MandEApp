package com.me.mseotsanyana.mande.BLL.model.common;

public class cRecordPermissionCaretakerModel {
    protected cRecordPermissionMementoModel memento;

    public void saveMemento(cRecordPermissionMementoModel memento){
        this.memento = memento;
    }

    public cRecordPermissionMementoModel getMemento(){
        return this.memento;
    }
}
