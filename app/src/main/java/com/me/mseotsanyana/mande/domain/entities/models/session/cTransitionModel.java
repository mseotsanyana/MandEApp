package com.me.mseotsanyana.mande.domain.entities.models.session;

public class cTransitionModel {
    private String eventServerID;
    private String actionServerID;
    private String sourceServerID;
    private String targetServerID;

    public cTransitionModel() {
    }

    public cTransitionModel(cTransitionModel transitionModel){
        this.setEventServerID(transitionModel.getEventServerID());
        this.setActionServerID(transitionModel.getActionServerID());
        this.setSourceServerID(transitionModel.getSourceServerID());
        this.setTargetServerID(transitionModel.getTargetServerID());
    }

    public String getEventServerID() {
        return eventServerID;
    }

    public void setEventServerID(String eventServerID) {
        this.eventServerID = eventServerID;
    }

    public String getActionServerID() {
        return actionServerID;
    }

    public void setActionServerID(String actionServerID) {
        this.actionServerID = actionServerID;
    }

    public String getSourceServerID() {
        return sourceServerID;
    }

    public void setSourceServerID(String sourceServerID) {
        this.sourceServerID = sourceServerID;
    }

    public String getTargetServerID() {
        return targetServerID;
    }

    public void setTargetServerID(String targetServerID) {
        this.targetServerID = targetServerID;
    }
}
