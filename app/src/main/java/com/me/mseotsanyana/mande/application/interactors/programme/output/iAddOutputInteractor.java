package com.me.mseotsanyana.mande.application.interactors.programme.output;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iAddOutputInteractor extends iInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
