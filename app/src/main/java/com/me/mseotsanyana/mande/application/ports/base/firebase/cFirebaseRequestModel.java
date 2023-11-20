package com.me.mseotsanyana.mande.infrastructure.ports.base.firebase;

import com.google.firebase.firestore.Query;

public class cFirebaseRequestModel {
    private Object listener;
    private Query query;

    public cFirebaseRequestModel(Object listener, Query query){
        this.listener = listener;
        this.query = query;
    }

    public Object getListener() {
        return listener;
    }

    public void setListener(Object listener) {
        this.listener = listener;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
