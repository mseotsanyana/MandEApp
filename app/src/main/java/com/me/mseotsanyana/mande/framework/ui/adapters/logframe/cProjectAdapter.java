package com.me.mseotsanyana.mande.framework.ui.adapters.logframe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.me.mseotsanyana.mande.domain.entities.models.logframe.cProjectModel;
import com.me.mseotsanyana.mande.infrastructure.ports.logframe.iProjectPresenter;
import com.me.mseotsanyana.mande.OLD.PL.ui.listeners.logframe.iViewProjectListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.OLD.cConstant;
import com.me.mseotsanyana.mande.framework.utils.CFontManager;
import com.me.mseotsanyana.mande.databinding.ProjectChildCardviewBinding;
import com.me.mseotsanyana.mande.databinding.ProjectParentCardviewBinding;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cProjectAdapter extends cTreeAdapter implements iViewProjectListener,
        Filterable {
    private static final String TAG = cProjectAdapter.class.getSimpleName();
    private static final SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PROJECT = 0;
    private static final int PROJECT_TABS = 1;

    private List<cTreeModel> filteredTreeModels;

    private LayoutInflater layoutInflater;

    private final iProjectPresenter.View projectPresenterView;
    private final iCommonCallback commonCallback;

    public cProjectAdapter(Context context, iCommonCallback commonCallback,
                           iProjectPresenter.View projectPresenterView,
                           List<cTreeModel> projectTree, int expLevel) {
        super(context, projectTree, expLevel);
        this.projectPresenterView = projectPresenterView;
        this.commonCallback = commonCallback;
    }

    public interface iCommonCallback {
        void onCreateViewPager2Adapter(ProjectParentCardviewBinding parentBinding,
                                       cProjectModel projectModel);
        void onDestroyViewPager2Adapter(ProjectParentCardviewBinding parentBinding);
    }


    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case PROJECT:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }
                ProjectParentCardviewBinding parentBinding;
                parentBinding = DataBindingUtil.inflate(layoutInflater,
                        R.layout.project_parent_cardview, parent, false);

                viewHolder = new cProjectParentViewHolder(parentBinding, this);
                cProjectParentViewHolder PPH = (cProjectParentViewHolder) viewHolder;

                // set listener on expand icon - toggling with a header
                PPH.binding.textViewExpandIcon.setOnClickListener(v -> {
                    int position = PPH.getAbsoluteAdapterPosition();
                    expandOrCollapse(position);
                });

                // set listener on detail icon

                /* the collapse and expansion of the impact */
                PPH.binding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                PPH.binding.textViewDetailIcon.setTypeface(CFontManager.getTypeface(context,
                        CFontManager.FONTAWESOME));
                PPH.binding.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                PPH.binding.textViewDetailIcon.setText(
                        context.getResources().getString(R.string.fa_angle_down));

                PPH.binding.textViewDetailIcon.setOnClickListener(v -> {
                    if (!(PPH.binding.expandableLayout.isExpanded())) {
                        PPH.binding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_down));

                        // initialise and register callback on page change
                        int position = PPH.getAbsoluteAdapterPosition();
                        //PPH.binding.projectViewPager2.meViewPager2.setOffscreenPageLimit(1);
                        PPH.projectListener.onRegisterProjectViewPager2(PPH.binding, position);

                    } else {
                        PPH.binding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_up));

                        // unregister callback on page change
                        PPH.projectListener.onUnRegisterProjectViewPager2(PPH.binding);
                    }
                    PPH.binding.expandableLayout.toggle();
                });

                /* icon for accessing a logframe */
                PPH.binding.textViewLogframeIcon.setOnClickListener(v -> {
                    int position = PPH.getAbsoluteAdapterPosition();
                    PPH.projectListener.onClickReadLogFrame(position);
                });

                /* icon for deleting a record */
                PPH.binding.textViewDeleteIcon.setOnClickListener(v -> {
                    //IPH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrameModel.getLogFrameID());
                });

                /* icon for updating a record */
                PPH.binding.textViewUpdateIcon.setOnClickListener(v -> {
                    //IPH.logFrameListener.onClickUpdateLogFrame(position,parentLogFrameModel);
                });

                /* icon for creating a record */
                PPH.binding.textViewCreateIcon.setOnClickListener(v -> {
                    //IPH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrameModel.getLogFrameID());
                });

                break;

            case PROJECT_TABS:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }
                ProjectChildCardviewBinding childBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.project_child_cardview, parent,
                        false);

                viewHolder = new cProjectChildViewHolder(childBinding);
                cProjectChildViewHolder PCH = (cProjectChildViewHolder) viewHolder;

                break;
            default:
                viewHolder = null;
                break;
        }

        return viewHolder;
    }

//    public void refreshViewPager2Adapter(ViewPager2 viewPager2, Fragment fragment,
//                                         CExpandableLayout expandableLayout) {
//
//        //Toast.makeText(context, "POS = " + position, Toast.LENGTH_SHORT).show();
//        // change the view pager only if the expandable layout is true
//        //Fragment fragment = melViewPagerAdapter.getPageFragment(position);
//        View childView = fragment.getView();
//
//        if (childView == null) return;
//
//        int wMeasureSpec = View.MeasureSpec.makeMeasureSpec(
//                childView.getWidth(), View.MeasureSpec.EXACTLY);
//        int hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0,
//                View.MeasureSpec.UNSPECIFIED);
//        childView.measure(wMeasureSpec, hMeasureSpec);
//
//        int height = viewPager2.getLayoutParams().height;
//        if (height != childView.getMeasuredHeight()) {
//            ViewGroup.LayoutParams lp;
//            lp = viewPager2.getLayoutParams();
//            lp.height = childView.getMeasuredHeight() + 120;
//
//            // expand the hidden layer with the specified height
//            expandableLayout.expand(lp.height);
//            //melViewPagerAdapter.notifyItemChanged(position);
//        }
//    }


    @SuppressLint("SetTextI18n")
    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        cNode node = visibleNodes.get(position);
        cTreeModel obj = (cTreeModel) node.getObj();

        if (obj != null) {
            switch (obj.getType()) {
                case PROJECT:
                    cProjectModel parentProject = (cProjectModel) obj.getModelObject();
                    cProjectParentViewHolder PPH = ((cProjectParentViewHolder) viewHolder);

                    PPH.setPaddingLeft(20 * node.getLevel());

                    PPH.binding.textViewName.setText(parentProject.getName() + " (" +
                            parentProject.getCode() + ")");
                    PPH.binding.textViewDescription.setText(parentProject.getDescription());
                    PPH.binding.textViewStartDateCaption.setText(
                            context.getResources().getString(R.string.startdate_caption));
                    PPH.binding.textViewStartDate.setText(sdf.format(parentProject.getStartDate()));
                    PPH.binding.textViewEndDateCaption.setText(
                            context.getResources().getString(R.string.enddate_caption));
                    PPH.binding.textViewEndDate.setText(sdf.format(parentProject.getEndDate()));
                    PPH.binding.textViewLocationCaption.setText(
                            context.getResources().getString(R.string.location));
                    PPH.binding.textViewLocation.setText(parentProject.getLocation());

                    /* the collapse and expansion of the parent logframe */
                    if (node.isLeaf()) {
                        PPH.binding.textViewExpandIcon.setVisibility(View.GONE);
                    } else {

                        PPH.binding.textViewExpandIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            PPH.binding.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            PPH.binding.textViewExpandIcon.setTypeface(
                                    CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                            PPH.binding.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));

                        } else {
                            PPH.binding.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            PPH.binding.textViewExpandIcon.setTypeface(
                                    CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                            PPH.binding.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }

                    /* icon for accessing a logframe */
                    PPH.binding.textViewLogframeIcon.setTypeface(null, Typeface.NORMAL);
                    PPH.binding.textViewLogframeIcon.setTypeface(
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    PPH.binding.textViewLogframeIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PPH.binding.textViewLogframeIcon.setText(context.getResources().getString(R.string.fa_logframe));

                    /* icon for deleting a record */
                    PPH.binding.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    PPH.binding.textViewDeleteIcon.setTypeface(
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    PPH.binding.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PPH.binding.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));

                    /* icon for saving updated record */
                    PPH.binding.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    PPH.binding.textViewUpdateIcon.setTypeface(
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    PPH.binding.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PPH.binding.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));

                    /* icon for create subproject record */
                    PPH.binding.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
                    PPH.binding.textViewCreateIcon.setTypeface(
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    PPH.binding.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PPH.binding.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_plus));

                    break;

                case PROJECT_TABS:
                    cProjectModel childProject = (cProjectModel) obj.getModelObject();
                    cProjectChildViewHolder ICH = ((cProjectChildViewHolder) viewHolder);

                    ICH.setPaddingLeft(20 * node.getLevel());

                    ICH.binding.textViewName.setText(childProject.getName() + " (" +
                            childProject.getCode() + ")");
                    ICH.binding.textViewDescription.setText(childProject.getDescription());
                    ICH.binding.textViewStartDateCaption.setText(
                            context.getResources().getString(R.string.startdate_caption));
                    ICH.binding.textViewStartDate.setText(sdf.format(childProject.getStartDate()));
                    ICH.binding.textViewEndDateCaption.setText(
                            context.getResources().getString(R.string.enddate_caption));
                    ICH.binding.textViewEndDate.setText(sdf.format(childProject.getEndDate()));
                    ICH.binding.textViewLocationCaption.setText(
                            context.getResources().getString(R.string.location));
                    ICH.binding.textViewLocation.setText(childProject.getLocation());

                    /* the collapse and expansion of the impact */
                    ICH.binding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    ICH.binding.textViewDetailIcon.setTypeface(CFontManager.getTypeface(context,
                            CFontManager.FONTAWESOME));
                    ICH.binding.textViewDetailIcon.setTextColor(context.getColor(R.color.black));
                    ICH.binding.textViewDetailIcon.setText(
                            context.getResources().getString(R.string.fa_angle_down));

                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + obj.getType());
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredTreeModels = getTreeModel();
                } else {

                    ArrayList<cTreeModel> filteredList = new ArrayList<>();
                    for (cTreeModel treeModel : getTreeModel()) {
                        if (((cProjectModel) treeModel.getModelObject()).getName().toLowerCase().
                                contains(charString.toLowerCase())) {
                            filteredList.add(treeModel);
                        }
                    }

                    filteredTreeModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredTreeModels.size();
                filterResults.values = filteredTreeModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //assert (ArrayList<cTreeModel>) filterResults.values != null;

                try {
                    filteredTreeModels = (ArrayList<cTreeModel>) filterResults.values;
                    notifyTreeModelChanged(filteredTreeModels);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onClickReadLogFrame(int position) {
        cNode node = visibleNodes.get(position);
        cTreeModel obj = (cTreeModel) node.getObj();
        cProjectModel projectModel = (cProjectModel) obj.getModelObject();
        projectPresenterView.onClickLogframe(projectModel.getProjectServerID());
    }

    @Override
    public void onRegisterProjectViewPager2(ProjectParentCardviewBinding parentBinding, int position) {
        cNode node = visibleNodes.get(position);
        cTreeModel obj = (cTreeModel) node.getObj();
        cProjectModel projectModel = (cProjectModel) obj.getModelObject();
        //parentBinding.projectViewPager2.meViewPager2.setOffscreenPageLimit(1);
        commonCallback.onCreateViewPager2Adapter(parentBinding, projectModel);
    }

    @Override
    public void onUnRegisterProjectViewPager2(ProjectParentCardviewBinding parentBinding) {
        commonCallback.onDestroyViewPager2Adapter(parentBinding);
    }

    public static class cProjectParentViewHolder extends cTreeViewHolder {
        private final ProjectParentCardviewBinding binding;
        private final View treeView;
        private final iViewProjectListener projectListener;


        //private final TabLayout meTabLayout;
        //private final ViewPager2 meViewPager2;
        //private final TextView textViewModifiedDate;
        //private final TextView textViewCreatedDate;

        private cProjectParentViewHolder(final ProjectParentCardviewBinding binding,
                                         iViewProjectListener projectListener) {
            super(binding.getRoot());

            this.binding = binding;
            this.treeView = binding.getRoot();
            this.projectListener = projectListener;

            // common attributes
            //meTabLayout = treeView.findViewById(R.id.meTabLayout);
            //meViewPager2 = treeView.findViewById(R.id.meViewPager2);

            //textViewCreatedDate = treeView.findViewById(R.id.textViewCreatedDate);
            //textViewModifiedDate = treeView.findViewById(R.id.textViewModifiedDate);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cProjectChildViewHolder extends cTreeViewHolder {
        private final ProjectChildCardviewBinding binding;

        private final CardView cardView;
        private final TabLayout meTabLayout;
        private final ViewPager2 meViewPager2;
        private final View treeView;

        private cProjectChildViewHolder(final ProjectChildCardviewBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
            this.treeView = binding.getRoot();

            this.cardView = treeView.findViewById(R.id.cardView);
            this.meTabLayout = treeView.findViewById(R.id.meTabLayout);
            this.meViewPager2 = treeView.findViewById(R.id.meViewPager2);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
