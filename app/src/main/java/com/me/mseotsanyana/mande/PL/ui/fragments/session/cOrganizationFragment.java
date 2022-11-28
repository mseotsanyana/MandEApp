package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cWorkspaceModel;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.repository.common.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cOrganizationFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cPrivilegeFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.common.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cUserProfileFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cOrganizationPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iOrganizationPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cOrganizationAdapter;
import com.me.mseotsanyana.mande.PL.utils.cIndexedLinkedHashMap;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationFragmentBinding;
import com.me.mseotsanyana.multiselectspinnerlibrary.CSingleSpinnerSearch;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cOrganizationFragment extends Fragment implements iOrganizationPresenter.View {
    private static final String TAG = cOrganizationFragment.class.getSimpleName();

    // interfaces
    private iOrganizationPresenter organizationPresenter;
    private iSharedPreferenceRepository sharedPreferenceRepository;

    // views
    //private ListenerRegistration listenerRegistration;

    // adapters
    private cOrganizationAdapter organizationAdapter;

    // datasets
    //private cIndexedLinkedHashMap<String, cOrganizationModel> organizationModels;
    private final String[] ORG_TYPE = {"National Partner", "Funder (or Donor)",
            "Beneficiary", "Implementing Agency"};
    private SessionOrganizationFragmentBinding binding;
    Gson gson = new Gson();

    public cOrganizationFragment() {
    }

    public static cOrganizationFragment newInstance() {
        return new cOrganizationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        sharedPreferenceRepository = new cSharedPreferenceFirestoreRepositoryImpl(requireContext());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = DataBindingUtil.inflate(inflater, R.layout.session_organization_fragment,
                container, false);

        initAppBarLayout(binding.toolbarLayout.toolbar, binding.toolbarLayout.appName,
                binding.toolbarLayout.collapsingToolbarLayout);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDataStructures();

        initDraggableFAB();
    }

    @Override
    public void onResume() {
        super.onResume();
        //FirebaseAuth.getInstance().signOut();
        sharedPreferenceRepository.deleteSettings();
        sharedPreferenceRepository.commitSettings();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentWorkspace = sharedPreferenceRepository.loadCurrentWorkspace();//FIXME

        if (firebaseUser == null) {
            sharedPreferenceRepository.deleteSettings();
            sharedPreferenceRepository.commitSettings();
            NavDirections action;
            action = cOrganizationFragmentDirections.actionCOrganizationFragmentToCLoginFragment();
            Navigation.findNavController(requireView()).navigate(action);
        } else if (!firebaseUser.isEmailVerified()) {
            NavDirections action;
            action = cOrganizationFragmentDirections.actionCOrganizationFragmentToCLoginFragment();
            Navigation.findNavController(requireView()).navigate(action);
            Toast.makeText(getContext(), "Please check and verify your email!",
                    Toast.LENGTH_SHORT).show();
        } else if (currentWorkspace != null) {
            NavDirections action;
            action = cOrganizationFragmentDirections.actionCOrganizationFragmentToCHomeFragment();
            Navigation.findNavController(requireView()).navigate(action);
        } else {
            organizationPresenter.readOrganizationWorkspaces();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        organizationPresenter.removeListener();
    }

    private void initDataStructures() {
        organizationPresenter = new cOrganizationPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cPrivilegeFirestoreRepositoryImpl(requireContext()),
                new cOrganizationFirestoreRepositoryImpl(requireContext()),
                new cUserProfileFirestoreRepositoryImpl(requireContext()));
    }

    private void initAppBarLayout(Toolbar toolbar, TextView textView,
                                  CollapsingToolbarLayout collapsingToolbarLayout) {
        textView.setText(R.string.app_name);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        collapsingToolbarLayout.setTitle("Organizations");

        /* show the back arrow button*/
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
            }
        }
    }

    private void initDraggableFAB() {
        binding.organizationFAB.setOnClickListener(view -> onClickCreateOrganization());
    }

    private void setAdapter(cIndexedLinkedHashMap<String, cOrganizationModel> organizationModels) {
        if (organizationAdapter == null) {
            organizationAdapter = new cOrganizationAdapter(getActivity(), this, organizationModels);
            binding.organizationRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(requireActivity());
            binding.organizationRecyclerView.setLayoutManager(mLayoutManager);
            binding.organizationRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.organizationRecyclerView.setAdapter(organizationAdapter);
        } else {
            organizationAdapter.reloadList(organizationModels);
        }
    }

    private void setAdapter(List<cTreeModel> organizationTreeModels) {
        if (organizationAdapter == null) {
            organizationAdapter = new cOrganizationAdapter(getActivity(),
                    this, organizationTreeModels);
            binding.organizationRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(requireActivity());
            binding.organizationRecyclerView.setLayoutManager(mLayoutManager);
            binding.organizationRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.organizationRecyclerView.setAdapter(organizationAdapter);
        } else {
            organizationAdapter = new cOrganizationAdapter(getActivity(), this,
                    organizationTreeModels);
        }
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

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //projectAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteItem:
                //projectPresenter.deleteProjectModels();
                return true;

            case R.id.uploadItem:
                //               projectPresenter.uploadProjectFromExcel(null);
//                launchUploadExcelFile.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                return true;

            case R.id.downloadItem:
                // do something
                return true;

            case R.id.logoutItem:
                organizationPresenter.signOutWithEmailAndPassword();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    // READ ORGANIZATIONS

    @Override
    public void onReadOrganizationsFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReadOrganizationsSucceeded(List<cTreeModel> treeModels) {
        Gson gson = new Gson();
        Log.d(TAG, "ORG WITH WORKSPACES " + gson.toJson(treeModels));
        setAdapter(treeModels);
    }

    @Override
    public void onReadOrganizationFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReadOrganizationSucceeded(cOrganizationModel organization, String operation) {
        if (organizationAdapter == null)
            setAdapter(new cIndexedLinkedHashMap<>());
        if (operation.equals("ADD")) {
            organizationAdapter.getOrganizationList().add(organization.getOrganizationServerID(),
                    organization);
            organizationAdapter.reloadList(
                    organizationAdapter.getOrganizationList().size() - 1, operation);
        }

        if (operation.equals("UPDATE")) {
            organizationAdapter.getOrganizationList().update(organization.getOrganizationServerID(),
                    organization);
            organizationAdapter.reloadList(
                    organizationAdapter.getOrganizationList().getIndexByKey(
                            organization.getOrganizationServerID()), operation);
        }

        if (operation.equals("DELETE")) {
            organizationAdapter.getOrganizationList().update(organization.getOrganizationServerID(),
                    organization);
            organizationAdapter.reloadList(
                    organizationAdapter.getOrganizationList().getIndexByKey(
                            organization.getOrganizationServerID()), operation);
        }
    }

    // SWITCH ORGANIZATION WORKSPACES
    @Override
    public void onSwitchOrganizationWorkspaceFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSwitchOrganizationWorkspaceSucceeded(String msg) {
        NavDirections action;
        action = cOrganizationFragmentDirections.actionCOrganizationFragmentToCHomeFragment();
        Navigation.findNavController(requireView()).navigate(action);

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();


//        // save settings for the long clicked workspace
//        iSharedPreferenceRepository settings;
//        settings = new cSharedPreferenceFirestoreRepositoryImpl(requireContext());
//        settings.saveCurrentWorkspace(workspaceModel.getCompositeServerID());
//
//        Toast.makeText(getContext(), "Workspace '" + workspaceModel.getName() + "' loaded.",
//                Toast.LENGTH_SHORT).show();
    }

    // READ ORGANIZATION MEMBERS
    @Override
    public void onReadOrganizationMembersFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReadOrganizationMembersSucceeded(List<CUserProfileModel> userProfileModels) {

    }

    // CREATE ORGANIZATION

    @Override
    public void onClickCreateOrganization() {
        createAlertDialog();
    }

    // SWITCH WORKSPACE
    @Override
    public void onLongClickWorkspace(@NonNull cWorkspaceModel workspaceModel) {
        organizationPresenter.switchOrganizationWorkspace(workspaceModel);
    }

    @Override
    public void onCreateOrganizationFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOrganizationSucceeded(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    // LOGOUT USER
    @Override
    public void onUserSignOutFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserSignOutSucceeded(String msg) {
        NavDirections action;
        action = cOrganizationFragmentDirections.actionCOrganizationFragmentToCLoginFragment();
        Navigation.findNavController(requireView()).navigate(action);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    // PRESENTER FUNCTIONS

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

    // OTHER

    private void createAlertDialog() {
        /* inflate the resource for create and update */
        LayoutInflater inflater = this.getLayoutInflater();
        View createView = inflater.inflate(R.layout.session_org_create_update, null);

        /* instantiates create views */
        final int[] org_index = new int[1];
        TextView textViewTitle = createView.findViewById(R.id.textViewTitle);
        TextView textViewOrgType = createView.findViewById(R.id.textViewOrgType);
        CSingleSpinnerSearch singleSpinner = createView.findViewById(R.id.singleSpinner);
        AppCompatEditText editTextName = createView.findViewById(R.id.editTextName);
        AppCompatEditText editTextEmail = createView.findViewById(R.id.editTextEmail);
        AppCompatEditText editTextWebsite = createView.findViewById(R.id.editTextWebsite);

        /* set a title of the create view */
        textViewTitle.setText(requireContext().getResources().getText(
                R.string.organization_create_title));

        /* populate the logical model with the create views */
        /* 1. create selection dialog box for organizations */
        List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
        for (int i = 0; i < ORG_TYPE.length; i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            idNameBool.setId(i);
            idNameBool.setName(ORG_TYPE[i]);
            idNameBool.setSelected(false);
            keyPairBoolOrgs.add(idNameBool);
        }

        // called when click spinner
        singleSpinner.setItem(keyPairBoolOrgs, -1, item -> {
            /* assign selected organization name to the view */
            textViewOrgType.setText(item.getName());
            org_index[0] = (int) item.getId();
        });

        /* create or cancel action */
        MaterialAlertDialogBuilder alertDialogBuilder =
                new MaterialAlertDialogBuilder(requireActivity(), R.style.AlertDialogTheme);
        alertDialogBuilder.setPositiveButton(requireContext().getResources().getText(
                R.string.Save), (dialogInterface, i) ->
                createStakeholder(org_index[0],
                        Objects.requireNonNull(editTextName.getText()).toString(),
                        Objects.requireNonNull(editTextEmail.getText()).toString(),
                        Objects.requireNonNull(editTextWebsite.getText()).toString()));

        alertDialogBuilder.setNegativeButton(requireContext().getResources().getText(
                        R.string.Cancel), (dialogInterface, i) -> {
                })
                .setView(createView)
                .show();
    }

    private void createStakeholder(int typeID, String name, String email, String website) {
        cOrganizationModel stakeholderModel = new cOrganizationModel(typeID, name, email,
                website);
        organizationPresenter.createOrganization(stakeholderModel);
    }


    private void deleteAlertDialog(int resID, String title, String message, int position,
                                   String organizationID) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireContext());

        // setting icon to dialog
        TextDrawable faIcon = new TextDrawable(requireContext());
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(cFontManager.getTypeface(requireContext(), cFontManager.FONTAWESOME));
        faIcon.setText(requireContext().getResources().getText(resID));
        faIcon.setTextColor(requireContext().getColor(R.color.colorAccent));
        alertDialogBuilder.setIcon(faIcon);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(requireContext().getResources().getText(
                        R.string.Yes), (dialog, id) -> {
                    deleteOrganizationModel(organizationID, position);
                    dialog.dismiss();
                })
                .setNegativeButton(requireContext().getResources().getText(
                        R.string.No), (dialog, id) -> {
                    // if this button is clicked, just close
                    dialog.cancel();
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void deleteOrganizationModel(String logFrameID, int position) {
        //organizationPresenter.deleteLogFrameModel(logFrameID, position);
    }
}


//    private void readAllOrganizations() {
//        if (listenerRegistration != null)
//            listenerRegistration.remove();
//
//        listenerRegistration = organizationRepository.readAllOrganizationsByChildEvent(
//                new cFirebaseChildCallBack() {
//                    @Override
//                    public void onChildAdded(Object object) {
//                        if (object != null) {
//                            cOrganizationModel organizationModel = (cOrganizationModel) object;
//                            Log.d(TAG, " ADD =============>>>>> " + gson.toJson(organizationModel));
//
////                    if (organizationAdapter == null)
////                        organizationAdapter = new cIndexedLinkedHashMap<String, cOrganizationModel>();
//
//                            organizationAdapter.getOrganizationList().add(
//                                    organizationModel.getOrganizationServerID(), organizationModel);
//
//                            organizationAdapter.notifyItemInserted(organizationAdapter.getReversePosition(
//                                    organizationAdapter.getOrganizationList().size() - 1));
//
//                            //organizationAdapter.reloadList(
//                            //        organizationAdapter.getOrganizationList().size() - 1,
//                            //        "ADD");
//                        }
//                    }
//
//                    @Override
//                    public void onChildChanged(Object object) {
//                        if (object != null) {
//                            cOrganizationModel organizationModel = (cOrganizationModel) object;
//                            organizationAdapter.getOrganizationList().update(
//                                    organizationModel.getOrganizationServerID(), organizationModel);
//
//
//                            organizationAdapter.reloadList(organizationAdapter.getOrganizationList().
//                                            getIndexByKey(organizationModel.getOrganizationServerID()),
//                                    "UPDATE");
//                        }
//                    }
//
//                    @Override
//                    public void onChildRemoved(Object object) {
//                        if (object != null) {
//                            cOrganizationModel organizationModel = (cOrganizationModel) object;
//                            organizationAdapter.getOrganizationList().update(
//                                    organizationModel.getOrganizationServerID(), organizationModel);
//                            organizationAdapter.reloadList(organizationAdapter.getOrganizationList().
//                                            getIndexByKey(organizationModel.getOrganizationServerID()),
//                                    "DELETE");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(Object object) {
//                        if (object != null)
//                            Toast.makeText(getContext(), "getAllOrganization", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//

//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        /* get all organizations from the database */
//        //organizationPresenter.readOrganizations();
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.session_organizations_fragment, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        /* create data structures */
//        initDataStructures();
//
//        /* create RecyclerView */
//        initOrganizationViews(view);
//
//        /* draggable floating button */
//        initDraggableFAB(view);
//    }

//    private void initOrganizationViews(View view) {
//        binding.organizationRecyclerView.
//
//
//        includeProgressBar = view.findViewById(R.id.includeProgressBar);
//        orgRecyclerView = view.findViewById(R.id.orgRecyclerView);
//
//        orgRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//
//        orgRecyclerView.setAdapter(organizationAdapter);
//        orgRecyclerView.setLayoutManager(llm);
//    }
