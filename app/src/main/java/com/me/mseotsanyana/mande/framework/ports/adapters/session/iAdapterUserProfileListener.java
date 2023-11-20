package com.me.mseotsanyana.mande.framework.ports.adapters.session;

import android.util.SparseBooleanArray;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;

public interface iAdapterUserProfileListener {
    void onClickUpdateUserProfileImage(CUserProfileModel userProfileModel);
    void onLongClickUserProfile(SparseBooleanArray selectedItems);
}
