package com.me.mseotsanyana.mande.application.structures;

public class CPreferenceConstant {
    // Shared preferences file name
    public static final String KEY_USER_PREFS                     = "sessions";

    // General global settings
    public static final String KEY_USER_PROFILE                   = "KEY-USER-PROFILE";

    // global settings for bitwise access control
    public static final String KEY_USER_ID                        = "KEY-USER-ID";
    public static final String KEY_USER_NAME                      = "KEY-USER-NAME";
    public static final String KEY_USER_OWNER_ID                  = "KEY-USER-OWNER-ID";
    public static final String KEY_USER_WORKSPACE_MEMBERSHIP_BITS = "KEY-USER-WKS-MEMBERSHIP-BITS";
    public static final String KEY_USER_ORGANIZATIONS             = "KEY-USER-ORGANIZATIONS";

    public static final String KEY_ORG_ID                         = "KEY-ORG-ID";
    public static final String KEY_ORG_OWNER_ID                   = "KEY-ORG-OWNER-ID";
    public static final String KEY_WORKSPACE_COMPOSITE_ID         = "KEY-WKSC-ID";
    public static final String KEY_WORKSPACE_ID                   = "KEY-WKS-ID";
    public static final String KEY_WORKSPACE_OWNER_BIT            = "KEY-WKS-OWNER-BIT";
    public static final String KEY_MODULE_BITS                    = "KEY-M-BITS";
    public static final String KEY_MODULE_ENTITY_BITS             = "KEY-ME-BITS";
    public static final String KEY_MODULE_ENTITY_ACTION_BITS      = "KEY-MEA-BITS";
    public static final String KEY_MODULE_ENTITY_ACTION_STATUS_BITS = "KEY-MEAS-BITS";
    public static final String KEY_MODULE_ENTITY_PERM_BITS        = "KEY-MEP-BITS";
    public static final String KEY_MENU_ITEM_BITS                 = "KEY-MI-BITS";

    public static final String KEY_SECONDARY_WORKSPACES   = "KEY-SWS";
    public static final String KEY_ORG_NAME               = "KEY-ORG-NAME";
    public static final String KEY_PRIMARY_WORKSPACE_NAME = "KEY-PWB_NAME";

    public static final String KEY_ENTITY_OPERATION_BITS  = "KEY-EOB";
    public static final String KEY_OPERATION_STATUS_BITS  = "KEY-OSB";
    public static final String KEY_UNIX_PERM_BITS         = "KEY-UPB";
    //public static final String KEY_IS_LOGGEDIN            = "KEY-ISLOGGEDIN";

    /* system module constants */
    public static final int SESSION           = 1;
    public static final int PROGRAMME_MODULE  = 2;
    public static final int EVALUATION_MODULE = 4;

    /* system entity constants */
    public static final int USER             = 1;
    //public static final int SESSION          = 2;
    public static final int PLAN             = 4;
    public static final int FEATURE          = 8;
    public static final int ORGANIZATION     = 16;
    public static final int USERACCOUNT      = 32;
    public static final int WORKSPACE        = 64;
    public static final int ROLE             = 128;
    public static final int PRIVILEGE        = 256;

    public static final int PROJECT          = 1;
    public static final int LOGFRAME         = 2;
    public static final int IMPACT           = 4;
    public static final int OUTCOME          = 8;
    public static final int OUTPUT           = 16;
    public static final int ACTIVITY         = 32;
    public static final int INPUT            = 64;

    /* system entity operation constants */
    public static final int CREATE           = 1;
    public static final int READ             = 2;
    public static final int UPDATE           = 4;
    public static final int DELETE           = 8;
    public static final int JOIN             = 16;
    public static final int UPLOAD           = 32;
    public static final int DOWNLOAD         = 64;

    /* unix-style row-level operations */
    public static final int OWNER_CREATE     = 524288;
    public static final int ROOM_CREATE      = 262144;
    public static final int HOUSE_CREATE     = 131072;
    public static final int VILLAGE_CREATE   = 65536;
    public static final int CLOUD_CREATE     = 32768;

    public static final int OWNER_READ       = 16384;
    public static final int ROOM_READ        = 8192;
    public static final int HOUSE_READ       = 4096;
    public static final int VILLAGE_READ     = 2048;
    public static final int CLOUD_READ       = 1024;

    public static final int OWNER_UPDATE     = 512;
    public static final int ROOM_UPDATE      = 256;
    public static final int HOUSE_UPDATE     = 128;
    public static final int VILLAGE_UPDATE   = 64;
    public static final int CLOUD_UPDATE     = 32;

    public static final int OWNER_DELETE     = 16;
    public static final int ROOM_DELETE      = 8;
    public static final int HOUSE_DELETE     = 4;
    public static final int VILLAGE_DELETE   = 2;
    public static final int CLOUD_DELETE     = 1;

    public static final int[] permissions = {
            OWNER_CREATE,     /* 0  */
            ROOM_CREATE,      /* 1  */
            HOUSE_CREATE,     /* 2  */
            VILLAGE_CREATE,   /* 3  */
            CLOUD_CREATE,     /* 4  */
            OWNER_READ,       /* 5  */
            ROOM_READ,        /* 6  */
            HOUSE_READ,       /* 7  */
            VILLAGE_READ,     /* 8  */
            CLOUD_READ,       /* 9  */
            OWNER_UPDATE,     /* 10 */
            ROOM_UPDATE,      /* 11 */
            HOUSE_UPDATE,     /* 12 */
            VILLAGE_UPDATE,   /* 13 */
            CLOUD_UPDATE,     /* 14 */
            OWNER_DELETE,     /* 15 */
            ROOM_DELETE,      /* 16 */
            HOUSE_DELETE,     /* 17 */
            VILLAGE_DELETE,   /* 18 */
            CLOUD_DELETE,     /* 19 */
    };

    /* operation statuses */
    public static final int DELETED   = 1;
    public static final int INACTIVE  = 2;
    public static final int ACTIVE    = 4;
    public static final int CANCELLED = 8;
    public static final int PENDING   = 16;

    public static final int ADMIN_WORKSPACE = 1;


//    private SharedPreferences settings;
//    private SharedPreferences.Editor editor;

//    @SuppressLint("CommitPrefEdits")
//    public CSharedPreference(@NonNull Context context) {
//        setSettings(context.getSharedPreferences(KEY_USER_PREFS, Context.MODE_PRIVATE));
//        setEditor(settings.edit());
//    }
//
//    public SharedPreferences.Editor getEditor() {
//        return editor;
//    }
//
//    public void setEditor(SharedPreferences.Editor editor) {
//        this.editor = editor;
//    }
//
//    public SharedPreferences getSettings() {
//        return settings;
//    }
//
//    public void setSettings(SharedPreferences settings) {
//        this.settings = settings;
//    }
}
