package com.me.mseotsanyana.mande.application.interactors.programme.input;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iAddInputInteractor extends IInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
