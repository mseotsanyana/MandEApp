package com.me.mseotsanyana.mande.OLD.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cOutputModel;

public interface iViewOutputListener {
    void onClickUpdateOutput(int position, cOutputModel outputModel);
    void onClickDeleteOutput(int position, long outputID);
    void onClickSyncOutput(int position, cOutputModel outputModel);
    void onClickBMBOutput(int index);
}
