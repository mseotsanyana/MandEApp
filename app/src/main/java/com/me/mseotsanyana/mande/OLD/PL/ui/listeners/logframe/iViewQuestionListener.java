package com.me.mseotsanyana.mande.OLD.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cQuestionModel;

public interface iViewQuestionListener {
    void onClickUpdateQuestion(int position, cQuestionModel questionModel);
    void onClickDeleteQuestion(int position, int questionID);
    void onClickSyncQuestion(int position, cQuestionModel questionModel);
    void onClickBMBQuestion(int index);
}