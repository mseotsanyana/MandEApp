package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;

public class cLoginFragmentDirections {
  private cLoginFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionCLoginFragmentToCSignUpFragment() {
    return new ActionOnlyNavDirections(R.id.action_cLoginFragment_to_cSignUpFragment);
  }

  @NonNull
  public static NavDirections actionCLoginFragmentToCResetPasswordFragment() {
    return new ActionOnlyNavDirections(R.id.action_cLoginFragment_to_cResetPasswordFragment);
  }

  @NonNull
  public static NavDirections actionCLoginFragmentToCOrganizationFragment() {
    return new ActionOnlyNavDirections(R.id.action_cLoginFragment_to_cOrganizationFragment);
  }
}
