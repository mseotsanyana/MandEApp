package com.me.mseotsanyana.mande.domain.entities.interfaces;

public interface IOrganizationAccountState {
    void signInEvent(String email, String password);
    void createAccountEvent();
    void approveAccountEvent();
    void suspendAccountEvent();
    void activateAccountEvent();
    void deactivateAccountEvent();
    void closeAccountEvent();

    void enableAccountEvent();

    void selectWorkspaceEvent();
    void logoutEvent();

    void entry();
    void exit();

    String getState();
}
