package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.me.mseotsanyana.mande.BLL.interactors.session.status.Impl.cStatusInteractorImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cUserAdapter;
import com.me.mseotsanyana.mande.UTIL.INTERFACE.iMEEntityInterface;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.UTIL.cParam;

import java.util.ArrayList;

/**
 * Created by mseotsanyana on 2018/01/22.
 */

public class cUserFragment extends Fragment implements iMEEntityInterface {
    private static String TAG = cUserFragment.class.getSimpleName();

    private ArrayList<cUserModel> listUsers = new ArrayList<>();
    private ArrayList<cStatusModel> statusDomains = new ArrayList<cStatusModel>();

    //private cSessionManager session;

    //set the fragment as a listener to adapter
    private cUserModel userDomain;

    //private cUserHandler userHandler;
    private cStatusInteractorImpl statusHandler;

    private cUserAdapter userAdapter;

    private Toolbar toolBar;
    private Menu toolBarMenu;
    private MenuItem toolBarMenuItem;

    private RecyclerView recyclerView;

    public static cUserFragment newInstance() {
        cUserFragment fragment = new cUserFragment();
        return fragment;
    }

    /*
     * this event fires 1st, before creation of fragment or any views
     * the onAttach method is called when the Fragment instance is
     * associated with an Activity and this does not mean the activity
     * is fully initialized.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * this method is fired 2nd, before views are created for the fragment,
     * the onCreate method is called when the fragment instance is being created,
     * or re-created use onCreate for any standard setup that does not require
     * the activity to be fully created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //this.session = new cSessionManager(getContext());

        this.userDomain = new cUserModel();

        //this.userHandler = new cUserHandler(getActivity(),  this);
        this.statusHandler = null;//new cStatusInteractorImpl(getActivity());

        this.userAdapter = new cUserAdapter(getActivity(),
                listUsers, statusDomains, this);
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_list_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // instantiate and initialize the action_list
        recyclerView = (RecyclerView) view.findViewById(R.id.user_list_cardview_id);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        // populate the action_list with data from database
        recyclerView.setAdapter(userAdapter);

        recyclerView.setLayoutManager(llm);

        // populate user action_list from database
        /*getUserList(session.loadUserID(),    /* loggedIn user id
                session.loadOrgID(),
                session.loadPrimaryRole(),   /* primary group bit
                session.loadSecondaryRoles() /* secondary group bits
        );*/

        // initialise the floating action button (FAB)
        initFab(view);

        // initialize the toolbar
        toolBar = (Toolbar) view.findViewById(R.id.me_toolbar);
        toolBar.setTitle(R.string.user_list_title);
        toolBar.setTitleTextColor(Color.WHITE);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolBar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /** create user entity **/
    private void initFab(View view) {
        view.findViewById(R.id.user_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushFragment(cUserAddFragment.newInstance(cUserFragment.this));
            }
        });
    }

    /** update user entity **/
    @Override
    public void onUpdateEntity(final int position){
        userDomain = userAdapter.getItem(position);
        pushFragment(cUserEditFragment.newInstance(userDomain, this));
    }

    /** read user entity **/
    private void getUserList(int userID,        /* loggedIn user id */
                             int orgID,
                             int primaryRole,   /* primary group bit */
                             int secondaryRoles /* secondary group bits */) {

        cParam param = new cParam(userID, orgID, primaryRole, secondaryRoles);

        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<cParam, Void, Void>() {
            @Override
            protected Void doInBackground(cParam... param) {
//                listUsers.clear();
//                listUsers.addAll(userHandler.getUserList(param[0].getUserID(),
//                        param[0].getOrgID(), param[0].getPrimaryRole(),
//                        param[0].getSecondaryRoles()));
//
//                statusDomains.clear();
//                //statusDomains.addAll(statusHandler.getStatusList());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                userAdapter.notifyDataSetChanged();
            }
        }.execute(param);
    }

    @Override
    public void onResponseMessage(final String title, final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                // setting dialog title
                alertDialog.setTitle(title);

                // setting dialog message
                alertDialog.setMessage(message);

                // setting icon to dialog
                TextDrawable faIcon = new TextDrawable(getContext());
                faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                faIcon.setTypeface(cFontManager.getTypeface(getContext(), cFontManager.FONTAWESOME));
                faIcon.setText(getContext().getResources().getText(R.string.fa_exclamation_triangle));
                faIcon.setTextColor(Color.BLUE);

                alertDialog.setIcon(faIcon);

                // setting OK button
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // go to main menu
                        pushFragment(cUserFragment.this);
                        dialog.cancel();
                    }
                });

                // modal mode
                alertDialog.setCancelable(false);

                // showing alert message
                alertDialog.show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        toolBar.inflateMenu(R.menu.menu_main);
        toolBarMenu = toolBar.getMenu();

        toolBarMenuItem = toolBarMenu.findItem(R.id.homeItem);

        toolBar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) toolBarMenu.findItem(R.id.searchItem).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeItem:
                //pushFragment(cLogFrameFragment.newInstance(session));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                userAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    protected void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (ft != null) {
            ft.replace(R.id.fragment_frame, fragment);
            ft.commit();
        }
    }
}
