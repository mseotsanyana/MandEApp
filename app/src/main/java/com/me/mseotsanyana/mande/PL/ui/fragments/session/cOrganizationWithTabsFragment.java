package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import androidx.appcompat.app.ActionBar;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cOrganizationViewPagerAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationWithTabsFragmentBinding;

import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/04.
 */

public class cOrganizationWithTabsFragment extends Fragment {
    private static final String TAG = cOrganizationWithTabsFragment.class.getSimpleName();

    private SessionOrganizationWithTabsFragmentBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = DataBindingUtil.inflate(inflater, R.layout.session_organization_with_tabs_fragment,
                container, false);

        initAppBarLayout(binding.toolbar, binding.appName, binding.collapsingToolbarLayout);

        initViewPager2(binding.organizationViewPager2, binding.organizationTabLayout);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initAppBarLayout(Toolbar toolbar, TextView textView,
                                  CollapsingToolbarLayout collapsingToolbarLayout) {
        textView.setText(R.string.app_name);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        collapsingToolbarLayout.setTitle("Organizations");

        /* show the back arrow button */
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

    private void initViewPager2(final ViewPager2 viewPager2, final TabLayout tabLayout) {
        /* setup the pager views */
        cOrganizationViewPagerAdapter organizationViewPagerAdapter;
        organizationViewPagerAdapter = new cOrganizationViewPagerAdapter(requireActivity());

        organizationViewPagerAdapter.addFrag(cOrganizationFragment.newInstance(),"organizations");
        organizationViewPagerAdapter.addFrag(cAgreementFragment.newInstance(), "agreements");

        viewPager2.setAdapter(organizationViewPagerAdapter);

        /* setup the tab layout and add tabs to the view pager2 */
        new TabLayoutMediator(tabLayout,
                viewPager2, (tab, position) ->
                tab.setText(organizationViewPagerAdapter.getPageTitle(position))).attach();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        /* get inflated option menu */
        Menu toolBarMenu = setToolBar();

        binding.toolbar.setOnMenuItemClickListener(
                this::onOptionsItemSelected);

        SearchManager searchManager = (SearchManager) requireActivity().
                getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) toolBarMenu.findItem(R.id.searchItem).getActionView();
        searchView.setSearchableInfo(Objects.requireNonNull(searchManager).
                getSearchableInfo(requireActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        search(searchView);
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

    private Menu setToolBar() {
        binding.toolbar.inflateMenu(R.menu.me_toolbar_menu);
        return binding.toolbar.getMenu();
    }
}
