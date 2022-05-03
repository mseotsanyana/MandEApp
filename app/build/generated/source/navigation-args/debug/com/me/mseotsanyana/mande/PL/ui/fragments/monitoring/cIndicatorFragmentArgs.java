package com.me.mseotsanyana.mande.PL.ui.fragments.monitoring;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class cIndicatorFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private cIndicatorFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private cIndicatorFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static cIndicatorFragmentArgs fromBundle(@NonNull Bundle bundle) {
    cIndicatorFragmentArgs __result = new cIndicatorFragmentArgs();
    bundle.setClassLoader(cIndicatorFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("logFrameID")) {
      long logFrameID;
      logFrameID = bundle.getLong("logFrameID");
      __result.arguments.put("logFrameID", logFrameID);
    } else {
      throw new IllegalArgumentException("Required argument \"logFrameID\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static cIndicatorFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    cIndicatorFragmentArgs __result = new cIndicatorFragmentArgs();
    if (savedStateHandle.contains("logFrameID")) {
      long logFrameID;
      logFrameID = savedStateHandle.get("logFrameID");
      __result.arguments.put("logFrameID", logFrameID);
    } else {
      throw new IllegalArgumentException("Required argument \"logFrameID\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public long getLogFrameID() {
    return (long) arguments.get("logFrameID");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("logFrameID")) {
      long logFrameID = (long) arguments.get("logFrameID");
      __result.putLong("logFrameID", logFrameID);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("logFrameID")) {
      long logFrameID = (long) arguments.get("logFrameID");
      __result.set("logFrameID", logFrameID);
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
    cIndicatorFragmentArgs that = (cIndicatorFragmentArgs) object;
    if (arguments.containsKey("logFrameID") != that.arguments.containsKey("logFrameID")) {
      return false;
    }
    if (getLogFrameID() != that.getLogFrameID()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (int)(getLogFrameID() ^ (getLogFrameID() >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "cIndicatorFragmentArgs{"
        + "logFrameID=" + getLogFrameID()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull cIndicatorFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    public cIndicatorFragmentArgs build() {
      cIndicatorFragmentArgs result = new cIndicatorFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setLogFrameID(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    public long getLogFrameID() {
      return (long) arguments.get("logFrameID");
    }
  }
}
