package com.me.mseotsanyana.mande.framework.ui.dialogs.session;

import android.content.Context;

import com.me.mseotsanyana.mande.framework.ports.CStandardDialog;
import com.me.mseotsanyana.mande.framework.ports.base.IBaseFragment;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.COrganizationWorkspaceFragment;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationWorkspaceController;

public class COrganizationWorkspaceDialog extends CStandardDialog {
    //private static final String TAG = COrganizationWorkspaceDialog.class.getSimpleName();

    //private Context context;
    private final IBaseFragment baseFragment;
    private final IOrganizationWorkspaceController.IViewModel iViewModel;

    private final String[] ORG_TYPE = {"National Partner", "Funder (or Donor)",
            "Beneficiary", "Implementing Agency"};

    public COrganizationWorkspaceDialog(Context context, IBaseFragment baseFragment,
                                        IOrganizationWorkspaceController.IViewModel iViewModel) {
        super(context);
        this.baseFragment = baseFragment;
        this.iViewModel = iViewModel;
    }

    public COrganizationWorkspaceFragment getFragment() {
        return (COrganizationWorkspaceFragment) baseFragment;
    }

//    @Override
//    public void showCreateDialog() {
//        // create organization==============================================
//
//        /* inflate the resource for create and update */
//        LayoutInflater inflater = getFragment().getLayoutInflater();
//        SessionOrgCreateUpdateBinding binding = DataBindingUtil.inflate(inflater,
//                R.layout.session_org_create_update, null, false);
//
//        /* instantiates create views */
//        /* set a title of the create view */
//        binding.textViewTitle.setText(getFragment().requireContext().getResources().getText(
//                R.string.organization_create_title));
//
//        /* populate the logical model with the create views */
//        /* 1. create selection dialog box for organizations */
//        List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
//        for (int i = 0; i < ORG_TYPE.length; i++) {
//            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
//            idNameBool.setId(i);
//            idNameBool.setName(ORG_TYPE[i]);
//            idNameBool.setSelected(false);
//            keyPairBoolOrgs.add(idNameBool);
//        }
//
//        // called when click spinner
//        final int[] org_index = new int[1];
//        binding.singleSpinner.setItem(keyPairBoolOrgs, -1, item -> {
//            /* assign selected organization name to the view */
//            binding.textViewOrgType.setText(item.getName());
//            org_index[0] = (int) item.getId();//callback needed...
//        });
////===================================================
//        /* create or cancel action */
//        MaterialAlertDialogBuilder alertDialogBuilder;
//        alertDialogBuilder = new MaterialAlertDialogBuilder(getFragment().requireActivity(),
//                R.style.AlertDialogTheme);
//        alertDialogBuilder.setCancelable(false);
//        alertDialogBuilder.setPositiveButton(getFragment().requireContext().getResources().getText(
//                R.string.Save), (dialogInterface, i) -> {
//            COrganizationModel organizationModel = new COrganizationModel(
//                    org_index[0],
//                    Objects.requireNonNull(binding.editTextName.getText()).toString(),
//                    Objects.requireNonNull(binding.editTextEmail.getText()).toString(),
//                    Objects.requireNonNull(binding.editTextWebsite.getText()).toString());
//            iViewModel.onClickCreateOrganization(organizationModel);
//        });
//
//        alertDialogBuilder.setNegativeButton(getFragment().requireContext().getResources().getText(
//                        R.string.Cancel), (dialogInterface, i) -> {
//                })
//                .setView(binding.getRoot())
//                .show();
//    }

//    public void createWorkspaceDialog(CWorkspaceModel workspaceModel,
//                                      IOrganizationWorkspaceListener listener) {
//
//        LayoutInflater inflater = getFragment().getLayoutInflater();
//
//
//        SessionWorkspaceCreateUpdateBinding binding = DataBindingUtil.inflate(inflater,
//                R.layout.session_workspace_create_update, null, false);
//
//        /* create or cancel action */
//        MaterialAlertDialogBuilder alertDialogBuilder;
//        alertDialogBuilder = new MaterialAlertDialogBuilder(getFragment().requireContext());
//        alertDialogBuilder.setCancelable(false);
//        alertDialogBuilder.setView(binding.getRoot()).setPositiveButton(
//                getFragment().requireContext().getResources().getText(R.string.Save), (dialog, i) -> {
//                    workspaceModel.setName(
//                            Objects.requireNonNull(binding.editTextName.getText()).toString());
//                    workspaceModel.setDescription(
//                            Objects.requireNonNull(binding.editTextDescription.getText()).toString());
//
//                    listener.OnCreateWorkspace(workspaceModel);
//
//                    dialog.dismiss();
//                });
//        alertDialogBuilder.setNegativeButton(getFragment().requireContext().getResources().
//                getText(R.string.Cancel), (dialog, i) -> {
//            //showMessageDialog("Operation cancelled!");
//            dialog.dismiss();
//        }).show();
//    }
}
