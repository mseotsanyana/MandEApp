package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.model.session.cStakeholderModel;
import com.me.mseotsanyana.mande.PL.utils.cIndexedLinkedHashMap;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationCardviewBinding;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cStakeholderAdapter extends RecyclerView.Adapter<cStakeholderAdapter.
        cOrganizationViewHolder> implements Filterable {
    private static final String TAG = cStakeholderAdapter.class.getSimpleName();
    //private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private final Context context;
    private cIndexedLinkedHashMap<String, cStakeholderModel> stakeholderModels;
    private cIndexedLinkedHashMap<String, cStakeholderModel> filteredStakeholderModels;

    private LayoutInflater layoutInflater;

    Gson gson = new Gson();

    public cStakeholderAdapter(Context context, cIndexedLinkedHashMap<String,
            cStakeholderModel> stakeholderModels) {
        this.context = context;
        this.stakeholderModels = stakeholderModels;
        this.filteredStakeholderModels = stakeholderModels;
    }

    public int getReversePosition(int index) {
        if (filteredStakeholderModels != null && !filteredStakeholderModels.isEmpty())
            return filteredStakeholderModels.size() - 1 - index;
        else return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void reloadList(cIndexedLinkedHashMap<String, cStakeholderModel> list) {
        stakeholderModels = list;
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

    public cIndexedLinkedHashMap<String, cStakeholderModel> getOrganizationList() {
        return filteredStakeholderModels;
    }

    @Override
    public int getItemCount() {
        return filteredStakeholderModels.size();
    }

    @NonNull
    @Override
    public cOrganizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        SessionOrganizationCardviewBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.session_organization_cardview, parent, false);

//        View view = LayoutInflater.from(parent.getContext()).inflate(
//                R.layout.session_organization_cardview, parent, false);
        return new cOrganizationViewHolder(binding);
    }

    public void onBindViewHolder(@NonNull cOrganizationViewHolder OH, int position) {
        cStakeholderModel stakeholderModel;
        stakeholderModel = filteredStakeholderModels.getItemByIndex(getReversePosition(position));

        Log.d(TAG, "ORGANIZATION ========= " + gson.toJson(stakeholderModel));

        /* icon for name of an organization */
        OH.binding.textViewOrganizationIcon.setTypeface(null, Typeface.NORMAL);
        OH.binding.textViewOrganizationIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));


        if (stakeholderModel.getTypeID() == 0) {
            OH.binding.textViewOrganizationIcon.setTextColor(Color.RED);
        } else if (stakeholderModel.getTypeID() == 1) {
            OH.binding.textViewOrganizationIcon.setTextColor(Color.GREEN);
        } else if (stakeholderModel.getTypeID() == 2) {
            OH.binding.textViewOrganizationIcon.setTextColor(Color.BLUE);
        } else {
            OH.binding.textViewOrganizationIcon.setTextColor(Color.MAGENTA);
        }

        OH.binding.textViewOrganizationIcon.setText(context.getResources().getString(R.string.fa_organization));
        OH.binding.textViewName.setText(stakeholderModel.getName());

        OH.binding.textViewEmailIcon.setTypeface(null, Typeface.NORMAL);
        OH.binding.textViewEmailIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.binding.textViewEmailIcon.setTextColor(context.getColor(R.color.black));
        OH.binding.textViewEmailIcon.setText(context.getResources().getString(R.string.fa_email));
        OH.binding.textViewEmail.setText(stakeholderModel.getEmail());

        OH.binding.textViewWebsiteIcon.setTypeface(null, Typeface.NORMAL);
        OH.binding.textViewWebsiteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.binding.textViewWebsiteIcon.setTextColor(context.getColor(R.color.black));
        OH.binding.textViewWebsiteIcon.setText(context.getResources().getString(R.string.fa_website));
        OH.binding.textViewWebsite.setText(stakeholderModel.getWebsite());

        /* icon for deleting a record */
        OH.binding.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        OH.binding.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.binding.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.binding.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
        OH.binding.textViewDeleteIcon.setOnClickListener(view -> {
            //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
        });

        /* icon for saving updated record */
        OH.binding.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        OH.binding.textViewUpdateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.binding.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.binding.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
        OH.binding.textViewUpdateIcon.setOnClickListener(view -> {
            //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
        });

        /* icon for joining a record */
        OH.binding.textViewJoinIcon.setTypeface(null, Typeface.NORMAL);
        OH.binding.textViewJoinIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.binding.textViewJoinIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.binding.textViewJoinIcon.setText(context.getResources().getString(R.string.fa_join));
        OH.binding.textViewJoinIcon.setOnClickListener(view -> {
            //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
        });

        /* icon for creating a record */
        OH.binding.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
        OH.binding.textViewCreateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.binding.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.binding.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
        OH.binding.textViewCreateIcon.setOnClickListener(view -> {
            //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
        });

        // collapse and expansion of the details of the role
        OH.binding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        OH.binding.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.binding.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        OH.binding.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
//        OH.binding.textViewDetailIcon.setOnClickListener(view -> {
//            if (!(OH.binding.expandableLayout.isExpanded())) {
//                OH.binding.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
//            } else {
//                OH.binding.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
//            }
//
//            OH.binding.expandableLayout.toggle();
//        });
    }

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

    public static class cOrganizationViewHolder extends RecyclerView.ViewHolder {
        private final SessionOrganizationCardviewBinding binding;

//        private final CExpandableLayout expandableLayout;
//
//        private final AppCompatTextView textViewName;
//        private final AppCompatTextView textViewEmail;
//        private final AppCompatTextView textViewWebsite;
//
//        private final AppCompatTextView textViewOrganizationIcon;
//        private final AppCompatTextView textViewEmailIcon;
//        private final AppCompatTextView textViewWebsiteIcon;
//
//        private final AppCompatTextView textViewDeleteIcon;
//        private final AppCompatTextView textViewUpdateIcon;
//        private final AppCompatTextView textViewJoinIcon;
//        private final AppCompatTextView textViewCreateIcon;
//        private final AppCompatTextView textViewDetailIcon;

        //private final View viewHolder;

        private cOrganizationViewHolder(SessionOrganizationCardviewBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            //this.viewHolder = viewHolder;

            //CardView cardView = viewHolder.findViewById(R.id.cardView);
//            this.expandableLayout = viewHolder.findViewById(R.id.expandableLayout);
//
//            this.textViewName = viewHolder.findViewById(R.id.textViewName);
//            this.textViewEmail = viewHolder.findViewById(R.id.textViewEmail);
//            this.textViewWebsite = viewHolder.findViewById(R.id.textViewWebsite);
//
//            this.textViewOrganizationIcon = viewHolder.findViewById(R.id.textViewOrganizationIcon);
//            this.textViewEmailIcon = viewHolder.findViewById(R.id.textViewEmailIcon);
//            this.textViewWebsiteIcon = viewHolder.findViewById(R.id.textViewWebsiteIcon);
//
//            this.textViewDeleteIcon = viewHolder.findViewById(R.id.textViewDeleteIcon);
//            this.textViewUpdateIcon = viewHolder.findViewById(R.id.textViewUpdateIcon);
//            this.textViewJoinIcon = viewHolder.findViewById(R.id.textViewJoinIcon);
//            this.textViewCreateIcon = viewHolder.findViewById(R.id.textViewCreateIcon);
//            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            binding.cardView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}