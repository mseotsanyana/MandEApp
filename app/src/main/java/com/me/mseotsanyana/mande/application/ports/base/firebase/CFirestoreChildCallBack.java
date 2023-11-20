package com.me.mseotsanyana.mande.application.ports.base.firebase;

public abstract class CFirebaseChildCallBack {

    public abstract void onChildAdded(Object object);

    public abstract void onChildChanged(Object object);

    public abstract void onChildRemoved(Object object);

    public abstract void onCancelled(Object object);
}
