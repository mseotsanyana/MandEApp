package com.me.mseotsanyana.mande.BLL.interactors.programme.input;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iAddInputInteractor extends iInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
