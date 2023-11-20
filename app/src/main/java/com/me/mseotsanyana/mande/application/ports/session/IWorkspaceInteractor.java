package com.me.mseotsanyana.mande.application.interactors.session.team;

import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.application.ports.base.iInteractor;

import java.util.List;

public interface iReadTeamInteractor extends iInteractor {
    interface Callback {
        void onReadTeamsFailed(String msg);
        void onReadTeamsSucceeded(List<CWorkspaceModel> teamModels);
    }
}
