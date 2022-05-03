package com.me.mseotsanyana.mande.PL.ui.fragments.evaluation;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.questionnairelibrary.forms.db.cDBQuestionnaire;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class cEvaluationPlanFragmentDirections {
  private cEvaluationPlanFragmentDirections() {
  }

  @NonNull
  public static ActionCEvaluationPlanFragmentToCEvaluationFormFragment actionCEvaluationPlanFragmentToCEvaluationFormFragment(
      @NonNull cDBQuestionnaire dbQuestionnaire) {
    return new ActionCEvaluationPlanFragmentToCEvaluationFormFragment(dbQuestionnaire);
  }

  public static class ActionCEvaluationPlanFragmentToCEvaluationFormFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCEvaluationPlanFragmentToCEvaluationFormFragment(
        @NonNull cDBQuestionnaire dbQuestionnaire) {
      if (dbQuestionnaire == null) {
        throw new IllegalArgumentException("Argument \"dbQuestionnaire\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("dbQuestionnaire", dbQuestionnaire);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCEvaluationPlanFragmentToCEvaluationFormFragment setDbQuestionnaire(
        @NonNull cDBQuestionnaire dbQuestionnaire) {
      if (dbQuestionnaire == null) {
        throw new IllegalArgumentException("Argument \"dbQuestionnaire\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("dbQuestionnaire", dbQuestionnaire);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("dbQuestionnaire")) {
        cDBQuestionnaire dbQuestionnaire = (cDBQuestionnaire) arguments.get("dbQuestionnaire");
        if (Parcelable.class.isAssignableFrom(cDBQuestionnaire.class) || dbQuestionnaire == null) {
          __result.putParcelable("dbQuestionnaire", Parcelable.class.cast(dbQuestionnaire));
        } else if (Serializable.class.isAssignableFrom(cDBQuestionnaire.class)) {
          __result.putSerializable("dbQuestionnaire", Serializable.class.cast(dbQuestionnaire));
        } else {
          throw new UnsupportedOperationException(cDBQuestionnaire.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
        }
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_cEvaluationPlanFragment_to_cEvaluationFormFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public cDBQuestionnaire getDbQuestionnaire() {
      return (cDBQuestionnaire) arguments.get("dbQuestionnaire");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionCEvaluationPlanFragmentToCEvaluationFormFragment that = (ActionCEvaluationPlanFragmentToCEvaluationFormFragment) object;
      if (arguments.containsKey("dbQuestionnaire") != that.arguments.containsKey("dbQuestionnaire")) {
        return false;
      }
      if (getDbQuestionnaire() != null ? !getDbQuestionnaire().equals(that.getDbQuestionnaire()) : that.getDbQuestionnaire() != null) {
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
      result = 31 * result + (getDbQuestionnaire() != null ? getDbQuestionnaire().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionCEvaluationPlanFragmentToCEvaluationFormFragment(actionId=" + getActionId() + "){"
          + "dbQuestionnaire=" + getDbQuestionnaire()
          + "}";
    }
  }
}
