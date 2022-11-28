package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.CLOUD_CREATE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.CLOUD_DELETE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.CLOUD_READ;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.CLOUD_UPDATE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.HOUSE_CREATE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.HOUSE_DELETE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.HOUSE_READ;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.HOUSE_UPDATE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.OWNER_CREATE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.OWNER_DELETE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.OWNER_READ;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.OWNER_UPDATE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.ROOM_CREATE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.ROOM_DELETE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.ROOM_READ;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.ROOM_UPDATE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.VILLAGE_CREATE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.VILLAGE_DELETE;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.VILLAGE_READ;
import static com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference.VILLAGE_UPDATE;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cEntityModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cPrivilegeModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cSectionModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cUnixOperationCollection;
import com.me.mseotsanyana.mande.BLL.entities.models.session.cUnixOperationModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.mande.PL.presenters.session.iPermissionPresenter;
import com.me.mseotsanyana.mande.PL.ui.listeners.session.iViewPermissionListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.databinding.SessionMenuEntityCardviewBinding;
import com.me.mseotsanyana.mande.databinding.SessionPrivilegeCardviewBinding;
import com.me.mseotsanyana.mande.databinding.SessionUnixPermissionBinding;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cPrivilegeAdapter extends cTreeAdapter implements iViewPermissionListener, Filterable {
    private static final String TAG = cPrivilegeAdapter.class.getSimpleName();
//    private static final SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PRIVILEGE_LIST = 0;
    private static final int MENUITEM_SECTION = 1;
    private static final int MENUITEM_LIST = 2;
    private static final int SUB_MENUITEM_LIST = 3;
    private static final int ENTITYMODULE_SECTION = 4;
    private static final int ENTITY_LIST = 5;
    //private static final int ENTITY_OPS_SECTION   = 6;
    //private static final int ENTITY_OPS_LIST      = 7;
    //private static final int OPS_STATUS_LIST      = 8;
    //private static final int UNIX_OPS_SECTION     = 9;
    private static final int ENTITY_PERM_TABLE = 10;

    private static final String DELETED = "1";
    private static final String BLOCKED = "2";
    private static final String ACTIVATED = "4";
    private static final String CANCELLED = "8";
    private static final String PENDING = "16";

//    private static final String OWNER_READ          = "2048";
//    private static final String OWNER_UPDATE        = "1024";
//    private static final String OWNER_DELETE        = "512";
//    private static final String PRIMARY_READ        = "256";
//    private static final String PRIMARY_UPDATE      = "128";
//    private static final String PRIMARY_DELETE      = "64";
//    private static final String SECONDARY_READ      = "32";
//    private static final String SECONDARY_UPDATE    = "16";
//    private static final String SECONDARY_DELETE    = "8";
//    private static final String ORGANIZATION_READ   = "4";
//    private static final String ORGANIZATION_UPDATE = "2";
//    private static final String ORGANIZATION_DELETE = "1";

    private final Context context;
    private LayoutInflater layoutInflater;

    private List<cTreeModel> filteredModuleTree;

    private final iPermissionPresenter.View iPermissionPresenterView;


    public cPrivilegeAdapter(Context context,
                             iPermissionPresenter.View iPermissionPresenterView,
                             List<cTreeModel> moduleTree) {
        super(context, moduleTree);
        this.context = context;
        this.filteredModuleTree = moduleTree;

        this.iPermissionPresenterView = iPermissionPresenterView;
    }

    public void setModuleWithSubMenu(ArrayList<cTreeModel> moduleTree) {
        this.filteredModuleTree = moduleTree;
    }

    @Override
    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {
            case PRIVILEGE_LIST:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }

                SessionPrivilegeCardviewBinding parentBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.session_privilege_cardview, parent,
                        false);

                viewHolder = new cPrivilegeViewHolder(parentBinding, this);
                cPrivilegeViewHolder POH = (cPrivilegeViewHolder) viewHolder;

                //view = inflater.inflate(R.layout.session_privilege_cardview, parent, false);
                //viewHolder = new cPermissionViewHolder(view, this);
                break;

            case MENUITEM_SECTION:
                view = inflater.inflate(R.layout.session_section_fragment, parent,
                        false);
                viewHolder = new cMenuItemSectionViewHolder(view);
                break;

            case MENUITEM_LIST:
                view = inflater.inflate(R.layout.session_menu_entity_cardview, parent,
                        false);
                viewHolder = new cMenuItemViewHolder(view);
                break;

            case SUB_MENUITEM_LIST:
                view = inflater.inflate(R.layout.session_menu_entity_cardview, parent,
                        false);
                viewHolder = new cSubMenuItemViewHolder(view);
                break;

            case ENTITYMODULE_SECTION:
                view = inflater.inflate(R.layout.session_section_fragment, parent,
                        false);
                viewHolder = new cEntityModuleSectionViewHolder(view);
                break;

            case ENTITY_LIST:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }

                SessionMenuEntityCardviewBinding entityBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.session_menu_entity_cardview, parent,
                        false);

                viewHolder = new cEntityViewHolder(entityBinding);

                cEntityViewHolder EH = (cEntityViewHolder) viewHolder;

                // initialise organization listeners

                // set listener on detail icon
                EH.entityBinding.textViewDetailIcon.setOnClickListener(v -> {
                    int position = EH.getAbsoluteAdapterPosition();
                    expandOrCollapse(position);
                });

                //view = inflater.inflate(R.layout.session_menu_entity_cardview, parent, false);
                //viewHolder = new cEntityViewHolder(view);
                break;

//            case ENTITY_OPS_SECTION:
//                view = inflater.inflate(R.layout.session_section_fragment, parent,
//                        false);
//                viewHolder = new cEntityOperationSectionViewHolder(view);
//                break;
//
//            case ENTITY_OPS_LIST:
//                view = inflater.inflate(R.layout.session_entity_operation_cardview, parent,
//                        false);
//                viewHolder = new cEntityOperationViewHolder(view);
//                break;
//
//            case OPS_STATUS_LIST:
//                view = inflater.inflate(R.layout.session_status_cardview, parent,
//                        false);
//                viewHolder = new cOperationStatusViewHolder(view);
//                break;
//
//            case UNIX_OPS_SECTION:
//                view = inflater.inflate(R.layout.session_section_fragment, parent,
//                        false);
//                viewHolder = new cUnixOperationSectionViewHolder(view);
//                break;

            case ENTITY_PERM_TABLE:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }

                SessionUnixPermissionBinding permBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.session_unix_permission, parent,
                        false);

                viewHolder = new cUnixOperationViewHolder(permBinding);

                //view = inflater.inflate(R.layout.session_unix_permission, parent, false);
                //viewHolder = new cUnixOperationViewHolder(view);
                break;

            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        cNode node = visibleNodes.get(position);
        cTreeModel treeModel = (cTreeModel) node.getObj();

        if (treeModel != null) {
            switch (treeModel.getType()) {
                case PRIVILEGE_LIST:
                    cPrivilegeModel permissionModel = (cPrivilegeModel) treeModel.getModelObject();
                    cPrivilegeViewHolder PH = ((cPrivilegeViewHolder) viewHolder);

                    PH.setPaddingLeft(20 * node.getLevel());

                    /* permission menu */
                    PH.parentBinding.textViewName.setText(permissionModel.getName());
                    PH.parentBinding.textViewDescription.setText(permissionModel.getDescription());

                    /* the collapse and expansion of the permission */
                    if (node.isLeaf()) {
                        PH.parentBinding.textViewDetailIcon.setVisibility(View.GONE);
                    } else {
                        PH.parentBinding.textViewDetailIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            PH.parentBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            PH.parentBinding.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PH.parentBinding.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        } else {
                            PH.parentBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            PH.parentBinding.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PH.parentBinding.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        }
                    }
                    PH.parentBinding.textViewDetailIcon.setOnClickListener(v -> expandOrCollapse(position));

                    /* icon for saving updated record */
                    PH.parentBinding.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    PH.parentBinding.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PH.parentBinding.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PH.parentBinding.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    PH.parentBinding.textViewUpdateIcon.setOnClickListener(view ->
                            PH.viewPermissionListener.onClickUpdatePermission(allNodes));

                    /* icon for deleting a record */
                    PH.parentBinding.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    PH.parentBinding.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PH.parentBinding.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PH.parentBinding.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    PH.parentBinding.textViewDeleteIcon.setOnClickListener(view -> {
                        //FIXME:PVH.logFrameListener.onClickDeleteLogFrame(
                        // position,parentLogFrame.getLogFrameID());
                    });

                    break;

                case MENUITEM_SECTION:
                    cSectionModel menuitemModel = (cSectionModel) treeModel.getModelObject();
                    cMenuItemSectionViewHolder MIH = ((cMenuItemSectionViewHolder) viewHolder);

                    MIH.setPaddingLeft(20 * node.getLevel());

                    /* menu item header */
                    MIH.textViewHeading.setText(menuitemModel.getName());

                    /* the collapse and expansion of the menu items */
                    if (node.isLeaf()) {
                        MIH.toggleView.setVisibility(View.GONE);
                    } else {
                        MIH.toggleView.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            MIH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            MIH.toggleIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            MIH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        } else {
                            MIH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            MIH.toggleIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            MIH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        }
                    }
                    MIH.toggleView.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case MENUITEM_LIST:
                    cMenuModel menuModel = (cMenuModel) treeModel.getModelObject();
                    cMenuItemViewHolder MH = ((cMenuItemViewHolder) viewHolder);

                    MH.setPaddingLeft(20 * node.getLevel());

                    /* check box to select the menu items */
                    MH.checkBoxMenu.setChecked(menuModel.isChecked());
                    MH.checkBoxMenu.setOnClickListener(view -> {
                        boolean checked = ((CheckBox) view).isChecked();
                        menuModel.setChecked(checked);
                    });

                    /* menu items */
                    MH.textViewName.setText(menuModel.getName());
                    MH.textViewDescription.setText(menuModel.getDescription());

                    /* the collapse and expansion of the main menu */
                    if (node.isLeaf()) {
                        MH.textViewDetailIcon.setVisibility(View.GONE);
                    } else {

                        MH.textViewDetailIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            MH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            MH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            MH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            MH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            MH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            MH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }
                    MH.textViewDetailIcon.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case SUB_MENUITEM_LIST:
                    cMenuModel subMenuModel = (cMenuModel) treeModel.getModelObject();
                    cSubMenuItemViewHolder SH = ((cSubMenuItemViewHolder) viewHolder);

                    SH.setPaddingLeft(20 * node.getLevel());

                    SH.textViewDetailIcon.setVisibility(View.GONE);

                    /* check box to select the sub menu item */
                    SH.checkBoxMenu.setChecked(subMenuModel.isChecked());
                    SH.checkBoxMenu.setOnClickListener(view -> {
                        boolean checked = ((CheckBox) view).isChecked();
                        subMenuModel.setChecked(checked);
                    });

                    /* sub menu details */
                    SH.textViewName.setText(subMenuModel.getName());
                    SH.textViewDescription.setText(subMenuModel.getDescription());

                    break;

                case ENTITYMODULE_SECTION:
                    cSectionModel entitymoduleModel = (cSectionModel) treeModel.getModelObject();
                    cEntityModuleSectionViewHolder EMH = ((cEntityModuleSectionViewHolder) viewHolder);

                    EMH.setPaddingLeft(20 * node.getLevel());

                    /* entity module header */
                    EMH.textViewHeading.setText(entitymoduleModel.getName());

                    /* the collapse and expansion of the entities */
                    if (node.isLeaf()) {
                        EMH.toggleView.setVisibility(View.GONE);
                    } else {
                        EMH.toggleView.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            EMH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            EMH.toggleIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            EMH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        } else {
                            EMH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            EMH.toggleIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            EMH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        }
                    }
                    EMH.toggleView.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case ENTITY_LIST:
                    cEntityModel entityModel = (cEntityModel) treeModel.getModelObject();
                    cEntityViewHolder EH = ((cEntityViewHolder) viewHolder);

                    EH.setPaddingLeft(20 * node.getLevel());

                    /* check box to select the entities */
                    EH.entityBinding.checkBoxMenu.setChecked(entityModel.isChecked());
                    EH.entityBinding.checkBoxMenu.setOnClickListener(view -> {
                        boolean checked = ((CheckBox) view).isChecked();
                        entityModel.setChecked(checked);
                    });

                    /* entity details */
                    EH.entityBinding.textViewName.setText(entityModel.getName());
                    EH.entityBinding.textViewDescription.setText(entityModel.getDescription());

                    /* the collapse and expansion of the entities */
                    if (node.isLeaf()) {
                        EH.entityBinding.textViewDetailIcon.setVisibility(View.GONE);
                    } else {

                        EH.entityBinding.textViewDetailIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            EH.entityBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            EH.entityBinding.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            EH.entityBinding.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            EH.entityBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            EH.entityBinding.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            EH.entityBinding.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }

                    Log.d("TAG", "MODULE TREE 12 ----------------->>>>>>>>" + treeModel.getChildID());


                    //EH.entityBinding.textViewDetailIcon.setOnClickListener(v -> expandOrCollapse(position));

                    break;

//                case ENTITY_OPS_SECTION:
//                    cSectionModel entityOperationSection = (cSectionModel) treeModel.getModelObject();
//                    cEntityOperationSectionViewHolder EOSH = ((cEntityOperationSectionViewHolder)
//                            viewHolder);
//
//                    EOSH.setPaddingLeft(20 * node.getLevel());
//
//                    /* entity operation header */
//                    EOSH.textViewHeading.setText(entityOperationSection.getName());
//
//                    /* the collapse and expansion of the entity operations */
//                    if (node.isLeaf()) {
//                        EOSH.toggleView.setVisibility(View.GONE);
//                    } else {
//                        EOSH.toggleView.setVisibility(View.VISIBLE);
//                        if (node.isExpand()) {
//                            EOSH.toggleIcon.setTypeface(null, Typeface.NORMAL);
//                            EOSH.toggleIcon.setTypeface(
//                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//                            EOSH.toggleIcon.setText(
//                                    context.getResources().getString(R.string.fa_angle_down));
//                        } else {
//                            EOSH.toggleIcon.setTypeface(null, Typeface.NORMAL);
//                            EOSH.toggleIcon.setTypeface(
//                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//                            EOSH.toggleIcon.setText(
//                                    context.getResources().getString(R.string.fa_angle_up));
//                        }
//                    }
//                    EOSH.toggleView.setOnClickListener(v -> expandOrCollapse(position));
//
//                    break;
//
//                case ENTITY_OPS_LIST:
//                    cOperationModel operationModel = (cOperationModel) treeModel.getModelObject();
//                    cEntityOperationViewHolder OH = ((cEntityOperationViewHolder) viewHolder);
//
//                    OH.setPaddingLeft(20 * node.getLevel());
//
//                    /* check box to select the entity operations */
//                    OH.checkBoxEntityOperation.setChecked(operationModel.isChecked());
//                    OH.checkBoxEntityOperation.setOnClickListener(view -> {
//                        boolean checked = ((CheckBox) view).isChecked();
//                        operationModel.setChecked(checked);
//                    });
//
//                    /* entity operation details */
//                    OH.textViewName.setText(operationModel.getName());
//                    OH.textViewDescription.setText(operationModel.getDescription());
//
//                    /* the collapse and expansion of the entity operations */
//                    if (node.isLeaf()) {
//                        OH.textViewDetailIcon.setVisibility(View.GONE);
//                    } else {
//
//                        OH.textViewDetailIcon.setVisibility(View.VISIBLE);
//                        if (node.isExpand()) {
//                            OH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
//                            OH.textViewDetailIcon.setTypeface(
//                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//                            OH.textViewDetailIcon.setText(
//                                    context.getResources().getString(R.string.fa_minus));
//                        } else {
//                            OH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
//                            OH.textViewDetailIcon.setTypeface(
//                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//                            OH.textViewDetailIcon.setText(
//                                    context.getResources().getString(R.string.fa_plus));
//                        }
//                    }
//
//                    OH.textViewDetailIcon.setOnClickListener(v -> expandOrCollapse(position));
//
//                    break;
//
//                case OPS_STATUS_LIST:
//                    cOperationStatusCollection statusCollection = (cOperationStatusCollection)
//                            treeModel.getModelObject();
//                    cOperationStatusViewHolder OSH = ((cOperationStatusViewHolder) viewHolder);
//
//                    OSH.setPaddingLeft(20 * node.getLevel());
//
//                    List<cStatusModel> statusModels = statusCollection.getStatusCollection();
//                    for (cStatusModel statusModel : statusModels) {
//                        switch (statusModel.getStatusServerID()) {
//                            case DELETED:
//                                OSH.switchDeleted.setChecked(statusModel.isChecked());
//                                OSH.switchDeleted.setTag(statusModel);
//                                break;
//                            case BLOCKED:
//                                OSH.switchBlocked.setChecked(statusModel.isChecked());
//                                OSH.switchBlocked.setTag(statusModel);
//                                break;
//                            case ACTIVATED:
//                                OSH.switchActivated.setChecked(statusModel.isChecked());
//                                OSH.switchActivated.setTag(statusModel);
//                                break;
//                            case CANCELLED:
//                                OSH.switchCancelled.setChecked(statusModel.isChecked());
//                                OSH.switchCancelled.setTag(statusModel);
//                                break;
//                            case PENDING:
//                                OSH.switchPending.setChecked(statusModel.isChecked());
//                                OSH.switchPending.setTag(statusModel);
//                                break;
//                        }
//                    }
//
//                    OSH.switchDeleted.setOnClickListener(view -> {
//                        boolean checked = ((SwitchMaterial) view).isChecked();
//                        ((cStatusModel) OSH.switchDeleted.getTag()).setChecked(checked);
//                    });
//
//                    OSH.switchBlocked.setOnClickListener(view -> {
//                        boolean checked = ((SwitchMaterial) view).isChecked();
//                        ((cStatusModel) OSH.switchBlocked.getTag()).setChecked(checked);
//                    });
//
//                    OSH.switchActivated.setOnClickListener(view -> {
//                        boolean checked = ((SwitchMaterial) view).isChecked();
//                        ((cStatusModel) OSH.switchActivated.getTag()).setChecked(checked);
//                    });
//
//                    OSH.switchCancelled.setOnClickListener(view -> {
//                        boolean checked = ((SwitchMaterial) view).isChecked();
//                        ((cStatusModel) OSH.switchCancelled.getTag()).setChecked(checked);
//                    });
//
//                    OSH.switchPending.setOnClickListener(view -> {
//                        boolean checked = ((SwitchMaterial) view).isChecked();
//                        ((cStatusModel) OSH.switchPending.getTag()).setChecked(checked);
//                    });
//
//                    break;
//
//                case UNIX_OPS_SECTION:
//                    cSectionModel unixOperationSection = (cSectionModel) treeModel.getModelObject();
//                    cUnixOperationSectionViewHolder UOSH = ((cUnixOperationSectionViewHolder)
//                            viewHolder);
//
//                    UOSH.setPaddingLeft(20 * node.getLevel());
//
//                    /* unix operation header */
//                    UOSH.textViewHeading.setText(unixOperationSection.getName());
//
//                    /* the collapse and expansion of the unix operations */
//                    if (node.isLeaf()) {
//                        UOSH.toggleView.setVisibility(View.GONE);
//                    } else {
//                        UOSH.toggleView.setVisibility(View.VISIBLE);
//                        if (node.isExpand()) {
//                            UOSH.toggleIcon.setTypeface(null, Typeface.NORMAL);
//                            UOSH.toggleIcon.setTypeface(
//                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//                            UOSH.toggleIcon.setText(
//                                    context.getResources().getString(R.string.fa_angle_down));
//                        } else {
//                            UOSH.toggleIcon.setTypeface(null, Typeface.NORMAL);
//                            UOSH.toggleIcon.setTypeface(
//                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//                            UOSH.toggleIcon.setText(
//                                    context.getResources().getString(R.string.fa_angle_up));
//                        }
//                    }
//                    UOSH.toggleView.setOnClickListener(v -> expandOrCollapse(position));
//
//                    break;

                case ENTITY_PERM_TABLE:
                    cUnixOperationCollection unix_ops = (cUnixOperationCollection)
                            treeModel.getModelObject();

                    cUnixOperationViewHolder UOH = ((cUnixOperationViewHolder) viewHolder);

                    List<cUnixOperationModel> models = unix_ops.getUnixOperationModels();
                    for (cUnixOperationModel unixOperationModel : models) {
                        int ops = Integer.parseInt(unixOperationModel.getOperationServerID());
                        switch (ops) {
                            /* create operations */
                            case OWNER_CREATE:
                                UOH.permBinding.checkBoxOwnerCreate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxOwnerCreate.setTag(unixOperationModel);
                                break;
                            case ROOM_CREATE:
                                UOH.permBinding.checkBoxRoomCreate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxRoomCreate.setTag(unixOperationModel);
                                break;
                            case HOUSE_CREATE:
                                UOH.permBinding.checkBoxHouseCreate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxHouseCreate.setTag(unixOperationModel);
                                break;
                            case VILLAGE_CREATE:
                                UOH.permBinding.checkBoxVillageCreate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxVillageCreate.setTag(unixOperationModel);
                                break;
                            case CLOUD_CREATE:
                                UOH.permBinding.checkBoxCloudCreate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxCloudCreate.setTag(unixOperationModel);
                                break;
                            /* read operations */
                            case OWNER_READ:
                                UOH.permBinding.checkBoxOwnerRead.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxOwnerRead.setTag(unixOperationModel);
                                break;
                            case ROOM_READ:
                                UOH.permBinding.checkBoxRoomRead.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxRoomRead.setTag(unixOperationModel);
                                break;
                            case HOUSE_READ:
                                UOH.permBinding.checkBoxHouseRead.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxHouseRead.setTag(unixOperationModel);
                                break;
                            case VILLAGE_READ:
                                UOH.permBinding.checkBoxVillageRead.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxVillageRead.setTag(unixOperationModel);
                                break;
                            case CLOUD_READ:
                                UOH.permBinding.checkBoxCloudRead.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxCloudRead.setTag(unixOperationModel);
                                break;
                            /* update operations */
                            case OWNER_UPDATE:
                                UOH.permBinding.checkBoxOwnerUpdate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxOwnerUpdate.setTag(unixOperationModel);
                                break;
                            case ROOM_UPDATE:
                                UOH.permBinding.checkBoxRoomUpdate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxRoomUpdate.setTag(unixOperationModel);
                                break;
                            case HOUSE_UPDATE:
                                UOH.permBinding.checkBoxHouseUpdate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxHouseUpdate.setTag(unixOperationModel);
                                break;
                            case VILLAGE_UPDATE:
                                UOH.permBinding.checkBoxVillageUpdate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxVillageUpdate.setTag(unixOperationModel);
                                break;
                            case CLOUD_UPDATE:
                                UOH.permBinding.checkBoxCloudUpdate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxCloudUpdate.setTag(unixOperationModel);
                                break;
                            /* delete operations */
                            case OWNER_DELETE:
                                UOH.permBinding.checkBoxOwnerDelete.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxOwnerDelete.setTag(unixOperationModel);
                                break;
                            case ROOM_DELETE:
                                UOH.permBinding.checkBoxRoomDelete.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxRoomDelete.setTag(unixOperationModel);
                                break;
                            case HOUSE_DELETE:
                                UOH.permBinding.checkBoxHouseDelete.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxHouseDelete.setTag(unixOperationModel);
                                break;
                            case VILLAGE_DELETE:
                                UOH.permBinding.checkBoxVillageDelete.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxVillageDelete.setTag(unixOperationModel);
                                break;
                            case CLOUD_DELETE:
                                UOH.permBinding.checkBoxCloudDelete.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.permBinding.checkBoxCloudDelete.setTag(unixOperationModel);
                                break;
                        }

                        /* owner permissions */
                        UOH.permBinding.checkBoxOwnerCreate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxOwnerCreate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxOwnerRead.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxOwnerRead.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxOwnerUpdate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxOwnerUpdate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxOwnerDelete.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxOwnerDelete.getTag()).
                                    setChecked(checked);
                        });

                        /* room permissions */
                        UOH.permBinding.checkBoxRoomCreate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxRoomCreate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxRoomRead.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxRoomRead.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxRoomUpdate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxRoomUpdate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxRoomDelete.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxRoomDelete.getTag()).
                                    setChecked(checked);
                        });

                        /* house permissions */
                        UOH.permBinding.checkBoxHouseCreate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxHouseCreate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxHouseRead.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxHouseRead.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxHouseUpdate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxHouseUpdate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxHouseDelete.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxHouseDelete.getTag()).
                                    setChecked(checked);
                        });

                        /* village permissions */
                        UOH.permBinding.checkBoxVillageCreate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxVillageCreate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxVillageRead.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxVillageRead.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxVillageUpdate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxVillageUpdate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxVillageDelete.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxVillageDelete.getTag()).
                                    setChecked(checked);
                        });

                        /* cloud permissions */
                        UOH.permBinding.checkBoxCloudCreate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxCloudCreate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxCloudRead.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxCloudRead.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxCloudUpdate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxCloudUpdate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.permBinding.checkBoxCloudDelete.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.permBinding.checkBoxCloudDelete.getTag()).
                                    setChecked(checked);
                        });
                    }

                    UOH.setPaddingLeft(20 * node.getLevel());

                    break;
            }
        }
    }

    @Override
    public void onClickUpdatePermission(List<cNode> nodes) {
        this.iPermissionPresenterView.onClickUpdateRolePermission(nodes);
    }

    @Override
    public void onClickDeletePermission(String permissionServerID) {

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredModuleTree = getTreeModel();
                } else {

                    ArrayList<cTreeModel> filteredList = new ArrayList<>();
                    for (cTreeModel treeModel : getTreeModel()) {
                        if (((cPermissionModel) treeModel.getModelObject()).getName().toLowerCase().
                                contains(charString.toLowerCase())) {
                            filteredList.add(treeModel);
                        }
                    }

                    filteredModuleTree = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredModuleTree.size();
                filterResults.values = filteredModuleTree;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredModuleTree = (ArrayList<cTreeModel>) filterResults.values;
                try {
                    Gson gson = new Gson();
                    Log.d(TAG, "===============>>>> " + gson.toJson(filteredModuleTree));
                    notifyTreeModelChanged(filteredModuleTree);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static class cPrivilegeViewHolder extends RecyclerView.ViewHolder {
        private final SessionPrivilegeCardviewBinding parentBinding;
        private final View treeView;

//        private final TextView textViewName;
//        private final TextView textViewDescription;
//        private final TextView textViewDetailIcon;
//        private final TextView textViewUpdateIcon;
//        private final TextView textViewDeleteIcon;
//
//        private final View viewHolder;

        private final iViewPermissionListener viewPermissionListener;

        private cPrivilegeViewHolder(SessionPrivilegeCardviewBinding parentBinding, iViewPermissionListener listener) {
            super(parentBinding.getRoot());

            this.parentBinding = parentBinding;
            this.treeView = parentBinding.getRoot();
            this.viewPermissionListener = listener;

//            this.viewHolder = viewHolder;
//            this.viewPermissionListener = listener;
//
//            this.textViewName = viewHolder.findViewById(R.id.textViewName);
//            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
//            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
//            this.textViewUpdateIcon = viewHolder.findViewById(R.id.textViewUpdateIcon);
//            this.textViewDeleteIcon = viewHolder.findViewById(R.id.textViewDeleteIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            this.treeView.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cMenuItemViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkBoxMenu;
        private final TextView textViewName;
        private final TextView textViewDescription;
        private final TextView textViewDetailIcon;

        private final View viewHolder;

        private cMenuItemViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.checkBoxMenu = viewHolder.findViewById(R.id.checkBoxMenu);
            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cSubMenuItemViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatCheckBox checkBoxMenu;
        private final TextView textViewName;
        private final TextView textViewDescription;
        private final TextView textViewDetailIcon;

        private final View viewHolder;

        private cSubMenuItemViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.checkBoxMenu = viewHolder.findViewById(R.id.checkBoxMenu);
            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cMenuItemSectionViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout toggleView;
        private final TextView textViewHeading;
        private final TextView toggleIcon;

        private final View viewHolder;

        private cMenuItemSectionViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.toggleView = viewHolder.findViewById(R.id.toggleView);
            this.textViewHeading = viewHolder.findViewById(R.id.textViewHeading);
            this.toggleIcon = viewHolder.findViewById(R.id.toggleIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cEntityModuleSectionViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout toggleView;
        private final TextView textViewHeading;
        private final TextView toggleIcon;

        private final View viewHolder;

        private cEntityModuleSectionViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.toggleView = viewHolder.findViewById(R.id.toggleView);
            this.textViewHeading = viewHolder.findViewById(R.id.textViewHeading);
            this.toggleIcon = viewHolder.findViewById(R.id.toggleIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }


    public static class cEntityViewHolder extends RecyclerView.ViewHolder {
        private final SessionMenuEntityCardviewBinding entityBinding;
        private final View treeView;

//        private final AppCompatCheckBox checkBoxEntity;
//        private final TextView textViewName;
//        private final TextView textViewDescription;
//        private final TextView textViewDetailIcon;
//
//        private final View viewHolder;

        private cEntityViewHolder(SessionMenuEntityCardviewBinding entityBinding) {
            super(entityBinding.getRoot());

            this.entityBinding = entityBinding;
            this.treeView = entityBinding.getRoot();

//            super(viewHolder);
//            this.viewHolder = viewHolder;
//
//            this.checkBoxEntity = viewHolder.findViewById(R.id.checkBoxMenu);
//            this.textViewName = viewHolder.findViewById(R.id.textViewName);
//            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
//            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            this.treeView.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cEntityOperationSectionViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout toggleView;
        private final TextView textViewHeading;
        private final TextView toggleIcon;

        private final View viewHolder;

        private cEntityOperationSectionViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;
            this.toggleView = viewHolder.findViewById(R.id.toggleView);
            this.textViewHeading = viewHolder.findViewById(R.id.textViewHeading);
            this.toggleIcon = viewHolder.findViewById(R.id.toggleIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cEntityOperationViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatCheckBox checkBoxEntityOperation;
        private final TextView textViewName;
        private final TextView textViewDescription;
        private final TextView textViewDetailIcon;

        private final View viewHolder;

        private cEntityOperationViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.checkBoxEntityOperation = viewHolder.findViewById(R.id.checkBoxMenu);
            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cOperationStatusViewHolder extends RecyclerView.ViewHolder {

        private final SwitchMaterial switchDeleted;
        private final SwitchMaterial switchBlocked;
        private final SwitchMaterial switchActivated;
        private final SwitchMaterial switchCancelled;
        private final SwitchMaterial switchPending;

        private final View viewHolder;

        private cOperationStatusViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.switchDeleted = viewHolder.findViewById(R.id.switchDeleted);
            this.switchBlocked = viewHolder.findViewById(R.id.switchBlocked);
            this.switchActivated = viewHolder.findViewById(R.id.switchActivated);
            this.switchCancelled = viewHolder.findViewById(R.id.switchCancelled);
            this.switchPending = viewHolder.findViewById(R.id.switchPending);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cUnixOperationSectionViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout toggleView;
        private final TextView textViewHeading;
        private final TextView toggleIcon;

        private final View viewHolder;

        private cUnixOperationSectionViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;
            this.toggleView = viewHolder.findViewById(R.id.toggleView);
            this.textViewHeading = viewHolder.findViewById(R.id.textViewHeading);
            this.toggleIcon = viewHolder.findViewById(R.id.toggleIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cUnixOperationViewHolder extends RecyclerView.ViewHolder {
        private final SessionUnixPermissionBinding permBinding;
        private final View treeView;

//        private final AppCompatCheckBox checkBoxOwnerRead;
//        private final AppCompatCheckBox checkBoxOwnerUpdate;
//        private final AppCompatCheckBox checkBoxOwnerDelete;
//
//        private final AppCompatCheckBox checkBoxPrimaryRead;
//        private final AppCompatCheckBox checkBoxPrimaryUpdate;
//        private final AppCompatCheckBox checkBoxPrimaryDelete;
//
//        private final AppCompatCheckBox checkBoxSecondaryRead;
//        private final AppCompatCheckBox checkBoxSecondaryUpdate;
//        private final AppCompatCheckBox checkBoxSecondaryDelete;
//
//        private final AppCompatCheckBox checkBoxOrganizationRead;
//        private final AppCompatCheckBox checkBoxOrganizationUpdate;
//        private final AppCompatCheckBox checkBoxOrganizationDelete;
//
//        private final View viewHolder;

        private cUnixOperationViewHolder(@NonNull SessionUnixPermissionBinding permBinding) {
            super(permBinding.getRoot());
            this.permBinding = permBinding;
            this.treeView = permBinding.getRoot();

//            super(viewHolder);
//            this.viewHolder = viewHolder;
//
//            this.checkBoxOwnerRead = viewHolder.findViewById(R.id.checkBoxOwnerRead);
//            this.checkBoxOwnerUpdate = viewHolder.findViewById(R.id.checkBoxOwnerUpdate);
//            this.checkBoxOwnerDelete = viewHolder.findViewById(R.id.checkBoxOwnerDelete);
//
//            this.checkBoxPrimaryRead = viewHolder.findViewById(R.id.checkBoxPrimaryRead);
//            this.checkBoxPrimaryUpdate = viewHolder.findViewById(R.id.checkBoxPrimaryUpdate);
//            this.checkBoxPrimaryDelete = viewHolder.findViewById(R.id.checkBoxPrimaryDelete);
//
//            this.checkBoxSecondaryRead = viewHolder.findViewById(R.id.checkBoxSecondaryRead);
//            this.checkBoxSecondaryUpdate = viewHolder.findViewById(R.id.checkBoxSecondaryUpdate);
//            this.checkBoxSecondaryDelete = viewHolder.findViewById(R.id.checkBoxSecondaryDelete);
//
//            this.checkBoxOrganizationRead = viewHolder.findViewById(R.id.checkBoxOrganizationRead);
//            this.checkBoxOrganizationUpdate = viewHolder.findViewById(R.id.checkBoxOrganizationUpdate);
//            this.checkBoxOrganizationDelete = viewHolder.findViewById(R.id.checkBoxOrganizationDelete);
        }

        public void setPaddingLeft(int paddingLeft) {
            this.treeView.setPadding(paddingLeft,
                    0, 0, 0);
        }
    }
}