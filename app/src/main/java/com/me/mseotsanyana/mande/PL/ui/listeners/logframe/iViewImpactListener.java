package com.me.mseotsanyana.mande.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cImpactModel;
import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cQuestionModel;

public interface iViewImpactListener {
    void onClickBMBImpact(int index, long impactID);
    void onClickCreateSubImpact(long impactID, cImpactModel impactModel);
    void onClickUpdateImpact(int position, cImpactModel impactModel);
    void onClickDeleteImpact(int position, long impactID);
    void onClickSyncImpact(int position, cImpactModel impactModel);
    void onClickDetailImpact(cOutcomeModel[] outcomeModels, cQuestionModel[] questionModels);
}
