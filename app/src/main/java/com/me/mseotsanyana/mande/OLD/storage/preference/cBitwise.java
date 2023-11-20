package com.me.mseotsanyana.mande.framework.storage.preference;

/**
 * Created by mseotsanyana on 2017/09/06.
 */

public class cBitwise {

    // Define actions(mask) for lowest level permissions as constants which
    // are powers of 2. This ensures that only one bit in an integer
    // is set to 1. Make these private because combinations will be
    // used to publicly access valid permission sets.

    /* system module constants */
    public static final int SESSION_MODULE    = 1;
    public static final int LOGFRAME_MODULE   = 2;
    public static final int EVALUATION_MODULE = 4;

    /* system entity constants */
    public static final int ADDRESS      = 1;
    public static final int ORGANIZATION = 2;
    public static final int VALUE        = 4;
    public static final int USER         = 8;
    public static final int SESSION      = 16;
    public static final int ROLE         = 32;
    public static final int MENU         = 64;
    public static final int PRIVILEGE    = 128;
    public static final int ENTITY       = 256;
    public static final int OPERATION    = 512;
    public static final int STATUS       = 1024;

    public static final int PROJECT      = 1;
    public static final int LOGFRAME     = 2;

    public static final int IMPACT       = 2;
    public static final int OUTCOME      = 4;
    public static final int OUTPUT       = 8;
    public static final int ACTIVITY     = 16;
    public static final int INPUT        = 32;

    public static final int MOVs         = 64;
    public static final int RISK         = 512;
    public static final int WORKPLAN     = 1024;
    public static final int BUDGET       = 2048;
    public static final int RESOURCE     = 4096;
    public static final int EVALUATION   = 8192;
    public static final int CRITERIA     = 16384;
    public static final int QUESTION     = 32768;
    public static final int CATEGORY     = 65536;
    public static final int EVALUATING   = 131072;
    public static final int MONITORING   = 262144;
    public static final int REPORT       = 524288;
    public static final int ALERT        = 1048576;

    /* system operation constants */
    public static final int CREATE = 1;
    public static final int READ   = 2;
    public static final int UPDATE = 4;
    public static final int DELETE = 8;
    public static final int SYNC   = 16;

    public static final int ALL_PERMS = CREATE | READ | UPDATE | DELETE | SYNC;

    public static final int[] entities = {
            ADDRESS,      /* 0 = 1 */
            ORGANIZATION, /* 1 = 1 */
            VALUE,        /* 2 = 1 */
            USER,         /* 3 = 1 */
            SESSION,      /* 4 = 1 */
            ROLE,         /* 5 = 1 */
            MENU,         /* 6 = 1 */
            PRIVILEGE,    /* 7 = 1 */
            ENTITY,       /* 8 = 1 */
            OPERATION,    /* 9 = 1 */
            STATUS,       /* 10 = 1 */
            PROJECT,      /* 11 = 2 */
            LOGFRAME,     /* 12 = 2 */
            IMPACT,       /* 13 = 2 */
            OUTCOME,      /* 14 = 2 */
            OUTPUT,       /* 15 = 2 */
            ACTIVITY,     /* 17 = 2 */
            INPUT,        /* 18 = 2 */
            MOVs,         /* 19 = 2 */
            RISK,         /* 20 = 2 */
            WORKPLAN,     /* 21 = 2 */
            BUDGET,       /* 22 = 2 */
            RESOURCE,     /* 23 = 2 */
            EVALUATION,   /* 24 = 2 */
            CRITERIA,     /* 25 = 2 */
            QUESTION,     /* 26 = 2 */
            CATEGORY,     /* 27 = 2 */
            EVALUATING,   /* 28 = 2 */
            MONITORING,   /* 29 = 2 */
            REPORT,       /* 30 = 2 */
            ALERT         /* 31 = 2 */
    };

    /* status bits */
    public static final int DELETED   = 1;
    public static final int BLOCKED   = 2;
    public static final int ACTIVATED = 4;
    public static final int CANCELLED = 8;
    public static final int PENDING   = 16;
    public static final int ALL_STATUSES = 31;

    public static final int[] statuses = {
            DELETED,   /* 0 */
            BLOCKED,   /* 1 */
            ACTIVATED, /* 2 */
            CANCELLED, /* 3 */
            PENDING    /* 4 */
    };

    /* unix-like permission bits */
    /* global operations (i.e., type 1) */
    public static final int OWNER_CREATE = 1;
    public static final int GROUP_CREATE = 2;
    public static final int OTHER_CREATE = 4;

    /* local operations (i.e., type 2) */
    public static final int OWNER_READ   = 2048;
    public static final int OWNER_UPDATE = 1024;
    public static final int OWNER_DELETE = 512;

    public static final int PRIMARY_TEAM_READ   = 256;
    public static final int PRIMARY_TEAM_UPDATE = 128;
    public static final int PRIMARY_TEAM_DELETE = 64;

    public static final int SECONDARY_TEAM_READ   = 32;
    public static final int SECONDARY_TEAM_UPDATE = 16;
    public static final int SECONDARY_TEAM_DELETE = 8;

    public static final int ORGANIZATION_READ   = 4;
    public static final int ORGANIZATION_UPDATE = 2;
    public static final int ORGANIZATION_DELETE = 1;

    public static final int[] permissions = {
            OWNER_READ,            /* 0  */
            OWNER_UPDATE,          /* 1  */
            OWNER_DELETE,          /* 2  */
            OWNER_UPDATE,          /* 3  */
            PRIMARY_TEAM_READ,     /* 4  */
            PRIMARY_TEAM_UPDATE,   /* 5  */
            PRIMARY_TEAM_DELETE,   /* 6  */
            SECONDARY_TEAM_READ,   /* 7  */
            SECONDARY_TEAM_UPDATE, /* 8  */
            SECONDARY_TEAM_DELETE, /* 9  */
            ORGANIZATION_READ,     /* 10 */
            ORGANIZATION_UPDATE,   /* 11 */
            ORGANIZATION_DELETE    /* 12 */
    };


    public static final int PERMISSION = ENTITY | OPERATION | STATUS;

    public static final int ROLE_PRIVILEGE = ROLE | PRIVILEGE;

    public static final int[] aux_entities = {
            ROLE_PRIVILEGE,  /* 0 = 160   */
            PERMISSION       /* 1 = 21792 */
    };

    public static final int[] types = {
            SESSION_MODULE,  /* 0 = 1 */
            LOGFRAME_MODULE  /* 1 = 2 */
    };

    public static final int[] entity_types = {
            1,  /* 0 = BRBAC (1) */
            2   /* 1 = PPMER (2) */
    };

    public static final String[] perm_names = {
            "Own Add",      /* 0  */
            "Group Add",    /* 1  */
            "Other Add",    /* 2  */
            "Own Read",     /* 3  */
            "Group Read",   /* 4  */
            "Other Read",   /* 5  */
            "Owner Update", /* 6  */
            "Group Update", /* 7  */
            "Other Update", /* 8  */
            "Owner Delete", /* 9  */
            "Group Delete", /* 10 */
            "Other Delete", /* 11 */
            "Owner Sync",   /* 12 */
            "Group Sync",   /* 13 */
            "Other Sync"    /* 14 */
    };

    public enum EntityType {
        PROJECT, LOGFRAME;
    }

/*
    public static final int OWNER = OWNER_CREATE | OWNER_READ | OWNER_UPDATE |
            OWNER_DELETE | OWNER_SYNC;
    public static final int GROUP = GROUP_CREATE | GROUP_READ | GROUP_UPDATE |
            GROUP_DELETE | GROUP_SYNC;
    public static final int OTHER = OTHER_CREATE | OTHER_READ | OTHER_UPDATE |
            OTHER_DELETE | OTHER_SYNC;

    public static final int[] cruds_perms = {
            CREATE,       /* 0
            READ,         /* 1
            UPDATE,       /* 2
            DELETE,       /* 3
            SYNC          /* 4
    };


    public static final int[] composite_perms = {
            CREATE,  0
            READ,    1
            UPDATE,  2
            DELETE,  3
            SYNC     4
    };
*/

    public static final String[] status_names = {
            "Activated", /* 0 */
            "Cancelled", /* 1 */
            "Deleted",   /* 2 */
            "Pending",   /* 3 */
            "Synced"     /* 4 */
    };
    public static final String[] status_descriptions = {
            "Is the record activated?",      /* 0 */
            "Is the record called?",         /* 1 */
            "Deleted Is the record deleted?",/* 2 */
            "Is the record pending?",        /* 3 */
            "Is the record synced?"          /* 4 */
    };

    public static final int ALL       = 31;

    public static final int[] aux_statuses = {
            ALL     /* 0 */

    };


    public cBitwise() {
    }

    /*
     * uid: is the user's id (from logged in user)
     * owner: is the owner of the table (one who created a record)
     * group: is the group that the table belongs to (group == owner organization)
     * memberships: are the groups that the table belongs to (memberships = other organization)
     * unixperms: are the permissions of the table (assigned to respective entities)
     */

    /* this is a global function - global operations = operations on whole table*/
    public boolean isCreate(int uid, int owner, int group, int memberships, int unixperms) {
        return (((uid == owner) && ((unixperms & permissions[0]) != 0)) ||
                (((memberships & group) == group) && ((unixperms & permissions[1]) != 0)) ||
                ((unixperms & permissions[2]) != 0));
    }

    public boolean isCreate(int local_perm) {
        return (((local_perm & permissions[0]) == permissions[0]) ||
                ((local_perm & permissions[1]) == permissions[1]) ||
                ((local_perm & permissions[2]) == permissions[2]));
    }

    /*
     * uid: is the user's id (from logged in user)
     * owner: is the owner of the record (one who created a record) - from db
     * group: is the group the record belongs to (group == owner organization) - from db
     * other: are the groups the user belongs to (memberships = other organization) - from db
     * unixperms: are the permissions of the record (assigned to respective entities) - from db
     */

    /* this is a local function - local operations = operations on a row of a table*/
    public boolean isRead(int uid, int owner, int group, int other, int memberships, int unixperms) {
        return (
                ((uid == owner) && ((unixperms & permissions[3]) == permissions[3])) ||
                (((memberships & group) == group) && ((unixperms & permissions[4]) == permissions[4])) ||
                (((memberships & other) != 0) && ((unixperms & permissions[5]) == permissions[5]))
        );
    }

    public static boolean isRead(int entityID, int entities, int operations, int status, int statuses) {
        return (((entityID & entities) != 0) && (((operations & permissions[3]) != 0) ||
                ((operations & permissions[4]) != 0) || ((operations & permissions[5]) != 0)) &&
                (status != 0 || ((status & statuses) != 0)));
    }

    public static boolean isRead(int operationBITS, int statusBITS) {
        return (((((operationBITS & permissions[3]) != 0) || ((operationBITS & permissions[4]) != 0) ||
                ((operationBITS & permissions[5]) != 0)) && ((statusBITS & aux_statuses[0]) != 0)));
    }

    public static boolean isEntity(int entityID, int entityBITS) {
        return ((entityBITS & entityID) == entityID);
    }

    /*
     * uid: is the user's id (from logged in user)
     * owner: is the owner of the record (one who created a record)
     * group: is the group the record belongs to (group == owner organization)
     * memberships: are the groups the user belongs to (memberships = other organization)
     * unixperms: are the permissions of the record (assigned to respective entities)
     */

    /* this is a local function - local operations = operations on a row of a table
    public boolean isUpdate(int uid, int owner, int group, int other, int memberships, int unixperms) {
        return (
                ((uid == owner) && ((unixperms & permissions[6]) == permissions[6])) ||
                (((memberships & group) == group) && ((unixperms & permissions[7]) == permissions[7])) ||
                (((memberships & other) != 0) && ((unixperms & permissions[8]) == permissions[8]))
        );
    }

    public boolean isUpdate(int local_perm) {
        return (((local_perm & permissions[6]) == permissions[6]) ||
                ((local_perm & permissions[7]) == permissions[7]) ||
                ((local_perm & permissions[8]) == permissions[8]));
    }*/

    /*
     * uid: is the user's id (from logged in user)
     * owner: is the owner of the record (one who created a record)
     * group: is the group the record belongs to (group == owner organization)
     * memberships: are the groups the user belongs to (memberships = other organization)
     * unixperms: are the permissions of the record (assigned to respective entities)
     */

    /* this is a local function - local operations = operations on a row of a table
    public boolean isDelete(int uid, int owner, int group, int other, int memberships, int unixperms) {
        return (
                ((uid == owner) && ((unixperms & permissions[9]) == permissions[9])) ||
                (((memberships & group) == group) && ((unixperms & permissions[10]) == permissions[10])) ||
                (((memberships & other) != 0) && ((unixperms & permissions[11]) == permissions[11]))
        );
    }

    public boolean isDelete(int local_perm) {
        return (((local_perm & permissions[9]) == permissions[9]) ||
                ((local_perm & permissions[10]) == permissions[10]) ||
                ((local_perm & permissions[11]) == permissions[11]));
    }*/

    /*
     * uid: is the user's id (from logged in user)
     * owner: is the owner of the record (one who created a record)
     * group: is the group the record belongs to (group == owner organization)
     * memberships: are the groups the user belongs to (memberships = other organization)
     * unixperms: are the permissions of the record (assigned to respective entities)
     */

    /* this is a local function - local operations = operations on a row of a table
    public boolean isSynchronize(int uid, int owner, int group, int other, int memberships, int unixperms) {
        return (
                ((uid == owner) && ((unixperms & permissions[12]) == permissions[12])) ||
                (((memberships & group) == group) && ((unixperms & permissions[13]) == permissions[13])) ||
                (((memberships & other) != 0) && ((unixperms & permissions[14]) == permissions[14]))
        );
    }

    public boolean isSynchronize(int local_perm) {
        return (((local_perm & permissions[12]) == permissions[12]) ||
                ((local_perm & permissions[13]) == permissions[13]) ||
                ((local_perm & permissions[14]) == permissions[14]));
    }

    // Check permission(s)
    public boolean isPermitted(int myPermissions, int permissionToCheck) {
        return ((myPermissions & permissionToCheck) == permissionToCheck);
    }

    // Public setter methods to make sure that only valid
    // permission sets can be assigned
    public int addPermission(int unixperms, int permissionToAdd) throws cDBSecurityException {
        return addPermissions(unixperms, new int[]{permissionToAdd});

    }

    public int addPermissions(int unixperms, int[] permissionsToAdd) throws cDBSecurityException {
        for (int i = 0; i < permissionsToAdd.length; i++) {
            unixperms |= permissionsToAdd[i];
        }

        if (Arrays.binarySearch(permissions, unixperms) < 0) {
            throw new cDBSecurityException(
                    "Resulting permission set will be invalid.  Aborted.");
        } else {
            return unixperms;
        }
    }

    public int deletePermission(int unixperms, int permissionToDelete) throws cDBSecurityException {
        return deletePermissions(unixperms, new int[]{permissionToDelete});
    }

    public int deletePermissions(int unixperms, int[] permissionsToDelete) throws cDBSecurityException {
        for (int i = 0; i < permissionsToDelete.length; i++) {
            unixperms &= ~permissionsToDelete[i];
        }

        if (Arrays.binarySearch(permissions, unixperms) < 0) {
            throw new cDBSecurityException(
                    "Resulting permission set will be invalid.  Aborted.");
        } else {
            return unixperms;
        }
    }

    // Toggle permission(s) - off if on, on if off - RARELY USED
    public static int togglePermission(int unixperms, int permissionToToggle) throws cDBSecurityException {

        unixperms ^= permissionToToggle;

        if (Arrays.binarySearch(permissions, unixperms) < 0) {
            throw new cDBSecurityException(
                    "Resulting permission set will be invalid.  Aborted.");
        } else {
            return unixperms;
        }
    }*/
}