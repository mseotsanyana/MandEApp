package com.me.mseotsanyana.mande.framework.ports;

import android.content.Context;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.application.structures.enums.EDialogAction;
import com.me.mseotsanyana.mande.framework.ports.base.IBaseDialog;

public class CStandardDialog implements IBaseDialog {
    //private static final String TAG = CStandardDialog.class.getSimpleName();
    private final Context context;

    public CStandardDialog(Context context) {
        this.context = context;
    }

    public void showAlertMessageDialog(String title, String message, IDialogActionHandler listener) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(context.getResources().getText(R.string.Yes), (dialog, i) -> {
                    listener.handleDialogAction(EDialogAction.YES);
                    dialog.dismiss();
                })
                .setNegativeButton(context.getResources().getText(R.string.No), (dialog, i) -> {
                    listener.handleDialogAction(EDialogAction.NO);
                    dialog.dismiss();
                })
                .show();
    }

    public void showCreateOrUpdateCustomDialog(String title, View view, IDialogActionHandler listener) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setCancelable(false)
                .setView(view)
                .setPositiveButton(context.getResources().getText(R.string.Save), (dialog, i) -> {
                    listener.handleDialogAction(EDialogAction.SAVE);
                    dialog.dismiss();
                })
                .setNegativeButton(context.getResources().getText(R.string.Cancel), (dialog, i) -> {
                    listener.handleDialogAction(EDialogAction.CANCEL);
                    dialog.dismiss();
                }).show();
    }
}
