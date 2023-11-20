package com.me.mseotsanyana.mande.application.structures;

/**
 * Created by mseotsanyana on 2017/09/06.
 */


public class CConstantModel {
    /* entity identifiers */
    public static final String NONEENTITY   = "0";
    public static final String ORGANIZATION = "1";
    public static final String WORKSPACE    = "2";

    /* entity states */
    public static final int INITIAL_STATE      = 1;
    public static final int ACTIVATED_STATE    = 2;
    public static final int APPROVED_STATE     = 4;
    public static final int BLOCKED_STATE      = 8;
    public static final int CANCELLED_STATE    = 16;
    public static final int COMPLETED_STATE    = 32;
    public static final int CLOSED_STATE       = 64;
    public static final int CHANGED_STATE      = 128;
    public static final int DELETED_STATE      = 256;
    public static final int DRAFT_STATE        = 512;
    public static final int DISENABLED_STATE   = 1024;
    public static final int ENABLED_STATE      = 2048;
    public static final int FREEPLAN_STATE     = 4096;
    public static final int NEGOTIATION_STATE  = 8192;
    public static final int PLAN_STATE         = 16384;
    public static final int PENDING_STATE      = 32768;
    public static final int PREMIUMPLAN_STATE  = 65536;
    public static final int REJECTED_STATE     = 131072;
    public static final int RESET_STATE        = 262144;
    public static final int SIGNIN_STATE       = 524288;
    public static final int SIGNUP_STATE       = 1048576;
    public static final int STANDARDPLAN_STATE = 2097152;
    public static final int WORKSPACE_STATE    = 4194304;

    /* type of account requests */
    public static final int ADD_REQUEST    = 1;
    public static final int JOIN_REQUEST   = 2;
    public static final int INVITE_REQUEST = 4;

    /* entity operations */
    public static final String CREATED  = "1";
    public static final String ADDED    = "2";
    public static final String MODIFIED = "4";
    public static final String DELETED  = "8";
    public static final String SIGNIN   = "16";
    public static final String SIGNOUT  = "32";
    public static final String SWITCH   = "64";
    public static final String MENUITEM = "128";

    enum Add{
        ORGANIZATION, WORKSPACE
    }

    public enum Create{
        ORGANIZATION, WORKSPACE
    }

    public enum Delete{
        ORGANIZATION, WORKSPACE
    }

    public enum Modified{
        ORGANIZATION, WORKSPACE
    }

    public enum Updated{
        ORGANIZATION, WORKSPACE
    }
}