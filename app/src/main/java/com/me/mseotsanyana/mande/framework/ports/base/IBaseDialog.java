package com.me.mseotsanyana.mande.framework.ports.base;

import android.view.View;

import com.me.mseotsanyana.mande.application.structures.enums.EDialogAction;

public interface IBaseDialog{
    void showAlertMessageDialog(String title, String message, IDialogActionHandler listener);
    void showCreateOrUpdateCustomDialog(String title, View view, IDialogActionHandler listener);

    interface IDialogActionHandler {
        void handleDialogAction(EDialogAction action);
    }
}
