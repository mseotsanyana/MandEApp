package com.me.mseotsanyana.mande.PL.ui.fragments.programme;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class cProjectFragmentDirections {
  private cProjectFragmentDirections() {
  }

  @NonNull
  public static ActionCProjectFragmentToCLogFrameFragment actionCProjectFragmentToCLogFrameFragment(
      @Nullable String projectServerID) {
    return new ActionCProjectFragmentToCLogFrameFragment(projectServerID);
  }

  public static class ActionCProjectFragmentToCLogFrameFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCProjectFragmentToCLogFrameFragment(@Nullable String projectServerID) {
      this.arguments.put("projectServerID", projectServerID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCProjectFragmentToCLogFrameFragment setProjectServerID(
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
      return R.id.action_cProjectFragment_to_cLogFrameFragment;
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
      ActionCProjectFragmentToCLogFrameFragment that = (ActionCProjectFragmentToCLogFrameFragment) object;
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
      return "ActionCProjectFragmentToCLogFrameFragment(actionId=" + getActionId() + "){"
          + "projectServerID=" + getProjectServerID()
          + "}";
    }
  }
}
