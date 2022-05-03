package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.me.mseotsanyana.bmblibrary.CBoomMenuButton;
import com.me.mseotsanyana.expandablelayoutlibrary.CExpandableLayout;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandableViewBinder;
import java.lang.Deprecated;
import java.lang.Override;

@Keep
public class cInputBodyView$cExpandableViewBinder extends cExpandableViewBinder<cInputBodyView, View> {
  public cInputBodyView$cExpandableViewBinder(cInputBodyView resolver) {
    super(resolver, R.layout.cInputBodyView, false, false, false);
  }

  @Override
  protected void resolveView(cInputBodyView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cInputBodyView resolver = getResolver();
  }

  @Deprecated
  @Override
  protected void unbind() {
  }

  @Deprecated
  @Override
  protected void bindAnimation(int deviceWidth, int deviceHeight, View view) {
  }

  @Override
  protected void bindViewPosition(cInputBodyView resolver, int position) {
  }

  @Override
  protected void bindViews(cInputBodyView resolver, View itemView) {
    resolver.cardView = (CardView)itemView.findViewById(R.id.cardView);
    resolver.textViewActivityCaption = (TextView)itemView.findViewById(R.id.textViewActivityCaption);
    resolver.textViewActivity = (TextView)itemView.findViewById(R.id.textViewActivity);
    resolver.textViewInputCaption = (TextView)itemView.findViewById(R.id.textViewInputCaption);
    resolver.textViewInput = (TextView)itemView.findViewById(R.id.textViewInput);
    resolver.textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescription);
    resolver.textViewStartDate = (TextView)itemView.findViewById(R.id.textViewStartDate);
    resolver.textViewEndDate = (TextView)itemView.findViewById(R.id.textViewEndDate);
    resolver.expandableLayout = (CExpandableLayout)itemView.findViewById(R.id.expandableLayout);
    resolver.textViewDetailIcon = (TextView)itemView.findViewById(R.id.textViewDetailIcon);
    resolver.textViewUpdateIcon = (TextView)itemView.findViewById(R.id.textViewUpdateIcon);
    resolver.textViewDeleteIcon = (TextView)itemView.findViewById(R.id.textViewDeleteIcon);
    resolver.textViewSyncIcon = (TextView)itemView.findViewById(R.id.textViewSyncIcon);
    resolver.bmbMenu = (CBoomMenuButton)itemView.findViewById(R.id.bmbMenu);
  }

  @Override
  protected void bindClick(final cInputBodyView resolver, View itemView) {
    itemView.findViewById(R.id.onDetailIconClick).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resolver.onDetailIconClick();
      }
    });
    itemView.findViewById(R.id.onUpdateIconClick).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resolver.onUpdateIconClick();
      }
    });
    itemView.findViewById(R.id.onDeleteIconClick).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resolver.onDeleteIconClick();
      }
    });
    itemView.findViewById(R.id.onSyncIconClick).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resolver.onSyncIconClick();
      }
    });
  }

  @Override
  protected void bindLongClick(final cInputBodyView resolver, View itemView) {
  }

  @Override
  protected void bindParentPosition(int position) {
    getResolver().parentPosition = position;
    setParentPosition(position);
  }

  @Override
  protected void bindChildPosition(int position) {
    getResolver().childPosition = position;
    setChildPosition(position);
  }

  @Override
  protected void bindToggle(final cInputBodyView resolver, View itemView) {
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isExpanded()) collapse();
        else expand();
      }
    });
  }

  @Override
  protected void bindExpand(cInputBodyView resolver) {
  }

  @Override
  protected void bindCollapse(cInputBodyView resolver) {
  }
}
