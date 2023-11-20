package com.me.mseotsanyana.mande.framework.ui.fragments.session;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.me.mseotsanyana.mande.framework.modelviews.session.COrganizationWorkspaceViewModel;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationWorkspaceFragmentBinding;
import com.me.mseotsanyana.mande.framework.ports.base.IBaseFragment;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationWorkspaceController;

import java.util.Objects;

public class COrganizationWorkspaceFragment extends Fragment implements IBaseFragment {
    private static final String TAG = COrganizationWorkspaceFragment.class.getSimpleName();

    private IOrganizationWorkspaceController.IViewModel iViewModel;

    FirebaseUser firebaseUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //FirebaseAuth.getInstance().signOut();
        //FirebaseFirestore.getInstance().clearPersistence();

        // binding object
        SessionOrganizationWorkspaceFragmentBinding binding;
        binding = DataBindingUtil.inflate(inflater,
                R.layout.session_organization_workspace_fragment, container, false);

        iViewModel = new COrganizationWorkspaceViewModel(this, binding);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iViewModel.onViewCreated();
    }

    @Override
    public void onResume() {
        super.onResume();

//        FirebaseAuth.getInstance().signOut();
//        CSessionManagerImpl sharedPreferenceRepository;
//        sharedPreferenceRepository = CSessionManagerImpl.getInstance(requireContext());
//        sharedPreferenceRepository.deleteSettings();
//        sharedPreferenceRepository.commitSettings();
//        FirebaseFirestore.getInstance().clearPersistence();

        if (firebaseUser == null) {
            //iController.clearAllPreferenceSettings();
            FirebaseFirestore.getInstance().clearPersistence();
            iViewModel.onUserLoggedOut();
        } else if (!firebaseUser.isEmailVerified()) {
            iViewModel.onEmailUnverified();
        } else {
            iViewModel.onResumeView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iViewModel.onRemoveListener();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
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
        searchView.setQueryHint("Search workspaces...");
        searchView.setSearchableInfo(Objects.requireNonNull(searchManager).
                getSearchableInfo(requireActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        /* by setting it true we are making it iconified so the search input will show up after
           taping the search iconified if you want to make it visible all the time make it false
         */
        //searchView.setIconifiedByDefault(true);
        //iViewModel.search(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                iViewModel.onSearchAdapter(query);
                return false;
            }
        });


        /* end */

        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteItem:
                //projectPresenter.deleteProjectModels();
                return true;

            case R.id.uploadItem:
                //projectPresenter.uploadProjectFromExcel(null);launchUploadExcelFile.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                return true;

            case R.id.downloadItem:
                // do something
                return true;

            case R.id.logoutItem:
                iViewModel.onUserLogOut();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    /********************************** organization click events *********************************/

//    public void onClickCreateOrganization(COrganizationModel organizationModel) {
//        getController().createOrganization(organizationModel);
//    }
//
//    public void onClickReadOrganizationWorkspaces(String organizationServerID) {
//        getController().readWorkspaces(organizationServerID);
//    }
//
//    public void onLongClickWorkspace(CWorkspaceModel workspaceModel) {
//        getController().chooseWorkspaceRequest(workspaceModel);
//    }
//
//    public void onClickCreateWorkspace(String organizationServerID, int workspaceBITS,
//                                       CWorkspaceModel workspaceModel) {
//        getController().createWorkspace(organizationServerID, workspaceBITS, workspaceModel);
//    }
//
//    public void onDeleteWorkspace(String workspaceServerID, List<String> workspaceMembers) {
//        getController().deleteWorkspace(workspaceServerID, workspaceMembers);
//    }
}