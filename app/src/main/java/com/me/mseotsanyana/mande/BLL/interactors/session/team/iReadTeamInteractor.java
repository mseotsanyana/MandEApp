package com.me.mseotsanyana.mande.BLL.interactors.session.team;

import com.me.mseotsanyana.mande.BLL.entities.models.session.cWorkspaceModel;
import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

import java.util.List;

public interface iReadTeamInteractor extends iInteractor {
    interface Callback {
        void onReadTeamsFailed(String msg);
        void onReadTeamsSucceeded(List<cWorkspaceModel> teamModels);
    }
}
