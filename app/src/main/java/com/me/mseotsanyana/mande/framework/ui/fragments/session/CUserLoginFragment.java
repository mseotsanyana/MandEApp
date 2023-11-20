package com.me.mseotsanyana.mande.framework.ui.fragments.session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.me.mseotsanyana.mande.databinding.SessionLoginFragmentBinding;
import com.me.mseotsanyana.mande.framework.modelviews.session.CUserLoginViewModel;
import com.me.mseotsanyana.mande.infrastructure.controllers.session.CUserProfileControllerImpl;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IUserProfileController;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.infrastructure.presenters.session.CUserProfilePresenterImpl;
import com.me.mseotsanyana.mande.infrastructure.services.CMainThreadImpl;
import com.me.mseotsanyana.mande.infrastructure.repository.firestore.session.CUserProfileFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.infrastructure.services.CThreadExecutorImpl;


public class CUserLoginFragment extends Fragment {
    private static final String TAG = CUserLoginFragment.class.getSimpleName();

    private CUserLoginViewModel cViewModel;
    private IUserProfileController iController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SessionLoginFragmentBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.session_login_fragment, container,
                false);

        cViewModel = new CUserLoginViewModel(this, binding);

        iController = new CUserProfileControllerImpl(CThreadExecutorImpl.getInstance(),
                CMainThreadImpl.getInstance(),
                cViewModel,
                new CUserProfilePresenterImpl(cViewModel),
                new CUserProfileFirestoreRepositoryImpl(requireContext()));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cViewModel.initViews();
    }

    /************************************* controller methods *************************************/

    public void signInWithEmailAndPassword(String email, String password){
        iController.signInWithEmailAndPassword(email, password);
    }

//    /* getters and setters */
//    @Override
//    public EditText getEmailEditText() {
//        return binding.emailEditText;
//    }
//
//    @Override
//    public EditText getPasswordEditText() {
//        return binding.passwordEditText;
//    }
//
//    @Override
//    public TextView getResetPasswordTextView() {
//        return binding.resetPasswordTextView;
//    }
//
//    @Override
//    public String getResourceString(int resourceID) {
//        return getString(resourceID);
//    }
}