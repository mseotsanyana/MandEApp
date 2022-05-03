package com.me.mseotsanyana.mande.PL.ui.fragments.monitoring;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;

public class cMonitoringPlanFragmentDirections {
  private cMonitoringPlanFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionCMonitoringPlanFragmentToCMonitoringFormFragment() {
    return new ActionOnlyNavDirections(R.id.action_cMonitoringPlanFragment_to_cMonitoringFormFragment);
  }
}
