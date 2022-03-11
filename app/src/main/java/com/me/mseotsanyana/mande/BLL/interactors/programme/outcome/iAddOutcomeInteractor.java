package com.me.mseotsanyana.mande.BLL.interactors.programme.outcome;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iAddOutcomeInteractor extends iInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
