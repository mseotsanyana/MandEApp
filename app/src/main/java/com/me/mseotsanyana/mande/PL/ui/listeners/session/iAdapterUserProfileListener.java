package com.me.mseotsanyana.mande.PL.ui.listeners.session;

import android.util.SparseBooleanArray;

import com.me.mseotsanyana.mande.BLL.entities.models.session.CUserProfileModel;

public interface iAdapterUserProfileListener {
    void onClickUpdateUserProfileImage(CUserProfileModel userProfileModel);
    void onLongClickUserProfile(SparseBooleanArray selectedItems);
}
