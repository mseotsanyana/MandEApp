package com.me.mseotsanyana.mande.OLD.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cInputModel;

public interface iViewInputListener {
    void onClickUpdateInput(int position, cInputModel inputModel);
    void onClickDeleteInput(int position, long outputID);
    void onClickSyncInput(int position, cInputModel inputModel);
    void onClickBMBInput(int index);
}
