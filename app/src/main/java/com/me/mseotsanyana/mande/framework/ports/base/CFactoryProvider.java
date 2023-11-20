package com.me.mseotsanyana.mande.framework.ports.base;

public class CFactoryProvider {
    public static AGUIFactory getFactory(EFactoryType EFactoryType, IBaseFragment fragment){
        switch (EFactoryType){
            case DIALOG_FACTORY:
                return new CDialogFactory(fragment);
            case ROUTER_FACTORY:
                return new CRouterFactory(fragment);
            default:
                return null;
        }
    }
}
