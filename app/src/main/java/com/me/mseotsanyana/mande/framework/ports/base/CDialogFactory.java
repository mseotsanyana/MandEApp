package com.me.mseotsanyana.mande.framework.ui;

import com.me.mseotsanyana.mande.framework.ports.base.IBaseDialog;
import com.me.mseotsanyana.mande.framework.ports.base.IBaseFragment;
import com.me.mseotsanyana.mande.framework.ports.base.IBaseRouter;
import com.me.mseotsanyana.mande.framework.ports.base.AGUIFactory;
import com.me.mseotsanyana.mande.framework.ui.routers.session.COrganizationWorkspaceRouter;
import com.me.mseotsanyana.mande.framework.ui.dialogs.session.COrganizationWorkspaceDialog;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.COrganizationWorkspaceFragment;


public class COrganizationWorkspaceFactory extends AGUIFactory {

    private final IBaseFragment fragment;

    public COrganizationWorkspaceFactory(IBaseFragment fragment) {
        this.fragment = fragment;
    }

//    @Override
//    public IBaseDialog createDialog(IBaseFragment fragment) {
//        return new COrganizationWorkspaceDialog((COrganizationWorkspaceFragment) getFragment());
//    }
//
//    @Override
//    public IBaseRouter createRouter() {
//        return new COrganizationWorkspaceRouter();
//    }

    public IBaseFragment getFragment() {
        return fragment;
    }
}
