package com.me.mseotsanyana.mande.framework.ui.dialogboxes;

import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.databinding.SessionOrgCreateUpdateBinding;
import com.me.mseotsanyana.mande.databinding.SessionWorkspaceCreateUpdateBinding;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CWorkspaceModel;
import com.me.mseotsanyana.mande.framework.ports.CAbstractDialog;
import com.me.mseotsanyana.mande.framework.ports.IDialog;
import com.me.mseotsanyana.mande.application.structures.IRequestDTO;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.COrganizationWorkspaceFragment;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class COrganizationWorkspaceDialog extends CAbstractDialog<COrganizationWorkspaceFragment> /* implements ISessionDialog.IOrganizationWorkspaceDialog */ {
    private static final String TAG = COrganizationWorkspaceDialog.class.getSimpleName();

    private final COrganizationWorkspaceFragment fragment;
    private IDialog dialog;

    private final String[] ORG_TYPE = {"National Partner", "Funder (or Donor)",
            "Beneficiary", "Implementing Agency"};

    @Override
    public void showDeleteDialog(IRequestDTO iRequestDTO) {

    }

    public COrganizationWorkspaceDialog(COrganizationWorkspaceFragment fragment) {
        super(fragment.getContext(), fragment);
        this.fragment = fragment;
    }
//
//    public COrganizationWorkspaceDialog(COrganizationWorkspaceFragment fragment, IDialog dialog) {
//        super(null);
//        this.fragment = fragment;
//        this.dialog = dialog;
//    }

    public COrganizationWorkspaceFragment getFragment() {
        return fragment;
    }

    @Override
    public void createDialog() {

    }

    @Override
    public void updateDialog() {

    }

    public void createOrganizationDialog() {
        /* inflate the resource for create and update */
        LayoutInflater inflater = getFragment().getLayoutInflater();
        SessionOrgCreateUpdateBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.session_org_create_update, null, false);

        /* instantiates create views */
        final int[] org_index = new int[1];
        /* set a title of the create view */
        binding.textViewTitle.setText(getFragment().requireContext().getResources().getText(
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
        binding.singleSpinner.setItem(keyPairBoolOrgs, -1, item -> {
            /* assign selected organization name to the view */
            binding.textViewOrgType.setText(item.getName());
            org_index[0] = (int) item.getId();
        });

        /* create or cancel action */
        MaterialAlertDialogBuilder alertDialogBuilder;
        alertDialogBuilder = new MaterialAlertDialogBuilder(getFragment().requireActivity(),
                R.style.AlertDialogTheme);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getFragment().requireContext().getResources().getText(
                R.string.Save), (dialogInterface, i) -> {
            COrganizationModel organizationModel = new COrganizationModel(
                    org_index[0],
                    Objects.requireNonNull(binding.editTextName.getText()).toString(),
                    Objects.requireNonNull(binding.editTextEmail.getText()).toString(),
                    Objects.requireNonNull(binding.editTextWebsite.getText()).toString());
            getFragment().onClickCreateOrganization(organizationModel);
        });

        alertDialogBuilder.setNegativeButton(getFragment().requireContext().getResources().getText(
                        R.string.Cancel), (dialogInterface, i) -> {
                })
                .setView(binding.getRoot())
                .show();
    }

    public void createWorkspaceDialog(String organizationServerID, int workspaceBITS,
                                      CWorkspaceModel workspaceModel) {

        LayoutInflater inflater = getFragment().getLayoutInflater();
        SessionWorkspaceCreateUpdateBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.session_workspace_create_update, null, false);

        /* create or cancel action */
        MaterialAlertDialogBuilder alertDialogBuilder;
        alertDialogBuilder = new MaterialAlertDialogBuilder(getFragment().requireContext());
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setView(binding.getRoot()).setPositiveButton(
                getFragment().requireContext().getResources().getText(R.string.Save), (dialog, i) -> {
                    workspaceModel.setName(
                            Objects.requireNonNull(binding.editTextName.getText()).toString());
                    workspaceModel.setDescription(
                            Objects.requireNonNull(binding.editTextDescription.getText()).toString());

                    getFragment().onClickCreateWorkspace(organizationServerID, workspaceBITS,
                            workspaceModel);

                    dialog.dismiss();
                });
        alertDialogBuilder.setNegativeButton(getFragment().requireContext().getResources().
                getText(R.string.Cancel), (dialog, i) -> {
            showMessageDialog("Operation cancelled!");
            dialog.dismiss();
        }).show();
    }

    @Override
    public void deleteWorkspaceDialog(String workspaceServerID, List<String> workspaceMembers) {
        new MaterialAlertDialogBuilder(getFragment().requireContext())
                .setTitle("Delete ?")
                .setMessage("Are you sure you want to delete the workspace.")
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, i) -> {
                    getFragment().onDeleteWorkspace(workspaceServerID, workspaceMembers);
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, i) -> {
                    dialog.dismiss();
                })
                .show();
    }

//    @Override
//    public void showMessageDialog(String message) {
//    }
}
