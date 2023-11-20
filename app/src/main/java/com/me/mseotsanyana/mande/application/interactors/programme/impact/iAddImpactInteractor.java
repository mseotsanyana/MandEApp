package com.me.mseotsanyana.mande.application.interactors.programme.impact;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iAddImpactInteractor extends iInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
