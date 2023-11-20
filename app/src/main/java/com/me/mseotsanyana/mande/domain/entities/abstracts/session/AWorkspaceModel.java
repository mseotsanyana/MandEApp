package com.me.mseotsanyana.mande.domain.entities.abstracts.session;

import com.me.mseotsanyana.mande.domain.entities.interfaces.IWorkspaceState;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;

/**
 * Created by mseotsanyana on 2021/03/18.
 */

abstract public class AWorkspaceModel implements IWorkspaceState {
    protected CWorkspaceModel workspaceModel;

    public AWorkspaceModel(CWorkspaceModel workspaceModel){
        this.workspaceModel = workspaceModel;
    }

    @Override
    public void createWorkspaceEvent() {

    }

    @Override
    public void acceptWorkspaceEvent() {

    }

    @Override
    public void rejectWorkspaceEvent() {

    }

    @Override
    public void activateWorkspaceEvent() {

    }

    @Override
    public void deactivateWorkspaceEvent() {

    }

    @Override
    public void deleteWorkspaceEvent() {

    }
}
