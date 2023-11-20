package com.me.mseotsanyana.mande.domain.entities.interfaces;

public interface IEventManagerState {
    void subscribe(String eventType, IEventListenerState listener);
    void unsubscribe(String eventType, IEventListenerState listener);
    void notify(String eventType, String message);
}
