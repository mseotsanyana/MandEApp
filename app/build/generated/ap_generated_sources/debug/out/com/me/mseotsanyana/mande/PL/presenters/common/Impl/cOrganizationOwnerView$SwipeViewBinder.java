package com.me.mseotsanyana.mande.PL.presenters.common.Impl;

import android.widget.TextView;
import com.me.mseotsanyana.multiselectspinnerlibrary.CSingleSpinnerSearch;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDecor;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipePlaceHolderView;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeViewBinder;
import java.lang.Override;

@Keep
public class cOrganizationOwnerView$SwipeViewBinder extends cSwipeViewBinder<cOrganizationOwnerView, cSwipePlaceHolderView.FrameView, cSwipePlaceHolderView.SwipeOption, cSwipeDecor> {
  public cOrganizationOwnerView$SwipeViewBinder(cOrganizationOwnerView resolver) {
    super(resolver, R.layout.cOrganizationOwnerView, false);
  }

  @Override
  protected void resolveView(cOrganizationOwnerView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cOrganizationOwnerView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cOrganizationOwnerView resolver = getResolver();
    boolean nullable = isNullable();
    if (resolver != null && nullable) {
      resolver.textViewCaption = null;
      resolver.textViewName = null;
      resolver.textViewDescription = null;
      resolver.singleSpinnerSearchOwners = null;
      setResolver(null);
      setAnimationResolver(null);
    }
  }

  @Override
  protected void bindViewPosition(cOrganizationOwnerView resolver, int position) {
  }

  @Override
  protected void bindViews(cOrganizationOwnerView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
    resolver.textViewCaption = (TextView)itemView.findViewById(R.id.textViewCaption);
    resolver.textViewName = (TextView)itemView.findViewById(R.id.textViewName);
    resolver.textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescription);
    resolver.singleSpinnerSearchOwners = (CSingleSpinnerSearch)itemView.findViewById(R.id.singleSpinnerSearchOwners);
  }

  @Override
  protected void bindClick(final cOrganizationOwnerView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindLongClick(final cOrganizationOwnerView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeView(cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeIn(cOrganizationOwnerView resolver) {
  }

  @Override
  protected void bindSwipeOut(cOrganizationOwnerView resolver) {
  }

  @Override
  protected void bindSwipeInState() {
  }

  @Override
  protected void bindSwipeOutState() {
  }

  @Override
  protected void bindSwipeCancelState() {
  }

  @Override
  protected void bindSwipeHead(cOrganizationOwnerView resolver) {
  }
}
