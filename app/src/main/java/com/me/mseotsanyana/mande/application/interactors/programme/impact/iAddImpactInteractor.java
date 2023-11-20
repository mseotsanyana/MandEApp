package com.me.mseotsanyana.mande.application.interactors.programme.impact;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iAddImpactInteractor extends IInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
