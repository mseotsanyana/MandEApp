package com.me.mseotsanyana.mande.OLD.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cOutcomeModel;

public interface iViewOutcomeListener {
    //void onClickDetailOutcome(cOutcomeModel[] outcomeModels, int position);
    void onClickUpdateOutcome(cOutcomeModel outcomeModel, int position);
    void onClickDeleteOutcome(long outcomeID, int position);
    void onClickSyncOutcome(cOutcomeModel outcomeModel, int position);
    void onClickBMBOutcome(int index);
}
