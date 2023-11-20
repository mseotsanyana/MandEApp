package com.me.mseotsanyana.mande.framework.ui.adapters.session;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.PL.presenters.session.iOrganizationPresenter;
import com.me.mseotsanyana.mande.PL.ui.listeners.session.IOrganizationAdapterListener;
import com.me.mseotsanyana.mande.PL.utils.cIndexedLinkedHashMap;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationChildCardviewBinding;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationParentCardviewBinding;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class COrganizationAdapter extends cTreeAdapter implements IOrganizationAdapterListener, Filterable {
    private static final String TAG = COrganizationAdapter.class.getSimpleName();
    //private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PARENT_ORGANIZATION = 0;
    private static final int CHILD_ORGANIZATION = 1;

    private final Context context;
    private final iOrganizationPresenter.View organizationPresenterView;

    private cIndexedLinkedHashMap<String, COrganizationModel> organizationModels;
    private cIndexedLinkedHashMap<String, COrganizationModel> filteredStakeholderModels;
    private List<cTreeModel> filteredTreeModels;

    private LayoutInflater layoutInflater;

    Gson gson = new Gson();

    public COrganizationAdapter(Context context, iOrganizationPresenter.View organizationPresenterView,
                                List<cTreeModel> treeModels) {
        super(context, treeModels);
        this.context = context;
        this.organizationPresenterView = organizationPresenterView;
        this.filteredTreeModels = treeModels;
    }

    public COrganizationAdapter(Context context, iOrganizationPresenter.View organizationPresenterView,
                                cIndexedLinkedHashMap<String,
                                        COrganizationModel> organizationModels) {
        super(context, null, 0);//fixme
        this.context = context;
        this.organizationPresenterView = organizationPresenterView;
        this.organizationModels = organizationModels;
        this.filteredStakeholderModels = organizationModels;
    }

    public int getReversePosition(int index) {
        if (filteredStakeholderModels != null && !filteredStakeholderModels.isEmpty())
            return filteredStakeholderModels.size() - 1 - index;
        else return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void reloadList(cIndexedLinkedHashMap<String, COrganizationModel> list) {
        //organizationModels = list;
        filteredStakeholderModels = list;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void reloadList(int index, String operation) {
        switch (operation) {
            case "ADD":
                notifyItemInserted(getReversePosition(index));
                Log.d(TAG, "INDEX = " + index + " - " + getReversePosition(index));
                break;
            case "UPDATE":
                notifyItemChanged(getReversePosition(index));
                break;
            case "DELETE":
                notifyItemRemoved(getReversePosition(index));
                break;
            default:
                notifyDataSetChanged();
                break;
        }
    }

    public cIndexedLinkedHashMap<String, COrganizationModel> getOrganizationList() {
        return filteredStakeholderModels;
    }

//    @Override
//    public int getItemCount() {
//        return filteredStakeholderModels.size();
//    }

    @Override
    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case PARENT_ORGANIZATION:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }

                SessionOrganizationParentCardviewBinding parentBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.session_organization_parent_cardview, parent,
                        false);

                viewHolder = new COrganizationViewHolder(parentBinding, this);
                COrganizationViewHolder POH = (COrganizationViewHolder) viewHolder;

                // initialise organization listeners

                // set listener on detail icon
                /* the collapse and expansion of the impact */
                POH.parentBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                POH.parentBinding.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                        cFontManager.FONTAWESOME));
                POH.parentBinding.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                POH.parentBinding.textViewDetailIcon.setText(
                        context.getResources().getString(R.string.fa_angle_down));

                POH.parentBinding.textViewDetailIcon.setOnClickListener(v -> {
                    if (!(POH.parentBinding.expandableLayout.isExpanded())) {
                        POH.parentBinding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_down));

                        // initialise and register callback on page change
                        int position = POH.getAbsoluteAdapterPosition();
                        //PPH.binding.projectViewPager2.meViewPager2.setOffscreenPageLimit(1);
                        POH.listener.onRegisterOrganizationViewPager2(position);

                    } else {
                        POH.parentBinding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_up));

                        // unregister callback on page change
                        POH.listener.onUnRegisterOrganizationViewPager2();
                    }
                    POH.parentBinding.expandableLayout.toggle();
                });


//                POH.parentBinding.textViewDetailIcon.setOnClickListener(v -> {
//                    int position = POH.getAbsoluteAdapterPosition();
//                    expandOrCollapse(position);
//                });

                break;

            case CHILD_ORGANIZATION:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }
                SessionOrganizationChildCardviewBinding childBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.session_organization_child_cardview, parent,
                        false);

                viewHolder = new CChildOrganizationViewHolder(childBinding, this);
                CChildOrganizationViewHolder CWH = (CChildOrganizationViewHolder) viewHolder;

                // initialise workspace listeners
//                CWH.childBinding.cardView.setOnLongClickListener(view -> {
//                    int position = CWH.getAbsoluteAdapterPosition();
//                    //CWH.listener.onLongClickWorkspace(position);
//                    return false;
//                });

                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        cNode node = visibleNodes.get(position);
        cTreeModel obj = (cTreeModel) node.getObj();

        if (obj != null) {
            switch (obj.getType()) {
                case PARENT_ORGANIZATION:

                    COrganizationModel organizationModel;
                    //organizationModel = filteredStakeholderModels.getItemByIndex(getReversePosition(position));

                    organizationModel = (COrganizationModel) obj.getModelObject();
                    COrganizationViewHolder POH = ((COrganizationViewHolder) viewHolder);

                    //Log.d(TAG, "ORGANIZATION ========= " + gson.toJson(organizationModel));

                    /* icon for name of an organization */
                    POH.parentBinding.textViewOrganizationIcon.setTypeface(null, Typeface.NORMAL);
                    POH.parentBinding.textViewOrganizationIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));

                    if (organizationModel.getTypeID() == 0) {
                        POH.parentBinding.textViewOrganizationIcon.setTextColor(Color.RED);
                    } else if (organizationModel.getTypeID() == 1) {
                        POH.parentBinding.textViewOrganizationIcon.setTextColor(Color.GREEN);
                    } else if (organizationModel.getTypeID() == 2) {
                        POH.parentBinding.textViewOrganizationIcon.setTextColor(Color.BLUE);
                    } else {
                        POH.parentBinding.textViewOrganizationIcon.setTextColor(Color.MAGENTA);
                    }

                    POH.parentBinding.textViewOrganizationIcon.setText(context.getResources().getString(R.string.fa_organization));
                    POH.parentBinding.textViewName.setText(organizationModel.getName());

                    POH.parentBinding.textViewEmailIcon.setTypeface(null, Typeface.NORMAL);
                    POH.parentBinding.textViewEmailIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    POH.parentBinding.textViewEmailIcon.setTextColor(context.getColor(R.color.black));
                    POH.parentBinding.textViewEmailIcon.setText(context.getResources().getString(R.string.fa_email));
                    POH.parentBinding.textViewEmail.setText(organizationModel.getEmail());

                    POH.parentBinding.textViewWebsiteIcon.setTypeface(null, Typeface.NORMAL);
                    POH.parentBinding.textViewWebsiteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    POH.parentBinding.textViewWebsiteIcon.setTextColor(context.getColor(R.color.black));
                    POH.parentBinding.textViewWebsiteIcon.setText(context.getResources().getString(R.string.fa_website));
                    POH.parentBinding.textViewWebsite.setText(organizationModel.getWebsite());

                    /* icon for deleting a record */
                    POH.parentBinding.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    POH.parentBinding.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    POH.parentBinding.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimary));
                    POH.parentBinding.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    POH.parentBinding.textViewDeleteIcon.setOnClickListener(view -> {
                        //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
                    });

                    /* icon for saving updated record */
                    POH.parentBinding.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    POH.parentBinding.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    POH.parentBinding.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimary));
                    POH.parentBinding.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    POH.parentBinding.textViewUpdateIcon.setOnClickListener(view -> {
                        //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
                    });

                    /* icon for joining a record
                    POH.parentBinding.textViewJoinIcon.setTypeface(null, Typeface.NORMAL);
                    POH.parentBinding.textViewJoinIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    POH.parentBinding.textViewJoinIcon.setTextColor(context.getColor(R.color.colorPrimary));
                    POH.parentBinding.textViewJoinIcon.setText(context.getResources().getString(R.string.fa_join));
                    POH.parentBinding.textViewJoinIcon.setOnClickListener(view -> {
                        //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
                    }); */

                    /* icon for creating a record */
                    POH.parentBinding.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
                    POH.parentBinding.textViewCreateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    POH.parentBinding.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimary));
                    POH.parentBinding.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
                    POH.parentBinding.textViewCreateIcon.setOnClickListener(view -> {
                        //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
                    });

                    // collapse and expansion of the details of the role
                    /*POH.parentBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    POH.parentBinding.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    POH.parentBinding.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    POH.parentBinding.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));*/


                    /* the collapse and expansion of the parent logframe */
                    if (node.isLeaf()) {
                        POH.parentBinding.textViewDetailIcon.setVisibility(View.GONE);
                    } else {

                        POH.parentBinding.textViewDetailIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            POH.parentBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            POH.parentBinding.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            POH.parentBinding.textViewDetailIcon.setTextColor(
                                    context.getColor(R.color.colorPrimaryDark));
                            POH.parentBinding.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        } else {
                            POH.parentBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            POH.parentBinding.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            POH.parentBinding.textViewDetailIcon.setTextColor(
                                    context.getColor(R.color.colorPrimaryDark));
                            POH.parentBinding.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        }
                    }

                    break;
                case CHILD_ORGANIZATION:
                    COrganizationModel commonAttribute = (COrganizationModel) obj.getModelObject();
                    CChildOrganizationViewHolder COH = ((CChildOrganizationViewHolder) viewHolder);

                    COH.setPaddingLeft(20 * node.getLevel());


                    //COH.childBinding.tablayoutViewpager2.meViewPager2.setOffscreenPageLimit();
//
//                    COH.childBinding.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
//                            R.color.child_body_colour));
//                    COH.childBinding.textViewName.setText(childTeamModel.getName());
//                    COH.childBinding.textViewDescription.setText(childTeamModel.getDescription());
//
//
//                    /* icon for deleting a record */
//                    COH.childBinding.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
//                    COH.childBinding.textViewDeleteIcon.setTypeface(
//                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//                    COH.childBinding.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorAccent));
//                    COH.childBinding.textViewDeleteIcon.setText(
//                            context.getResources().getString(R.string.fa_delete));
//                    COH.childBinding.textViewDeleteIcon.setOnClickListener(view ->{}
//                            /*CVH.logFrameListener.onClickDeleteLogFrame(null
//                                    childLogFrameModel.getLogFrameID())*/);
//
//                    /* icon for saving updated record */
//                    COH.childBinding.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
//                    COH.childBinding.textViewUpdateIcon.setTypeface(
//                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//                    COH.childBinding.textViewUpdateIcon.setTextColor(
//                            context.getColor(R.color.colorAccent));
//                    COH.childBinding.textViewUpdateIcon.setText(
//                            context.getResources().getString(R.string.fa_update));
//                    COH.childBinding.textViewUpdateIcon.setOnClickListener(view ->{}
//                            /*CVH.logFrameListener.onClickUpdateLogFrame(position,
//                                    childLogFrameModel)*/);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + obj.getType());
            }
        }
    }

//    public void onBindViewHolder(@NonNull cOrganizationViewHolder OH, int position) {
//        cOrganizationModel stakeholderModel;
//        stakeholderModel = filteredStakeholderModels.getItemByIndex(getReversePosition(position));
//
//        Log.d(TAG, "ORGANIZATION ========= " + gson.toJson(stakeholderModel));
//
//        /* icon for name of an organization */
//        OH.binding.textViewOrganizationIcon.setTypeface(null, Typeface.NORMAL);
//        OH.binding.textViewOrganizationIcon.setTypeface(
//                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//
//
//        if (stakeholderModel.getTypeID() == 0) {
//            OH.binding.textViewOrganizationIcon.setTextColor(Color.RED);
//        } else if (stakeholderModel.getTypeID() == 1) {
//            OH.binding.textViewOrganizationIcon.setTextColor(Color.GREEN);
//        } else if (stakeholderModel.getTypeID() == 2) {
//            OH.binding.textViewOrganizationIcon.setTextColor(Color.BLUE);
//        } else {
//            OH.binding.textViewOrganizationIcon.setTextColor(Color.MAGENTA);
//        }
//
//        OH.binding.textViewOrganizationIcon.setText(context.getResources().getString(R.string.fa_organization));
//        OH.binding.textViewName.setText(stakeholderModel.getName());
//
//        OH.binding.textViewEmailIcon.setTypeface(null, Typeface.NORMAL);
//        OH.binding.textViewEmailIcon.setTypeface(
//                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//        OH.binding.textViewEmailIcon.setTextColor(context.getColor(R.color.black));
//        OH.binding.textViewEmailIcon.setText(context.getResources().getString(R.string.fa_email));
//        OH.binding.textViewEmail.setText(stakeholderModel.getEmail());
//
//        OH.binding.textViewWebsiteIcon.setTypeface(null, Typeface.NORMAL);
//        OH.binding.textViewWebsiteIcon.setTypeface(
//                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//        OH.binding.textViewWebsiteIcon.setTextColor(context.getColor(R.color.black));
//        OH.binding.textViewWebsiteIcon.setText(context.getResources().getString(R.string.fa_website));
//        OH.binding.textViewWebsite.setText(stakeholderModel.getWebsite());
//
//        /* icon for deleting a record */
//        OH.binding.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
//        OH.binding.textViewDeleteIcon.setTypeface(
//                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//        OH.binding.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimary));
//        OH.binding.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
//        OH.binding.textViewDeleteIcon.setOnClickListener(view -> {
//            //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
//        });
//
//        /* icon for saving updated record */
//        OH.binding.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
//        OH.binding.textViewUpdateIcon.setTypeface(
//                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//        OH.binding.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimary));
//        OH.binding.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
//        OH.binding.textViewUpdateIcon.setOnClickListener(view -> {
//            //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
//        });
//
//        /* icon for joining a record */
//        OH.binding.textViewJoinIcon.setTypeface(null, Typeface.NORMAL);
//        OH.binding.textViewJoinIcon.setTypeface(
//                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//        OH.binding.textViewJoinIcon.setTextColor(context.getColor(R.color.colorPrimary));
//        OH.binding.textViewJoinIcon.setText(context.getResources().getString(R.string.fa_join));
//        OH.binding.textViewJoinIcon.setOnClickListener(view -> {
//            //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
//        });
//
//        /* icon for creating a record */
//        OH.binding.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
//        OH.binding.textViewCreateIcon.setTypeface(
//                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//        OH.binding.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimary));
//        OH.binding.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
//        OH.binding.textViewCreateIcon.setOnClickListener(view -> {
//            //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
//        });
//
//        // collapse and expansion of the details of the role
//        OH.binding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
//        OH.binding.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//        OH.binding.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
//        OH.binding.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
////        OH.binding.textViewDetailIcon.setOnClickListener(view -> {
////            if (!(OH.binding.expandableLayout.isExpanded())) {
////                OH.binding.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
////            } else {
////                OH.binding.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
////            }
////
////            OH.binding.expandableLayout.toggle();
////        });
//    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                /*String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredOrganizationModels = organizationModels;
                } else {

                    ArrayList<cOrganizationModel> filteredList = new ArrayList<>();
                    for (cOrganizationModel organizationModel : organizationModels) {
                        if (organizationModel.getName().toLowerCase().
                                contains(charString.toLowerCase())) {
                            filteredList.add(organizationModel);
                        }
                    }

                    filteredOrganizationModels = filteredList;
                }*/

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredStakeholderModels.size();
                filterResults.values = filteredStakeholderModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredStakeholderModels = null;//(ArrayList<cOrganizationModel>) filterResults.values;
                //notifyDataSetChanged();
            }
        };
    }

//    @Override
//    public void onLongClickWorkspace(int position) {
//        cNode node = visibleNodes.get(position);
//        cTreeModel obj = (cTreeModel) node.getObj();
//        CWorkspaceModel workspaceModel = (CWorkspaceModel) obj.getModelObject();
//        organizationPresenterView.onLongClickWorkspace(workspaceModel);
//    }

    @Override
    public void onRegisterOrganizationViewPager2(int position) {

    }

    @Override
    public void onUnRegisterOrganizationViewPager2() {

    }

    public static class COrganizationViewHolder extends RecyclerView.ViewHolder {
        private final SessionOrganizationParentCardviewBinding parentBinding;
        private final IOrganizationAdapterListener listener;
        private final View treeView;

        private COrganizationViewHolder(SessionOrganizationParentCardviewBinding parentBinding,
                                        IOrganizationAdapterListener listener) {
            super(parentBinding.getRoot());
            this.treeView = parentBinding.getRoot();
            this.parentBinding = parentBinding;
            this.listener = listener;
        }

        public void setPaddingLeft(int paddingLeft) {
            this.treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class CChildOrganizationViewHolder extends RecyclerView.ViewHolder {
        private final SessionOrganizationChildCardviewBinding childBinding;
        private final IOrganizationAdapterListener listener;
        private final View treeView;

        private CChildOrganizationViewHolder(@NonNull SessionOrganizationChildCardviewBinding childBinding,
                                             IOrganizationAdapterListener listener) {
            super(childBinding.getRoot());

            this.childBinding = childBinding;
            this.listener = listener;
            this.treeView = childBinding.getRoot();

//            this.cardView = treeView.findViewById(R.id.cardView);
//            this.meTabLayout = treeView.findViewById(R.id.meTabLayout);
//            this.meViewPager2 = treeView.findViewById(R.id.meViewPager2);
        }

        public void setPaddingLeft(int paddingLeft) {
            this.treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}