package com.me.mseotsanyana.mande.framework.modelviews.session;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.application.structures.CConstantModel;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;
import com.me.mseotsanyana.mande.application.structures.enums.EAction;
import com.me.mseotsanyana.mande.application.structures.enums.EDialogAction;
import com.me.mseotsanyana.mande.databinding.SessionOrgCreateUpdateBinding;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationWorkspaceFragmentBinding;
import com.me.mseotsanyana.mande.databinding.SessionWorkspaceCreateUpdateBinding;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.framework.ports.CStandardDialog;
import com.me.mseotsanyana.mande.framework.ports.base.AGUIFactory;
import com.me.mseotsanyana.mande.framework.ports.base.CFactoryProvider;
import com.me.mseotsanyana.mande.framework.ports.base.EAdapterType;
import com.me.mseotsanyana.mande.framework.ports.base.EDialogType;
import com.me.mseotsanyana.mande.framework.ports.base.ERouterType;
import com.me.mseotsanyana.mande.framework.ports.base.IBaseAdapter;
import com.me.mseotsanyana.mande.framework.ports.base.IBaseDialog;
import com.me.mseotsanyana.mande.framework.ports.base.IBaseRouter;
import com.me.mseotsanyana.mande.framework.ui.routers.session.COrganizationWorkspaceRouter;
import com.me.mseotsanyana.mande.framework.ui.adapters.session.COrganizationWorkspaceAdapter;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.COrganizationWorkspaceFragment;
import com.me.mseotsanyana.mande.infrastructure.controllers.session.COrganizationWorkspaceControllerImpl;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationWorkspaceController;
import com.me.mseotsanyana.mande.infrastructure.presenters.session.COrganizationWorkspacePresenterImpl;
import com.me.mseotsanyana.mande.infrastructure.repository.firestore.session.COrganizationFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.infrastructure.repository.firestore.session.CUserProfileFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.infrastructure.repository.firestore.session.CWorkspaceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.infrastructure.services.CMainThreadImpl;
import com.me.mseotsanyana.mande.infrastructure.services.CSessionManagerImpl;
import com.me.mseotsanyana.mande.infrastructure.services.CThreadExecutorImpl;
import com.me.mseotsanyana.mande.infrastructure.utils.responsemodel.CTreeModel;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class COrganizationWorkspaceViewModel implements IOrganizationWorkspaceController.IViewModel {
    private static final String TAG = COrganizationWorkspaceViewModel.class.getSimpleName();
    /* organization workspace binding */
    private final SessionOrganizationWorkspaceFragmentBinding binding;
    /* organization workspace view interface */
    private final COrganizationWorkspaceFragment fragment;
    /* organization workspace dialog */
    private final CStandardDialog dialog;
    /* navigation to and from other views */
    private final COrganizationWorkspaceRouter router;
    /* organization workspace adapters */
    private final COrganizationWorkspaceAdapter adapter;
    /* organization workspace controller */
    private IOrganizationWorkspaceController controller;

    private final String[] ORG_TYPE = {"National Partner", "Funder (or Donor)",
            "Beneficiary", "Implementing Agency"};

    public COrganizationWorkspaceViewModel(COrganizationWorkspaceFragment fragment,
                                           SessionOrganizationWorkspaceFragmentBinding binding) {
        this.fragment = fragment;
        this.binding = binding;

        CFactoryProvider factoryProvider = new CFactoryProvider(fragment.getContext(),
                fragment, this);

        AGUIFactory<IBaseRouter> routerFactory;
        routerFactory = factoryProvider.getRouterFactory(ERouterType.ORG_WKS);
        this.router = (COrganizationWorkspaceRouter) routerFactory.create();

        AGUIFactory<IBaseAdapter> adapterFactory;
        adapterFactory = factoryProvider.getAdapterFactory(EAdapterType.ORG_WKS);
        this.adapter = (COrganizationWorkspaceAdapter) adapterFactory.create();

        AGUIFactory<IBaseDialog> dialogFactory;
        dialogFactory = factoryProvider.getDialogFactory(EDialogType.ORG_WKS);
        this.dialog = (CStandardDialog) dialogFactory.create();
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

    public void initController() {
        controller = new COrganizationWorkspaceControllerImpl(
                CThreadExecutorImpl.getInstance(),
                CMainThreadImpl.getInstance(),
                CSessionManagerImpl.getInstance(fragment.getContext()),
                this,
                new COrganizationWorkspacePresenterImpl(this),
                new COrganizationFirestoreRepositoryImpl(fragment.getContext()),
                new CWorkspaceFirestoreRepositoryImpl(fragment.getContext()),
                new CUserProfileFirestoreRepositoryImpl(fragment.getContext())
        );
    }

    private void setAdapter(EAction action, CTreeModel treeModel) {
        //if (adapter == null) {
        if (binding.organizationRecyclerView.getAdapter() == null) {
            //adapter = new COrganizationWorkspaceAdapter(fragment.getContext(),
            //        this, organizationTreeModels);

            binding.organizationRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager;
            mLayoutManager = new LinearLayoutManager(fragment.getContext());
            binding.organizationRecyclerView.setLayoutManager(mLayoutManager);
            binding.organizationRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.organizationRecyclerView.setAdapter(adapter);
        } else {
            adapter.reloadTreeModels(action, treeModel);
        }
    }

    /**************************** event messages received from fragment ***************************/

    @Override
    public void onViewCreated() {
        initAppBarLayout(binding.toolbarLayout.toolbar, binding.toolbarLayout.appName,
                binding.toolbarLayout.collapsingToolbarLayout);
        initDraggableFAB();
        initController();
    }

    @Override
    public void onResumeView() {
        controller.resume();
    }

    @Override
    public void onRemoveListener() {
        controller.removeListener();
    }

    @Override
    public void onSearchAdapter(String query) {
        adapter.getFilter().filter(query);
    }

    @Override
    public void onUserLogOut() {
        controller.signOutWithEmailAndPassword();
    }

    @Override
    public void onUserLoggedOut() {
        router.actionCOrganizationWorkspaceFragmentToCLoginFragment();
        Toast.makeText(fragment.getContext(), "No user signed in!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmailUnverified() {
        router.actionCOrganizationWorkspaceFragmentToCLoginFragment();
        Toast.makeText(fragment.getContext(), "Please check and verify your email!",
                Toast.LENGTH_SHORT).show();
    }

    /************************** controller and presenter feedback methods *************************/

    @Override
    public void showProgress() {
        binding.includeProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.includeProgressBar.setVisibility(View.GONE);
    }

//    @Override
//    public void showResponse(@NonNull Map<String, CTreeModel> response) {
//
//        if (response.containsValue(null)) {
//            router.actionCOrganizationWorkspaceFragmentToCLoginFragment();
//            Set<String> msg = response.keySet();
//            Log.i(TAG, (String) msg.toArray()[0]);
//        } else {
//            setAdapter((CIndexedLinkedHashMap<String, CTreeModel>) response);
//        }
//    }

//    @Override
//    public void showResponse(String response) {
//        setAdapter((CIndexedLinkedHashMap<String, CTreeModel>) response);
//    }

    @Override
    public void onShowTreeModel(IResponseDTO<CTreeModel> response) {
        EAction operation = response.getAction();
        switch (operation) {
            case Added_ORGANIZATION -> setAdapter(EAction.Added_ORGANIZATION, response.getData());
            case Modified_ORGANIZATION ->
                    adapter.reloadTreeModels(EAction.Modified_ORGANIZATION, response.getData());
            case Deleted_ORGANIZATION ->
                    adapter.reloadTreeModels(EAction.Deleted_ORGANIZATION, response.getData());
            case Added_WORKSPACE -> setAdapter(EAction.Added_WORKSPACE, response.getData());
            case Modified_WORKSPACE ->
                    adapter.reloadTreeModels(EAction.Modified_WORKSPACE, response.getData());
            case Deleted_WORKSPACE ->
                    adapter.reloadTreeModels(EAction.Deleted_WORKSPACE, response.getData());
        }

//        if (response.getAction().equals(EAction.Added_ORGANIZATION)) {
//            setAdapter(EAction.Added_ORGANIZATION, response.getData());
//        }
//
//        if (response.getAction().equals(EAction.Modified_ORGANIZATION)) {
//            adapter.updateTreeModels(response.getData());
//        }
//
//        if (response.getAction().equals(EAction.Added_WORKSPACE)) {
//            setAdapter(EAction.Added_WORKSPACE, response.getData());
//        }
    }

//    @Override
//    public void onUpdateTreeModels(IResponseDTO<String> response) {
//        if (response.getAction().equals(EAction.Deleted_WORKSPACE)) {
//            Log.d(TAG, "------->>>>>>> " + response.getData());
//            adapter.reloadTreeModels(response.getData());
//            //adapter.reloadList((CIndexedLinkedHashMap<String, CTreeModel>) response.getData());
//        }
//    }

    @Override
    public void switchResponse(String response) {
        router.actionCOrganizationWorkspaceFragmentToCHomePageFragment();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(fragment.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResponseMessage(String message) {
        router.actionCOrganizationWorkspaceFragmentToCLoginFragment();
        Log.i(TAG, message);
    }

    /*********************************** on click event methods ***********************************/

    @Override
    public void onClickCreateWorkspace(CWorkspaceModel workspaceModel) {
        SessionWorkspaceCreateUpdateBinding binding = DataBindingUtil.inflate(
                fragment.getLayoutInflater(), R.layout.session_workspace_create_update,
                null, false);

        binding.setWorkspace(workspaceModel);

        dialog.showCreateOrUpdateCustomDialog("New Workspace", binding.getRoot(), (action) -> {
            workspaceModel.setName(String.valueOf(binding.editTextName.getText()));
            workspaceModel.setDescription(String.valueOf(binding.editTextDescription.getText()));
            controller.createWorkspace(workspaceModel);
        });
    }

    @Override
    public void onClickDeleteWorkspace(int workspaceBITS, CWorkspaceModel workspaceModel) {
        dialog.showAlertMessageDialog("Delete ?",
                "Are you sure you want to delete the workspace.", action -> {
                    if (action.equals(EDialogAction.YES))
                        controller.deleteWorkspace(workspaceBITS, workspaceModel);
                    else
                        showMessage("Delete cancelled!!");
                });
    }

    @Override
    public void onClickUpdateWorkspace(CWorkspaceModel workspaceModel) {
        SessionWorkspaceCreateUpdateBinding binding = DataBindingUtil.inflate(
                fragment.getLayoutInflater(), R.layout.session_workspace_create_update,
                null, false);

        binding.setWorkspace(workspaceModel);
        binding.editTextName.setText(workspaceModel.getName());
        binding.editTextDescription.setText(workspaceModel.getDescription());

        dialog.showCreateOrUpdateCustomDialog("Update Workspace",
                binding.getRoot(), (action) -> {
                    if (action.equals(EDialogAction.SAVE)) {
                        workspaceModel.setName(String.valueOf(binding.editTextName.getText()));
                        workspaceModel.setDescription(Objects.requireNonNull(
                                binding.editTextDescription.getText()).toString().trim());
                        controller.updateWorkspace(workspaceModel);
                    } else {
                        showMessage("Update cancelled!!");
                    }
                });
    }

    @Override
    public void onClickReadOrganizationWorkspaces(String organizationServerID) {
        controller.readWorkspaces(organizationServerID);
    }

    @Override
    public void onLongClickWorkspace(@NonNull CWorkspaceModel workspaceModel) {
        controller.chooseWorkspaceRequest(workspaceModel);
    }

    @Override
    public void onClickUpdateOrganization(COrganizationModel organizationModel) {
        SessionOrgCreateUpdateBinding binding = DataBindingUtil.inflate(
                fragment.getLayoutInflater(), R.layout.session_org_create_update,
                null, false);

        binding.setOrganization(organizationModel);
        binding.textViewOrgType.setText(organizationModel.getType());
        binding.editTextName.setText(organizationModel.getName());
        binding.editTextEmail.setText(organizationModel.getEmail());
        binding.editTextWebsite.setText(organizationModel.getWebsite());

        /* populate the logical model with the create views */
        /* 1. create selection dialog box for organizations */
        List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
        for (int i = 0; i < ORG_TYPE.length; i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            idNameBool.setId(i);
            idNameBool.setName(ORG_TYPE[i]);
            idNameBool.setSelected(ORG_TYPE[i].equals(organizationModel.getType()));
            keyPairBoolOrgs.add(idNameBool);
        }
        // called when click spinner
        final int[] org_index = new int[1];

        binding.singleSpinner.setItem(keyPairBoolOrgs, -1, item -> {
            /* assign selected organization name to the view */
            binding.textViewOrgType.setText(item.getName());
            org_index[0] = (int) item.getId();//callback needed...FIXME
        });

        dialog.showCreateOrUpdateCustomDialog("Update Workspace",
                binding.getRoot(), (action) -> {
                    if (action.equals(EDialogAction.SAVE)) {
                        organizationModel.setTypeID(org_index[0]);
                        organizationModel.setType(String.valueOf(binding.textViewOrgType.getText()));
                        organizationModel.setName(String.valueOf(binding.editTextName.getText()));
                        organizationModel.setEmail(String.valueOf(binding.editTextEmail.getText()));
                        organizationModel.setWebsite(String.valueOf(binding.editTextWebsite.getText()));

                        controller.updateOrganization(organizationModel);
                    } else {
                        showMessage("Update cancelled!!");
                    }
                });
    }

    @Override
    public void onClickDeleteOrganization(String organizationServerID) {
        dialog.showAlertMessageDialog("Delete ?",
                "Are you sure you want to delete the organization.", action -> {
                    if (action.equals(EDialogAction.YES))
                        controller.deleteOrganization(organizationServerID);
                    else
                        showMessage("Delete cancelled!!");
                });
    }

    @Override
    public void onClickCreateOrganization() {
        /* inflate the resource for create and update */
        LayoutInflater inflater = fragment.getLayoutInflater();
        SessionOrgCreateUpdateBinding sessionBinding = DataBindingUtil.inflate(inflater,
                R.layout.session_org_create_update, null, false);

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
        final int[] org_index = new int[1];


        sessionBinding.singleSpinner.setItem(keyPairBoolOrgs, -1, item -> {
            /* assign selected organization name to the view */
            sessionBinding.textViewOrgType.setText(item.getName());
            org_index[0] = (int) item.getId();//callback needed...FIXME
        });

        dialog.showCreateOrUpdateCustomDialog("New Organization",
                sessionBinding.getRoot(), (action) -> {

                    COrganizationModel organizationModel = new COrganizationModel(
                            org_index[0],
                            String.valueOf(sessionBinding.editTextName.getText()),
                            String.valueOf(sessionBinding.editTextEmail.getText()),
                            String.valueOf(sessionBinding.editTextWebsite.getText()));

                    controller.createOrganization(organizationModel);
                });
    }
}

//    //---- control handler
//    private final IOrganizationWorkspaceListener listener = new IOrganizationWorkspaceListener() {
//        @Override
//        public void OnDeleteWorkspace(String dialogAction, int workspaceBITS,
//                                      CWorkspaceModel workspaceModel) {
//            controller.deleteWorkspace(workspaceBITS, workspaceModel);
//        }
//
//        @Override
//        public void OnCreateOrganization(Object object) {
//
//        }
//
//        @Override
//        public void OnUpdateOrganization(Object object) {
//
//        }
//
//        @Override
//        public void OnDeleteOrganization(String dialogAction) {
//
//        }
//
//        @Override
//        public void OnCreateWorkspace(CWorkspaceModel workspaceModel) {
//            controller.createWorkspace(workspaceModel);
//        }
//
//        @Override
//        public void OnUpdateWorkspace(Object object) {
//
//        }
//    };

//----

//    void showDialog(String action){
//        dialog.showDeleteDialog(action, new IOrganizationWorkspaceListener() {
//            @Override
//            public void OnDeleteWorkspace(String dialogAction) {
//                if(dialogAction.equals("YES")){
//                    controller.deleteWorkspace(workspaceServerID, workspaceMembers);
//                }else {
//                    Toast.makeText(fragment.getContext(), "Action Cancelled", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void OnCreateWorkspace(Object object) {
//
//            }
//        });
//    }


//    @Override
//    public void onError(String msg) {
//        //Toast.makeText(iView.getContext(), msg, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onSuccess(CIndexedLinkedHashMap<String, CTreeModel> treeModels) {
//        //setAdapter(treeModels);
//    }
//
//    @Override
//    public void OnReadWorkspaceSucceeded(CTreeModel treeModel) {
//        //adapter.updateAllNodes(treeModel, absoluteAdapterPosition);
//    }

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

//    @Override
//    public void OnCreateOrganizationFailed(String msg) {
//        Toast.makeText(fragment.getContext(), msg, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void OnCreateOrganizationSucceeded(String msg) {
//        Toast.makeText(fragment.getContext(), msg, Toast.LENGTH_SHORT).show();
//    }
//
//    // LOGOUT USER
//    @Override
//    public void OnUserSignOutFailed(String msg) {
//        Toast.makeText(fragment.getContext(), msg, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void OnUserSignOutSucceeded(String msg) {
//        router.actionCOrganizationWorkspaceFragmentToCHomeFragment();
//    }


//    @Override
//    public void OnPreferenceClearedSucceeded(String msg) {
//        Log.d(TAG, msg);
//        router.actionCOrganizationWorkspaceFragmentToCLoginFragment();
//    }
//
//    @Override
//    public void OnPreferenceClearedFailed(String msg) {
//        Toast.makeText(fragment.getContext(), msg, Toast.LENGTH_SHORT).show();
//        router.actionCOrganizationWorkspaceFragmentToCLoginFragment();
//    }


//    private void setAdapter(List<cTreeModel> organizationTreeModels) {
//        if (adapter == null) {
//            adapter = new COrganizationWorkspaceAdapter(fragment.getContext(),
//                    this, organizationTreeModels);
//
//            binding.organizationRecyclerView.setHasFixedSize(true);
//            RecyclerView.LayoutManager mLayoutManager;
//            mLayoutManager = new LinearLayoutManager(fragment.getContext());
//            binding.organizationRecyclerView.setLayoutManager(mLayoutManager);
//            binding.organizationRecyclerView.setItemAnimator(new DefaultItemAnimator());
//            binding.organizationRecyclerView.setAdapter(adapter);
//        } else {
//            try {
//                adapter.notifyTreeModelChanged(organizationTreeModels);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//    }


//=========================================

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
