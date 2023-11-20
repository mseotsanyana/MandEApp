package com.me.mseotsanyana.mande.framework.ports.base;

import android.content.Context;

import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

public class CFactoryProvider {
    private final Context context;
    private final IBaseFragment baseFragment;
    private final IBaseView baseView;

    public CFactoryProvider(Context context, IBaseFragment baseFragment, IBaseView baseView) {
        this.context = context;
        this.baseFragment = baseFragment;
        this.baseView = baseView;
    }

    public IBaseView getIBaseView() {
        return baseView;
    }

    public AGUIFactory<IBaseDialog> getDialogFactory(EDialogType dialogType) {
        return new CDialogFactory(context, baseFragment, baseView, dialogType);
    }

    public AGUIFactory<IBaseAdapter> getAdapterFactory(EAdapterType adapterType) {
        return new CAdapterFactory(context, baseView, adapterType);
    }

    public AGUIFactory<IBaseRouter> getRouterFactory(ERouterType routerType) {
        return new CRouterFactory(baseFragment, routerType);
    }
}
