package com.me.mseotsanyana.mande.framework.ports.adapters.session;

import com.me.mseotsanyana.treeadapterlibrary.cNode;

public interface iViewPrivilegeListener {
    void onClickUpdatePrivilege(cNode node);
    void onClickDeletePrivilege(String privilegeServerID);
}
