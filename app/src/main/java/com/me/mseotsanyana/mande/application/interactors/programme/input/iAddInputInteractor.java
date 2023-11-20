package com.me.mseotsanyana.mande.application.interactors.programme.input;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iAddInputInteractor extends iInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
