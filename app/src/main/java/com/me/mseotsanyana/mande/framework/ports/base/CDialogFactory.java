package com.me.mseotsanyana.mande.framework.ports.base;

import android.content.Context;

import com.me.mseotsanyana.mande.framework.ports.CStandardDialog;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

public class CDialogFactory implements AGUIFactory<IBaseDialog> {

    private final Context context;
    private final IBaseFragment baseFragment;
    private final IBaseView baseView;
    private final EDialogType dialogType;

    public CDialogFactory(Context context, IBaseFragment baseFragment, IBaseView baseView,
                          EDialogType dialogType) {
        this.context = context;
        this.baseFragment = baseFragment;
        this.baseView = baseView;
        this.dialogType = dialogType;
    }

    public IBaseFragment getBaseFragment() {
        return baseFragment;
    }

    public IBaseView getIBaseView() {
        return baseView;
    }

    @Override
    public IBaseDialog create() {
        return new CStandardDialog(context
        );
    }
}
