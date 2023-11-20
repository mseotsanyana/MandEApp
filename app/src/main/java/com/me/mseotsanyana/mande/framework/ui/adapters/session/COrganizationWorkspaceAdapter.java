package com.me.mseotsanyana.mande.framework.ui.adapters.session;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.application.structures.enums.EAction;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.framework.ports.adapters.session.IOrganizationWorkspaceAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.framework.ports.base.IBaseAdapter;
import com.me.mseotsanyana.mande.framework.utils.CFontManager;
import com.me.mseotsanyana.mande.databinding.SessionChildWorkspaceCardviewBinding;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationParentCardviewBinding;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationWorkspaceController;
import com.me.mseotsanyana.mande.infrastructure.utils.responsemodel.CNode;
import com.me.mseotsanyana.mande.infrastructure.utils.responsemodel.CTreeAdapter;
import com.me.mseotsanyana.mande.infrastructure.utils.responsemodel.CTreeModel;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class COrganizationWorkspaceAdapter extends CTreeAdapter
        implements IBaseAdapter, IOrganizationWorkspaceAdapter, Filterable {
    private static final String TAG = COrganizationWorkspaceAdapter.class.getSimpleName();
    //private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int ORGANIZATION = 0;
    private static final int WORKSPACE = 1;

    private final Context context;
    private final IOrganizationWorkspaceController.IViewModel iViewModel;

    //private final CIndexedLinkedHashMap<String, CTreeModel> unFilteredTreeModels;

    private COrganizationModel organizationModel;

    private int absoluteAdapterPosition = -1;

    private LayoutInflater layoutInflater;

    public COrganizationWorkspaceAdapter(
            Context context,
            IOrganizationWorkspaceController.IViewModel iViewModel) {
        super(context);

        this.context = context;
        this.iViewModel = iViewModel;
        //this.unFilteredTreeModels = treeModels;
    }

    public void reloadTreeModels(EAction action, CTreeModel treeModel) {
        switch (action) {
            case Added_ORGANIZATION, Added_WORKSPACE -> {
                try {
                    addTreeModel2TreeAdapter(treeModel);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            case Modified_ORGANIZATION, Modified_WORKSPACE -> {
                try {
                    modifyTreeModelInTreeAdapter(treeModel);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            case Deleted_ORGANIZATION, Deleted_WORKSPACE -> {
                try {
                    deleteTreeModelInTreeAdapter(treeModel);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        if (absoluteAdapterPosition > -1) {
            expandOrCollapse(absoluteAdapterPosition);
            // make sure the expand corresponds to the click, not number of reads.
            absoluteAdapterPosition = -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case ORGANIZATION -> {
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }

                SessionOrganizationParentCardviewBinding parentBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.session_organization_parent_cardview, parent,
                        false);
                viewHolder = new cOrganizationViewHolder(parentBinding, this);
                cOrganizationViewHolder POH = (cOrganizationViewHolder) viewHolder;

                // expand or collapse organization
                POH.parentBinding.textViewDetailIcon.setOnClickListener(v -> {
                    int absoluteAdapterPosition = POH.getAbsoluteAdapterPosition();
                    CNode node = visibleNodes.get(absoluteAdapterPosition);

                    CTreeModel treeModel = node.getTreeModelObject();
                    String organizationServerID = treeModel.getChildID();

                    if (!node.isExpand()) {
                        POH.listener.onClickOrganization(organizationServerID, absoluteAdapterPosition);
                    } else {
                        expandOrCollapse(absoluteAdapterPosition);
                    }
                });

                // create workspace
                POH.parentBinding.textViewCreateIcon.setOnClickListener(view -> {
                    int absoluteAdapterPosition = POH.getAbsoluteAdapterPosition();
                    CNode node = visibleNodes.get(absoluteAdapterPosition);

                    CTreeModel treeModel = node.getTreeModelObject();

                    String organizationServerID = treeModel.getChildID();
                    organizationModel = (COrganizationModel) treeModel.getModelObject();
                    int workspaceBITS = organizationModel.getWorkspaceBITS();

                    POH.listener.onClickCreateWorkspace(organizationServerID, workspaceBITS,
                            absoluteAdapterPosition);
                });

                // update workspace
                POH.parentBinding.textViewUpdateIcon.setOnClickListener(view -> {
                    int absoluteAdapterPosition = POH.getAbsoluteAdapterPosition();
                    CNode node = visibleNodes.get(absoluteAdapterPosition);
                    CTreeModel treeModel = node.getTreeModelObject();

                    COrganizationModel organizationModel;
                    organizationModel = (COrganizationModel) treeModel.getModelObject();
                    POH.listener.onClickUpdateOrganization(organizationModel);
                });

                // delete organization
                POH.parentBinding.textViewDeleteIcon.setOnClickListener(view -> {
                    int absoluteAdapterPosition = POH.getAbsoluteAdapterPosition();
                    CNode node = visibleNodes.get(absoluteAdapterPosition);
                    CTreeModel treeModel = node.getTreeModelObject();
                    COrganizationModel organizationModel;
                    organizationModel = (COrganizationModel) treeModel.getModelObject();

                    POH.listener.onClickDeleteOrganization(organizationModel.getOrganizationServerID());
                });
            }
            case WORKSPACE -> {
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }
                SessionChildWorkspaceCardviewBinding childBinding;
                childBinding = DataBindingUtil.inflate(layoutInflater,
                        R.layout.session_child_workspace_cardview, parent, false);
                viewHolder = new cWorkspaceViewHolder(childBinding, this);
                cWorkspaceViewHolder CWH = (cWorkspaceViewHolder) viewHolder;

                // switch to workspace
                CWH.childBinding.cardView.setOnLongClickListener(view -> {
                    int position = CWH.getAbsoluteAdapterPosition();
                    CWH.listener.onLongClickWorkspace(position);
                    return false;
                });

                // update workspace
                CWH.childBinding.textViewUpdateIcon.setOnClickListener(view -> {
                    int absoluteAdapterPosition = CWH.getAbsoluteAdapterPosition();
                    CNode node = visibleNodes.get(absoluteAdapterPosition);
                    CTreeModel treeModel = node.getTreeModelObject();

                    CWorkspaceModel workspaceModel;
                    workspaceModel = (CWorkspaceModel) treeModel.getModelObject();
                    CWH.listener.onClickUpdateWorkspace(workspaceModel);

                });

                // delete workspace
                CWH.childBinding.textViewDeleteIcon.setOnClickListener(view -> {
                    int absoluteAdapterPosition = CWH.getAbsoluteAdapterPosition();
                    CNode node = visibleNodes.get(absoluteAdapterPosition);
                    CNode parentNode = node.getParent();

                    CTreeModel treeModel = node.getTreeModelObject();
                    CTreeModel parentTreeModel = parentNode.getTreeModelObject();

                    CWorkspaceModel workspaceModel;
                    workspaceModel = (CWorkspaceModel) treeModel.getModelObject();

                    COrganizationModel organizationModel;
                    organizationModel = (COrganizationModel) parentTreeModel.getModelObject();

                    CWH.listener.onClickDeleteWorkspace(organizationModel.getWorkspaceBITS(),
                            workspaceModel);
                });
            }
            default -> viewHolder = null;
        }
        return viewHolder;
    }

    @Override
    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CNode node = visibleNodes.get(position);
        CTreeModel treeModel = (CTreeModel) node.getTreeModelObject();

        if (treeModel != null) {
            switch (treeModel.getType()) {
                case ORGANIZATION -> {
                    COrganizationModel organizationModel;
                    organizationModel = (COrganizationModel) treeModel.getModelObject();
                    cOrganizationViewHolder POH = ((cOrganizationViewHolder) viewHolder);

                    /* icon for name of an organization */
                    POH.parentBinding.textViewOrganizationIcon.setTypeface(null, Typeface.NORMAL);
                    POH.parentBinding.textViewOrganizationIcon.setTypeface(
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
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
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    POH.parentBinding.textViewEmailIcon.setTextColor(context.getColor(R.color.black));
                    POH.parentBinding.textViewEmailIcon.setText(context.getResources().getString(R.string.fa_email));
                    POH.parentBinding.textViewEmail.setText(organizationModel.getEmail());
                    POH.parentBinding.textViewWebsiteIcon.setTypeface(null, Typeface.NORMAL);
                    POH.parentBinding.textViewWebsiteIcon.setTypeface(
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    POH.parentBinding.textViewWebsiteIcon.setTextColor(context.getColor(R.color.black));
                    POH.parentBinding.textViewWebsiteIcon.setText(context.getResources().getString(R.string.fa_website));
                    POH.parentBinding.textViewWebsite.setText(organizationModel.getWebsite());

                    /* icon for deleting a record */
                    POH.parentBinding.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    POH.parentBinding.textViewDeleteIcon.setTypeface(
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    POH.parentBinding.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimary));
                    POH.parentBinding.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    //POH.parentBinding.textViewDeleteIcon.setOnClickListener(view -> {
                        //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
                    //});

                    /* icon for saving updated record */
                    POH.parentBinding.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    POH.parentBinding.textViewUpdateIcon.setTypeface(
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    POH.parentBinding.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimary));
                    POH.parentBinding.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    //POH.parentBinding.textViewUpdateIcon.setOnClickListener(view -> {
                        //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
                    //});

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
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    POH.parentBinding.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimary));
                    POH.parentBinding.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));

                    /* the collapse and expansion of the parent logframe */
                    POH.parentBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    POH.parentBinding.textViewDetailIcon.setTypeface(
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    POH.parentBinding.textViewDetailIcon.setTextColor(
                            context.getColor(R.color.colorPrimaryDark));
                    POH.parentBinding.textViewDetailIcon.setVisibility(View.VISIBLE);
                    if (node.isExpand()) {
                        POH.parentBinding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_up));
                    } else {
                        POH.parentBinding.textViewDetailIcon.setText(
                                context.getResources().getString(R.string.fa_angle_down));
                    }
                }
                case WORKSPACE -> {
                    CWorkspaceModel workspaceModel;
                    workspaceModel = (CWorkspaceModel) treeModel.getModelObject();
                    cWorkspaceViewHolder COH = ((cWorkspaceViewHolder) viewHolder);
                    COH.setPaddingLeft(20 * node.getLevel());
                    COH.childBinding.cardView.setCardBackgroundColor(ContextCompat.getColor(
                            context, R.color.child_body_colour));
                    COH.childBinding.textViewName.setText(workspaceModel.getName());
                    COH.childBinding.textViewDescription.setText(workspaceModel.getDescription());

                    /* icon for deleting a record */
                    COH.childBinding.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    COH.childBinding.textViewDeleteIcon.setTypeface(
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    COH.childBinding.textViewDeleteIcon.setTextColor(
                            context.getColor(R.color.colorAccent));
                    COH.childBinding.textViewDeleteIcon.setText(
                            context.getResources().getString(R.string.fa_delete));

                    /* icon for saving updated record */
                    COH.childBinding.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    COH.childBinding.textViewUpdateIcon.setTypeface(
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    COH.childBinding.textViewUpdateIcon.setTextColor(
                            context.getColor(R.color.colorAccent));
                    COH.childBinding.textViewUpdateIcon.setText(
                            context.getResources().getString(R.string.fa_update));
                }
                //COH.childBinding.textViewUpdateIcon.setOnClickListener(view -> {}
                            /*CVH.logFrameListener.onClickUpdateLogFrame(position,
                                    childLogFrameModel));*/
                default ->
                        throw new IllegalStateException("Unexpected value: " + treeModel.getType());
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

//                String charString = charSequence.toString();
//                //Toast.makeText(fragment.getContext(), , Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "onSearchAdapter = " + charString);
//                if (charString.isEmpty()) {
//                    treeModels = unFilteredTreeModels;
//                } else {
//
//                    CIndexedLinkedHashMap<String, CTreeModel> filteredList = new CIndexedLinkedHashMap<>();
//
//                    for (Map.Entry<String, CTreeModel> entry : unFilteredTreeModels.entrySet()) {
//                        String serverID = entry.getKey();
//                        CTreeModel treeModel = (CTreeModel) entry.getValue();
//                        switch (treeModel.getType()) {
//
//                            case ORGANIZATION:
//                                COrganizationModel organizationModel;
//                                organizationModel = (COrganizationModel) treeModel.getModelObject();
//                                if (organizationModel.getName().toLowerCase().
//                                        contains(charString.toLowerCase())) {
//                                    filteredList.put(serverID, treeModel);
//                                }
//                                break;
//
//                            case WORKSPACE://FIXME: check whether filter works for workspaces
//                                CWorkspaceModel workspaceModel;
//                                workspaceModel = (CWorkspaceModel) treeModel.getModelObject();
//                                if (workspaceModel.getName().toLowerCase().
//                                        contains(charString.toLowerCase())) {
//                                    filteredList.put(serverID, treeModel);
//                                }
//                                break;
//                        }
//                    }
//                    treeModels = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.count = treeModels.size();
//                filterResults.values = treeModels;

                return null;//filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                try {
//                    setTreeModel(treeModels);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
            }
        };
    }

    @Override
    public void onClickOrganization(String organizationServerID, int absoluteAdapterPosition) {
        this.absoluteAdapterPosition = absoluteAdapterPosition;
        iViewModel.onClickReadOrganizationWorkspaces(organizationServerID);
    }

    @Override
    public void onLongClickWorkspace(int position) {
        CNode node = visibleNodes.get(position);
        CTreeModel obj = (CTreeModel) node.getTreeModelObject();
        CWorkspaceModel workspaceModel = (CWorkspaceModel) obj.getModelObject();
        iViewModel.onLongClickWorkspace(workspaceModel);
    }

    @Override
    public void onClickUpdateOrganization(COrganizationModel organizationModel) {
        iViewModel.onClickUpdateOrganization(organizationModel);

    }

    @Override
    public void onClickDeleteOrganization(String organizationServerID) {
        iViewModel.onClickDeleteOrganization(organizationServerID);
    }

    @Override
    public void onClickCreateWorkspace(String organizationServerID, int workspaceBITS, int position) {
        CWorkspaceModel workspaceModel = new CWorkspaceModel();
        workspaceModel.setOrganizationServerID(organizationServerID);
        workspaceModel.setWorkspaceServerID(String.valueOf(workspaceBITS));
        iViewModel.onClickCreateWorkspace(workspaceModel);
    }

    @Override
    public void onClickDeleteWorkspace(int workspaceBITS, CWorkspaceModel workspaceModel) {
        iViewModel.onClickDeleteWorkspace(workspaceBITS, workspaceModel);
    }

    @Override
    public void onClickUpdateWorkspace(CWorkspaceModel workspaceModel) {
        iViewModel.onClickUpdateWorkspace(workspaceModel);
    }

    public static class cOrganizationViewHolder extends RecyclerView.ViewHolder {
        private final SessionOrganizationParentCardviewBinding parentBinding;
        private final IOrganizationWorkspaceAdapter listener;
        private final View treeView;

        private cOrganizationViewHolder(SessionOrganizationParentCardviewBinding parentBinding,
                                        IOrganizationWorkspaceAdapter listener) {
            super(parentBinding.getRoot());

            this.parentBinding = parentBinding;
            this.listener = listener;
            this.treeView = parentBinding.getRoot();
        }

        public void setPaddingLeft(int paddingLeft) {
            this.treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cWorkspaceViewHolder extends RecyclerView.ViewHolder {
        private final SessionChildWorkspaceCardviewBinding childBinding;
        private final IOrganizationWorkspaceAdapter listener;
        private final View treeView;

        private cWorkspaceViewHolder(SessionChildWorkspaceCardviewBinding childBinding,
                                     IOrganizationWorkspaceAdapter listener) {
            super(childBinding.getRoot());

            this.childBinding = childBinding;
            this.listener = listener;
            this.treeView = childBinding.getRoot();
        }

        public void setPaddingLeft(int paddingLeft) {
            this.treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}

//    public void updateTreeModels(CTreeModel treeModel) {
//        try {
//            updateTreeModelInTreeAdapter(treeModel);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

/*    public void updateAllNodes(CTreeModel treeModel, int position) {
        try {
            this.addTreeModel2AllNodes(treeModel);
            expandOrCollapse(position);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }*/

//    public void reloadTreeModels(int index, String operation) {
//        switch (operation) {
//            case "ADD":
//                notifyItemInserted(getReversePosition(index));
//                Log.d(TAG, "INDEX = " + index + " - " + getReversePosition(index));
//                break;
//            case "UPDATE":
//                notifyItemChanged(getReversePosition(index));
//                break;
//            case "DELETE":
//                notifyItemRemoved(getReversePosition(index));
//                break;
//            default:
//                notifyDataSetChanged();
//                break;
//        }
//    }