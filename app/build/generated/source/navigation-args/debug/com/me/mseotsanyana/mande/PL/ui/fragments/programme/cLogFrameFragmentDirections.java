package com.me.mseotsanyana.mande.PL.ui.fragments.programme;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.R;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class cLogFrameFragmentDirections {
  private cLogFrameFragmentDirections() {
  }

  @NonNull
  public static ActionCLogFrameFragmentToCImpactFragment actionCLogFrameFragmentToCImpactFragment(
      @NonNull cLogFrameModel logFrameModel) {
    return new ActionCLogFrameFragmentToCImpactFragment(logFrameModel);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCOutcomeFragment actionCLogFrameFragmentToCOutcomeFragment(
      @NonNull cLogFrameModel logFrameModel) {
    return new ActionCLogFrameFragmentToCOutcomeFragment(logFrameModel);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCOutputFragment actionCLogFrameFragmentToCOutputFragment(
      @NonNull cLogFrameModel logFrameModel) {
    return new ActionCLogFrameFragmentToCOutputFragment(logFrameModel);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCInputFragment actionCLogFrameFragmentToCInputFragment(
      @NonNull cLogFrameModel logFrameModel) {
    return new ActionCLogFrameFragmentToCInputFragment(logFrameModel);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCActivityFragment actionCLogFrameFragmentToCActivityFragment(
      @NonNull cLogFrameModel logFrameModel) {
    return new ActionCLogFrameFragmentToCActivityFragment(logFrameModel);
  }

  public static class ActionCLogFrameFragmentToCImpactFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCImpactFragment(@NonNull cLogFrameModel logFrameModel) {
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("logFrameModel", logFrameModel);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCImpactFragment setLogFrameModel(
        @NonNull cLogFrameModel logFrameModel) {
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("logFrameModel", logFrameModel);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.action_cLogFrameFragment_to_cImpactFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public cLogFrameModel getLogFrameModel() {
      return (cLogFrameModel) arguments.get("logFrameModel");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionCLogFrameFragmentToCImpactFragment that = (ActionCLogFrameFragmentToCImpactFragment) object;
      if (arguments.containsKey("logFrameModel") != that.arguments.containsKey("logFrameModel")) {
        return false;
      }
      if (getLogFrameModel() != null ? !getLogFrameModel().equals(that.getLogFrameModel()) : that.getLogFrameModel() != null) {
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
      result = 31 * result + (getLogFrameModel() != null ? getLogFrameModel().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionCLogFrameFragmentToCImpactFragment(actionId=" + getActionId() + "){"
          + "logFrameModel=" + getLogFrameModel()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCOutcomeFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCOutcomeFragment(@NonNull cLogFrameModel logFrameModel) {
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("logFrameModel", logFrameModel);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCOutcomeFragment setLogFrameModel(
        @NonNull cLogFrameModel logFrameModel) {
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("logFrameModel", logFrameModel);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.action_cLogFrameFragment_to_cOutcomeFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public cLogFrameModel getLogFrameModel() {
      return (cLogFrameModel) arguments.get("logFrameModel");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionCLogFrameFragmentToCOutcomeFragment that = (ActionCLogFrameFragmentToCOutcomeFragment) object;
      if (arguments.containsKey("logFrameModel") != that.arguments.containsKey("logFrameModel")) {
        return false;
      }
      if (getLogFrameModel() != null ? !getLogFrameModel().equals(that.getLogFrameModel()) : that.getLogFrameModel() != null) {
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
      result = 31 * result + (getLogFrameModel() != null ? getLogFrameModel().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionCLogFrameFragmentToCOutcomeFragment(actionId=" + getActionId() + "){"
          + "logFrameModel=" + getLogFrameModel()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCOutputFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCOutputFragment(@NonNull cLogFrameModel logFrameModel) {
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("logFrameModel", logFrameModel);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCOutputFragment setLogFrameModel(
        @NonNull cLogFrameModel logFrameModel) {
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("logFrameModel", logFrameModel);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.action_cLogFrameFragment_to_cOutputFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public cLogFrameModel getLogFrameModel() {
      return (cLogFrameModel) arguments.get("logFrameModel");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionCLogFrameFragmentToCOutputFragment that = (ActionCLogFrameFragmentToCOutputFragment) object;
      if (arguments.containsKey("logFrameModel") != that.arguments.containsKey("logFrameModel")) {
        return false;
      }
      if (getLogFrameModel() != null ? !getLogFrameModel().equals(that.getLogFrameModel()) : that.getLogFrameModel() != null) {
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
      result = 31 * result + (getLogFrameModel() != null ? getLogFrameModel().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionCLogFrameFragmentToCOutputFragment(actionId=" + getActionId() + "){"
          + "logFrameModel=" + getLogFrameModel()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCInputFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCInputFragment(@NonNull cLogFrameModel logFrameModel) {
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("logFrameModel", logFrameModel);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCInputFragment setLogFrameModel(
        @NonNull cLogFrameModel logFrameModel) {
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("logFrameModel", logFrameModel);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.action_cLogFrameFragment_to_cInputFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public cLogFrameModel getLogFrameModel() {
      return (cLogFrameModel) arguments.get("logFrameModel");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionCLogFrameFragmentToCInputFragment that = (ActionCLogFrameFragmentToCInputFragment) object;
      if (arguments.containsKey("logFrameModel") != that.arguments.containsKey("logFrameModel")) {
        return false;
      }
      if (getLogFrameModel() != null ? !getLogFrameModel().equals(that.getLogFrameModel()) : that.getLogFrameModel() != null) {
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
      result = 31 * result + (getLogFrameModel() != null ? getLogFrameModel().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionCLogFrameFragmentToCInputFragment(actionId=" + getActionId() + "){"
          + "logFrameModel=" + getLogFrameModel()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCActivityFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCActivityFragment(@NonNull cLogFrameModel logFrameModel) {
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("logFrameModel", logFrameModel);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCActivityFragment setLogFrameModel(
        @NonNull cLogFrameModel logFrameModel) {
      if (logFrameModel == null) {
        throw new IllegalArgumentException("Argument \"logFrameModel\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("logFrameModel", logFrameModel);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.action_cLogFrameFragment_to_cActivityFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public cLogFrameModel getLogFrameModel() {
      return (cLogFrameModel) arguments.get("logFrameModel");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionCLogFrameFragmentToCActivityFragment that = (ActionCLogFrameFragmentToCActivityFragment) object;
      if (arguments.containsKey("logFrameModel") != that.arguments.containsKey("logFrameModel")) {
        return false;
      }
      if (getLogFrameModel() != null ? !getLogFrameModel().equals(that.getLogFrameModel()) : that.getLogFrameModel() != null) {
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
      result = 31 * result + (getLogFrameModel() != null ? getLogFrameModel().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionCLogFrameFragmentToCActivityFragment(actionId=" + getActionId() + "){"
          + "logFrameModel=" + getLogFrameModel()
          + "}";
    }
  }
}
