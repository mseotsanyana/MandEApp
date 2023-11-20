package com.me.mseotsanyana.mande.framework.routers;


import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.me.mseotsanyana.mande.framework.ui.fragments.session.COrganizationWorkspaceFragment;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.COrganizationWorkspaceFragmentDirections;

public class COrganizationWorkspaceRouter {
    private final COrganizationWorkspaceFragment fragment;
    private NavDirections action;

    public COrganizationWorkspaceRouter(COrganizationWorkspaceFragment fragment){
        this.fragment = fragment;
    }

    public void actionCOrganizationWorkspaceFragmentToCHomePageFragment(){
        action = COrganizationWorkspaceFragmentDirections.
                actionCOrganizationWorkspaceFragmentToCHomePageFragment();
        Navigation.findNavController(fragment.requireView()).navigate(action);
    }

    public void actionCOrganizationWorkspaceFragmentToCLoginFragment() {
        action = COrganizationWorkspaceFragmentDirections.
                actionCOrganizationWorkspaceFragmentToCUserLoginFragment();
        Navigation.findNavController(fragment.requireView()).navigate(action);

    }
}
