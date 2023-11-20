package com.me.mseotsanyana.mande.framework.ui.dialogboxes;

import android.text.Layout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.domain.entities.models.session.COrganizationModel;
import com.me.mseotsanyana.mande.framework.ui.fragments.session.COrganizationWorkspaceFragment;
import com.me.mseotsanyana.mande.framework.utils.CFontManager;
import com.me.mseotsanyana.mande.framework.utils.CTextDrawable;
import com.me.mseotsanyana.multiselectspinnerlibrary.CSingleSpinnerSearch;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class COrganizationDialog {

    private final COrganizationWorkspaceFragment fragment;
    private final String[] ORG_TYPE = {"National Partner", "Funder (or Donor)",
            "Beneficiary", "Implementing Agency"};
    public COrganizationDialog(COrganizationWorkspaceFragment fragment) {
        this.fragment = fragment;
    }

    public void deleteOrganizationModel(String logFrameID, int position) {
        //organizationPresenter.deleteLogFrameModel(logFrameID, position);
    }

    /************************************ view events methods ************************************/

    private void createOrganizationDialog() {
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
        CTextDrawable faIcon = new CTextDrawable(fragment.requireContext());
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
                    //deleteOrganizationModel(organizationID, position);
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

}
