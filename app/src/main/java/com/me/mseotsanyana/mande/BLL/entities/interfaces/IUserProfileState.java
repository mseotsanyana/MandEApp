package com.me.mseotsanyana.mande.BLL.entities.interfaces;

public interface IUserProfileState {
    void activateEvent();
    void submitEvent();
    void signInEvent(String email, String password);
    void signUpEvent();
    void cancelEvent();
    void resetPasswordEvent();
    void changePasswordEvent();
    void logoutEvent();
    String getState();
}
