package com.me.mseotsanyana.mande.OLD.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cRaidModel;

public interface iViewRaidListener {
    void onClickUpdateRaid(int position, cRaidModel raidModel);
    void onClickDeleteRaid(int position, int raidID);
    void onClickSyncRaid(int position, cRaidModel raidModel);
    void onClickBMBRaid(int index);
}