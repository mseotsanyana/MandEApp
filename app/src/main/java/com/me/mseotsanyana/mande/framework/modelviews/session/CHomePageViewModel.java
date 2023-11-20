package com.me.mseotsanyana.mande.framework.modelviews.session;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;

import com.google.android.material.navigation.NavigationBarView;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cSettingsActivity;
import com.me.mseotsanyana.mande.databinding.SessionHomeFragmentBinding;
import com.me.mseotsanyana.mande.domain.entities.models.session.CMenuModel;
import com.me.mseotsanyana.mande.framework.ui.routers.session.CHomePageRouter;
import com.me.mseotsanyana.mande.framework.ui.adapters.session.cExpandableListAdapter;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.CHomePageFragment;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.cDashboardFragment;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.cMessagesFragment;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.cSettingsFragment;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IHomePageController;

import java.util.ArrayList;
import java.util.List;

public class CHomePageViewModel implements IHomePageController.IViewModel {
    private static final String TAG = CHomePageViewModel.class.getSimpleName();

    /* organization workspace binding */
    private final SessionHomeFragmentBinding binding;
    /* organization workspace view interface */
    private final CHomePageFragment fragment;
    /* navigation to and from other views */
    private final CHomePageRouter router;


    private View headerView;
    private ActionBarDrawerToggle drawerToggle;
    private ExpandableListView expandableListView;
    private cExpandableListAdapter expandableListAdapter;

    /* menu data structures */
    private final List<CMenuModel> menuModels = new ArrayList<>();

    //private iUserSignOutPresenter userSignOutPresenter;
    //private IHomePageController homePagePresenter;

    private AppCompatActivity activity;

    public CHomePageViewModel(CHomePageFragment fragment,
                              SessionHomeFragmentBinding binding) {
        this.router = new CHomePageRouter(fragment);
        this.fragment = fragment;
        this.binding = binding;
    }

    public void initViews(@NonNull View view) {
        activity = ((AppCompatActivity) fragment.getActivity());

//        userSignOutPresenter = new cUserSignOutPresenterImpl(
//                CThreadExecutorImpl.getInstance(),
//                CMainThreadImpl.getInstance(),
//                this,
//                null,
//                new CUserProfileFirestoreRepositoryImpl(requireContext()));
//
//        homePagePresenter = new cHomePageControllerImpl(
//                CThreadExecutorImpl.getInstance(),
//                CMainThreadImpl.getInstance(),
//                this,
//                null,
//                new cHomePageFirestoreRepositoryImpl(getContext()));

        /* initialize the toolbar */
        //toolbar = view.findViewById(R.id.toolbar);
        if (activity != null) {
            activity.setSupportActionBar(binding.toolbar);
        }

        /* initialize the progress bar*/
        //progressbar = view.findViewById(R.id.progressbar);

        // instantiating the expandable action_list view under the DrawerLayout
        //expandableListView = view.findViewById(R.id.expandableListView);

        // instantiating the navigation view
        //NavigationView navigationView = view.findViewById(R.id.navigationView);
        headerView = binding.navigationView.getHeaderView(0);

        expandableListAdapter = new cExpandableListAdapter(fragment.getContext(), menuModels);
        binding.expandableListView.setAdapter(expandableListAdapter);
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }

    public void initNavigationDrawerViews(@NonNull View view) {
        //drawerLayout = view.findViewById(R.id.drawer_layout);
        // host activity
        // drawer layout
        // custom toolbar
        // open drawer description
        // close drawer description
        /* Called when a drawer has settled in a completely open state. */
        // creates call to onPrepareOptionsMenu()
        /* Called when a drawer has settled in a completely closed state. */
        // creates call to onPrepareOptionsMenu()
        //    private Toolbar toolbar;
        //    private View progressbar;
        //
        //    //private ImageView userIcon;
        //    private TextView currentDate, displayName, displayEmail;
        //
        //
        //    private DrawerLayout drawerLayout;
        drawerToggle = new ActionBarDrawerToggle(
                fragment.getActivity(),       // host activity
                binding.drawerLayout,         // drawer layout
                binding.toolbar,              // custom toolbar
                R.string.drawer_open, // open drawer description
                R.string.drawer_close // close drawer description
        ) {

            /* Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // creates call to onPrepareOptionsMenu()
                fragment.requireActivity().invalidateOptionsMenu();
            }

            /* Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // creates call to onPrepareOptionsMenu()
                fragment.requireActivity().invalidateOptionsMenu();
            }
        };

        // show animations
        binding.drawerLayout.addDrawerListener(drawerToggle);

        //
        drawerToggle.setDrawerIndicatorEnabled(true);

        // Sync the toggle state after onRestoreInstanceState has occur
        // and show the display menu icon
        drawerToggle.syncState();
    }

    public void drawerNavigationListener() {
        // called when expanding...
        binding.expandableListView.setOnGroupExpandListener(groupPosition ->
                Log.d(TAG, "I AM EXPANDING!!!!!!!!!!!!!!!"));

        // called when collapsing...
        binding.expandableListView.setOnGroupCollapseListener(groupPosition ->
                Log.d(TAG, "I AM CLOSING!!!!!!!!!!!!!!!!"));

        // called when clicking on parent menu item...
        binding.expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            /**
             * Callback method to be invoked when a group in this expandable action_list has
             * been clicked.
             *
             * @param parent        The ExpandableListConnector where the click happened
             * @param v             The view within the expandable action_list/ListView
             *                      that was clicked
             * @param groupPosition The group position that was clicked
             * @param id            The row id of the group that was clicked
             * @return True if the click was handled
             */
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                                        long id) {
                boolean retVal = true; // used to enable expanding to child menu items
                CMenuModel menuModel = expandableListAdapter.getGroup(groupPosition);
                switch (menuModel.getMenuServerID()) {
                    case 0:  // My Profile
                    case 16: // User Management
                        retVal = false;
                        break;
                    case 512: // Notification Settings
                        Toast.makeText(fragment.getActivity(), "Notification Fragment",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 1024: // Settings
                        Intent intent = new Intent(activity, cSettingsActivity.class);
                        fragment.startActivity(intent);
                        break;
                    case 2048: // Logout
                        fragment.signOutWithEmailAndPassword();
                        router.actionCHomePageFragmentToCUserLoginFragment();
                        break;
                    default:
                        break;
                }
                return retVal;
            }
        });

        // called when clicking on child menu item...
        binding.expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            CMenuModel parentModel = expandableListAdapter.getGroup(groupPosition);
            CMenuModel childModel = expandableListAdapter.getChild(groupPosition, childPosition);

            NavDirections action;

            switch (parentModel.getMenuServerID()) {
                case 0: // User Management
                    switch (childModel.getMenuServerID()) {
                        case 1: // My Profile
                            //action = CHomePageFragmentDirections.actionCHomeFragmentToCMyUserProfileFragment();
                            //Navigation.findNavController(fragment.requireView()).navigate(action);
                            break;

                        case 2: // My Accounts
                            //action = CHomePageFragmentDirections.actionCHomeFragmentToCOrganizationAccountFragment();
                            //Navigation.findNavController(fragment.requireView()).navigate(action);
                            break;

                        case 4: // My Collaborators
                            //action = CHomePageFragmentDirections.actionCHomeFragmentToCUserProfilesFragment();
                            //Navigation.findNavController(fragment.requireView()).navigate(action);

                            break;

                        case 8: // Switch Organization

                            fragment.switchWorkspaceRequest();

//                            // clear current workspace settings
//                            ISessionManager settings;
//                            settings = null;//new CSessionManagerImpl(requireContext());
//                            settings.deleteSettings();
//                            settings.commitSettings();
//
//                            // read organization with their workspaces
//                            action = CHomePageFragmentDirections.actionCHomeFragmentToCOrganizationWorkspaceFragment();
//                            //Navigation.findNavController(fragment.requireView()).navigate(action);

                            break;

                        default:
                            break;
                    }
                    break;

                case 16: // Admin Management
                    switch (childModel.getMenuServerID()) {
                        case 32: // My Organizations
//                            action = cHomeFragmentDirections.
//                                    actionCHomeFragmentToCOrganizationMemberFragment();
//                            Navigation.findNavController(requireView()).navigate(action);
                            //NavDirections action;
                            //action = cHomeFragmentDirections.actionCHomeFragmentToCOrganizationWithTabsFragment();
                            //Navigation.findNavController(requireView()).navigate(action);
                            break;

                        case 64: // My Workspaces
                            //action = cHomeFragmentDirections.
                            //        actionCHomeFragmentToCTeamRoleFragment();
                            //Navigation.findNavController(requireView()).navigate(action);
                            break;

                        case 128: // Contracts/Agreements
                            Toast.makeText(fragment.getActivity(), "Organization Members",
                                    Toast.LENGTH_SHORT).show();
                            break;

                        case 256: // Workspace Privileges
                            //action = cHomeFragmentDirections.
                            //        actionCHomeFragmentToCPermissionFragment();
                            //Navigation.findNavController(requireView()).navigate(action);
                            break;

                        default:
                            break;
                    }
                    break;

                default:
                    break;
            }

            return true;
        });
    }

    /* bottom navigation views */
    public void initBottomNavigationViews(@NonNull View view) {
        //NavigationBarView navigationBarView = view.findViewById(R.id.bottomNavigationView);
        binding.bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);
        openFragment(cDashboardFragment.newInstance());
    }

    @SuppressLint("NonConstantResourceId")
    NavigationBarView.OnItemSelectedListener onItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.cHomeFragment:
                        openFragment(cDashboardFragment.newInstance());
                        return true;

                    case R.id.cMessagesFragment:
                        openFragment(cMessagesFragment.newInstance());
                        return true;

                    case R.id.cDashboardFragment:
                        openFragment(cSettingsFragment.newInstance());
                        return true;
                }
                return false;
            };

    @SuppressLint("SetTextI18n")
    public void populateHeaderViews() {
        // instantiate header view objects
        //userIcon = headerView.findViewById(R.id.userIcon);
//        currentDate = headerView.findViewById(R.id.currentDate);
//        displayName = headerView.findViewById(R.id.displayName);
//        displayEmail = headerView.findViewById(R.id.displayEmail);
    }

    public void openFragment(@NonNull Fragment childFragment) {
        FragmentTransaction transaction = fragment.getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.sessionFrameLayout, childFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


//===========end

    /********************************* view model response methods ********************************/

    @Override
    public void showMenuItemsResponse(List<CMenuModel> menuModels) {
        this.expandableListAdapter.setMenuModels(menuModels);
        this.expandableListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showWorkspacesResponse(String message) {
        router.actionCHomePageFragmentToCOrganizationWorkspaceFragment();
        Log.i(TAG, message);
    }

    /********************************** base view response methods ********************************/

    @Override
    public void showProgress() {
        binding.progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressbar.setVisibility(View.GONE);
    }

    @Override
    public void showResponseMessage(String message) {
        Toast.makeText(fragment.getActivity(), message, Toast.LENGTH_SHORT).show();
        Log.i(TAG, message);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(fragment.getActivity(), message, Toast.LENGTH_SHORT).show();
        Log.i(TAG, message);
    }
}
