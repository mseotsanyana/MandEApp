package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cPrivilegeFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.common.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cUserProfileFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cUserLoginPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserLoginPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.mande.databinding.SessionLoginFragmentBinding;


public class cLoginFragment extends Fragment implements iUserLoginPresenter.View {
    private static final String TAG = cLoginFragment.class.getSimpleName();

    private iUserLoginPresenter userLoginPresenter;

    private SessionLoginFragmentBinding binding;

    public cLoginFragment() {
    }

    public static cLoginFragment newInstance() {
        return new cLoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userLoginPresenter = new cUserLoginPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(), this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cPrivilegeFirestoreRepositoryImpl(requireContext()),
                new cUserProfileFirestoreRepositoryImpl(requireContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.session_login_fragment, container,
                false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        /* initial hide progress bar */
        hideProgress();

        /* login listener */
        binding.loginTextView.setOnClickListener(v -> {
            String email = binding.emailEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                CUserProfileModel userProfileModel = new CUserProfileModel(email, password);
                userLoginPresenter.signInWithEmailAndPassword(userProfileModel.getEmail(),
                        userProfileModel.getPassword());
                Log.d(TAG, userProfileModel.getActiveState().getState());
            } else {
                Snackbar.make(requireView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
            }
        });

        /* change password listener
        binding.changePasswordTextView.setOnClickListener(v -> {
            NavDirections action = cLoginFragmentDirections.
                    actionCLoginFragmentToCChangePasswordFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });*/

        /* change password listener */
        binding.resetPasswordTextView.setOnClickListener(v -> {
            NavDirections action = cLoginFragmentDirections.
                    actionCLoginFragmentToCResetPasswordFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });

        /* sign up listener */
        binding.signUpTextView.setOnClickListener(view1 -> {
            NavDirections action = cLoginFragmentDirections.
                    actionCLoginFragmentToCSignUpFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });
    }

    @Override
    public void onUserLoginSucceeded(String msg) {
      NavDirections action = cLoginFragmentDirections.
              actionCLoginFragmentToCOrganizationFragment();
      Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onUserLoginFailed(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {
        Log.d(TAG, msg);
    }

    /* getters and setters */
    @Override
    public EditText getEmailEditText() {
        return binding.emailEditText;
    }

    @Override
    public EditText getPasswordEditText() {
        return binding.passwordEditText;
    }

    @Override
    public TextView getResetPasswordTextView() {
        return binding.resetPasswordTextView;
    }

    @Override
    public String getResourceString(int resourceID) {
        return getString(resourceID);
    }
}