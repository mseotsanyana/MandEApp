package com.me.mseotsanyana.mande.PL.ui.fragments.programme;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cLogFrameModel;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class cInputFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private cInputFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private cInputFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static cInputFragmentArgs fromBundle(@NonNull Bundle bundle) {
    cInputFragmentArgs __result = new cInputFragmentArgs();
    bundle.setClassLoader(cInputFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("logFrameModel")) {
      cLogFrameModel logFrameModel;
      if (Parcelable.class.isAssignableFrom(cLogFrameModel.class) || Serializable.class.isAssignableFrom(cLogFrameModel.class)) {
        logFrameModel = (cLogFrameModel) bundle.get("logFrameModel");
      } else {
        throw new UnsupportedOperationException(cLogFrameModel.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("logFrameModel", logFrameModel);
    } else {
      throw new IllegalArgumentException("Required argument \"logFrameModel\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static cInputFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    cInputFragmentArgs __result = new cInputFragmentArgs();
    if (savedStateHandle.contains("logFrameModel")) {
      cLogFrameModel logFrameModel;
      logFrameModel = savedStateHandle.get("logFrameModel");
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("logFrameModel", logFrameModel);
    } else {
      throw new IllegalArgumentException("Required argument \"logFrameModel\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public cLogFrameModel getLogFrameModel() {
    return (cLogFrameModel) arguments.get("logFrameModel");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("logFrameModel")) {
      cLogFrameModel logFrameModel = (cLogFrameModel) arguments.get("logFrameModel");
      if (Parcelable.class.isAssignableFrom(cLogFrameModel.class) || logFrameModel == null) {
        __result.putParcelable("logFrameModel", Parcelable.class.cast(logFrameModel));
      } else if (Serializable.class.isAssignableFrom(cLogFrameModel.class)) {
        __result.putSerializable("logFrameModel", Serializable.class.cast(logFrameModel));
      } else {
        throw new UnsupportedOperationException(cLogFrameModel.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("logFrameModel")) {
      cLogFrameModel logFrameModel = (cLogFrameModel) arguments.get("logFrameModel");
      if (Parcelable.class.isAssignableFrom(cLogFrameModel.class) || logFrameModel == null) {
        __result.set("logFrameModel", Parcelable.class.cast(logFrameModel));
      } else if (Serializable.class.isAssignableFrom(cLogFrameModel.class)) {
        __result.set("logFrameModel", Serializable.class.cast(logFrameModel));
      } else {
        throw new UnsupportedOperationException(cLogFrameModel.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
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
    cInputFragmentArgs that = (cInputFragmentArgs) object;
    if (arguments.containsKey("logFrameModel") != that.arguments.containsKey("logFrameModel")) {
      return false;
    }
    if (getLogFrameModel() != null ? !getLogFrameModel().equals(that.getLogFrameModel()) : that.getLogFrameModel() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getLogFrameModel() != null ? getLogFrameModel().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "cInputFragmentArgs{"
        + "logFrameModel=" + getLogFrameModel()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull cInputFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull cLogFrameModel logFrameModel) {
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("logFrameModel", logFrameModel);
    }

    @NonNull
    public cInputFragmentArgs build() {
      cInputFragmentArgs result = new cInputFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setLogFrameModel(@NonNull cLogFrameModel logFrameModel) {
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("logFrameModel", logFrameModel);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public cLogFrameModel getLogFrameModel() {
      return (cLogFrameModel) arguments.get("logFrameModel");
    }
  }
}
