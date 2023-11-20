package com.me.mseotsanyana.mande.application.interactors.evaluation;

import com.me.mseotsanyana.mande.application.ports.base.IInteractor;

public interface iUploadEvaluationInteractor extends IInteractor {
    interface Callback{
        void onUploadEvaluationCompleted(String s);
    }
}
