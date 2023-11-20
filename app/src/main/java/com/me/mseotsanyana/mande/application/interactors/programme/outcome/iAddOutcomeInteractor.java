package com.me.mseotsanyana.mande.application.interactors.programme.outcome;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iAddOutcomeInteractor extends iInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
