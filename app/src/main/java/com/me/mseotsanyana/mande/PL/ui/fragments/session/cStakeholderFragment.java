package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cStakeholderModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cStakeholderFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cPermissionFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cStakeholderPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iStakeholderPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cStakeholderAdapter;
import com.me.mseotsanyana.mande.PL.utils.cIndexedLinkedHashMap;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.mande.databinding.SessionStakeholderPageBinding;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cStakeholderFragment extends Fragment implements iStakeholderPresenter.View {
    private static final String TAG = cStakeholderFragment.class.getSimpleName();

    // interfaces
    private iStakeholderPresenter stakeholderPresenter;

    // views
    //private ListenerRegistration listenerRegistration;

    // adapters
    private cStakeholderAdapter stakeholderAdapter;

    // datasets
    //private cIndexedLinkedHashMap<String, cOrganizationModel> organizationModels;
    private final String[] ORG_TYPE = {"My Organization", "My Partner", "My Funder (or Donor)",
            "My Beneficiary", "My Implementing Agency"};
    private SessionStakeholderPageBinding binding;
    Gson gson = new Gson();

    public cStakeholderFragment() {
    }

    public static cStakeholderFragment newInstance() {
        return new cStakeholderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = DataBindingUtil.inflate(inflater, R.layout.session_stakeholder_page, container,
                true);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDataStructures();

        initDraggableFAB();

        stakeholderPresenter.readStakeholders();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stakeholderPresenter.removeListener();
    }

    private void initDataStructures() {
        stakeholderPresenter = new cStakeholderPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cPermissionFirestoreRepositoryImpl(requireContext()),
                new cStakeholderFirestoreRepositoryImpl(requireContext()));
    }

    private void initDraggableFAB() {
        binding.stakeholderFAB.setOnClickListener(view -> onClickCreateStakeholder());
    }

    private void setAdapter(cIndexedLinkedHashMap<String, cStakeholderModel> stakeholderModels) {
        if (stakeholderAdapter == null) {
            stakeholderAdapter = new cStakeholderAdapter(getActivity(), stakeholderModels);
            binding.stakeholderRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(requireActivity());
            binding.stakeholderRecyclerView.setLayoutManager(mLayoutManager);
            binding.stakeholderRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.stakeholderRecyclerView.setAdapter(stakeholderAdapter);
        } else {
            stakeholderAdapter.reloadList(stakeholderModels);
        }
    }

    // READ ORGANIZATIONS

    @Override
    public void onReadStakeholdersFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReadStakeholdersSucceeded(cStakeholderModel stakeholder, String operation) {
        if (stakeholderAdapter == null)
            setAdapter(new cIndexedLinkedHashMap<>());
        if (operation.equals("ADD")) {
            //Gson gson = new Gson();
            //Log.d(TAG, "=========================>>>>>> "+gson.toJson(stakeholderAdapter));
            stakeholderAdapter.getOrganizationList().add(stakeholder.getStakeholderServerID(),
                    stakeholder);
            stakeholderAdapter.reloadList(
                    stakeholderAdapter.getOrganizationList().size() - 1, operation);
        }

        if (operation.equals("UPDATE")) {
            stakeholderAdapter.getOrganizationList().update(stakeholder.getStakeholderServerID(),
                    stakeholder);
            stakeholderAdapter.reloadList(
                    stakeholderAdapter.getOrganizationList().getIndexByKey(
                            stakeholder.getStakeholderServerID()), operation);
        }

        if (operation.equals("DELETE")) {
            stakeholderAdapter.getOrganizationList().update(stakeholder.getStakeholderServerID(),
                    stakeholder);
            stakeholderAdapter.reloadList(
                    stakeholderAdapter.getOrganizationList().getIndexByKey(
                            stakeholder.getStakeholderServerID()), operation);
        }
    }

    // READ ORGANIZATION MEMBERS
    @Override
    public void onReadStakeholderMembersFailed(String msg) {

    }

    @Override
    public void onReadStakeholderMembersSucceeded(List<cUserProfileModel> userProfileModels) {

    }

    // CREATE ORGANIZATION

    @Override
    public void onClickCreateStakeholder() {
        createAlertDialog();
    }

    @Override
    public void onCreateStakeholderFailed(String msg) {

    }

    @Override
    public void onCreateStakeholderSucceeded(String msg) {

    }

    // PRESENTER FUNCTIONS

    @Override
    public void showProgress() {
        //binding.includeProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        //binding.includeProgressBar.setVisibility(View.GONE);
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
        cSingleSpinnerSearch singleSpinner = createView.findViewById(R.id.singleSpinner);
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
            Log.d(TAG, "====================== " + gson.toJson(item.getName()));
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
        cStakeholderModel stakeholderModel = new cStakeholderModel(typeID, name, email,
                website);
        stakeholderPresenter.createStakeholder(stakeholderModel);
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
