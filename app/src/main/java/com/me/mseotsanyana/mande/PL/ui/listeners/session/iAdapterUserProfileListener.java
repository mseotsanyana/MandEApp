package com.me.mseotsanyana.mande.PL.ui.listeners.session;

import android.util.SparseBooleanArray;

import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

public interface iAdapterUserProfileListener {
    void onClickUpdateUserProfileImage(cUserProfileModel userProfileModel);
    void onLongClickUserProfile(SparseBooleanArray selectedItems);
}
