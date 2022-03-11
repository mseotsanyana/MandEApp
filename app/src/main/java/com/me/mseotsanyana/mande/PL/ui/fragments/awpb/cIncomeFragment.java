package com.me.mseotsanyana.mande.PL.ui.fragments.awpb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.wpb.cIncomeModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.awpb.cIncomeAdapter;
import com.me.mseotsanyana.mande.R;

import java.util.ArrayList;

public class cIncomeFragment extends Fragment {
    private static String TAG = cIncomeFragment.class.getSimpleName();
    private cIncomeAdapter incomeAdapter;

    public cIncomeFragment() {
    }

    public static cIncomeFragment newInstance(ArrayList<cIncomeModel> incomeModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("INCOME_MODELS", incomeModels);
        cIncomeFragment fragment = new cIncomeFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.resources_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        assert getArguments() != null;
        ArrayList<cIncomeModel> incomeModels = getArguments().getParcelableArrayList(
                "INCOME_MODELS");

        RecyclerView inputRecyclerView = view.findViewById(R.id.resourcesRecyclerView);
        inputRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        incomeAdapter = new cIncomeAdapter(getActivity(), null, incomeModels);
        inputRecyclerView.setAdapter(incomeAdapter);
        inputRecyclerView.setLayoutManager(llm);
    }

    public cIncomeAdapter getIncomeAdapter() {
        return incomeAdapter;
    }
}