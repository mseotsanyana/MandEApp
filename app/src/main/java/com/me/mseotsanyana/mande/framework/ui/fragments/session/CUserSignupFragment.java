package com.me.mseotsanyana.mande.framework.ui.fragments.session;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.me.mseotsanyana.mande.infrastructure.controllers.session.cUserSignUpPresenterImpl;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iUserSignUpPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.infrastructure.services.CMainThreadImpl;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.infrastructure.repository.firestore.session.CUserProfileFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.infrastructure.services.CThreadExecutorImpl;
import com.me.mseotsanyana.mande.infrastructure.utils.responsemodel.CTreeModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class CUserSignupFragment extends Fragment implements iUserSignUpPresenter.View {
//    private static final String TAG = cSignUpFragment.class.getSimpleName();

    private CircleImageView profileCircleImageView;
    private Bitmap bitmap;

    private EditText firstNameEditText, surnameEditText, designationEditText, emailEditText,
            passwordEditText, confirmPasswordEditText;
    private View progressBar;

    private iUserSignUpPresenter userSignUpPresenter;
    private ActivityResultLauncher<String> launchUploadActivity;

    public CUserSignupFragment() {
    }

    public static CUserSignupFragment newInstance() {
        return new CUserSignupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialise default profile image
        try {
            AssetManager am = requireActivity().getAssets();
            InputStream is = am.open("image/me_default_avatar.png");
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create launcher variable to select profile image from gallery
        launchUploadActivity = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Picasso.get().load(uri).into(profileCircleImageView);
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.session_signup_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /* create data structures */
        initDataStructures();

        /* initialize views */
        initViews(view);
    }

    private void initDataStructures() {
        userSignUpPresenter = new cUserSignUpPresenterImpl(
                CThreadExecutorImpl.getInstance(),
                CMainThreadImpl.getInstance(),
                null,
                this,
                new CUserProfileFirestoreRepositoryImpl(requireContext()));
    }

    private void initViews(View view) {
        profileCircleImageView = view.findViewById(R.id.profileCircleImageView);
        firstNameEditText = view.findViewById(R.id.firstNameEditText);
        surnameEditText = view.findViewById(R.id.surnameEditText);
        designationEditText = view.findViewById(R.id.designationEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText);
        progressBar = view.findViewById(R.id.progressBar);

        TextView loginTextView = view.findViewById(R.id.signTextView);
        TextView signUpTextView = view.findViewById(R.id.signUpTextView);

        signUpTextView.setText(R.string.user_sign_up);
        loginTextView.setText(R.string.user_sign_in);

        /* initial hide progress bar */
        hideProgress();

        /* change profile image */
        profileCircleImageView.setOnClickListener(v -> launchUploadActivity.launch("image/*"));

        /* login listener */
        loginTextView.setOnClickListener(v -> {
            NavDirections action = CUserSignupFragmentDirections.actionCUserSignupFragmentToCUserLoginFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });

        /* sign up listener */
        signUpTextView.setOnClickListener(v -> {
            String name = Objects.requireNonNull(firstNameEditText.getText()).toString().trim();
            String surname = Objects.requireNonNull(surnameEditText.getText()).toString().trim();
            String designation = Objects.requireNonNull(designationEditText.getText()).toString().trim();
            String email = Objects.requireNonNull(emailEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();
            String confirmPassword = Objects.requireNonNull(confirmPasswordEditText.getText()).toString().trim();

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] imageData = bytes.toByteArray();

            CUserProfileModel userProfileModel = new CUserProfileModel(imageData, name, surname,
                    designation, email, password);

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() &&
                    !confirmPassword.isEmpty()) {
                userSignUpPresenter.createUserWithEmailAndPassword(userProfileModel);
            } else {
                Snackbar.make(requireView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
            }
        });

        /* login listener */
        loginTextView.setOnClickListener(v -> {
            NavDirections action = CUserSignupFragmentDirections.actionCUserSignupFragmentToCUserLoginFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });
    }

    /*private String getFileExtension(Uri uri){
        ContentResolver contentResolver = requireActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }*/

    @Override
    public void onUserSignUpSucceeded(String msg) {
        NavDirections action = CUserSignupFragmentDirections.actionCUserSignupFragmentToCUserLoginFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onUserSignUpFailed(String msg) {

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    public void showResponse(Map<String, CTreeModel> response) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showResponseMessage(String message) {

    }

    /* getters and setters */
    @Override
    public EditText getEmailEditText() {
        return emailEditText;
    }

    @Override
    public EditText getPasswordEditText() {
        return passwordEditText;
    }

    @Override
    public EditText getConfirmPasswordEditText() {
        return confirmPasswordEditText;
    }

    @Override
    public String getResourceString(int resourceID) {
        return getString(resourceID);
    }
}
