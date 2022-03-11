package com.me.mseotsanyana.mande.BLL.interactors.programme.activity;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iAddOutputInteractor extends iInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
