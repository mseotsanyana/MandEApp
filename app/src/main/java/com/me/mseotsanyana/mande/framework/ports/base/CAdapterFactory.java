package com.me.mseotsanyana.mande.framework.ports.base;

import com.me.mseotsanyana.mande.framework.ui.fragments.session.COrganizationWorkspaceFragment;
import com.me.mseotsanyana.mande.framework.ui.routers.session.COrganizationWorkspaceRouter;

public class CRouterFactory implements AGUIFactory<IBaseRouter> {

    private final ERouterType routerType;
    private final IBaseFragment fragment;

    public CRouterFactory(IBaseFragment fragment, ERouterType routerType) {
        this.fragment = fragment;
        this.routerType = routerType;
    }

    public IBaseFragment getFragment() {
        return fragment;
    }

    @Override
    public IBaseRouter create() {
        return new COrganizationWorkspaceRouter((COrganizationWorkspaceFragment) getFragment());
    }

}
