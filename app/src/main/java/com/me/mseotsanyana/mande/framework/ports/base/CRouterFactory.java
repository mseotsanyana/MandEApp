package com.me.mseotsanyana.mande.framework.ports.base;

public class CDialogFactory extends AGUIFactory<IBaseDialog> {

    private final IBaseFragment fragment;

    public CDialogFactory(IBaseFragment fragment) {
        this.fragment = fragment;
    }

    public IBaseFragment getFragment() {
        return fragment;
    }

    @Override
    IBaseDialog createGUI(GUIType guiType) {
        return null;
    }
}
