package com.me.mseotsanyana.mande.framework.modelviews.session;

import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.OLD.TextDrawable;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationWorkspaceFragmentBinding;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.framework.routers.COrganizationWorkspaceRouter;
import com.me.mseotsanyana.mande.framework.ui.adapters.session.COrganizationWorkspaceAdapter;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.COrganizationWorkspaceFragment;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.COrganizationWorkspaceFragmentDirections;
import com.me.mseotsanyana.mande.framework.utils.CFontManager;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationWorkspaceController;
import com.me.mseotsanyana.multiselectspinnerlibrary.CSingleSpinnerSearch;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class COrganizationWorkspaceOrganizationWorkspaceViewModel implements IOrganizationWorkspaceController.IOrganizationWorkspaceViewModel {
    /* organization workspace binding */
    private final SessionOrganizationWorkspaceFragmentBinding binding;
    /* organization workspace view interface */
    private final COrganizationWorkspaceFragment fragment;
    /* organization workspace adapters */
    private COrganizationWorkspaceAdapter adapter;
    /* navigation to and from other views */
    private final COrganizationWorkspaceRouter router;

    private final String[] ORG_TYPE = {"National Partner", "Funder (or Donor)",
            "Beneficiary", "Implementing Agency"};


    public COrganizationWorkspaceOrganizationWorkspaceViewModel(COrganizationWorkspaceFragment fragment,
                                                                SessionOrganizationWorkspaceFragmentBinding binding) {
        this.router = new COrganizationWorkspaceRouter(fragment);
        //this.adapter = new COrganizationWorkspaceAdapter(fragment.getContext(),this,new ArrayList<>());
        this.fragment = fragment;
        this.binding = binding;
    }

    public void initDataStructures() {

    }

    public void initAppBarLayout(Toolbar toolbar, TextView textView,
                                 CollapsingToolbarLayout collapsingToolbarLayout) {
        textView.setText(R.string.app_name);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        collapsingToolbarLayout.setTitle("My Workspaces");

        /* show the back arrow button*/
        AppCompatActivity activity = ((AppCompatActivity) fragment.getActivity());
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
            }
        }
    }

    public void initDraggableFAB() {
        binding.organizationFAB.setOnClickListener(view -> onClickCreateOrganization());
    }

    private void setAdapter(List<cTreeModel> organizationTreeModels) {
        if (adapter == null) {
            adapter = new COrganizationWorkspaceAdapter(fragment.getContext(),
                    this, organizationTreeModels);

            binding.organizationRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager;
            mLayoutManager = new LinearLayoutManager(fragment.getContext());
            binding.organizationRecyclerView.setLayoutManager(mLayoutManager);
            binding.organizationRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.organizationRecyclerView.setAdapter(adapter);
        } else {
            try {
                adapter.notifyTreeModelChanged(organizationTreeModels);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onInitViewModel() {
        initAppBarLayout(binding.toolbarLayout.toolbar, binding.toolbarLayout.appName,
                binding.toolbarLayout.collapsingToolbarLayout);
        initDataStructures();
        initDraggableFAB();
    }

    // READ ORGANIZATIONS

    @Override
    public void onReadOrganizationWorkspacesFailed(String msg) {
        //Toast.makeText(iView.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReadOrganizationWorkspacesSucceeded(List<cTreeModel> treeModels) {
        Gson gson = new Gson();
        Log.d("TAG", "ORG WITH WORKSPACES " + gson.toJson(treeModels));
        setAdapter(treeModels);
    }

//    @Override
//    public void onReadOrganizationFailed(String msg) {
//        //Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onReadOrganizationSucceeded(COrganizationModel organization, String operation) {
//        if (adapter == null)
//            setAdapter(new cIndexedLinkedHashMap<>());
//        if (operation.equals("ADD")) {
//            adapter.getOrganizationList().add(organization.getOrganizationServerID(),
//                    organization);
//            adapter.reloadList(
//                    adapter.getOrganizationList().size() - 1, operation);
//        }
//
//        if (operation.equals("UPDATE")) {
//            adapter.getOrganizationList().update(organization.getOrganizationServerID(),
//                    organization);
//            adapter.reloadList(
//                    adapter.getOrganizationList().getIndexByKey(
//                            organization.getOrganizationServerID()), operation);
//        }
//
//        if (operation.equals("DELETE")) {
//            adapter.getOrganizationList().update(organization.getOrganizationServerID(),
//                    organization);
//            adapter.reloadList(
//                    adapter.getOrganizationList().getIndexByKey(
//                            organization.getOrganizationServerID()), operation);
//        }
//    }

    /************************************* view model methods *************************************/

    // SWITCH ORGANIZATION WORKSPACES
//    @Override
//    public void onSwitchOrganizationWorkspaceFailed(String msg) {
//        //Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onSwitchOrganizationWorkspaceSucceeded(String msg) {
//        router.actionCOrganizationFragmentToCHomeFragment();
//

//        NavDirections action;
//        action = COrganizationWorkspaceFragmentDirections.actionCOrganizationFragmentToCHomeFragment();
//        Navigation.findNavController(requireView()).navigate(action);

        //Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();


//        // save settings for the long clicked workspace
//        iSharedPreferenceRepository settings;
//        settings = new cSharedPreferenceFirestoreRepositoryImpl(requireContext());
//        settings.saveCurrentWorkspace(workspaceModel.getCompositeServerID());
//
//        Toast.makeText(getContext(), "Workspace '" + workspaceModel.getName() + "' loaded.",
//                Toast.LENGTH_SHORT).show();
//    }

    // READ ORGANIZATION MEMBERS
//    @Override
//    public void onReadOrganizationMembersFailed(String msg) {
//        //Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onReadOrganizationMembersSucceeded(List<CUserProfileModel> userProfileModels) {
//
//    }

    // CREATE ORGANIZATION

    // SWITCH WORKSPACE
    @Override
    public void onLongClickWorkspace(@NonNull CWorkspaceModel workspaceModel) {
        //switchOrganizationWorkspace(workspaceModel);
    }

    @Override
    public void onClickCreateOrganization() {

    }

    @Override
    public void onCreateOrganizationFailed(String msg) {
        Toast.makeText(fragment.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOrganizationSucceeded(String msg) {
        Toast.makeText(fragment.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    // LOGOUT USER
    @Override
    public void onUserSignOutFailed(String msg) {
        Toast.makeText(fragment.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserSignOutSucceeded(String msg) {
        NavDirections action;
        action = COrganizationWorkspaceFragmentDirections.actionCOrganizationFragmentToCLoginFragment();
        Navigation.findNavController(fragment.requireView()).navigate(action);
        Toast.makeText(fragment.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /********************************* use case feedback methods *********************************/

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
        Toast.makeText(fragment.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /************************************ view events methods ************************************/

    private void createAlertDialog() {
        /* inflate the resource for create and update */
        LayoutInflater inflater = fragment.getLayoutInflater();
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
        textViewTitle.setText(fragment.requireContext().getResources().getText(
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
                new MaterialAlertDialogBuilder(fragment.requireActivity(), R.style.AlertDialogTheme);
        alertDialogBuilder.setPositiveButton(fragment.requireContext().getResources().getText(
                R.string.Save), (dialogInterface, i) ->
                createStakeholder(org_index[0],
                        Objects.requireNonNull(editTextName.getText()).toString(),
                        Objects.requireNonNull(editTextEmail.getText()).toString(),
                        Objects.requireNonNull(editTextWebsite.getText()).toString()));

        alertDialogBuilder.setNegativeButton(fragment.requireContext().getResources().getText(
                        R.string.Cancel), (dialogInterface, i) -> {
                })
                .setView(createView)
                .show();
    }

    private void createStakeholder(int typeID, String name, String email, String website) {
        COrganizationModel stakeholderModel = new COrganizationModel(typeID, name, email,
                website);
        //organizationPresenter.createOrganization(stakeholderModel);
    }


    private void deleteAlertDialog(int resID, String title, String message, int position,
                                   String organizationID) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(fragment.requireContext());

        // setting icon to dialog
        TextDrawable faIcon = new TextDrawable(fragment.requireContext());
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(CFontManager.getTypeface(fragment.requireContext(),
                CFontManager.FONTAWESOME));
        faIcon.setText(fragment.requireContext().getResources().getText(resID));
        faIcon.setTextColor(fragment.requireContext().getColor(R.color.colorAccent));
        alertDialogBuilder.setIcon(faIcon);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(fragment.requireContext().getResources().getText(
                        R.string.Yes), (dialog, id) -> {
                    deleteOrganizationModel(organizationID, position);
                    dialog.dismiss();
                })
                .setNegativeButton(fragment.requireContext().getResources().getText(
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
