package com.me.mseotsanyana.mande.application.interactors.programme.outcome;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iAddOutcomeInteractor extends IInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
