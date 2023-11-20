package com.me.mseotsanyana.mande.framework.ui.fragments.session;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.me.mseotsanyana.mande.infrastructure.presenters.session.COrganizationPresenterImpl;
import com.me.mseotsanyana.mande.infrastructure.services.CMainThreadImpl;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationFragmentBinding;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationController;
import com.me.mseotsanyana.mande.framework.modelviews.session.COrganizationViewModel;
import com.me.mseotsanyana.mande.infrastructure.controllers.session.COrganizationControllerImpl;
import com.me.mseotsanyana.mande.infrastructure.services.CSessionManagerImpl;
import com.me.mseotsanyana.mande.infrastructure.repository.firestore.session.COrganizationFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.infrastructure.services.CThreadExecutorImpl;

import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/04.
 */

public class COrganizationFragment extends Fragment {
    private static final String TAG = COrganizationFragment.class.getSimpleName();
    /* binding data to view controls */
    private SessionOrganizationFragmentBinding binding;
    /* model view to provide view details */
    private COrganizationViewModel cViewModel;
    /* organization controller to accept requests */
    private IOrganizationController controller;

    /* organization views */
    //private LinearLayout includeProgressBar;

    public IOrganizationController getController() {
        return controller;
    }

    public void setController(IOrganizationController controller) {
        this.controller = controller;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        /* read all projects from the database */
        getController().resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getController().removeListener();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = DataBindingUtil.inflate(inflater, R.layout.session_organization_fragment,
                container, false);

        cViewModel = new COrganizationViewModel(this, binding);

        setController(new COrganizationControllerImpl(
                CThreadExecutorImpl.getInstance(),
                CMainThreadImpl.getInstance(),
                CSessionManagerImpl.getInstance(requireContext()),
                cViewModel,
                new COrganizationPresenterImpl(cViewModel),
                new COrganizationFirestoreRepositoryImpl(getContext())));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /* initialize data structures */
        cViewModel.initDataStructures();
        /* initialize views */
        cViewModel.initViews(view);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        /* get inflated option menu */
        Menu toolBarMenu = setToolBar();

        binding.includeToolbar.toolbar.setOnMenuItemClickListener(
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.uploadItem) {
            Log.d(TAG, "Stub for information button");
        }
        return super.onOptionsItemSelected(item);
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

    private Menu setToolBar() {
        binding.includeToolbar.toolbar.inflateMenu(R.menu.me_toolbar_menu);
        return binding.includeToolbar.toolbar.getMenu();
    }
}
