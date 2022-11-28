package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class cHomeFragmentDirections {
  private cHomeFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCMyUserProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cMyUserProfileFragment);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCTeamFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cTeamFragment);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCOrganizationMemberFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cOrganizationMemberFragment);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCTeamRoleFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cTeamRoleFragment);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCMenuFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cMenuFragment);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCPermissionFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cPermissionFragment);
  }

  @NonNull
  public static ActionCHomeFragmentToCLogFrameFragment actionCHomeFragmentToCLogFrameFragment(
      @Nullable String projectServerID) {
    return new ActionCHomeFragmentToCLogFrameFragment(projectServerID);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCUserProfilesFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cUserProfilesFragment);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCMonitoringPlanFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cMonitoringPlanFragment);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCEvaluationPlanFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cEvaluationPlanFragment);
  }

  @NonNull
  public static ActionCHomeFragmentToCRaidLogFragment actionCHomeFragmentToCRaidLogFragment(
      long logFrameID) {
    return new ActionCHomeFragmentToCRaidLogFragment(logFrameID);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCProjectFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cProjectFragment);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCLearningPlanFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cLearningPlanFragment);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCLoginFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cLoginFragment);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCOrganizationFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cOrganizationFragment);
  }

  public static class ActionCHomeFragmentToCLogFrameFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCHomeFragmentToCLogFrameFragment(@Nullable String projectServerID) {
      this.arguments.put("projectServerID", projectServerID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCHomeFragmentToCLogFrameFragment setProjectServerID(
        @Nullable String projectServerID) {
      this.arguments.put("projectServerID", projectServerID);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("projectServerID")) {
        String projectServerID = (String) arguments.get("projectServerID");
        __result.putString("projectServerID", projectServerID);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_cHomeFragment_to_cLogFrameFragment;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public String getProjectServerID() {
      return (String) arguments.get("projectServerID");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionCHomeFragmentToCLogFrameFragment that = (ActionCHomeFragmentToCLogFrameFragment) object;
      if (arguments.containsKey("projectServerID") != that.arguments.containsKey("projectServerID")) {
        return false;
      }
      if (getProjectServerID() != null ? !getProjectServerID().equals(that.getProjectServerID()) : that.getProjectServerID() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getProjectServerID() != null ? getProjectServerID().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionCHomeFragmentToCLogFrameFragment(actionId=" + getActionId() + "){"
          + "projectServerID=" + getProjectServerID()
          + "}";
    }
  }

  public static class ActionCHomeFragmentToCRaidLogFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCHomeFragmentToCRaidLogFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCHomeFragmentToCRaidLogFragment setLogFrameID(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("logFrameID")) {
        long logFrameID = (long) arguments.get("logFrameID");
        __result.putLong("logFrameID", logFrameID);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_cHomeFragment_to_cRaidLogFragment;
    }

    @SuppressWarnings("unchecked")
    public long getLogFrameID() {
      return (long) arguments.get("logFrameID");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionCHomeFragmentToCRaidLogFragment that = (ActionCHomeFragmentToCRaidLogFragment) object;
      if (arguments.containsKey("logFrameID") != that.arguments.containsKey("logFrameID")) {
        return false;
      }
      if (getLogFrameID() != that.getLogFrameID()) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (int)(getLogFrameID() ^ (getLogFrameID() >>> 32));
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionCHomeFragmentToCRaidLogFragment(actionId=" + getActionId() + "){"
          + "logFrameID=" + getLogFrameID()
          + "}";
    }
  }
}
