package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.me.mseotsanyana.mande.BLL.model.logframe.cProjectModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cMELViewPagerAdapter;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cEntityFragment;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
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

public class cProjectAdapter extends cTreeAdapter implements Filterable {
    //private static final String TAG = cProjectAdapter.class.getSimpleName();
    private static final SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PROJECT      = 0;
    private static final int PROJECT_TABS = 1;

    private List<cTreeModel> filteredTreeModels;

    private LayoutInflater layoutInflater;

    public cProjectAdapter(Context context, List<cTreeModel> projectTree, int expLevel) {
        super(context, projectTree, expLevel);
        this.filteredTreeModels = projectTree;
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case PROJECT:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }
                ProjectParentCardviewBinding parentBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.project_parent_cardview, parent,
                        false);

                viewHolder = new cProjectViewHolder(parentBinding);
                cProjectViewHolder IPH = (cProjectViewHolder) viewHolder;

                // set listener on expand icon - toggling with a header
                IPH.binding.textViewExpandIcon.setOnClickListener(v -> {
                    int position = IPH.getAbsoluteAdapterPosition();
                    expandOrCollapse(position);
                });

                // set listener on detail icon
                IPH.binding.textViewDetailIcon.setOnClickListener(v -> {
                    if (!(IPH.binding.expandableLayout.isExpanded())) {
                        IPH.binding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_up));
                    } else {
                        IPH.binding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_down));
                    }
                    IPH.binding.expandableLayout.toggle();
                });

                /* icon for deleting a record */
                IPH.binding.textViewDeleteIcon.setOnClickListener(v -> {
                    //IPH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrameModel.getLogFrameID());
                });

                /* icon for saving updated record */
                IPH.binding.textViewUpdateIcon.setOnClickListener(v -> {
                    //IPH.logFrameListener.onClickUpdateLogFrame(position,parentLogFrameModel);
                });

                break;
            case PROJECT_TABS:
                view = inflater.inflate(R.layout.me_tablayout_viewpager2, parent,
                        false);
                viewHolder = new cProjectChildrenViewHolder(view);
                break;
            default:
                viewHolder = null;
                break;
        }

        return viewHolder;
    }

    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        cNode node = visibleNodes.get(position);
        cTreeModel obj = (cTreeModel) node.getObj();

        if (obj != null) {
            switch (obj.getType()) {
                case PROJECT:
                    cProjectModel parentImpact = (cProjectModel) obj.getModelObject();
                    cProjectViewHolder IPH = ((cProjectViewHolder) viewHolder);

                    IPH.setPaddingLeft(20 * node.getLevel());

                    IPH.binding.textViewName.setText(parentImpact.getName());
                    IPH.binding.textViewDescription.setText(parentImpact.getDescription());
                    IPH.binding.textViewStartDateCaption.setText(
                            context.getResources().getString(R.string.startdate_caption));
                    IPH.binding.textViewStartDate.setText(sdf.format(parentImpact.getStartDate()));
                    IPH.binding.textViewEndDateCaption.setText(
                            context.getResources().getString(R.string.enddate_caption));
                    IPH.binding.textViewEndDate.setText(sdf.format(parentImpact.getEndDate()));

                    /* the collapse and expansion of the impact */
                    IPH.binding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    IPH.binding.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));
                    IPH.binding.textViewDetailIcon.setTextColor(context.getColor(R.color.black));
                    IPH.binding.textViewDetailIcon.setText(
                            context.getResources().getString(R.string.fa_angle_down));

                    /* the collapse and expansion of the parent logframe */
                    if (node.isLeaf()) {
                        IPH.binding.textViewExpandIcon.setVisibility(View.GONE);
                    } else {

                        IPH.binding.textViewExpandIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            IPH.binding.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            IPH.binding.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            IPH.binding.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            IPH.binding.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            IPH.binding.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            IPH.binding.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }

                    /* icon for deleting a record */
                    IPH.binding.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    IPH.binding.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    IPH.binding.textViewDeleteIcon.setTextColor(context.getColor(R.color.black));
                    IPH.binding.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));

                    /* icon for saving updated record */
                    IPH.binding.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    IPH.binding.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    IPH.binding.textViewUpdateIcon.setTextColor(context.getColor(R.color.black));
                    IPH.binding.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));

                    /* common attributes */
                    IPH.textViewCreatedDate.setText(sdf.format(parentImpact.getCreatedDate()));
                    IPH.textViewModifiedDate.setText(sdf.format(parentImpact.getModifiedDate()));

                    break;

                case PROJECT_TABS:
                    cProjectModel childProject = (cProjectModel) obj.getModelObject();
                    cProjectChildrenViewHolder ICH = ((cProjectChildrenViewHolder) viewHolder);

                    cMELViewPagerAdapter melViewPagerAdapter = new cMELViewPagerAdapter(
                            (AppCompatActivity) context);

                    melViewPagerAdapter.addFrag(cEntityFragment.newInstance(), "documents");
                    //melViewPagerAdapter.addFrag(cCommonAttributeFragment.newInstance(childImpact), "RAID");

                    ICH.meViewPager2.setOffscreenPageLimit(1);

                    ICH.meViewPager2.setAdapter(melViewPagerAdapter);

                    /* setup the tab layout and add tabs to the view pager2 */
                    new TabLayoutMediator(ICH.meTabLayout, ICH.meViewPager2, (tab, pos) ->
                        tab.setText(melViewPagerAdapter.getPageTitle(pos))
                    ).attach();

                    ICH.meViewPager2.registerOnPageChangeCallback(
                            new ViewPager2.OnPageChangeCallback() {
                                @Override
                                public void onPageSelected(int position) {
                                    super.onPageSelected(position);

                                    Fragment fragment = melViewPagerAdapter.getPageFragment(position);
                                    View childView = fragment.getView();

                                    if (childView == null) return;

                                    int wMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                                            childView.getWidth(), View.MeasureSpec.EXACTLY);
                                    int hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0,
                                            View.MeasureSpec.UNSPECIFIED);
                                    childView.measure(wMeasureSpec, hMeasureSpec);

                                    if (ICH.meViewPager2.getLayoutParams().height != childView.getMeasuredHeight()) {
                                        ViewGroup.LayoutParams lp = ICH.meViewPager2.getLayoutParams();
                                        lp.height = childView.getMeasuredHeight();

/*                                        Log.d(TAG, "POS = " + position + " VP2 HEIGHT = " +
                                                ICH.moduleViewPager2.getLayoutParams().height +
                                                " FRAG HEIGHT = " + childView.getMeasuredHeight());*/
                                    }
                                    melViewPagerAdapter.notifyItemChanged(position);
                                }
                            });

                    ICH.setPaddingLeft(20 * node.getLevel());

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

    public static class cProjectViewHolder extends cTreeViewHolder {
        private final ProjectParentCardviewBinding binding;
        private final View treeView;

        private final TextView textViewModifiedDate;
        private final TextView textViewCreatedDate;

        private cProjectViewHolder(final ProjectParentCardviewBinding binding) {

            super(binding.getRoot());

            this.binding = binding;
            this.treeView = binding.getRoot();

            // common attributes
            textViewCreatedDate = treeView.findViewById(R.id.textViewCreatedDate);
            textViewModifiedDate = treeView.findViewById(R.id.textViewModifiedDate);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cProjectChildrenViewHolder extends cTreeViewHolder {
        private final CardView cardView;
        private final TabLayout meTabLayout;
        private final ViewPager2 meViewPager2;
        private final View treeView;

        private cProjectChildrenViewHolder(View treeViewHolder) {
            super(treeViewHolder);
            this.treeView = treeViewHolder;

            this.cardView = treeViewHolder.findViewById(R.id.cardView);
            this.meTabLayout = treeViewHolder.findViewById(R.id.meTabLayout);
            this.meViewPager2 = treeViewHolder.findViewById(R.id.meViewPager2);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
