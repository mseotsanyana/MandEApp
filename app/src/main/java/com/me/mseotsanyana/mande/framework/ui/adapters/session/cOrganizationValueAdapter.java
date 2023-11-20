package com.me.mseotsanyana.mande.framework.ui.adapters.session;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.domain.entities.models.session.cValueModel;
import com.me.mseotsanyana.mande.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by mseotsanyana on 2016/08/02.
 */

public class cOrganizationValueAdapter extends RecyclerView.Adapter<cOrganizationValueAdapter.cOrganizationMoreViewHolder> {

    Context context;
    private List<cValueModel> valueList = new ArrayList<>();
    LayoutInflater inflater;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    public cOrganizationValueAdapter(Context context, List<cValueModel> detailList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.valueList = detailList;
    }

    @Override
    public int getItemCount() {
        return valueList.size();
    }

    public cValueModel getItem(int position) {
        return valueList.get(position);
    }

    public void removeItem(int position) {
        valueList.remove(position);
    }

    public void addItem(cValueModel item) {
        valueList.add(item);
    }

    public void updateItem(cValueModel item, int position) {
        removeItem(position);
        valueList.add(position, item);
    }

    @Override
    public cOrganizationMoreViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.organization_values_cardview, viewGroup, false);

        return new cOrganizationMoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cOrganizationMoreViewHolder organizationMoreViewHolder, int position) {
        cValueModel valueDomain = valueList.get(position);
        organizationMoreViewHolder.valBodyInfo.setText(valueDomain.getName());
    }

    public class cOrganizationMoreViewHolder extends RecyclerView.ViewHolder {
        //protected TextView valHeader;
        protected TextView valBodyInfo;


        public cOrganizationMoreViewHolder(final View view) {
            super(view);

            //valHeader = (TextView) view.findViewById(R.id.valHeader);
            valBodyInfo = (TextView) view.findViewById(R.id.valBodyInfo);

        }
    }
}
