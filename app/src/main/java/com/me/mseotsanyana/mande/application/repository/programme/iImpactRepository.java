package com.me.mseotsanyana.mande.application.repository.programme;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cImpactModel;

import java.util.List;

public interface iImpactRepository {
    void readImpacts(String logframeID, String organizationServerID, String userServerID,
                     int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                     List<Integer> statusBITS, iReadImpactsCallback callback);

    interface iReadImpactsCallback {
        void onReadImpactSucceeded(List<cImpactModel> impactModels);

        void onReadImpactFailed(String msg);
    }
}
