package com.me.mseotsanyana.mande.PL.ui.fragments.programme;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;

public class cImpactFragmentDirections {
  private cImpactFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionCImpactFragmentToCImpactDetailFragment() {
    return new ActionOnlyNavDirections(R.id.action_cImpactFragment_to_cImpactDetailFragment);
  }
}
