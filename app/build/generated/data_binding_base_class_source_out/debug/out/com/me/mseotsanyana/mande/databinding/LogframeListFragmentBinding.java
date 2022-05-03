// Generated by data binding compiler. Do not edit!
package com.me.mseotsanyana.mande.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.me.mseotsanyana.bmblibrary.DraggableFAB;
import com.me.mseotsanyana.mande.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LogframeListFragmentBinding extends ViewDataBinding {
  @NonNull
  public final DraggableFAB logFrameFAB;

  @NonNull
  public final RecyclerView logFrameRecyclerView;

  protected LogframeListFragmentBinding(Object _bindingComponent, View _root, int _localFieldCount,
      DraggableFAB logFrameFAB, RecyclerView logFrameRecyclerView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.logFrameFAB = logFrameFAB;
    this.logFrameRecyclerView = logFrameRecyclerView;
  }

  @NonNull
  public static LogframeListFragmentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.logframe_list_fragment, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LogframeListFragmentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LogframeListFragmentBinding>inflateInternal(inflater, R.layout.logframe_list_fragment, root, attachToRoot, component);
  }

  @NonNull
  public static LogframeListFragmentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.logframe_list_fragment, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LogframeListFragmentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LogframeListFragmentBinding>inflateInternal(inflater, R.layout.logframe_list_fragment, null, false, component);
  }

  public static LogframeListFragmentBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static LogframeListFragmentBinding bind(@NonNull View view, @Nullable Object component) {
    return (LogframeListFragmentBinding)bind(component, view, R.layout.logframe_list_fragment);
  }
}