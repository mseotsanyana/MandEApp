package com.me.mseotsanyana.mande.PL.ui.fragments.session;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cPrivilegeFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.common.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cPermissionPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iPermissionPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cPrivilegeAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cPermissionFragment extends Fragment implements iPermissionPresenter.View{
    private static final String TAG = cPermissionFragment.class.getSimpleName();

    private Toolbar toolbar;

    private iPermissionPresenter permissionPresenter;

    private LinearLayout includeProgressBar;

    private cPrivilegeAdapter permissionAdapter;

    private AppCompatActivity activity;

    public cPermissionFragment() {
    }

    public static cPermissionFragment newInstance() {
        return new cPermissionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        permissionPresenter.readWorkspacePermissions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.session_menu_items_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* create data structures */
        initDataStructures();

        /* create RecyclerView */
        initModuleViews(view);

        /* apply button */
        //initButton(view);

        /* show the back arrow button */
        toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    private void initDataStructures() {
        //List<cTreeModel> permissionTree = new ArrayList<>();

        permissionAdapter = new cPrivilegeAdapter(getActivity(), this,
                new ArrayList<>());

        permissionPresenter = new cPermissionPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cPrivilegeFirestoreRepositoryImpl(getContext()));

        activity = ((AppCompatActivity) getActivity());
    }

    private void initModuleViews(View view) {
        includeProgressBar = view.findViewById(R.id.includeProgressBar);
        RecyclerView moduleRecyclerView = view.findViewById(R.id.menuRecyclerView);

        moduleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        moduleRecyclerView.setAdapter(permissionAdapter);
        moduleRecyclerView.setLayoutManager(llm);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        /* get inflated option menu */
        Menu toolBarMenu = setToolBar();

        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

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
                permissionAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    private Menu setToolBar(){
        toolbar.inflateMenu(R.menu.me_toolbar_menu);
        return toolbar.getMenu();
    }

    // READ MENU ITEMS

    @Override
    public void onReadRolePermissionFailed(String msg) {

    }

    @Override
    public void onReadRolePermissionSucceeded(List<cTreeModel> treeModels) {
        //Gson gson = new Gson();
        //Log.d(TAG, "PERM MENU = "+gson.toJson(treeModels));
        try {
            permissionAdapter.setTreeModel(treeModels);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateRolePermissionFailed(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateRolePermissionSucceeded(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickUpdateRolePermission(List<cNode> nodes) {
        permissionPresenter.updateRolePermissions(nodes);
    }

    @Override
    public void onClickDeleteRolePermission(String permissionServerID) {

    }

    // PRESENTER FUNCTIONS

    @Override
    public void showProgress() {
        includeProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        includeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }
}