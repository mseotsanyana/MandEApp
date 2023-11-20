package com.me.mseotsanyana.mande.application.interactors.evaluation;

import com.me.mseotsanyana.mande.application.interactors.base.iInteractor;

public interface iUploadEvaluationInteractor extends iInteractor {
    interface Callback{
        void onUploadEvaluationCompleted(String s);
    }
}
