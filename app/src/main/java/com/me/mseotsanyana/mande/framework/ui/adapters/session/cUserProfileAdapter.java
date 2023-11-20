package com.me.mseotsanyana.mande.framework.ui.adapters.session;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iUserProfilePresenter;
import com.me.mseotsanyana.mande.framework.ports.adapters.session.iAdapterUserProfileListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.OLD.cConstant;
import com.me.mseotsanyana.mande.framework.utils.CFontManager;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cUserProfileAdapter extends RecyclerView.Adapter<cUserProfileAdapter.cUserProfileViewHolder>
        implements iAdapterUserProfileListener, Filterable {
    private static final String TAG = cUserProfileAdapter.class.getSimpleName();
    private static final SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private final Context context;
    private List<CUserProfileModel> userProfileModels;
    private List<CUserProfileModel> filteredUserProfileModels;
    //private cIndexedLinkedHashMap<String, cUserProfileModel> userProfiles;

    private final SparseBooleanArray selectedItems;

    private final iUserProfilePresenter.View iUserProfilePresenterView;

    Gson gson = new Gson();

    public cUserProfileAdapter(Context context, List<CUserProfileModel> userProfileModels,
                               iUserProfilePresenter.View iUserProfilePresenterView) {
        this.context = context;
        this.userProfileModels = userProfileModels;
        this.filteredUserProfileModels = userProfileModels;
        this.iUserProfilePresenterView = iUserProfilePresenterView;

        this.selectedItems = new SparseBooleanArray();
    }

    @Override
    public int getItemCount() {
        return filteredUserProfileModels.size();
    }

    @NonNull
    @Override
    public cUserProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.session_userprofile_cardview, parent, false);
        cUserProfileViewHolder holder = new cUserProfileViewHolder(view, this);

        // set interface between the adapter and view holder
        holder.circleImageViewUser.setOnClickListener(v -> {
            int position = holder.getAbsoluteAdapterPosition();
            CUserProfileModel userProfileModel = filteredUserProfileModels.get(position);
            holder.listener.onClickUpdateUserProfileImage(userProfileModel);
        });

        // set listener on the holder
        holder.cardView.setOnLongClickListener(v -> {
            int position = holder.getAbsoluteAdapterPosition();
            if (selectedItems.get(position, false)) {
                selectedItems.delete(position);
                holder.cardView.setCardBackgroundColor(Color.WHITE);
            } else {
                selectedItems.put(position, true);
                holder.cardView.setCardBackgroundColor(Color.CYAN);
            }

            holder.listener.onLongClickUserProfile(selectedItems);

            return true;
        });

        return holder;
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull cUserProfileViewHolder OH, int position) {
        CUserProfileModel userProfileModel = this.filteredUserProfileModels.get(position);

        if (selectedItems.get(position, false)) {
            OH.cardView.setCardBackgroundColor(Color.CYAN);
        } else {
            OH.cardView.setCardBackgroundColor(Color.WHITE);
        }

        // profile photo
        Picasso.get().load(Uri.parse(userProfileModel.getPhotoUrl()))
                .placeholder(R.drawable.me_default_avatar)
                .fit()
                .centerCrop()
                .into(OH.circleImageViewUser);

        // full name
        OH.textViewFullname.setText(userProfileModel.getName() +
                " " + userProfileModel.getSurname());
        // designation
        OH.textViewDesignation.setText(userProfileModel.getDesignation());

        // email
        OH.textViewEmailIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewEmailIcon.setTypeface(
                CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
        OH.textViewEmailIcon.setTextColor(context.getColor(R.color.black));
        OH.textViewEmailIcon.setText(context.getResources().getString(R.string.fa_email));
        OH.textViewEmail.setText(userProfileModel.getEmail());

        // phone
        OH.textViewPhoneIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewPhoneIcon.setTypeface(
                CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
        OH.textViewPhoneIcon.setTextColor(context.getColor(R.color.black));
        OH.textViewPhoneIcon.setText(context.getResources().getString(R.string.fa_phone));
        OH.textViewPhone.setText(userProfileModel.getPhone());

        // website
        OH.textViewWebsiteIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewWebsiteIcon.setTypeface(
                CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
        OH.textViewWebsiteIcon.setTextColor(context.getColor(R.color.black));
        OH.textViewWebsiteIcon.setText(context.getResources().getString(R.string.fa_website));
        OH.textViewWebsite.setText(userProfileModel.getWebsite());

        // location
        OH.textViewLocationIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewLocationIcon.setTypeface(
                CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
        OH.textViewLocationIcon.setTextColor(context.getColor(R.color.black));
        OH.textViewLocationIcon.setText(context.getResources().getString(R.string.fa_location));
        OH.textViewLocation.setText(userProfileModel.getLocation());

        OH.textViewCreatedDateCaption.setText(
                context.getResources().getString(R.string.common_created_on));
        OH.textViewCreatedDate.setText(sdf.format(userProfileModel.getCreatedDate()));
        OH.textViewModifiedDateCaption.setText(
                context.getResources().getString(R.string.common_modified_on));
        OH.textViewModifiedDate.setText(sdf.format(userProfileModel.getModifiedDate()));

        /* icon for deleting a record */
        OH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewDeleteIcon.setTypeface(
                CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
        OH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
        OH.textViewDeleteIcon.setOnClickListener(view -> {
            //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
        });

        /* icon for saving updated record */
        OH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewUpdateIcon.setTypeface(
                CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
        OH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
        OH.textViewUpdateIcon.setOnClickListener(view -> {
            //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
        });

        /* icon for joining a record */
        OH.textViewJoinIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewJoinIcon.setTypeface(
                CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
        OH.textViewJoinIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.textViewJoinIcon.setText(context.getResources().getString(R.string.fa_join));
        OH.textViewJoinIcon.setOnClickListener(view -> {
            //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
        });

        /* icon for changing ownership of the record */
        OH.textViewChangeIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewChangeIcon.setTypeface(
                CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
        OH.textViewChangeIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.textViewChangeIcon.setText(context.getResources().getString(R.string.fa_change));
        OH.textViewChangeIcon.setOnClickListener(view -> {
            //PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredUserProfileModels = userProfileModels;
                } else {

                    ArrayList<CUserProfileModel> filteredList = new ArrayList<>();
                    for (CUserProfileModel userProfileModel : userProfileModels) {
                        if (userProfileModel.getName().toLowerCase().
                                contains(charString.toLowerCase()) ||
                                userProfileModel.getSurname().toLowerCase().
                                        contains(charString.toLowerCase())) {
                            filteredList.add(userProfileModel);
                        }
                    }

                    filteredUserProfileModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredUserProfileModels.size();
                filterResults.values = filteredUserProfileModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserProfileModels = (List<CUserProfileModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    //FIXME: refresh not working maybe read listener
    public void updateUserProfileImage(CUserProfileModel userProfileModel) {
        if (userProfileModel == null)
            return;

        for (int pos = 0; pos < filteredUserProfileModels.size(); pos++) {
            if (userProfileModel.getUserServerID().equals(
                    filteredUserProfileModels.get(pos).getUserServerID())) {
                filteredUserProfileModels.get(pos).setPhotoUrl(userProfileModel.getPhotoUrl());
                notifyItemChanged(pos);
                break;
            }
        }
    }

    public void setUserProfileModels(List<CUserProfileModel> userProfileModels) {
        this.userProfileModels = userProfileModels;
        this.filteredUserProfileModels = userProfileModels;
    }

    public List<CUserProfileModel> getUserProfileModels() {
        return userProfileModels;
    }

    private int getReversedPosition(int index) {
        if (userProfileModels != null && !userProfileModels.isEmpty())
            return userProfileModels.size() - index - 1;
        return 0;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void reloadList(List<CUserProfileModel> userProfileModels){
        this.userProfileModels = userProfileModels;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void reloadList(int index, String operation){
        switch (operation){
            case "ADD":
                notifyItemInserted(getReversedPosition(index));
                break;
            case "UPDATE":
                notifyItemChanged(getReversedPosition(index));
                break;
            case "DELETE":
                notifyItemRemoved(getReversedPosition(index));
            default:
                notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onClickUpdateUserProfileImage(CUserProfileModel userProfileModel) {
        iUserProfilePresenterView.onClickUserProfileImage(userProfileModel);
    }

    @Override
    public void onLongClickUserProfile(SparseBooleanArray selectedItems) {
        Log.d(TAG, "SELECTED USER PROFILES ====>>>> " +selectedItems);
    }

    public static class cUserProfileViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout linearLayout;
        private final CardView cardView;

        private final CircleImageView circleImageViewUser;

        private final AppCompatTextView textViewFullname;
        private final AppCompatTextView textViewDesignation;

        private final AppCompatTextView textViewEmailIcon;
        private final AppCompatTextView textViewEmail;
        private final AppCompatTextView textViewPhoneIcon;
        private final AppCompatTextView textViewPhone;
        private final AppCompatTextView textViewWebsiteIcon;
        private final AppCompatTextView textViewWebsite;
        private final AppCompatTextView textViewLocationIcon;
        private final AppCompatTextView textViewLocation;
        private final AppCompatTextView textViewCreatedDateCaption;
        private final AppCompatTextView textViewCreatedDate;
        private final AppCompatTextView textViewModifiedDateCaption;
        private final AppCompatTextView textViewModifiedDate;

        private final AppCompatTextView textViewDeleteIcon;
        private final AppCompatTextView textViewUpdateIcon;
        private final AppCompatTextView textViewJoinIcon;
        private final AppCompatTextView textViewChangeIcon;

        private final View viewHolder;
        private final iAdapterUserProfileListener listener;

        private cUserProfileViewHolder(final View viewHolder,
                                       iAdapterUserProfileListener listener) {
            super(viewHolder);
            this.viewHolder = viewHolder;
            this.listener = listener;

            this.linearLayout = viewHolder.findViewById(R.id.linearLayout);
            this.cardView = viewHolder.findViewById(R.id.cardView);

            this.circleImageViewUser = viewHolder.findViewById(R.id.circleImageViewUser);

            this.textViewFullname = viewHolder.findViewById(R.id.textViewFullname);
            this.textViewDesignation = viewHolder.findViewById(R.id.textViewDesignation);
            this.textViewEmail = viewHolder.findViewById(R.id.textViewEmail);
            this.textViewEmailIcon = viewHolder.findViewById(R.id.textViewEmailIcon);
            this.textViewPhone = viewHolder.findViewById(R.id.textViewPhone);
            this.textViewPhoneIcon = viewHolder.findViewById(R.id.textViewPhoneIcon);
            this.textViewWebsite = viewHolder.findViewById(R.id.textViewWebsite);
            this.textViewWebsiteIcon = viewHolder.findViewById(R.id.textViewWebsiteIcon);
            this.textViewLocation = viewHolder.findViewById(R.id.textViewLocation);
            this.textViewLocationIcon = viewHolder.findViewById(R.id.textViewLocationIcon);
            this.textViewCreatedDateCaption = viewHolder.findViewById(
                    R.id.textViewCreatedDateCaption);
            this.textViewCreatedDate = viewHolder.findViewById(R.id.textViewCreatedDate);
            this.textViewModifiedDateCaption = viewHolder.findViewById(
                    R.id.textViewModifiedDateCaption);
            this.textViewModifiedDate = viewHolder.findViewById(R.id.textViewModifiedDate);

            this.textViewDeleteIcon = viewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewUpdateIcon = viewHolder.findViewById(R.id.textViewUpdateIcon);
            this.textViewJoinIcon = viewHolder.findViewById(R.id.textViewJoinIcon);
            this.textViewChangeIcon = viewHolder.findViewById(R.id.textViewChangeIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}