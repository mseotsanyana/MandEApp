package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.me.mseotsanyana.mande.PL.presenters.session.iChangePasswordPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.databinding.SessionChangepasswordFragmentBinding;

public class cChangePasswordFragment extends Fragment implements iChangePasswordPresenter.View {
    private static final String TAG = cChangePasswordFragment.class.getSimpleName();

    private iChangePasswordPresenter changePasswordPresenter;

    private SessionChangepasswordFragmentBinding binding;

    public cChangePasswordFragment() {
    }

    public static cChangePasswordFragment newInstance() {
        return new cChangePasswordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*changePasswordPresenter = new cUserLoginPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(), this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cPermissionFirestoreRepositoryImpl(requireContext()),
                new cChangePasswordFirestoreRepositoryImpl(requireContext()));*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.session_changepassword_fragment,
                container, false);

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

        TextView loginTextView = binding.getRoot().findViewById(R.id.signTextView);
        loginTextView.setText(R.string.user_sign_in);

        /* forget password listener */
        binding.changePasswordTextView.setOnClickListener(v -> {
            String oldPassword = binding.oldPasswordEditText.getText().toString().trim();
            String newPassword = binding.newPasswordEditText.getText().toString().trim();
            String confirmPassword = binding.confirmPasswordEditText.getText().toString().trim();

            if (!oldPassword.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty()) {
                if(newPassword.equals(confirmPassword)) {
                    changePasswordPresenter.changePassword(newPassword);
                }else{
                    Snackbar.make(requireView(), "Passwords don't match!", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(requireView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
            }
        });

        /* sign in listener */
        loginTextView.setOnClickListener(view -> {
//            NavDirections action = cChangePasswordFragmentDirections.
//                    actionCChangePasswordFragmentToCLoginFragment();
//            Navigation.findNavController(requireView()).navigate(action);
        });
    }

    @Override
    public void onChangePasswordSucceeded(String msg) {
//        NavDirections action = cChangePasswordFragmentDirections.
//                actionCChangePasswordFragmentToCLoginFragment();
//        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onChangePasswordFailed(String msg) {
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
    public EditText getOldPasswordEditText() {
        return binding.oldPasswordEditText;
    }

    @Override
    public EditText getNewPasswordEditText() {
        return binding.newPasswordEditText;
    }

    @Override
    public TextView getConfirmPasswordTextView() {
        return null;
    }
}
