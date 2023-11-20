package com.me.mseotsanyana.mande.framework.ports.base;

public interface IGUIFactory {
    IBaseDialog createDialog(IBaseFragment fragment);
    IBaseRouter createRouter();
}