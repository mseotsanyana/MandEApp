package com.me.mseotsanyana.mande;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.me.mseotsanyana.mande.databinding.ComponentParentCardviewBindingImpl;
import com.me.mseotsanyana.mande.databinding.LogframeChildCardviewBindingImpl;
import com.me.mseotsanyana.mande.databinding.LogframeListFragmentBindingImpl;
import com.me.mseotsanyana.mande.databinding.LogframeParentCardviewBindingImpl;
import com.me.mseotsanyana.mande.databinding.MeCommonAttributesBindingImpl;
import com.me.mseotsanyana.mande.databinding.MeTablayoutViewpager2BindingImpl;
import com.me.mseotsanyana.mande.databinding.MeToolbarLayoutBindingImpl;
import com.me.mseotsanyana.mande.databinding.ProjectChildCardviewBindingImpl;
import com.me.mseotsanyana.mande.databinding.ProjectListFragmentBindingImpl;
import com.me.mseotsanyana.mande.databinding.ProjectParentCardviewBindingImpl;
import com.me.mseotsanyana.mande.databinding.SessionChangepasswordFragmentBindingImpl;
import com.me.mseotsanyana.mande.databinding.SessionLoginFragmentBindingImpl;
import com.me.mseotsanyana.mande.databinding.SessionMenuEntityCardviewBindingImpl;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationCardviewBindingImpl;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationFragmentBindingImpl;
import com.me.mseotsanyana.mande.databinding.SessionOrganizationWithTabsFragmentBindingImpl;
import com.me.mseotsanyana.mande.databinding.SessionPrivilegeCardviewBindingImpl;
import com.me.mseotsanyana.mande.databinding.SessionResetpasswordFragmentBindingImpl;
import com.me.mseotsanyana.mande.databinding.SessionUnixPermissionBindingImpl;
import com.me.mseotsanyana.mande.databinding.SessionWorkspaceCardviewBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_COMPONENTPARENTCARDVIEW = 1;

  private static final int LAYOUT_LOGFRAMECHILDCARDVIEW = 2;

  private static final int LAYOUT_LOGFRAMELISTFRAGMENT = 3;

  private static final int LAYOUT_LOGFRAMEPARENTCARDVIEW = 4;

  private static final int LAYOUT_MECOMMONATTRIBUTES = 5;

  private static final int LAYOUT_METABLAYOUTVIEWPAGER2 = 6;

  private static final int LAYOUT_METOOLBARLAYOUT = 7;

  private static final int LAYOUT_PROJECTCHILDCARDVIEW = 8;

  private static final int LAYOUT_PROJECTLISTFRAGMENT = 9;

  private static final int LAYOUT_PROJECTPARENTCARDVIEW = 10;

  private static final int LAYOUT_SESSIONCHANGEPASSWORDFRAGMENT = 11;

  private static final int LAYOUT_SESSIONLOGINFRAGMENT = 12;

  private static final int LAYOUT_SESSIONMENUENTITYCARDVIEW = 13;

  private static final int LAYOUT_SESSIONORGANIZATIONCARDVIEW = 14;

  private static final int LAYOUT_SESSIONORGANIZATIONFRAGMENT = 15;

  private static final int LAYOUT_SESSIONORGANIZATIONWITHTABSFRAGMENT = 16;

  private static final int LAYOUT_SESSIONPRIVILEGECARDVIEW = 17;

  private static final int LAYOUT_SESSIONRESETPASSWORDFRAGMENT = 18;

  private static final int LAYOUT_SESSIONUNIXPERMISSION = 19;

  private static final int LAYOUT_SESSIONWORKSPACECARDVIEW = 20;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(20);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.component_parent_cardview, LAYOUT_COMPONENTPARENTCARDVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.logframe_child_cardview, LAYOUT_LOGFRAMECHILDCARDVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.logframe_list_fragment, LAYOUT_LOGFRAMELISTFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.logframe_parent_cardview, LAYOUT_LOGFRAMEPARENTCARDVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.me_common_attributes, LAYOUT_MECOMMONATTRIBUTES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.me_tablayout_viewpager2, LAYOUT_METABLAYOUTVIEWPAGER2);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.me_toolbar_layout, LAYOUT_METOOLBARLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.project_child_cardview, LAYOUT_PROJECTCHILDCARDVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.project_list_fragment, LAYOUT_PROJECTLISTFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.project_parent_cardview, LAYOUT_PROJECTPARENTCARDVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.session_changepassword_fragment, LAYOUT_SESSIONCHANGEPASSWORDFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.session_login_fragment, LAYOUT_SESSIONLOGINFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.session_menu_entity_cardview, LAYOUT_SESSIONMENUENTITYCARDVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.session_organization_cardview, LAYOUT_SESSIONORGANIZATIONCARDVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.session_organization_fragment, LAYOUT_SESSIONORGANIZATIONFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.session_organization_with_tabs_fragment, LAYOUT_SESSIONORGANIZATIONWITHTABSFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.session_privilege_cardview, LAYOUT_SESSIONPRIVILEGECARDVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.session_resetpassword_fragment, LAYOUT_SESSIONRESETPASSWORDFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.session_unix_permission, LAYOUT_SESSIONUNIXPERMISSION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.me.mseotsanyana.mande.R.layout.session_workspace_cardview, LAYOUT_SESSIONWORKSPACECARDVIEW);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_COMPONENTPARENTCARDVIEW: {
          if ("layout/component_parent_cardview_0".equals(tag)) {
            return new ComponentParentCardviewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for component_parent_cardview is invalid. Received: " + tag);
        }
        case  LAYOUT_LOGFRAMECHILDCARDVIEW: {
          if ("layout/logframe_child_cardview_0".equals(tag)) {
            return new LogframeChildCardviewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for logframe_child_cardview is invalid. Received: " + tag);
        }
        case  LAYOUT_LOGFRAMELISTFRAGMENT: {
          if ("layout/logframe_list_fragment_0".equals(tag)) {
            return new LogframeListFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for logframe_list_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_LOGFRAMEPARENTCARDVIEW: {
          if ("layout/logframe_parent_cardview_0".equals(tag)) {
            return new LogframeParentCardviewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for logframe_parent_cardview is invalid. Received: " + tag);
        }
        case  LAYOUT_MECOMMONATTRIBUTES: {
          if ("layout/me_common_attributes_0".equals(tag)) {
            return new MeCommonAttributesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for me_common_attributes is invalid. Received: " + tag);
        }
        case  LAYOUT_METABLAYOUTVIEWPAGER2: {
          if ("layout/me_tablayout_viewpager2_0".equals(tag)) {
            return new MeTablayoutViewpager2BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for me_tablayout_viewpager2 is invalid. Received: " + tag);
        }
        case  LAYOUT_METOOLBARLAYOUT: {
          if ("layout/me_toolbar_layout_0".equals(tag)) {
            return new MeToolbarLayoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for me_toolbar_layout is invalid. Received: " + tag);
        }
        case  LAYOUT_PROJECTCHILDCARDVIEW: {
          if ("layout/project_child_cardview_0".equals(tag)) {
            return new ProjectChildCardviewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for project_child_cardview is invalid. Received: " + tag);
        }
        case  LAYOUT_PROJECTLISTFRAGMENT: {
          if ("layout/project_list_fragment_0".equals(tag)) {
            return new ProjectListFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for project_list_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_PROJECTPARENTCARDVIEW: {
          if ("layout/project_parent_cardview_0".equals(tag)) {
            return new ProjectParentCardviewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for project_parent_cardview is invalid. Received: " + tag);
        }
        case  LAYOUT_SESSIONCHANGEPASSWORDFRAGMENT: {
          if ("layout/session_changepassword_fragment_0".equals(tag)) {
            return new SessionChangepasswordFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for session_changepassword_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_SESSIONLOGINFRAGMENT: {
          if ("layout/session_login_fragment_0".equals(tag)) {
            return new SessionLoginFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for session_login_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_SESSIONMENUENTITYCARDVIEW: {
          if ("layout/session_menu_entity_cardview_0".equals(tag)) {
            return new SessionMenuEntityCardviewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for session_menu_entity_cardview is invalid. Received: " + tag);
        }
        case  LAYOUT_SESSIONORGANIZATIONCARDVIEW: {
          if ("layout/session_organization_cardview_0".equals(tag)) {
            return new SessionOrganizationCardviewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for session_organization_cardview is invalid. Received: " + tag);
        }
        case  LAYOUT_SESSIONORGANIZATIONFRAGMENT: {
          if ("layout/session_organization_fragment_0".equals(tag)) {
            return new SessionOrganizationFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for session_organization_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_SESSIONORGANIZATIONWITHTABSFRAGMENT: {
          if ("layout/session_organization_with_tabs_fragment_0".equals(tag)) {
            return new SessionOrganizationWithTabsFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for session_organization_with_tabs_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_SESSIONPRIVILEGECARDVIEW: {
          if ("layout/session_privilege_cardview_0".equals(tag)) {
            return new SessionPrivilegeCardviewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for session_privilege_cardview is invalid. Received: " + tag);
        }
        case  LAYOUT_SESSIONRESETPASSWORDFRAGMENT: {
          if ("layout/session_resetpassword_fragment_0".equals(tag)) {
            return new SessionResetpasswordFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for session_resetpassword_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_SESSIONUNIXPERMISSION: {
          if ("layout/session_unix_permission_0".equals(tag)) {
            return new SessionUnixPermissionBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for session_unix_permission is invalid. Received: " + tag);
        }
        case  LAYOUT_SESSIONWORKSPACECARDVIEW: {
          if ("layout/session_workspace_cardview_0".equals(tag)) {
            return new SessionWorkspaceCardviewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for session_workspace_cardview is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(20);

    static {
      sKeys.put("layout/component_parent_cardview_0", com.me.mseotsanyana.mande.R.layout.component_parent_cardview);
      sKeys.put("layout/logframe_child_cardview_0", com.me.mseotsanyana.mande.R.layout.logframe_child_cardview);
      sKeys.put("layout/logframe_list_fragment_0", com.me.mseotsanyana.mande.R.layout.logframe_list_fragment);
      sKeys.put("layout/logframe_parent_cardview_0", com.me.mseotsanyana.mande.R.layout.logframe_parent_cardview);
      sKeys.put("layout/me_common_attributes_0", com.me.mseotsanyana.mande.R.layout.me_common_attributes);
      sKeys.put("layout/me_tablayout_viewpager2_0", com.me.mseotsanyana.mande.R.layout.me_tablayout_viewpager2);
      sKeys.put("layout/me_toolbar_layout_0", com.me.mseotsanyana.mande.R.layout.me_toolbar_layout);
      sKeys.put("layout/project_child_cardview_0", com.me.mseotsanyana.mande.R.layout.project_child_cardview);
      sKeys.put("layout/project_list_fragment_0", com.me.mseotsanyana.mande.R.layout.project_list_fragment);
      sKeys.put("layout/project_parent_cardview_0", com.me.mseotsanyana.mande.R.layout.project_parent_cardview);
      sKeys.put("layout/session_changepassword_fragment_0", com.me.mseotsanyana.mande.R.layout.session_changepassword_fragment);
      sKeys.put("layout/session_login_fragment_0", com.me.mseotsanyana.mande.R.layout.session_login_fragment);
      sKeys.put("layout/session_menu_entity_cardview_0", com.me.mseotsanyana.mande.R.layout.session_menu_entity_cardview);
      sKeys.put("layout/session_organization_cardview_0", com.me.mseotsanyana.mande.R.layout.session_organization_cardview);
      sKeys.put("layout/session_organization_fragment_0", com.me.mseotsanyana.mande.R.layout.session_organization_fragment);
      sKeys.put("layout/session_organization_with_tabs_fragment_0", com.me.mseotsanyana.mande.R.layout.session_organization_with_tabs_fragment);
      sKeys.put("layout/session_privilege_cardview_0", com.me.mseotsanyana.mande.R.layout.session_privilege_cardview);
      sKeys.put("layout/session_resetpassword_fragment_0", com.me.mseotsanyana.mande.R.layout.session_resetpassword_fragment);
      sKeys.put("layout/session_unix_permission_0", com.me.mseotsanyana.mande.R.layout.session_unix_permission);
      sKeys.put("layout/session_workspace_cardview_0", com.me.mseotsanyana.mande.R.layout.session_workspace_cardview);
    }
  }
}
