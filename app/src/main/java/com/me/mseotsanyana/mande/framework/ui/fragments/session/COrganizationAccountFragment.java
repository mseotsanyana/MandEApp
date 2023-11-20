package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cOrganizationAccountsPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iOrganizationAccountPresenter;
import com.me.mseotsanyana.mande.framework.ui.adapters.session.cOrganizationAccountAdapter;
import com.me.mseotsanyana.mande.PL.utils.cIndexedLinkedHashMap;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.mande.databinding.SessionOrgAccountsFragmentBinding;
import com.me.mseotsanyana.mande.interfaceadapters.repository.firestore.common.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.interfaceadapters.repository.firestore.session.cOrganizationFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.usecases.executor.Impl.cThreadExecutorImpl;

import java.util.Map;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/04.
 */

public class COrganizationAccountFragment extends Fragment implements iOrganizationAccountPresenter.View {
    private static final String TAG = COrganizationAccountFragment.class.getSimpleName();

    private iOrganizationAccountPresenter organizationAccountPresenter;

    private cOrganizationAccountAdapter organizationAccountAdapter;

    private SessionOrgAccountsFragmentBinding binding;

    public COrganizationAccountFragment(){

    }

    public static COrganizationAccountFragment newInstance() {
        return new COrganizationAccountFragment();
    }

    /**
     * this method is fired 2nd, before views are created for the fragment,
     * the onCreate method is called when the fragment instance is being created,
     * or re-created use onCreate for any standard setup that does not require
     * the activity to be fully created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        organizationAccountPresenter.resume();
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = DataBindingUtil.inflate(inflater, R.layout.session_org_accounts_fragment,
                container, false);

        initAppBarLayout(binding.toolbarLayout.toolbar, binding.toolbarLayout.appName,
                binding.toolbarLayout.collapsingToolbarLayout);

        return binding.getRoot();
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* initialise data structures */
        initDataStructures();
        /* initialise draggable FAB */
        initDraggableFAB();
    }

    private void initDataStructures() {
        organizationAccountPresenter = new cOrganizationAccountsPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cOrganizationFirestoreRepositoryImpl(getContext()));
    }

    private void initAppBarLayout(Toolbar toolbar, TextView textView,
                                  CollapsingToolbarLayout collapsingToolbarLayout) {
        textView.setText(R.string.app_name);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        collapsingToolbarLayout.setTitle("My Accounts");

        /* show the back arrow button*/
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
            }
        }
    }

    /* initialise the floating action button */
    private void initDraggableFAB() {
        binding.organizationAccountFAB.setOnClickListener(view -> {

        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getActivity().getMenuInflater().inflate(R.menu.drawer_menu_main, menu);
        inflater.inflate(R.menu.me_toolbar_menu, menu);

        //getting the search view from the menu
        MenuItem toolBarMenu = menu.findItem(R.id.searchItem);

        /* getting search manager from system service */
        SearchManager searchManager = (SearchManager) requireActivity().
                getSystemService(Context.SEARCH_SERVICE);
        /* getting the search view */
        SearchView searchView = (SearchView) toolBarMenu.getActionView();
        /* you can put a hint for the search input field */
        searchView.setQueryHint("Search projects...");
        searchView.setSearchableInfo(Objects.requireNonNull(searchManager).
                getSearchableInfo(requireActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        /* by setting it true we are making it iconified
           so the search input will show up after taping the search iconified
           if you want to make it visible all the time make it false
         */
        //searchView.setIconifiedByDefault(true);
        search(searchView);

        /* end */
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.uploadItem) {
            Log.d(TAG, "Stub for information button");
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //organizationAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    @Override
    public void onReadOrganizationAccountsFailed(String msg) {

    }

    @Override
    public void onReadOrganizationAccountsSucceeded(Map<String, Object> orgAccountsMap,
                                                    String operation) {
        if (organizationAccountAdapter == null)
            setAdapter(new cIndexedLinkedHashMap<>());

        if (operation.equals("ADD")) {
            organizationAccountAdapter.getOrgAccountList().add(
                    (String)orgAccountsMap.get("userAccountServerID"), orgAccountsMap);
            organizationAccountAdapter.reloadList(
                    organizationAccountAdapter.getOrgAccountList().size() - 1, operation);
        }

        if (operation.equals("UPDATE")) {
            organizationAccountAdapter.getOrgAccountList().update(
                    (String)orgAccountsMap.get("userAccountServerID"), orgAccountsMap);
            organizationAccountAdapter.reloadList(
                    organizationAccountAdapter.getOrgAccountList().getIndexByKey(
                            (String)orgAccountsMap.get("userAccountServerID")), operation);
        }

        if (operation.equals("DELETE")) {
            organizationAccountAdapter.getOrgAccountList().update(
                    (String)orgAccountsMap.get("userAccountServerID"), orgAccountsMap);
            organizationAccountAdapter.reloadList(
                    organizationAccountAdapter.getOrgAccountList().getIndexByKey(
                            (String)orgAccountsMap.get("userAccountServerID")), operation);
        }
    }

    private void setAdapter(cIndexedLinkedHashMap<String, Map<String, Object>>
                                    orgAccountModels) {
        if (organizationAccountAdapter == null) {
            organizationAccountAdapter = new cOrganizationAccountAdapter(getActivity(),
                    orgAccountModels);
            binding.organizationAccountRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(requireActivity());
            binding.organizationAccountRecyclerView.setLayoutManager(mLayoutManager);
            binding.organizationAccountRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.organizationAccountRecyclerView.setAdapter(organizationAccountAdapter);
        } else {
            organizationAccountAdapter.reloadList(orgAccountModels);
        }
    }


    @Override
    public void showProgress() {
        binding.includeProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.includeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }
}
