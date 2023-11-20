package com.me.mseotsanyana.mande.framework.modelviews.session;

import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.me.mseotsanyana.mande.databinding.SessionLoginFragmentBinding;
import com.me.mseotsanyana.mande.framework.routers.CUserLoginRouter;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.CUserLoginFragment;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IUserProfileController;

public class CUserLoginIViewModel implements IUserProfileController.IViewModel<String> {
    private static final String TAG = CUserLoginIViewModel.class.getSimpleName();

    /* organization workspace binding */
    private final SessionLoginFragmentBinding binding;
    /* organization workspace view interface */
    private final CUserLoginFragment fragment;
    /* navigation to and from other views */
    private final CUserLoginRouter router;

    //private final String[] ORG_TYPE = {"National Partner", "Funder (or Donor)", "Beneficiary", "Implementing Agency"};

    public CUserLoginIViewModel(CUserLoginFragment fragment,
                                SessionLoginFragmentBinding binding) {
        this.router = new CUserLoginRouter(fragment);
        this.fragment = fragment;
        this.binding = binding;
    }

    public void initViews() {
        /* initial hide progress bar */
        hideProgress();

        /* login listener */
        binding.loginTextView.setOnClickListener(v -> {
            String email = binding.emailEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                //CUserProfileModel userProfileModel = new CUserProfileModel(email, password);
                fragment.signInWithEmailAndPassword(email, password);
                //Log.d(TAG, userProfileModel.getActiveState().getState());
            } else {
                Snackbar.make(fragment.requireView(), "Fields are empty !",
                        Snackbar.LENGTH_LONG).show();
            }
        });

        /* change password listener
        binding.changePasswordTextView.setOnClickListener(v -> {
            NavDirections action = cLoginFragmentDirections.
                    actionCLoginFragmentToCChangePasswordFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });*/

        /* change password listener */
        binding.resetPasswordTextView.setOnClickListener(resetView ->
            router.actionCUserLoginFragmentToCUserResetPasswordFragment());

        /* sign up listener */
        binding.signUpTextView.setOnClickListener(signupView ->
            router.actionCUserLoginFragmentToCUserSignupFragment());
    }

    /********************************* user login feedback methods ********************************/

//    @Override
//    public void OnUserLoginSucceeded(String msg) {
//        Toast.makeText(fragment.getActivity(), msg, Toast.LENGTH_SHORT).show();
//        router.actionCUserLoginFragmentToCOrganizationWorkspaceFragment();
//    }
//
//    @Override
//    public void OnUserSignOutSucceeded(String msg) {
//
//    }

//    @Override
//    public void OnUserLoginFailed(String msg) {
//        Toast.makeText(fragment.getActivity(), msg, Toast.LENGTH_SHORT).show();
//    }

    /********************************* interactor feedback methods ********************************/

    @Override
    public void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showResponse(String response) {
        router.actionCUserLoginFragmentToCOrganizationWorkspaceFragment();
        Log.i(TAG, " "+response);
    }

    @Override
    public void showError(String msg) {
        Log.d(TAG, msg);
    }
}
