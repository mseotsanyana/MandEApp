package com.me.mseotsanyana.mande.domain.entities.interfaces;

public interface IWorkspaceAccountState {
    void createAccountEvent(int requestType);
    void approveAccountEvent();
    void rejectedAccountEvent();
    void activateAccountEvent();
    void deactivateAccountEvent();
    void deleteAccountEvent();
}
