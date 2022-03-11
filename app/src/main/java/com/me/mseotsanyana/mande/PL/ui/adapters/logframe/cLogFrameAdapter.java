package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.gson.Gson;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cTextOutsideCircleButton;
import com.me.mseotsanyana.bmblibrary.cUtil;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iLogFramePresenter;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewLogFrameListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.databinding.LogframeChildCardviewBinding;
import com.me.mseotsanyana.mande.databinding.LogframeParentCardviewBinding;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cLogFrameAdapter extends cTreeAdapter implements iViewLogFrameListener,
        Filterable {
    private static final String TAG = cLogFrameAdapter.class.getSimpleName();
    private static final SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PARENT_LOGFRAME = 0;
    private static final int CHILD_LOGFRAME = 1;

    private final iLogFramePresenter.View logframePresenterView;
    public FragmentManager fragmentManager;
    private LayoutInflater layoutInflater;

    private List<cTreeModel> filteredTreeModels;
    private final SparseBooleanArray selectedItems;

    private Set<String> logframe_ids;
    private List<String> components;

    private final String[] bmb_caption = {
            "Impacts (or Goals)",
            "Outcomes",
            "Outputs",
            "Activities",
            "Inputs"
    };

    private final int[] bmb_imageid = {
            R.drawable.dashboard_impact,
            R.drawable.dashboard_outcome,
            R.drawable.dashboard_output,
            R.drawable.dashboard_logframe,
            R.drawable.dashboard_input
    };

    Gson gson = new Gson();

    public cLogFrameAdapter(Context context, iLogFramePresenter.View logframePresenterView,
                            List<cTreeModel> treeModels) {
        super(context, treeModels);

        this.logframePresenterView = logframePresenterView;
        this.filteredTreeModels = treeModels;

        this.selectedItems = new SparseBooleanArray();
        this.logframe_ids = new HashSet<>();
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        //LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //View view;
        switch (viewType) {
            case PARENT_LOGFRAME:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }
                LogframeParentCardviewBinding parentBinding = DataBindingUtil.inflate(layoutInflater,
                        R.layout.logframe_parent_cardview, parent, false);

                viewHolder = new cParentLogFrameViewHolder(parentBinding, this);
                cParentLogFrameViewHolder PVH = (cParentLogFrameViewHolder) viewHolder;

                // set listener on expand icon - toggling with a header
                PVH.binding.textViewExpandIcon.setOnClickListener(v -> {
                    int position = PVH.getAbsoluteAdapterPosition();
                    expandOrCollapse(position);
                });
                PVH.binding.linearLayoutHeader.setOnClickListener(v -> {
                    int position = PVH.getAbsoluteAdapterPosition();
                    expandOrCollapse(position);
                });

                // set listener on boom menu
                PVH.binding.bmbMenu.setOnClickListener(v -> PVH.binding.bmbMenu.boom());

                // set listener on detail icon
                PVH.binding.textViewDetailIcon.setOnClickListener(v -> {
                    if (!(PVH.binding.expandableLayout.isExpanded())) {
                        PVH.binding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_up));
                    } else {
                        PVH.binding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_down));
                    }
                    PVH.binding.expandableLayout.toggle();
                });

                // set listener on delete icon
                PVH.binding.textViewDeleteIcon.setOnClickListener(v -> {
                    int position = PVH.getAbsoluteAdapterPosition();
                    PVH.logFrameListener.onClickDeleteLogFrame(null
                            /*parentLogFrameModel.getLogFrameID()*/);
                });

                // set listener on update icon
                PVH.binding.textViewUpdateIcon.setOnClickListener(v -> {
                    int position = PVH.getAbsoluteAdapterPosition();
                    PVH.logFrameListener.onClickUpdateLogFrame(position, null
                            /*parentLogFrameModel*/);
                });

                // set listener on create icon
                PVH.binding.textViewCreateIcon.setOnClickListener(v -> {
                    PVH.logFrameListener.onClickCreateSubLogFrame(null
                            /*parentLogFrameModel.getLogFrameID()*/, new cLogFrameModel());
                });

                // set listener on upload icon
                PVH.binding.textViewUploadIcon.setOnClickListener(v -> {
                    //holder.logFrameListener.onClickSyncLogFrame(position, parentLogFrameModel);
                });

                // set listener on detail icon
                PVH.binding.textViewDetailIcon.setOnClickListener(v -> {
                    if (!(PVH.binding.expandableLayout.isExpanded())) {
                        PVH.binding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_up));
                    } else {
                        PVH.binding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_down));
                    }
                    PVH.binding.expandableLayout.toggle();
                });

                // set listener on the holder
                PVH.binding.textViewDescription.setOnLongClickListener(v -> {
                    int position = PVH.getAbsoluteAdapterPosition();
                    if (selectedItems.get(position, false)) {
                        selectedItems.delete(position);
                        PVH.binding.cardView.setCardBackgroundColor(Color.WHITE);
                    } else {
                        selectedItems.put(position, true);
                        PVH.binding.cardView.setCardBackgroundColor(Color.CYAN);
                    }

                    PVH.logFrameListener.onLongClickLogFrame(selectedItems);

                    return true;
                });

                break;

            case CHILD_LOGFRAME:
                //view = inflater.inflate(R.layout.logframe_child_cardview, parent, false);

                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }
                LogframeChildCardviewBinding childBinding = DataBindingUtil.inflate(layoutInflater,
                        R.layout.logframe_child_cardview, parent, false);

                viewHolder = new cChildLogFrameViewHolder(childBinding, this);
                cChildLogFrameViewHolder CVH = (cChildLogFrameViewHolder) viewHolder;

                // set listener on boom menu
                CVH.binding.bmbMenu.setOnClickListener(v -> CVH.binding.bmbMenu.boom());

                // set listener on detail icon
                CVH.binding.textViewDetailIcon.setOnClickListener(v -> {
                    if (!(CVH.binding.expandableLayout.isExpanded())) {
                        CVH.binding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_up));
                    } else {
                        CVH.binding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_down));
                    }
                    CVH.binding.expandableLayout.toggle();
                });

                // set listener on delete icon
                CVH.binding.textViewDeleteIcon.setOnClickListener(v -> {
                    int position = CVH.getAbsoluteAdapterPosition();
                    CVH.logFrameListener.onClickDeleteLogFrame(null
                            /*parentLogFrameModel.getLogFrameID()*/);
                });

                // set listener on update icon
                CVH.binding.textViewUpdateIcon.setOnClickListener(v -> {
                    int position = CVH.getAbsoluteAdapterPosition();
                    CVH.logFrameListener.onClickUpdateLogFrame(position, null
                            /*parentLogFrameModel*/);
                });

                // set listener on detail icon
                CVH.binding.textViewDetailIcon.setOnClickListener(v -> {
                    if (!(CVH.binding.expandableLayout.isExpanded())) {
                        CVH.binding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_up));
                    } else {
                        CVH.binding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_down));
                    }
                    CVH.binding.expandableLayout.toggle();
                });

                // set listener on the holder
                CVH.binding.textViewDescription.setOnLongClickListener(v -> {
                    int position = CVH.getAbsoluteAdapterPosition();
                    if (selectedItems.get(position, false)) {
                        selectedItems.delete(position);
                        CVH.binding.cardView.setCardBackgroundColor(Color.WHITE);
                    } else {
                        selectedItems.put(position, true);
                        CVH.binding.cardView.setCardBackgroundColor(Color.CYAN);
                    }

                    CVH.logFrameListener.onLongClickLogFrame(selectedItems);

                    return true;
                });

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
                case PARENT_LOGFRAME:
                    cLogFrameModel parentLogFrameModel = (cLogFrameModel) obj.getModelObject();
                    cParentLogFrameViewHolder PVH = ((cParentLogFrameViewHolder) viewHolder);

                    PVH.setPaddingLeft(20 * node.getLevel());

                    // highlight selected items
                    if (selectedItems.get(position, false)) {
                        PVH.binding.cardView.setCardBackgroundColor(Color.CYAN);
                    } else {
                        PVH.binding.cardView.setCardBackgroundColor(Color.WHITE);
                    }

                    PVH.binding.textViewName.setText(parentLogFrameModel.getName());
                    PVH.binding.textViewDescription.setText(parentLogFrameModel.getDescription());
                    PVH.binding.textViewStartDate.setText(sdf.format(parentLogFrameModel.getStartDate()));
                    PVH.binding.textViewEndDate.setText(sdf.format(parentLogFrameModel.getEndDate()));

                    /* the collapse and expansion of the parent logframe */
                    if (node.isLeaf()) {
                        PVH.binding.textViewExpandIcon.setVisibility(View.GONE);
                    } else {

                        PVH.binding.textViewExpandIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            PVH.binding.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            PVH.binding.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PVH.binding.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            PVH.binding.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            PVH.binding.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PVH.binding.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }

                    /* icon for accessing the boom menu */
                    PVH.binding.bmbMenu.clearBuilders();
                    for (int i = 0; i < PVH.binding.bmbMenu.getPiecePlaceEnum().pieceNumber(); i++) {

                        cTextOutsideCircleButton.Builder builder = new cTextOutsideCircleButton
                                .Builder()
                                .isRound(false)
                                .shadowCornerRadius(cUtil.dp2px(20))
                                .buttonCornerRadius(cUtil.dp2px(20))
                                .normalColor(Color.LTGRAY)
                                .pieceColor(context.getColor(R.color.colorPrimaryDark))
                                .normalImageRes(bmb_imageid[i])
                                .normalText(bmb_caption[i])
                                .listener(index -> {
                                    // when the boom-button is clicked.
                                    PVH.logFrameListener.onClickBMBLogFrame(
                                            index, parentLogFrameModel);
                                });
                        PVH.binding.bmbMenu.addBuilder(builder);
                    }

                    /* icon for deleting a record */
                    PVH.binding.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.binding.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.binding.textViewDeleteIcon.setTextColor(
                            context.getColor(R.color.colorPrimaryDark));
                    PVH.binding.textViewDeleteIcon.setText(
                            context.getResources().getString(R.string.fa_delete));

                    /* icon for saving updated record */
                    PVH.binding.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.binding.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.binding.textViewUpdateIcon.setTextColor(
                            context.getColor(R.color.colorPrimaryDark));
                    PVH.binding.textViewUpdateIcon.setText(
                            context.getResources().getString(R.string.fa_update));

                    /* icon for creating a record */
                    PVH.binding.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.binding.textViewCreateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.binding.textViewCreateIcon.setTextColor(
                            context.getColor(R.color.colorPrimaryDark));
                    PVH.binding.textViewCreateIcon.setText(
                            context.getResources().getString(R.string.fa_create));

                    /* icon for uploading logframes from excel file */
                    PVH.binding.textViewUploadIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.binding.textViewUploadIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.binding.textViewUploadIcon.setTextColor(
                            context.getColor(R.color.colorPrimaryDark));
                    PVH.binding.textViewUploadIcon.setText(
                            context.getResources().getString(R.string.fa_upload));

                    // collapse and expansion of the details of the role
                    PVH.binding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.binding.textViewDetailIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.binding.textViewDetailIcon.setTextColor(
                            context.getColor(R.color.colorPrimaryDark));
                    PVH.binding.textViewDetailIcon.setText(
                            context.getResources().getString(R.string.fa_angle_down));

                    /* common attributes */
                    PVH.textViewCreatedDate.setText(sdf.format(parentLogFrameModel.getCreatedDate()));
                    PVH.textViewModifiedDate.setText(sdf.format(parentLogFrameModel.getModifiedDate()));

                    break;

                case CHILD_LOGFRAME:
                    cLogFrameModel childLogFrameModel = (cLogFrameModel) obj.getModelObject();
                    cChildLogFrameViewHolder CVH = ((cChildLogFrameViewHolder) viewHolder);

                    CVH.setPaddingLeft(20 * node.getLevel());

                    // highlight selected items
                    if (selectedItems.get(position, false)) {
                        CVH.binding.cardView.setCardBackgroundColor(Color.CYAN);
                    } else {
                        CVH.binding.cardView.setCardBackgroundColor(Color.WHITE);
                    }


                    CVH.binding.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            R.color.child_body_colour));
                    CVH.binding.textViewName.setText(childLogFrameModel.getName());
                    CVH.binding.textViewDescription.setText(childLogFrameModel.getDescription());
                    CVH.binding.textViewStartDate.setText(sdf.format(childLogFrameModel.getStartDate()));
                    CVH.binding.textViewEndDate.setText(sdf.format(childLogFrameModel.getEndDate()));

                    /* icon for accessing the boom menu */
                    CVH.binding.bmbMenu.clearBuilders();
                    for (int i = 0; i < CVH.binding.bmbMenu.getPiecePlaceEnum().pieceNumber(); i++) {
                        cTextOutsideCircleButton.Builder builder = new cTextOutsideCircleButton
                                .Builder()
                                .isRound(false)
                                .shadowCornerRadius(cUtil.dp2px(20))
                                .buttonCornerRadius(cUtil.dp2px(20))
                                .normalColor(Color.LTGRAY)
                                .pieceColor(context.getColor(R.color.colorAccent))
                                .normalImageRes(bmb_imageid[i])
                                .normalText(bmb_caption[i])
                                .listener(index -> {
                                    /* when the boom-button is clicked. */
                                    CVH.logFrameListener.onClickBMBLogFrame(
                                            index, null/*childLogFrameModel.getLogFrameID()*/);
                                });
                        CVH.binding.bmbMenu.addBuilder(builder);
                    }
                    CVH.binding.bmbMenu.setOnClickListener(v -> CVH.binding.bmbMenu.boom());

                    /* icon for deleting a record */
                    CVH.binding.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.binding.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.binding.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorAccent));
                    CVH.binding.textViewDeleteIcon.setText(
                            context.getResources().getString(R.string.fa_delete));
                    CVH.binding.textViewDeleteIcon.setOnClickListener(view ->
                            CVH.logFrameListener.onClickDeleteLogFrame(null
                                    /*childLogFrameModel.getLogFrameID()*/));

                    /* icon for saving updated record */
                    CVH.binding.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.binding.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.binding.textViewUpdateIcon.setTextColor(
                            context.getColor(R.color.colorAccent));
                    CVH.binding.textViewUpdateIcon.setText(
                            context.getResources().getString(R.string.fa_update));
                    CVH.binding.textViewUpdateIcon.setOnClickListener(view ->
                            CVH.logFrameListener.onClickUpdateLogFrame(position,
                                    childLogFrameModel));

                    // collapse and expansion of the details of the role
                    CVH.binding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.binding.textViewDetailIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.binding.textViewDetailIcon.setTextColor(
                            context.getColor(R.color.colorAccent));
                    CVH.binding.textViewDetailIcon.setText(
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
//                    for (cTreeModel treeModel : getTreeModel()) {
//                        if (((cLogFrameModel) treeModel.getModelObject()).getName().toLowerCase().
//                                contains(charString.toLowerCase()) ||
//                                ((cLogFrameModel) treeModel.getModelObject()).getOrganizationModel().
//                                        getName().toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(treeModel);
//                        }
//                    }

                    filteredTreeModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredTreeModels.size();
                filterResults.values = filteredTreeModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredTreeModels = (ArrayList<cTreeModel>) filterResults.values;

                try {
                    notifyTreeModelChanged(filteredTreeModels);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /* these functions communicate data from the adapter to a fragment */

    @Override
    public void onClickBMBLogFrame(int index, cLogFrameModel logFrameModel) {
        logframePresenterView.onClickBMBLogFrame(index, logFrameModel);
    }

    @Override
    public void onClickCreateSubLogFrame(String logFrameID, cLogFrameModel logFrameModel) {
        logframePresenterView.onClickCreateSubLogFrame(logFrameID, logFrameModel);
    }

    @Override
    public void onClickUpdateLogFrame(int position, cLogFrameModel logFrameModel) {
        logframePresenterView.onClickUpdateLogFrame(position, logFrameModel);
    }

    @Override
    public void onClickDeleteLogFrame(String logframeID) {
        logframePresenterView.onClickDeleteLogFrame(logframeID);
    }

    @Override
    public void onLongClickLogFrame(SparseBooleanArray selectedItems) {
        logframe_ids = new HashSet<>(selectedItems.size());
        components = new ArrayList<>(selectedItems.size());

        Log.d(TAG, "LOGFRAME = "+gson.toJson(filteredTreeModels.size()));

        for (int i = 0; i < selectedItems.size(); ++i) {
            cLogFrameModel parentFrame, childLogFrame;
            parentFrame = (cLogFrameModel) filteredTreeModels.get(
                    selectedItems.keyAt(i)).getModelObject();
            logframe_ids.add(parentFrame.getProjectServerID());
            components.addAll(parentFrame.getComponents());

            for(int j = 0; j < parentFrame.getChildren().size(); j++){
                String childID = parentFrame.getChildren().get(j);
                for(int k = 0; k < filteredTreeModels.size(); k++){
                    childLogFrame = (cLogFrameModel) filteredTreeModels.get(k).getModelObject();
                    if (childID.equals(childLogFrame.getProjectServerID())){
                        logframe_ids.add(childLogFrame.getProjectServerID());
                        components.addAll(childLogFrame.getComponents());
                        //Log.d(TAG, "SELECTED SUB-LOGFRAME ==>> "+childLogFrame.getComponents());
                    }
                }
            }
        }

        Log.d(TAG, "SELECTED LOGFRAME ====>>>> "
                + gson.toJson(logframe_ids)+" <--> "
                + gson.toJson(components));
    }

    @Override
    public List<String> onGetLogframeServerIDs() {
        return new ArrayList<>(logframe_ids);
    }

    @Override
    public List<String> onGetComponentServerIDs() {
        return components;
    }

    public static class cParentLogFrameViewHolder extends cTreeViewHolder {
        private final LogframeParentCardviewBinding binding;
        private final iViewLogFrameListener logFrameListener;
        private final View treeView;

        private final TextView textViewModifiedDate;
        private final TextView textViewCreatedDate;

        private cParentLogFrameViewHolder(LogframeParentCardviewBinding binding,
                                          iViewLogFrameListener listener) {
            super(binding.getRoot());

            this.binding = binding;
            this.treeView = binding.getRoot();
            this.logFrameListener = listener;

            // common attributes
            textViewCreatedDate = treeView.findViewById(R.id.textViewCreatedDate);
            textViewModifiedDate = treeView.findViewById(R.id.textViewModifiedDate);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cChildLogFrameViewHolder extends cTreeViewHolder {
        private final LogframeChildCardviewBinding binding;
        private final iViewLogFrameListener logFrameListener;
        private final View treeView;

//        private final CardView cardView;
//        private final AppCompatTextView textViewOrganization;
//        private final AppCompatTextView textViewName;
//        private final AppCompatTextView textViewDescription;
//        private final AppCompatTextView textViewStartDate;
//        private final AppCompatTextView textViewEndDate;
//
//        private final CBoomMenuButton bmbMenu;
//        private final AppCompatTextView textViewSyncIcon;
//        private final AppCompatTextView textViewDeleteIcon;
//        private final AppCompatTextView textViewUpdateIcon;
//        //private AppCompatTextView textViewCreateIcon;
//
//        private final View treeView;
//        private final iViewLogFrameListener logFrameListener;

        private cChildLogFrameViewHolder(LogframeChildCardviewBinding binding,
                                         iViewLogFrameListener listener) {
            super(binding.getRoot());

            this.binding = binding;
            this.treeView = binding.getRoot();
            this.logFrameListener = listener;

//            this.cardView = treeViewHolder.findViewById(R.id.cardView);
//            this.bmbMenu = treeViewHolder.findViewById(R.id.bmbMenu);
//            this.textViewOrganization = treeViewHolder.findViewById(R.id.textViewOrganization);
//            this.textViewName = treeViewHolder.findViewById(R.id.textViewName);
//            this.textViewDescription = treeViewHolder.findViewById(R.id.textViewDescription);
//            this.textViewStartDate = treeViewHolder.findViewById(R.id.textViewStartDate);
//            this.textViewEndDate = treeViewHolder.findViewById(R.id.textViewEndDate);
//            this.textViewSyncIcon = treeViewHolder.findViewById(R.id.textViewSyncIcon);
//            this.textViewDeleteIcon = treeViewHolder.findViewById(R.id.textViewDeleteIcon);
//            this.textViewUpdateIcon = treeViewHolder.findViewById(R.id.textViewUpdateIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }


//    static public class cQAAdapter extends BaseAdapter {
//
//        final int[] ICONS = new int[]{
//                R.string.fa_plus,
//                R.string.fa_upload
//        };
//
//        LayoutInflater mLayoutInflater;
//        List<cCustomActionItemText> mItems;
//        cCustomActionItemText item;
//
//        Context context;
//
//        public cQAAdapter(Context context) {
//            this.context = context;
//            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            mItems = new ArrayList<>();
//
//            item = new cCustomActionItemText(context, "Add", ICONS[0]);
//            mItems.add(item);
//
//            item = new cCustomActionItemText(context, "Upload", ICONS[1]);
//            mItems.add(item);
//        }
//
//        @Override
//        public int getCount() {
//            return mItems.size();
//        }
//
//        @Override
//        public Object getItem(int arg) {
//            return mItems.get(arg);
//        }
//
//        @Override
//        public long getItemId(int arg) {
//            return arg;
//        }
//
//        @SuppressLint("ViewHolder")
//        @Override
//        public View getView(int position, View arg1, ViewGroup viewGroup) {
//            View view;
//            view = mLayoutInflater.inflate(R.layout.action_item_flexible, viewGroup,
//                    false);
//
//            cCustomActionItemText item = (cCustomActionItemText) getItem(position);
//
//            TextView image = (TextView) view.findViewById(R.id.image);
//
//            image.setTypeface(null, Typeface.NORMAL);
//            image.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//            image.setText(context.getResources().getString(item.getImage()));
//            image.setTextColor(Color.GRAY);
//
//            return view;
//        }
//    }
}