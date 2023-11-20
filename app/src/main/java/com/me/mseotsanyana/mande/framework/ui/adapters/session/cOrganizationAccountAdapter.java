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

import com.me.mseotsanyana.mande.PL.utils.cIndexedLinkedHashMap;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.databinding.SessionOrgAccountCardviewBinding;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cOrganizationAccountAdapter extends
        RecyclerView.Adapter<cOrganizationAccountAdapter.cOrganizationAccountViewHolder> implements
        Filterable {
    //private static final String TAG = cOrganizationMemberAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private final Context context;
    private cIndexedLinkedHashMap<String, Map<String, Object>> orgAccountModels;
    private cIndexedLinkedHashMap<String, Map<String, Object>> filterOrgAccountModels;

    private LayoutInflater layoutInflater;

    public cOrganizationAccountAdapter(Context context,
                                       cIndexedLinkedHashMap<String, Map<String, Object>> orgAccountsMap) {
        this.context = context;
        this.orgAccountModels = orgAccountsMap;
        this.filterOrgAccountModels = orgAccountsMap;
    }

    public void setOrganizationAccountModels(cIndexedLinkedHashMap<String, Map<String, Object>> orgAccountsMap) {
        this.orgAccountModels = orgAccountsMap;
        this.filterOrgAccountModels = orgAccountsMap;
    }

    public int getReversePosition(int index) {
        if (filterOrgAccountModels != null && !filterOrgAccountModels.isEmpty())
            return filterOrgAccountModels.size() - 1 - index;
        else return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void reloadList(cIndexedLinkedHashMap<String, Map<String, Object>> list) {
        orgAccountModels = list;
        filterOrgAccountModels = list;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void reloadList(int index, @NonNull String operation) {
        switch (operation) {
            case "ADD":
                notifyItemInserted(getReversePosition(index));
                //Log.d(TAG, "INDEX = " + index + " - " + getReversePosition(index));
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

    public cIndexedLinkedHashMap<String, Map<String, Object>> getOrgAccountList() {
        return filterOrgAccountModels;
    }
    @Override
    public int getItemCount() {
        return filterOrgAccountModels.size();
    }

    @NonNull
    @Override
    public cOrganizationAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        SessionOrgAccountCardviewBinding parentBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.session_org_account_cardview, parent,
                false);

        cOrganizationAccountViewHolder viewHolder;
        viewHolder = new cOrganizationAccountViewHolder(parentBinding);
        cOrganizationAccountViewHolder OAH = (cOrganizationAccountViewHolder)viewHolder;


        // initialise organization listeners

//        View view = LayoutInflater.from(parent.getContext()).inflate(
//                R.layout.session_org_account_cardview, parent, false);
//        return new cOrganizationAccountViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull cOrganizationAccountViewHolder OH, int position) {
        Map<String, Object> orgAccount = this.filterOrgAccountModels.getItemByIndex(getReversePosition(position));

        //Map<String, String> orgAccount = this.filterOrgAccountModels.get("name");
        Log.d("TAG", "MY ACCOUNTS =========== "+orgAccount);

//        OH.circleImageViewUser.setImageResource(gray);
        OH.parentBinding.textViewOrganizationIcon.setTypeface(null, Typeface.NORMAL);
        OH.parentBinding.textViewOrganizationIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        Long typeID = (Long)orgAccount.get("typeID");
        if (typeID != null && typeID == 0) {
            OH.parentBinding.textViewOrganizationIcon.setTextColor(Color.RED);
        } else if (typeID != null && typeID  == 1) {
            OH.parentBinding.textViewOrganizationIcon.setTextColor(Color.GREEN);
        } else if (typeID != null && typeID  == 2) {
            OH.parentBinding.textViewOrganizationIcon.setTextColor(Color.BLUE);
        } else {
            OH.parentBinding.textViewOrganizationIcon.setTextColor(Color.MAGENTA);
        }

        OH.parentBinding.textViewOrganizationIcon.setText(context.getResources().getString(R.string.fa_organization));
        OH.parentBinding.textViewName.setText((String) orgAccount.get("name"));

        OH.parentBinding.textViewEmailIcon.setTypeface(null, Typeface.NORMAL);
        OH.parentBinding.textViewEmailIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        //OH.parentBinding.textViewEmailIcon.setTextSize(context.getResources().));
        OH.parentBinding.textViewEmailIcon.setTextColor(context.getColor(R.color.black));
        OH.parentBinding.textViewEmailIcon.setText(context.getResources().getString(R.string.fa_email));
        OH.parentBinding.textViewEmail.setText((String) orgAccount.get("email"));


        Integer bit = (Integer) orgAccount.get("statusBIT");
        if (bit != null && bit == 4) {
            OH.parentBinding.textViewStatus.setText("Active");
            OH.parentBinding.textViewStatus.setTextColor(context.getColor(R.color.colorPrimaryDark));
        }

        if(Objects.requireNonNull(orgAccount.get("planServerID")).equals("FREE_SUB")){
            OH.parentBinding.textViewSubscription.setText("Free Subscription");
        }

        OH.parentBinding.textViewCreatedDate.setText(sdf.format(orgAccount.get("createdDate")));
        OH.parentBinding.textViewModifiedDate.setText(sdf.format(orgAccount.get("modifiedDate")));


        /* icon for saving updated record */
        OH.parentBinding.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        OH.parentBinding.textViewUpdateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.parentBinding.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.parentBinding.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
        OH.parentBinding.textViewUpdateIcon.setOnClickListener(view -> {
            //HPH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
        });

        /* icon for deleting a record */
        OH.parentBinding.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        OH.parentBinding.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.parentBinding.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimary));
        OH.parentBinding.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
        OH.parentBinding.textViewDeleteIcon.setOnClickListener(view -> {
            //PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filterOrgAccountModels = orgAccountModels;
                } else {

//                    List<String, Map<String, String>> filteredList = new ArrayList<>();
//                    for (CUserProfileModel userProfileModel : userProfileModels) {
//                        if (userProfileModel.getName().toLowerCase().
//                                contains(charString.toLowerCase())) {
//                            filteredList.add(userProfileModel);
//                        }
//                    }
//
//                    filterOrgAccountModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filterOrgAccountModels.size();
                filterResults.values = filterOrgAccountModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterOrgAccountModels = (cIndexedLinkedHashMap<String, Map<String, Object>>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class cOrganizationAccountViewHolder extends RecyclerView.ViewHolder {
        SessionOrgAccountCardviewBinding parentBinding;

//        private final CircleImageView circleImageViewUser;
//        private final AppCompatTextView textViewName;
//        private final AppCompatTextView textViewEmail;
//        private final AppCompatTextView textViewCreatedDate;
//
//        private final AppCompatTextView textViewDeleteIcon;
//
        private final View viewHolder;

        private cOrganizationAccountViewHolder(SessionOrgAccountCardviewBinding parentBinding) {
            super(parentBinding.getRoot());

            this.parentBinding = parentBinding;
            this.viewHolder = parentBinding.getRoot();

//            super(viewHolder);
//
//            this.circleImageViewUser = (CircleImageView)
//                    viewHolder.findViewById(R.id.circleImageViewUser);
//
//            this.textViewName = viewHolder.findViewById(R.id.textViewName);
//            this.textViewEmail = viewHolder.findViewById(R.id.textViewEmail);
//            this.textViewCreatedDate = viewHolder.findViewById(R.id.textViewCreatedDate);
//
//            this.textViewDeleteIcon = viewHolder.findViewById(R.id.textViewDeleteIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}