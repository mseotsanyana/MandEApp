// Generated by data binding compiler. Do not edit!
package com.me.mseotsanyana.mande.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.me.mseotsanyana.mande.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class SessionOrganizationCardviewBinding extends ViewDataBinding {
  @NonNull
  public final CardView cardView;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final LinearLayout linearLayoutEmail;

  @NonNull
  public final LinearLayout linearLayoutName;

  @NonNull
  public final LinearLayout linearLayoutWebsite;

  @NonNull
  public final AppCompatTextView textViewCreateIcon;

  @NonNull
  public final AppCompatTextView textViewDeleteIcon;

  @NonNull
  public final AppCompatTextView textViewDetailIcon;

  @NonNull
  public final AppCompatTextView textViewEmail;

  @NonNull
  public final AppCompatTextView textViewEmailIcon;

  @NonNull
  public final AppCompatTextView textViewJoinIcon;

  @NonNull
  public final AppCompatTextView textViewName;

  @NonNull
  public final AppCompatTextView textViewOrganizationIcon;

  @NonNull
  public final AppCompatTextView textViewUpdateIcon;

  @NonNull
  public final AppCompatTextView textViewWebsite;

  @NonNull
  public final AppCompatTextView textViewWebsiteIcon;

  protected SessionOrganizationCardviewBinding(Object _bindingComponent, View _root,
      int _localFieldCount, CardView cardView, LinearLayout linearLayout,
      LinearLayout linearLayoutEmail, LinearLayout linearLayoutName,
      LinearLayout linearLayoutWebsite, AppCompatTextView textViewCreateIcon,
      AppCompatTextView textViewDeleteIcon, AppCompatTextView textViewDetailIcon,
      AppCompatTextView textViewEmail, AppCompatTextView textViewEmailIcon,
      AppCompatTextView textViewJoinIcon, AppCompatTextView textViewName,
      AppCompatTextView textViewOrganizationIcon, AppCompatTextView textViewUpdateIcon,
      AppCompatTextView textViewWebsite, AppCompatTextView textViewWebsiteIcon) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cardView = cardView;
    this.linearLayout = linearLayout;
    this.linearLayoutEmail = linearLayoutEmail;
    this.linearLayoutName = linearLayoutName;
    this.linearLayoutWebsite = linearLayoutWebsite;
    this.textViewCreateIcon = textViewCreateIcon;
    this.textViewDeleteIcon = textViewDeleteIcon;
    this.textViewDetailIcon = textViewDetailIcon;
    this.textViewEmail = textViewEmail;
    this.textViewEmailIcon = textViewEmailIcon;
    this.textViewJoinIcon = textViewJoinIcon;
    this.textViewName = textViewName;
    this.textViewOrganizationIcon = textViewOrganizationIcon;
    this.textViewUpdateIcon = textViewUpdateIcon;
    this.textViewWebsite = textViewWebsite;
    this.textViewWebsiteIcon = textViewWebsiteIcon;
  }

  @NonNull
  public static SessionOrganizationCardviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.session_organization_cardview, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static SessionOrganizationCardviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<SessionOrganizationCardviewBinding>inflateInternal(inflater, R.layout.session_organization_cardview, root, attachToRoot, component);
  }

  @NonNull
  public static SessionOrganizationCardviewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.session_organization_cardview, null, false, component)
   */
  @NonNull
  @Deprecated
  public static SessionOrganizationCardviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<SessionOrganizationCardviewBinding>inflateInternal(inflater, R.layout.session_organization_cardview, null, false, component);
  }

  public static SessionOrganizationCardviewBinding bind(@NonNull View view) {
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
  public static SessionOrganizationCardviewBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (SessionOrganizationCardviewBinding)bind(component, view, R.layout.session_organization_cardview);
  }
}