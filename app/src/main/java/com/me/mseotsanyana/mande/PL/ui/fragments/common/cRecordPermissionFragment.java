package com.me.mseotsanyana.mande.PL.ui.fragments.common;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cWorkspaceModel;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.entities.models.common.cRecordPermissionCaretakerModel;
import com.me.mseotsanyana.mande.BLL.entities.models.common.cRecordPermissionMementoModel;
import com.me.mseotsanyana.mande.BLL.entities.models.common.cRecordPermissionModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.common.cRecordPermissionFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.common.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.programme.cProjectFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.Impl.cRecordPermissionPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iRecordPermissionPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.mande.databinding.MeCommonAttributesBinding;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class cRecordPermissionFragment extends Fragment implements iRecordPermissionPresenter.View {
    //private static final String TAG = cCommonFragment.class.getSimpleName();
    private static final SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private iRecordPermissionPresenter recordPermissionPresenter;
    private MeCommonAttributesBinding binding;

    private cRecordPermissionModel recordPermissionModel;

    private cRecordPermissionCaretakerModel caretaker;

    private String entityServerID;

    public cRecordPermissionFragment() {
    }

    @NonNull
    public static cRecordPermissionFragment newInstance(String entityServerID,
                                                        cRecordPermissionModel recordPermissionModel) {
        Bundle bundle = new Bundle();
        cRecordPermissionFragment fragment = new cRecordPermissionFragment();
        bundle.putString("ENTITY_ID", entityServerID);
        bundle.putParcelable("RECORD_PERMISSION", recordPermissionModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        recordPermissionPresenter.resume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.me_common_attributes, container,
                false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // initialise record permissions
        assert getArguments() != null;
        entityServerID = getArguments().getString("ENTITY_ID");
        recordPermissionModel = getArguments().getParcelable("RECORD_PERMISSION");

        // create and save copy of the current record permissions
        caretaker = new cRecordPermissionCaretakerModel();
        caretaker.saveMemento(recordPermissionModel.createMemento());

        // initialise presenters
        initDataStructures();

        // initialise views
        initCommonViews(recordPermissionModel);
    }

    private void initDataStructures() {
        /* instantiate presenters */
        recordPermissionPresenter = new cRecordPermissionPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cRecordPermissionFirestoreRepositoryImpl(),
                new cProjectFirestoreRepositoryImpl(requireContext()));
    }

    private void initCommonViews(@NonNull cRecordPermissionModel recordPermissionModel) {
        // initially disable spinners until initialised.
        binding.appCompatSpinnerOwner.setEnabled(false);
        binding.appCompatSpinnerTeam.setEnabled(false);
        binding.appCompatSpinnerOrganization.setEnabled(false);

        // initially disable reset and update buttons.
        binding.buttonReset.setEnabled(false);
        binding.buttonReset.setTextColor(
                requireContext().getColor(android.R.color.darker_gray));
        binding.buttonUpdate.setEnabled(false);
        binding.buttonUpdate.setTextColor(
                requireContext().getColor(android.R.color.darker_gray));

        binding.appCompatTextViewOwner.setText(new StringBuilder("N/A"));
        binding.appCompatTextViewOrganization.setText(new StringBuilder("N/A"));
        binding.appCompatTextViewTeam.setText(new StringBuilder("N/A"));

        binding.textViewCreatedDate.setText(sdf.format(recordPermissionModel.getCreatedDate()));
        binding.textViewModifiedDate.setText(sdf.format(recordPermissionModel.getModifiedDate()));

        setupStatusBITS(recordPermissionModel.getStatusBIT());
        setupUnixPerms(recordPermissionModel.getUnixpermBITS());

        binding.switchDeleted.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            SwitchMaterial sm = (SwitchMaterial) compoundButton;
            if (isChecked) {
                sm.setChecked(true);
                recordPermissionModel.setStatusBIT(cBitwise.DELETED);

                binding.switchBlocked.setChecked(false);
                binding.switchActivated.setChecked(false);
                binding.switchCancelled.setChecked(false);
                binding.switchPending.setChecked(false);
            } else if (!binding.switchBlocked.isChecked() && !binding.switchActivated.isChecked() &&
                    !binding.switchCancelled.isChecked() && !binding.switchPending.isChecked()) {
                sm.setChecked(true);
            }
            recordPermissionModel.setModifiedDate(new Date());

            enableButtons();
        });

        binding.switchBlocked.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            SwitchMaterial sm = (SwitchMaterial) compoundButton;
            if (isChecked) {
                sm.setChecked(true);
                recordPermissionModel.setStatusBIT(cBitwise.BLOCKED);

                binding.switchDeleted.setChecked(false);
                binding.switchActivated.setChecked(false);
                binding.switchCancelled.setChecked(false);
                binding.switchPending.setChecked(false);
            } else if (!binding.switchDeleted.isChecked() && !binding.switchActivated.isChecked() &&
                    !binding.switchCancelled.isChecked() && !binding.switchPending.isChecked()) {
                sm.setChecked(true);
            }
            recordPermissionModel.setModifiedDate(new Date());

            enableButtons();
        });

        binding.switchActivated.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            SwitchMaterial sm = (SwitchMaterial) compoundButton;
            if (isChecked) {
                sm.setChecked(true);
                recordPermissionModel.setStatusBIT(cBitwise.ACTIVATED);

                binding.switchDeleted.setChecked(false);
                binding.switchBlocked.setChecked(false);
                binding.switchCancelled.setChecked(false);
                binding.switchPending.setChecked(false);
            } else if (!binding.switchDeleted.isChecked() && !binding.switchBlocked.isChecked() &&
                    !binding.switchCancelled.isChecked() && !binding.switchPending.isChecked()) {
                sm.setChecked(true);
            }
            recordPermissionModel.setModifiedDate(new Date());

            enableButtons();
        });

        binding.switchCancelled.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            SwitchMaterial sm = (SwitchMaterial) compoundButton;
            if (isChecked) {
                sm.setChecked(true);
                recordPermissionModel.setStatusBIT(cBitwise.CANCELLED);

                binding.switchDeleted.setChecked(false);
                binding.switchBlocked.setChecked(false);
                binding.switchActivated.setChecked(false);
                binding.switchPending.setChecked(false);
            } else if (!binding.switchDeleted.isChecked() && !binding.switchBlocked.isChecked() &&
                    !binding.switchActivated.isChecked() && !binding.switchPending.isChecked()) {
                sm.setChecked(true);
            }
            recordPermissionModel.setModifiedDate(new Date());

            enableButtons();
        });

        binding.switchPending.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            SwitchMaterial sm = (SwitchMaterial) compoundButton;
            if (isChecked) {
                sm.setChecked(true);
                recordPermissionModel.setStatusBIT(cBitwise.PENDING);

                binding.switchDeleted.setChecked(false);
                binding.switchBlocked.setChecked(false);
                binding.switchActivated.setChecked(false);
                binding.switchCancelled.setChecked(false);
            } else if (!binding.switchDeleted.isChecked() && !binding.switchBlocked.isChecked() &&
                    !binding.switchActivated.isChecked() && !binding.switchCancelled.isChecked()) {
                sm.setChecked(true);
            }
            recordPermissionModel.setModifiedDate(new Date());

            enableButtons();
        });

//FIXME        /* owner permissions */
//        binding.checkBoxOwnerRead.setOnClickListener(ownerReadView -> {
//            boolean checked = ((CheckBox) ownerReadView).isChecked();
//            binding.checkBoxOwnerRead.setChecked(checked);
//            if (checked) {
//                recordPermissionModel.getUnixpermBITS().add(cBitwise.OWNER_READ);
//            } else {
//                recordPermissionModel.getUnixpermBITS().remove((Integer) cBitwise.OWNER_READ);
//            }
//            recordPermissionModel.setModifiedDate(new Date());
//
//            enableButtons();
//        });
//        binding.checkBoxOwnerUpdate.setOnClickListener(ownerUpdateView -> {
//            boolean checked = ((CheckBox) ownerUpdateView).isChecked();
//            binding.checkBoxOwnerUpdate.setChecked(checked);
//            if (checked) {
//                recordPermissionModel.getUnixpermBITS().add(cBitwise.OWNER_UPDATE);
//            } else {
//                recordPermissionModel.getUnixpermBITS().remove((Integer) cBitwise.OWNER_UPDATE);
//            }
//            recordPermissionModel.setModifiedDate(new Date());
//
//            enableButtons();
//        });
//        binding.checkBoxOwnerDelete.setOnClickListener(ownerDeleteView -> {
//            boolean checked = ((CheckBox) ownerDeleteView).isChecked();
//            binding.checkBoxOwnerDelete.setChecked(checked);
//            if (checked) {
//                recordPermissionModel.getUnixpermBITS().add(cBitwise.OWNER_DELETE);
//            } else {
//                recordPermissionModel.getUnixpermBITS().remove((Integer) cBitwise.OWNER_DELETE);
//            }
//            recordPermissionModel.setModifiedDate(new Date());
//
//            enableButtons();
//        });
//
//        /* primary permissions */
//        binding.checkBoxPrimaryRead.setOnClickListener(primaryReadView -> {
//            boolean checked = ((CheckBox) primaryReadView).isChecked();
//            binding.checkBoxPrimaryRead.setChecked(checked);
//            if (checked) {
//                recordPermissionModel.getUnixpermBITS().add(cBitwise.PRIMARY_TEAM_READ);
//            } else {
//                recordPermissionModel.getUnixpermBITS().remove((Integer) cBitwise.PRIMARY_TEAM_READ);
//            }
//            recordPermissionModel.setModifiedDate(new Date());
//
//            enableButtons();
//        });
//        binding.checkBoxPrimaryUpdate.setOnClickListener(primaryUpdateView -> {
//            boolean checked = ((CheckBox) primaryUpdateView).isChecked();
//            binding.checkBoxPrimaryUpdate.setChecked(checked);
//            if (checked) {
//                recordPermissionModel.getUnixpermBITS().add(cBitwise.PRIMARY_TEAM_UPDATE);
//            } else {
//                recordPermissionModel.getUnixpermBITS().remove((Integer) cBitwise.PRIMARY_TEAM_UPDATE);
//            }
//            recordPermissionModel.setModifiedDate(new Date());
//
//            enableButtons();
//        });
//        binding.checkBoxPrimaryDelete.setOnClickListener(primaryDeleteView -> {
//            boolean checked = ((CheckBox) primaryDeleteView).isChecked();
//            binding.checkBoxPrimaryDelete.setChecked(checked);
//            if (checked) {
//                recordPermissionModel.getUnixpermBITS().add(cBitwise.PRIMARY_TEAM_DELETE);
//            } else {
//                recordPermissionModel.getUnixpermBITS().remove((Integer) cBitwise.PRIMARY_TEAM_DELETE);
//            }
//            recordPermissionModel.setModifiedDate(new Date());
//
//            enableButtons();
//        });
//
//        /* secondary permissions */
//        binding.checkBoxSecondaryRead.setOnClickListener(secondaryReadView -> {
//            boolean checked = ((CheckBox) secondaryReadView).isChecked();
//            binding.checkBoxSecondaryRead.setChecked(checked);
//            if (checked) {
//                recordPermissionModel.getUnixpermBITS().add(cBitwise.SECONDARY_TEAM_READ);
//            } else {
//                recordPermissionModel.getUnixpermBITS().remove((Integer) cBitwise.SECONDARY_TEAM_READ);
//            }
//            recordPermissionModel.setModifiedDate(new Date());
//
//            enableButtons();
//        });
//        binding.checkBoxSecondaryUpdate.setOnClickListener(secondaryUpdateView -> {
//            boolean checked = ((CheckBox) secondaryUpdateView).isChecked();
//            binding.checkBoxSecondaryUpdate.setChecked(checked);
//            if (checked) {
//                recordPermissionModel.getUnixpermBITS().add(cBitwise.SECONDARY_TEAM_UPDATE);
//            } else {
//                recordPermissionModel.getUnixpermBITS().remove((Integer) cBitwise.SECONDARY_TEAM_UPDATE);
//            }
//            recordPermissionModel.setModifiedDate(new Date());
//
//            enableButtons();
//        });
//        binding.checkBoxSecondaryDelete.setOnClickListener(secondaryDeleteView -> {
//            boolean checked = ((CheckBox) secondaryDeleteView).isChecked();
//            binding.checkBoxSecondaryDelete.setChecked(checked);
//            if (checked) {
//                recordPermissionModel.getUnixpermBITS().add(cBitwise.SECONDARY_TEAM_DELETE);
//            } else {
//                recordPermissionModel.getUnixpermBITS().remove((Integer) cBitwise.SECONDARY_TEAM_DELETE);
//            }
//            recordPermissionModel.setModifiedDate(new Date());
//
//            enableButtons();
//        });
//
//        /* organization permissions */
//        binding.checkBoxOrganizationRead.setOnClickListener(organizationReadView -> {
//            boolean checked = ((CheckBox) organizationReadView).isChecked();
//            binding.checkBoxOrganizationRead.setChecked(checked);
//            if (checked) {
//                recordPermissionModel.getUnixpermBITS().add(cBitwise.ORGANIZATION_READ);
//            } else {
//                recordPermissionModel.getUnixpermBITS().remove((Integer) cBitwise.ORGANIZATION_READ);
//            }
//            recordPermissionModel.setModifiedDate(new Date());
//
//            enableButtons();
//        });
//        binding.checkBoxOrganizationUpdate.setOnClickListener(organizationUpdateView -> {
//            boolean checked = ((CheckBox) organizationUpdateView).isChecked();
//            binding.checkBoxOrganizationUpdate.setChecked(checked);
//            if (checked) {
//                recordPermissionModel.getUnixpermBITS().add(cBitwise.ORGANIZATION_UPDATE);
//            } else {
//                recordPermissionModel.getUnixpermBITS().remove((Integer) cBitwise.ORGANIZATION_UPDATE);
//            }
//            recordPermissionModel.setModifiedDate(new Date());
//
//            enableButtons();
//        });
//        binding.checkBoxOrganizationDelete.setOnClickListener(organizationDeleteView -> {
//            boolean checked = ((CheckBox) organizationDeleteView).isChecked();
//            binding.checkBoxOrganizationDelete.setChecked(checked);
//
//            if (checked) {
//                recordPermissionModel.getUnixpermBITS().add(cBitwise.ORGANIZATION_DELETE);
//            } else {
//                recordPermissionModel.getUnixpermBITS().remove((Integer) cBitwise.ORGANIZATION_DELETE);
//            }
//            recordPermissionModel.setModifiedDate(new Date());
//
//            enableButtons();
//        });

        binding.buttonReset.setOnClickListener(ownerReadView -> {
            cRecordPermissionMementoModel memento = caretaker.getMemento();
            recordPermissionModel.restoreMemento(memento);

            setupStatusBITS(recordPermissionModel.getStatusBIT());
            setupUnixPerms(recordPermissionModel.getUnixpermBITS());

            disableButtons();
        });

        binding.buttonUpdate.setOnClickListener(view -> {
            recordPermissionPresenter.updateRecordPermissions(entityServerID, recordPermissionModel);

            disableButtons();
        });
    }

    private void setupStatusBITS(int status_bit) {
        //boolean isStatusChecked;
        switch (status_bit) {
            case cBitwise.DELETED:
                binding.switchDeleted.setChecked(true);
                binding.switchDeleted.setTag(true);
                break;
            case cBitwise.BLOCKED:
                binding.switchBlocked.setChecked(true);
                binding.switchBlocked.setTag(true);
                break;
            case cBitwise.ACTIVATED:
                binding.switchActivated.setChecked(true);
                binding.switchActivated.setTag(true);
                break;
            case cBitwise.CANCELLED:
                binding.switchCancelled.setChecked(true);
                binding.switchCancelled.setTag(true);
                break;
            case cBitwise.PENDING:
                binding.switchPending.setChecked(true);
                binding.switchPending.setTag(true);
                break;
        }
    }

    private void setupUnixPerms(int unixpermBITS) {

        clearPermissions();

//FIXME        for (Integer perm_bit : unixpermBITS) {
//            switch (perm_bit) {
//                case cBitwise.OWNER_READ:
//                    binding.checkBoxOwnerRead.setChecked(true);
//                    binding.checkBoxOwnerRead.setTag(perm_bit.toString());
//                    break;
//                case cBitwise.OWNER_UPDATE:
//                    binding.checkBoxOwnerUpdate.setChecked(true);
//                    binding.checkBoxOwnerUpdate.setTag(perm_bit.toString());
//                    break;
//                case cBitwise.OWNER_DELETE:
//                    binding.checkBoxOwnerDelete.setChecked(true);
//                    binding.checkBoxOwnerDelete.setTag(perm_bit.toString());
//                    break;
//                case cBitwise.PRIMARY_TEAM_READ:
//                    binding.checkBoxPrimaryRead.setChecked(true);
//                    binding.checkBoxPrimaryRead.setTag(perm_bit.toString());
//                    break;
//                case cBitwise.PRIMARY_TEAM_UPDATE:
//                    binding.checkBoxPrimaryUpdate.setChecked(true);
//                    binding.checkBoxPrimaryUpdate.setTag(perm_bit.toString());
//                    break;
//                case cBitwise.PRIMARY_TEAM_DELETE:
//                    binding.checkBoxPrimaryDelete.setChecked(true);
//                    binding.checkBoxPrimaryDelete.setTag(perm_bit.toString());
//                    break;
//                case cBitwise.SECONDARY_TEAM_READ:
//                    binding.checkBoxSecondaryRead.setChecked(true);
//                    binding.checkBoxSecondaryRead.setTag(perm_bit.toString());
//                    break;
//                case cBitwise.SECONDARY_TEAM_UPDATE:
//                    binding.checkBoxSecondaryUpdate.setChecked(true);
//                    binding.checkBoxSecondaryUpdate.setTag(perm_bit.toString());
//                    break;
//                case cBitwise.SECONDARY_TEAM_DELETE:
//                    binding.checkBoxSecondaryDelete.setChecked(true);
//                    binding.checkBoxSecondaryDelete.setTag(perm_bit.toString());
//                    break;
//                case cBitwise.ORGANIZATION_READ:
//                    binding.checkBoxOrganizationRead.setChecked(true);
//                    binding.checkBoxOrganizationRead.setTag(perm_bit.toString());
//                    break;
//                case cBitwise.ORGANIZATION_UPDATE:
//                    binding.checkBoxOrganizationUpdate.setChecked(true);
//                    binding.checkBoxOrganizationUpdate.setTag(perm_bit.toString());
//                    break;
//                case cBitwise.ORGANIZATION_DELETE:
//                    binding.checkBoxOrganizationDelete.setChecked(true);
//                    binding.checkBoxOrganizationDelete.setTag(perm_bit.toString());
//                    break;
//            }
//        }
    }

    @Nullable
    private Pair<String, String> getOwnerName(String ownerID,
                                              @NonNull List<CUserProfileModel> userProfileModels) {
        for (CUserProfileModel user : userProfileModels) {
            if (user.getUserOwnerID().equals(ownerID)) {
                return new Pair<>(user.getName(), user.getSurname());
            }
        }
        return null;
    }

    @Nullable
    private String getTeamName(String teamID, @NonNull List<cWorkspaceModel> teamModels) {
        for (cWorkspaceModel team : teamModels) {
            if (team.getWorkspaceServerID().equals(teamID)) {
                return team.getName();
            }
        }
        return null;
    }

    @Nullable
    private String getOrganizationName(String stakeholderID,
                                       @NonNull List<cOrganizationModel> stakeholderModels) {
        for (cOrganizationModel stakeholder : stakeholderModels) {
            if (stakeholder.getOrganizationOwnerID().equals(stakeholderID)) {
                return stakeholder.getName();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onReadRecordPermissionsSucceeded(@NonNull Map<String, Object> propertyLists) {
        for (Map.Entry<String, Object> entry : propertyLists.entrySet()) {
            String key = entry.getKey();
            Object object = entry.getValue();
            switch (key) {
                case "ORGANIZATIONS":
                    // assign organization label
                    List<cOrganizationModel> stakeholderModels;
                    stakeholderModels = (List<cOrganizationModel>) object;
                    String organizationID = String.valueOf(recordPermissionModel.getOrganizationOwnerID());
                    binding.appCompatTextViewOrganization.setText(
                            getOrganizationName(organizationID, stakeholderModels));

                    // populate spinner with list of stakeholders
                    List<cKeyPairBoolData> stakeholders = new ArrayList<>();
                    for (int i = 0; i < stakeholderModels.size(); i++) {
                        cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                        idNameBool.setId(i);
                        idNameBool.setRefStringID(stakeholderModels.get(i).getOrganizationServerID());
                        idNameBool.setName(stakeholderModels.get(i).getName());
                        idNameBool.setSelected(organizationID.equals(
                                stakeholderModels.get(i).getOrganizationServerID()));
                        stakeholders.add(idNameBool);
                    }

                    // select stakeholders to change organization ownership
                    binding.appCompatSpinnerOrganization.setItem(stakeholders, -1, item -> {
                        recordPermissionModel.setOrganizationOwnerID(item.getRefStringID());
                        recordPermissionModel.setModifiedDate(new Date());

                        binding.appCompatTextViewOrganization.setText(item.getName());
                        if (!item.isSelected()) {
                            item.setSelected(true);
                        }

                        enableButtons();
                    });

                    break;

                case "TEAMS":
                    // assign team label
                    List<cWorkspaceModel> teamModels;
                    teamModels = (List<cWorkspaceModel>) object;
                    String teamID = String.valueOf(recordPermissionModel.getTeamOwnerBIT());
                    binding.appCompatTextViewTeam.setText(getTeamName(teamID, teamModels));

                    // populate spinner with list of stakeholders
                    List<cKeyPairBoolData> teams = new ArrayList<>();
                    for (int i = 0; i < teamModels.size(); i++) {
                        cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                        idNameBool.setId(i);
                        idNameBool.setRefStringID(teamModels.get(i).getWorkspaceServerID());
                        idNameBool.setName(teamModels.get(i).getName());
                        idNameBool.setSelected(teamID.equals(teamModels.get(i).getWorkspaceServerID()));
                        teams.add(idNameBool);
                    }

                    // select stakeholders to change organization ownership
                    binding.appCompatSpinnerTeam.setItem(teams, -1, item -> {
                        recordPermissionModel.setTeamOwnerBIT(Integer.parseInt(item.getRefStringID()));
                        recordPermissionModel.setModifiedDate(new Date());

                        binding.appCompatTextViewTeam.setText(item.getName());

                        if (!item.isSelected()) {
                            item.setSelected(true);
                        }

                        enableButtons();
                    });

                    break;

                case "USERS":
                    // assign user profile label
                    List<CUserProfileModel> userProfileModels;
                    userProfileModels = (List<CUserProfileModel>) object;
                    String userID = String.valueOf(recordPermissionModel.getUserOwnerID());
                    Pair<String, String> pair = getOwnerName(userID, userProfileModels);
                    assert pair != null;
                    StringBuilder full_name;
                    full_name = new StringBuilder(pair.first).append(" ").append(pair.second);
                    binding.appCompatTextViewOwner.setText(full_name);

                    // populate spinner with list of user profiles
                    List<cKeyPairBoolData> user_profiles = new ArrayList<>();
                    for (int i = 0; i < userProfileModels.size(); i++) {
                        cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                        idNameBool.setId(i);
                        idNameBool.setRefStringID(userProfileModels.get(i).getUserServerID());
                        full_name = new StringBuilder(userProfileModels.get(i).getName())
                                .append(" ").append(userProfileModels.get(i).getSurname());
                        idNameBool.setName(full_name.toString());
                        idNameBool.setSelected(userID.equals(
                                userProfileModels.get(i).getUserServerID()));
                        user_profiles.add(idNameBool);
                    }

                    // select stakeholders to change ownership
                    binding.appCompatSpinnerOwner.setItem(user_profiles, -1, item -> {
                        recordPermissionModel.setUserOwnerID(item.getRefStringID());
                        recordPermissionModel.setModifiedDate(new Date());

                        binding.appCompatTextViewOwner.setText(item.getName());

                        if (!item.isSelected()) {
                            item.setSelected(true);
                        }

                        enableButtons();
                    });

                    break;

                default:
                    break;
            }

            // initially enable spinners after initialisation.
            binding.appCompatSpinnerOwner.setEnabled(true);
            binding.appCompatSpinnerTeam.setEnabled(true);
            binding.appCompatSpinnerOrganization.setEnabled(true);
        }
    }

    private void enableButtons() {
        binding.buttonReset.setEnabled(true);
        binding.buttonReset.setTextColor(
                requireContext().getColor(android.R.color.white));
        binding.buttonUpdate.setEnabled(true);
        binding.buttonUpdate.setTextColor(
                requireContext().getColor(android.R.color.white));
    }

    private void disableButtons() {
        binding.buttonReset.setEnabled(false);
        binding.buttonReset.setTextColor(
                requireContext().getColor(android.R.color.darker_gray));
        binding.buttonUpdate.setEnabled(false);
        binding.buttonUpdate.setTextColor(
                requireContext().getColor(android.R.color.darker_gray));
    }

    private void clearPermissions() {
        binding.checkBoxOwnerRead.setChecked(false);
        binding.checkBoxOwnerUpdate.setChecked(false);
        binding.checkBoxOwnerDelete.setChecked(false);

        binding.checkBoxPrimaryRead.setChecked(false);
        binding.checkBoxPrimaryUpdate.setChecked(false);
        binding.checkBoxPrimaryDelete.setChecked(false);

        binding.checkBoxSecondaryRead.setChecked(false);
        binding.checkBoxSecondaryUpdate.setChecked(false);
        binding.checkBoxSecondaryDelete.setChecked(false);

        binding.checkBoxOrganizationRead.setChecked(false);
        binding.checkBoxOrganizationUpdate.setChecked(false);
        binding.checkBoxOrganizationDelete.setChecked(false);
    }

    @Override
    public void onReadRecordPermissionsFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateRecordPermissionsSucceeded(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onUpdateRecordPermissionsFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

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
}