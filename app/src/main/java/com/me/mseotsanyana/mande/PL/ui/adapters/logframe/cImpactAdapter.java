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
import com.me.mseotsanyana.mande.BLL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iImpactPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iOutcomePresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cMELViewPagerAdapter;
import com.me.mseotsanyana.mande.PL.ui.fragments.common.cCommonAttributeFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.logframe.cImpactOutcomeFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cEntityFragment;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewImpactListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewOutcomeListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.databinding.ComponentParentCardviewBinding;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cImpactAdapter extends cTreeAdapter implements iViewImpactListener,
        iViewOutcomeListener, Filterable {
    private static final String TAG = cImpactAdapter.class.getSimpleName();
    private static final SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int IMPACT = 0;
    private static final int IMPACT_CHILDREN = 1;

    private final iImpactPresenter.View impactPresenterView;
    private final iOutcomePresenter.View outcomePresenterView;

    private List<cTreeModel> filteredTreeModels;

    private LayoutInflater layoutInflater;

    public cImpactAdapter(Context context, iImpactPresenter.View impactPresenterView,
                          iOutcomePresenter.View outcomePresenterView, List<cTreeModel> impactTree,
                          int expLevel) {
        super(context, impactTree, expLevel);

        this.impactPresenterView = impactPresenterView;
        this.outcomePresenterView = outcomePresenterView;

        this.filteredTreeModels = impactTree;
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case IMPACT:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }
                ComponentParentCardviewBinding parentBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.component_parent_cardview, parent,
                        false);

                viewHolder = new cImpactViewHolder(parentBinding, this);
                cImpactViewHolder IPH = (cImpactViewHolder) viewHolder;

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
                    //IPH.logFrameListener.onClickDeleteLogFrame(position,
                    //       parentLogFrameModel.getLogFrameID());
                });

                /* icon for saving updated record */
                IPH.binding.textViewUpdateIcon.setOnClickListener(v -> {
                    //IPH.logFrameListener.onClickUpdateLogFrame(position,
                    //        parentLogFrameModel);
                });

                break;
            case IMPACT_CHILDREN:
                view = inflater.inflate(R.layout.me_tablayout_viewpager2, parent,
                        false);
                viewHolder = new cImpactChildrenViewHolder(view, this);
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
                case IMPACT:
                    cImpactModel parentImpact = (cImpactModel) obj.getModelObject();
                    cImpactViewHolder IPH = ((cImpactViewHolder) viewHolder);

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

                case IMPACT_CHILDREN:
                    cImpactModel childImpact = (cImpactModel) obj.getModelObject();
                    cImpactChildrenViewHolder ICH = ((cImpactChildrenViewHolder) viewHolder);

                    cMELViewPagerAdapter moduleViewPagerAdapter = new cMELViewPagerAdapter(
                            (AppCompatActivity) context);

                    moduleViewPagerAdapter.addFrag(
                            cEntityFragment.newInstance(), "subimpacts");
                    moduleViewPagerAdapter.addFrag(
                            cImpactOutcomeFragment.newInstance(childImpact.getOutcomeModels()),
                            "outcomes");
                    moduleViewPagerAdapter.addFrag(
                            cImpactOutcomeFragment.newInstance(childImpact.getOutcomeModels()),
                            "questions");
                    moduleViewPagerAdapter.addFrag(
                            cCommonAttributeFragment.newInstance(childImpact), "RAID");

                    ICH.moduleViewPager2.setOffscreenPageLimit(1);

                    ICH.moduleViewPager2.setAdapter(moduleViewPagerAdapter);

                    /* setup the tab layout and add tabs to the view pager2 */
                    new TabLayoutMediator(
                            ICH.moduleTabLayout, ICH.moduleViewPager2, (tab, pos) -> {
                        tab.setText(moduleViewPagerAdapter.getPageTitle(pos));
                    }).attach();

                    ICH.moduleViewPager2.registerOnPageChangeCallback(
                            new ViewPager2.OnPageChangeCallback() {
                                @Override
                                public void onPageSelected(int position) {
                                    super.onPageSelected(position);

                                    Fragment fragment = moduleViewPagerAdapter.getPageFragment(position);
                                    View childView = fragment.getView();

                                    if (childView == null) return;

                                    int wMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                                            childView.getWidth(), View.MeasureSpec.EXACTLY);
                                    int hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0,
                                            View.MeasureSpec.UNSPECIFIED);
                                    childView.measure(wMeasureSpec, hMeasureSpec);

                                    if (ICH.moduleViewPager2.getLayoutParams().height != childView.getMeasuredHeight()) {
                                        ViewGroup.LayoutParams lp = ICH.moduleViewPager2.getLayoutParams();
                                        lp.height = childView.getMeasuredHeight();

//                                        Log.d(TAG, "POS = " + position + " VP2 HEIGHT = " +
//                                                ICH.moduleViewPager2.getLayoutParams().height +
//                                                " FRAG HEIGHT = " + childView.getMeasuredHeight());
                                    }
                                    moduleViewPagerAdapter.notifyItemChanged(position);
                                }
                            });

                    ICH.setPaddingLeft(20 * node.getLevel());

                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + obj.getType());
            }
        }
    }

    /* impact click events */

    @Override
    public void onClickBMBImpact(int index, long impactID) {
        this.impactPresenterView.onClickBMBImpact(index);
    }

    @Override
    public void onClickCreateSubImpact(long impactID, cImpactModel impactModel) {
        this.impactPresenterView.onClickCreateImpact(impactModel);
    }

    @Override
    public void onClickUpdateImpact(int position, cImpactModel impactModel) {
        this.impactPresenterView.onClickUpdateImpact(position, impactModel);
    }

    @Override
    public void onClickDeleteImpact(int position, long impactID) {
        this.impactPresenterView.onClickDeleteImpact(position, impactID);
    }

    @Override
    public void onClickSyncImpact(int position, cImpactModel impactModel) {
        this.impactPresenterView.onClickSyncImpact(impactModel);
    }

    @Override
    public void onClickDetailImpact(cOutcomeModel[] outcomeModels, cQuestionModel[] questionModels) {
        this.impactPresenterView.onClickDetailImpact(outcomeModels, questionModels);
    }

    /* outcome click events */

    @Override
    public void onClickUpdateOutcome(cOutcomeModel outcomeModel, int position) {
        this.outcomePresenterView.onClickUpdateOutcome(outcomeModel, position);
    }

    @Override
    public void onClickDeleteOutcome(long outcomeID, int position) {
        this.outcomePresenterView.onClickDeleteOutcome(outcomeID, position);
    }

    @Override
    public void onClickSyncOutcome(cOutcomeModel outcomeModel, int position) {
        this.outcomePresenterView.onClickSyncOutcome(outcomeModel);
    }

    @Override
    public void onClickBMBOutcome(int index) {
        this.outcomePresenterView.onClickBMBOutcome(index);
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
                        if (((cImpactModel) treeModel.getModelObject()).getName().toLowerCase().
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
                filteredTreeModels = (ArrayList<cTreeModel>) filterResults.values;

                try {
                    notifyTreeModelChanged(filteredTreeModels);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static class cImpactViewHolder extends cTreeViewHolder {
        private final ComponentParentCardviewBinding binding;
        private final iViewImpactListener impactListener;
        private final View treeView;

        private final TextView textViewModifiedDate;
        private final TextView textViewCreatedDate;

        private cImpactViewHolder(final ComponentParentCardviewBinding binding,
                                  iViewImpactListener impactListener) {

            super(binding.getRoot());

            this.binding = binding;
            this.treeView = binding.getRoot();
            this.impactListener = impactListener;

            // common attributes
            textViewCreatedDate = treeView.findViewById(R.id.textViewCreatedDate);
            textViewModifiedDate = treeView.findViewById(R.id.textViewModifiedDate);

//            this.treeView = treeViewHolder;
//            this.impactListener = impactListener;
//
//            this.textViewStartDateCaption = treeViewHolder.findViewById(R.id.textViewStartDateCaption);
//            this.textViewEndDateCaption = treeViewHolder.findViewById(R.id.textViewEndDateCaption);
//            this.textViewName = treeViewHolder.findViewById(R.id.textViewName);
//            this.textViewDescription = treeViewHolder.findViewById(R.id.textViewDescription);
//            this.textViewStartDate = treeViewHolder.findViewById(R.id.textViewStartDate);
//            this.textViewEndDate = treeViewHolder.findViewById(R.id.textViewEndDate);
//            this.textViewDetailIcon = treeViewHolder.findViewById(R.id.textViewDetailIcon);
//            this.textViewDeleteIcon = treeViewHolder.findViewById(R.id.textViewDeleteIcon);
//            this.textViewUpdateIcon = treeViewHolder.findViewById(R.id.textViewUpdateIcon);

//            this.moduleTabLayout = treeViewHolder.findViewById(R.id.moduleTabLayout);
//            this.moduleViewPager2 = treeViewHolder.findViewById(R.id.moduleViewPager2);

        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cImpactChildrenViewHolder extends cTreeViewHolder {
        private final CardView cardView;
        private final TabLayout moduleTabLayout;
        private final ViewPager2 moduleViewPager2;

        //private final TabLayoutMediator tabLayoutMediator;

//        private void initViewPager(View view) {
//            /* setup the pager views */
//            ViewPager2 moduleViewPager2 = view.findViewById(R.id.moduleViewPager2);
//
//            moduleViewPagerAdapter = new cModuleViewPagerAdapter(requireActivity());
//
//            moduleViewPagerAdapter.addFrag(cEntityFragment.newInstance(), "entity permissions");
//            moduleViewPagerAdapter.addFrag(cMenuFragment.newInstance(), "menu permissions");
//
//            moduleViewPager2.setAdapter(moduleViewPagerAdapter);
//
//            /* setup the tab layout and add tabs to the view pager2 */
//            TabLayout moduleTabLayout = view.findViewById(R.id.moduleTabLayout);

//            TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(moduleTabLayout,
//                    moduleViewPager2, (tab, position) ->
//                    tab.setText(moduleViewPagerAdapter.getPageTitle(position)));

//            tabLayoutMediator.attach();
//        }

//        private AppCompatTextView textViewExpandIcon;
//        private LinearLayout linearLayoutHeader;
//
//        private AppCompatTextView textViewNameCaption;
//        private AppCompatTextView textViewDescriptionCaption;
//
//        private AppCompatTextView textViewStartDateCaption;
//        private AppCompatTextView textViewEndDateCaption;
//
//        private AppCompatTextView textViewName;
//        private AppCompatTextView textViewDescription;
//        private AppCompatTextView textViewStartDate;
//        private AppCompatTextView textViewEndDate;
//
//        private AppCompatTextView textViewDetailIcon;
//        private AppCompatTextView textViewSyncIcon;
//        private AppCompatTextView textViewDeleteIcon;
//        private AppCompatTextView textViewUpdateIcon;

        private View treeView;
        private iViewImpactListener impactListener;

        private cImpactChildrenViewHolder(View treeViewHolder,
                                          iViewImpactListener impactListener) {
            super(treeViewHolder);
            this.treeView = treeViewHolder;
            this.impactListener = impactListener;

            this.cardView = treeViewHolder.findViewById(R.id.cardView);
            //this.impactTabLayout = treeViewHolder.findViewById(R.id.impactTabLayout);
            this.moduleTabLayout = treeViewHolder.findViewById(R.id.moduleTabLayout);
            this.moduleViewPager2 = treeViewHolder.findViewById(R.id.moduleViewPager2);
//
//            moduleViewPagerAdapter.addFrag(cEntityFragment.newInstance(), "sub impacts");
//            moduleViewPagerAdapter.addFrag(cMenuFragment.newInstance(), "outcomes");
//            moduleViewPagerAdapter.addFrag(cMenuFragment.newInstance(), "details");
//            moduleViewPager2.setAdapter(moduleViewPagerAdapter);
//
//            this.tabLayoutMediator = new TabLayoutMediator(moduleTabLayout,
//                    moduleViewPager2, (tab, pos) ->
//                    tab.setText(moduleViewPagerAdapter.getPageTitle(pos)));
//            tabLayoutMediator.attach();


//            this.linearLayoutHeader = treeViewHolder.findViewById(R.id.linearLayoutHeader);
//
//            this.textViewNameCaption = treeViewHolder.findViewById(R.id.textViewNameCaption);
//            this.textViewDescriptionCaption = treeViewHolder.findViewById(R.id.textViewDescriptionCaption);
//            this.textViewStartDateCaption = treeViewHolder.findViewById(R.id.textViewStartDateCaption);
//            this.textViewEndDateCaption = treeViewHolder.findViewById(R.id.textViewEndDateCaption);
//
//            this.textViewName = treeViewHolder.findViewById(R.id.textViewName);
//            this.textViewDescription = treeViewHolder.findViewById(R.id.textViewDescription);
//            this.textViewStartDate = treeViewHolder.findViewById(R.id.textViewStartDate);
//            this.textViewEndDate = treeViewHolder.findViewById(R.id.textViewEndDate);
//
//            this.textViewDetailIcon = treeViewHolder.findViewById(R.id.textViewDetailIcon);
//            this.textViewSyncIcon = treeViewHolder.findViewById(R.id.textViewSyncIcon);
//            this.textViewDeleteIcon = treeViewHolder.findViewById(R.id.textViewDeleteIcon);
//            this.textViewUpdateIcon = treeViewHolder.findViewById(R.id.textViewUpdateIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
