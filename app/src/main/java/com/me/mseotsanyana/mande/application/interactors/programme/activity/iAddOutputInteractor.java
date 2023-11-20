package com.me.mseotsanyana.mande.application.interactors.programme.activity;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iAddOutputInteractor extends IInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
