package com.me.mseotsanyana.mande.framework.ui.routers.session;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.me.mseotsanyana.mande.framework.ui.fragments.session.CHomePageFragment;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.CHomePageFragmentDirections;

public class CHomePageRouter {
    private final CHomePageFragment fragment;
    private NavDirections action;

    public CHomePageRouter(CHomePageFragment fragment){
        this.fragment = fragment;
    }

    public void actionCHomePageFragmentToCUserLoginFragment(){
        action = CHomePageFragmentDirections.actionCHomePageFragmentToCUserLoginFragment();
        Navigation.findNavController(fragment.requireView()).navigate(action);
    }

    public void actionCHomePageFragmentToCOrganizationWorkspaceFragment(){
        action = CHomePageFragmentDirections.actionCHomePageFragmentToCOrganizationWorkspaceFragment();
        Navigation.findNavController(fragment.requireView()).navigate(action);
    }
}
