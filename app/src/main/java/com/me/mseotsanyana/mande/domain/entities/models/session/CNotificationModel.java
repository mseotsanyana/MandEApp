package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.interfaces.IEventListenerState;

public class CNotificationModel implements IEventListenerState {
    private String notificationServerID;
    private String subscriberServerID;
    private String transitionServerID;

    private String userOwnerID;
    private String organizationOwnerID;
    private int workspaceOwnerBIT;
    private int unixpermBITS;
    private int statusBIT;

    private String message;

    public String getNotificationServerID() {
        return notificationServerID;
    }

    public void setNotificationServerID(String notificationServerID) {
        this.notificationServerID = notificationServerID;
    }

    public String getSubscriberServerID() {
        return subscriberServerID;
    }

    public void setSubscriberServerID(String subscriberServerID) {
        this.subscriberServerID = subscriberServerID;
    }

    public String getTransitionServerID() {
        return transitionServerID;
    }

    public void setTransitionServerID(String transitionServerID) {
        this.transitionServerID = transitionServerID;
    }

    public String getUserOwnerID() {
        return userOwnerID;
    }

    public void setUserOwnerID(String userOwnerID) {
        this.userOwnerID = userOwnerID;
    }

    public String getOrganizationOwnerID() {
        return organizationOwnerID;
    }

    public void setOrganizationOwnerID(String organizationOwnerID) {
        this.organizationOwnerID = organizationOwnerID;
    }

    public int getWorkspaceOwnerBIT() {
        return workspaceOwnerBIT;
    }

    public void setWorkspaceOwnerBIT(int workspaceOwnerBIT) {
        this.workspaceOwnerBIT = workspaceOwnerBIT;
    }

    public int getUnixpermBITS() {
        return unixpermBITS;
    }

    public void setUnixpermBITS(int unixpermBITS) {
        this.unixpermBITS = unixpermBITS;
    }

    public int getStatusBIT() {
        return statusBIT;
    }

    public void setStatusBIT(int statusBIT) {
        this.statusBIT = statusBIT;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // eventType = entityServerID + eventServerID
    @Override
    public void update(String eventType, String message) {
        this.setTransitionServerID(eventType);
        this.setMessage(message);
    }
}
