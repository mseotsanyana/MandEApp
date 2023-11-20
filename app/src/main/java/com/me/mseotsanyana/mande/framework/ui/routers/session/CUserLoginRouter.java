package com.me.mseotsanyana.mande.framework.ui.routers.session;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.me.mseotsanyana.mande.framework.ui.fragments.session.CUserLoginFragment;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.CUserLoginFragmentDirections;

public class CUserLoginRouter {
    private final CUserLoginFragment fragment;
    private NavDirections action;

    public CUserLoginRouter(CUserLoginFragment fragment){
        this.fragment = fragment;
    }

    public void actionCUserLoginFragmentToCUserResetPasswordFragment(){
        action = CUserLoginFragmentDirections.
                actionCUserLoginFragmentToCUserResetPasswordFragment();
        Navigation.findNavController(fragment.requireView()).navigate(action);
    }

    public void actionCUserLoginFragmentToCUserSignupFragment() {
        action = CUserLoginFragmentDirections.
                actionCUserLoginFragmentToCUserSignupFragment();
        Navigation.findNavController(fragment.requireView()).navigate(action);
    }

    public void actionCUserLoginFragmentToCOrganizationWorkspaceFragment() {
        action = CUserLoginFragmentDirections.
                actionCUserLoginFragmentToCOrganizationWorkspaceFragment();
        Navigation.findNavController(fragment.requireView()).navigate(action);
    }
}
