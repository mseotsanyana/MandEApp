package com.me.mseotsanyana.mande.framework.ui.adapters.session;

import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.CLOUD_CREATE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.CLOUD_DELETE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.CLOUD_READ;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.CLOUD_UPDATE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.HOUSE_CREATE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.HOUSE_DELETE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.HOUSE_READ;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.HOUSE_UPDATE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.OWNER_CREATE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.OWNER_DELETE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.OWNER_READ;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.OWNER_UPDATE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.ROOM_CREATE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.ROOM_DELETE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.ROOM_READ;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.ROOM_UPDATE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.VILLAGE_CREATE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.VILLAGE_DELETE;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.VILLAGE_READ;
import static com.me.mseotsanyana.mande.application.structures.CPreferenceConstant.VILLAGE_UPDATE;

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
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.domain.entities.models.session.cEntityModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CMenuModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CPrivilegeModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cSectionModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cPermissionModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.cUnixOperationCollection;
import com.me.mseotsanyana.mande.domain.entities.models.session.cUnixOperationModel;
import com.me.mseotsanyana.mande.infrastructure.ports.session.iPrivilegePresenter;
import com.me.mseotsanyana.mande.framework.ports.adapters.session.iViewPrivilegeListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.framework.utils.CFontManager;
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

public class cPrivilegeAdapter extends cTreeAdapter implements iViewPrivilegeListener, Filterable {
    private static final String TAG = cPrivilegeAdapter.class.getSimpleName();
//    private static final SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PRIVILEGES           = 0;
    private static final int MENUITEM_SECTION     = 1;
    private static final int MENUITEMS            = 2;
    private static final int SUB_MENUITEMS        = 3;
    private static final int ENTITYMODULE_SECTION = 4;
    private static final int ENTITIES             = 5;
    private static final int ENTITY_PERMS         = 6;

    private final Context context;
    private LayoutInflater layoutInflater;

    private List<cTreeModel> filteredModuleTree;

    private final iPrivilegePresenter.View iPermissionPresenterView;


    public cPrivilegeAdapter(Context context,
                             iPrivilegePresenter.View iPermissionPresenterView,
                             List<cTreeModel> moduleTree) {
        super(context, moduleTree);
        this.context = context;
        this.filteredModuleTree = moduleTree;

        this.iPermissionPresenterView = iPermissionPresenterView;
    }

    /*
    public void setModuleWithSubMenu(ArrayList<cTreeModel> moduleTree) {
        this.filteredModuleTree = moduleTree;
    }*/

    @Override
    public RecyclerView.ViewHolder OnCreateTreeViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {
            case PRIVILEGES:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }

                SessionPrivilegeCardviewBinding parentBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.session_privilege_cardview, parent,
                        false);

                viewHolder = new cPrivilegeViewHolder(parentBinding, this);
                //cPrivilegeViewHolder POH = (cPrivilegeViewHolder) viewHolder;

                break;

            case MENUITEM_SECTION:
                view = inflater.inflate(R.layout.session_section_fragment, parent,
                        false);
                viewHolder = new cMenuItemSectionViewHolder(view);
                break;

            case MENUITEMS:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }

                SessionMenuEntityCardviewBinding menuitemsBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.session_menu_entity_cardview, parent,
                        false);

                viewHolder = new cMenuItemViewHolder(menuitemsBinding);

                break;

            case SUB_MENUITEMS:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }

                SessionMenuEntityCardviewBinding submenuitemsBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.session_menu_entity_cardview, parent,
                        false);

                viewHolder = new cSubMenuItemViewHolder(submenuitemsBinding);

                break;

            case ENTITYMODULE_SECTION:
                view = inflater.inflate(R.layout.session_section_fragment, parent,
                        false);
                viewHolder = new cEntityModuleSectionViewHolder(view);
                break;

            case ENTITIES:
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

                break;

            case ENTITY_PERMS:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }

                SessionUnixPermissionBinding permBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.session_unix_permission, parent,
                        false);

                viewHolder = new cUnixOperationViewHolder(permBinding);

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
                case PRIVILEGES:
                    CPrivilegeModel privilegeModel = (CPrivilegeModel) treeModel.getModelObject();
                    cPrivilegeViewHolder PH = ((cPrivilegeViewHolder) viewHolder);

                    PH.setPaddingLeft(20 * node.getLevel());

                    /* permission menu */
                    PH.parentBinding.textViewName.setText(privilegeModel.getName());
                    PH.parentBinding.textViewDescription.setText(privilegeModel.getDescription());

                    /* the collapse and expansion of the permission */
                    if (node.isLeaf()) {
                        PH.parentBinding.textViewDetailIcon.setVisibility(View.GONE);
                    } else {
                        PH.parentBinding.textViewDetailIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            PH.parentBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            PH.parentBinding.textViewDetailIcon.setTypeface(
                                    CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                            PH.parentBinding.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        } else {
                            PH.parentBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            PH.parentBinding.textViewDetailIcon.setTypeface(
                                    CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                            PH.parentBinding.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        }
                    }
                    PH.parentBinding.textViewDetailIcon.setOnClickListener(v -> expandOrCollapse(position));

                    /* icon for saving updated record */
                    PH.parentBinding.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    PH.parentBinding.textViewUpdateIcon.setTypeface(
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    PH.parentBinding.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PH.parentBinding.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    PH.parentBinding.textViewUpdateIcon.setOnClickListener(view -> {
                        String name = PH.parentBinding.textViewName.getText().toString();
                        String description = PH.parentBinding.textViewDescription.getText().toString();
                        PH.viewPrivilegeListener.onClickUpdatePrivilege(node);
                    });

                    /* icon for deleting a record */
                    PH.parentBinding.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    PH.parentBinding.textViewDeleteIcon.setTypeface(
                            CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                    PH.parentBinding.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PH.parentBinding.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    PH.parentBinding.textViewDeleteIcon.setOnClickListener(view -> {
                        //FIXME:PVH.logFrameListener.onClickDeleteLogFrame(position,parentLogFrame.getLogFrameID());
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
                                    CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                            MIH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        } else {
                            MIH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            MIH.toggleIcon.setTypeface(
                                    CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                            MIH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        }
                    }
                    MIH.toggleView.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case MENUITEMS:
                    CMenuModel menuModel = (CMenuModel) treeModel.getModelObject();
                    cMenuItemViewHolder MH = ((cMenuItemViewHolder) viewHolder);

                    MH.setPaddingLeft(20 * node.getLevel());

                    /* check box to select the menu items */
                    MH.menuitemsBinding.checkBoxMenu.setChecked(menuModel.isChecked());
                    MH.menuitemsBinding.checkBoxMenu.setOnClickListener(view -> {
                        boolean checked = ((CheckBox) view).isChecked();
                        menuModel.setChecked(checked);
                    });

                    /* menu items */
                    MH.menuitemsBinding.textViewName.setText(menuModel.getName());
                    MH.menuitemsBinding.textViewDescription.setText(menuModel.getDescription());

                    /* the collapse and expansion of the main menu */
                    if (node.isLeaf()) {
                        MH.menuitemsBinding.textViewDetailIcon.setVisibility(View.GONE);
                    } else {

                        MH.menuitemsBinding.textViewDetailIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            MH.menuitemsBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            MH.menuitemsBinding.textViewDetailIcon.setTypeface(
                                    CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                            MH.menuitemsBinding.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            MH.menuitemsBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            MH.menuitemsBinding.textViewDetailIcon.setTypeface(
                                    CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                            MH.menuitemsBinding.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }
                    MH.menuitemsBinding.textViewDetailIcon.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case SUB_MENUITEMS:
                    CMenuModel subMenuModel = (CMenuModel) treeModel.getModelObject();
                    cSubMenuItemViewHolder SH = ((cSubMenuItemViewHolder) viewHolder);

                    SH.setPaddingLeft(20 * node.getLevel());

                    SH.submenuitemsBinding.textViewDetailIcon.setVisibility(View.GONE);

                    /* check box to select the sub menu item */
                    SH.submenuitemsBinding.checkBoxMenu.setChecked(subMenuModel.isChecked());
                    SH.submenuitemsBinding.checkBoxMenu.setOnClickListener(view -> {
                        boolean checked = ((CheckBox) view).isChecked();
                        subMenuModel.setChecked(checked);
                    });

                    /* sub menu details */
                    SH.submenuitemsBinding.textViewName.setText(subMenuModel.getName());
                    SH.submenuitemsBinding.textViewDescription.setText(subMenuModel.getDescription());

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
                                    CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                            EMH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        } else {
                            EMH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            EMH.toggleIcon.setTypeface(
                                    CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                            EMH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        }
                    }
                    EMH.toggleView.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case ENTITIES:
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
                                    CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                            EH.entityBinding.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            EH.entityBinding.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            EH.entityBinding.textViewDetailIcon.setTypeface(
                                    CFontManager.getTypeface(context, CFontManager.FONTAWESOME));
                            EH.entityBinding.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }

                    break;

                case ENTITY_PERMS:
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
    public void onClickUpdatePrivilege(cNode node) {
        this.iPermissionPresenterView.onClickUpdateWorkspacePrivilege(node);
    }

    @Override
    public void onClickDeletePrivilege(String permissionServerID) {

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

        private final iViewPrivilegeListener viewPrivilegeListener;

        private cPrivilegeViewHolder(@NonNull SessionPrivilegeCardviewBinding parentBinding,
                                     iViewPrivilegeListener listener) {
            super(parentBinding.getRoot());

            this.parentBinding = parentBinding;
            this.treeView = parentBinding.getRoot();
            this.viewPrivilegeListener = listener;
        }

        public void setPaddingLeft(int paddingLeft) {
            this.treeView.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cMenuItemViewHolder extends RecyclerView.ViewHolder {
        private final SessionMenuEntityCardviewBinding menuitemsBinding;
        private final View treeView;

        private cMenuItemViewHolder(@NonNull SessionMenuEntityCardviewBinding menuitemsBinding) {
            super(menuitemsBinding.getRoot());

            this.menuitemsBinding = menuitemsBinding;
            this.treeView = menuitemsBinding.getRoot();
        }

        public void setPaddingLeft(int paddingLeft) {
            this.treeView.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cSubMenuItemViewHolder extends RecyclerView.ViewHolder {
        private final SessionMenuEntityCardviewBinding submenuitemsBinding;
        private final View treeView;

        private cSubMenuItemViewHolder(@NonNull SessionMenuEntityCardviewBinding submenuitemsBinding) {
            super(submenuitemsBinding.getRoot());

            this.submenuitemsBinding = submenuitemsBinding;
            this.treeView = submenuitemsBinding.getRoot();
        }

        public void setPaddingLeft(int paddingLeft) {
            this.treeView.setPadding(paddingLeft, 0,
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
            this.viewHolder.setPadding(paddingLeft, 0,
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

        private cEntityViewHolder(@NonNull SessionMenuEntityCardviewBinding entityBinding) {
            super(entityBinding.getRoot());

            this.entityBinding = entityBinding;
            this.treeView = entityBinding.getRoot();
        }

        public void setPaddingLeft(int paddingLeft) {
            this.treeView.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cUnixOperationViewHolder extends RecyclerView.ViewHolder {
        private final SessionUnixPermissionBinding permBinding;
        private final View treeView;

        private cUnixOperationViewHolder(@NonNull SessionUnixPermissionBinding permBinding) {
            super(permBinding.getRoot());
            this.permBinding = permBinding;
            this.treeView = permBinding.getRoot();
        }

        public void setPaddingLeft(int paddingLeft) {
            this.treeView.setPadding(paddingLeft,
                    0, 0, 0);
        }
    }
}