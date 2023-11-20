package com.me.mseotsanyana.mande.framework.ui.fragments.session;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.me.mseotsanyana.mande.infrastructure.controllers.session.cResetPasswordPresenterImpl;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iResetPasswordPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.infrastructure.services.CMainThreadImpl;
import com.me.mseotsanyana.mande.databinding.SessionResetpasswordFragmentBinding;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.infrastructure.repository.firestore.session.CUserProfileFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.infrastructure.services.CThreadExecutorImpl;
import com.me.mseotsanyana.mande.infrastructure.utils.responsemodel.CTreeModel;

import java.util.Map;

public class CUserResetPasswordFragment extends Fragment implements iResetPasswordPresenter.View {
    private static final String TAG = CUserResetPasswordFragment.class.getSimpleName();

    private iResetPasswordPresenter resetPasswordPresenter;

    private SessionResetpasswordFragmentBinding binding;

    public CUserResetPasswordFragment() {
    }

    public static CUserResetPasswordFragment newInstance() {
        return new CUserResetPasswordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resetPasswordPresenter = new cResetPasswordPresenterImpl(
                CThreadExecutorImpl.getInstance(),
                CMainThreadImpl.getInstance(), this,
                new CUserProfileFirestoreRepositoryImpl(requireContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.session_resetpassword_fragment,
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
        binding.resetPasswordTextView.setOnClickListener(v -> {
            String email = binding.emailEditText.getText().toString().trim();

            if (!email.isEmpty()) {
                resetPasswordPresenter.sendPasswordResetEmail(new CUserProfileModel(email));
            } else {
                Snackbar.make(requireView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
            }
        });

        /* sign in listener */
        loginTextView.setOnClickListener(view -> {
            NavDirections action = CUserResetPasswordFragmentDirections.actionCUserResetPasswordFragmentToCUserLoginFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });
    }

    @Override
    public void onResetPasswordSucceeded(String msg) {
        NavDirections action = CUserResetPasswordFragmentDirections.actionCUserResetPasswordFragmentToCUserLoginFragment();
        Navigation.findNavController(requireView()).navigate(action);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResetPasswordFailed(String msg) {
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

    public void showResponse(Map<String, CTreeModel> response) {

    }

    @Override
    public void showMessage(String msg) {
        Log.d(TAG, msg);
    }

    @Override
    public void showResponseMessage(String message) {

    }
}
