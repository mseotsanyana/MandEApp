package com.me.mseotsanyana.mande.PL.ui.fragments.learning;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;

public class cLearningPlanFragmentDirections {
  private cLearningPlanFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionCLearningPlanFragmentToCLearningFormFragment() {
    return new ActionOnlyNavDirections(R.id.action_cLearningPlanFragment_to_cLearningFormFragment);
  }
}
