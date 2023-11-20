package com.me.mseotsanyana.mande.domain.entities.interfaces;

public interface IWorkspaceState {
    void createWorkspaceEvent();
    void acceptWorkspaceEvent();
    void rejectWorkspaceEvent();
    void activateWorkspaceEvent();
    void deactivateWorkspaceEvent();
    void deleteWorkspaceEvent();
}
