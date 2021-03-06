package com.me.mseotsanyana.mande.PL.ui.views;

import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDecor;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipePlaceHolderView;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeViewBinder;
import java.lang.Override;

@Keep
public class cInputHeaderView$SwipeViewBinder extends cSwipeViewBinder<cInputHeaderView, cSwipePlaceHolderView.FrameView, cSwipePlaceHolderView.SwipeOption, cSwipeDecor> {
  public cInputHeaderView$SwipeViewBinder(cInputHeaderView resolver) {
    super(resolver, R.layout.cInputHeaderView, false);
  }

  @Override
  protected void resolveView(cInputHeaderView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cInputHeaderView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cInputHeaderView resolver = getResolver();
    boolean nullable = isNullable();
    if (resolver != null && nullable) {
      resolver.textViewHeading = null;
      resolver.toggleIcon = null;
      resolver.toggleView = null;
      setResolver(null);
      setAnimationResolver(null);
    }
  }

  @Override
  protected void bindViewPosition(cInputHeaderView resolver, int position) {
  }

  @Override
  protected void bindViews(cInputHeaderView resolver, cSwipePlaceHolderView.FrameView itemView) {
    resolver.textViewHeading = (TextView)itemView.findViewById(R.id.textViewHeading);
    resolver.toggleIcon = (TextView)itemView.findViewById(R.id.toggleIcon);
  }

  @Override
  protected void bindClick(final cInputHeaderView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindLongClick(final cInputHeaderView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeView(cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeIn(cInputHeaderView resolver) {
  }

  @Override
  protected void bindSwipeOut(cInputHeaderView resolver) {
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
  protected void bindSwipeHead(cInputHeaderView resolver) {
  }
}
