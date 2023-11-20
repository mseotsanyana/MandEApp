package com.me.mseotsanyana.mande.application.ports.base.firebase;

public abstract class CFirestoreCallBack {
    public abstract void onFirebaseSuccess(Object object);

    public abstract void onFirebaseFailure(Object object);
}
