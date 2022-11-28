package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.me.mseotsanyana.bmblibrary.BoomButtons.cHamButton;
import com.me.mseotsanyana.bmblibrary.CBoomMenuButton;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.entities.models.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.programme.cLogFrameFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.common.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cDashboardPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iDashboardPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.CSingleSpinnerSearch;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

public class cDashboardFragment extends Fragment implements iDashboardPresenter.View {
    //private static final String TAG = cDashboardFragment.class.getSimpleName();

    //ME
    private final String[] me_normal_caption = {
            "Monitoring Plans",
            "Evaluation Plans",
            "Learning Plans"
    };

    private final String[] me_sub_caption = {
            "Creating questionnaires for collecting data on indicators.",
            "Creating questionnaires for evaluation purposes.",
            "Creating groupings of M&E results to document findings and recommendations."
    };

    private final int[] me_bmb_imageid = {
            R.drawable.butterfly,
            R.drawable.butterfly,
            R.drawable.butterfly
    };

    //AWPB
    private final String[] awpb_normal_caption = {
            "Work Plans",
            "Budget Plans"
    };

    private final String[] awpb_sub_caption = {
            "Creating resource allocations to activities and tasks.",
            "Creating fund allocations to activities and tasks."
    };

    private final int[] awpb_bmb_imageid = {
            R.drawable.eagle,
            R.drawable.eagle
    };

    //Data Collection
    private final String[] data_normal_caption = {
            "Update indicators",
            "Update evaluations",
            "Update findings"
    };

    private final String[] data_sub_caption = {
            "Creating questionnaires for collecting data on indicators.",
            "Creating questionnaires for evaluation purposes.",
            "Creating groupings of M&E results to document findings and recommendations."
    };

    private final int[] data_bmb_imageid = {
            R.drawable.butterfly,
            R.drawable.butterfly,
            R.drawable.butterfly
    };

    //RAID
    private final String[] raid_normal_caption = {
            "Risk Registers",
            "Assumption Registers",
            "Issue Registers",
            "Dependency Registers"
    };

    private final String[] raid_sub_caption = {
            "Creating and updating project risk register.",
            "Creating and updating project assumption register.",
            "Creating and updating project issue register.",
            "Creating and updating project dependency register."
    };

    private final int[] raid_bmb_imageid = {
            R.drawable.butterfly,
            R.drawable.butterfly,
            R.drawable.butterfly,
            R.drawable.butterfly
    };

    // dashboard enum menu
    private enum DashboardMenu {
        MONITORING_PLAN(1),
        EVALUATION_PLAN(2),
        LEARNING_PLAN(3),
        WORK_PLAN(4),
        BUDGET_PLAN(5),
        UPDATE_INDICATOR(6),
        UPDATE_EVALUATION(7),
        UPDATE_FINDING(8),
        RISK_REGISTER(9),
        ASSUMPTION_REGISTER(10),
        ISSUE_REGISTER(11),
        DEPENDENCY_REGISTER(12),
        TIMESHEET(13),
        TRANSACTION(14);

        private final int menuItem;

        DashboardMenu(int menuItem) {
            this.menuItem = menuItem;
        }

        public int getMenuItem() {
            return this.menuItem;
        }
    }

    private DashboardMenu dashboardMenu;

    /* dashboard interface */
    private iDashboardPresenter dashboardPresenter;


    /* logframe views */
    private LinearLayout includeProgressBar;

    // required empty public constructor
    public cDashboardFragment() {
    }

    public static cDashboardFragment newInstance() {
        return new cDashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.session_dashboard_fragment, container, false);
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

        /* create toolbar and progressbar */
        initViews(view);
    }

    private void initDataStructures() {
        /* instantiate presenters */
        dashboardPresenter = new cDashboardPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cLogFrameFirestoreRepositoryImpl(getContext()));
    }

    private void initViews(View view) {
        /* initialise progress bar */
        includeProgressBar = view.findViewById(R.id.includeProgressBar);

        // 1. Programme or projects portfolio menu item
        ImageView imageViewProjects = view.findViewById(R.id.imageViewProjects);
        imageViewProjects.setOnClickListener(clickView -> {
            NavDirections action;
            action = cHomeFragmentDirections.actionCHomeFragmentToCProjectFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });

//        ImageView imageViewOrganizations = view.findViewById(R.id.imageViewStakeholders);
//        imageViewOrganizations.setOnClickListener(clickView -> {
//            NavDirections action;
//            action = cHomeFragmentDirections.actionCHomeFragmentToCOrganizationDetailFragment();
//            Navigation.findNavController(requireView()).navigate(action);
//        });

        // 2. Logframe menu item
        ImageView imageViewLogframes = view.findViewById(R.id.imageViewLogframes);
        imageViewLogframes.setOnClickListener(clickView -> {
            NavDirections action;
            action = cHomeFragmentDirections.actionCHomeFragmentToCLogFrameFragment(null);
            Navigation.findNavController(requireView()).navigate(action);
        });

        /* monitoring and evaluation menu */
        CBoomMenuButton bmbMenuMEL = view.findViewById(R.id.bmbMenuMEL);
        bmbMenuMEL.clearBuilders();
        for (int i = 0; i < bmbMenuMEL.getPiecePlaceEnum().pieceNumber(); i++) {
            cHamButton.Builder builder = new cHamButton.Builder()
                    .normalImageRes(me_bmb_imageid[i])
                    .normalText(me_normal_caption[i])
                    .subNormalText(me_sub_caption[i])
                    .listener(index -> {
                        switch (index) {
                            case 0:
                                dashboardMenu = DashboardMenu.MONITORING_PLAN;
                                dashboardPresenter.readLogFrames();
                                break;
                            case 1:
                                dashboardMenu = DashboardMenu.EVALUATION_PLAN;
                                dashboardPresenter.readLogFrames();
                                break;
                            case 2:
                                dashboardMenu = DashboardMenu.LEARNING_PLAN;
                                dashboardPresenter.readLogFrames();
                                break;
                        }
                    });
            bmbMenuMEL.addBuilder(builder);
        }
        ImageView imageViewMEL = view.findViewById(R.id.imageViewMEL);
        imageViewMEL.setOnClickListener(boomView -> bmbMenuMEL.boom());

        /* annual work plan and budget menu */
        CBoomMenuButton bmbMenuAWPB = view.findViewById(R.id.bmbMenuAWPB);
        bmbMenuAWPB.clearBuilders();
        for (int i = 0; i < bmbMenuAWPB.getPiecePlaceEnum().pieceNumber(); i++) {
            cHamButton.Builder builder = new cHamButton.Builder()
                    .normalImageRes(awpb_bmb_imageid[i])
                    .normalText(awpb_normal_caption[i])
                    .subNormalText(awpb_sub_caption[i])
                    .listener(index -> {
                        switch (index) {
                            case 0:
                                dashboardMenu = DashboardMenu.WORK_PLAN;
                                dashboardPresenter.readLogFrames();
                                break;
                            case 1:
                                dashboardMenu = DashboardMenu.BUDGET_PLAN;
                                dashboardPresenter.readLogFrames();
                                break;
                        }
                    });
            bmbMenuAWPB.addBuilder(builder);
        }
        ImageView imageViewAWPB = view.findViewById(R.id.imageViewAWPB);
        imageViewAWPB.setOnClickListener(boomView -> bmbMenuAWPB.boom());

        /* data collection of monitoring, evaluation and learning menu */
        CBoomMenuButton bmbMenuDataCollection = view.findViewById(R.id.bmbMenuDataCollection);
        bmbMenuDataCollection.clearBuilders();
        for (int i = 0; i < bmbMenuDataCollection.getPiecePlaceEnum().pieceNumber(); i++) {
            cHamButton.Builder builder = new cHamButton.Builder()
                    .normalImageRes(data_bmb_imageid[i])
                    .normalText(data_normal_caption[i])
                    .subNormalText(data_sub_caption[i])
                    .listener(index -> {
                        switch (index) {
                            case 0:
                                dashboardMenu = DashboardMenu.UPDATE_INDICATOR;
                                dashboardPresenter.readLogFrames();
                                break;
                            case 1:
                                dashboardMenu = DashboardMenu.UPDATE_EVALUATION;
                                dashboardPresenter.readLogFrames();
                                break;
                            case 2:
                                dashboardMenu = DashboardMenu.UPDATE_FINDING;
                                dashboardPresenter.readLogFrames();
                                break;
                        }
                    });
            bmbMenuDataCollection.addBuilder(builder);
        }
        ImageView imageViewDataCollection = view.findViewById(R.id.imageViewDataCollection);
        imageViewDataCollection.setOnClickListener(boomView -> bmbMenuDataCollection.boom());

        /* Risk, Assumption, Issue and Dependency registers */
        CBoomMenuButton bmbMenuRAID = view.findViewById(R.id.bmbMenuRAID);
        bmbMenuRAID.clearBuilders();
        for (int i = 0; i < bmbMenuRAID.getPiecePlaceEnum().pieceNumber(); i++) {
            cHamButton.Builder builder = new cHamButton.Builder()
                    .normalImageRes(raid_bmb_imageid[i])
                    .normalText(raid_normal_caption[i])
                    .subNormalText(raid_sub_caption[i])
                    .listener(index -> {
                        switch (index) {
                            case 0:
                                dashboardMenu = DashboardMenu.RISK_REGISTER;
                                dashboardPresenter.readLogFrames();
                                break;
                            case 1:
                                dashboardMenu = DashboardMenu.ASSUMPTION_REGISTER;
                                dashboardPresenter.readLogFrames();
                                break;
                            case 2:
                                dashboardMenu = DashboardMenu.ISSUE_REGISTER;
                                dashboardPresenter.readLogFrames();
                                break;
                            case 3:
                                dashboardMenu = DashboardMenu.DEPENDENCY_REGISTER;
                                dashboardPresenter.readLogFrames();
                                break;
                        }
                    });
            bmbMenuRAID.addBuilder(builder);
        }
        ImageView imageViewRAID = view.findViewById(R.id.imageViewRAID);
        imageViewRAID.setOnClickListener(boomView -> bmbMenuRAID.boom());

        ImageView imageViewTimesheet = view.findViewById(R.id.imageViewTimesheet);
        imageViewTimesheet.setOnClickListener(clickView -> {
            dashboardMenu = DashboardMenu.TIMESHEET;
            dashboardPresenter.readLogFrames();
        });

        ImageView imageViewTransactions = view.findViewById(R.id.imageViewTransactions);
        imageViewTransactions.setOnClickListener(clickView -> {
            dashboardMenu = DashboardMenu.TRANSACTION;
            dashboardPresenter.readLogFrames();
        });

    }

    @Override
    public void onReadLogFramesSucceeded(List<cTreeModel> treeModels) {
        filterLogframes(treeModels);
    }

    @Override
    public void onReadLogFramesFailed(String msg) {
    }

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

    }

    //FIXME: if statement with three options: when treeModels.size() == 0, 1, or > 1
    private void filterLogframes(@NonNull List<cTreeModel> treeModels) {
        // 1. create a spinner for filtering purposes spinner
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.me_logframe_filter, null);

        // 2. create a list of logframes ids and names
        final List<cKeyPairBoolData> keyPair = new ArrayList<>();
        for (int i = 0; i < treeModels.size(); i++) {
            cLogFrameModel logFrameModel = (cLogFrameModel) treeModels.get(i).getModelObject();
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();

            idNameBool.setId(i);
            idNameBool.setRefStringID(logFrameModel.getProjectServerID());
            idNameBool.setName(logFrameModel.getName());
            idNameBool.setSelected(false);
            keyPair.add(idNameBool);
        }

        // 3. -1 is no by default selection 0 to length will select corresponding values
        CSingleSpinnerSearch singleSpinnerSearch =
                (CSingleSpinnerSearch) view.findViewById(R.id.logframeSingleSpinnerSearch);
        singleSpinnerSearch.setItem(keyPair, -1, selectedItem -> {

            switch (dashboardMenu) {
                case MONITORING_PLAN:
                    Toast.makeText(getContext(), "MONITORING PLAN ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case EVALUATION_PLAN:
                    Toast.makeText(getContext(), "EVALUATION PLAN ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case LEARNING_PLAN:
                    Toast.makeText(getContext(), "LEARNING PLAN ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case WORK_PLAN:
                    Toast.makeText(getContext(), "WORK PLAN ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case BUDGET_PLAN:
                    Toast.makeText(getContext(), "BUDGET PLAN ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case UPDATE_INDICATOR:
                    Toast.makeText(getContext(), "UPDATE INDICATOR ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case UPDATE_EVALUATION:
                    Toast.makeText(getContext(), "UPDATE EVALUATION ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case UPDATE_FINDING:
                    Toast.makeText(getContext(), "UPDATE FINDING ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case RISK_REGISTER:
                    Toast.makeText(getContext(), "RISK REGISTER ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case ASSUMPTION_REGISTER:
                    Toast.makeText(getContext(), "ASSUMPTION REGISTER ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case ISSUE_REGISTER:
                    Toast.makeText(getContext(), "ISSUE REGISTER ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case DEPENDENCY_REGISTER:
                    Toast.makeText(getContext(), "DEPENDENCY REGISTER ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case TIMESHEET:
                    Toast.makeText(getContext(), "TIMESHEET ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case TRANSACTION:
                    Toast.makeText(getContext(), "TRANSACTION ==>> " +
                            selectedItem.getRefStringID() + " -> " +
                            selectedItem.getName(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        // call pop up window for list of logframes
        singleSpinnerSearch.performClick();
    }
}