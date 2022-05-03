package com.me.mseotsanyana.mande.PL.ui.fragments.raid;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;

public class cRAIDLogFragmentDirections {
  private cRAIDLogFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionCRAIDLogFragmentToCRiskRegisterFragment() {
    return new ActionOnlyNavDirections(R.id.action_cRAIDLogFragment_to_cRiskRegisterFragment);
  }

  @NonNull
  public static NavDirections actionCRAIDLogFragmentToCAssumptionRegisterFragment() {
    return new ActionOnlyNavDirections(R.id.action_cRAIDLogFragment_to_cAssumptionRegisterFragment);
  }

  @NonNull
  public static NavDirections actionCRAIDLogFragmentToCIssueRegisterFragment() {
    return new ActionOnlyNavDirections(R.id.action_cRAIDLogFragment_to_cIssueRegisterFragment);
  }

  @NonNull
  public static NavDirections actionCRAIDLogFragmentToCDependencyRegisterFragment() {
    return new ActionOnlyNavDirections(R.id.action_cRAIDLogFragment_to_cDependencyRegisterFragment);
  }
}
