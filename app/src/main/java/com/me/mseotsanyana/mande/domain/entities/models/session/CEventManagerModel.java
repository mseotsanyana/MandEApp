package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.interfaces.IEventListenerState;
import com.me.mseotsanyana.mande.domain.entities.interfaces.IEventManagerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CEventManagerModel implements IEventManagerState {
    HashMap<String, List<IEventListenerState>> listeners;

    public CEventManagerModel(String... eventTypes){
        this.listeners = new HashMap<>();
        for (String eventType: eventTypes){
            this.listeners.put(eventType, new ArrayList<>());
        }
    }

    @Override
    public void subscribe(String eventType, IEventListenerState listener) {
        List<IEventListenerState> subscribers = this.listeners.get(eventType);
        assert subscribers != null;
        subscribers.add(listener);
    }

    @Override
    public void unsubscribe(String eventType, IEventListenerState listener) {
        List<IEventListenerState> subscribers = this.listeners.get(eventType);
        assert subscribers != null;
        subscribers.remove(listener);
    }

    @Override
    public void notify(String eventType, String message) {
        List<IEventListenerState> subscribers = this.listeners.get(eventType);
        assert subscribers != null;
        for (IEventListenerState listener: subscribers){
            listener.update(eventType, message);
        }
    }
}
