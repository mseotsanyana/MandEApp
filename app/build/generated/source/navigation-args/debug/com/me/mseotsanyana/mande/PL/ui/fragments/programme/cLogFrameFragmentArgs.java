package com.me.mseotsanyana.mande.PL.ui.fragments.programme;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class cLogFrameFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private cLogFrameFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private cLogFrameFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static cLogFrameFragmentArgs fromBundle(@NonNull Bundle bundle) {
    cLogFrameFragmentArgs __result = new cLogFrameFragmentArgs();
    bundle.setClassLoader(cLogFrameFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("projectServerID")) {
      String projectServerID;
      projectServerID = bundle.getString("projectServerID");
      __result.arguments.put("projectServerID", projectServerID);
    } else {
      throw new IllegalArgumentException("Required argument \"projectServerID\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static cLogFrameFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    cLogFrameFragmentArgs __result = new cLogFrameFragmentArgs();
    if (savedStateHandle.contains("projectServerID")) {
      String projectServerID;
      projectServerID = savedStateHandle.get("projectServerID");
      __result.arguments.put("projectServerID", projectServerID);
    } else {
      throw new IllegalArgumentException("Required argument \"projectServerID\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public String getProjectServerID() {
    return (String) arguments.get("projectServerID");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("projectServerID")) {
      String projectServerID = (String) arguments.get("projectServerID");
      __result.putString("projectServerID", projectServerID);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("projectServerID")) {
      String projectServerID = (String) arguments.get("projectServerID");
      __result.set("projectServerID", projectServerID);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    cLogFrameFragmentArgs that = (cLogFrameFragmentArgs) object;
    if (arguments.containsKey("projectServerID") != that.arguments.containsKey("projectServerID")) {
      return false;
    }
    if (getProjectServerID() != null ? !getProjectServerID().equals(that.getProjectServerID()) : that.getProjectServerID() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getProjectServerID() != null ? getProjectServerID().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "cLogFrameFragmentArgs{"
        + "projectServerID=" + getProjectServerID()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull cLogFrameFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@Nullable String projectServerID) {
      this.arguments.put("projectServerID", projectServerID);
    }

    @NonNull
    public cLogFrameFragmentArgs build() {
      cLogFrameFragmentArgs result = new cLogFrameFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setProjectServerID(@Nullable String projectServerID) {
      this.arguments.put("projectServerID", projectServerID);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @Nullable
    public String getProjectServerID() {
      return (String) arguments.get("projectServerID");
    }
  }
}
