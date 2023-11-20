package com.me.mseotsanyana.mande.framework.modelviews.session;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationController;
import com.me.mseotsanyana.mande.framework.ui.adapters.session.cOrganizationViewPagerAdapter;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.COrganizationWorkspaceFragment;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.cAgreementFragment;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationFragmentBinding;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.framework.ui.adapters.session.COrganizationAdapter;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.COrganizationFragment;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class COrganizationModelViewModel implements IOrganizationController.IViewModel {
    /* organization binding */
    private final SessionOrganizationFragmentBinding binding;
    /* organization fragment */
    private final COrganizationFragment view;
    /* organization adapters */
    private COrganizationAdapter adapter;


    public COrganizationModelViewModel(COrganizationFragment view,
                                       SessionOrganizationFragmentBinding binding) {
        this.binding = binding;
        this.view = view;
        //this.adapter = new COrganizationAdapter();

    }

//    public IOrganizationController getController() {
//        return controller;
//    }

    public void initDataStructures() {
        /* instantiate presenters */
        //controller =
    }

    public void initViews(@NonNull View view) {
//        // initialise activity associated with the fragment
//        AppCompatActivity activity = ((AppCompatActivity) view.getActivity());
//
//        // initialise and show the back arrow button on the toolbar
//        Toolbar toolbar = view.findViewById(R.id.toolbar);
//        assert activity != null;
//        activity.setSupportActionBar(toolbar);
//        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);
//
//        // initialise the collapsing toolbar layout
//        CollapsingToolbarLayout collapsingToolbarLayout;
//        collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
//        collapsingToolbarLayout.setTitle(
//                requireContext().getResources().getText(R.string.project_list_title));
//
//        // initialise and attach recycler view with the adapter
//        List<cTreeModel> projectTreeModels = new ArrayList<>();
//        organizationAdapter = new COrganizationAdapter(view.getActivity(),
//                this, projectTreeModels);
//        binding.organizationRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        binding.organizationRecyclerView.setLayoutManager(layoutManager);
//        binding.organizationRecyclerView.setAdapter(organizationAdapter);
//
        // initialise progress bar
//        includeProgressBar = view.findViewById(R.id.includeProgressBar);

        // initialise floating action button
        binding.organizationFAB.setOnClickListener(v -> {
            COrganizationModel organizationModel = new COrganizationModel();
            this.view.getController().createOrganization(organizationModel);
        });
    }

    private void initAppBarLayout(Toolbar toolbar, TextView textView,
                                  CollapsingToolbarLayout collapsingToolbarLayout) {
        textView.setText(R.string.app_name);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        collapsingToolbarLayout.setTitle("Organizations????");

        /* show the back arrow button */
        AppCompatActivity activity = ((AppCompatActivity) view.getActivity());
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
        organizationViewPagerAdapter = new cOrganizationViewPagerAdapter(view.requireActivity());

        organizationViewPagerAdapter.addFrag(COrganizationWorkspaceFragment.newInstance(), "organizations");
        organizationViewPagerAdapter.addFrag(cAgreementFragment.newInstance(), "agreements");

        viewPager2.setAdapter(organizationViewPagerAdapter);

        /* setup the tab layout and add tabs to the view pager2 */
        new TabLayoutMediator(tabLayout,
                viewPager2, (tab, position) ->
                tab.setText(organizationViewPagerAdapter.getPageTitle(position))).attach();
    }
//
//    @Override
//    public void onClickCreateOrganization() {
//
//    }
//
//    @Override
//    public void onLongClickWorkspace(CWorkspaceModel workspaceModel) {
//
//    }

    @Override
    public void onCreateOrganizationFailed(String msg) {

    }

    @Override
    public void onCreateOrganizationSucceeded(String msg) {

    }

    @Override
    public void onReadOrganizationFailed(String msg) {
        Toast.makeText(view.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReadOrganizationSucceeded(COrganizationModel organizationModel, String operation) {

    }

    @Override
    public void onReadOrganizationsFailed(String msg) {

    }

    @Override
    public void onReadOrganizationsSucceeded(List<cTreeModel> treeModels) {

    }

    @Override
    public void onSwitchOrganizationWorkspaceFailed(String msg) {

    }

    @Override
    public void onSwitchOrganizationWorkspaceSucceeded(String msg) {

    }

    @Override
    public void onUserSignOutFailed(String msg) {

    }

    @Override
    public void onUserSignOutSucceeded(String msg) {

    }

    @Override
    public void onReadOrganizationMembersFailed(String msg) {

    }

    @Override
    public void onReadOrganizationMembersSucceeded(List<CUserProfileModel> userProfileModels) {

    }


    /**
     * Methods which are implemented by all fragments.
     */

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {
        //binding.includeProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void showError(String message) {

    }

    public void removeListener() {

    }
}
