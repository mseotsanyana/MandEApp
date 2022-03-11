package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.logframe.cProjectModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.logframe.cProjectFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.Impl.cProjectPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iProjectPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cProjectAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.mande.databinding.ProjectListFragmentBinding;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/11/02.
 */
public class cProjectFragment extends Fragment implements iProjectPresenter.View {
    private static final String TAG = cProjectFragment.class.getSimpleName();
    private static final SimpleDateFormat tsdf = cConstant.TIMESTAMP_FORMAT_DATE;
    private static final SimpleDateFormat ssdf = cConstant.SHORT_FORMAT_DATE;

    /* project views */
    private LinearLayout includeProgressBar;

    /* project interface */
    private iProjectPresenter projectPresenter;

    /* project adapter */
    private cProjectAdapter projectAdapter;

    private ActivityResultLauncher<Intent> launchUploadActivity;

    private AppCompatActivity activity;


    private ProjectListFragmentBinding binding;

    public cProjectFragment() {
    }

    public static cProjectFragment newInstance() {
        return new cProjectFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Create launcher variable inside onAttach or onCreate or global
//        launchUploadActivity = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(), result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//
//
//
//                        Intent resultDate = result.getData();
//                        Uri fileUri = Objects.requireNonNull(resultDate).getData();
//                        String filePath = cPresenterUtils.getPath(getContext(), fileUri);
//
//                        Log.d(TAG, "======================= " + filePath);
//                        logFramePresenter.uploadLogFrameFromExcel(filePath);
//                    }
//                });
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all logframes from the database*/
        projectPresenter.resume();
        //logFramePresenter.readSharedOrganizations();
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.logframe_list_fragment, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.project_list_fragment, container,
                false);
        return binding.getRoot();
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* create data structures */
        initDataStructures();

        /* create related views */
        initProjectView(view);

        /* draggable floating button */
        initDraggableFAB(view);
    }

    private void initDataStructures() {
        /* instantiate presenters */
        projectPresenter = new cProjectPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cProjectFirestoreRepositoryImpl(getContext()));
    }

    private void initDraggableFAB(View view) {
        binding.projectFAB.setOnClickListener(v -> {
            cProjectModel projectModel = new cProjectModel();
            onClickCreateProject(projectModel);
        });
    }

    private void initProjectView(View view) {
        /* initialise progress bar */
        includeProgressBar = view.findViewById(R.id.includeProgressBar);

        /* show the back arrow button */
        activity = ((AppCompatActivity) getActivity());
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);

        // set the title
        CollapsingToolbarLayout collapsingToolbarLayout;
        collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(
                requireContext().getResources().getText(R.string.project_list_title));
    }

    private void setAdapter(List<cTreeModel> projectTreeModels) {
        //List<cTreeModel> logFrameTreeModels = new ArrayList<>();
        if (projectAdapter == null) {
            projectAdapter = new cProjectAdapter(getActivity(), projectTreeModels, -1);
            binding.projectRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(requireActivity());
            binding.projectRecyclerView.setLayoutManager(mLayoutManager);
            binding.projectRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.projectRecyclerView.setAdapter(projectAdapter);
        } else {
            projectAdapter = new cProjectAdapter(getActivity(), projectTreeModels, -1);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getActivity().getMenuInflater().inflate(R.menu.drawer_menu_main, menu);
        inflater.inflate(R.menu.home_toolbar_menu, menu);

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
                projectAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteItem:
                //List<String> selected_projects = new ArrayList<>();
                //List<String> selected_components = new ArrayList<>();
                //selected_projects = projectAdapter.onGetprojectServerIDs();
                //selected_components = projectAdapter.onGetComponentServerIDs();
                projectPresenter.deleteProjectModels();
                return true;

            case R.id.uploadItem:
                projectPresenter.uploadProjectFromExcel(null);
//                launchUploadExcelFile.launch(
//                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                return true;

            case R.id.downloadItem:
                // do something
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    //=========== these functions are called in the adapter when clicking on the buttons ===========

    @Override
    public void onClickProject(cProjectModel projectModel) {

    }

    @Override
    public void onClickCreateProject(cProjectModel projectModel) {
        createAlertDialog(null, projectModel, true);

    }

    @Override
    public void onClickCreateSubProject(String projectID, cProjectModel projectModel) {
        createAlertDialog(projectID, projectModel, false);

    }

    @Override
    public void onClickUpdateProject(int position, cProjectModel projectModel) {
        updateAlertDialog(position, projectModel);
    }

    @Override
    public void onClickDeleteProject(String projectID) {
        int resID = R.string.fa_delete;
        String title = "Delete project.";
        String message = "Are you sure you want to delete this project ?";

        deleteAlertDialog(resID, title, message, projectID);
    }

    @Override
    public void onClickDeleteProjects(List<String> project_ids) {

    }

    @Override
    public void onClickUploadProject(File excelFile) {

    }

    @Override
    public void onCreateProjectCompleted(cProjectModel projectModel, String msg) {
        try {
            int parentIndex = projectAdapter.getMaxParentIndex();
            cTreeModel treeModel = new cTreeModel(parentIndex, -1, 0, projectModel);
            projectAdapter.addData(treeModel);
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateProjectFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReadProjectsCompleted(List<cTreeModel> treeModels) {
        setAdapter(treeModels);
    }

    @Override
    public void onReadProjectsFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProjectUpdated(cProjectModel projectModel, int position, String msg) {
        cTreeModel treeModel = new cTreeModel(projectModel);
        projectAdapter.notifyTreeModelUpdated(treeModel, position);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProjectUpdateFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteProjectsSucceeded(String msg) {

    }

    @Override
    public void onDeleteProjectsFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadProjectCompleted(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

        // reload projects
        projectPresenter.readProjects();
    }

    @Override
    public void onUploadProjectFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //========================== these functions give feedback to the user =========================


    //========================= these functions show and hide progress bar =========================

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
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    //======================= these function show the forms to capture data ========================

    /**
     * create/add a project
     *
     * @param projectID    project identification
     * @param projectModel project model
     * @param isProject    is this a parent or child project
     */
    private void createAlertDialog(String projectID, cProjectModel projectModel,
                                   boolean isProject) {
        /* inflate the Project model */
        LayoutInflater inflater = this.getLayoutInflater();
        View createView = inflater.inflate(R.layout.project_update, null);
        /* instantiates create views */
        TextView textViewTitle = createView.findViewById(R.id.textViewTitle);
        TextView textViewOrganization = createView.findViewById(R.id.textViewOrganization);
        cSingleSpinnerSearch singleSpinnerSearchOrg = createView.findViewById(R.id.singleSpinnerSearchOrg);
        AppCompatEditText editTextName = createView.findViewById(R.id.editTextName);
        AppCompatEditText editTextDescription = createView.findViewById(R.id.editTextDescription);
        AppCompatEditText startDateEditText = createView.findViewById(R.id.editTextStartDate);
        AppCompatEditText endDateEditText = createView.findViewById(R.id.editTextEndDate);
        TextView datePickerIcon = createView.findViewById(R.id.textViewDatePicker);

        /* set a title of the create view */
        textViewTitle.setText(requireContext().getResources().getText(
                R.string.project_create_title));

        /* populate the logical model with the create views */
        /* 1. create selection dialog box for organizations */
        List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
//        for (int i = 0; i < sharedOrganizations.size(); i++) {
//            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
//            //--idNameBool.setId(sharedOrganizations.get(i).getOrganizationID());
//            idNameBool.setName(sharedOrganizations.get(i).getName());
//            idNameBool.setObject(sharedOrganizations.get(i));
//            idNameBool.setSelected(false);
//
//            keyPairBoolOrgs.add(idNameBool);
//        }

        // called when click spinner
        singleSpinnerSearchOrg.setItem(keyPairBoolOrgs, -1, item -> {
            /* assign selected organization name to the view */
            textViewOrganization.setText(item.getName());
            /* modify the organization ID for the Project model */

            //ProjectModel.setOrganizationID((int) item.getId());
            //ProjectModel.getOrganizationModel().setName(item.getName());
        });

        /* 4. set start and end date */
        datePickerIcon.setTypeface(null, Typeface.NORMAL);
        datePickerIcon.setTypeface(cFontManager.getTypeface(requireActivity(),
                cFontManager.FONTAWESOME));
        datePickerIcon.setTextColor(requireActivity().getColor(R.color.colorPrimaryDark));
        datePickerIcon.setText(requireActivity().getResources().getString(R.string.fa_calendar));
        datePickerIcon.setOnClickListener(view -> {
            // date range picker
            MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.
                    dateRangePicker();
            MaterialDatePicker<Pair<Long, Long>> picker = builder.build();

            picker.addOnCancelListener(dialogInterface ->
                    Log.d(TAG, "DatePicker Activity Dialog was cancelled"));

            picker.addOnPositiveButtonClickListener(
                    selection -> {
                        /* get start and end date from picker */
                        assert selection.first != null;
                        assert selection.second != null;
                        Date startDate = new Date(selection.first);
                        Date endDate = new Date(selection.second);
                        /* populate start and end dates on the view */
                        startDateEditText.setText(ssdf.format(startDate));
                        endDateEditText.setText(ssdf.format(endDate));

                        /* modify start and end dates of Project model */
                        projectModel.setStartDate(Timestamp.valueOf(tsdf.format(startDate)));
                        projectModel.setEndDate(Timestamp.valueOf(tsdf.format(endDate)));

                        Log.d(TAG, "DatePicker Activity Date String =" +
                                picker.getHeaderText() + " Date epoch values :: " +
                                selection.first + " :: to :: " + selection.second);
                    });

            picker.addOnNegativeButtonClickListener(view1 ->
                    Log.d(TAG, "DatePicker Activity Dialog Negative Button was clicked"));

            picker.show(getParentFragmentManager(), picker.toString());
        });

        /* create or cancel action */
        MaterialAlertDialogBuilder alertDialogBuilder =
                new MaterialAlertDialogBuilder(requireActivity(), R.style.AlertDialogTheme);
        alertDialogBuilder.setPositiveButton(requireContext().getResources().getText(
                R.string.Save), (dialogInterface, i) -> {
            /* 2. set name of the Project */
            projectModel.setName(Objects.requireNonNull(editTextName.getText()).toString());
            /* 3. set description of the Project */
            projectModel.setDescription(Objects.requireNonNull(editTextDescription.getText()).
                    toString());

            if (isProject) {
                projectPresenter.createProjectModel(projectModel);
            } else {
                projectPresenter.createSubProjectModel(projectID, projectModel);
            }
        });

        alertDialogBuilder.setNegativeButton(requireContext().getResources().getText(
                R.string.Cancel), (dialogInterface, i) -> {

        })
                .setView(createView)
                .show();
    }

    /**
     * update a Project
     *
     * @param position     position
     * @param projectModel Project model
     */
    private void updateAlertDialog(int position, cProjectModel projectModel) {
        /* inflates */
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.project_update, null);

        /* instantiate update views */
        TextView textViewTitle = dialogView.findViewById(R.id.textViewTitle);
        TextView textViewOrganization = dialogView.findViewById(R.id.textViewOrganization);
        cSingleSpinnerSearch singleSpinnerSearchOrg = dialogView.findViewById(
                R.id.singleSpinnerSearchOrg);
        AppCompatEditText editTextName = dialogView.findViewById(R.id.editTextName);
        AppCompatEditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
        AppCompatEditText startDateEditText = dialogView.findViewById(R.id.editTextStartDate);
        AppCompatEditText endDateEditText = dialogView.findViewById(R.id.editTextEndDate);
        TextView datePickerIcon = dialogView.findViewById(R.id.textViewDatePicker);

        /* populate the fields to be updated */
        textViewTitle.setText(requireContext().getResources().getText(
                R.string.project_update_title));
        //textViewOrganization.setText(ProjectModel.getOrganizationModel().getName());
        editTextName.setText(projectModel.getName());
        editTextDescription.setText(projectModel.getDescription());
        startDateEditText.setText(ssdf.format(projectModel.getStartDate()));
        endDateEditText.setText(ssdf.format(projectModel.getEndDate()));

        /* 1. set own organization */
        List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
//        for (int i = 0; i < sharedOrganizations.size(); i++) {
//            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
//            //--idNameBool.setId(sharedOrganizations.get(i).getOrganizationID());
//            idNameBool.setName(sharedOrganizations.get(i).getName());
//            idNameBool.setObject(sharedOrganizations.get(i));
//
//            /* get the current organization ID */
//            long organizationID = 0;//ProjectModel.getOrganizationModel().getOrganizationID();
//
//            if (true/*(sharedOrganizations.get(i).getOrganizationID() == organizationID)*/) {
//                idNameBool.setSelected(true);
//                /* initialize organization ID for the Project model */
//                ProjectModel.setOrganizationID(organizationID);
//            } else {
//                idNameBool.setSelected(false);
//            }
//
//            keyPairBoolOrgs.add(idNameBool);
//        }

        // called when click spinner
        singleSpinnerSearchOrg.setItem(keyPairBoolOrgs, -1, item -> {
            /* assign selected organization name to the view */
            textViewOrganization.setText(item.getName());
            /* modify the organization ID for the Project model */
            //ProjectModel.setOrganizationID((int) item.getId());
            //ProjectModel.getOrganizationModel().setName(item.getName());
        });

        /* 3. set start and end dates */
        datePickerIcon.setTypeface(null, Typeface.NORMAL);
        datePickerIcon.setTypeface(cFontManager.getTypeface(requireActivity(),
                cFontManager.FONTAWESOME));
        datePickerIcon.setTextColor(requireActivity().getColor(R.color.colorPrimaryDark));
        datePickerIcon.setText(requireActivity().getResources().getString(R.string.fa_calendar));
        datePickerIcon.setOnClickListener(view -> {
            // date range picker
            MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.
                    dateRangePicker();
            MaterialDatePicker<Pair<Long, Long>> picker = builder.build();

            picker.addOnCancelListener(dialogInterface ->
                    Log.d(TAG, "DatePicker Activity Dialog was cancelled"));

            picker.addOnPositiveButtonClickListener(
                    selection -> {
                        /* get start and end date from picker */
                        assert selection.first != null;
                        assert selection.second != null;
                        Date startDate = new Date(selection.first);
                        Date endDate = new Date(selection.second);

                        /* populate start and end dates on the view */
                        startDateEditText.setText(ssdf.format(startDate));
                        endDateEditText.setText(ssdf.format(endDate));

                        /* modify start and end dates of Project model */
                        projectModel.setStartDate(Timestamp.valueOf(tsdf.format(startDate)));
                        projectModel.setEndDate(Timestamp.valueOf(tsdf.format(endDate)));

                        Log.d(TAG, "DatePicker Activity Date String =" +
                                picker.getHeaderText() + " Date epoch values :: " +
                                selection.first + " :: to :: " + selection.second);
                    });

            picker.addOnNegativeButtonClickListener(view1 ->
                    Log.d(TAG, "DatePicker Activity Dialog Negative Button was clicked"));

            picker.show(getParentFragmentManager(), picker.toString());
        });

        /* save or cancel actions */
        MaterialAlertDialogBuilder alertDialogBuilder =
                new MaterialAlertDialogBuilder(requireActivity(), R.style.AlertDialogTheme);
        alertDialogBuilder.setPositiveButton(requireContext().getResources().getText(
                R.string.Save), (dialogInterface, i) -> {
            /* 2. update the remaining view attributes to the logical model */
            projectModel.setName(Objects.requireNonNull(editTextName.getText()).toString());
            projectModel.setDescription(Objects.requireNonNull(editTextDescription.getText()).
                    toString());

            projectPresenter.updateProject(projectModel, position);

        });

        alertDialogBuilder.setNegativeButton(requireContext().getResources().getText(
                R.string.Cancel), (dialogInterface, i) -> {

        })
                .setView(dialogView)
                .show();
    }

    /**
     * delete a project
     *
     * @param resID     resource identification
     * @param title     title
     * @param message   message
     *                  //* @param position   position
     * @param projectID project identification
     */
    private void deleteAlertDialog(int resID, String title, String message, String projectID) {

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
                    projectPresenter.deleteProjectModel(projectID);
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
}