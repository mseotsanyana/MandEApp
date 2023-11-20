package com.me.mseotsanyana.mande.domain.entities.models.session;

public class CEntityEventModel {
    private String eventTypeServerID;
    private String entityServerID;
    private String eventServerID;

    private String message;

    public String getEventTypeServerID() {
        return eventTypeServerID;
    }

    public void setEventTypeServerID(String eventTypeServerID) {
        this.eventTypeServerID = eventTypeServerID;
    }

    public String getEntityServerID() {
        return entityServerID;
    }

    public void setEntityServerID(String entityServerID) {
        this.entityServerID = entityServerID;
    }

    public String getEventServerID() {
        return eventServerID;
    }

    public void setEventServerID(String eventServerID) {
        this.eventServerID = eventServerID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
