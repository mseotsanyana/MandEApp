package com.me.mseotsanyana.mande.framework.ui.fragments.session;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.me.mseotsanyana.mande.databinding.SessionHomeFragmentBinding;
import com.me.mseotsanyana.mande.framework.modelviews.session.CHomePageViewModel;
import com.me.mseotsanyana.mande.infrastructure.controllers.session.CHomePageControllerImpl;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IHomePageController;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.infrastructure.presenters.session.CHomePagePresenterImpl;
import com.me.mseotsanyana.mande.infrastructure.repository.firestore.session.CUserProfileFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.infrastructure.services.CMainThreadImpl;
import com.me.mseotsanyana.mande.infrastructure.services.CSessionManagerImpl;
import com.me.mseotsanyana.mande.infrastructure.services.CThreadExecutorImpl;

import java.util.Objects;

public class CHomePageFragment extends Fragment {
    private static final String TAG = CHomePageFragment.class.getSimpleName();
    //private static SimpleDateFormat ssdf = cConstant.SHORT_FORMAT_DATE;

    private CHomePageViewModel cViewModel;
    private IHomePageController iController;

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//    }

    @Override
    public void onResume() {
        super.onResume();
        /* read menu items from the asset */
        iController.resume();
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // binding object
        SessionHomeFragmentBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.session_home_fragment,
                container, false);

        cViewModel = new CHomePageViewModel(this, binding);


        iController = new CHomePageControllerImpl(
                CThreadExecutorImpl.getInstance(),
                CMainThreadImpl.getInstance(),
                CSessionManagerImpl.getInstance(requireContext()),
                cViewModel,
                new CHomePagePresenterImpl(cViewModel),
                new CUserProfileFirestoreRepositoryImpl(requireContext()));

        return binding.getRoot();

    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* create toolbar and progressbar */
        cViewModel.initViews(view);

        /* create navigation drawer menu */
        cViewModel.initNavigationDrawerViews(view);

        /* create bottom navigation menu */
        cViewModel.initBottomNavigationViews(view);

        /* populate header items */
        cViewModel.populateHeaderViews();

        // initialise navigationView listeners
        cViewModel.drawerNavigationListener();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        if (item.getItemId() == R.id.homeItem) {
            Log.d(TAG, "Stub for overflow menu");
        }

        // Activate the navigation drawer toggle
        if (cViewModel.getDrawerToggle().onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        cViewModel.getDrawerToggle().onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getActivity().getMenuInflater().inflate(R.menu.drawer_menu_main, menu);
        inflater.inflate(R.menu.home_toolbar_menu, menu);

        //getting the search view from the menu
        MenuItem toolBarMenu = menu.findItem(R.id.searchItem);

        /* getting search manager from system service */
        SearchManager searchManager = (SearchManager) requireActivity().
                getSystemService(Context.SEARCH_SERVICE);
        /* getting the search view */
        SearchView searchView = (SearchView) toolBarMenu.getActionView();
        /* you can put a hint for the search input field */
        searchView.setQueryHint("Search LogFrames...");
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

    private void search(@NonNull SearchView searchView) {
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

    public void signOutWithEmailAndPassword() {
        iController.signOutRequest();
    }

    public void switchWorkspaceRequest() {
        iController.switchWorkspaceRequest();
    }
}

//    @Override
//    public void onUserSignOutFailed(String msg) {
//        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onUserSignOutSucceeded(String msg) {
//        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//    }
//
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    public void onReadHomePageSucceeded(@NonNull CUserProfileModel userProfileModel,
//                                        List<cMenuModel> menuModels) {
//        /* update the user profile */
//        currentDate.setText(tsdf.format(Calendar.getInstance().getTime()));
//        displayName.setText(userProfileModel.getName() + " " + userProfileModel.getSurname());
//        displayEmail.setText(userProfileModel.getEmail());
//
//        // update the menu items when there is a change
//        this.expandableListAdapter.setMenuModels(menuModels);
//        this.expandableListAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onReadHomePageFailed(String msg) {
//        hideProgress();
//    }
//
//    @Override
//    public void showProgress() {
//        progressbar.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void hideProgress() {
//        progressbar.setVisibility(View.GONE);
//    }
//
//    public void showResponse(Map<String, CTreeModel> response) {
//
//    }
//    @Override
//    public void showError(String message) {
//
//    }