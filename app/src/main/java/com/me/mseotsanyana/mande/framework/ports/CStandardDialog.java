package com.me.mseotsanyana.mande.framework.ports;

import android.content.Context;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.application.structures.enums.EDialogAction;
import com.me.mseotsanyana.mande.framework.ports.base.IBaseDialog;
import com.me.mseotsanyana.mande.framework.ports.base.IBaseFragment;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationWorkspaceController;

public class CAbstractDialog implements IBaseDialog {
    private static final String TAG = CAbstractDialog.class.getSimpleName();
    private final Context context;

    public CAbstractDialog(Context context, IBaseFragment baseFragment,
                           IOrganizationWorkspaceController.IViewModel iViewModel) {
        this.context = context;
    }

    public void showAlertMessageDialog(String title, String message, IDialogHandler listener) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(context.getResources().getText(R.string.Yes), (dialog, i) -> {
                    listener.handleSimpleAction(EDialogAction.YES);
                    dialog.dismiss();
                })
                .setNegativeButton(context.getResources().getText(R.string.No), (dialog, i) -> {
                    listener.handleSimpleAction(EDialogAction.NO);
                    dialog.dismiss();
                })
                .show();
    }

    public void showCreateOrUpdateCustomDialog(String title, View view, IDialogHandler listener) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setCancelable(false)
                .setView(view)
                .setPositiveButton(context.getResources().getText(R.string.Save), (dialog, i) -> {
                    listener.handleSimpleAction(EDialogAction.SAVE);
                    dialog.dismiss();
                })
                .setNegativeButton(context.getResources().getText(R.string.Cancel), (dialog, i) -> {
                    listener.handleSimpleAction(EDialogAction.CANCEL);
                    dialog.dismiss();
                }).show();
    }
}
